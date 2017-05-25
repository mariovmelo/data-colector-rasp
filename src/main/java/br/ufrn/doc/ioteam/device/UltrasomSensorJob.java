package br.ufrn.doc.ioteam.device;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

import br.ufrn.doc.ioteam.estacionamento.Vaga;
import br.ufrn.doc.ioteam.jobs.AppDevice;
import br.ufrn.doc.ioteam.util.Connection;
import br.ufrn.doc.ioteam.util.DistanceMonitor;

public class UltrasomSensorJob implements SensorJob, Runnable{

	public final static String DEVICE_KEY = "ULTRA_SOM_VAGA";

	private AppDevice appDevice;

	public UltrasomSensorJob(AppDevice appDevice) {
		this.appDevice = appDevice;
	}

	public void run() {

		DistanceMonitor monitor;
		Map<Integer, Vaga> mapaVagas = Connection.getInstance().getMapaVagas();
		float distancias[] = new float[12];


		while(true) {
			try {
				for(Entry<Integer, Vaga> entry : mapaVagas.entrySet()){

					monitor = new DistanceMonitor( 1, 7 );
					distancias[entry.getKey()] = monitor.monitorar();

					if(distancias[entry.getKey()] <= 5.0){
						PresenceData data = new PresenceData(entry.getKey(), true);
						appDevice.getSender().addMsg(data.serialize());
					}
				}
				Thread.sleep(5000);	
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private class PresenceData{
		private int numeroVaga;
		private boolean detectou;
		private String sensor;

		public PresenceData(int numeroVaga, boolean detectou){
			this.numeroVaga = numeroVaga;
			this.detectou = detectou;
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
