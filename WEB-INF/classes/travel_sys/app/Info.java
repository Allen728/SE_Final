package travel_sys.app;

import org.json.*;

public class Info {
	
	private String country_name;
	private String region_name;
	private String traffic_info;
	//private String mrt;
	private InfoHelper ih = InfoHelper.getHelper();
	
	/*public Info(String country_name, String region_name, String traffic_info,String mrt) {
		this.region_name = region_name;
		this.traffic_info = traffic_info;
		this.country_name = country_name;
		this.mrt = mrt;
	}*/
	
	public Info(String country_name, String region_name, String traffic_info) {
		this.region_name = region_name;
		this.traffic_info = traffic_info;
		this.country_name = country_name;
	}
	
	public String getTrafficInfo() {
		return this.traffic_info;
	}
	public String getCountryName() {
		return this.country_name;
	}
	public String getRegionName() {
		return this.region_name;
	}

	public JSONObject getData() {
		JSONObject jso = new JSONObject();
		jso.put("country_name", getCountryName());
		jso.put("region_name", getRegionName());
		jso.put("traffic_info", getTrafficInfo());
		return jso;
	}
}
