package shop.json;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Created by xvzh on 2016/7/29.
 */
public class SendJsonResponse {
    public static void parseMap2Json(Map<?, ?> map, HttpServletResponse resp) throws IOException{
        String data = JSONObject.toJSONString(map);
        resp.setCharacterEncoding("utf-8");
        OutputStreamWriter osw = new OutputStreamWriter(resp.getOutputStream());
        osw.write(data);
        osw.flush();
    }
}
