package com.qmd.action.payment;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yujian on 2017/9/18.
 */
public abstract class NotifyAction {

    public static void printInfo(Logger log, HttpServletRequest request ){


        log.info("请求地址:" + request.getRequestURL().toString());
        Iterator<?> iter = request.getParameterMap().keySet().iterator();
        try{
            while (iter.hasNext()) {
                String key = (String) iter.next();
                String value = request.getParameter(key);

                value  = URLDecoder.decode(value,"GBK");

                log.info("key:"+key+",value:"+value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("参数打印结束");
    }

    public Map<String,String> getRequestMap(HttpServletRequest request){

        Map<String,String> map = new HashMap<String,String>();
        Iterator<?> iter = request.getParameterMap().keySet().iterator();
        try{
            while (iter.hasNext()) {
                String key = (String) iter.next();
                String value = request.getParameter(key);
                value  = URLDecoder.decode(value,"GBK");
                map.put(key,value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

}


