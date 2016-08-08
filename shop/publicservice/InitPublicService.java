package shop.publicservice;

import shop.server.ShopServer;
import shop.server.URLRouter;

public class InitPublicService {
    public static void initPublicService() throws Exception{
        ShopServer.getServerInstance("service").registServlet("api",new URLRouter(new ServiceHttpApi()));
    }
}
