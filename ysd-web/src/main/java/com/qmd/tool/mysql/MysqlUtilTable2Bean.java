package com.qmd.tool.mysql;

import com.qmd.tool.MysqlMain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class MysqlUtilTable2Bean {
	/**
	 * 打印entity的信息
	 * 
	 * @param list
	 */
	public static void printEntity(TablesBean tableBean) {
		
		boolean hasDate = false;
		
		List<FieldBean> list = tableBean.getFieldList();

		StringBuffer bf = new StringBuffer();

		// 定义声明
		for (FieldBean tb : list) {
			String temp = "";
			temp += "//" + tb.getComment() + "\n";
			temp += "private " + tb.getJavaType() + " " + tb.getJavaCode()
					+ ";";

			// System.out.println(temp);
			bf.append(temp).append("\n");
			
			
			if (!hasDate&&"Date".equals(tb.getJavaType())) {
				hasDate = true;
			}
		}

		// 定义get set方法
		for (FieldBean tb : list) {
			String temp = "";
			temp += "/**\n";
			temp += " * " + tb.getComment() + " 的取得\n";
			temp += " * @return\n";
			temp += " */\n";
			temp += "public " + tb.getJavaType() + " "
					+ tb.getJavaCodeForGet() + "() {\n";
			temp += "    return " + tb.getJavaCode() + ";\n";
			temp += "}";
			// System.out.println(temp);

			bf.append(temp).append("\n");

			temp = "";
			temp += "/**\n";
			temp += " * " + tb.getComment() + " 的设置\n";
			temp += " * @param " + tb.getJavaCode() + "\n";
			temp += " */\n";
			temp += "public void " + tb.getJavaCodeForSet() + "("
					+ tb.getJavaType() + " " + tb.getJavaCode() + ") {\n";
			temp += "    this." + tb.getJavaCode() + " = " + tb.getJavaCode()
					+ ";\n";
			temp += "}\n";
			// System.out.println(temp);
			bf.append(temp).append("\n");

		}

		String fileName = MysqlMain.save_path +"/" + tableBean.getSpaceName() + ".java";

		try {

			String content = "package " + MysqlMain.package_name + ";\n";
			if (hasDate) {
				content += "import java.util.Date;"+ "\n";
			}
			//content +="import com.web.model.ModelBase;"+ "\n";
			
			content +="import java.io.Serializable;"+ "\n";
			content += "public class " + tableBean.getSpaceName() + " implements Serializable {\n" + bf.toString();
			content += "}";

			FileOutputStream fos = new FileOutputStream(fileName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(content);
			out.close();
			fos.close();

			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			// FileWriter writer = new FileWriter(fileName, false);
			// writer.write(content);
			// writer.close();
			System.out.println("==="+tableBean.getSpaceName() + ".java"+"生成");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
