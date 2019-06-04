package yj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SQL语句工具类
 * @author yangj
 *2019年5月9日
 */
@SuppressWarnings("rawtypes")
public class SQLUtil {

	/**
	 * 构建查询语句
	 * yangj 2019年5月9日
	 */
	public void getSelectSql(Map map) {
		
	}
	
	/**
	 * 构建删除WhereSql语句
	 * 参数规则: _type_XXX代表参数的类型 _sign_代表参数使用的符号
	 * yangj 2019年5月31日
	 */
	public  SQLNode getDeleteSql(Map paramMap,List<String> listExcludeKey,String tableName) {
		SQLNode node=new SQLNode();
		//1.非空校验
		if(listExcludeKey==null) listExcludeKey=new ArrayList<String>();
		if(paramMap==null) return node;
		//对于参数个数为0的map,不允许构造出删除整张表的sql语句,所以直接返回一个内容为null的node
		if(paramMap.size()==0) return node;
		//where语句对应的参数
		List<Object> listParam=new ArrayList<Object>();
		//where语句
		StringBuilder sbWhere=new StringBuilder();
		//2.遍历map
		Set keySet=paramMap.keySet();
		for (Object key : keySet) {
			if(key instanceof String) {
				if(!listExcludeKey.contains(key)) {
					String mapKey=(String)key;//key值
					if(mapKey.startsWith("_type_")) continue;//如果是_type_开头则不参与构建
					if(mapKey.startsWith("_sign_")) continue;//如果是_sign_开头则不参与构建
					String paramName=mapKey;//参数名字
					String paramValue=(String)paramMap.get(paramName);//参数的对应的值
					String paramType=(String)paramMap.get("_type_"+mapKey);//参数对应的类型
					String paramSign=(String)paramMap.get("_sign_"+mapKey);//参数对应的符号
					
					ParamNode paramNode=new ParamNode(paramName, paramSign);
					//where语句的一部分
					String result=paramNode.getResult();
					//参数的值
					Object paramValueObject=getParamValueByParamType(paramValue, paramType);
					sbWhere.append(result+" and ");
					listParam.add(paramValueObject);
				}
			}
		}
		//3.处理sbWhere
		String where=dealString(sbWhere.toString());
		if(where.trim().length()!=0) {
			node.setSql("delete from "+ tableName+"  "+where);
		}else {
			node.setSql(where);
		}
		node.setSql("delete from "+ tableName+"  "+where);
		node.setParams(listParam.toArray());
		return node;
	}
	
	
	/**
	 * 根据参数类型paramType将paramValue转换成对应的类型的值
	 * yangj 2019年6月2日
	 */
	private Object getParamValueByParamType(String paramValue,String paramType) {
		if(paramType.equalsIgnoreCase("Integer")) {
			return Integer.parseInt(paramValue);
		}
		if(paramType.equalsIgnoreCase("String")) {
			return paramValue;
		}
		if(paramType.equalsIgnoreCase("Float")){
			return Float.parseFloat(paramValue);
		}
		if(paramType.equalsIgnoreCase("Double")){
			return Double.parseDouble(paramValue);
		}
		return null;
	}
	
	private String dealString(String sbWhere){
		sbWhere=sbWhere.trim();
		if(sbWhere.length()==0){
			return sbWhere;
		}
		if(sbWhere.lastIndexOf("and")!=-1){
			sbWhere=sbWhere.substring(0,sbWhere.lastIndexOf("and"));
			sbWhere=" where "+sbWhere;
		}
		return sbWhere;
	}
	
	
	/**
	 * SQL节点 
	 * @author yangj
	 *2019年5月9日
	 */
	public class SQLNode{
		
		private String sql="";//带有占位符的sql
		private Object[] params=new Object[0];//占位符对应的值数组
		
		public String getSql() {
			return sql;
		}
		public void setSql(String sql) {
			this.sql = sql;
		}
		public Object[] getParams() {
			return params;
		}
		public void setParams(Object[] params) {
			this.params = params;
		}
	}
	
	/**
	 * 参数节点
	 * @author yangj
	 *2019年5月31日
	 */
	class ParamNode{
		/**
		 * 参数名字 如:userName
		 */
		private String paramName;
		/**
		 * 参数对应的符号:如 equal (=) 、 less(<) 等
		 */
		private String paramSign;
		
		public ParamNode(String paramName, String paramSign) {
			super();
			this.paramName = " "+paramName+" ";
			this.paramSign = " "+paramSign+" ";
		}
		
		/**
		 * 返回构建结果: 如 userName like ? 、 beginDate > ?等
		 * yangj 2019年5月31日
		 */
		public String getResult() {
			return paramName + getSqlParamSign(paramSign) + getSqlPlaceHolder(paramSign);
		}
		
		//根据用户传入的paramSign转化为Sql对应的符号
		private String getSqlParamSign(String paramSign) {
			paramSign=paramSign.trim();
			if(paramSign.equalsIgnoreCase("like")) return " like ";
			if(paramSign.equalsIgnoreCase("equal")) return " = ";
			if(paramSign.equalsIgnoreCase("greater")) return " > ";
			if(paramSign.equalsIgnoreCase("greaterAndEqual")) return " >= ";
			if(paramSign.equalsIgnoreCase("less")) return " < ";
			if(paramSign.equalsIgnoreCase("lessAndEqual")) return " <= ";
			if(paramSign.equalsIgnoreCase("notEqual")) return "!=";
			return " = ";
		}
		
		//根据传入的符号获取对应的占位符
		private String getSqlPlaceHolder(String paramSign) {
			if(paramSign.equals("like")) return " %?% ";
			if(paramSign.equals("equal")) return " ? ";
			return " ? ";
		}
	}
}
