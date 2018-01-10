package com.qmd.tool.mysql;

import com.qmd.tool.MysqlMain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class MysqlUtilTable2Service {
	
	public static void printService(TablesBean tableBean) {
		
		String fileName = MysqlMain.save_path +"/" + tableBean.getSpaceName() + "Service.java";

		try {

			String content = "package " + MysqlMain.package_name + ";\n";
			
			content +="import com.qmd.service.BaseService;\n";
			content += "public interface " + tableBean.getSpaceName() + "Service extends BaseService<"+tableBean.getSpaceName()+",Integer> {\n" ;
			content += "}";

			FileOutputStream fos = new FileOutputStream(fileName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(content);
			out.close();
			fos.close();

			System.out.println("==="+tableBean.getSpaceName() + "Service.java"+"生成");
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			// FileWriter writer = new FileWriter(fileName, false);
			// writer.write(content);
			// writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void printServiceImpl(TablesBean tableBean) {
		
		String fileName = MysqlMain.save_path +"/" + tableBean.getSpaceName() + "ServiceImpl.java";

		try {

			String content = "package " + MysqlMain.package_name + ";\n";
			
			content += "import javax.annotation.Resource;"+ "\n";
			content += "import org.springframework.stereotype.Service;"+ "\n";
			content += "import com.qmd.service.impl.BaseServiceImpl;"+ "\n";
			
			
			//content += "import "+MysqlMain.package_name+".dao."+tableBean.getSpaceName()+"Dao;"+ "\n";
			//content += "import "+MysqlMain.package_name+".model."+tableBean.getSpaceName()+";"+ "\n";
			//content += "import "+MysqlMain.package_name+".service."+tableBean.getSpaceName()+"Service;"+ "\n";
			
			content += "@Service(\""+tableBean.getJavaName()+"Service\")"+ "\n";
			
			content += "public class " + tableBean.getSpaceName() + "ServiceImpl extends BaseServiceImpl<"+tableBean.getSpaceName()+",Integer> implements "+tableBean.getSpaceName()+"Service {\n" ;
			
			content += "@SuppressWarnings(\"unused\")"+ "\n";
			content += "@Resource"+ "\n";
			content += "private " + tableBean.getSpaceName() + "Dao " + tableBean.getJavaName() + "Dao;"+ "\n";
			
			content += "@Resource"+ "\n";
			content += "public void setBaseDao(" + tableBean.getSpaceName() + "Dao " + tableBean.getJavaName() + "Dao) {"+ "\n";
				content += "	super.setBaseDao(" + tableBean.getJavaName() + "Dao);"+ "\n";
				content += "}"+ "\n";
			
			content += "}"+ "\n";

			FileOutputStream fos = new FileOutputStream(fileName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(content);
			out.close();
			fos.close();

			System.out.println("==="+tableBean.getSpaceName() + "ServiceImpl.java"+"生成");
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			// FileWriter writer = new FileWriter(fileName, false);
			// writer.write(content);
			// writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
