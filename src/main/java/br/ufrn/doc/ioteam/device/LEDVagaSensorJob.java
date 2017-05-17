package br.ufrn.doc.ioteam.device;

import javax.json.JsonObject;

//ledVaga
//aY1gHjwtYG3UkbMIp0oDGQ==
public class LEDVagaSensorJob implements ActuatorJob{
	
	public final static String DEVICE_KEY = "ledVaga";
	
	public LEDVagaSensorJob() {
	}

	@Override
	public void acting(JsonObject msg) {
		System.out.println("\n\n\n Lendo msg para ligar o led ->"+  msg +"\n\n\n");
		
	}

}
