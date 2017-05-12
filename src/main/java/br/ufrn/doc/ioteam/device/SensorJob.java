package br.ufrn.doc.ioteam.device;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.IotHubMessageResult;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.MessageCallback;

import api.reader.nesslab.interfaces.ApiReaderFacade;
import br.ufrn.doc.ioteam.util.Connection;

public abstract class SensorJob{

	private String deviceId;
	private String accessKey;
	
	public SensorJob(String deviceId,String accessKey){
		this.deviceId = deviceId;
		this.accessKey = accessKey;
	}
	
	
	public void initiate() {
		
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
		newSingleThreadExecutor.submit(() -> {
			//Create instance of Microsoft Azure IoT Hub Client.
			//To avoid the creation of multiple DeviceClient instances and invoke open(),
			//it is needed to write the following code outside of while statement.
			Random gerador = new Random();
			try (DeviceClient client = new DeviceClient(String.format(Connection.AZURE_CONECTION,deviceId,accessKey), IotHubClientProtocol.AMQPS_WS)) {
				client.setMessageCallback((Message message, Object object) -> {
					String msg = new String(message.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET);
					acting(msg);
					return IotHubMessageResult.COMPLETE;
				}, null);
				client.open();

				sensing(client);
				//System.out.println(getHTML("http://www.globo.com"));
				
				client.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
	}
	
	public void send(DeviceClient client, Message msg){
		
		client.sendEventAsync(msg, (IotHubStatusCode status, Object context) -> {
			if(!(status == IotHubStatusCode.OK || status == IotHubStatusCode.OK_EMPTY)){
				System.out.println("Failed due to : "+status.name() +":" +context);
			}
		}, msg.getMessageId());
	}
	
	public abstract void acting(String msg);
	
	public abstract void sensing(DeviceClient client);
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

}
