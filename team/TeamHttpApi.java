package shop.team;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import shop.db.Init;
import shop.json.SendJsonResponse;
import shop.log.LoggerTool;
import shop.server.MyAnnotation;


public class TeamHttpApi {
    @MyAnnotation
    public void createTeam(HttpServletRequest req, HttpServletResponse resp) throws SQLException,ClassNotFoundException,IOException{
        String creatorID = req.getParameter("id");
        String teamName = req.getParameter("name");
        if(!Init.authUser(creatorID)) {
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }
        if(teamName == null) {
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","lackOfTeamName");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }

        Init.createTeam(creatorID, teamName);
        Map<String,String> map = new HashMap<String,String>();
        map.put("stat","OK");
        SendJsonResponse.parseMap2Json(map,resp);
    }
    
    @MyAnnotation
    public void listTeams(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
    	String userID = req.getParameter("id");
    	if(!Init.authUser(userID)) {
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }
    	Map<String,ArrayList<Map<String,String>>> map = new HashMap<String,ArrayList<Map<String,String>>>();
    	map = Init.getTeams();
        SendJsonResponse.parseMap2Json(map,resp);
        LoggerTool.getDefaultLogger().error("程序里:"+JSONObject.toJSONString(map));
    }
    @MyAnnotation
    public void getAllTeamsBelongsToCreator(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, IOException, SQLException {
    	String creatorID = req.getParameter("id");
    	if(!Init.isCreator(creatorID)) {
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidCreatorID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
    	}

    	Map<String,ArrayList<Map<String,String>>> map = new HashMap<String,ArrayList<Map<String,String>>>();
    	map = Init.getTeamPropertyByCreator(creatorID);
    	SendJsonResponse.parseMap2Json(map,resp);
    }
    
    @MyAnnotation
    public void getAllTeamMembers(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, IOException, SQLException {
    	String userID = req.getParameter("id");
    	String tid = req.getParameter("tid");
    	/*查询组下所有用户信息的权限开放给所有用户*/
    	if(!Init.authUser(userID)) {
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidCreatorID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
    	}
    	Map<String,ArrayList<Map<String,String>>> map = new HashMap<String,ArrayList<Map<String,String>>>();
    	map = Init.getTeamUsers(tid);
    	SendJsonResponse.parseMap2Json(map,resp);
    }
    /**
     * 删除组，解散组内所有成员，并取消本组当前所有未完成订单。
     * @param req
     * @param resp
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    @MyAnnotation
    public void deleteTeam(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
    	String creatorID = req.getParameter("id");
    	String tid = req.getParameter("tid");
    	if(! Init.isCreator(creatorID)) {
    		Map<String,String> map = new HashMap<String,String>();
            map.put("stat","you are not team creator");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
    	}
    	Init.deleteTeam(tid);
    	Map<String,String> map = new HashMap<String,String>();
        map.put("stat","OK");
        SendJsonResponse.parseMap2Json(map,resp);
    }
     @MyAnnotation
     public void quitFromTeam(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
    	 String userID = req.getParameter("id");
    	 String tid = req.getParameter("tid");
    	 if(!Init.authUser(userID)) {
    		 Map<String,String> map = new HashMap<String,String>();
             map.put("stat","invalidID");
             SendJsonResponse.parseMap2Json(map,resp);
             return;
    	 }
    	 Init.deleteUserFromTeam(tid, userID);
    	 Map<String,String> map = new HashMap<String,String>();
         map.put("stat","OK");
         SendJsonResponse.parseMap2Json(map,resp);
         return;
     }
}
