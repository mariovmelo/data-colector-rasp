package br.ufrn.doc.ioteam.device;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.microsoft.azure.sdk.iot.device.DeviceClient;

import api.reader.nesslab.utils.OperationUtil;
import br.ufrn.doc.ioteam.jobs.AppDevice;

public class RFIDSensorJobIn implements SensorJob, Runnable{
	
	public final static String DEVICE_KEY = "ANTENA_RFID_ENTRADA";
	
	private AppDevice appDevice;
	
	public RFIDSensorJobIn(AppDevice appDevice) {
		this.appDevice = appDevice;
	}

	public void run() {
		Scanner sc = new Scanner(System.in);

		try {
			//ApiReaderFacade api = new ApiReaderNesslab("192.168.1.231");

		
//			api.executeAction(new EnableBuzzer());		
//			api.executeAction(new SetPowerControl("250"));
//			api.executeAction(new EnableContinueMode());
//
//			api.executeAction(new ResquestStatusBuzzer());
//			System.out.println(api.getTranslatedResponse());
//
//			api.executeAction(new RequestStatusAntenna());
//			System.out.println(api.getTranslatedResponse());
//
//			api.executeAction(new RequestStatusPowerAntenna());
//			System.out.println(api.getTranslatedResponse());
//
//			api.executeAction(new RequestStatusMode());
//			System.out.println(api.getTranslatedResponse());
//
//			api.executeAction(new ReaderTags());
			
			byte[] b = InetAddress.getByName("localhost").getAddress();
			System.out.println(b[0] + "." + b[1] + "." + b[2] + "." + b[3]);
			
			
			
			
			while(true) {
				try {
//					Socket cliente = new Socket("192.168.0.16",12345);
//					ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
//					saida.writeObject("Teste");
//					saida.close();
					//api.captureTagsObject();
					//if(api.hasNewTag()){
						//System.out.println("Chegou aqui");
					System.out.println("Passou um carro?");
					int entrada = sc.nextInt();
					if(entrada == 1 || entrada == 2){
						String valor = "teste";
						if(entrada == 2){
							System.out.println("Tag Number?");
							valor = sc.nextLine();
						}
						RFIDData data = new RFIDData(valor, true);
						//System.out.println(api.getTagUniqueJsonRepresentation());
						//System.out.println("Enviando: "+data.serialize());
						appDevice.getSender().addMsg(data.serialize());
						//Message msg = new Message(data.serialize());
						//send(client,msg);
					}
						
						//System.out.println("enviou");
					//}
//						cliente.close();
					Thread.sleep(3000);	
				} catch (Exception e) {
					e.printStackTrace();
					//api.executeAction(new ReaderTagsReset());
				}
				
			}
			//System.out.println("saiu do while");
		} catch (UnknownHostException e) {
			System.err.println("Host not found: " + OperationUtil.getIpReaderNesslab());
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Don't possible the conection: "+ OperationUtil.getIpReaderNesslab());
			System.exit(1);
		}

	}
	
	private class RFIDData{
		private String tagNumber;
		private boolean detectou;
		private String sensor;
		
		public RFIDData(String tagNumber, boolean detectou){
			this.tagNumber = tagNumber;
			this.detectou = detectou;
			this.sensor = DEVICE_KEY;
		}

		public String serialize(){
			Gson gson = new Gson();
			return gson.toJson(this);
		}

	}

	public void sensing() {
		// TODO Auto-generated method stub
		
	}
}
