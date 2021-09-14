package com.insigma.siis.demo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.dom4j.DocumentException;


public class test {
	private static int count=0;
	public int addCount(){
		count++;
		return count;
	}

	/**
	 * @param args
	 * @throws DocumentException 
	 * @throws IOException  
	 */
	public static void main(String[] args) throws DocumentException, IOException {
		test d0 = new test();
		int c0 = d0.addCount();
		System.out.println(c0);
		test d1 = new test();
		int c1= d1.addCount();
		System.out.println(c1);
		int c2 = d0.addCount();
		System.out.println(c2);
				
		//4、 
//		SAXReader reader = new SAXReader();
//		Document document = reader.read(new File("E:/A01.xml"));
////        System.out.println(document);
//		Element root = document.getRootElement();
//	    Iterator it = root.elementIterator();
//	    while (it.hasNext()) {
//	    Element element = (Element) it.next();
//		   //未知属性名称情况下
//		    Iterator attrIt = element.attributeIterator();
//		    while (attrIt.hasNext()) {
//			    Attribute a  = (Attribute) attrIt.next();
//			    System.out.println(a.getValue());
//			}
//	    }
//		//3、
//		DataFile dfdesc;
//		try {
//			dfdesc = new DataFile(new File("E:/A02.xml"));
//			DataFileHead dataFileHead = dfdesc.getHead();   //解析XML头，返回头对象
//			DataFileBodyListBean dataFileBodyListBean = dfdesc.getBody();   //解析XML体，放回结构体
//			System.out.println(dataFileBodyListBean.getParamData());  
//			List<DataFileBodyBean>  listbean = dataFileBodyListBean.getListBean(); // List<  HashMap tableHashMap ,List ( HashMap columnHashMap)        >
//			//取表信息
//			for (int i=0;i<listbean.size();i++){
//				DataFileBodyBean bean = listbean.get(i);    //取得bean对象
//				HashMap tableHashMap = bean.getTabAttribute();  //取得bean对象的表对象（HashMap）
//				Aa26 aa26Tab = new Aa26();
//				try {
//					BeanCopyUtil.CopyfromHashMap(aa26Tab, tableHashMap);
//					List<HashMap> columnlist = bean.getColAttribute(); //取得bean对象的字段对象（List<HashMap>）
//					for (int j=0;j<columnlist.size();j++){
//						HashMap columnHashMap = columnlist.get(j);   //取得字段信息（HashMap）
//						Aa10 aa10Col = new Aa10();
//						BeanCopyUtil.CopyfromHashMap(aa10Col, columnHashMap);
//						/************此处可以构建字段SQL语句*****************/
//						/************************************************/
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} //把HashMap拷贝到对象中
//				/************此处可以构建表SQL语句*****************/
//				/**********************************************/
//			}
////			System.out.println(df.getHead().getVersion());  //打印部分解析信息
//		} catch (AppException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
 
		//1、SAX
//		SAXReader reader = new SAXReader();
//		Document document = reader.read(new File("E:/A01.xml"));
////        System.out.println(document);
////		String asXML= document.asXML();
////		asXML = asXML.replaceAll("\r\n", "&lt;br/&gt;");
////        System.out.println(asXML);
//		Element root = document.getRootElement();
//		List<Element> listnode = root.elements();
//		String a="";
//		List<Element> listrow = listnode.get(0).elements();
//		for (Element element : listrow) {
//			List<Attribute> attrs = element.attributes();  
//			for (Attribute attr : attrs) {  
//				if(attr.getValue().contains("中国人民银")){
//					String[] b=  attr.getValue().split(System.getProperty("line.separator"));
//					for(int i=0;i<b.length;i++){
//						System.out.println(b[i]);
//					}
//				}
//            }
//		} 
		//2、
//		File file = new File("E:/A01.xml");
//		String a="";
//		try {
//			a = FileUtil.read6String(file);
//			System.out.println(a);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		Connection con = null;// 创建一个数据库连接
//	    PreparedStatement pstmt = null;// 创建预编译语句对象，一般都是用这个而不用Statement
//	    ResultSet result = null;// 创建一个结果集对象
//	    int i = 0;
//	    try
//	    {
//	    	//mysql
////	        String name = "com.mysql.jdbc.Driver";  
////	        String url = "jdbc:mysql://127.0.0.1:3308/zwhzyq";  
////	        String user = "root";  
////	        String password = "admin"; 
//	        //oracle
//	        String name = "oracle.jdbc.driver.OracleDriver";  
//	        String url = "jdbc:oracle:thin:@192.168.1.115:1521:orcl";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名  
//	        String user = "zwhzyq";// 用户名,系统默认的账户名
//	        String password = "zwhzyq";// 你安装时选设置的密码
//	        
//	        Class.forName(name);// 加载Oracle驱动程序
//	        System.out.println("开始尝试连接数据库！");
//	        con = DriverManager.getConnection(url, user, password);// 获取连接
//	        System.out.println("连接成功！");
//	        String sql = "insert into text_temp (a1701) values(?)";
//	        System.out.println(sql);
//	        pstmt = (PreparedStatement)con.prepareStatement(sql);// 实例化预编译语句
////	        pstmt.setString(1, a);
//	        i = pstmt.executeUpdate();
//	    }
//	    catch (Exception e)
//	    {
//	        e.printStackTrace();
//	    }
//	    finally
//	    {
//	        try
//	        {
//	            // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
//	            // 注意关闭的顺序，最后使用的最先关闭
//	            if (result != null)
//	                result.close();
//	            if (pstmt != null)
//	            	pstmt.close();
//	            if (con != null)
//	                con.close();
//	            System.out.println("数据库连接已关闭！");
//	        }
//	        catch (Exception e)
//	        {
//	            e.printStackTrace();
//	        }
//	    }
		System.out.println("001.001.001".contains("001.0011"));
	}

	
	public void con(){
		Connection con = null;// 创建一个数据库连接
	    PreparedStatement pstmt = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	    ResultSet result = null;// 创建一个结果集对象
	    int i = 0;
	    try
	    {
	        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
	        System.out.println("开始尝试连接数据库！");
	        String url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:XE";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
	        String user = "system";// 用户名,系统默认的账户名
	        String password = "147";// 你安装时选设置的密码
	        con = DriverManager.getConnection(url, user, password);// 获取连接
	        System.out.println("连接成功！");
	        String sql = "insert into text_temp (a1701) values(?)";
	        System.out.println(sql);
	        pstmt = (PreparedStatement)con.prepareStatement(sql);// 实例化预编译语句
	        pstmt.setString(0, "1");
	        i = pstmt.executeUpdate();
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        try
	        {
	            // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
	            // 注意关闭的顺序，最后使用的最先关闭
	            if (result != null)
	                result.close();
	            if (pstmt != null)
	            	pstmt.close();
	            if (con != null)
	                con.close();
	            System.out.println("数据库连接已关闭！");
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	}
}
