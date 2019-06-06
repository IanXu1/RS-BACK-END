package com.teee.referencestation.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhanglei
 */
public class ExcelUtils {

    private final Pattern NUMBER_PATTERN = Pattern.compile("^//d+(//.//d+)?$");

    /**
     * 导出 xlsx格式Excel XSSF
     * @param titleName 标题名字
     * @param sheetName sheet页名字
     * @param headers
     * @param columns
     * @param dataSet
     * @param out
     * @param pattern
     */
    public void exportXSSExcel(String titleName, String sheetName, String[] headers, String[] columns,
                                      Collection<Map<String, Object>> dataSet, OutputStream out, String pattern) {
        try{
            Workbook workbook = new SXSSFWorkbook();
            // 生成一个表格
            Sheet sheet = workbook.createSheet(sheetName);
            // 设置表格默认列宽度为20个字节
            sheet.setDefaultRowHeightInPoints(18);
            //设置列宽
            sheet.setColumnWidth(1, 256 * 18);
            sheet.setColumnWidth(3, 256 * 18);
            sheet.setColumnWidth(4, 256 * 22);
            sheet.setColumnWidth(5, 256 * 32);
            // 标题行样式
            CellStyle titleStyle = createNormalCellStyle(workbook);
            // 标题行字体
            Font titleFont = createNormalFont(workbook, (short) 16, true);
            // 把字体应用到当前的样式
            titleStyle.setFont(titleFont);

            // 表头行样式
            CellStyle headerStyle = createNormalCellStyle(workbook);
            // 表头行字体
            Font headerFont = createNormalFont(workbook, (short) 11, true);
            // 把字体应用到当前的样式
            headerStyle.setFont(headerFont);

            // 数据行样式
            CellStyle bodyStyle = createNormalCellStyle(workbook);
            // 数据行字体
            Font bodyFont = createNormalFont(workbook, (short) 10, false);
            // 把字体应用到当前的样式
            bodyStyle.setFont(bodyFont);
            bodyStyle.setWrapText(true);

            Row row = sheet.createRow(0);
            int rows = 0;
            if (titleName != null) {
                row.setHeightInPoints((short) 32);
                //创建标题行
                Cell titleCell = row.createCell(0);
                //合并单元格,CellRangeAddress构造参数为起始行，截止行，起始列，截止列
                CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, headers.length - 1);
                sheet.addMergedRegion(rangeAddress);
                titleCell.setCellValue(titleName);
                titleCell.setCellStyle(titleStyle);
                //合并单元格后边框丢失
                RegionUtil.setBorderBottom(BorderStyle.THIN, rangeAddress, sheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN, rangeAddress, sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN, rangeAddress, sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, rangeAddress, sheet);
                rows++;
            }

            // 产生表头行
            row = sheet.createRow(rows);
            row.setHeightInPoints((short) 24);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                RichTextString text = new XSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            if(StringUtils.isEmpty(pattern)) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
            FastDateFormat instance = FastDateFormat.getInstance(pattern);
            // 遍历集合数据，产生数据行
            // 多个Map集合
            Iterator<Map<String, Object>> it = dataSet.iterator();
            int count = 0;
            while (it.hasNext()) {
                rows++;
                row = sheet.createRow(rows);
                row.setHeightInPoints((short) 20);
                Map<String, Object> map = it.next();
                count = headers.length < columns.length ? headers.length : columns.length;
                for (int i = 0; i < count; i++) {
                    //判断告警颜色
                    if (columns[i].equalsIgnoreCase("levelName")) {
                        int level = Integer.valueOf(map.get("level").toString());
                        // 数据行样式
                        bodyStyle = createWarningLevelCellStyle(workbook, level);
                        // 数据行字体
                        // 把字体应用到当前的样式
                        bodyStyle.setFont(bodyFont);
                        bodyStyle.setWrapText(true);
                    } else {
                        bodyStyle = createNormalCellStyle(workbook);
                        // 把字体应用到当前的样式
                        bodyStyle.setFont(bodyFont);
                        bodyStyle.setWrapText(true);
                    }
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(bodyStyle);

                    try {
                        Object value = map.get(columns[i]);
                        if (ObjUtil.isEmpty(value)) {
                            break;
                        }
                        // 判断值的类型后进行强制类型转换
                        String textValue = null;
                        if (value instanceof Date) {
                            Date date = (Date) value;
                            textValue = instance.format(date);
                        } else {
                            // 其它数据类型都当作字符串简单处理
                            if (value != null) {
                                textValue = value.toString();
                            } else {
                                textValue = "";
                            }
                        }
                        // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                        if (textValue != null) {
                            Matcher matcher = NUMBER_PATTERN.matcher(textValue);
                            if (matcher.matches()) {
                                // 是数字当作double处理
                                cell.setCellValue(Double.parseDouble(textValue));
                            } else {
                                RichTextString richString = new XSSFRichTextString(textValue);
                                Font font3 = workbook.createFont();
                                font3.setColor(IndexedColors.BLACK.index);
                                // 内容
                                richString.applyFont(font3);
                                cell.setCellValue(richString);
                            }
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
            workbook.write(out);
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public CellStyle createNormalCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    public CellStyle createWarningLevelCellStyle(Workbook workbook, int level) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        if (level == 1) {
            style.setFillForegroundColor(IndexedColors.TURQUOISE.index);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        } else if (level == 2) {
            style.setFillForegroundColor(IndexedColors.YELLOW.index);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        } else if (level == 3) {
            style.setFillForegroundColor(IndexedColors.CORAL.index);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        } else if (level == 4) {
            style.setFillForegroundColor(IndexedColors.RED.index);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        return style;
    }

    public Font createNormalFont(Workbook workbook, short fontHeight, boolean isBold) {
        Font font = workbook.createFont();
        font.setItalic(false);
        font.setColor(Font.COLOR_NORMAL);
        font.setFontHeightInPoints(fontHeight);
        font.setFontName("宋体");
        font.setBold(isBold);
        return font;
    }

}
