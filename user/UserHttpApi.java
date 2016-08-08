package shop.user;

import shop.db.Init;
import shop.event.EventHub;
import shop.eventImp.UserDeletedEvent;
import shop.json.SendJsonResponse;
import shop.server.MyAnnotation;
import shop.token.ShortToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/29.
 */
public class UserHttpApi {

    @MyAnnotation
    public void createUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException,ClassNotFoundException,IOException{
    	String username = req.getParameter("username");
        String tel = req.getParameter("tel");
        System.out.println(username);
        if(username==null || tel==null) {
            Map<String,String> map = new HashMap<String,String>();
            map.put("stat","paramError");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }   
        if(Init.userExist(username, tel)) {
        	String ID = Init.getUserID(username, tel);
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","OK");
            map.put("id", ID);
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }
        
    	String id = ShortToken.generateShortUuid();
        Init.createUser(id, username, tel);
        Map<String,String> map = new HashMap<String,String>();
        map.put("stat","OK");
        map.put("id", id);
        map.put("中文", "中文");
        SendJsonResponse.parseMap2Json(map,resp);
        
    }
    
    @MyAnnotation
    public void joinTeam(HttpServletRequest req, HttpServletResponse resp) throws SQLException,ClassNotFoundException,IOException{
    	String id = req.getParameter("id");
    	String tid = req.getParameter("tid");
    	if(!Init.authUser(id)) {
    		Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
    	}
    	if(Init.alreadyBelongsToThisTeam(id, tid)) {
    		Map<String,String> map = new HashMap<String,String>();
            map.put("stat","already belongs to the team requested");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
    	}
    	try {
			Init.joinTeam(id, tid);
		} catch (SQLException e) {
			Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidTeamID");
            SendJsonResponse.parseMap2Json(map,resp);
            e.printStackTrace();
            return;
		}
    	
    	Map<String,String> map = new HashMap<String,String>();
        map.put("stat","OK");
        SendJsonResponse.parseMap2Json(map,resp);
    }
    
    @MyAnnotation
    public void getMyTeam(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
    	String id = req.getParameter("id");
    	if(!Init.authUser(id)) {
    		Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
    	}
    	Map<String,ArrayList<Map<String,String>>> map = new HashMap<String,ArrayList<Map<String,String>>>();
    	map = Init.getMyTeams(id);
        SendJsonResponse.parseMap2Json(map,resp);
    }
    @MyAnnotation
    public void deleteUserFromTeam(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
    	String tid = req.getParameter("tid");
    	String userID = req.getParameter("userID");
    	String creatorID = req.getParameter("id");
    	if(!Init.isCreator(creatorID)) {
    		Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
    	}
    	Init.deleteUserFromTeam(tid, userID);
    	Map<String,String> map = new HashMap<String,String>();
        map.put("stat","OK");
        SendJsonResponse.parseMap2Json(map,resp);
        UserDeletedEvent event = new UserDeletedEvent(userID, tid);
        /*触发用户被删除事件*/
        EventHub.getInstence().dispatchEvent(UserDeletedEvent.Type.After_Delete, event);
        return;
    }
    
    @MyAnnotation
    public void clearAllMyHistoryOrders(HttpServletRequest req, HttpServletResponse resp) throws IOException, ClassNotFoundException, SQLException {
    	String userID = req.getParameter("id");
    	if(!Init.authUser(userID)) {
    		Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
    	}
    	Init.clearAllMyHistoryOrders(userID);
    	Map<String,String> map = new HashMap<String,String>();
        map.put("stat","OK");
        SendJsonResponse.parseMap2Json(map,resp);
        return;
    }
}
