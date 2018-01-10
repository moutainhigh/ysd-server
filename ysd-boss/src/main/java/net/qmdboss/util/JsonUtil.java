package net.qmdboss.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * Util - JSON
 * http://www.shopxx.net
 * 
 * Copyright © 2012 shopxx.net All Rights Reserved.
 * License: http://www.shopxx.net/license
 * 
 * Revision: 2.1
 * Date: 2012-01
 */

public class JsonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 将对象转换为JSON字符串
	 * @param object 对象
	 */
	public static String toJson(Object object) {
		Assert.notNull(object);
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将对象转换为JSON流
	 * @param response HttpServletResponse
	 * @param contentType contentType
	 * @param object 对象
	 */
	public static void toJson(HttpServletResponse response, String contentType, Object value) {
		Assert.notNull(response);
		Assert.notNull(contentType);
		Assert.notNull(value);
		try {
			response.setContentType(contentType);
			mapper.writeValue(response.getWriter(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将对象转换为JSON流
	 * @param response HttpServletResponse
	 * @param object 对象
	 */
	public static void toJson(HttpServletResponse response, Object value) {
		Assert.notNull(response);
		Assert.notNull(value);
		try {
			mapper.writeValue(response.getWriter(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将JSON字符串转换为对象
	 * @param json JSON字符串
	 * @param valueType 对象类型
	 */
	public static <T> T toObject(String json, Class<T> valueType) {
		Assert.notNull(json);
		Assert.notNull(valueType);
		try {
			return mapper.readValue(json, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将JSON字符串转换为对象
	 * @param json JSON字符串
	 * @param typeReference 对象类型
	 */
	public static <T> T toObject(String json, TypeReference<?> typeReference) {
		Assert.notNull(json);
		Assert.notNull(typeReference);
		try {
			return mapper.readValue(json, typeReference);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将JSON字符串转换为对象
	 * @param json JSON字符串
	 * @param javaType 对象类型
	 */
	public static <T> T toObject(String json, JavaType javaType) {
		Assert.notNull(json);
		Assert.notNull(javaType);
		try {
			return mapper.readValue(json, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/** 
     * 功能描述:传入任意一个 javabean 对象生成一个指定规格的字符串 
     * 
     * @param bean 
     *             bean对象 
     * @return String 
     */    
   public static String beanToJson(Object bean) {     
        StringBuilder json = new StringBuilder();     
        json.append("{");     
        PropertyDescriptor[] props = null;     
       try {     
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)     
                    .getPropertyDescriptors();     
        } catch (IntrospectionException e) {     
        }     
       if (props != null) {     
           for (int i = 0; i < props.length; i++) {     
               try {    
                    String name = objectToJson(props[i].getName());     
                    String value = objectToJson(props[i].getReadMethod().invoke(bean));    
                    json.append(name);     
                    json.append(":");     
                    json.append(value);     
                    json.append(",");    
                } catch (Exception e) {     
                }     
            }     
            json.setCharAt(json.length() - 1, '}');     
        } else {     
            json.append("}");     
        }     
       return json.toString();     
    }  
   
   /** 
    * @param object 
    *             任意对象 
    * @return java.lang.String 
    */    
  public static String objectToJson(Object object) {     
       StringBuilder json = new StringBuilder();     
      if (object == null) {     
           json.append("\"\"");     
       } else if (object instanceof String || object instanceof Integer) {   
           json.append("\"").append(object.toString()).append("\"");    
       } else {     
           json.append(beanToJson(object));     
       }     
      return json.toString();     
   }     
  /** 
   * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串 
   * 
   * @param list 
   *             列表对象 
   * @return java.lang.String 
   */    
 public static String listToJson(List<?> list) {     
      StringBuilder json = new StringBuilder();     
      json.append("[");     
     if (list != null && list.size() > 0) {     
         for (Object obj : list) {     
              json.append(objectToJson(obj));     
              json.append(",");     
          }     
          json.setCharAt(json.length() - 1, ']');     
      } else {     
          json.append("]");     
      }     
     return json.toString();     
  }  

}