package com.xbboomOrder.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLUtil {
	 public static Map doXMLParse(String strxml) throws JDOMException, IOException {  
	        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");  
	  
	        if(null == strxml || "".equals(strxml)) {  
	            return null;  
	        }  
	          
	        Map m = new HashMap();  
	          
	        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));  
	        SAXBuilder builder = new SAXBuilder();  
	        Document doc = builder.build(in);  
	        Element root = doc.getRootElement();  
	        List list = root.getChildren();  
	        Iterator it = list.iterator();  
	        while(it.hasNext()) {  
	            Element e = (Element) it.next();  
	            String k = e.getName();  
	            String v = "";  
	            List children = e.getChildren();  
	            if(children.isEmpty()) {  
	                v = e.getTextNormalize();  
	            } else {  
	                v = XMLUtil.getChildrenText(children);  
	            }  
	              
	            m.put(k, v);  
	        }  
	          
	        //关闭�?  
	        in.close();  
	          
	        return m;  
	    }  
	      
	    /** 
	     * 获取子结点的xml 
	     * @param children 
	     * @return String 
	     */  
	    public static String getChildrenText(List children) {  
	        StringBuffer sb = new StringBuffer();  
	        if(!children.isEmpty()) {  
	            Iterator it = children.iterator();  
	            while(it.hasNext()) {  
	                Element e = (Element) it.next();  
	                String name = e.getName();  
	                String value = e.getTextNormalize();  
	                List list = e.getChildren();  
	                sb.append("<" + name + ">");  
	                if(!list.isEmpty()) {  
	                    sb.append(XMLUtil.getChildrenText(list));  
	                }  
	                sb.append(value);  
	                sb.append("</" + name + ">");  
	            }  
	        }  
	          
	        return sb.toString();  
	    }  
	    
	   
	    private static final String PREFIX_XML = "<xml>";

		private static final String SUFFIX_XML = "</xml>";

		private static final String PREFIX_CDATA = "<![CDATA[";

		private static final String SUFFIX_CDATA = "]]>";

		/**
		 * 转化成xml, 单层无嵌套
		 * 
		 * @param map
		 * @param isAddCDATA
		 * @return
		 */
		public static String xmlFormat(Map<String, String> parm, boolean isAddCDATA) {

			StringBuffer strbuff = new StringBuffer(PREFIX_XML);
			if (CollectionUtil.isNotEmpty(parm)) {
				for (Entry<String, String> entry : parm.entrySet()) {
					strbuff.append("<").append(entry.getKey()).append(">");
					if (isAddCDATA) {
						strbuff.append(PREFIX_CDATA);
						if (StringUtil.isNotEmpty(entry.getValue())) {
							strbuff.append(entry.getValue());
						}
						strbuff.append(SUFFIX_CDATA);
					} else {
						if (StringUtil.isNotEmpty(entry.getValue())) {
							strbuff.append(entry.getValue());
						}
					}
					strbuff.append("</").append(entry.getKey()).append(">");
				}
			}
			return strbuff.append(SUFFIX_XML).toString();
		}

		/**
		 * 解析xml
		 * 
		 * @param xml
		 * @return
		 * @throws XmlPullParserException
		 * @throws IOException
		 */
		public static Map<String, String> xmlParse(String xml) throws XmlPullParserException, IOException {
			Map<String, String> map = null;
			if (StringUtil.isNotEmpty(xml)) {
				InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
				XmlPullParser pullParser = XmlPullParserFactory.newInstance().newPullParser();
				pullParser.setInput(inputStream, "UTF-8"); // 为xml设置要解析的xml数据
				int eventType = pullParser.getEventType();

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_DOCUMENT:
						map = new HashMap<String, String>();
						break;
					case XmlPullParser.START_TAG:
						String key = pullParser.getName();
						if (key.equals("xml"))
							break;
						String value = pullParser.nextText().trim();
						map.put(key, value);
						break;
					case XmlPullParser.END_TAG:
						break;
					}
					eventType = pullParser.next();
				}
			}
			return map;
		}
}
