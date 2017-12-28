package com.qmd.tool.mysql;

import com.qmd.tool.MysqlMain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class MysqlUtilTable2XML {
	
	public static String TAB = "	";
	
	public static void printXMLForMap(TablesBean bean) {
		StringBuffer bf = new StringBuffer();

		bf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
		
		bf.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >").append("\n");
		
		bf.append("<mapper namespace=\"").append(bean.getSpaceName()).append("\">").append("\n");
		bf.append("\n");
		
		// 根据ID取值
		bf.append(TAB).append("<select id=\"getById\" resultType =\"").append(MysqlMain.package_name_model).append(".").append(bean.getSpaceName()).append("\" parameterType = \"java.lang.Integer\">").append("\n");
		bf.append(TAB).append(TAB).append("select t.* from ").append(bean.getTableName()).append(" t where t.id=#{id}").append("\n");
		bf.append(TAB).append("</select>").append("\n");
		bf.append("\n");
		
		// 根据条件取列表
		bf.append(TAB).append("<select id=\"queryListByMap\" resultType=\"").append(MysqlMain.package_name_model).append(".").append(bean.getSpaceName()).append("\" parameterType=\"java.util.Map\">").append("\n");
		bf.append(TAB).append(TAB).append("select t.* from ").append(bean.getTableName()).append(" t ").append("\n");
		bf.append(TAB).append(TAB).append("<include refid=\"queryWhere\"/>").append("\n");
		bf.append(TAB).append(TAB).append("<include refid=\"queryOrder\"/>").append("\n");
		bf.append(TAB).append(TAB).append("<include refid=\"queryLimit\"/>").append("\n");
		bf.append(TAB).append("</select>").append("\n");
		bf.append("\n");
		
		// count
		bf.append(TAB).append("<select id=\"queryCountByMap\" resultType=\"java.lang.Integer").append("\" parameterType=\"java.util.Map\">").append("\n");
		bf.append(TAB).append(TAB).append("select count(*) from ").append(bean.getTableName()).append(" t ").append("\n");
		bf.append(TAB).append(TAB).append("<include refid=\"queryWhere\"/>").append("\n");
		bf.append(TAB).append("</select>").append("\n");
		bf.append("\n");
		
		// where 条件
		bf.append(TAB).append("<sql id=\"queryWhere\">").append("\n");
		bf.append(TAB).append(TAB).append("<where> ").append("\n");
		for(int i =0;i<bean.getFieldList().size();i++) {
			FieldBean obj = bean.getFieldList().get(i);
			bf.append(TAB).append(TAB).append(TAB).append("<if test=\"").append(obj.getJavaCode()).append(" != null\">and t.").append(obj.getField()).append(" = #{").append(obj.getJavaCode()).append("} </if>").append("\n");
		}
		bf.append(TAB).append(TAB).append("</where> ").append("\n");
		bf.append(TAB).append("</sql>").append("\n");
		bf.append("\n");
		//order
		bf.append(TAB).append("<sql id=\"queryOrder\">").append("\n");
		bf.append(TAB).append(TAB).append("<if test=\"orderBy != null\">").append("\n");
		bf.append(TAB).append(TAB).append(TAB).append("ORDER BY ${orderBy}").append("\n");
		bf.append(TAB).append(TAB).append("</if>").append("\n");
		bf.append(TAB).append("</sql>").append("\n");
		bf.append("\n");
		// limit
		bf.append(TAB).append("<sql id=\"queryLimit\">").append("\n");
		bf.append(TAB).append(TAB).append("<if test=\"pageStart != null and pageSize != null\">").append("\n");
		bf.append(TAB).append(TAB).append(TAB).append("limit #{pageStart},#{pageSize}").append("\n");
		bf.append(TAB).append(TAB).append("</if>").append("\n");
		bf.append(TAB).append("</sql>").append("\n");
		bf.append("\n");
		//insert
		bf.append(TAB).append("<insert id=\"insert\" parameterType = \"").append(MysqlMain.package_name_model).append(".").append(bean.getSpaceName()).append("\" useGeneratedKeys=\"true\" keyProperty=\"id\" >").append("\n");
		bf.append(TAB).append(TAB).append("insert into ").append(bean.getTableName()).append("(").append("\n");
		for(int i =0;i<bean.getFieldList().size();i++) {
			FieldBean obj = bean.getFieldList().get(i);
			if ("id".equals(obj.getField().toLowerCase()) ||"create_date".equals(obj.getField())) {
				continue;
			}
			bf.append(TAB).append(TAB).append(TAB).append("<if test=\"").append(obj.getJavaCode()).append(" != null\">").append(obj.getField()).append(", </if>").append("\n");
		}
		bf.append(TAB).append(TAB).append(TAB).append("create_date").append("\n");
		
		bf.append(TAB).append(TAB).append(")values(").append("\n");
		for(int i =0;i<bean.getFieldList().size();i++) {
			FieldBean obj = bean.getFieldList().get(i);
			if ("id".equals(obj.getField().toLowerCase()) ||"create_date".equals(obj.getField())) {
				continue;
			}
			bf.append(TAB).append(TAB).append(TAB).append("<if test=\"").append(obj.getJavaCode()).append(" != null\">#{ ").append(obj.getJavaCode()).append("}, </if>").append("\n");
		}
		bf.append(TAB).append(TAB).append(TAB).append("now()").append("\n");
		bf.append(TAB).append(TAB).append(")").append("\n");
		bf.append(TAB).append("</insert>").append("\n");
		bf.append("\n");
		// update
		bf.append(TAB).append("<update id=\"update\" parameterType=\"").append(MysqlMain.package_name_model).append(".").append(bean.getSpaceName()).append("\" >").append("\n");
		bf.append(TAB).append(TAB).append("update").append("\n");
		bf.append(TAB).append(TAB).append(TAB).append(bean.getTableName()).append("\n");
		bf.append(TAB).append(TAB).append("<set>").append("\n");
		for(int i =0;i<bean.getFieldList().size();i++) {
			FieldBean obj = bean.getFieldList().get(i);
			if ("id".equals(obj.getField().toLowerCase()) ||"modify_date".equals(obj.getField().toLowerCase())) {
				continue;
			}
			bf.append(TAB).append(TAB).append(TAB).append("<if test=\"").append(obj.getJavaCode()).append(" != null\">");
			bf.append(obj.getField()).append(" = #{").append(obj.getJavaCode()).append("},");
			bf.append("</if>").append("\n");
		}
		bf.append(TAB).append(TAB).append(TAB).append(TAB).append("modify_date = now()").append("\n");
		bf.append(TAB).append(TAB).append("</set>").append("\n");
		bf.append(TAB).append(TAB).append("where id = #{id}").append("\n");
		bf.append(TAB).append("</update>").append("\n");
		
		bf.append("</mapper>").append("\n");
		
		bf.append("\n");

		//System.out.println(bf.toString());

		String fileName = MysqlMain.save_path +"/" + bean.getSpaceName() + ".xml";

		try {

			FileOutputStream fos = new FileOutputStream(fileName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(bf.toString());
			out.close();
			fos.close();
			
			System.out.println("==="+bean.getSpaceName() + ".xml"+"生成");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
