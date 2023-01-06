package travel_sys.app;

import org.json.*;

public class CommentRating {
	private String user_email;
	private int comment_id;
	private String user_comment;
	private String user_rating;
	
	private CommentRatingHelper crHelper = CommentRatingHelper.getHelper();
	
	public CommentRating(String uemail, int id, String com, String rate) {
		this.user_email = uemail;
		this.comment_id = id;
		this.user_comment = com;
		this.user_rating = rate;
	}
	public CommentRating(String uemail, String com, String rate) {
		this.user_email = uemail;
		this.user_comment = com;
		this.user_rating = rate;
	}
	public String getUserEmail() {
		return this.user_email;
	}
	public int getCommentID() {
		return this.comment_id;
	}
	public String getUserComment() {
		return this.user_comment;
	}
	public String getUserRating() {
		return this.user_rating;
	}
	//get the user's comment and rating
	public JSONObject getData() {
		JSONObject jso = new JSONObject();
		jso.put("user_email", getUserEmail());
		jso.put("comment_id", getCommentID());
		jso.put("user_comment", getUserComment());
		jso.put("user_rating", getUserRating());
		return jso;
	}
}
