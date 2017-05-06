package br.ufrn.doc.ioteam.util;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;

public class Connection {
	//"HostName=EstacionamentoIoT.azure-devices.net;DeviceId=myFirstJavaDevice;SharedAccessKey=Ph/nSASaqUOBjbWu5i0rjQ=="
	public static final String AZURE_CONECTION = "HostName=EstacionamentoIoT.azure-devices.net;DeviceId=%s;SharedAccessKey=%s";
	
	private static Connection instance;
	
	private Map<String, DeviceClient> mapaClientes;
	
	private Connection(){
		mapaClientes = new HashMap<String, DeviceClient>();
	}
	
	public static Connection getInstance(){
		if(instance == null)
			instance = new Connection();
		
		return instance;
	}
	
	public void putConnection(String deviceId, String accessKey){
		
		try{
			if(mapaClientes.get(deviceId) == null){
				 DeviceClient client = new DeviceClient(String.format(AZURE_CONECTION,deviceId,accessKey), IotHubClientProtocol.HTTPS);
				
				//Registrar o callback
//				MessageCallback callback = new AppMessageCallback();
//				client.setMessageCallback(callback, null);
				client.open();
				mapaClientes.put(deviceId, client);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DeviceClient getClient(String deviceId, String accessKey){
		putConnection(deviceId,accessKey);
		return mapaClientes.get(deviceId);
	}
}
