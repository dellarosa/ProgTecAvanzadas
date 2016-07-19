package tap.exampleapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import tap.generated.code.Pedido;

public class HttpURLConnectionExample 
{

	private static final String USER_AGENT = "Mozilla/5.0";

	private static final String GET_URL = "http://localhost:8080/RESTServer/rest/service/json";
	//private static final String GET_URL = "http://localhost:8080/RESTServer/rest/hello/json";
	
	public static final String POST_URL = "http://localhost:8080/RESTServer/rest/service/psj";;
	//private static final String POST_URL = "http://localhost:8080/RESTServer/rest/hello/psj";

	//private static final String POST_PARAMS = "userName=Pankaj";
	//private static final String POST_PARAMS = "{\"name\" : \"pepe\",\"cantidad\" : \"2\" }";
	//private static final String POST_PARAMS = "{\"name\" : \"pepe\"}";
	//private static final String POST_PARAMS = "{\"cliente\":\"pepe\"}";
	public static final String POST_PARAMS = "{\"cantidad\": \"20\",\"cliente\": \"fab\",\"id\": \"0\"}";

	
	public static void main(String[] args) throws IOException {

		sendGET();
		System.out.println("GET DONE");
		sendPOST(POST_PARAMS);
		System.out.println("POST DONE");
	}

	public static String sendGET() throws IOException {
		StringBuffer response=null;
		try
		{
			URL obj = new URL(GET_URL);
		
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				 response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
	
				// print result
				System.out.println(response.toString());
			} else {
				System.out.println("GET request not worked");
				return "FALLO GET";
			}
		}catch(Exception e)
		{
			System.out.println("GET EX: "+e.toString());
			return "FALLO GET EXP";
		}
		return response.toString();
	}

	public static String sendPOST(String Pedido) throws IOException {
		StringBuffer response =null;
		try
		{
			URL obj = new URL(POST_URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);			
			con.setRequestProperty("Content-Type", "application/json; charset=utf8");
			//con.setRequestProperty("Accept:","application/json" );
			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			
			
			System.out.println("POST PARAMS: " + POST_PARAMS);
			//os.write(POST_PARAMS.getBytes());
			os.write(Pedido.getBytes());
			os.flush();
			os.close();
			// For POST only - END
	
			int responseCode = con.getResponseCode();
			System.out.println("POST Response Code :: " + responseCode);
			
			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				 response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
	
				// print result
				System.out.println(response.toString());
			} else {
				System.out.println("POST request not worked");
				return "FALLO POST";
			}
					
		}catch(Exception e)
		{
			System.out.println("POST EX: "+e.toString());
			return null;
		}
		return response.toString();
	}

}


