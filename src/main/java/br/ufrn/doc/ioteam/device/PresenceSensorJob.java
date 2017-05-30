package br.ufrn.doc.ioteam.device;

import com.google.gson.Gson;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

import br.ufrn.doc.ioteam.jobs.AppDevice;

public class PresenceSensorJob implements SensorJob, Runnable{

	public final static String DEVICE_KEY = "PRESENCE_VAGA";

	private AppDevice appDevice;
	
	// create gpio controller
    final GpioController gpio = GpioFactory.getInstance();

    final GpioPinDigitalInput presencePin1; // pin 11
    final GpioPinDigitalInput presencePin2; // pin 12
    final GpioPinDigitalInput presencePin3; // pin 13
    
	public PresenceSensorJob(AppDevice appDevice) {
		this.appDevice = appDevice;
		presencePin1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
		presencePin2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
		presencePin3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
	}

	public void run() {
		
		// low == presence , high == no presence
		
		boolean lastState1 = presencePin1.getState().isHigh(), 
				lastState2 = presencePin2.getState().isHigh(), 
				lastState3 = presencePin3.getState().isHigh();
		
		boolean currentState1, currentState2, currentState3;
		
		while(true){
            
            try {
            	currentState1 =  presencePin1.getState().isHigh();
            	currentState2 =  presencePin2.getState().isHigh();
            	currentState3 =  presencePin3.getState().isHigh();
            	
            	
            	if (currentState1 == false) {
            		System.out.println("Presenca Vaga 1");
            		if (lastState1 != currentState1) {
            			PresenceData data = new PresenceData(1, 2);
            			appDevice.getSender().addMsg(data.serialize());
            		}
            	} else {
            		if (lastState1 != currentState1) {
            			PresenceData data = new PresenceData(1, 0);
            			appDevice.getSender().addMsg(data.serialize());
            		}
            	}
            	
            	if (currentState2 == false) {
            		System.out.println("Presenca Vaga 2");
            		if (lastState2 != currentState2) {
            			PresenceData data = new PresenceData(2, 2);
            			appDevice.getSender().addMsg(data.serialize());
            		}
            	} else {
            		if (lastState2 != currentState2) {
            			PresenceData data = new PresenceData(2, 0);
            			appDevice.getSender().addMsg(data.serialize());
            		}
            	}
            	
            	if (currentState3 == false) {
            		System.out.println("Presenca Vaga 3");
            		if (lastState3 != currentState3) {
            			PresenceData data = new PresenceData(3, 2);
            			appDevice.getSender().addMsg(data.serialize());
            		}
            	} else {
            		if (lastState3 != currentState3) {
            			PresenceData data = new PresenceData(3, 0);
            			appDevice.getSender().addMsg(data.serialize());
            		}
            	}
            	
            	lastState1 = currentState1;
            	lastState2 = currentState2;
            	lastState3 = currentState3;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }

	}

	private class PresenceData{
		private int numeroVaga;
		private int status;
		private String sensor;

		public PresenceData(int numeroVaga, int detectou){
			this.numeroVaga = numeroVaga;
			this.status = detectou;
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
