package com.insigma.siis.local.epsoft.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

/**
 * XMLת��������
 * @author gezh
 *
 */
public class JXUtil {
	
	
	/**
	 * Ĭ��xmlת�����ַ���
	 */
	private static final String DEFAULT_XOENCODING = "UTF-8";
	
	/**
	 * Ĭ�϶���תxml�ַ���
	 */
	private static final String DEFAULT_OXENCODING = "UTF-8";
	
	private static final Logger log = Logger.getLogger(JXUtil.class);
	
	/**
	 * Xmlת��ָ������
	 * @param xml xml�ַ���
	 * @param targetClass ָ������class
	 * @return ���ض���
	 * @throws Exception
	 */
	public static Object Xml2Object(String xml,Class targetClass) throws Exception {
		try {
			JAXBContext context = JAXBContext.newInstance(targetClass);
			InputSource is = new InputSource();
			is.setEncoding(DEFAULT_XOENCODING);
			StringReader xmlStr = new StringReader(xml);
			is.setCharacterStream(xmlStr);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object object = unmarshaller.unmarshal(is);
			return object;
		} catch (JAXBException e) {
			log.error("Xmlת��Objectʧ��",e);
			throw new Exception(e);
		}
	}
	
	/**
	 * ����ת����xml�����Ŀ¼Ϊ������@XmlRootElementָ��������
	 * @param object ��ת���Ķ���
	 * @return
	 * @throws Exception
	 */
	public static String Object2Xml(Object object) throws Exception {
		ByteArrayOutputStream baos = null;
	    try {  
	    	JAXBContext context = JAXBContext.newInstance(object.getClass());
	        Marshaller marshal = context.createMarshaller();  
	        //���ò��Զ�ת��  2019.12.12 yzk
	        marshal.setProperty( "com.sun.xml.bind.characterEscapeHandler", new CharacterEscapeHandler() {
	            public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
	                String s = new String(ch, start, length);
	                out.write(StringEscapeUtils.escapeXml(StringEscapeUtils.unescapeXml(s)));
	            }
	        });
	        marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);  
	        marshal.setProperty(Marshaller.JAXB_ENCODING, DEFAULT_OXENCODING);
	        marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);
	        baos = new ByteArrayOutputStream();
	        marshal.marshal(object, baos);  
	        return new String(baos.toByteArray(),DEFAULT_OXENCODING);
	    } catch (JAXBException e) {  
	    	log.error("Objectת��Xmlʧ��",e);
	    	throw new Exception(e);
	    }finally{
	    	if(baos!=null){
	    		try{
	    			baos.close();
	    		}catch(Exception e){
	    		}
	    	}
	    }
	}
	
	
	/**
	 * ����ת����xml�����Ŀ¼Ϊ������@XmlRootElementָ��������
	 * @param object ��ת���Ķ���
	 * @param format ��ת�����XML�Ƿ���Ҫ��ʽ��
	 * @return
	 * @throws Exception
	 */
	public static String Object2Xml(Object object,boolean format) throws Exception {
		ByteArrayOutputStream baos = null;
	    try {  
	    	JAXBContext context = JAXBContext.newInstance(object.getClass());
	        Marshaller marshal = context.createMarshaller();
	        //���ò��Զ�ת��  2019.12.12 yzk
	        marshal.setProperty( "com.sun.xml.bind.characterEscapeHandler", new CharacterEscapeHandler() {
	            public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
	                String s = new String(ch, start, length);
	                out.write(StringEscapeUtils.escapeXml(StringEscapeUtils.unescapeXml(s)));
	            }
	        });
	        marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,format);  
	        marshal.setProperty(Marshaller.JAXB_ENCODING, DEFAULT_OXENCODING);
	        marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);
	        baos = new ByteArrayOutputStream();
	        marshal.marshal(object, baos);  
	        return new String(baos.toByteArray(),DEFAULT_OXENCODING);
	    } catch (JAXBException e) {  
	    	log.error("Objectת��Xmlʧ��",e);
	    	throw new Exception(e);
	    }finally{
	    	if(baos!=null){
	    		try{
	    			baos.close();
	    		}catch(Exception e){
	    		}
	    	}
	    }
	}
	
	
	/**
	 * ����ת����xml�����Ŀ¼Ϊ������@XmlRootElementָ��������
	 * @param object ��ת���Ķ���
	 * @param format ��ת�����XML�Ƿ���Ҫ��ʽ��
	 * @param xmlEncoding ת�����XMLʹ�õ��ַ���
	 * @return
	 * @throws Exception
	 */
	public static String Object2Xml(Object object,boolean format,String xmlEncoding) throws Exception {
		ByteArrayOutputStream baos = null;
	    try {  
	    	JAXBContext context = JAXBContext.newInstance(object.getClass());
	        Marshaller marshal = context.createMarshaller();  
	        //���ò��Զ�ת��  2019.12.12 yzk
	        marshal.setProperty( "com.sun.xml.bind.characterEscapeHandler", new CharacterEscapeHandler() {
	            public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
	                String s = new String(ch, start, length);
	                out.write(StringEscapeUtils.escapeXml(StringEscapeUtils.unescapeXml(s)));
	            }
	        });
	        marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,format);  
	        marshal.setProperty("jaxb.encoding", (xmlEncoding!=null&&!"".equals(xmlEncoding))?xmlEncoding:DEFAULT_OXENCODING);
	        baos = new ByteArrayOutputStream();
	        marshal.marshal(object, baos);  
	        return baos.toString();
	    } catch (JAXBException e) {  
	    	log.error("Objectת��Xmlʧ��",e);
	    	throw new Exception(e);
	    }finally{
	    	if(baos!=null){
	    		try{
	    			baos.close();
	    		}catch(Exception e){
	    		}
	    	}
	    }
	}
	
}
