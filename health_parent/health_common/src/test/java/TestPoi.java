
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 问题1,每次读取得设置单元格类型cell
 *                    加:        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
 *                   读不了数字:  String value = cell.getStringCellValue();
 *
 * 问题2,写出xlsx文件损坏
 *
 *
 * 问题3,工具类读不了date类型
 *
 */





public class TestPoi {
    //读取第一种方式
    @Test
    public void test01() throws IOException {

        //1.创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook("F:\\rrr.xlsx");
        //2,得到工作表,第一个表
        XSSFSheet sheet = workbook.getSheetAt(0);

        //3.遍历工作表
        for (Row row : sheet) {
            //4.遍历得到格子,取数据
            for (Cell cell : row) {
                /**
                 * int的cell声明成String的cell格式
                 */
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                String value = cell.getStringCellValue();
                System.out.println("value = " + value);
            }
        }

    }

    //读取第二种方式
    @Test
    public void testRead02() throws IOException {

        //1. 创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook("F:\\rrr.xlsx");

        //2. 得到工作表 得到第一个工作表。
        XSSFSheet sheet = wb.getSheetAt(0);

        //3. 得到最后一行的下标
        int lastRowNum = sheet.getLastRowNum();

        //行号从0开始。
        for (int i = 0; i <= lastRowNum; i++) {
            //取出来每一行
            XSSFRow row = sheet.getRow(i);
            /**
             * int的cell声明成String的cell格式
             */

//            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
//            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            //获取最后一个格子的下标
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                Cell cell=row.getCell(j);
                /**
                 * 第二种方式
                 */

                cell.setCellType(XSSFCell.CELL_TYPE_STRING);


                System.out.println(cell.getStringCellValue());
            }
        }

        wb.close();//工作簿关闭
    }

    @Test
    public void testWrite() throws IOException {
        //定义工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
                 //wb.setWorkbookType(XSSFWorkbookType.XLSX);
        //创建工作表
        XSSFSheet sheet = wb.createSheet("成绩单");

        //构建第一行,填充格子内容
        XSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("课程");
        row.createCell(1).setCellValue("数学");
        row.createCell(2).setCellValue("语文");
        //第二行
        XSSFRow row1 = sheet.createRow(1);

        row1.createCell(0).setCellValue("个人成绩");
        row1.createCell(1).setCellValue("67");
        row1.createCell(2).setCellValue("18");

        //第三行
        XSSFRow row2 = sheet.createRow(2);

        row2.createCell(0).setCellValue("平均成绩");
        row2.createCell(1).setCellValue("89");
        row2.createCell(2).setCellValue("28");

        //4.把工作簿保存到硬盘上某个文件中
        FileOutputStream fos = new FileOutputStream("F:\\chao.xlsx");
        //5.收尾
        fos.flush();
        fos.close();
        wb.close();
//TODO~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    }

}
