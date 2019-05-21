import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;



public class Controller {

	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
		
		final VehicleDAO dao = new VehicleDAO();
		
		
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
			final Gson gson = new Gson();
			//list contacts...
			server.createContext("/api", new HttpHandler() {

				@Override
				public void handle(HttpExchange he) throws IOException {
					String head ="<html><head></head><body><table><tr><th>VehicleID</th><th>Make</th><th>Model</th><th>Year</th><th>Price</th><th>License Number</th><th>Colour</th><th>Number of Doors</th><th>Transmission</th><th>Mileage</th><th>Fuel Type</th><th>Engine Size</th><th>Body Style</th><th>Condition</th><th>Notes</th></tr>";
					String response = "</table></body></html>";
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
					
					ArrayList<Vehicle> allEmps = new ArrayList<>();
					try {
						allEmps = dao.getAllVehicles();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					he.sendResponseHeaders(200,0); //Must send Http response, otherwise will not work
					out.write(gson.toJson(allEmps));
					out.close();					
					
				}
				
			});
			
			
			server.createContext("/add_vehicle", new HttpHandler() {
				@Override
				public void handle(HttpExchange he) throws IOException {
				System.out.println("adding new vehicle");
				HashMap<String,String> post = new HashMap<String,String>();
				//read the request body
				BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
				String line = "";
				String request = "";
				while((line = in.readLine()) != null) {
					request = request + line;
				}
				System.out.println(">>>>>>>>>>>>>>> "+request);
				//individual key=value pairs are delimited by ampersands. Tokenize.
				String[] pairs = request.split("&");					
				for(int i=0;i<pairs.length;i++) {
					//each key=value pair is separated by an equals, and both halves require URL decoding.
					String pair = pairs[i];
					post.put(URLDecoder.decode(pair.split("=")[0],"UTF-8"),URLDecoder.decode(pair.split("=")[1],"UTF-8"));
				}
				int vid = Integer.parseInt(post.get("vehicle_id"));
				int pr = Integer.parseInt(post.get("price"));
				int yr = Integer.parseInt(post.get("year"));
				int doors = Integer.parseInt(post.get("number_doors"));
				int mile = Integer.parseInt(post.get("mileage"));
				int engine = Integer.parseInt(post.get("engine_size"));

				
				
				Vehicle v = new Vehicle(vid, post.get("make"),post.get("model"),yr,pr,
						post.get("license_number"),post.get("colour"),doors,post.get("transmission"),
						mile,post.get("fuel_type"),engine,post.get("body_style"),
						post.get("condition"),post.get("notes"));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));					
				try { 
					dao.insertRecordIntoVehicles(v); 
					he.sendResponseHeaders(200, 0); //HTTP 200 (OK)
					out.write("Success!");
					out.close();
				}
				catch(SQLException se) {
					 he.sendResponseHeaders(500, 0); //HTTP 500 (Internal Server Error)
					 out.write("Error Adding Contact");
					 out.close();
				}
				
				
			}
			
			
			
			
			});
			
			server.createContext("/add_contact_json",new HttpHandler() {
				//process data from /add form
				@Override
				public void handle(HttpExchange he) throws IOException {
					System.out.println("adding new vehicle");
					HashMap<String,String> post = new HashMap<String,String>();
					Gson gson = new Gson();
					//read the request body
					BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
					String line = "";
					String request = "";
					while((line = in.readLine()) != null) {
						request = request + line;
					}
					System.out.println(">>>>>>>>>>>>>>> "+request);
					//individual key=value pairs are delimited by ampersands. Tokenize.
					String[] pairs = request.split("&");					
					for(int i=0;i<pairs.length;i++) {
						//each key=value pair is separated by an equals, and both halves require URL decoding.
						String pair = pairs[i];
						post.put(URLDecoder.decode(pair.split("=")[0],"UTF-8"),URLDecoder.decode(pair.split("=")[1],"UTF-8"));
					}
					
					//Should have a HashMap of posted data in our "post" variable. Now to add a vehicle
					Vehicle v = gson.fromJson(post.get("json"), Vehicle.class);
					
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));					
					try { 
						dao.insertRecordIntoVehicles(v); 
						he.sendResponseHeaders(200, 0); //HTTP 200 (OK)
						out.write("Success!");
						out.close();
					}
					catch(SQLException se) {
						 he.sendResponseHeaders(500, 0); //HTTP 500 (Internal Server Error)
						 out.write("Error Adding Contact");
						 out.close();
					}
				}
				
			});
			
			
			server.createContext("/add_vehicle_json",new HttpHandler() {
				//process data from /add form
				@Override
				public void handle(HttpExchange he) throws IOException {
					System.out.println("adding new contact");
					HashMap<String,String> post = new HashMap<String,String>();
					Gson gson = new Gson();
					//read the request body
					BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
					String line = "";
					String request = "";
					while((line = in.readLine()) != null) {
						request = request + line;
					}
					System.out.println(">>>>>>>>>>>>>>> "+request);
					//individual key=value pairs are delimited by ampersands. Tokenize.
					String[] pairs = request.split("&");					
					for(int i=0;i<pairs.length;i++) {
						//each key=value pair is separated by an equals, and both halves require URL decoding.
						String pair = pairs[i];
						post.put(URLDecoder.decode(pair.split("=")[0],"UTF-8"),URLDecoder.decode(pair.split("=")[1],"UTF-8"));
					}
					
					//Should have a HashMap of posted data in our "post" variable. Now to add a contact
					Vehicle v = gson.fromJson(post.get("json"), Vehicle.class);
					
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));					
					try { 
						dao.insertRecordIntoVehicles(v); 
						he.sendResponseHeaders(200, 0); //HTTP 200 (OK)
						out.write("Success!");
						out.close();
					}
					catch(SQLException se) {
						 he.sendResponseHeaders(500, 0); //HTTP 500 (Internal Server Error)
						 out.write("Error Adding Contact");
						 out.close();
					}
				}
				
			});
			
			
			
			
			System.out.println("server running on port 8000");
			server.start();
	}
		catch(IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage() + "  " + ioe.getStackTrace());
		}
}
}
