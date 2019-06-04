package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import yj.util.SQLUtil;
import yj.util.SQLUtil.SQLNode;

@SuppressWarnings({"unchecked","rawtypes"})
public class Test {

	public static void main(String[] args) {
		Map map=new HashMap();
		// string =
		map.put("userName","张三");
		map.put("_type_userName","string");
		map.put("_sign_userName","equal");
		
		//string like
		map.put("phone","131");
		map.put("_type_phone","string");
		map.put("_sign_phone", "like");
		
		//int >
		map.put("age","18");
		map.put("_type_age","integer");
		map.put("_sign_age", "less");
		
		//weight float
		map.put("weight","60");
		map.put("_type_weight","float");
		map.put("_sign_weight", "greaterAndEqual");

		//money double
        map.put("money","8000.0");
        map.put("_type_money","double");
        map.put("_sign_money","lessAndEqual");

		SQLUtil sqlUtil=new SQLUtil();
		SQLNode node=sqlUtil.getDeleteSql(map,null,"User");
		
		System.out.println(node.getSql());
		System.out.println(Arrays.asList(node.getParams()));
	}

}
