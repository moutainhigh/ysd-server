package com.ysd.ipyy;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * @author xishengchun on 2017-09-06.
 */
public class Log4jTest {

    private Logger logger = Logger.getLogger(Log4jTest.class);

    private org.slf4j.Logger logger2 = LoggerFactory.getLogger(Log4jTest.class);

    @Test
    public void testLevel() {
        logger.debug("1-debug");
        logger.info("2-info");
        logger.warn("3-warn");
        logger.error("4-error");
    }

    @Test
    public void testSlf4j() {
        logger2.error("异常:{}", "23", new Exception());
    }

}
