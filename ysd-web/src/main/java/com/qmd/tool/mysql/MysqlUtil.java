package com.qmd.tool.mysql;

public class MysqlUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String field2JavaCode(String field) {
		String javaCode = field;

		javaCode = javaCode.toLowerCase();
		javaCode = javaCode.trim();
		String[] codes = javaCode.split("_");
		if (codes.length > 1) {
			for (int i = 1; i < codes.length; i++) {
				codes[i] = (codes[i].substring(0, 1)).toUpperCase()
						+ codes[i].substring(1);
			}
			javaCode = "";
			for (int i = 0; i < codes.length; i++) {
				javaCode += codes[i];
			}
		}
		return javaCode;
	}

	public static String type2JavaTypeFull(String type) {
		String tableType = type.toUpperCase();

		tableType = tableType.trim().toUpperCase();
		String javaType = "";
		if (tableType.indexOf("BIGINT") >= 0) {
			javaType = "java.lang.Long";
		} else if (tableType.indexOf("VARCHAR") >= 0) {
			javaType = "java.lang.String";
		} else if (tableType.indexOf("INT") >= 0) {
			javaType = "java.lang.Integer";
		} else if (tableType.indexOf("DATETIME") >= 0) {
			javaType = "java.util.Date";
		} else if (tableType.indexOf("TEXT") >= 0) {
			javaType = "java.lang.String";
		} else if (tableType.indexOf("DOUBLE") >= 0) {
			javaType = "java.lang.Double";
		} else if (tableType.indexOf("VARBINARY") >= 0) {
			javaType = "java.lang.String";
		} else {
			javaType = "";
		}

		return javaType;
	}

	public static String type2JavaType(String type) {
		String tableType = type.toUpperCase();

		tableType = tableType.trim().toUpperCase();
		String javaType = "";
		if (tableType.indexOf("BIGINT") >= 0) {
			javaType = "Long";
		} else if (tableType.indexOf("VARCHAR") >= 0) {
			javaType = "String";
		} else if (tableType.indexOf("INT") >= 0) {
			javaType = "Integer";
		} else if (tableType.indexOf("DATETIME") >= 0) {
			javaType = "Date";
		} else if (tableType.indexOf("TEXT") >= 0) {
			javaType = "String";
		} else if (tableType.indexOf("DOUBLE") >= 0) {
			javaType = "Double";
		} else if (tableType.indexOf("VARBINARY") >= 0) {
			javaType = "String";
		} else {
			javaType = "";
		}

		return javaType;
	}

	public static String tableName2SpaceName(String tableName) {

		String javaCode = tableName.toLowerCase();
		javaCode = javaCode.trim();
		String[] codes = javaCode.split("_");

		javaCode = "";
		for (int i = 1; i < codes.length; i++) {
			codes[i] = (codes[i].substring(0, 1)).toUpperCase()
					+ codes[i].substring(1);

			javaCode += codes[i];
		}

		return javaCode;

	}
	

}
