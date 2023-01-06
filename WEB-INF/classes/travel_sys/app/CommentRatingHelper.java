package travel_sys.app;

import java.sql.*;
import org.json.*;

import travel_sys.util.DBMgr;

public class CommentRatingHelper {
	private CommentRatingHelper() {}
	private static CommentRatingHelper crHelper;
	
	//Prepare for Connection of database
	private Connection conn = null;
	private PreparedStatement pres = null;
	
	public static CommentRatingHelper getHelper() {
		if(crHelper == null) crHelper = new CommentRatingHelper();
		return crHelper;
	}
	
	//Create Comment and Rating of the user to database
	public JSONObject createCommentRating(CommentRating cr){
		String execute_sql = "";
		int row = 0;
		try {
			//get connection to database
			System.out.println("try to connect db.");
			conn = DBMgr.getConnection();
			//sql statement
			String sql = "INSERT INTO `travel_saver`.`comment_rating`(`user_email`,`user_comment`,`user_rating`)"+"VALUES(?,?,?)";
			//value for the sql statement
			String user_email = cr.getUserEmail();
			String user_comment = cr.getUserComment();
			String user_rating = cr.getUserRating();
			
			System.out.println("[system] try to create comment...");
			//set prepared statement with sql statement and variables
			pres = conn.prepareStatement(sql);
			pres.setString(1, user_email);
			pres.setString(2, user_comment);
			pres.setString(3,user_rating);
			row = pres.executeUpdate();
			
			//set the execute_sql statement
			execute_sql = pres.toString();
			System.out.println(execute_sql);
			
		}catch(SQLException e) {
			//show error message
			System.err.format("SQL State: %s \n%s \n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBMgr.close(pres,conn);
		}
		JSONObject response = new JSONObject();
		//set response of the execute_sql
		response.put("sql", execute_sql);
		response.put("row", row);
		return response;
	}
	
	//Get all comment and rating data from database
	public JSONObject getAll() {
		CommentRating cr = null;
		JSONArray jsa = new JSONArray();
		int row = 0; //number of comment and rating
		String execute_sql = "";
		ResultSet rs = null;
		try {
			conn = DBMgr.getConnection();
			String sql = "SELECT * FROM `travel_saver`.`comment_rating`";
			pres = conn.prepareStatement(sql);
			rs = pres.executeQuery();
			execute_sql = pres.toString();
			System.out.println(execute_sql);
			
			while(rs.next()) {
				row += 1;
				String user_email = rs.getString("user_email");
				String user_comment = rs.getString("user_comment");
				String user_rating = rs.getString("user_rating");
				
				cr = new CommentRating(user_email,user_comment,user_rating);
				jsa.put(cr.getData());
				System.out.println("Check:"+cr.getCommentID()+","+cr.getUserComment()+","+cr.getUserRating());
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
	public JSONObject getByEmail(String email) {
        CommentRating cr = null;
        String exexcute_sql = "";
        int row = 0;
        JSONArray jsa = new JSONArray();
        ResultSet rs = null;
        JSONObject response = null;
        try {
            conn = DBMgr.getConnection();
            String sql = "SELECT * FROM `travel_saver`.`comment_rating` WHERE user_email = ?";
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, email);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該會員編號之資料，因此其實可以不用使用 while 迴圈 */
            
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                String user_email = rs.getString("user_email");
                int comment_id = rs.getInt("comment_id");
                String user_comment = rs.getString("user_comment");
                String user_rating = rs.getString("user_rating");
                
                /** 將每一筆會員資料產生一名新User物件 */
                cr = new CommentRating(user_email, comment_id,user_comment, user_rating);
                jsa.put(cr.getData());
                
                
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
        response = new JSONObject();
        response.put("row", row);
        response.put("data", jsa);
        return response;
    }
	   public JSONObject deleteByID(int id) {
	       /** 記錄實際執行之SQL指令 */
	       String exexcute_sql = "";
	       /** 紀錄SQL總行數 */
	       int row = 0;
	       /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
	       ResultSet rs = null;
	       
	       try {
	           /** 取得資料庫之連線 */
	           conn = DBMgr.getConnection();
	           
	           /** SQL指令 */
	           String sql = "DELETE FROM `travel_saver`.`comment_rating` WHERE comment_id = ? LIMIT 1";
	           
	           /** 將參數回填至SQL指令當中 */
	           pres = conn.prepareStatement(sql);
	           pres.setInt(1, id);
	           /** 執行刪除之SQL指令並記錄影響之行數 */
	           row = pres.executeUpdate();

	           /** 紀錄真實執行的SQL指令，並印出 **/
	           exexcute_sql = pres.toString();
	           System.out.println(exexcute_sql);
	           
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
	       
	       /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
	       JSONObject response = new JSONObject();
	       response.put("row", row);

	       return response;
	   }
	
}
