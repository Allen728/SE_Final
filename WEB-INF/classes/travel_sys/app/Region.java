package travel_sys.app;
import org.json.*;

//DB的所有欄位
public class Region {
	
	private String country_name;
	private String travel_way;
	private String region_name;
	
	//DB的select
	private RegionHelper rh =  RegionHelper.getHelper();
	//有region_name
	//public Region(String region_name) {
	//	this.region_name = region_name;
	//}
	//有region_name
	public Region(String country_name, String region_name, String travel_way) {
		this.region_name = region_name;
		this.travel_way = travel_way;
		this.country_name = country_name;
	}
    public String getTravelWays() {
        return this.travel_way;
    }	
    public String getRegionName() {
        return this.region_name;
    }
    public String getCountryName() {
    	return this.country_name;
    }
	public JSONObject getData() {
		JSONObject jso = new JSONObject();
		jso.put("travel_ways", getTravelWays());
		jso.put("region_name", getRegionName());
		jso.put("country_name", getCountryName());
		return jso;
	}
	
	
}
