package shop.server;

import shop.log.LoggerTool;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;
import java.util.LinkedList;
import java.util.List;

public class ShopServer {

    private String moduleName;
    private static Server jettyServer;

    private static List<Handler> handlers;
    private ShopServer(String moduleName) {
        this.moduleName = moduleName;
    }

    public static ShopServer getServerInstance(String moduleName) throws Exception{
        if(jettyServer == null) {
            LoggerTool.getDefaultLogger().error("服务器异未正常初始化！");
            throw  new Exception("服务器未初始化错误");
        }   else {
            return new ShopServer(moduleName);
        }
    }
    /**
     * 初始化服务器
     */
    public static void initServer() {
        if(jettyServer != null) {
            LoggerTool.getDefaultLogger().error("服务器已经初始化！");
        }   else {
            jettyServer = new Server(8080);
            handlers = new LinkedList<Handler>();
        }

    }
    /**
     * 
     * @param prefix URL前缀
     * @param servlet 当前路径下包含的Servlet
     */
    public void registServlet(String prefix, Servlet servlet) {
        String contexPath = "/" + prefix + "/" + moduleName;
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath(contexPath);
        handler.addServlet(new ServletHolder(servlet),"/*");
        handlers.add(handler);
    }
    /**
     * 初始化上下文，开启服务（加载完各模块之后开启）
     * @throws Exception
     */
    public static void startService() throws Exception{
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(handlers.toArray(new Handler[0]));
        jettyServer.setHandler(contexts);
        jettyServer.start();
    }
}
