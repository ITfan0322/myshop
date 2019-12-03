package com.xbboomOrder.utils;

import com.alibaba.fastjson.serializer.SerializerFeature;



/**
 * 閸掓稑缂撻弮鍫曟？閿涳拷2015楠烇拷10閺堬拷23閺冿拷 娑撳宕�5:12:46
 * 
 * @author andy
 * @version 2.2
 */

public class SerializerFeatureUtil {

	public static final SerializerFeature[] FEATURES = {
			SerializerFeature.WriteMapNullValue,
			SerializerFeature.QuoteFieldNames,
			SerializerFeature.WriteNullStringAsEmpty,
			SerializerFeature.WriteNullBooleanAsFalse,
			SerializerFeature.WriteNullListAsEmpty,
			SerializerFeature.WriteNullNumberAsZero,
			SerializerFeature.DisableCircularReferenceDetect,
			SerializerFeature.BeanToArray,
	};
}
