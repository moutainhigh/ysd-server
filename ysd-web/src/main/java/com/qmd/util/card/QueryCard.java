package com.qmd.util.card;

import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class QueryCard {

	public static String wstester(String param) {
		String msg="";
		long start = System.currentTimeMillis();
		try {
			System.out.println("======start===thread========");

			JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
			svr.setServiceClass(QueryValidatorServices.class);
			// http://211.147.7.47/zcxy/services/QueryValidatorServices?wsdl   真实地址
			// http://211.147.7.46/zcxy/services/QueryValidatorServices?wsdl 测试地址
			svr.setAddress("http://211.147.7.47/zcxy/services/QueryValidatorServices?wsdl"); // 这个地址有可能调整，请根据实际需要配置
			QueryValidatorServices service = (QueryValidatorServices) svr.create();
//			String userName = "zjwjactext";// 用户名
//			String password = "zjwjac@20150806";// 密码  zjwac密码:zjwac@20150828
			String userName = "zjwac";// 用户名
			String password = "zjwac@20150828";
			System.setProperty("javax.net.ssl.trustStore", "");
			String resultXML = "";
			String datasource = "1A020202";// 

			String encodeUserName = "", encodePassword = "", encodeParam = "", encodeDatasource = "";

			encodeUserName = QueryThread.encode(QueryThread.ENCODE_KEY, userName.getBytes("UTF-8")).toString();
			encodePassword = QueryThread.encode(QueryThread.ENCODE_KEY, password.getBytes("UTF-8")).toString();
			encodeDatasource = QueryThread.encode(QueryThread.ENCODE_KEY, datasource.getBytes("UTF-8")).toString();
			encodeParam = QueryThread.encode(QueryThread.ENCODE_KEY, param.getBytes("GBK")).toString();

			int queryCount = 1;

			for (int i = 0; i < queryCount; i++) {
				// 单条
				resultXML = service.querySingle(encodeUserName, encodePassword, encodeDatasource, encodeParam);

				System.out.println("解密前：" + resultXML);

				msg = new String(QueryThread.decode(QueryThread.ENCODE_KEY, Base64.decodeBase64(resultXML.getBytes())), "GBK");
				System.out.println("解密后：" + msg);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();

		System.out.println("thread-time:" + (end - start));

		return msg;
	}
	
	public static Map<String, String> getParseMapFromXMLStream(Document doc,String parentPath) throws DocumentException {  
		  
        List rowList = doc.selectNodes(parentPath);  
        Map<String, String> map = null;  
        if(rowList != null && rowList.size() >0) {  
            for(Iterator iter = rowList.iterator();iter.hasNext();){  
                map = new HashMap();  
                //获得具体的节点的父元素     
                Element element = (Element)iter.next();
                //获得父节点内的各种借点或者属性  
                Iterator it1 = element.elementIterator();  
                while(it1.hasNext()) {  
                    Element element1 = (Element)it1.next();    
                    //获得子节点的所有列表    
                    map.put(element1.getName(), element1.getText());  
                }  
            }  
        }     
        return map;  
    }  
	
	
	
	
}
