package servlet.filters;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Utils {
	
	/**
	 * Funtion to consume Restful Web Services that generate the JWT
	 * @param user
	 * @param password
	 * @return JWT
	 */
	static public String getTokenJWT(String user, String password)
	{
		
		URL url;
		BufferedReader br;
		StringBuilder builder;
		HttpURLConnection conn;
		
		String Uri = "http://localhost:8080/ontogov/r/webservicesjwt/getJWT_Token/"
				+ user
				+ "/"
				+ password;
		
		try {
			
			url = new URL(Uri);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "  + conn.getResponseCode());
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		    builder = new StringBuilder();
			
			for (String line = null; (line = br.readLine()) != null;) {
			    builder.append(line).append("\n");
			}
			
			JSONObject json = new JSONObject(builder.toString());
			JSONObject jsonf = (JSONObject) json.get("map");
			
			
			conn.disconnect();
		
			return jsonf.getString("JWT");
		
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	static public boolean validateToken(String token)
	{

		String Uri = "http://localhost:8080/ontogov/r/webservicesjwt/validateJWT_Token";
		String POST_PARAMS = "token=" + token;

	    
		URL url;
		HttpURLConnection conn;
	    
	    
		try{
	    
	        url = new URL(Uri);
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	
	        conn.setDoOutput(true);
	        OutputStream os = conn.getOutputStream();
	        os.write(POST_PARAMS.getBytes());
	        os.flush();
	        os.close();
	        // For POST only - END
	 
	        int responseCode = conn.getResponseCode();
	        System.out.println("POST Response Code :: " + responseCode);
	 
	        if (responseCode == HttpURLConnection.HTTP_OK) { 
	        	
	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	 
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	 
	            // print result
	            System.out.println(response.toString());
	            
	            if(response.toString().equals("true")){
	            	
	            	return true;
	            	
	            }else
	            {
	            	return false;
	            	
	            }
	            
	            
	        } else {
	            System.out.println("POST request not worked");
	            
	            return false;
	        }
        
		}catch(Exception ex)
		{
			
			return false;
			
		}
		
	}

}
