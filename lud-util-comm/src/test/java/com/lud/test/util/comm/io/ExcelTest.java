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
package com.lud.test.util.comm.io;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.comm.io.Excel;
import com.lud.util.comm.server.ServerResource;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 15:18
 */
public class ExcelTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @Test
    public void test() {
        String path = ServerResource.getAbsolutePath("/resource-test/comm/test.xlsx");
        Excel excel = new Excel(path, "DATA");
        excel = new Excel("DATA");
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Font font = excel.getFonts("微软雅黑", 12, i % 2 == 0 ? "b" : "i", Font.COLOR_RED);
                HSSFCellStyle style = excel.getCellStyle(j % 2 == 0, j % 2 == 0, j % 2 == 0, Font.COLOR_RED);
                style.setFont(font);
                HSSFCell cell = excel.creatRowAndCell(i, j);
                cell.setCellStyle(style);
                excel.setValue(i, j, i * j + "");
            }
        }
        excel.mergeCell(0, 5, 0, 5, "M");
        excel.save(path);
        loger.trace(path);
        path = ServerResource.getAbsolutePath("/resource-test/comm/xls.xls");
        excel = new Excel(path, "泵");
        excel.save(path);
        loger.trace(path);
    }
}
