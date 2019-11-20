/*
 * Copyright 2018-2019 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lud.util.comm.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.text.IOContent;

/**
 * 对EXCEL文件的操作
 * @author sunqinqiu 
 * @date   2018-05-09 15:29
 */
public final class Excel {
    /**
     * 
     */
    private Logger       loger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * EXCEL存储路径
     */
    private String       path  = CT.EMPTY;
    
    /**
     * EXCEL文件
     */
    private HSSFWorkbook excelBook;
    
    /**
     * EXCEL的SHEET
     */
    private HSSFSheet    excelSheet;
    
    /**
     * 初始化一个EXCEL
     */
    public Excel() {
        this(CT.STRING_DATA);
    }
    
    /**
     * 根据SheetName初始化
     */
    public Excel(String sheetName) {
        initNewExcel(sheetName);
    }
    
    /**
     * 根据路径和SHEETNAME初始化
     */
    public Excel(String path, String sheetName) {
        this.path = path;
        if (FileUtil.exists(path)) {
            try (InputStream inputStream = new FileInputStream(path)) {
                excelBook = new HSSFWorkbook(inputStream);
                selectExcelSheet(sheetName);
            } catch (Exception e) {
                loger.error(e.toString());
            }
        } else {
            initNewExcel(sheetName);
        }
    }
    
    /**
     * 创建新的SHEET
     * @author sunqinqiu 
     * @date   2018-05-09 16:03
     * @param sheetName
     */
    public void creatNewExcelSheet(String sheetName) {
        excelSheet = this.excelBook.createSheet(sheetName);
    }
    
    /**
     * 创建单元格
     * @author sunqinqiu 
     * @date   2018-05-09 19:37
     * @param row
     * @param cell
     * @return
     */
    public HSSFCell creatRowAndCell(int row, int cell) {
        if (excelSheet.getRow(row) == null) {
            excelSheet.createRow(row);
        }
        HSSFCell cellItem = excelSheet.getRow(row).getCell(cell);
        if (cellItem == null) {
            cellItem = excelSheet.getRow(row).createCell(cell);
        }
        return cellItem;
    }
    
    /**
     * 设置样式
     * @author sunqinqiu 
     * @date   2018-05-09 19:40
     * @param isBorder
     * @param isThin
     * @param isCenter
     * @param color
     * @return
     */
    public HSSFCellStyle getCellStyle(boolean isBorder, boolean isThin, boolean isCenter, int color) {
        HSSFCellStyle cellStyle = excelBook.createCellStyle();
        BorderStyle border = isBorder ? (isThin ? BorderStyle.THIN : BorderStyle.THICK) : BorderStyle.NONE;
        cellStyle.setBorderTop(border);
        cellStyle.setBorderRight(border);
        cellStyle.setBorderBottom(border);
        cellStyle.setBorderLeft(border);
        cellStyle.setTopBorderColor((short) color);
        cellStyle.setRightBorderColor((short) color);
        cellStyle.setBottomBorderColor((short) color);
        cellStyle.setLeftBorderColor((short) color);
        cellStyle.setAlignment(isCenter ? HorizontalAlignment.CENTER : HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }
    
    /**
     * 设置字体样式
     * @author sunqinqiu 
     * @date   2018-05-09 19:41
     * @param fontName
     * @param size
     * @param style
     * @param color
     * @return
     */
    public Font getFonts(String fontName, int size, String style, int color) {
        Font font = excelBook.createFont();
        font.setFontName(fontName);
        font.setColor((short) color);
        if (style.contains("b")) {
            font.setBold(true);
        }
        if (style.contains("i")) {
            font.setItalic(true);
        }
        font.setFontHeight((short) (size * 20));
        return font;
    }
    
    /**
     * 初始化新文档
     * @author sunqinqiu 
     * @date   2018-05-09 15:53
     */
    private void initNewExcel(String sheetName) {
        excelBook = new HSSFWorkbook();
        this.creatNewExcelSheet(sheetName);
        this.excelSheet.setDefaultRowHeight(pixToNumber(30));
    }
    
    /**
     * 合并单元格
     * @author sunqinqiu 
     * @date   2018-05-09 19:44
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @param content
     * @return
     */
    public HSSFCell mergeCell(int firstRow, int lastRow, int firstCol, int lastCol, String content) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        excelSheet.addMergedRegion(cellRangeAddress);
        return setValue(firstRow, firstCol, content);
    }
    
    /**
     * 在EXCEL中用像素数转成EXCEL里面的数值
     * @author sunqinqiu 
     * @date   2018-05-09 15:51
     * @param pix
     * @return
     */
    private short pixToNumber(int pix) {
        return (short) (pix * IOContent.EXCEL_PIX_EV_NUM);
    }
    
    /**
     * 保存
     * @author sunqinqiu 
     * @date   2018-05-09 19:44
     * @param path
     */
    public void save(String path) {
        FileUtil.checkDir(path);
        try (FileOutputStream out = new FileOutputStream(path.isEmpty() ? this.path : path)) {
            excelBook.write(out);
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_ERROR_EXCEL_SAVE, e);
        }
    }
    
    /**
     * 根据SHEETNAME查询SHEET
     * @author sunqinqiu 
     * @date   2018-05-09 16:00
     * @param sheetName
     */
    public void selectExcelSheet(String sheetName) {
        excelSheet = sheetName.isEmpty() ? excelBook.getSheetAt(0) : excelBook.getSheet(sheetName);
        if (this.excelSheet == null) {
            creatNewExcelSheet(sheetName);
        }
    }
    
    /**
     * 设置单元格的值
     * @author sunqinqiu 
     * @date   2018-05-09 19:37
     * @param row
     * @param cell
     * @param value
     * @return
     */
    public HSSFCell setValue(int row, int cell, String value) {
        HSSFCell cellItem = creatRowAndCell(row, cell);
        cellItem.setCellValue(value);
        return cellItem;
    }
}
