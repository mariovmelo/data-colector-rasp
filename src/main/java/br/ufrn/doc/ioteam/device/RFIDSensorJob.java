package br.ufrn.doc.ioteam.device;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import api.reader.nesslab.commands.EnableBuzzer;
import api.reader.nesslab.commands.EnableContinueMode;
import api.reader.nesslab.commands.ReaderTags;
import api.reader.nesslab.commands.ReaderTagsReset;
import api.reader.nesslab.commands.RequestStatusAntenna;
import api.reader.nesslab.commands.RequestStatusMode;
import api.reader.nesslab.commands.RequestStatusPowerAntenna;
import api.reader.nesslab.commands.ResquestStatusBuzzer;
import api.reader.nesslab.commands.SetPowerControl;
import api.reader.nesslab.facade.ApiReaderNesslab;
import api.reader.nesslab.interfaces.ApiReaderFacade;
import api.reader.nesslab.utils.OperationUtil;
import br.ufrn.doc.ioteam.jobs.AppDevice;

public class RFIDSensorJob implements Runnable{

	public final static String DEVICE_KEY = "ANTENA_RFID_ENTRADA";
	
	private AppDevice appDevice;
	
	public RFIDSensorJob(AppDevice appDevice) {
		this.appDevice = appDevice;
	}

	public void run() {
		try {
			ApiReaderFacade api = new ApiReaderNesslab("192.168.1.231");


			api.executeAction(new EnableBuzzer());		
			api.executeAction(new SetPowerControl("250"));
			api.executeAction(new EnableContinueMode());

			api.executeAction(new ResquestStatusBuzzer());
			System.out.println(api.getTranslatedResponse());

			api.executeAction(new RequestStatusAntenna());
			System.out.println(api.getTranslatedResponse());

			api.executeAction(new RequestStatusPowerAntenna());
			System.out.println(api.getTranslatedResponse());

			api.executeAction(new RequestStatusMode());
			System.out.println(api.getTranslatedResponse());

			api.executeAction(new ReaderTags());

			while(true) {
				try {
					api.captureTagsObject();
					if(api.hasNewTag()){
						System.out.println("Passou um carro?");
						String tag = api.getTagUniqueJsonRepresentation();

						JsonParser parser = new JsonParser();
						JsonObject o = parser.parse(tag).getAsJsonObject();
						if(o != null && o.get("tag") != null){
							System.out.println(o.get("tag").getAsString());
							//System.out.println("Enviando: "+data.serialize());
							RFIDData data =  new RFIDData(o.get("tag").getAsString(), true);
							appDevice.getSender().addMsg(data.serialize());
						}

					}

					//}
					Thread.sleep(2000);	
				} catch (Exception e) {
					e.printStackTrace();
					api.executeAction(new ReaderTagsReset());
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
