package br.ufrn.doc.ioteam.jobs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import api.reader.nesslab.commands.EnableBuzzer;
import api.reader.nesslab.commands.EnableContinueMode;
import api.reader.nesslab.commands.ReaderTags;
import api.reader.nesslab.commands.RequestStatusAntenna;
import api.reader.nesslab.commands.RequestStatusMode;
import api.reader.nesslab.commands.RequestStatusPowerAntenna;
import api.reader.nesslab.commands.ResquestStatusBuzzer;
import api.reader.nesslab.commands.SetPowerControl;
import api.reader.nesslab.facade.ApiReaderNesslab;
import api.reader.nesslab.interfaces.ApiReaderFacade;
import br.ufrn.doc.ioteam.device.RFIDSensorJob;

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

			

			RFIDSensorJob rfidSensorJob = new RFIDSensorJob("myFirstJavaDevice", "Ph/nSASaqUOBjbWu5i0rjQ==");
			rfidSensorJob.initiate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
