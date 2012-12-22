package com.delicious.clustering.evaluate;
import java.io.*;  
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;  
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;  
  
public class BigGridDemoMultiSheet {  
    public static void main(String[] args) throws Exception {  
  
        // Step 1. Create a template file. Setup sheets and workbook-level objects such as  
        // cell styles, number formats, etc.  
  
        XSSFWorkbook wb = new XSSFWorkbook();  
        Map<String, File> sheets = new HashMap<String, File>();  
  
        addSheet("Jwala1", wb, sheets);  
        addSheet("Jwala2", wb, sheets);
         addSheet("Jwala3", wb, sheets);
          addSheet("Jwala4", wb, sheets);
           addSheet("Jwala5", wb, sheets);
  
      
        //save the template  
        FileOutputStream os = new FileOutputStream("template.xlsx");  
        wb.write(os);  
        os.close();  
  
        // generate data for each sheet  
        for (Map.Entry<String, File> entry : sheets.entrySet()) {  
            JOptionPane.showMessageDialog(null, entry.getValue());
            Writer fw = new FileWriter(entry.getValue());  
            generate(fw);  
            fw.close();  
        }  
  
        //Step 3. Substitute the template entry with the generated data  
        FileOutputStream out = new FileOutputStream("MultiSheetBigGrid.xlsx");  
        substitute(new File("template.xlsx"), sheets, out);  
        out.close();  
    }  
  
    private static void addSheet(String name, XSSFWorkbook book, Map<String, File> sheets) throws IOException {  
        XSSFSheet sheet = book.createSheet(name);  
        String ref = sheet.getPackagePart().getPartName().getName().substring(1);  
        File tmp = File.createTempFile("sheet" + (sheets.size() + 1), ".xml");  
        sheets.put(ref, tmp);  
    }  
  
    /** 
     * Create a library of cell styles. 
     */  
    
  
  
    private static void generate(Writer out) throws Exception {  
  
        Random rnd = new Random();  
        Calendar calendar = Calendar.getInstance();  
  
        SpreadsheetWriter sw = new SpreadsheetWriter(out);  
        sw.beginSheet();  
  
        //insert header row  
        sw.insertRow(0);  
      
        sw.createCell(0, "Title");  
        sw.createCell(1, "% Change");  
        sw.createCell(2, "Ratio");  
        sw.createCell(3, "Expenses");  
        //sw.createCell(4, "Date");  
  
        sw.endRow();  
  
        //write data rows  
        for (int rownum = 1; rownum < 100000; rownum++) {  
            sw.insertRow(rownum);  
  
            sw.createCell(0, "Hello, " + rownum + "!");  
            sw.createCell(1, (double)rnd.nextInt(100)/100);  
            sw.createCell(2, (double)rnd.nextInt(10)/10);  
            sw.createCell(3, rnd.nextInt(10000));  
            //sw.createCell(4, calendar, styles.get("date").getIndex());  
  
            sw.endRow();  
  
            calendar.roll(Calendar.DAY_OF_YEAR, 1);  
        }  
        sw.endSheet();  
    }  
  
    /** 
     * 
     * @param zipfile the template file 
     * @param sheets the Map with  
     *        key "name of the sheet entry to substitute  
     *            (e.g. xl/worksheets/sheet1.xml, xl/worksheets/sheet2.xml etc)" 
     *        and value "XML file with the sheet data" 
     * @param out the stream to write the result to 
     */  
    private static void substitute(File zipfile, Map<String, File> sheets, OutputStream out) throws IOException {  
        ZipFile zip = new ZipFile(zipfile);  
  
        ZipOutputStream zos = new ZipOutputStream(out);  
  
        @SuppressWarnings("unchecked")  
        Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();  
        while (en.hasMoreElements()) {  
            ZipEntry ze = en.nextElement();  
            System.out.println(ze.getName());  
            if(!sheets.containsKey(ze.getName())){  
                zos.putNextEntry(new ZipEntry(ze.getName()));  
                InputStream is = zip.getInputStream(ze);  
                copyStream(is, zos);  
                is.close();  
            }  
        }  
  
        for (Map.Entry<String, File> entry : sheets.entrySet()) {  
            System.out.println(entry.getKey());  
            zos.putNextEntry(new ZipEntry(entry.getKey()));  
            InputStream is = new FileInputStream(entry.getValue());  
            copyStream(is, zos);  
            is.close();  
        }  
        zos.close();  
    }  
  
    private static void copyStream(InputStream in, OutputStream out) throws IOException {  
        byte[] chunk = new byte[1024];  
        int count;  
        while ((count = in.read(chunk)) >=0 ) {  
          out.write(chunk,0,count);  
        }  
    }  
  
    /** 
     * Writes spreadsheet data in a Writer. 
     * (YK: in future it may evolve in a full-featured API for streaming data in Excel) 
     */  
    public static class SpreadsheetWriter {  
        private final Writer _out;  
        private int _rownum;  
  
        public SpreadsheetWriter(Writer out){  
            _out = out;  
        }  
  
        public void beginSheet() throws IOException {  
            _out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +  
                    "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">" );  
            _out.write("<sheetData>\n");  
        }  
  
        public void endSheet() throws IOException {  
            _out.write("</sheetData>");  
            _out.write("</worksheet>");  
        }  
  
        /** 
         * Insert a new row 
         * 
         * @param rownum 0-based row number 
         */  
        public void insertRow(int rownum) throws IOException {  
            _out.write("<row r=\""+(rownum+1)+"\">\n");  
            this._rownum = rownum;  
        }  
  
        /** 
         * Insert row end marker 
         */  
        public void endRow() throws IOException {  
            _out.write("</row>\n");  
        }  
  
        public void createCell(int columnIndex, String value, int styleIndex) throws IOException {  
            String ref = new CellReference(_rownum, columnIndex).formatAsString();  
            _out.write("<c r=\""+ref+"\" t=\"inlineStr\"");  
            if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");  
            _out.write(">");  
            _out.write("<is><t>"+value+"</t></is>");  
            _out.write("</c>");  
        }  
  
        public void createCell(int columnIndex, String value) throws IOException {  
            createCell(columnIndex, value, -1);  
        }  
  
        public void createCell(int columnIndex, double value, int styleIndex) throws IOException {  
            String ref = new CellReference(_rownum, columnIndex).formatAsString();  
            _out.write("<c r=\""+ref+"\" t=\"n\"");  
            if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");  
            _out.write(">");  
            _out.write("<v>"+value+"</v>");  
            _out.write("</c>");  
        }  
  
        public void createCell(int columnIndex, double value) throws IOException {  
            createCell(columnIndex, value, -1);  
        }  
  
        public void createCell(int columnIndex, Calendar value, int styleIndex) throws IOException {  
            createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);  
        }  
    }  
}  