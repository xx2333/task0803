package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.constant.MessageConstant;
import com.lwy.entity.Result;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ReportService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        List<String> months = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            months.add(sdf.format(calendar.getTime()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("months", months);
        List<Integer> memberCount = reportService.getMemberCountsByMonths(months);
        map.put("memberCount", memberCount);
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        try {
            List<Map<String, Object>> list = reportService.getSetmealReport();
            Map<String, Object> map = new HashMap<>();
            map.put("setmealCount", list);
            List<String> setmealNames = new ArrayList<>();
//            list.forEach(m -> setmealNames.add((String) m.get("name")));
            list.forEach(m -> setmealNames.addAll( m.keySet()));
            map.put("setmealNames", setmealNames);
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request,HttpServletResponse response){
        try {
            Map<String, Object> reportData = reportService.getBusinessReportData();
            String path = request.getSession().getServletContext().getRealPath("/template/report_template.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(path);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue((String)reportData.get("reportDate"));
            row=sheet.getRow(4);
            row.getCell(5).setCellValue(String.valueOf((Integer)reportData.get("todayNewMember")));
            row.getCell(6).setCellValue(String.valueOf((Integer)reportData.get("totalMember")));

            row=sheet.getRow(5);
            row.getCell(5).setCellValue(String.valueOf((Integer)reportData.get("thisWeekNewMember")));
            row.getCell(6).setCellValue(String.valueOf((Integer)reportData.get("thisMonthNewMember")));

            row=sheet.getRow(7);
            row.getCell(5).setCellValue(String.valueOf((Integer)reportData.get("todayOrderNumber")));
            row.getCell(6).setCellValue(String.valueOf((Integer)reportData.get("todayVisitsNumber")));
            row=sheet.getRow(8);
            row.getCell(5).setCellValue(String.valueOf((Integer)reportData.get("thisWeekOrderNumber")));
            row.getCell(6).setCellValue(String.valueOf((Integer)reportData.get("thisWeekVisitsNumber")));
            row=sheet.getRow(9);
            row.getCell(5).setCellValue(String.valueOf((Integer)reportData.get("thisMonthOrderNumber")));
            row.getCell(6).setCellValue(String.valueOf((Integer)reportData.get("thisMonthVisitsNumber")));

            List<Map<String, Object>> hotSetmeal = (List<Map<String, Object>>) reportData.get("hotSetmeal");
            if (hotSetmeal!=null) {
                int n=12;
                for (Map<String, Object> map : hotSetmeal) {
                    row = sheet.getRow(n);
                    n++;
                    row.getCell(4).setCellValue((String)map.get("name"));
                    row.getCell(5).setCellValue(((Long) map.get("setmeal_count")));
                    row.getCell(6).setCellValue(((BigDecimal)map.get("proportion")).doubleValue());
                }
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            ServletOutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
            workbook.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
    }
}
