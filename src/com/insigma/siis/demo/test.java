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
				
		//4�� 
//		SAXReader reader = new SAXReader();
//		Document document = reader.read(new File("E:/A01.xml"));
////        System.out.println(document);
//		Element root = document.getRootElement();
//	    Iterator it = root.elementIterator();
//	    while (it.hasNext()) {
//	    Element element = (Element) it.next();
//		   //δ֪�������������
//		    Iterator attrIt = element.attributeIterator();
//		    while (attrIt.hasNext()) {
//			    Attribute a  = (Attribute) attrIt.next();
//			    System.out.println(a.getValue());
//			}
//	    }
//		//3��
//		DataFile dfdesc;
//		try {
//			dfdesc = new DataFile(new File("E:/A02.xml"));
//			DataFileHead dataFileHead = dfdesc.getHead();   //����XMLͷ������ͷ����
//			DataFileBodyListBean dataFileBodyListBean = dfdesc.getBody();   //����XML�壬�Żؽṹ��
//			System.out.println(dataFileBodyListBean.getParamData());  
//			List<DataFileBodyBean>  listbean = dataFileBodyListBean.getListBean(); // List<  HashMap tableHashMap ,List ( HashMap columnHashMap)        >
//			//ȡ����Ϣ
//			for (int i=0;i<listbean.size();i++){
//				DataFileBodyBean bean = listbean.get(i);    //ȡ��bean����
//				HashMap tableHashMap = bean.getTabAttribute();  //ȡ��bean����ı����HashMap��
//				Aa26 aa26Tab = new Aa26();
//				try {
//					BeanCopyUtil.CopyfromHashMap(aa26Tab, tableHashMap);
//					List<HashMap> columnlist = bean.getColAttribute(); //ȡ��bean������ֶζ���List<HashMap>��
//					for (int j=0;j<columnlist.size();j++){
//						HashMap columnHashMap = columnlist.get(j);   //ȡ���ֶ���Ϣ��HashMap��
//						Aa10 aa10Col = new Aa10();
//						BeanCopyUtil.CopyfromHashMap(aa10Col, columnHashMap);
//						/************�˴����Թ����ֶ�SQL���*****************/
//						/************************************************/
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} //��HashMap������������
//				/************�˴����Թ�����SQL���*****************/
//				/**********************************************/
//			}
////			System.out.println(df.getHead().getVersion());  //��ӡ���ֽ�����Ϣ
//		} catch (AppException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
 
		//1��SAX
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
//				if(attr.getValue().contains("�й�������")){
//					String[] b=  attr.getValue().split(System.getProperty("line.separator"));
//					for(int i=0;i<b.length;i++){
//						System.out.println(b[i]);
//					}
//				}
//            }
//		} 
		//2��
//		File file = new File("E:/A01.xml");
//		String a="";
//		try {
//			a = FileUtil.read6String(file);
//			System.out.println(a);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		Connection con = null;// ����һ�����ݿ�����
//	    PreparedStatement pstmt = null;// ����Ԥ����������һ�㶼�������������Statement
//	    ResultSet result = null;// ����һ�����������
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
//	        String url = "jdbc:oracle:thin:@192.168.1.115:1521:orcl";// 127.0.0.1�Ǳ�����ַ��XE�Ǿ����Oracle��Ĭ�����ݿ���  
//	        String user = "zwhzyq";// �û���,ϵͳĬ�ϵ��˻���
//	        String password = "zwhzyq";// �㰲װʱѡ���õ�����
//	        
//	        Class.forName(name);// ����Oracle��������
//	        System.out.println("��ʼ�����������ݿ⣡");
//	        con = DriverManager.getConnection(url, user, password);// ��ȡ����
//	        System.out.println("���ӳɹ���");
//	        String sql = "insert into text_temp (a1701) values(?)";
//	        System.out.println(sql);
//	        pstmt = (PreparedStatement)con.prepareStatement(sql);// ʵ����Ԥ�������
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
//	            // ��һ������ļ�������رգ���Ϊ���رյĻ���Ӱ�����ܡ�����ռ����Դ
//	            // ע��رյ�˳�����ʹ�õ����ȹر�
//	            if (result != null)
//	                result.close();
//	            if (pstmt != null)
//	            	pstmt.close();
//	            if (con != null)
//	                con.close();
//	            System.out.println("���ݿ������ѹرգ�");
//	        }
//	        catch (Exception e)
//	        {
//	            e.printStackTrace();
//	        }
//	    }
		System.out.println("001.001.001".contains("001.0011"));
	}

	
	public void con(){
		Connection con = null;// ����һ�����ݿ�����
	    PreparedStatement pstmt = null;// ����Ԥ����������һ�㶼�������������Statement
	    ResultSet result = null;// ����һ�����������
	    int i = 0;
	    try
	    {
	        Class.forName("oracle.jdbc.driver.OracleDriver");// ����Oracle��������
	        System.out.println("��ʼ�����������ݿ⣡");
	        String url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:XE";// 127.0.0.1�Ǳ�����ַ��XE�Ǿ����Oracle��Ĭ�����ݿ���
	        String user = "system";// �û���,ϵͳĬ�ϵ��˻���
	        String password = "147";// �㰲װʱѡ���õ�����
	        con = DriverManager.getConnection(url, user, password);// ��ȡ����
	        System.out.println("���ӳɹ���");
	        String sql = "insert into text_temp (a1701) values(?)";
	        System.out.println(sql);
	        pstmt = (PreparedStatement)con.prepareStatement(sql);// ʵ����Ԥ�������
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
	            // ��һ������ļ�������رգ���Ϊ���رյĻ���Ӱ�����ܡ�����ռ����Դ
	            // ע��رյ�˳�����ʹ�õ����ȹر�
	            if (result != null)
	                result.close();
	            if (pstmt != null)
	            	pstmt.close();
	            if (con != null)
	                con.close();
	            System.out.println("���ݿ������ѹرգ�");
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	}
}
