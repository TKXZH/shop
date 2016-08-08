package shop.notify;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shop.db.Init;
import shop.filter.DoFilter;
import shop.filter.NotifyMealRules;
import shop.json.SendJsonResponse;
import shop.server.MyAnnotation;
/**
 * 通知推送API
 * @author Administrator
 *
 */
public class NotifyHttpApi {
	@MyAnnotation
	public void getNotify(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String userID = req.getParameter("id");
		Map<String,Object> map = new HashMap<String,Object>();
		DoFilter.getInstence().doFilter(userID, map);
		if(map.size() == 0) {
			map.put("stat","sleep");
		}
		SendJsonResponse.parseMap2Json(map, resp);
	}
	
	@MyAnnotation
	public void notifyAllTeamMembers(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
		String creatorID = req.getParameter("id");
		String tid = req.getParameter("tid");
		if(! Init.isCreator(creatorID)) {
    		Map<String,String> map = new HashMap<String,String>();
            map.put("stat","you are not team creator");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
    	}
		ArrayList<String> users = Init.getTeamUserID(tid);
		for(String user : users) {
			NotifyMealRules.getInstence().addRules(user, tid);
		}
		Map<String,String> map = new HashMap<String,String>();
        map.put("stat","OK");
        SendJsonResponse.parseMap2Json(map,resp);
	}
}
