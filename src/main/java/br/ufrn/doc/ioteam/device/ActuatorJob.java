package br.ufrn.doc.ioteam.device;

import javax.json.JsonObject;

public interface ActuatorJob{

	public void acting(JsonObject msg);
	
}
