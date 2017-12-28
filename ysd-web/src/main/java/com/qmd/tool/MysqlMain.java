package com.qmd.tool;

import com.qmd.tool.mysql.*;

import java.util.ArrayList;
import java.util.List;

public class MysqlMain {


	public static String package_name = "com.qmd.tool.temp";
	public static String package_name_model= "com.qmd.mode";
	public static String save_path = "E:/workspace/321wangdai/src/main/java/com/qmd/tool/temp";

	public static String mysql_url = "jdbc:mysql://192.168.0.240:3306";
	public static String mysql_dbname = "qtz";
	public static String mysql_username = "root";
	public static String mysql_password = "7890uiop";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// List<TablesBean> list = MysqlUtil2ShowTables.showTableNameList();
		List<TablesBean> list = new ArrayList<TablesBean>();
		String tableName = "fy_activity";

		TablesBean bean = new TablesBean(tableName);
		list.add(bean);

		List<TablesBean> list2 = new ArrayList<TablesBean>();

		for (int i = 0; i < list.size(); i++) {
			TablesBean obj = list.get(i);
			List<FieldBean> itemList = MysqlUtil2ShowCreateTable
					.readTableDetail(list.get(i).getTableName());
			obj.setFieldList(itemList);
			list2.add(obj);
		}

		for (int i = 0; i < list2.size(); i++) {
			MysqlUtilTable2Bean.printEntity(list2.get(i));
		}

		for (int i = 0; i < list2.size(); i++) {
			MysqlUtilTable2XML.printXMLForMap(list2.get(i));
		}

		for (int i = 0; i < list2.size(); i++) {
			MysqlUtilTable2Dao.printDao(list2.get(i));
			MysqlUtilTable2Dao.printDaoImpl(list2.get(i));
		}

		for (int i = 0; i < list2.size(); i++) {
			MysqlUtilTable2Service.printService(list2.get(i));
			MysqlUtilTable2Service.printServiceImpl(list2.get(i));
		}

	}

}
