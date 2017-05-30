package br.ufrn.doc.ioteam.jobs;

import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;

import api.reader.nesslab.facade.ApiReaderNesslab;
import api.reader.nesslab.interfaces.ApiReaderFacade;
import br.ufrn.doc.ioteam.device.LEDVagaSensorJob;
import br.ufrn.doc.ioteam.device.RFIDSensorJobSystemIn;

/**
 * Hello world!
 *
 */
public class JobsHandler 
{
	public static void main(String[] args) {
		try {
//			for(int i = 0; i < 10000;i+=1000){
//				System.out.println("Esperando 10s para iniciar.... " +i);
//				Thread.sleep(1000);
//			}

			ApiReaderFacade api = new ApiReaderNesslab("192.168.1.231");
//
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

			
			AppDevice appDevice = new AppDevice();
			appDevice.initiate();
			
			
			//rfidSensorJob.initiate();
			//rfidSensorJob.sensing();
			
//			LEDVagaSensorJob ledVagaSensorJob = new LEDVagaSensorJob("ledVaga", "aY1gHjwtYG3UkbMIp0oDGQ==");
//			ledVagaSensorJob.setProtocol(IotHubClientProtocol.MQTT);
//			ledVagaSensorJob.initiate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
