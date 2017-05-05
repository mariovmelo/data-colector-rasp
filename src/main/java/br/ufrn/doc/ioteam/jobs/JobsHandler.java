package br.ufrn.doc.ioteam.jobs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import br.ufrn.doc.ioteam.device.RFIDSensorJob;

/**
 * Hello world!
 *
 */
public class JobsHandler 
{
    public static void main(String[] args) {
		try {
			ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(50);
			
			scheduledExecutorService.scheduleAtFixedRate(new RFIDSensorJob("rfidportalentrada", "m5XkIa6DorAnL05MGuZWeA=="),
					0,1, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
