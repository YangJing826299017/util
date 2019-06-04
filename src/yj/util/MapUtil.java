package yj.util;

import java.util.Map;

/**
 * Map工具类
 * @author yangj
 *2019年5月31日
 */
public class MapUtil {

	/**
	 * 获取Map中的key值
	 * yangj 2019年5月31日
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(Map map,String key,T valueClass) {
		//如果不存在则返回空
		if(map.get(key)==null) return null;
		return (T)map.get(key);
	}
}
