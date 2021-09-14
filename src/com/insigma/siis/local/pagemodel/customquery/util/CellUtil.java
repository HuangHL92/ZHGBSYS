package com.insigma.siis.local.pagemodel.customquery.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaError;


public class CellUtil {
	/**
	 * ��ȡ��Ԫ��ֵ
	 * 
	 * @param cell {@link Cell}��Ԫ��
	 * @param cellEditor ��Ԫ��ֵ�༭��������ͨ���˱༭���Ե�Ԫ��ֵ���Զ������
	 * @return ֵ�����Ϳ���Ϊ��Date��Double��Boolean��String
	 */
	public static Object getCellValue(Cell cell, CellEditor cellEditor) {
		if (null == cell) {
			return null;
		}
		return getCellValue(cell, cell.getCellTypeEnum(), cellEditor);
	}
	
	/**
	 * ��ȡ��Ԫ��ֵ<br>
	 * �����Ԫ��ֵΪ���ָ�ʽ�����ж����ʽ���Ƿ���С�����֣����򷵻�Long���ͣ����򷵻�Double����
	 * 
	 * @param cell {@link Cell}��Ԫ��
	 * @param cellType ��Ԫ��ֵ����{@link CellType}ö�٣����Ϊ{@code null}Ĭ��ʹ��cell������
	 * @param cellEditor ��Ԫ��ֵ�༭��������ͨ���˱༭���Ե�Ԫ��ֵ���Զ������
	 * @return ֵ�����Ϳ���Ϊ��Date��Double��Boolean��String
	 */
	public static Object getCellValue(Cell cell, CellType cellType, CellEditor cellEditor) {
		if (null == cell) {
			return null;
		}
		if (null == cellType) {
			cellType = cell.getCellTypeEnum();
		}

		Object value;
		switch (cellType) {
		case NUMERIC:
			value = getNumericValue(cell);
			break;
		case BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case FORMULA:
			// ������ʽʱ���ҹ�ʽ�������
			value = getCellValue(cell, cell.getCachedFormulaResultTypeEnum(), cellEditor);
			break;
		case BLANK:
			value = "";
			break;
		case ERROR:
			final FormulaError error = FormulaError.forInt(cell.getErrorCellValue());
			value = (null == error) ? "" : error.getString();
			break;
		default:
			value = cell.getStringCellValue();
		}

		return null == cellEditor ? value : cellEditor.edit(cell, value);
	}
	
	/**
	 * ��ȡ�������͵ĵ�Ԫ��ֵ
	 * 
	 * @param cell ��Ԫ��
	 * @return ��Ԫ��ֵ������ΪLong��Double��Date
	 */
	private static Object getNumericValue(Cell cell) {
		final double value = cell.getNumericCellValue();
		final CellStyle style = cell.getCellStyle();
		if (null == style) {
			return value;
		}

		final short formatIndex = style.getDataFormat();
		final String format = style.getDataFormatString();
		// ��ͨ����
		if (null != format && format.indexOf('.') < 0) {
			final long longPart = (long) value;
			if (longPart == value) {
				// ������С�����ֵ��������ͣ�תΪLong
				return longPart;
			}
		}
		return value;
	}
}
