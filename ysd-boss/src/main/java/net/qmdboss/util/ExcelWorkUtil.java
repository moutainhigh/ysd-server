package net.qmdboss.util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelWorkUtil {
	
	
	
	
	public static List getDateId(File f) {
		List l=new ArrayList();
	//	HashSet<String> l = new HashSet<String>();
		Sheet sheet;  
		Workbook book;   
		String key="userid";
		int keynum=0;//第几列
		Cell cell;   

						try {        //t.xls为要读取的excel文件名      
							book= Workbook.getWorkbook(f);      //"E:23.xls"        
							//获得第一个工作表对象
							sheet=book.getSheet(0); 
							
							//获取左上角的单元格
							
							for (int i = 0; i < sheet.getColumns(); i++) {//共有多少列
								cell = sheet.getCell(i, 0);
								if(key.equals(cell.getContents())){
									keynum=i;
								}
				            }
							
							
							for (int j = 1; j < sheet.getRows(); j++) {
						        cell = sheet.getCell(keynum, j);//从第二行开始导数据
						       if(StringUtils.isNotEmpty(cell.getContents())){
						    	   l.add(cell.getContents());
						       }
						   }
							
							
				
				            book.close();
						}    
						catch(Exception e) {
							System.out.println(e);
						}  
					
						System.out.println("list==="+l);
			
						//HashSet<String> hs = new HashSet<String>(l);
						//List<String> list = new ArrayList<String>(l);
						
					
		return l;
	}
	
	
	
	
	public static void main(String[] args) {
		List l=new ArrayList();
		l=getDateId(new File("E:2.xls"));
		System.out.println("value==="+l);
		System.out.println("size==="+l.size());
		
	
	//	HashSet<String> hs = new HashSet<String>(l);
	//	List<String> list = new ArrayList<String>(hs);
	
	//	System.out.println("value==="+list);
	//	System.out.println("size111==="+list.size());
		
		
		
	}

}