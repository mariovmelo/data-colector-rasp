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
	

	final GpioPinDigitalOutput pinLedReserva1; // pin 40
	final GpioPinDigitalOutput pinLedPresenca1; // pin 38
	final GpioPinDigitalOutput pinLedReserva2; // pin 36
	final GpioPinDigitalOutput pinLedPresenca2; // pin 32
	final GpioPinDigitalOutput pinLedReserva3; // pin 37
	final GpioPinDigitalOutput pinLedPresenca3; // pin 35
	
	public LEDVagaSensorJob() {
		pinLedReserva1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "ledVagaReserva1", PinState.LOW);
		pinLedReserva1.setShutdownOptions(true, PinState.LOW);
		pinLedPresenca1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "ledVagaPresenca1", PinState.LOW);
		pinLedPresenca1.setShutdownOptions(true, PinState.LOW);
		
		pinLedReserva2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "ledVagaReserva2", PinState.LOW);
		pinLedReserva2.setShutdownOptions(true, PinState.LOW);
		pinLedPresenca2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "ledVagaPresenca2", PinState.LOW);
		pinLedPresenca2.setShutdownOptions(true, PinState.LOW);
		
		pinLedReserva3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "ledVagaReserva3", PinState.LOW);
		pinLedReserva3.setShutdownOptions(true, PinState.LOW);
		pinLedPresenca3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24, "ledVagaPresenca3", PinState.LOW);
		pinLedPresenca3.setShutdownOptions(true, PinState.LOW);
		
	}

	public void acting(JsonObject msg) {
		System.out.println("Atuando nos LEDs....");
		
		
		if (msg.getInt("numero") == 1) {
			if(msg.getInt("status") == 1){
				System.out.println("Vaga "+msg.getInt("numero")+" Reservada ligar o led");
				pinLedReserva1.high();
				pinLedPresenca1.low();
			}else if(msg.getInt("status") == 2){
				System.out.println("Vaga "+msg.getInt("numero")+" ocupada ligar o led");
				pinLedPresenca1.high();
				pinLedReserva1.low();
			}else if(msg.getInt("status") == 0){
				pinLedPresenca1.low();
				pinLedReserva1.low();
			}
		}
		
		if (msg.getInt("numero") == 2) {
			if(msg.getInt("status") == 1){
				System.out.println("Vaga "+msg.getInt("numero")+" Reservada ligar o led");
				pinLedReserva2.high();
				pinLedPresenca2.low();
			}else if(msg.getInt("status") == 2){
				System.out.println("Vaga "+msg.getInt("numero")+" ocupada ligar o led");
				pinLedPresenca2.high();
				pinLedReserva2.low();
			}else if(msg.getInt("status") == 0){
				pinLedPresenca2.low();
				pinLedReserva2.low();
			}
		}
		
		if (msg.getInt("numero") == 3) {
			if(msg.getInt("status") == 1){
				System.out.println("Vaga "+msg.getInt("numero")+" Reservada ligar o led");
				pinLedReserva3.high();
				pinLedPresenca3.low();
			}else if(msg.getInt("status") == 2){
				System.out.println("Vaga "+msg.getInt("numero")+" ocupada ligar o led");
				pinLedPresenca3.high();
				pinLedReserva3.low();
			}else if(msg.getInt("status") == 0){
				pinLedPresenca3.low();
				pinLedReserva3.low();
			}
		}
		
		
		
		
	}

}
