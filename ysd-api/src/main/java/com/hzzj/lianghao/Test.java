package com.hzzj.lianghao;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();
		Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });
		process.getOutputStream().close();
		Scanner sc = new Scanner(process.getInputStream());
		String property = sc.next();
		String serial = sc.next();
		System.out.println(property + "cpu: " + serial);
		System.out.println("time:" + (System.currentTimeMillis() - start));


 		//获取硬盘序列号
 		System.out.println("硬盘序列号："+getSerialNumber("C"));
 		//获取主板SN
 		System.out.println("主板 SN："+getMotherboardSN());
		//获取本地InetAddress
		InetAddress ia = InetAddress.getLocalHost();
 		System.out.println(ia);
 		getLocalMac(ia);
	}

	// =======================获取硬盘序列号==========================
	public static String getSerialNumber(String drive) {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n"
					+ "Set objDrive = colDrives.item(\""
					+ drive
					+ "\")\n"
					+ "Wscript.Echo objDrive.SerialNumber"; // see note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}

	public static String getMotherboardSN() {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
					+ "Set colItems = objWMIService.ExecQuery _ \n"
					+ "   (\"Select * from Win32_BaseBoard\") \n"
					+ "For Each objItem in colItems \n"
					+ "    Wscript.Echo objItem.SerialNumber \n"
					+ "    exit for  ' do the first cpu only! \n" + "Next \n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}
	
	
	private static void getLocalMac(InetAddress ia) throws SocketException {
 		// TODO Auto-generated method stub
 		//获取网卡，获取地址
 		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
 		System.out.println("mac数组长度："+mac.length);
 		StringBuffer sb = new StringBuffer("");
 		for(int i=0; i<mac.length; i++) {
 			if(i!=0) {
 				sb.append("-");
 			}
 			//字节转换为整数
 			int temp = mac[i]&0xff;
 			String str = Integer.toHexString(temp);
 			System.out.println("每8位:"+str);
 			if(str.length()==1) {
 				sb.append("0"+str);
 			}else {
 				sb.append(str);
 			}
 		}
 		System.out.println("本机MAC地址:"+sb.toString().toUpperCase());
 	}


}
