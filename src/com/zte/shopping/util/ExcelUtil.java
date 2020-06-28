package com.zte.shopping.util;

import com.zte.shopping.entity.ProductType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eytins
 */

public class ExcelUtil {

    public void exportProductType(List<ProductType> productTypeList, OutputStream outputStream) {
        try {
            // 1.创建一个工作簿Workbook
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 2.创建工作表 Sheet
            HSSFSheet sheet = workbook.createSheet("productType");
            sheet.setDefaultRowHeightInPoints(20);  // 设置行高
            sheet.setDefaultColumnWidth(12);        // 设置列宽

            // 合并单元格:CellRangeAddress
            //     (1)将第一个Row中的3列合并成一列
            //     new CellRangeAddress(0, 0, 0, 2); 下表从0开始   从第1行   第1行   第1列   第3列  合并单元格
            CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 2);
            //     (2)做合并单元格操作
            sheet.addMergedRegion(cellRangeAddress);

            // 3.创建行
            //   a.创建标题行,并设置标题
            HSSFRow rowTitle = sheet.createRow(0);
            rowTitle.setHeightInPoints(40);   // 行高
            HSSFCell cellTitle = rowTitle.createCell(0);   // 设置标题列
            cellTitle.setCellStyle(createCellTitleStyle(workbook, HSSFColor.RED.index, (short) 14));
            cellTitle.setCellValue("商品信息列表");

            //   b.创建导出哪些数据的提示信息
            HSSFRow rowTip = sheet.createRow(1);
            //
            String[] tips = {"序号", "商品类型", "状态"};

            for (int i = 0; i < tips.length; i++) {
                HSSFCell cellTip = rowTip.createCell(i);
                cellTip.setCellStyle(createCellTitleStyle(workbook, HSSFColor.BLACK.index, (short) 12));
                // 往提示信息行中加入7列  设置信息行中单元格Cell的内容
                cellTip.setCellValue(tips[i]);
            }

            // 4.遍历学生信息列表 塞入到对应的单元格中
            if (productTypeList != null && productTypeList.size() != 0) {
                for (int i = 0; i < productTypeList.size(); i++) {
                    ProductType productType = productTypeList.get(i);

                    HSSFRow rowContent = sheet.createRow(i + 2);

                    // 第1列: 序号
                    HSSFCell cellContent0 = rowContent.createCell(0);
                    cellContent0.setCellValue(i + 1 + "");

                    // 第2列: 用户名
                    HSSFCell cellContent1 = rowContent.createCell(1);
                    cellContent1.setCellValue(productType.getName());

                    // 第3列: 姓名
                    HSSFCell cellContent2 = rowContent.createCell(2);
                    cellContent2.setCellValue(productType.getStatus());

                }
            }

            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("success");

    }

    /**
     * Cell设置样式
     *
     * @return
     */
    public static HSSFCellStyle createCellTitleStyle(HSSFWorkbook workbook, short fontColor, short fontSize) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  // 垂直居中

        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(fontColor);
        font.setFontHeightInPoints(fontSize);

        // 将font添加到cellStyle
        cellStyle.setFont(font);

        return cellStyle;
    }

    public List<ProductType> produceProductType(CommonsMultipartFile file) throws IOException {

        Workbook          workbook;
        List<ProductType> productTypeList = new ArrayList<>();

        if (file.getOriginalFilename().matches("^.+\\.(?i)(xls)|(xlsx)$")) {
            if (file.getOriginalFilename().matches("^.+\\.(?i)(xls)$")) {
                workbook = new HSSFWorkbook(new FileInputStream(String.valueOf(file)));
                HSSFSheet sheet = (HSSFSheet) workbook.getSheet("Sheet1");
                for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                    HSSFRow     row         = sheet.getRow(i + 1);
                    HSSFCell    cell        = row.getCell(1);
                    HSSFCell    cell1       = row.getCell(2);
                    ProductType productType = new ProductType();
                    productType.setName(cell.toString());
                    productType.setStatus(Integer.parseInt(cell1.toString()));
                    productTypeList.add(productType);
                }
            } else if (file.getOriginalFilename().matches("^.+\\.(?i)(xlsx)$")) {
                workbook = new XSSFWorkbook(new FileInputStream(String.valueOf(file)));
                XSSFSheet sheet = (XSSFSheet) workbook.getSheet("Sheet1");
                for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                    XSSFRow     row         = sheet.getRow(i + 1);
                    XSSFCell    cell        = row.getCell(1);
                    XSSFCell    cell1       = row.getCell(2);
                    ProductType productType = new ProductType();
                    productType.setName(cell.toString());
                    productType.setStatus(Integer.parseInt(cell1.toString()));
                    productTypeList.add(productType);
                }
            }
            //todo:添加productTypeList到数据库中
        }

        return productTypeList;
    }
}
