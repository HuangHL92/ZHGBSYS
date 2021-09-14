package com.insigma.siis.local.pagemodel.customquery.util;

import java.io.File;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class WorkbookUtil {
	
	/**
	 * 创建或加载工作簿
	 * 
	 * @param excelFile Excel文件
	 * @return {@link Workbook}
	 * @throws Exception 
	 */
	public static Workbook createBook(File excelFile) throws Exception {
		return createBook(excelFile, null);
	}
	
	/**
	 * 创建或加载工作簿，只读模式
	 * 
	 * @param excelFile Excel文件
	 * @param password Excel工作簿密码，如果无密码传{@code null}
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
