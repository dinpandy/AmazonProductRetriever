package com.amazon.productretriever;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class MaptoExcel {
	public static void main(String args[]) {
		
	
    Map<String, Double>  data = new LinkedHashMap<String, Double>();
    data.put("P1", 0.0);
    data.put("P2", 2.01);
    data.put("P3", 5555.22);  
	ExcelCreation(data);
	}
    public static  void ExcelCreation(Map<String, Double> data)
	{
    	HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");
    Set<String> keyset = data.keySet();
    Double max = Collections.max(data.values());
        
    Double minm = Double.MAX_VALUE; 
    
        for(Map.Entry<String, Double> entry : data.entrySet()) {
        if(entry.getValue() < minm && entry.getValue() != 0.0) {
            minm = entry.getValue();
            
        }
    }
    System.out.println("Minm Value is "+minm);
    Map <String, Double> maxProductMap = new LinkedHashMap<String, Double>();
    Map <String, Double> minProductMap = new LinkedHashMap<String, Double>();
    int rownum = 0;
    
    Row rowHeading = sheet.createRow(rownum++);
    rowHeading.createCell(0).setCellValue("Product Name");
    rowHeading.createCell(1).setCellValue("Product Amount");  
     
    for (String key : keyset) {
        Row row = sheet.createRow(rownum++);
        Double productValue = data.get(key);
        int cellnum = 0;
               	Cell cell = row.createCell(cellnum++);    
                cell.setCellValue(key);
                Cell cell2 = row.createCell(cellnum++);    
               
                cell2.setCellValue("$"+(Double)productValue);
                if(productValue.equals(max)) {
                   maxProductMap.put(key,productValue);
                }else if (productValue.equals(minm)) {              
                	minProductMap.put(key,productValue);
                }                    
    }
        
    try {
        FileOutputStream out
                = new FileOutputStream(new File("ProductDetails.xls"));
        workbook.write(out);
        out.close();
        System.out.println("Minimum Price Product details: ");
        for (String keys : minProductMap.keySet())
        {
        	   System.out.println("Product Name =>" + keys + " Product Value => $" + minProductMap.get(keys));
           
        }
        System.out.println("Maximum Price Product details: ");
        for (String keys : maxProductMap.keySet())
        {
        	 System.out.println("Product Name =>" + keys + " Product Value => $" + maxProductMap.get(keys));
                     
        }
      

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
	}
}