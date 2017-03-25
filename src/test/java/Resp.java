import javax.ws.rs.core.Response;

import com.google.gson.Gson;
/**
 * 
 * I added this code from my github. 
 *  
 */

public class Resp {
	private Response r;
	
	public Resp(Response r) {
		this.r = r;
	}
	
	public int getStatus() {
		return r.getStatus();
	}
	public String getBody() {
		return (String)r.getEntity();
	}
	
	
	public Response getResponseObject() {
		return r;
	}
	
	
}
