package ratna.genie1.user.genie;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class CustomHttpClient {
	
	public static final int HTTP_TIMEOUT = 60 * 1000; 

	
	private static HttpClient mHttpClient;
	static InputStream is = null;

	
	private static HttpClient getHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new DefaultHttpClient();
			final HttpParams params = mHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
		}
		return mHttpClient;
	}

	
	public static void setTimeOut(){
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
		HttpConnectionParams.setSoTimeout(httpParameters, 10000);
	}

	public static String executeHttpPost(String url, ArrayList<NameValuePair> postParameters) throws Exception {
		BufferedReader in = null;
		try {
			  
			setTimeOut();
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(url);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();

			String result = sb.toString();
			return result;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static String executeHttpPost2(String url, ArrayList<NameValuePair> postParameters) throws Exception {
		  try {
		     
		   setTimeOut();
		   HttpClient client = getHttpClient();
		   HttpPost request = new HttpPost(url);
		   UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
		   request.setEntity(formEntity);
		   HttpResponse response = client.execute(request);
		   
		   StatusLine status = response.getStatusLine();
		   int statusCode = status.getStatusCode();
		   
		   if( statusCode == 200 || statusCode == 204 ) {
		    return "Failed";
		   }else{
		    return "Success";
		   }
		  }catch(Exception e){
		   return "Failed";
		  }
		 }

	public static String executeHttpGet(String url) throws Exception {
		BufferedReader br = null;
		try {
			HttpClient client = getHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = br.readLine()) != null) {
				sb.append(line + NL);
			}
			br.close();

			String result = sb.toString();
			return result;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static String executeHttpPut(String url, ArrayList<NameValuePair> postParameters) throws Exception {
		  try{
		   HttpClient client = getHttpClient();
		   HttpPut httpPut = new HttpPut(new URI(url));
		   httpPut.setEntity(new UrlEncodedFormEntity(postParameters));

		   HttpResponse httpResponse = client.execute(httpPut);
		   
		   StatusLine response = httpResponse.getStatusLine();
		   int statusCode = response.getStatusCode();

		   if( statusCode < 200 || statusCode >= 300 ) {
		    return "Failed";
		   }else{
		    return "Success";
		   }
		  }catch(Exception e){
		   return "Failed";
		  }
		 }
	public static String executeHttpPut2(String url) throws Exception {
		  try{
			  HttpClient client = getHttpClient();
				HttpPut request = new HttpPut();
				request.setURI(new URI(url));
				HttpResponse httpResponse = client.execute(request);
				
				 StatusLine response = httpResponse.getStatusLine();
				   int statusCode = response.getStatusCode();

				   if( statusCode < 200 || statusCode >= 300 ) {
				    return "Failed";
				   }else{
				    return "Success";
				   }
				  }catch(Exception e){
				   return "Failed";
				  }
				 }
  }
