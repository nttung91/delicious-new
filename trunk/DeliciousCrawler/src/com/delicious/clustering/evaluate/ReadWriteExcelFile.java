/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delicious.clustering.evaluate;

import com.delicious.clustering.dbscan.DBPoint;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import model.pojo.Link;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author THANHTUNG
 */
public class ReadWriteExcelFile {

    public void writeResultToExcelFile(ArrayList<DBPoint> SetOfPoints, List<Link> l, int numberCluster, String filename) throws FileNotFoundException, IOException, Exception {
        int max = numberCluster;
        XSSFWorkbook wb = new XSSFWorkbook();
        
        Map<String, File> sheets = new HashMap<String, File>();
        //add sheet
        for (int i = -2; i <= max; i++) {
            if (i == -1) {
                continue;
            }
            String sheetname = "";
            if (i == -2) {
                sheetname = "Noise";
            } else {
                sheetname = "Cluster" + (i + 1);
            }
            addSheet(sheetname, wb, sheets);

        }

        //save the template  
        FileOutputStream os = new FileOutputStream("template.xlsx");
        wb.write(os);
        os.close();
        
        ArrayList<Map.Entry<String, File>> sheetList = new ArrayList<>();
        sheetList.addAll(sheets.entrySet());
         int s=0;
        //JOptionPane.showMessageDialog(null, sheetList.size());
        for (int i = -2; i <= max; i++) {


            System.out.println("---------------Cluster " + (i));
            int count = 0;
            ArrayList<Link> arr = new ArrayList<>();
            for (int j = 0; j < SetOfPoints.size(); j++) {

                if (SetOfPoints.get(j).getClusterID() == i) {
                    count++;
                    arr.add(l.get(SetOfPoints.get(j).getId()));
                    //System.out.printf("%s ", l.get(SetOfPoints.get(j).getId()).getUrl());
//                    List<Object[]> ll = dao.getDistinctTags(l.get(SetOfPoints.get(j).getId()));
//                    for (int k = 0; k < ll.size(); k++) {
//                        System.out.printf("%s(%.0f) ", ll.get(k)[0].toString(), Double.parseDouble(ll.get(k)[1].toString()));
//                    }
//                    System.out.println();
                }
            }
            System.out.printf("Numbers of Cluster %d is %d\n", i, count);
            if (count > 0) {
                DataCollect dao = new DataCollect();
                ArrayList<String> TagList = new ArrayList<>();
                double[][] kq = dao.getListVector(arr, TagList);
                (new Visualize()).Visualize1(kq);
                //write kq to file
                //write header
                Map.Entry<String, File> entry = sheetList.get(s);
             //   JOptionPane.showMessageDialog(null, s);
                Writer fw = new FileWriter(entry.getValue());
                writeData(fw, kq, TagList);
                 fw.close();
                s++;
            }
        }
         String fout = filename + ".xlsx";
        FileOutputStream out = new FileOutputStream(fout);
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
    private static void writeData(Writer out, double[][] kq, ArrayList<String> TagList) throws Exception {
         SpreadsheetWriter sw = new SpreadsheetWriter(out);
        sw.beginSheet();

        int r = 0;
        sw.insertRow(r);
        
        for (int j = 0; j < kq[0].length && j < 16384; j++) {
            sw.createCell(j, TagList.get(j));
        }
        sw.endRow();
        r++;
        for (int m = 0; m < kq.length; m++) {
            System.out.println(m);
            sw.insertRow(r);
            for (int n = 0; n < kq[0].length && n < 16384; n++) {
                // System.out.printf("%4.0f ", kq[i][j]);
                sw.createCell(n, kq[m][n]);
            }
            sw.endRow();
            r++;
        }
        sw.endSheet();
    }
    private static void substitute(File zipfile, Map<String, File> sheets, OutputStream out) throws IOException {
        ZipFile zip = new ZipFile(zipfile);

        ZipOutputStream zos = new ZipOutputStream(out);

        @SuppressWarnings("unchecked")
        Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
        while (en.hasMoreElements()) {
            ZipEntry ze = en.nextElement();
            System.out.println(ze.getName());
            if (!sheets.containsKey(ze.getName())) {
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
        while ((count = in.read(chunk)) >= 0) {
            out.write(chunk, 0, count);
        }
    }
}
