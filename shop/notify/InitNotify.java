package shop.notify;

import shop.server.ShopServer;
import shop.server.URLRouter;
import shop.timer.TimerControl;

public class InitNotify {
    public static void initNotify() throws Exception{
        ShopServer.getServerInstance("notify").registServlet("api",new URLRouter(new NotifyHttpApi()));
        new TimerControl();//开启定时提醒任务
    }
}
