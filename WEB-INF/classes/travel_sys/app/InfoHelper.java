package travel_sys.app;

import java.sql.*;
import org.json.*;

import travel_sys.util.DBMgr;

public class InfoHelper {
	private InfoHelper() {}
	private static InfoHelper ih;
	
	private Connection conn = null;
	private PreparedStatement pres = null;
	
	public static InfoHelper getHelper() {
		if(ih == null) ih = new InfoHelper();
		return ih;
	}

	public JSONObject getParking(String country, String region) {
		Info myinfo = null;
		JSONArray jsa = new JSONArray();
		int row = 0; 
		String sql = "";
		String execute_sql = "";
		ResultSet rs = null;
		try {
			conn = DBMgr.getConnection();
			
			sql="SELECT * FROM `travel_saver`.`region_parking`,`travel_saver`.`parking`,`travel_saver`.`region`" + 
					"WHERE `travel_saver`.`region_parking`.`parking_id` = `travel_saver`.`parking`.`parking_id` and" + 
					"`travel_saver`.`region`.`region_codename` = `travel_saver`.`region_parking`.`region_codename` and" + 
					"`travel_saver`.`region`.`region_name` ='"+region+"'";
			
			pres = conn.prepareStatement(sql);
			rs = pres.executeQuery();
			execute_sql = pres.toString();
			System.out.println(execute_sql);
			
			while(rs.next()) {
				row += 1;
  
				String road = rs.getString("road");

				myinfo = new Info(country,region,road);
				jsa.put(myinfo.getData());
				
			}
		}catch(SQLException e) {
			System.err.format("SQL state: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBMgr.close(rs, pres, conn);
		}
		
		JSONObject response = new JSONObject();
		response.put("sql", execute_sql);
		response.put("row", row);
		response.put("data", jsa);
		
		return response;
	}
	
	
	public JSONObject getMRT(String country, String region) {
		Info myinfo = null;
		JSONArray jsa = new JSONArray();
		int row = 0; 
		String sql = "";
		String execute_sql = "";
		ResultSet rs = null;
		try {
			conn = DBMgr.getConnection();
			
			sql="SELECT * FROM `travel_saver`.`public_transport`,`travel_saver`.`region_public_transport`,`travel_saver`.`country_region`,`travel_saver`.`country`,`travel_saver`.`region`" + 
				     "WHERE `travel_saver`.`region_public_transport`.`transport_id` = `travel_saver`.`public_transport`.`transport_id` and" + 
				     "`travel_saver`.`region_public_transport`.`region_codename` = `travel_saver`.`country_region`.`region_codename` and" + 
				     "`travel_saver`.`country`.`country_codename` = `travel_saver`.`country_region`.`country_codename` and" + 
				     "`travel_saver`.`region`.`region_codename` = `travel_saver`.`country_region`.`region_codename` and" + 
				     "`travel_saver`.`country`.`country_name` ='"+country+"'and `travel_saver`.`region`.`region_name` ='"+region+"'";
			
			pres = conn.prepareStatement(sql);
			rs = pres.executeQuery();
			execute_sql = pres.toString();
			System.out.println(execute_sql);
			
			while(rs.next()) {
				row += 1;
  
				String mrt = rs.getString("mrt");

				myinfo = new Info(country,region,mrt);
				jsa.put(myinfo.getData());
				
			}
		}catch(SQLException e) {
			System.err.format("SQL state: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBMgr.close(rs, pres, conn);
		}
		
		JSONObject response = new JSONObject();
		response.put("sql", execute_sql);
		response.put("row", row);
		response.put("data", jsa);
		
		return response;
	}
	
}
