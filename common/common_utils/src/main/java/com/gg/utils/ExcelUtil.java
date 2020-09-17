package com.gg.utils;

import org.apache.poi.ss.usermodel.*;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class ExcelUtil {
    /**
     * controller 调用入口
     *
     * @param file   前端上传文件
     * @param header 头部所在行（0行算起）
     * @return
     */
    public static Map<String, Object> getExcelData(MultipartFile file, int header) {
        Map<String, Object> result = new HashMap<>(16);
        if (file == null || file.isEmpty()) {
            result.put("msg", "文件不存在!");
            result.put("code", "1");
            return result;
        }

        Workbook wbs = null;//工作簿对象
        try {
            wbs = WorkbookFactory.create(file.getInputStream());
        } catch (IOException e) {
            result.put("msg", "读取excel文件错误");
            result.put("code", "1");
            return result;
        }
        if (wbs != null) {
            List<Object> sheetDataList = new ArrayList<>();
            List<Object> sheetList = new ArrayList<>();
            if (wbs.getNumberOfSheets() == 0) {
                result.put("msg", "上传excel文件不存在工作表");
                result.put("code", "1");
                return result;
            }
            Map<String, String> cellValueMap = null;
            //依次遍历每个sheet的数据
            for (int sheetNum = 0; sheetNum < wbs.getNumberOfSheets(); sheetNum++) {
                Sheet sheet = wbs.getSheetAt(sheetNum);
                List<Object> currentSheetDataList = new ArrayList<>();
                if (sheet != null) {
                    int rowCnt = sheet.getLastRowNum();//总行数
                    //总行数小于header行(排除空数据)继续读下一张sheet数据
                    if (rowCnt < header + 1) {
                        continue;
                    }
                    //逐行获取Excel列数据

                    for (int rowNum = header + 1; rowNum <= rowCnt; rowNum++) {//第header+1行开始读取数据
                        Row rowData = sheet.getRow(rowNum);
                        if (rowData == null) {//剔除空行
                            continue;
                        }
                        //读取每行的每列数据
                        cellValueMap = new HashMap<>(16);
                        for (int cellNum = 0; cellNum <= rowData.getLastCellNum() && sheet.getRow(header).getCell(cellNum) != null; cellNum++) {
                            String cellName = sheet.getRow(header).getCell(cellNum).getStringCellValue();//字段名
                            String cellValue = rowData.getCell(cellNum) == null ? "" : getCellValue(rowData.getCell(cellNum)).trim();//字段值
                            cellValueMap.put(cellName, cellValue);
                        }
                        currentSheetDataList.add(cellValueMap);
                        sheetDataList.add(cellValueMap);//每一个sheet
                    }
                    sheetList.add(currentSheetDataList);
                }
            }
            result.put("code", "0");
//            result.put("data", sheetDataList);
            result.put("data", sheetList);
            result.put("beans",sheetDataList);
        }
        return result;
    }

    private static String getCellValue(Cell cell) {
        String value = "";

        switch (cell.getCellType().getCode()) {
            case 0: {
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    value = new DateTime(date).toString("yyyy-MM-dd");
                } else value = cell.getNumericCellValue() + "";
                break;
            }
            case 1:
                value = cell.getStringCellValue();
                break;
            case 2:
                value = cell.getCellFormula();
                break;
            case 4:
                value = cell.getBooleanCellValue() + "";
                break;
            default:
                break;
        }
        return value;
    }
}