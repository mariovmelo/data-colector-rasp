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

    GpioPinDigitalInput presencePin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
    
	public PresenceSensorJob(AppDevice appDevice) {
		this.appDevice = appDevice;
	}

	public void run() {
		
		int contadorPresenca = 0;
		
		boolean lastState = false;
		while(true){
            
            try {
            	boolean currentState = presencePin.getState().isHigh();
            	
            	if(presencePin.getState().isHigh()){
    				System.out.println("Presenca");
            		contadorPresenca = 30;
    				if(lastState != currentState){
    					PresenceData data = new PresenceData(1, 2);
        				appDevice.getSender().addMsg(data.serialize());
    				}
    				
    			}else{
    				contadorPresenca--;
    				if(contadorPresenca == 0){
						PresenceData data = new PresenceData(1, 0);
    					appDevice.getSender().addMsg(data.serialize());
    					
    				}
    			}
            	lastState = currentState;
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
