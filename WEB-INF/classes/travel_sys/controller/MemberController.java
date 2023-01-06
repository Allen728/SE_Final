package travel_sys.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import travel_sys.app.Member;
import travel_sys.app.MemberHelper;
import travel_sys.tools.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class MemberController<br>
 * MemberController類別（class）主要用於處理Member相關之Http請求（Request），繼承HttpServlet
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class MemberController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper mh =  MemberHelper.getHelper();
    
    /**
     * 處理Http Method請求POST方法（新增資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        String name = jso.getString("name");
        String email = jso.getString("email");
        String password = jso.getString("password");
        
        /** 建立一個新的會員物件 */
        Member m = new Member(name, email, password);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'no~no\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }else if (!mh.checkDuplicate(m)) {
            /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
            JSONObject data = mh.create(m);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "挖你好棒");
            resp.put("message", "成功! 註冊會員資料...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }else {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'喔不\', \"message\": \'新增帳號失敗，此E-Mail帳號重複！\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
    }

    /**
     * 處理Http Method請求PUT方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int id = jso.getInt("id");
        String name = jso.getString("name");
        String email = jso.getString("email");
        String password = jso.getString("password");

        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
        Member m = new Member(id, name, email, password);
        
        /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
        JSONObject data = m.update();
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "讚喔");
        resp.put("message", "成功更新會員資料...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	  /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
    	        JsonReader jsr = new JsonReader(request);

    	        String email = jsr.getParameter("email");
    	        String id = jsr.getParameter("id");
    	        
    	        if(id.isEmpty()){
    	            /** 透過UserHelper物件的getByEmail()方法自資料庫取回該名會員之資料，回傳之資料為JSONObject物件 */
    	            JSONObject query = mh.getByEmail(email);
    	            
    	            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
    	            JSONObject resp = new JSONObject();
    	            resp.put("status", "200");
    	            resp.put("message", "資料庫連線成功");
    	            resp.put("response", query);
    	    
    	            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
    	            jsr.response(resp, response);
    	        }else {
    	            /** 透過UserHelper物件的getByID()方法自資料庫取回該名會員之資料，回傳之資料為JSONObject物件 */
    	            JSONObject query = mh.getByID(id);
    	            
    	            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
    	            JSONObject resp = new JSONObject();
    	            resp.put("status", "200");
    	            resp.put("message", "資料庫連線成功");
    	            resp.put("response", query);
    	    
    	            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
    	            jsr.response(resp, response);
    	        }

    	 }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	  
    	  // 刪除會員
    	  
    	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
    	        JsonReader jsr = new JsonReader(request);
    	        JSONObject jso = jsr.getObject();
    	        
    	        /** 取出經解析到JSONObject之Request參數 */
    	        int id = jso.getInt("id");
    	        
    	        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
    	        JSONObject query = mh.deleteByID(id);
    	        
    	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
    	        JSONObject resp = new JSONObject();
    	        resp.put("status", "400");
    	        resp.put("message", "會員移除成功！");
    	        resp.put("response", query);

    	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
    	        jsr.response(resp, response);
    	 }
}
