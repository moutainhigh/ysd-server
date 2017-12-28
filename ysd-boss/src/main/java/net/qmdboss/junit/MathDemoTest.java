package net.qmdboss.junit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MathDemoTest{

	MathDemo demo;
	
	@Test
	public void testAdd(){
		int expected = 2;
		int trueValue = demo.add(1, 1);
		Assert.assertEquals(expected, trueValue);
	}
	
	@Test
	 public void rrr() {  
	        int expected = 10;  
	        int trueValue = demo.div(1, 1);  
//	        assertEquals(expected, trueValue);  
	        Assert.assertFalse(expected==trueValue);
	    } 
	@Test
	 public void testBb(){
		 Assert.assertNotNull(demo.div(2, 1));
	 }
	
	@Before
	public void t1(){
		 System.out.println("setUp......");
		 demo = new MathDemo();
	 }
	 
	@After
	public void t2(){
		 System.out.println("Gone......");
		 demo = null;
	 }
	
	
}
