package br.ufrn.doc.ioteam.device;

import javax.json.JsonObject;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

//ledVaga
//aY1gHjwtYG3UkbMIp0oDGQ==
public class LEDVagaSensorJob implements ActuatorJob{

	public final static String DEVICE_KEY = "ledVaga";

	final GpioController gpio = GpioFactory.getInstance();
	
	// provision gpio pin #01 as an output pin and turn on
	final GpioPinDigitalOutput pinLedReserva;
	final GpioPinDigitalOutput pinLedPresenca;
	
	public LEDVagaSensorJob() {
		pinLedReserva = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "ledVagaReserva", PinState.LOW);
		// set shutdown state for this pin
		pinLedReserva.setShutdownOptions(true, PinState.LOW);
		pinLedPresenca = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "ledVagaPresenca", PinState.LOW);
		// set shutdown state for this pin
		pinLedPresenca.setShutdownOptions(true, PinState.LOW);
	}

	public void acting(JsonObject msg) {
		System.out.println("Atuando nos LEDs....");
		
		if(msg.getInt("status") == 1){
			System.out.println("Vaga "+msg.getInt("numero")+" reservada ligar o led");
			pinLedReserva.high();
			pinLedPresenca.low();
		}else if(msg.getInt("status") == 2){
			System.out.println("Vaga "+msg.getInt("numero")+" ocupada ligar o led");
			pinLedPresenca.high();
			pinLedReserva.low();
		}else if(msg.getInt("status") == 0){
			pinLedPresenca.low();
			pinLedReserva.low();
		}
		
	}

}
