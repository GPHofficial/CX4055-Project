
/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.jfree.chart.ChartFactory
 *  org.jfree.chart.ChartPanel
 *  org.jfree.chart.JFreeChart
 *  org.jfree.chart.plot.PlotOrientation
 *  org.jfree.data.xy.XYDataset
 *  org.jfree.data.xy.XYSeries
 *  org.jfree.data.xy.XYSeriesCollection
 *  org.jfree.ui.ApplicationFrame
 */
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
 
 public class PATMain {
     private static final long serialVersionUID = 1L;
     private static final Logger LOGGER = Logger.getLogger(PATMain.class.getName());
     private int numberOfTraces = 0;
     private int numberOfTracePoints = 0;
     private File traceFile;
     private BufferedReader br;
     private double[][] traceMatrix;
     private String[] plainText;
     private String[] cipherText;
     private int[][] hypothesis;
     private double[][] cor;
     private int[] key;
     private int plaintextCol;
     private int extraColFront;
     private int extraColRear;
     private String cipher;
     private String[] supportedCiphers = new String[]{"AES128"};
     private FileWriter csvWriter;
 
     public PATMain() {
        this.plaintextCol = 0;
        this.extraColFront = 2;
        this.extraColRear = 0;
        this.cipher = "AES128";
        LOGGER.info("Creating Trace object");
     }
 
     public void init(File fileName, File correlationFile) throws IOException {

         this.csvWriter = new FileWriter(correlationFile);
         LOGGER.info("Initializing Trace object");
         this.traceFile = fileName;
         this.br = new BufferedReader(new FileReader(this.traceFile));
         int count = 1;
         this.numberOfTracePoints = this.br.readLine().split(",").length - (this.extraColFront + this.extraColRear);
         while (this.br.readLine() != null) {
             ++count;
         }
         this.numberOfTraces = count;
         this.br.close();
     }
 
     public int getNumberOfTraces() {
         return this.numberOfTraces;
     }
 
     public int getNumberOfTracePoints() {
         return this.numberOfTracePoints;
     }
 
     public void initTraceMatrix() throws IOException {
         this.br = new BufferedReader(new FileReader(this.traceFile));
         this.traceMatrix = new double[this.numberOfTraces][this.numberOfTracePoints];
         this.plainText = new String[this.numberOfTraces];
         this.cipherText = new String[this.numberOfTraces];
         for (int count = 0; count < this.numberOfTraces; ++count) {
             String[] t = this.br.readLine().split(",");
             this.plainText[count] = t[this.plaintextCol];
             this.cipherText[count] = t[1];
             for (int j = this.extraColFront; j < this.numberOfTracePoints - this.extraColRear; ++j) {
                 this.traceMatrix[count][j - this.extraColFront] = Double.parseDouble(t[j]);
             }
         }
         this.br.close();
     }
 
     public int HW(int n) {
         return Integer.bitCount(n);
     }
 
     public int hex2int(String hex) {
         HashMap<String, Integer> hexmap = new HashMap<String, Integer>();
         hexmap.put("0", 0);
         hexmap.put("1", 1);
         hexmap.put("2", 2);
         hexmap.put("3", 3);
         hexmap.put("4", 4);
         hexmap.put("5", 5);
         hexmap.put("6", 6);
         hexmap.put("7", 7);
         hexmap.put("8", 8);
         hexmap.put("9", 9);
         hexmap.put("A", 10);
         hexmap.put("B", 11);
         hexmap.put("C", 12);
         hexmap.put("D", 13);
         hexmap.put("E", 14);
         hexmap.put("F", 15);
         return (Integer)hexmap.get(hex.charAt(0) + "") << 4 ^ (Integer)hexmap.get(hex.charAt(1) + "");
     }
 
     public int SBOX(int a) {
         int[] s = new int[]{99, 124, 119, 123, 242, 107, 111, 197, 48, 1, 103, 43, 254, 215, 171, 118, 202, 130, 201, 125, 250, 89, 71, 240, 173, 212, 162, 175, 156, 164, 114, 192, 183, 253, 147, 38, 54, 63, 247, 204, 52, 165, 229, 241, 113, 216, 49, 21, 4, 199, 35, 195, 24, 150, 5, 154, 7, 18, 128, 226, 235, 39, 178, 117, 9, 131, 44, 26, 27, 110, 90, 160, 82, 59, 214, 179, 41, 227, 47, 132, 83, 209, 0, 237, 32, 252, 177, 91, 106, 203, 190, 57, 74, 76, 88, 207, 208, 239, 170, 251, 67, 77, 51, 133, 69, 249, 2, 127, 80, 60, 159, 168, 81, 163, 64, 143, 146, 157, 56, 245, 188, 182, 218, 33, 16, 255, 243, 210, 205, 12, 19, 236, 95, 151, 68, 23, 196, 167, 126, 61, 100, 93, 25, 115, 96, 129, 79, 220, 34, 42, 144, 136, 70, 238, 184, 20, 222, 94, 11, 219, 224, 50, 58, 10, 73, 6, 36, 92, 194, 211, 172, 98, 145, 149, 228, 121, 231, 200, 55, 109, 141, 213, 78, 169, 108, 86, 244, 234, 101, 122, 174, 8, 186, 120, 37, 46, 28, 166, 180, 198, 232, 221, 116, 31, 75, 189, 139, 138, 112, 62, 181, 102, 72, 3, 246, 14, 97, 53, 87, 185, 134, 193, 29, 158, 225, 248, 152, 17, 105, 217, 142, 148, 155, 30, 135, 233, 206, 85, 40, 223, 140, 161, 137, 13, 191, 230, 66, 104, 65, 153, 45, 15, 176, 84, 187, 22};
         return s[a];
     }
 
     public void initHypothesis_MCU8_AES128(int byteNumber) throws IOException {
         int i;
         int[] keyHyp = new int[256];
         for (i = 0; i < 255; ++i) {
             keyHyp[i] = i;
         }
         this.hypothesis = new int[this.plainText.length][keyHyp.length];
         for (i = 0; i < this.plainText.length; ++i) {
             String P = this.plainText[i].substring(2 * (byteNumber - 1), 2 * byteNumber);
             for (int j = 0; j < keyHyp.length; ++j) {
                 int temp1 = this.hex2int(P) ^ keyHyp[j];
                 int temp2 = this.SBOX(temp1);
                 int temp = this.HW(temp2);
                 if(j == 255){
                     System.out.println("x");
                 }
                 this.hypothesis[i][j] = temp;
             }
         }
     }
 
     public double Correlation(double[] xs, double[] ys) {
         double sx = 0.0;
         double sy = 0.0;
         double sxx = 0.0;
         double syy = 0.0;
         double sxy = 0.0;
         int n = xs.length;
         for (int i = 0; i < n; ++i) {
             double x = xs[i];
             double y = ys[i];
             sx += x;
             sy += y;
             sxx += x * x;
             syy += y * y;
             sxy += x * y;
         }
         double cov = sxy / (double)n - sx * sy / (double)n / (double)n;
         double sigmax = Math.sqrt(sxx / (double)n - sx * sx / (double)n / (double)n);
         double sigmay = Math.sqrt(syy / (double)n - sy * sy / (double)n / (double)n);
        
        //  double correlationValue = ;

         return cov / sigmax / sigmay;
     }
 
     public void findCorrelation() {
         this.cor = new double[256][this.numberOfTracePoints];
         double[] x = new double[this.numberOfTraces];
         double[] y = new double[this.numberOfTraces];
         int i = 0;
         for (int count = 0; count <= 255; ++count) {
             for (int j = 0; j < this.numberOfTraces; j++) {
                 y[j] = (double)this.hypothesis[j][count] / 256.0;
             }
             for (i = 0; i < this.numberOfTracePoints; i++) {
                // if(count == 1 && i == 0){
                //     System.out.println("x");
                // }
                 for (int j = 0; j < this.numberOfTraces; ++j) {
                     x[j] = this.traceMatrix[j][i];
                 }
                 
                 this.cor[count][i] = this.Correlation(x, y);
             }
         }


        

        try{
            for (int count = 0; count <= 255; ++count) {
                List<String> stringArray = doubleArrayToStringArray(this.cor[count], this.numberOfTracePoints);
                csvWriter.append(String.join(",", stringArray));
                csvWriter.append("\n");
            }
            csvWriter.flush();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
     }

     public List<String> doubleArrayToStringArray(double[] doubleArray,int range){
         List<String> stringArray = new ArrayList<String>();
         for(int i = 0;i<range;++i){
            // stringArray.add(Double.toString(doubleArray[i]));
            stringArray.add(String.format("%.5f", doubleArray[i]));
         }
         return stringArray;
     }
 
     public int findKey() {
         double max = this.cor[0][0];
         int loc = 0;
         for (int i = 0; i < 256; ++i) {
             for (int j = 0; j < this.numberOfTracePoints; ++j) {
                 if (!(this.cor[i][j] > max)) continue;
                 max = this.cor[i][j];
                 loc = i;
             }
         }
         return loc;
     }
 
     public HashMap<Object, Object> CPA(int keysize) throws IOException {
         this.key = new int[keysize];
         LOGGER.info("Running Analysis...");
         this.initTraceMatrix();
         long start = System.currentTimeMillis();
         for (int i = 1; i <= keysize; ++i) {
             this.initHypothesis_MCU8_AES128(i);
             this.findCorrelation();
             this.key[i - 1] = this.findKey();
         }
         long end = System.currentTimeMillis();
         LOGGER.info("Analysis Complete.");
         double timetaken = (end - start) / 1000L;
         String strkey = "";
         String t = "";
         for (int i = 0; i < keysize; ++i) {
             strkey = strkey + ((t = Integer.toHexString(this.key[i]).toUpperCase() + " ").length() < 3 ? "0" + t : t);
             System.out.println("t = " + t + " \t strkey = " + strkey);
         }
         System.out.println(strkey.trim());
         System.out.println("Total Time: " + timetaken + " seconds");
         HashMap<Object, Object> retVal = new HashMap<Object, Object>();
         retVal.put("key", strkey);
         retVal.put("time", timetaken);

         csvWriter.close();

         return retVal;
     }
 
     public static void main(String[] args) throws IOException {
        PATMain ob = new PATMain();
        String filename = "waveform.csv";
        File file = new File("input/" + filename);
        File debugFile = new File("intermediate/" + filename);
        ob.init(file, debugFile);
        HashMap<Object, Object> values = ob.CPA(16);
        System.out.println("\n***********************************************\n");
        System.out.println("Obtained key: " + values.get("key").toString() + "\n");
        System.out.println("\n***********************************************\n");
        System.out.println("Time taken: " + values.get("time").toString() + "second(s)\n");
        System.out.println("\n");
        System.out.println("choose another file or show/hide original traces");

     }
 
 }
 
 