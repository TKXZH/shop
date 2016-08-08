package shop.order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shop.db.Init;
import shop.event.EventHub;
import shop.eventImp.OrderCanceledByCreatorEvent;
import shop.json.SendJsonResponse;
import shop.server.MyAnnotation;

public class OrderHttpApi {
    @MyAnnotation
    public void createOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception{
    	String userID = req.getParameter("userID");
        String productID = req.getParameter("productID");
        String productNum = req.getParameter("productNum");
        String tid = req.getParameter("tid");
        String time = req.getParameter("time");
        /*检验用户ID是否合法*/
        if(!Init.isInTeam(userID)) {
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }	
        /*检验商品ID是否存在*/
        if(!Init.authProduct(productID)) {
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidProductID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }
        /*检验商品数量是否合法*/
        if(Integer.parseInt(productNum)<=0) {
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidProductNum");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }
        /*检验teamID是否存在*/
        if(!Init.teamExist(tid)) {
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidTid");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }
        try{
        	Init.createOrder(productID, userID, Integer.parseInt(productNum),Integer.parseInt(tid),time);
            Map<String,String> map = new HashMap<String,String>();
            map.put("stat","OK");
            SendJsonResponse.parseMap2Json(map,resp);
        }	catch(SQLException e) {
        	e.printStackTrace();
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidProductID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }

    }
    
    @MyAnnotation
    public void getUserOrders(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
    	String id = req.getParameter("id");
    	if(!Init.authUser(id)) {
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }
    	 Map<String,ArrayList<Map<String,String>>> map = new HashMap<String,ArrayList<Map<String,String>>>();         
    	 map = Init.getMyOrders(id);
    	 ArrayList<Map<String,String>> list = map.get("orders");
    	 Collections.sort(list,new compareTime());
         SendJsonResponse.parseMap2Json(map,resp);
    }
    /*重写比较器，实现比较订单按时间排序*/
    //TODO 完成比较器逻辑
    class compareTime implements Comparator<Map<String, String>> {

		@Override
		public int compare(Map<String, String> o1, Map<String, String> o2) {
			long time1 = Long.parseLong(o1.get("orderTime"));
			System.out.println(time1);
			long time2 = Long.parseLong(o2.get("orderTime"));
			
			if(time1 < time2) {
				return -1;
			}	else if (time1 == time2) {
				return 0;
			}	else {
				return 1;
			}
		}
    	
    }
    @MyAnnotation
    public void getUserOrdersByTeamID(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
    	String creatorID = req.getParameter("id");
    	String tid = req.getParameter("tid");
    	if(!Init.isCreator(creatorID)) {
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","you are not team creator");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
    	}
    	Map<String,ArrayList<Map<String, String>>> map = new HashMap<String,ArrayList<Map<String, String>>>();
        map = Init.getTeamOrders(tid);
        SendJsonResponse.parseMap2Json(map,resp);	
    }
    
    /**
     * 取消订单接口，请求参数为用户ID和订单ID
     * @param req
     * @param resp
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    @MyAnnotation
    public void cancelOrder(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
    	String userID = req.getParameter("id");
    	String orderID = req.getParameter("orderID");
    	if(!Init.authUser(userID)) {
            Map<String,String> map = new HashMap<String,String>();
            map.put("stat","invalidID");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }
    	Init.cancelOrder(orderID);
    	if(Init.isCreator(userID)) {
    		OrderCanceledByCreatorEvent event = new OrderCanceledByCreatorEvent(userID, orderID);
        	/*触发用户订单被组长取消事件*/
        	EventHub.getInstence().dispatchEvent(OrderCanceledByCreatorEvent.Type.Order_Canceled_By_Creator, event);
    	}
    	Map<String,String> map = new HashMap<String,String>();
        map.put("stat","OK");
        SendJsonResponse.parseMap2Json(map,resp);
    }
    @MyAnnotation
    public void finishTeamOrder(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException {
    	String creatorID = req.getParameter("id");
    	String tid = req.getParameter("tid");
    	if(! Init.isCreator(creatorID)) {
            Map<String,String> map = new HashMap<String,String>();
            map.put("stat","you are not team creator");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
    	}
    	Init.finishTeamOrder(tid);
    	Map<String,String> map = new HashMap<String,String>();
        map.put("stat","OK");
        SendJsonResponse.parseMap2Json(map,resp);
    }
}
