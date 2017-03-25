import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

/**
 * 
 * I added this code from my github. 
 *I 
 *  
 */


public class HttpUtil {

	
	public HttpUtil() {
		
	}
	
	private boolean debugMode = false;
	
	private ArrayList<Header> headers = new ArrayList<Header>();
	
	public void setDebugMode(boolean isOn) {
		debugMode = isOn;
	}
	
	public void AddHeader(String name, String value) {
		BasicHeader h = new BasicHeader(name, value);
		headers.add(h);
	}
	public void AddHeader(BasicHeader header) {
		headers.add(header);
	}
	
	

	
	public Resp Get(String url) {
		Resp r = null;
		r = HttpUtil.HttpGet(url, headers, debugMode);	
		return r;
	}
	
	public Resp Post(String url, String data) {
		Resp r = null;
		r = HttpUtil.HttpPost(url, data, headers, debugMode);
		return r;
	}
	
	public Resp Put(String url, String data) {
		Resp r = null;
		r = HttpUtil.HttpPut(url, data, headers, debugMode);
		return r;
	}
	
	public Resp Delete(String url) {
		Resp r = null;
		r = HttpUtil.HttpDelete(url, headers, debugMode);
		return r;
	}
	
	
	/**
	 * Http Get method.
	 * @param urlToRead - A string in the form of a url with a query.
	 * @return - Resp object with status code and body.
	 */
	public static Resp HttpGet(String urlToRead) {
		return HttpGet(urlToRead, new ArrayList<Header>(), false);
	}
	public static Resp HttpGet(String urlToRead, ArrayList<Header> heads) {
		return HttpGet(urlToRead, heads, false);
	}
	public static Resp HttpGet(String urlToRead, ArrayList<Header> heads, boolean test) {	
		
		String returningResponse ="";

		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 
		
		try {
		    
		    HttpGet request = new HttpGet(urlToRead);
		    
		    for (Header h :heads) {
		    	request.addHeader(h);
		    }
		    
		    long t1= System.nanoTime();
		   
		    CloseableHttpResponse cs = httpClient.execute(request);
		    
		    if (test) {
		    	System.out.println(cs.getStatusLine().getStatusCode());
		    	System.out.println("Execution time: " + ((System.nanoTime() - t1) * 1e-6) + " milliseconds");
		    }
		    
			HttpEntity response = cs.getEntity();

		    InputStream content = null;
		    if (response != null)
		    	content = response.getContent();
		    returningResponse = convertStreamToString(content);
		    
		    return new Resp(Response.status(cs.getStatusLine().getStatusCode()).entity(returningResponse).build());
		    // handle response here...
		}catch (Exception ex) {
		    // handle exception here
			ex.printStackTrace();
			return new Resp(Response.status(500).entity(ex.getMessage()).build());
			//System.out.println(returningResponse);
		}
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * @param url
	 * @param myJsonObject
	 * @return
	 */
	public static Resp HttpPut(String url, String myJsonObject) {
		return HttpPut(url, myJsonObject, new ArrayList<Header>(), false);
	}
	public static Resp HttpPut(String url, String myJsonObject, ArrayList<Header> heads) {
		return HttpPut(url, myJsonObject, heads, false);
	}
	public static Resp HttpPut(String url, String myJsonObject, ArrayList<Header> heads, boolean test) {
		
		String returningResponse ="";

		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

		try {
		    
		    HttpPut request = new HttpPut(url);
		    
		    for (Header h :heads) {
		    	request.addHeader(h);
		    }
		    
		    StringEntity params =new StringEntity(myJsonObject);
		    request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		    request.setEntity(params);
		    
		    long t1= System.nanoTime();
		    CloseableHttpResponse cs = httpClient.execute(request);

		    if (test) {
		    	System.out.println(cs.getStatusLine().getStatusCode());
		    	System.out.println("Execution time: " + ((System.nanoTime() - t1) * 1e-6) + " milliseconds");
		    }
		    
		    HttpEntity response = cs.getEntity();

		    InputStream content = null;
		    if (response != null)
		    	content = response.getContent();
		    returningResponse = convertStreamToString(content);
		    
		    return new Resp(Response.status(cs.getStatusLine().getStatusCode()).entity(returningResponse).build());
		    // handle response here...
		}catch (Exception ex) {
		    // handle exception here
			ex.printStackTrace();
			return new Resp(Response.status(500).entity(ex.getMessage()).build());
			//System.out.println(returningResponse);
		}
	}
	
	
	
	
	
	
   
	
	/**
	 * CLASSIC HTTP POST METHOD.
	 * @param url
	 * @param myJsonObject - doesn't have to be json, but we usually use Json so it's named that.
	 * @return //the http response from the post. String
	 */
	public static Resp HttpPost(String url, String myJsonObject) {
		return HttpPost(url, myJsonObject, new ArrayList<Header>(), false);
	}
	public static Resp HttpPost(String url, String myJsonObject, ArrayList<Header> heads) {
		return HttpPost(url, myJsonObject, heads, false);
	}
	public static Resp HttpPost(String url, String myJsonObject, ArrayList<Header> heads, boolean test) {
		
		String returningResponse ="";

		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

		try {
		    
		    HttpPost request = new HttpPost(url);
		    
		    for (Header h :heads) {
		    	request.addHeader(h);
		    }
		    
		    StringEntity params =new StringEntity(myJsonObject);
		    request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		    request.setEntity(params);
		    
		    long t1= System.nanoTime();
		    CloseableHttpResponse cs = httpClient.execute(request);

		    if (test) {
		    	System.out.println(cs.getStatusLine().getStatusCode());
		    	System.out.println("Execution time: " + ((System.nanoTime() - t1) * 1e-6) + " milliseconds");
		    }
		    
		    HttpEntity response = cs.getEntity();
		    
		    InputStream content = null;
		    if (response != null)
		    	content = response.getContent();
		    returningResponse = convertStreamToString(content);
		    
		    return new Resp(Response.status(cs.getStatusLine().getStatusCode()).entity(returningResponse).build());
		    // handle response here...
		}catch (Exception ex) {
		    // handle exception here
			ex.printStackTrace();
			return new Resp(Response.status(500).entity(ex.getMessage()).build());
			//System.out.println(returningResponse);
		}
	}
	
	
	
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static Resp HttpDelete(String url) {
		return HttpDelete(url, new ArrayList<Header>(), false);
	}
	public static Resp HttpDelete(String url, ArrayList<Header> heads) {
		return HttpDelete(url, heads, false);
	}
	public static Resp HttpDelete(String url, ArrayList<Header> heads, boolean test) {
		
		String returningResponse ="";

		CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

		try {
		    
		    HttpDelete request = new HttpDelete(url);
		    
		    for (Header h :heads) {
		    	request.addHeader(h);
		    }
		    
		    long t1= System.nanoTime();
		    CloseableHttpResponse cs = httpClient.execute(request);

		    if (test) {
		    	System.out.println(cs.getStatusLine().getStatusCode());
		    	System.out.println("Execution time: " + ((System.nanoTime() - t1) * 1e-6) + " milliseconds");
		    }
		    
		    HttpEntity response = cs.getEntity();
		    
		    InputStream content = null;
		    if (response != null)
		    	content = response.getContent();
		    returningResponse = convertStreamToString(content);
		    
		    return new Resp(Response.status(cs.getStatusLine().getStatusCode()).entity(returningResponse).build());
		    // handle response here...
		}catch (Exception ex) {
		    // handle exception here
			ex.printStackTrace();
			return new Resp(Response.status(500).entity(ex.getMessage()).build());
			//System.out.println(returningResponse);
		}
	}
	
	
	
	
	


	static String convertStreamToString(java.io.InputStream is) {
		if (is == null) return "No Response!";
	    Scanner s = new Scanner(is);
	    s.useDelimiter("\\A");
	    String r = s.hasNext() ? s.next() : "";
	    s.close();
	    try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return r;
	}
	
	
	
	
}
