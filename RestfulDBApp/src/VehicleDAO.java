import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
// Aidan Mackintosh-Stewart 17004449
public class VehicleDAO {
	
	private static Connection getDBConnection() {
		
		Connection conn = null;
		
		try {
				Class.forName("org.sqlite.JDBC");
		} catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			String url = "jdbc:sqlite:vehicles.sqlite";
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return conn;
	}
	
	public ArrayList<Vehicle> getAllVehicles() throws SQLException {
		System.out.println("Retrieving all Vehicles...");
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = "SELECT * FROM vehicles;";
		Vehicle temp = null;
		ArrayList<Vehicle> Vehicles = new ArrayList<Vehicle>();
		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println("DBQuery = " + query);
			System.out.println("----------");
			
			//execute SQL query
			result = statement.executeQuery(query);
			
			
			 while(result.next()) {
				 
		
				 int vehicle_id = result.getInt("vehicle_id");				 
				 String make = result.getString("make");
				 String model = result.getString("model");
				 int year = result.getInt("year");
				 int price = result.getInt("price");
		         String license_number = result.getString("license_number");
				 String colour = result.getString("colour");
				 int number_doors = result.getInt("number_doors");
				 String transmission = result.getString("transmission");
				 int mileage = result.getInt("mileage");
				 String fuel_type = result.getString("fuel_type");
				 int engine_size = result.getInt("engine_size");
				 String body_style = result.getString("body_style");
				 String condition = result.getString("condition");
				 String notes = result.getString("notes");
				 
				 Vehicles.add(new Vehicle(vehicle_id, make, model, year, price, license_number, colour,
						 number_doors, transmission, mileage, fuel_type, engine_size, body_style, condition, notes));
			     }
		} finally {
			if (result !=null) {result.close(); }
			if (statement != null) {statement.close();}
			if (dbConnection !=null) {dbConnection.close();}
		}
		return Vehicles;
	}
	
	public Vehicle getVehicle(int vehicleID) throws SQLException {
		Vehicle temp = null;
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = "SELECT * FROM vehicles WHERE vehicle_id =" + 
		vehicleID + ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println("DBQuery = " + query);
			result = statement.executeQuery(query);
			
			 while(result.next()) {
				 							 
				 int vehicle_id = result.getInt("vehicle_id");				 
				 String make = result.getString("make");
				 String model = result.getString("model");
				 int year = result.getInt("year");
				 int price = result.getInt("price");
		         String license_number = result.getString("license_number");
				 String colour = result.getString("colour");
				 int number_doors = result.getInt("number_doors");
				 String transmission = result.getString("transmission");
				 int mileage = result.getInt("mileage");
				 String fuel_type = result.getString("fuel_type");
				 int engine_size = result.getInt("engine_size");
				 String body_style = result.getString("body_style");
				 String condition = result.getString("condition");
				 String notes = result.getString("notes");
				 
				 temp = (new Vehicle(vehicle_id, make, model, year, price, license_number, colour,
						 number_doors, transmission, mileage, fuel_type, engine_size, body_style, condition, notes));
				 
			 }
		
		} finally {
			if (result !=null) {result.close(); }
			if (statement != null) {statement.close();}
			if (dbConnection !=null) {dbConnection.close();}
		}
		return temp;
	}
	
	public Boolean deleteVehicle(int VehicleID) throws SQLException {
		System.out.println("Deleting vehicle");
		Connection dbConnection = null;
		Statement statement = null;
		int result = 0;
		String query = "DELETE FROM vehicles WHERE vehicle_id = " + VehicleID + ";";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(query);
			// execute SQL query
			result = statement.executeUpdate(query);
			
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		if (result == 1) {
			return true;
		} else {
			return false;
		}
}
	
	public Boolean updateVehicle(Vehicle v) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		
		String query = "UPDATE vehicles SET vehicle_id = ?, make = ?, model = ?, year = ?, price = ?, license_number = ?, colour = ?, number_doors = ?, transmission = ?, mileage = ?, fuel_type = ?, engine_size = ?, body_style = ?, condition = ?, notes = ? WHERE vehicle_id =" + v.getVehicle_id() + ";"; 
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(query);
			// execute SQL update
			//statement.executeUpdate(query);
			PreparedStatement pstmt = dbConnection.prepareStatement(query);
			pstmt.setInt(1,  v.getVehicle_id());
			pstmt.setString(2,  v.getMake());
			pstmt.setString(3,  v.getModel());
			pstmt.setInt(4,  v.getYear());
			pstmt.setInt(5, v.getPrice());
			pstmt.setString(6, v.getLicense_number());
			pstmt.setString(7, v.getColour());
			pstmt.setInt(8, v.getNumber_doors());
			pstmt.setString(9, v.getTransmission());
			pstmt.setInt(10, v.getMileage());
			pstmt.setString(11,v.getFuel_type());
			pstmt.setInt(12, v.getEngine_size());
			pstmt.setString(13, v.getBody_style());
			pstmt.setString(14, v.getCondition());
			pstmt.setString(15, v.getNotes());
			
			
			pstmt.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return false;

		} finally {

			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return true;
	}
	
	public boolean insertRecordIntoVehicles(Vehicle v) throws SQLException{
		Connection dbConnection = null;
		Statement statement = null;
		
		String update = "INSERT INTO vehicles (vehicle_id, make, model, year, price, license_number, colour, number_doors, transmission, mileage, fuel_type, engine_size, body_style, condition, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		boolean ok = false;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(update);
			PreparedStatement pstmt = dbConnection.prepareStatement(update);
			pstmt.setInt(1, v.getVehicle_id());
			pstmt.setString(2,  v.getMake());
			pstmt.setString(3,  v.getModel());
			pstmt.setInt(4,  v.getYear());
			pstmt.setInt(5, v.getPrice());
			pstmt.setString(6, v.getLicense_number());
			pstmt.setString(7,v.getColour());
			pstmt.setInt(8, v.getNumber_doors());
			pstmt.setString(9, v.getTransmission());
			pstmt.setInt(10, v.getMileage());
			pstmt.setString(11,v.getFuel_type());
			pstmt.setInt(12, v.getEngine_size());
			pstmt.setString(13, v.getBody_style());
			pstmt.setString(14, v.getCondition());
			pstmt.setString(15, v.getNotes());
			pstmt.executeUpdate();
			// execute SQL query
			//statement.executeUpdate(update);
			ok = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
			
		}
	return ok;
}
	
	
	 public boolean login(String userName, String password) throws SQLException {
		 Connection dbConnection = null;
			Statement statement = null;
		
		String login = "SELECT username, password FROM users WHERE username=? AND password=?";
	        try {
	        	dbConnection = getDBConnection();
				
	        	System.out.println(login);
				PreparedStatement pstmt = dbConnection.prepareStatement(login);
	            pstmt.setString(1, userName);
	            pstmt.setString(2, password);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	            		  
	            		   System.out.println("Success");
	            		   return true;
	       				
	            }
	            	   else {
	            		   System.out.println("Invalid username or password!");
	            		   
	            		
	            	   	}
	            } catch (Exception e) {
	                System.out.println("DB related Error");
	                e.printStackTrace();
	            } finally {
	    			if (statement != null) {
	    				statement.close();
	    			}
	    			if (dbConnection != null) {
	    				dbConnection.close();
	    			}
	            }
			return false;
	        }
	            	  
	            	
	            	
	            
	          
	  
	        	 	
            
	   
			
	         
	        
	  
	 }	    



