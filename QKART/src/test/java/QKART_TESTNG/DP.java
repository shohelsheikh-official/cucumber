package QKART_TESTNG;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.Table.Cell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;


public class DP {
    @DataProvider (name = "data-provider")
    public Object[][] dpMethod (Method m) throws IOException{
        FileInputStream file = new FileInputStream("/src/test/resources/Dataset.xlsx");
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheet("TestCase01");

        switch (m.getName()) {
            case "TestCase01":

                // Iterator<Row> rowIterator = sheet.iterator();
                // while (rowIterator.hasNext()) {
                //     Row row = rowIterator.next();
                //     Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.iterator();
                //     while (cellIterator.hasNext()) {
                //         org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                //         // Process the cell value

                //         DataFormatter dataFormatter = new DataFormatter();
                //         String cellValue = dataFormatter.formatCellValue(cell);

                Row row = sheet.getRow(0);
                Cell cell = (Cell) row.getCell(0);
                System.out.println(cell);
                System.out.println(sheet.getRow(0).getCell(0));

        }


        //         return new Object[][] {{2, 3 , 5}, {5, 7, 9}};
        // case "Diff":
        //         return new Object[][] {{2, 3, -1}, {5, 7, -2}};
        // }


        return null;
    }
}
