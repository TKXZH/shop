package shop.server;

import shop.json.SendJsonResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/29.
 */
public class URLRouter extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7687026922194323264L;
	private Object apiObject;
    public URLRouter(Object apiObject) {
        this.apiObject = apiObject;
        this.loadApis();
    }
    /*存储本组件的API方法*/
    public Map<String, Method> apis = new HashMap<String,Method>();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        /*取得用户访问的API信息*/
        String path = req.getPathInfo().substring(1);
        /*获取用户的IP地址*/
        Method m = this.getApiMethod(path);
        if(m != null) {
            try {
                m.invoke(apiObject,req,resp);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }	else{
        	Map<String,String> map = new HashMap<String,String>();
            map.put("stat","URL MAPPING ERROR");
            SendJsonResponse.parseMap2Json(map,resp);
            return;
        }

    }
    public void loadApis() {
        Method[] methods = apiObject.getClass().getMethods();
        for(Method m:methods) {
            Annotation apiAnnotation = null;
            for(Annotation a : m.getAnnotations()) {
                if(a.annotationType().equals(MyAnnotation.class)) {
                    apiAnnotation = a;
                    break;
                }
            }
            if (apiAnnotation == null) {
                continue;
            }
            System.out.println("添加了一个api");
            apis.put(m.getName(),m);
        }
    }
    private Method getApiMethod(String apiName) {
        return apis.get(apiName);
    }

}
