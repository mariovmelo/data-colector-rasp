package br.ufrn.doc.ioteam.jobs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.json.Json;
import javax.json.JsonObject;

import com.google.gson.Gson;
import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubMessageResult;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.MessageCallback;

import br.ufrn.doc.ioteam.device.ActuatorJob;
import br.ufrn.doc.ioteam.device.LEDVagaSensorJob;
import br.ufrn.doc.ioteam.device.PresenceSensorJob;
import br.ufrn.doc.ioteam.device.RFIDSensorJob;

/**
 * Hello world!
 *
 */
public class AppDevice 
{
	private static String connString = "HostName=ioteamhub.azure-devices.net;DeviceId=rfidentrada;SharedAccessKey=HcTo5i2TM/osfqSfWurZSw==";
	private static IotHubClientProtocol protocol = IotHubClientProtocol.AMQPS_WS;
	private static String deviceId = "rfidentrada";
	private static DeviceClient client;
	private MessageSender sender;
	
	public static void main( String[] args ) throws IOException, URISyntaxException
	{
		client = new DeviceClient(connString, protocol);
		
		//Registrar o callback
		MessageCallback callback = new AppMessageCallback();
		client.setMessageCallback(callback, null);
		
		client.open();

		MessageSender sender = new MessageSender();

		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(sender);

		System.out.println("Press ENTER to exit.");
		System.in.read();
		executor.shutdownNow();
		client.close();
	}
	
	public void initiate() throws IOException, URISyntaxException
	{
		client = new DeviceClient(connString, protocol);
		
		//Registrar o callback
		MessageCallback callback = new AppMessageCallback();
		client.setMessageCallback(callback, null);
		
		client.open();

		sender = new MessageSender();
		
		RFIDSensorJob rfidSensorJob = new RFIDSensorJob(this);
		PresenceSensorJob presenceSensor = new PresenceSensorJob(this);
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.execute(sender);
		executor.execute(rfidSensorJob);
		executor.execute(presenceSensor);
		
		//System.out.println("Press ENTER to exit.");
		while(true);
		
		//executor.shutdownNow();
		//client.close();
	} 
	
	//Define o tempo gasto com o envio
	private static class TelemetryDataPoint {
		public String deviceId;
		public double windSpeed;
		public Date date;

		public String serialize() {
			Gson gson = new Gson();
			return gson.toJson(this);
		}
	}
	
	//Define o tempo gasto com o envio
		private static class DadosRFID {
			public String tagId;
			public int detectouCarro;

			public String serialize() {
				Gson gson = new Gson();
				return gson.toJson(this);
			}
		}
		
	//Notifica a main thread que o evento foi processado
	//Ack da cloud
	private static class EventCallback implements IotHubEventCallback
	{
		public void execute(IotHubStatusCode status, Object context) {
			System.out.println("IoT Hub responded to message with status: " + status.name());

			if (context != null) {
				synchronized (context) {
					context.notify();
				}
			}
		}
	}

	//Cloud to device message...
	private static class AppMessageCallback implements MessageCallback {
		private static Map<String, ActuatorJob> listaAtuadores;
		static{
			listaAtuadores = new HashMap<String,ActuatorJob>();
			listaAtuadores.put(LEDVagaSensorJob.DEVICE_KEY, new LEDVagaSensorJob());
		}
		public IotHubMessageResult execute(Message msg, Object context) {
			System.out.println("Received message from hub: "
					+ new String(msg.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET));
			
			JsonObject mensagem = Json.createReader(new ByteArrayInputStream(msg.getBytes())).readObject();
			listaAtuadores.get(mensagem.getString("sensor")).acting(mensagem);
			return IotHubMessageResult.COMPLETE;
		}
	}

	public static class MessageSender implements Runnable {
		
		private Queue<String> fila = new LinkedBlockingQueue<String>() ;
		
		public void run()  {
			try {

				while (true) {
					
					if(fila.peek() != null){
						String msgStr = fila.poll();
						Message msg = new Message(msgStr); 
						//msg.setProperty("carro", "passou");
						
						System.out.println("Sending: " + msgStr);

						Object lockobj = new Object();
						EventCallback callback = new EventCallback();
						client.sendEventAsync(msg, callback, lockobj);

						synchronized (lockobj) {
							lockobj.wait();
						}
						Thread.sleep(3000);
					}
					
				}
			} catch (InterruptedException e) {
				System.out.println("Finished.");
			}
		}
		public void addMsg(String msg){
			fila.add(msg);
		}
	}

	public MessageSender getSender() {
		return sender;
	}
}


