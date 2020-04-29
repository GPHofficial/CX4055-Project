import csv

key = []
numberOfTraces = 0
numberOfTracesPoint = 0
plainText = []
cipherText = []
traceMatrix = []
hypothesis = []
correlation = []

#Predefined Constants

cipherTextCol = 0
plainTextCol = 1
extraColFront = 2
extraColRear = 0
cipher = 'AES128'
traceFile = 'waveform.csv'
# br = new BufferedReader(new FileReader(this.traceFile))
# int count = 1
# while (this.br.readLine() != null) {
#     ++count
# }
# this.numberOfTraces = count



def initTraceMatrix():

    print("initTraceMatrix")
    global numberOfTraces
    global numberOfTracesPoint

    global plainTextCol
    global ciperTextCol

    global plainText
    global cipherText
    global traceMatrix

    global traceFile

    global extraColFront
    global extraColRear

    with open(traceFile, newline='') as csvfile:
        waveformReader = csv.reader(csvfile)
        for row in waveformReader:


            if(numberOfTraces is 0):
                numberOfTracesPoint = len(row) - (extraColRear + extraColFront)
            
            plainText.append(row[plainTextCol])
            cipherText.append(row[cipherTextCol])

            numberOfTraces = numberOfTraces + 1

            traceRow = []
            for col in range(0 + extraColFront,(len(row) - 1) - extraColRear):
                traceRow.append(row[col])

            traceRow.append(traceRow)
                    
                    
    
    print(numberOfTraces)

    # this.br = new BufferedReader(new FileReader(this.traceFile));
    # this.traceMatrix = new double[this.numberOfTraces][this.numberOfTracePoints];
    # this.plainText = new String[this.numberOfTraces];
    # this.cipherText = new String[this.numberOfTraces];

    # for (int count = 0; count < this.numberOfTraces; ++count) {
    #     String[] t = this.br.readLine().split(","); 

    #     this.plainText[count] = t[this.plaintextCol];
    #     this.cipherText[count] = t[1];
    #     for (int j = this.extraColFront; j < this.numberOfTracePoints - this.extraColRear; ++j) {
    #         this.traceMatrix[count][j - this.extraColFront] = Double.parseDouble(t[j]);
    #     }
    # }

def HW(n): 
    count = 0
    while (n): 
        count += n & 1
        n >>= 1
    return count 

def SBOX(a):
    s = [99, 124, 119, 123, 242, 107, 111, 197, 48, 1, 103, 43, 254, 215, 171, 118, 202, 130, 201, 125, 250, 89, 71, 240, 173, 212, 162, 175, 156, 164, 114, 192, 183, 253, 147, 38, 54, 63, 247, 204, 52, 165, 229, 241, 113, 216, 49, 21, 4, 199, 35, 195, 24, 150, 5, 154, 7, 18, 128, 226, 235, 39, 178, 117, 9, 131, 44, 26, 27, 110, 90, 160, 82, 59, 214, 179, 41, 227, 47, 132, 83, 209, 0, 237, 32, 252, 177, 91, 106, 203, 190, 57, 74, 76, 88, 207, 208, 239, 170, 251, 67, 77, 51, 133, 69, 249, 2, 127, 80, 60, 159, 168, 81, 163, 64, 143, 146, 157, 56, 245, 188, 182, 218, 33, 16, 255, 243, 210, 205, 12, 19, 236, 95, 151, 68, 23, 196, 167, 126, 61, 100, 93, 25, 115, 96, 129, 79, 220, 34, 42, 144, 136, 70, 238, 184, 20, 222, 94, 11, 219, 224, 50, 58, 10, 73, 6, 36, 92, 194, 211, 172, 98, 145, 149, 228, 121, 231, 200, 55, 109, 141, 213, 78, 169, 108, 86, 244, 234, 101, 122, 174, 8, 186, 120, 37, 46, 28, 166, 180, 198, 232, 221, 116, 31, 75, 189, 139, 138, 112, 62, 181, 102, 72, 3, 246, 14, 97, 53, 87, 185, 134, 193, 29, 158, 225, 248, 152, 17, 105, 217, 142, 148, 155, 30, 135, 233, 206, 85, 40, 223, 140, 161, 137, 13, 191, 230, 66, 104, 65, 153, 45, 15, 176, 84, 187, 22]
    return s[a]

def Correlation(xs,ys):
    sx = 0.0
    sy = 0.0
    sxx = 0.0
    syy = 0.0
    sxy = 0.0

    n = len(xs)

    for i in range(0,n):
        x = xs[i]
        y = ys[i]

        sx = sx + x
        sy = sy + y

        sxx = sxx + (x*x)
        syy = syy + (y * y)
        sxy = sxy + (x * y)

    cov = sxy / n - sx * sy / n / n
    sigmax = Math.sqrt(sxx / n - sx * sx / n / n)
    sigmay = Math.sqrt(syy / n - sy * sy / n / n)
    
    return cov / sigmax / sigmay



    # public double Correlation(double[] xs, double[] ys) {
    #      double sx = 0.0;
    #      double sy = 0.0;
    #      double sxx = 0.0;
    #      double syy = 0.0;
    #      double sxy = 0.0;
    #      int n = xs.length;
    #      for (int i = 0; i < n; ++i) {
    #          double x = xs[i];
    #          double y = ys[i];
    #          sx += x;
    #          sy += y;
    #          sxx += x * x;
    #          syy += y * y;
    #          sxy += x * y;
    #      }
    #      double cov = sxy / (double)n - sx * sy / (double)n / (double)n;
    #      double sigmax = Math.sqrt(sxx / (double)n - sx * sx / (double)n / (double)n);
    #      double sigmay = Math.sqrt(syy / (double)n - sy * sy / (double)n / (double)n);
    #      return cov / sigmax / sigmay;
    #  }



def findCorrelation():

    global correlation
    global hypothesis
    global traceMatrix
    global numberOfTracesPoint
    global numberOfTraces

    x = []
    y = []

    for count in range(0,256):
        for j in range(0,numberOfTraces):
            y.append(hypothesis[j][count] / 256.0)
        for i in range(0,numberOfTracesPoint):
            for j in range(0,numberOfTraces):
                x.append(traceMatrix[j][i])
            cor[count][i] = Correlation(x,y)



# public void findCorrelation() {
    #     this.cor = new double[256][this.numberOfTracePoints];
    #     double[] x = new double[this.numberOfTraces];
    #     double[] y = new double[this.numberOfTraces];
    #     for (int count = 0; count <= 255; ++count) {
    #         for (int j = 0; j < this.numberOfTraces; ++j) {
    #             y[j] = (double)this.hypothesis[j][count] / 256.0;
    #         }
    #         for (int i = 0; i < this.numberOfTracePoints; ++i) {
    #             for (int j = 0; j < this.numberOfTraces; ++j) {
    #                 x[j] = this.traceMatrix[j][i];
    #             }
    #             this.cor[count][i] = this.Correlation(x, y);
    #         }
    #     }
    # }

def hex2int(hexString):
    hexmap = {}
    hexmap["0"] = 0
    hexmap["1"] = 1
    hexmap["2"] = 2
    hexmap["3"] = 3
    hexmap["4"] = 4
    hexmap["5"] = 5
    hexmap["6"] = 6
    hexmap["7"] = 7
    hexmap["8"] = 8
    hexmap["9"] = 9
    hexmap["A"] = 10
    hexmap["B"] = 11
    hexmap["C"] = 12
    hexmap["D"] = 13
    hexmap["E"] = 14
    hexmap["F"] = 15
    return hexmap[hexString[0] + ""] << 4 ^ hexmap[hexString[1] + ""];

def initHypothesis_MCU8_AES128(byteNumber):

    global plainText
    global hypothesis

    keyHyp = []
    for i in range(0,255):
        keyHyp.append(i)
    
    hypothesis = []

    for i in range(0,len(plainText)):
        rowHypothesis = []
        print(2 * (byteNumber))
        P = plainText[i][2 * (byteNumber - 1): 2 * byteNumber]
        for j in range(0,len(keyHyp)):
            rowHypothesis.append(HW(SBOX(hex2int(P) ^ keyHyp[j])))
        hypothesis.append(rowHypothesis)


    # int i;
    # int[] keyHyp = new int[256];
    # for (i = 0; i < 255; ++i) {
    #     keyHyp[i] = i;
    # }
    # this.hypothesis = new int[this.plainText.length][keyHyp.length];
    # for (i = 0; i < this.plainText.length; ++i) {
    #     String P = this.plainText[i].substring(2 * (byteNumber - 1), 2 * byteNumber);
    #     for (int j = 0; j < keyHyp.length; ++j) {
    #         this.hypothesis[i][j] = this.HW(this.SBOX(this.hex2int(P) ^ keyHyp[j]));
    #     }
    # }


def findKey():
    global cor

    max = cor[0][0]
    loc = 0
    for i in range(0,256):
        for i in range(0,numberOfTracesPoint):
            if not (cor[i][j] > max):
                continue
            max = cor[i][j]
            loc = i
    return loc
        

    # public int findKey() {
    #      double max = this.cor[0][0];
    #      int loc = 0;
    #      for (int i = 0; i < 256; ++i) {
    #          for (int j = 0; j < this.numberOfTracePoints; ++j) {
    #              if (!(this.cor[i][j] > max)) continue;
    #              max = this.cor[i][j];
    #              loc = i;
    #          }
    #      }
    #      return loc;
    #  }


def CPA(keysize):

    #this.key = new int[keysize];
    global key
    key = [None] * keysize

#   LOGGER.info("Running Analysis...");
    print("Running Analysis...")

#   this.initTraceMatrix();
    initTraceMatrix()

##  long start = System.currentTimeMillis();
#   for (int i = 1; i <= keysize; ++i) {
#       this.initHypothesis_MCU8_AES128(i);
#       this.findCorrelation();
#       this.key[i - 1] = this.findKey();
#   }

    for i in range(2,keysize):
        initHypothesis_MCU8_AES128(i)
        findCorrelation()
        key[i - 1] = findKey()
    
##  long end = System.currentTimeMillis();
#   LOGGER.info("Analysis Complete.");

    print("Analysis Complete.")

##  double timetaken = (end - start) / 1000L;

#   String strkey = "";
#   String t = "";

    strkey = ""
    t = ""

#   for (int i = 0; i < keysize; ++i) {
#       strkey = strkey + ((t = Integer.toHexString(this.key[i]).toUpperCase() + " ").length() < 3 ? "0" + t : t);
#       System.out.println("t = " + t + " \t strkey = " + strkey);
#   }

    for i in range(0,keysize):
        t = hex(key[i].toUpperCase() + " ")
        if len(t) < 3:
            strkey = strkey + "0" + t
        else:
            strkey = strkey + t
        print("t = " + t + " \t strkey = " + strkey)
    
#   System.out.println(strkey.trim());
#   System.out.println("Total Time: " + timetaken + " seconds");

    print(strkey.strip())
    print("Total Time: " + timetaken + " seconds")

#   HashMap<Object, Object> retVal = new HashMap<Object, Object>();
#   retVal.put("key", strkey);
#   retVal.put("time", timetaken);
#   return retVal;

    retVal = {"key": strkey, "time": timetaken}
    return retVal



CPA(128)
    



