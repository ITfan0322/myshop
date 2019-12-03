package com.xbboomOrder.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CollectionUtil {

	private CollectionUtil() {
		super();
	}

	// 闁告帇鍊栭弻鍥ㄧ▔閿熻姤绋夐鍫熻偁闁告艾鐗婂Σ鎼佸触閿旇儻绀嬬紒宀嬫嫹
	public static <T> boolean isEmpty(Collection<T> col) {
		if (col == null || col.isEmpty()) {
			return true;
		}

		return false;
	}

	// 闁告帇鍊栭弻鍥ㄧ▔閿熻姤绋夐鍫熻偁闁告艾鐗婂Σ鎼佸触閿旇法鐟濆☉鎾规閳癸拷
	public static <T> boolean isNotEmpty(Collection<T> col) {
		return !isEmpty(col);
	}

	// 闁告帇鍊栭弻鍢檃p闁哄嫷鍨伴幆浣圭▔閾忓厜鏁�
	public static <K, V> boolean isEmpty(Map<K, V> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}

		return false;
	}

	// 闁告帇鍊栭弻鍢檃p闁哄嫷鍨伴幆浣圭▔瀹ュ嫯绀嬬紒宀冩〃鐠愮喓绮氶敓锟�
	public static <K, V> boolean isNotEmpty(Map<K, V> map) {
		return !isEmpty(map);
	}

	// 闁告ê顭峰▍宸恑st濞戞搩鍘惧▓鎴︽煂瀹ュ拋妲婚柡浣哄瀹擄拷
	public static <T> List<T> removeRepeat(List<T> list) {
		if (isEmpty(list)) {
			return list;
		}

		List<T> result = new ArrayList<T>();
		for (T e : list) {
			if (!result.contains(e)) {
				result.add(e);
			}
		}

		return result;
	}

	// 閻忓繐妫濆▔锕傚触閸絾绁柟璇℃線鐠愮兓tring闁轰焦澹嗙划锟�
	public static <T> String[] toArray(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}

		String[] result = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = String.valueOf(list.get(i));
		}

		return result;
	}

}
