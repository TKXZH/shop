package shop.server;

import shop.notify.InitNotify;
import shop.order.InitOrder;
import shop.publicservice.InitPublicService;
import shop.team.InitTeam;
import shop.user.InitUserLogin;


/**
 * Created by xvzh on 2016/7/29.
 */
public class Start {
	/**
	 * 初始化各模块并开启服务器
	 * @param args
	 */
    public static void main(String args[]) {
        try {
            //Init.createDatabase();
            ShopServer.initServer();
            InitPublicService.initPublicService();
            InitUserLogin.initUserServlet();
            InitOrder.initOrderRelated();
            InitTeam.initTeamRelated();
            InitNotify.initNotify();
            ShopServer.startService();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
