package com.insigma.siis.local.pagemodel.customquery.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaError;


public class CellUtil {
	/**
	 * 获取单元格值
	 * 
	 * @param cell {@link Cell}单元格
	 * @param cellEditor 单元格值编辑器。可以通过此编辑器对单元格值做自定义操作
	 * @return 值，类型可能为：Date、Double、Boolean、String
	 */
	public static Object getCellValue(Cell cell, CellEditor cellEditor) {
		if (null == cell) {
			return null;
		}
		return getCellValue(cell, cell.getCellTypeEnum(), cellEditor);
	}
	
	/**
	 * 获取单元格值<br>
	 * 如果单元格值为数字格式，则判断其格式中是否有小数部分，无则返回Long类型，否则返回Double类型
	 * 
	 * @param cell {@link Cell}单元格
	 * @param cellType 单元格值类型{@link CellType}枚举，如果为{@code null}默认使用cell的类型
	 * @param cellEditor 单元格值编辑器。可以通过此编辑器对单元格值做自定义操作
	 * @return 值，类型可能为：Date、Double、Boolean、String
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
			// 遇到公式时查找公式结果类型
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
	 * 获取数字类型的单元格值
	 * 
	 * @param cell 单元格
	 * @return 单元格值，可能为Long、Double、Date
	 */
	private static Object getNumericValue(Cell cell) {
		final double value = cell.getNumericCellValue();
		final CellStyle style = cell.getCellStyle();
		if (null == style) {
			return value;
		}

		final short formatIndex = style.getDataFormat();
		final String format = style.getDataFormatString();
		// 普通数字
		if (null != format && format.indexOf('.') < 0) {
			final long longPart = (long) value;
			if (longPart == value) {
				// 对于无小数部分的数字类型，转为Long
				return longPart;
			}
		}
		return value;
	}
}
