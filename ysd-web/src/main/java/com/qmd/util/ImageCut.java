package com.qmd.util;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;

public class ImageCut {

	/**
	 * 图像切割
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param x
	 *            目标切片起点x坐标
	 * @param y
	 *            目标切片起点y坐标
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度 //
	 */
	// public static String abscut(String srcImageFile, int x, int y,
	// int destWidth, int destHeight) {
	// String _src = "";
	// try {
	// Image img;
	// String s = srcImageFile;
	// ImageFilter cropFilter;
	// // 读取源图像
	// BufferedImage bi = ImageIO.read(new File(srcImageFile));
	// int srcWidth = bi.getWidth(); // 源图宽度
	// int srcHeight = bi.getHeight(); // 源图高度
	// if (srcWidth >= destWidth && srcHeight >= destHeight) {
	// Image image = bi.getScaledInstance(srcWidth, srcHeight,
	// Image.SCALE_DEFAULT);
	//
	// // 四个参数分别为图像起点坐标和宽高
	// // 即: CropImageFilter(int x,int y,int width,int height)
	// cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
	// img = Toolkit.getDefaultToolkit().createImage(
	// new FilteredImageSource(image.getSource(), cropFilter));
	// BufferedImage tag = new BufferedImage(destWidth, destHeight,
	// BufferedImage.TYPE_INT_RGB);
	// Graphics g1 = tag.getGraphics();
	// Graphics2D g=(Graphics2D)g1;
	// g.setRenderingHint(RenderingHints.KEY_INTERPOLATION
	// ,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	//
	// g.drawImage(img, 0, 0, null); // 绘制剪切后的图
	// g.dispose();
	// // 输出为文件
	// String[] srcs = null;
	// try {
	// srcs = s.split("\\\\");
	// } catch (RuntimeException e) {
	// // TODO kaba-generated catch block
	// e.printStackTrace();
	// }
	//
	// for (int i = 0; i < srcs.length; i++) {//连接拆分后的URL 重新得到一个URL
	// if (i == srcs.length - 1) {
	// srcs[i] = srcs[i].split("\\.")[0] + "_m."
	// + srcs[i].split("\\.")[1];
	// _src = _src + srcs[i];
	// } else
	// _src = _src + srcs[i] + "\\";
	// }
	// ImageIO.write(tag, "png", new File(_src));
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return _src;
	// }

	public static String abscut(ServletContext servletContext,
			String srcImageFile, int x, int y, int destWidth, int destHeight) {

		// String _src = "";
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像IMG_DIR
//			String temp = servletContext.getRealPath("/") + srcImageFile;
//			String temp = ImageUtil.IMG_DIR + srcImageFile;
//			BufferedImage bi = ImageIO.read(new File(servletContext
//					.getRealPath("/") + srcImageFile));
			BufferedImage bi = ImageIO.read(new File(ImageUtil.IMG_DIR + srcImageFile));
			
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度
			if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);

				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(destWidth, destHeight,
						BufferedImage.TYPE_INT_RGB);
				Graphics g1 = tag.getGraphics();
				Graphics2D g = (Graphics2D) g1;
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);

				g.drawImage(img, 0, 0, null); // 绘制剪切后的图
				g.dispose();
				// 输出为文件
				// String[] srcs = null;
				// try {
				// srcs = s.split("\\\\");
				// } catch (RuntimeException e) {
				// // TODO kaba-generated catch block
				// e.printStackTrace();
				// }

				// for (int i = 0; i < srcs.length; i++) {//连接拆分后的URL 重新得到一个URL
				// if (i == srcs.length - 1) {
				// srcs[i] = srcs[i].split("\\.")[0] + "_m."
				// + srcs[i].split("\\.")[1];
				// _src = _src + srcs[i];
				// } else
				// _src = _src + srcs[i] + "\\";
				// }
				System.out.println("1="+servletContext.getRealPath("/") + srcImageFile);
				System.out.println("2="+ImageUtil.IMG_DIR + srcImageFile);
				ImageIO.write(
						tag,
						"png",
//						new File(servletContext.getRealPath("/") + srcImageFile));
						new File(ImageUtil.IMG_DIR+srcImageFile));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srcImageFile;
	}

	/** */
	/** */
	/** */
	/**
	 * @param args
	 */
}
