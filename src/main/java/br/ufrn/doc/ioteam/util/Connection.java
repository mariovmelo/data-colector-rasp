package br.ufrn.doc.ioteam.util;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.MessageCallback;

import br.ufrn.doc.ioteam.estacionamento.Vaga;

public class Connection {
	//"HostName=EstacionamentoIoT.azure-devices.net;DeviceId=myFirstJavaDevice;SharedAccessKey=Ph/nSASaqUOBjbWu5i0rjQ=="
	public static final String AZURE_CONECTION = "HostName=ioteamhub.azure-devices.net;DeviceId=%s;SharedAccessKey=%s";
	
	private static Connection instance;
	
	private Map<String, DeviceClient> mapaClientes;
	
	private Map<Integer, Vaga> mapaVagas;
	
	private Connection(){
		mapaClientes = new HashMap<String, DeviceClient>();
		mapaVagas = new HashMap<Integer,Vaga>();
	}
	
	public static Connection getInstance(){
		if(instance == null)
			instance = new Connection();
		
		return instance;
	}
	
	public Map<Integer, Vaga> getMapaVagas(){
		return mapaVagas;
	}
}
