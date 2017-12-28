package com.qmd.tool.mysql;

import com.qmd.tool.MysqlMain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class MysqlUtilTable2Dao {
	
	public static void printDao(TablesBean tableBean) {
		
		String fileName = MysqlMain.save_path +"/" + tableBean.getSpaceName() + "Dao.java";

		try {

			String content = "package " + MysqlMain.package_name + ";\n";
			
			content += "import com.qmd.dao.BaseDao;"+ "\n";
			
			content += "public interface " + tableBean.getSpaceName() + "Dao extends BaseDao<"+tableBean.getSpaceName()+",Integer> {\n" ;
			content += "}";

			FileOutputStream fos = new FileOutputStream(fileName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(content);
			out.close();
			fos.close();

			System.out.println("==="+tableBean.getSpaceName() + "Dao.java"+"生成");
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			// FileWriter writer = new FileWriter(fileName, false);
			// writer.write(content);
			// writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void printDaoImpl(TablesBean tableBean) {
		
		String fileName = MysqlMain.save_path +"/" + tableBean.getSpaceName() + "DaoImpl.java";

		try {

			String content = "package " + MysqlMain.package_name + ";\n";
			
			//content += "import java.util.List;"+ "\n";
			content += "import org.springframework.stereotype.Repository;"+ "\n";
			content += "import com.qmd.dao.impl.BaseDaoImpl;\n";
			//content += "import "+MysqlMain.package_name+"."+tableBean.getSpaceName()+";"+ "\n";
			
			content += "@Repository(\""+tableBean.getJavaName()+"Dao\")"+ "\n";
			
			content += "public class " + tableBean.getSpaceName() + "DaoImpl extends BaseDaoImpl<"+tableBean.getSpaceName()+",Integer> implements "+tableBean.getSpaceName()+"Dao {\n" ;
			content += "}";

			FileOutputStream fos = new FileOutputStream(fileName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(content);
			out.close();
			fos.close();

			System.out.println("==="+tableBean.getSpaceName() + "DaoImpl.java"+"生成");
			
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			// FileWriter writer = new FileWriter(fileName, false);
			// writer.write(content);
			// writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
