package com.jeesite.modules.common.utils;


import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

/**
 * @version 1.0
 * @author
 * @data 2017年6月21日
 * @描述:
 */
public class ExcelHelper {

    public static String cellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellTypeEnum()) {
        case STRING:
            return cell.getRichStringCellValue().getString().trim();
        case NUMERIC:
            String trim = new BigDecimal(Double.toString(cell.getNumericCellValue())).toPlainString().trim();
            return trim.endsWith(".0") ? trim.substring(0, trim.length() - 2) : trim;
        case BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        case FORMULA:
            return cell.getCellFormula();
        case BLANK:
        default:
            return "";
        }
    }

    public static CellStyle align(Workbook wb, HorizontalAlignment align) {
        CellStyle styleLeft = wb.createCellStyle();
        styleLeft.setWrapText(true);
        styleLeft.setAlignment(align);
        styleLeft.setVerticalAlignment(VerticalAlignment.CENTER);
        return styleLeft;
    }

    /**
     * 文本格式
     */
    public static CellStyle style(Workbook wb, HorizontalAlignment align, boolean... bold) {
        CellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        style.setAlignment(align);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        if (bold.length > 0 && bold[0]) {
            Font font = wb.createFont();
            font.setBold(true);
            style.setFont(font);
        }
        return style;
    }
    
    /**
     * 标题格式
     */
    public static CellStyle styleTitle(Workbook wb) {
        CellStyle styleTitle = wb.createCellStyle();
        Font font = wb.createFont();// 前端ajax导出的时候不会自适应单元格宽度
        font.setBold(true);
        styleTitle.setFont(font);
        styleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleTitle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());// 设置背景色;
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        return styleTitle;
    }

    /**
     * 数值格式
     * 
     * @param format
     *            格式，示例保留4位小数：0.0000
     */
    public static CellStyle styleNumber(Workbook wb, String format, boolean... bold) {
        CellStyle style = style(wb, HorizontalAlignment.RIGHT);
        DataFormat df = wb.createDataFormat();
        if (StringUtils.isNotBlank(format)) {
            style.setDataFormat(df.getFormat(format));
        }
        if (bold.length > 0 && bold[0]) {
            Font font = wb.createFont();
            font.setBold(true);
            style.setFont(font);
        }
        return style;
    }

    public static String convertNumToColString(int col) {
        return CellReference.convertNumToColString(col);
    }

    public static int convertColStringToNum(String colString) {
        if (StringUtils.isBlank(colString) || colString.length() > 2) {
            return -1;
        } else {
            return CellReference.convertColStringToIndex(colString);
        }
    }

    public static void main(String[] args) {
        System.out.println(convertColStringToNum("A"));
    }
}
