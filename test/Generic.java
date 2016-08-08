package shop.test;

import java.util.ArrayList;
import java.util.List;


public class Generic {
	public static void main(String args[]) {
		List<? super Number> list=new ArrayList<Number>();
        list.add(4.0);//编译错误
        list.add(3);//编译错误
        Object object  = list.get(1);
	}
}
