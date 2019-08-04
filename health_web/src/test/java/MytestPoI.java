import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class MytestPoI {
    public static void main(String[] args) throws IOException {
        fun();
    }
    public static void fun() throws IOException {
        XSSFWorkbook sheets = new XSSFWorkbook();
        XSSFSheet xsmd = sheets.createSheet("学生名单");
        XSSFRow row1 = xsmd.createRow(0);
        row1.createCell(0).setCellValue("姓名");
        row1.createCell(1).setCellValue("性别");
        row1.createCell(2).setCellValue("地址");
        XSSFRow row02 = xsmd.createRow(1);
        row02.createCell(0).setCellValue("张三");
        row02.createCell(1).setCellValue("男");
        row02.createCell(2).setCellValue("深圳");

        XSSFRow row03 = xsmd.createRow(2);
        row03.createCell(0).setCellValue("李四");
        row03.createCell(1).setCellValue("男");
        row03.createCell(2).setCellValue("北京");

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\studentsheet.xlsx");
        sheets.write(fileOutputStream);
        fileOutputStream.close();
        sheets.close();
    }
}
