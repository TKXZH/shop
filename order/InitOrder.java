package shop.order;


import shop.event.EventHub;
import shop.eventImp.OrderCanceledByCreatorEvent;
import shop.eventImp.OrderCanceledByCreatorListener;
import shop.server.ShopServer;
import shop.server.URLRouter;


public class InitOrder {
    public static void initOrderRelated() throws Exception{
        ShopServer.getServerInstance("order").registServlet("api",new URLRouter(new OrderHttpApi()));
        /*注册用户订单被取消监听器*/
        EventHub.getInstence().registListener(OrderCanceledByCreatorEvent.Type.Order_Canceled_By_Creator, new OrderCanceledByCreatorListener());
    }
}
