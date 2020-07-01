package com.zte.shopping.util;

import com.zte.shopping.entity.ProductType;
import com.zte.shopping.entity.Staff;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
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
/*

        System.out.println(file.getOriginalFilename());

        if (file.getName().matches("^.+\\.(?i)(xls)|(xlsx)$")) {
            if (file.getName().matches("^.+\\.(?i)(xls)$")) {

                workbook = new HSSFWorkbook(file.getInputStream());
                HSSFSheet sheet = (HSSFSheet) workbook.getSheet("Sheet1");
                System.out.println(sheet.getPhysicalNumberOfRows());
                for (int i = 0; i < sheet.getPhysicalNumberOfRows() - 1; i++) {
                    HSSFRow row   = sheet.getRow(i + 1);
                    String  cell  = row.getCell(1).getStringCellValue();
                    double  cell1 = row.getCell(2).getNumericCellValue();
                    System.out.println(cell1);
                    ProductType productType = new ProductType();
                    productType.setName(cell);
                    productType.setStatus(new Double(cell1).intValue());
                    productTypeList.add(productType);
                }

            } else if (file.getName().matches("^.+\\.(?i)(xlsx)$")) {
*/

        workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = (XSSFSheet) workbook.getSheet("Sheet1");
        System.out.println(sheet.getPhysicalNumberOfRows());
        for (int i = 0; i < sheet.getPhysicalNumberOfRows() - 1; i++) {
            XSSFRow row   = sheet.getRow(i + 1);
            String  cell  = row.getCell(1).getStringCellValue();
            double  cell1 = row.getCell(2).getNumericCellValue();
            System.out.println(cell1);
            ProductType productType = new ProductType();
            productType.setName(cell);
            productType.setStatus(new Double(cell1).intValue());
            productTypeList.add(productType);
        }
/*
            }
        }*/

        return productTypeList;
    }


    public void exportStaff(List<Staff> staffList, OutputStream outputStream){
        try {
            // 1.创建一个工作簿Workbook
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 2.创建工作表 Sheet
            HSSFSheet sheet = workbook.createSheet("Staff");
            sheet.setDefaultRowHeightInPoints(20);  // 设置行高
            sheet.setDefaultColumnWidth(12);        // 设置列宽

            // 合并单元格:CellRangeAddress
            // (1)将第一个Row中的3列合并成一列
            // new CellRangeAddress(0, 0, 0, 2); 下表从0开始   从第1行   第1行   第1列   第3列  合并单元格
            CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 7);
            // (2)做合并单元格操作
            sheet.addMergedRegion(cellRangeAddress);

            // 3.创建行
            // a.创建标题行,并设置标题
            HSSFRow rowTitle = sheet.createRow(0);
            rowTitle.setHeightInPoints(40);   // 行高

            HSSFCell cellTitle = rowTitle.createCell(0);   // 设置标题列
            cellTitle.setCellStyle(createCellTitleStyle(workbook, HSSFColor.RED.index, (short) 14));
            cellTitle.setCellValue("Staff列表");

            // b.创建导出哪些数据的提示信息
            HSSFRow rowTip = sheet.createRow(1);
            String[] tips = {"序号","姓名","帐号","电话","邮箱","部门","状态","角色"};

            for (int i = 0; i < tips.length; i++) {
                HSSFCell cellTip = rowTip.createCell(i);
                cellTip.setCellStyle(createCellTitleStyle(workbook, HSSFColor.BLACK.index, (short) 12));
                // 往提示信息行中加入7列  设置信息行中单元格Cell的内容
                cellTip.setCellValue(tips[i]);
            }

            // 4.遍历信息列表 塞入到对应的单元格中
            if (staffList != null && staffList.size() != 0) {
                for (int i = 0; i < staffList.size(); i++) {
                    Staff staff = staffList.get(i);

                    HSSFRow rowContent = sheet.createRow(i + 2);

                    // 为每一列加入信息
                    HSSFCell cellContent0 = rowContent.createCell(0);
                    HSSFCell cellContent1 = rowContent.createCell(1);
                    HSSFCell cellContent2 = rowContent.createCell(2);
                    HSSFCell cellContent3 = rowContent.createCell(3);
                    HSSFCell cellContent4 = rowContent.createCell(4);
                    HSSFCell cellContent5 = rowContent.createCell(5);
                    HSSFCell cellContent6 = rowContent.createCell(6);
                    HSSFCell cellContent7 = rowContent.createCell(7);

                    // "序号","姓名","帐号","电话","邮箱","部门","状态","角色"
                    cellContent0.setCellValue(i + 1 + "");
                    cellContent1.setCellValue(staff.getStaffName());
                    cellContent2.setCellValue(staff.getLoginName());
                    cellContent3.setCellValue(staff.getPhone());
                    cellContent4.setCellValue(staff.getEmail());
                    cellContent5.setCellValue(staff.getDept().getDeptName());

                    if(staff.getIsValid()==1){
                        cellContent6.setCellValue("有效");
                    }else{
                        cellContent6.setCellValue("无效");
                    }

                    if(staff.getRole().equals("2001")){
                        cellContent7.setCellValue("系统管理员");
                    }
                }
            }

            workbook.write(outputStream);
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("success");
    }

    public List<Staff> produceStaff(CommonsMultipartFile file) throws IOException {

        Workbook workbook;
        List<Staff> staffList = new ArrayList<>();
/*

        System.out.println(file.getOriginalFilename());

        if (file.getName().matches("^.+\\.(?i)(xls)|(xlsx)$")) {
            if (file.getName().matches("^.+\\.(?i)(xls)$")) {

                workbook = new HSSFWorkbook(file.getInputStream());
                HSSFSheet sheet = (HSSFSheet) workbook.getSheet("Sheet1");
                System.out.println(sheet.getPhysicalNumberOfRows());
                for (int i = 0; i < sheet.getPhysicalNumberOfRows() - 1; i++) {
                    HSSFRow row   = sheet.getRow(i + 1);
                    String  cell  = row.getCell(1).getStringCellValue();
                    double  cell1 = row.getCell(2).getNumericCellValue();
                    System.out.println(cell1);
                    ProductType productType = new ProductType();
                    productType.setName(cell);
                    productType.setStatus(new Double(cell1).intValue());
                    productTypeList.add(productType);
                }

            } else if (file.getName().matches("^.+\\.(?i)(xlsx)$")) {
*/

        workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = (XSSFSheet) workbook.getSheet("Sheet1");

        System.out.println(sheet.getPhysicalNumberOfRows());

        for (int i = 0; i < sheet.getPhysicalNumberOfRows() - 1; i++) {

            XSSFRow row   = sheet.getRow(i + 1);
            String  cell  = row.getCell(1).getStringCellValue();
            double  cell1 = row.getCell(2).getNumericCellValue();

            System.out.println(cell1);

            Staff staff = new Staff();
//            productType.setName(cell);
//            productType.setStatus(new Double(cell1).intValue());
//            productTypeList.add(productType);
        }
/*
            }
        }*/

        return staffList;
    }
}

