package shop.user;

import shop.event.EventHub;
import shop.eventImp.UserDeletedEvent;
import shop.eventImp.UserDeletedListener;
import shop.server.ShopServer;
import shop.server.URLRouter;

/**
 * Created by Administrator on 2016/7/29.
 */
public class InitUserLogin{
    public static void initUserServlet() throws Exception{
        ShopServer.getServerInstance("user").registServlet("api",new URLRouter(new UserHttpApi()));
        /*注册监听器监听用户被删除事件*/
        EventHub.getInstence().registListener(UserDeletedEvent.Type.After_Delete, new UserDeletedListener());
    }
}
