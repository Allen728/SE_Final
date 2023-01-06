package travel_sys.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

import travel_sys.app.InfoHelper;
import travel_sys.app.Info;
import travel_sys.app.Region;
import travel_sys.app.RegionHelper;
import travel_sys.tools.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class RegionController<br>
 * RegionController類別（class）主要用於處理Region相關之Http請求（Request），繼承HttpServlet
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
@WebServlet("/api/region.do")
public class RegionController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private RegionHelper rh =  RegionHelper.getHelper();
    private InfoHelper ih =  InfoHelper.getHelper();
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
     JsonReader jsr = new JsonReader(request);
     
     String region_name = jsr.getParameter("region");
        String country_name = jsr.getParameter("country");
        
        JSONObject query1 = rh.getTravelWays(country_name,region_name);
        JSONObject query2 = ih.getParking(country_name,region_name);
        JSONObject query3 = ih.getMRT(country_name,region_name);
        
        JSONObject resp = new JSONObject();
        resp.put("response", query1);
        resp.put("parking_response", query2);
        resp.put("mrt_response", query3);
       
        jsr.response(resp, response);
    }
    

  
}