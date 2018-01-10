package com.qmd.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
		@Override  
	    protected void processProperties(ConfigurableListableBeanFactory beanFactory,Properties props)throws BeansException {  
			
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            
            //解密jdbc.password属性值，并重新设置
            //props.setProperty("jdbc.username",ImageDesUtil.mysqlDecrypt(username));
            //props.setProperty("jdbc.password",ImageDesUtil.mysqlDecrypt(password));
            super.processProperties(beanFactory, props); 
			
	    }  
}
