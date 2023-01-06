package travel_sys.app;

import java.sql.*;
import java.time.LocalDateTime;
import org.json.*;

import travel_sys.util.DBMgr;

//DB指令
public class RegionHelper {
	private RegionHelper() {
		
    }
	private static RegionHelper rh;
	
	
	/** 儲存JDBC資料庫連線 */
    private Connection conn = null;
    
    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;
    
    public static RegionHelper getHelper() {
        if(rh == null) rh = new RegionHelper();
        return rh;
    }
        
    /**
     * 透過地區名稱取得資料
     *
     * @param region_name 地區名稱
     * @return the JSON object 回傳SQL執行結果與該地區名稱之_適合旅遊方式
     */
    public JSONObject getTravelWays(String country_name, String region_name) {
        Region r = null;
        JSONArray jsa = new JSONArray();
        int row = 0;
        String exexcute_sql = "";
        String sql = "";
       
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            
            sql = "SELECT * FROM `travel_saver`.`country_region`,`travel_saver`.`country`,`travel_saver`.`region` "
            		+ "WHERE `travel_saver`.`country_region`.`country_codename` = `travel_saver`.`country`.`country_codename` and "
            		+ "`travel_saver`.`country_region`.`region_codename` = `travel_saver`.`region`.`region_codename` and "
            		+ "`travel_saver`.`country`.`country_name` ='"+country_name+"' and `travel_saver`.`region`.`region_name` ='"+region_name+"'";
            
            
            
            
            //sql = "SELECT * FROM `travel_saver`.`taiwan_violation` WHERE `area` ='"+ region_name + "' LIMIT 1 ";
            
            /*Ways
            if(country_name == "台灣") {
            	sql = "SELECT * FROM `travel_sys`.`taiwan_violation` WHERE `area` = \""+ region_name+"\" LIMIT 1";
            }
            else if(country_name == "日本") {
            	sql = "SELECT * FROM `travel_sys`.`japan_violation` WHERE `area` = \""+ region_name+"\" LIMIT 1";
            }
            else if(country_name == "韓國") {
            	sql = "SELECT * FROM `travel_sys`.`korea_violation` WHERE `area` = \""+ region_name+"\" LIMIT 1";
            }*/
            pres = conn.prepareStatement(sql);
            rs = pres.executeQuery();
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            while(rs.next()) {
                row += 1;
                String ways = rs.getString("ways");
                String area = rs.getString("region_name");
                String country = rs.getString("country_name");
                
                r = new Region(country,area,ways);
                jsa.put(r.getData());
            }
            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("data", jsa);
        response.put("data1", "testing");
        response.put("data2", region_name);

        return response;
    }
    
	

}
