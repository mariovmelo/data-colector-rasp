package br.ufrn.doc.ioteam.device;

import java.io.IOException;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.microsoft.azure.sdk.iot.device.Message;

import api.reader.nesslab.commands.EnableBuzzer;
import api.reader.nesslab.commands.EnableContinueMode;
import api.reader.nesslab.commands.ReaderTags;
import api.reader.nesslab.commands.ReaderTagsReset;
import api.reader.nesslab.commands.RequestStatusAntenna;
import api.reader.nesslab.commands.RequestStatusMode;
import api.reader.nesslab.commands.RequestStatusPowerAntenna;
import api.reader.nesslab.commands.ResquestStatusBuzzer;
import api.reader.nesslab.commands.SetPowerControl;
import api.reader.nesslab.exceptions.SessionFullException;
import api.reader.nesslab.facade.ApiReaderNesslab;
import api.reader.nesslab.interfaces.ApiReaderFacade;
import api.reader.nesslab.utils.OperationUtil;
import api.reader.nesslab.exceptions.SessionFullException;

public class RFIDSensorJob extends SensorJob{


	public RFIDSensorJob(String deviceId,String accessKey) {
		super(deviceId,accessKey);
	}

	@Override
	public void acting(String msg) {

	}

	@Override
	public void sensing() {

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
			
			while(api.hasResponse()) {
				try {
					api.getTagStringRepresentation();
					// só entra aqui quando detecta
					// RFIDData data = new RFIDData(tagNumber, detectou);
					// Message msg = new Message(data.serialize());
					// send(msg);
					
				} catch (SessionFullException e) {
					api.executeAction(new ReaderTagsReset());
				}
			}
			
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

		public RFIDData(String tagNumber, boolean detectou){
			this.tagNumber = tagNumber;
			this.detectou = detectou;
		}

		public String serialize(){
			Gson gson = new Gson();
			return gson.toJson(this);
		}

	}

}
