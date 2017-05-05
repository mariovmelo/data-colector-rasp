package br.ufrn.doc.ioteam.device;

import com.google.gson.Gson;
import com.microsoft.azure.sdk.iot.device.Message;

public class RFIDSensorJob extends SensorJob{


	public RFIDSensorJob(String deviceId,String accessKey) {
		super(deviceId,accessKey);
	}

	@Override
	public void acting(String msg) {

	}

	@Override
	public void sensing() {

		//Aqui você vai usar a api para detectar se leu ou não algo da antena.
		boolean detectou = true;

		if(detectou){
			//se detectou você vai popular os dados com o valor da tag e um booleano que detectou.
			RFIDData data  = new RFIDData("tag123", true);
			
			Message msg = new Message(data.serialize());
			//msg.setProperty("carro", "passou");

			send(msg);
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
