import br.ufrn.doc.ioteam.device.RFIDSensorJob;

public class Main {

	public static void main(String[] args) {
		RFIDSensorJob sensor = new RFIDSensorJob("Sei la", "Tb nao sei");
		sensor.sensing();
	}
	
}
