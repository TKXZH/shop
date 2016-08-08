package shop.publicservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shop.db.Init;
import shop.json.SendJsonResponse;
import shop.server.MyAnnotation;

public class ServiceHttpApi {

    @MyAnnotation
    public void getProducts(HttpServletRequest req, HttpServletResponse resp) throws SQLException,ClassNotFoundException,IOException{
        Map<String,ArrayList<Map<String,String>>> map = Init.getProducts();
        SendJsonResponse.parseMap2Json(map, resp);
    }
    @MyAnnotation
    public void getProductsInformationByProductID(HttpServletRequest req, HttpServletResponse resp) throws SQLException,ClassNotFoundException,IOException{
        String productID = req.getParameter("productID");
        Map<String,String> map;
        map = Init.getProductInformation(productID);
        SendJsonResponse.parseMap2Json(map, resp);    
    }
}