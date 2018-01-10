package com.qmd.util;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

public class RandomNumLettersPlaceUtil {
	private ByteArrayInputStream image;//图像
	private String str;//验证码
	
	
//	// 图片长
//	private static int WIDTH = 70;
//	// 图片宽
//	private static int HEIGHT = 25;
//	// 数字数量
//	private static int NUM = 4;
//	//干扰线数量
//	private static int LINE = 25;
	
	
	private RandomNumLettersPlaceUtil(){
		init();//初始化属性
	}
	/*
	 * 取得RandomNumUtil实例
	 */
	public static RandomNumLettersPlaceUtil Instance(){
		return new RandomNumLettersPlaceUtil();
	}
	/*
	 * 取得验证码图片
	 */
	public ByteArrayInputStream getImage(){
		return this.image;
	}
	/*
	 * 取得图片的验证码
	 */
	public String getString(){
		return this.str;
	}
	
	private void init() {

		int WIDTH = 630;// 图片长
		int HEIGHT = 140;// 图片宽
		int NUM = 4;// 数字数量
		int LINE = 20;// 干扰线数量
		int SIZE1 = 35; //字体大小（值越小抖动幅度越小）
		int SIZE2 = 70; //字体大小(控制字体大小，SIZE1值越小  SIZE2相对越大)
		int TILT = 2; //字体飘动幅度
		int SPACING = 85;// 字体间距
		int SHAKE = 300; //字符抖动幅度
		

		// 在内存中创建图象
		//int width=65, height=20;
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		Graphics2D g2d = (Graphics2D)g;
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(200,250));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		// 设定字体
		//g.setFont(new Font("Times New Roman",Font.PLAIN,60));

		
		
		// 吧字母和数字保存在数组里

	    char c[] = {'2','3','4','5','6','7','8','9','A', 'B', 'C', 
	    		'D', 'E', 'F', 'G', 'H','J', 'K', 'L', 'M', 'N' , 
	    		'P', 'R', 'S', 'T', 'U', 'V','W', 'X', 'Y', 'Z'};
	
//	    char c[] = new char[62];
//
//	    for (int i = 97, j = 0; i < 123; i++, j++) {
//	        c[j] = (char) i;
//	    }
//	    for (int o = 65, p = 26; o < 91; o++, p++) {
//	        c[p] = (char) o;
//	    }
//	    for (int m = 48, n = 52; m < 58; m++, n++) {
//	        c[n] = (char) m;
//	    }
	    
		// 取随机产生的认证码(6位数字)
		String sRand="";
		// 用于存储随即生成的验证码
		for (int i=0;i<NUM;i++){
			int x = random.nextInt(c.length);
			int h = (int) ((HEIGHT * SIZE1 / 100) * random.nextDouble() + (HEIGHT * SIZE2 / 100));
			String rand = String.valueOf(c[x]);
			g.setFont(new Font("Courier", Font.BOLD | Font.ITALIC, h));//Times New Roman|Courier|Arial
			sRand+=rand;
		    // 将认证码显示到图象中
		    g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
		    // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
		    
		    g2d.rotate((random.nextInt(TILT)-(TILT/2))*Math.PI/180.0, (random.nextInt(SHAKE)-(SHAKE/2)), h);//i * WIDTH / NUM * 200/ 100
		    g.drawString(rand, i * WIDTH / NUM * SPACING / 100, h);
		    
//		    g.drawString(rand,13*i+6,16);
		    this.str=sRand;/*   赋值验证码   */
		}
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		
		for (int i=0;i<LINE;i++)
		{
			g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		int x = random.nextInt(WIDTH);
		 int y = random.nextInt(HEIGHT);
		        int xl = random.nextInt(12);
		        int yl = random.nextInt(12);
		 g.drawLine(x,y,x+xl,y+yl);
		}
		// 图象生效
		g.dispose();
		ByteArrayInputStream input=null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try{
			ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
	        ImageIO.write(image, "JPEG", imageOut);
	        imageOut.close();
	        input = new ByteArrayInputStream(output.toByteArray());
		}catch(Exception e){
			System.out.println("验证码图片产生出现错误："+e.toString());
		}
        
        this.image=input;/*  赋值图像  */
	}
	/*
	 * 给定范围获得随机颜色
	 */
	private Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
   }

}
