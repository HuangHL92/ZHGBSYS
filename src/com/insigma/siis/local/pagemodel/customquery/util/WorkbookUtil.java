package com.insigma.siis.local.pagemodel.customquery.util;

import java.io.File;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class WorkbookUtil {
	
	/**
	 * ��������ع�����
	 * 
	 * @param excelFile Excel�ļ�
	 * @return {@link Workbook}
	 * @throws Exception 
	 */
	public static Workbook createBook(File excelFile) throws Exception {
		return createBook(excelFile, null);
	}
	
	/**
	 * ��������ع�������ֻ��ģʽ
	 * 
	 * @param excelFile Excel�ļ�
	 * @param password Excel���������룬��������봫{@code null}
	 * @return {@link Workbook}
	 * @throws Exception 
	 */
	public static Workbook createBook(File excelFile, String password) throws Exception {
		try {
			return WorkbookFactory.create(excelFile, password);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
