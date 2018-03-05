package test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetClassifyNews {

	public static void main(String[] args) throws Exception {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		// 获取session
		SessionFactory sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		Session session = sessionFactory.openSession();
		System.out.println(session);

		// 创建分类新闻存放位置
		String base = "E:\\开发\\Eclipse工作空间\\DATA\\NEWS\\";
		String sql = "SELECT DISTINCT(CLASSIFIED) FROM Search_Engine_Test_ZXL_2 WHERE CLASSIFIED is not null and CLASSIFIED != '' and CLASSIFIED != '其他'";
		SQLQuery query = session.createSQLQuery(sql);
		List<String> classify = query.list();
		System.out.println(classify);
		for (int i = 0; i < classify.size(); i++) {
			makeDirs(base, classify.get(i));
		}

		sql = "SELECT HTML, CLASSIFIED FROM Search_Engine_Test_ZXL_2 WHERE CLASSIFIED is not null and CLASSIFIED != '' and CLASSIFIED != '其他'";
		query = session.createSQLQuery(sql);
		List<Object> result = query.list();
		for (int i = 0; i < result.size(); i++) {
			Object[] row = (Object[]) result.get(i);
			System.out.println(i + ":" + row[1]);
			writeStringToFile(base + row[1] + "\\" + i + ".txt", row[0].toString());
		}
	}

	/**
	 * 传入文件名以及字符串, 将字符串信息保存到文件中
	 * 
	 * @param strFilename
	 * @param strBuffer
	 */
	public static void writeStringToFile(String filePath, String text) {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(text.getBytes());
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param base
	 * @param filePath
	 * @return
	 */
	public static boolean makeDirs(String base, String filePath) {
		String folderName = base + filePath;
		if (folderName == null || folderName.isEmpty()) {
			return false;
		}

		File folder = new File(folderName);
		return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
	}

}
