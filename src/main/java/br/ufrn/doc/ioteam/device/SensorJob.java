package br.ufrn.doc.ioteam.device;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubMessageResult;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.MessageCallback;

import br.ufrn.doc.ioteam.util.Connection;

public abstract class SensorJob implements Runnable{

	private String deviceId;
	private String accessKey;
	
	protected DeviceClient client;
	
	public SensorJob(String deviceId,String accessKey){
		this.deviceId = deviceId;
		this.accessKey = accessKey;
		this.client = Connection.getInstance().getClient(deviceId,accessKey);
		client.setMessageCallback(new MessageCallback() {
			public IotHubMessageResult execute(Message message, Object callbackContext) {
				String msg = new String(message.getBytes(), Message.DEFAULT_IOTHUB_MESSAGE_CHARSET);
				
				System.out.println("Received message from hub: "
						+ msg);
				acting(msg);
				return IotHubMessageResult.COMPLETE;
			}
		}, null);
	}
	
	public void run() {
		
		sensing();
		
	}
	
	public void send(Message msg){
		
		Object lockobj = new Object();

		client.sendEventAsync(msg, new IotHubEventCallback(){
			public void execute(IotHubStatusCode status, Object context) {
				System.out.println("Message status: " + status.name());
				if (context != null) {
					synchronized (context) {
						context.notify();
					}
				}
			}
		}, lockobj);
	}
	
	public abstract void acting(String msg);
	
	public abstract void sensing();
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

}
