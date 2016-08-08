package shop.team;

import shop.server.ShopServer;
import shop.server.URLRouter;

public class InitTeam {
	   public static void initTeamRelated() throws Exception{
	        ShopServer.getServerInstance("team").registServlet("api",new URLRouter(new TeamHttpApi()));
	    }	
}
