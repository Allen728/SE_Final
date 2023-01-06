package travel_sys.controller;

import java.io.*;
import org.json.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import travel_sys.app.CommentRating;
import travel_sys.app.CommentRatingHelper;
import travel_sys.tools.JsonReader;

/**
 * Servlet implementation class CommentRatingController
 */
@WebServlet("/api/CommentRatingController.do")
public class CommentRatingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	//connect to database
    private CommentRatingHelper crHelper = CommentRatingHelper.getHelper();
    
	//show all user comment and rating on the page
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonReader jsr = new JsonReader(request);
		String email = jsr.getParameter("email");
		if(email.isEmpty()) {
			JSONObject query = crHelper.getAll();
			
			JSONObject resp = new JSONObject();
			resp.put("status","200");
			resp.put("message", "成功取得所有留言");
			resp.put("response", query);
			
			jsr.response(resp, response);
		}else {
			JSONObject query = crHelper.getByEmail(email);
			JSONObject resp = new JSONObject();
			resp.put("message", "取得留言成功");
			resp.put("response", query);
			
			jsr.response(resp, response);
		}
	}

	//Create user's Comment and Rating
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		JsonReader jsr = new JsonReader(request);
		JSONObject jso = jsr.getObject();
		
		String user_email = jso.getString("user_email");
		String user_comment = jso.getString("user_comment");
		String user_rating = jso.getString("user_rating");
		
		CommentRating cr = new CommentRating(user_email,user_comment,user_rating);
		
		if(user_comment.isEmpty() || user_rating.isEmpty()) {
			String resp = "還有欄位沒有填寫喔，請檢查！";
			jsr.response(resp,response);
		}else{
			JSONObject data = crHelper.createCommentRating(cr);
			JSONObject resp = new JSONObject();
			resp.put("status", "200");
			resp.put("message", "您已經成功留下您的足跡囉:D");
			resp.put("response", data);
			
			jsr.response(resp, response);
		}
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  	  JsonReader jsr = new JsonReader(request);
	  	  JSONObject jso = jsr.getObject();
	  	        
	  	  int id = jso.getInt("comment_id");
	  	        
	  	  JSONObject query = crHelper.deleteByID(id);
	  	        
	  	  JSONObject resp = new JSONObject();
	  	  resp.put("status", "400");
	  	  resp.put("message", "留言移除成功！");
	  	  resp.put("response", query);
	
	  	  jsr.response(resp, response);
  	  }

}
