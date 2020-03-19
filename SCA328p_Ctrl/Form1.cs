// Decompiled with JetBrains decompiler
// Type: SCA328p_Ctrl.Form1
// Assembly: SCA328p_Ctrl, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null
// MVID: 6CDBC530-B8A4-40B2-9F3F-0311C31A3480
// Assembly location: C:\Users\Puay Hiang\Downloads\SCA328p_Ctrl_Capture\SCA328p_Ctrl_Capture\SCA328p_Ctrl.exe

using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.IO;
using System.IO.Ports;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using TekVISANet;

namespace SCA328p_Ctrl
{
  public class Form1 : Form
  {
    private StringBuilder sb = new StringBuilder();
    private List<byte> tempBuffer = new List<byte>();
    private byte[] cipherText_Global = new byte[16];
    private VISA TVA = new VISA();
    private bool scopeOpened;
    private IContainer components;
    private StatusStrip statusStrip1;
    private TabControl tabControl1;
    private TabPage tabPage1;
    private TabPage tabPage2;
    private ToolStripStatusLabel StatusLabel;
    private Button button_Send;
    private SerialPort serialPort_Main;
    private TableLayoutPanel tableLayoutPanel1;
    private Panel panel1;
    private RichTextBox richTextBox_Console;
    private Label label3;
    private TextBox textBox_PlainText;
    private ComboBox comboBox_Baud;
    private Label label2;
    private Label label1;
    private ComboBox comboBox_Port;
    private Button button_Open;
    private ComboBox comboBox_Scope;
    private Label label4;
    private Button buttonScope;
    private NumericUpDown numOfTraces;
    private Label label5;
    private Button button_collect;
    private Button button_RescanPorts;
    private Label label6;
    private Button button_Test;
    private Label label_Trace;
    private Button button_CLS;
    private Button button_updateKey;
    private TextBox textBox_Key;
    private Label label7;

    public Form1()
    {
      this.InitializeComponent();
      this.serialPort_Main.DataReceived += new SerialDataReceivedEventHandler(this.serialPort_Main_DataReceived);
    }

    private bool validateDataFormat(List<byte> data, out byte[] formatted)
    {
      formatted = new byte[16];
      bool flag = false;
      if (data.Count >= 20)
      {
        for (int index = 0; index <= data.Count - 20; ++index)
        {
          if (data[index] == (byte) 1 && data[index + 1] == (byte) 35 && (data[index + 2] == (byte) 171 && data[index + 3] == (byte) 205))
          {
            int sourceIndex1 = index + 4;
            if (sourceIndex1 + 16 <= data.Count)
            {
              Array.Copy((Array) data.ToArray(), sourceIndex1, (Array) formatted, 0, 16);
              flag = true;
              int sourceIndex2 = sourceIndex1 + 16;
              if (sourceIndex2 <= data.Count)
              {
                int length = data.Count - sourceIndex2;
                byte[] numArray = new byte[length];
                Array.Copy((Array) data.ToArray(), sourceIndex2, (Array) numArray, 0, length);
                data.Clear();
                data.AddRange((IEnumerable<byte>) numArray);
              }
            }
          }
        }
      }
      return flag;
    }

    private void validateDataFormatMultiple(List<byte> data, List<byte[]> formatted)
    {
      byte[] formatted1;
      while (this.validateDataFormat(data, out formatted1))
        formatted.Add(formatted1);
    }

    private void serialPort_Main_DataReceived(object sender, SerialDataReceivedEventArgs e)
    {
      byte[] buffer = new byte[this.serialPort_Main.BytesToRead];
      int num = this.serialPort_Main.Read(buffer, 0, this.serialPort_Main.BytesToRead);
      for (int index = 0; index < num; ++index)
        this.tempBuffer.Add(buffer[index]);
      List<byte[]> formatted = new List<byte[]>();
      this.validateDataFormatMultiple(this.tempBuffer, formatted);
      if (formatted.Count <= 0)
        return;
      for (int index1 = 0; index1 < formatted.Count; ++index1)
      {
        if (formatted[index1].Length == 16)
        {
          for (int index2 = 0; index2 < formatted[index1].Length; ++index2)
          {
            this.sb.Append(formatted[index1][index2].ToString("X2"));
            this.cipherText_Global[index2] = formatted[index1][index2];
          }
          this.richTextBox_Console.Invoke((Delegate) (() => this.richTextBox_Console.AppendText("       CT: " + this.sb.ToString() + "\n")));
        }
        else
          this.richTextBox_Console.Invoke((Delegate) (() => this.richTextBox_Console.AppendText("\n ERROR: Invalid Reply Length \n")));
        this.sb.Clear();
      }
    }

    private void Form1_Load(object sender, EventArgs e)
    {
      this.button_RescanPorts.Text = "\xE72C";
      this.comboBox_Baud.SelectedIndex = 3;
      this.label_Trace.Text = "";
      this.LoadPorts();
      ArrayList arrayList;
      this.TVA.FindResources("?*", ref arrayList);
      Console.WriteLine("Visa Resources");
      for (int index = 0; index < arrayList.Count; ++index)
        this.comboBox_Scope.Items.Add(arrayList[index]);
      if (this.comboBox_Scope.Items.Count > 1)
      {
        this.comboBox_Scope.SelectedIndex = this.comboBox_Scope.Items.Count - 1;
      }
      else
      {
        if (this.comboBox_Scope.Items.Count != 1)
          return;
        this.comboBox_Scope.SelectedIndex = 0;
      }
    }

    private void LoadPorts()
    {
      this.comboBox_Port.Items.Clear();
      foreach (object portName in SerialPort.GetPortNames())
        this.comboBox_Port.Items.Add(portName);
      if (this.comboBox_Port.Items.Count > 1)
      {
        this.comboBox_Port.SelectedIndex = this.comboBox_Port.Items.Count - 1;
      }
      else
      {
        if (this.comboBox_Port.Items.Count != 1)
          return;
        this.comboBox_Port.SelectedIndex = 0;
      }
    }

    private void button_Open_Click(object sender, EventArgs e)
    {
      try
      {
        if (this.serialPort_Main.IsOpen)
        {
          this.serialPort_Main.Close();
          this.StatusLabel.Text = "Port Closed";
          this.button_Open.Text = "Open";
          this.button_Send.Enabled = false;
          this.button_collect.Enabled = false;
          this.button_Test.Enabled = false;
          this.button_updateKey.Enabled = false;
        }
        else
        {
          this.serialPort_Main.BaudRate = int.Parse(this.comboBox_Baud.Text);
          this.serialPort_Main.PortName = this.comboBox_Port.Text;
          this.serialPort_Main.Open();
          if (!this.serialPort_Main.IsOpen)
            return;
          this.StatusLabel.Text = "Port Opened";
          this.button_Open.Text = "Close";
          this.button_Send.Enabled = true;
          this.button_collect.Enabled = true;
          this.button_Test.Enabled = true;
          this.button_updateKey.Enabled = true;
        }
      }
      catch (Exception ex)
      {
        this.StatusLabel.Text = "Exception:" + ex.Message;
      }
    }

    public static byte[] StringToByteArray(string hex)
    {
      return Enumerable.Range(0, hex.Length).Where<int>((Func<int, bool>) (x => x % 2 == 0)).Select<int, byte>((Func<int, byte>) (x => Convert.ToByte(hex.Substring(x, 2), 16))).ToArray<byte>();
    }

    private void buttonScope_Click(object sender, EventArgs e)
    {
      try
      {
        this.scopeOpened = this.TVA.Open(this.comboBox_Scope.Text);
        this.TVA.Write("*IDN?");
        string str;
        bool flag = this.TVA.Read(ref str);
        this.richTextBox_Console.AppendText("\nOscilloscope Connected\n");
        if (flag)
          this.richTextBox_Console.AppendText(str + "\n");
        this.richTextBox_Console.AppendText(this.InitializeSettings_TDS2012_AVR() + "\n\n");
      }
      catch (Exception ex)
      {
        this.StatusLabel.Text = "Exception:" + ex.Message;
      }
    }

    public void PressStart(ref int ret)
    {
      if (this.TVA.Write("ACQ:STATE RUN") && this.TVA.Write("ACQ:STOP SEQ"))
      {
        Thread.Sleep(100);
        ret = 0;
      }
      else
      {
        ret = -2;
        throw new Exception(" Error in TriggerMSO()");
      }
    }

    private void buttonSend_Click(object sender, EventArgs e)
    {
      this.richTextBox_Console.AppendText("\n");
      this.SendToBoard(Form1.StringToByteArray(this.textBox_PlainText.Text.Trim()), new byte[4]
      {
        (byte) 1,
        (byte) 35,
        (byte) 171,
        (byte) 205
      });
      if (this.scopeOpened)
      {
        do
          ;
        while (!this.TVA.Write("*OPC?"));
        NumberStyles style = NumberStyles.Number | NumberStyles.AllowExponent;
        string str1 = "";
        this.TVA.Write("DATA:START 0");
        this.TVA.Write("DATA:SOU CH1");
        this.TVA.Write("DATA:WIDTH 1");
        this.TVA.Write("DATA:ENC ASCII");
        this.TVA.Write("DATA:STOP 2500");
        this.TVA.Query("WFMPRE:YMULT?", ref str1);
        string s1 = str1.Replace(":WFMPRE:YMULT ", "");
        float num1 = float.Parse(s1, style);
        this.TVA.Query("WFMPRE:YZERO?", ref s1);
        string s2 = s1.Replace(":WFMPRE:YZERO ", "");
        float num2 = float.Parse(s2, style);
        this.TVA.Query("WFMPRE:YOFF?", ref s2);
        string s3 = s2.Replace(":WFMPRE:YOFF ", "");
        float num3 = float.Parse(s3, style);
        this.TVA.Query("WFMPRE:XINCR?", ref s3);
        float.Parse(s3.Replace(":WFMPRE:XINCR ", ""), style);
        string str2 = "";
        this.TVA.Write("CURVE?");
        this.TVA.Read(ref str2);
        sbyte[] array = ((IEnumerable<string>) str2.Split(',')).Select<string, sbyte>((Func<string, sbyte>) (n => Convert.ToSByte(n))).ToArray<sbyte>();
        float[] numArray = new float[((IEnumerable<sbyte>) array).Count<sbyte>()];
        for (int index = 0; index < ((IEnumerable<sbyte>) array).Count<sbyte>(); ++index)
          numArray[index] = ((float) array[index] - num3) * num1 + num2;
        StreamWriter streamWriter;
        if (!File.Exists("waveform.csv"))
        {
          streamWriter = new StreamWriter("waveform.csv", true);
        }
        else
        {
          streamWriter = new StreamWriter("waveform.csv", true);
          streamWriter.WriteLine();
        }
        streamWriter.Write(this.textBox_PlainText.Text + "," + this.sb.ToString() + ",");
        for (int index = 0; index < ((IEnumerable<float>) numArray).Count<float>(); ++index)
          streamWriter.Write(numArray[index].ToString() + ",");
        streamWriter.Close();
        this.TVA.Clear();
      }
      else
        this.StatusLabel.Text = "Error applying oscilloscope settings!";
    }

    private void SendToBoard(byte[] bytes, byte[] pre)
    {
      this.serialPort_Main.Write(pre, 0, 4);
      this.serialPort_Main.Write(bytes, 0, 16);
    }

    public int WriteSetting(string setting)
    {
      return this.scopeOpened && this.TVA.Write(setting) ? 0 : 1;
    }

    public string InitializeSettings_TDS2012_AVR()
    {
      int num = 0 + this.WriteSetting("SEL:CH1 ON") + this.WriteSetting("SEL:CH2 ON") + this.WriteSetting("SEL:MATH OFF") + this.WriteSetting("CH1:BAN FUL") + this.WriteSetting("CH1:COUP DC") + this.WriteSetting("CH1:IMP MEG") + this.WriteSetting("CH1:INV OFF") + this.WriteSetting("CH1:OFFS 0") + this.WriteSetting("CH1:POS -8.4") + this.WriteSetting("CH1:SCA 50E-3") + this.WriteSetting("CH2:BAN FUL") + this.WriteSetting("CH2:COUP DC") + this.WriteSetting("CH2:IMP MEG") + this.WriteSetting("CH2:INV OFF") + this.WriteSetting("CH2:OFFS 0") + this.WriteSetting("CH2:POS -3.6") + this.WriteSetting("CH2:SCA 5") + this.WriteSetting("HOR:SCA 2.5E-6") + this.WriteSetting("HOR:POS 9.3E-6") + this.WriteSetting("HOR:RESO 2500") + this.WriteSetting("HOR:RECO 2500") + this.WriteSetting("TRIG:MAI:MOD NORM") + this.WriteSetting("TRIG:MAI:TYP EDG") + this.WriteSetting("TRIG:MAI:EDGE:COUP DC") + this.WriteSetting("TRIG:MAI:EDGE:SLO RISE") + this.WriteSetting("TRIG:MAI:EDGE:SOU CH2") + this.WriteSetting("TRIG:MAI:LEV 1.6");
      return num == 0 ? "All settings applied !!!" : num.ToString() + " Errors ocurred.";
    }

    private void button_RescanPorts_Click(object sender, EventArgs e)
    {
      this.LoadPorts();
    }

    private void collectTrace(byte[] plainText, byte[] cipherText)
    {
      if (this.scopeOpened)
      {
        do
          ;
        while (!this.TVA.Write("*OPC?"));
        NumberStyles style = NumberStyles.Number | NumberStyles.AllowExponent;
        string str1 = "";
        this.TVA.Write("DATA:START 0");
        this.TVA.Write("DATA:SOU CH1");
        this.TVA.Write("DATA:WIDTH 1");
        this.TVA.Write("DATA:ENC ASCII");
        this.TVA.Write("DATA:STOP 2500");
        this.TVA.Query("WFMPRE:YMULT?", ref str1);
        string s = str1.Replace(":WFMPRE:YMULT ", "");
        float num1 = float.Parse(s, style);
        this.TVA.Query("WFMPRE:YZERO?", ref s);
        s = s.Replace(":WFMPRE:YZERO ", "");
        float num2 = float.Parse(s, style);
        this.TVA.Query("WFMPRE:YOFF?", ref s);
        s = s.Replace(":WFMPRE:YOFF ", "");
        float num3 = float.Parse(s, style);
        this.TVA.Query("WFMPRE:XINCR?", ref s);
        s = s.Replace(":WFMPRE:XINCR ", "");
        float.Parse(s, style);
        string str2 = "";
        this.TVA.Write("CURVE?");
        this.TVA.Read(ref str2);
        sbyte[] array = ((IEnumerable<string>) str2.Split(',')).Select<string, sbyte>((Func<string, sbyte>) (n => Convert.ToSByte(n))).ToArray<sbyte>();
        float[] numArray = new float[((IEnumerable<sbyte>) array).Count<sbyte>()];
        for (int index = 0; index < ((IEnumerable<sbyte>) array).Count<sbyte>(); ++index)
          numArray[index] = ((float) array[index] - num3) * num1 + num2;
        StreamWriter streamWriter = new StreamWriter("waveform.csv", true);
        string str3 = Regex.Replace(BitConverter.ToString(plainText), "-", "");
        string str4 = Regex.Replace(BitConverter.ToString(cipherText), "-", "");
        streamWriter.Write(str3 + "," + str4 + ",");
        for (int index = 0; index < ((IEnumerable<float>) numArray).Count<float>(); ++index)
          streamWriter.Write(numArray[index].ToString("0.0000") + ",");
        streamWriter.WriteLine();
        streamWriter.Close();
        this.TVA.Clear();
      }
      else
        this.StatusLabel.Text = "Error applying oscilloscope settings!";
    }

    private void sendAndCollect(byte[] bytes, bool collect_Osc)
    {
      byte[] pre = new byte[4]
      {
        (byte) 1,
        (byte) 35,
        (byte) 171,
        (byte) 205
      };
      int ret = 0;
      if (collect_Osc)
        this.PressStart(ref ret);
      this.SendToBoard(bytes, pre);
      if (!collect_Osc)
        return;
      this.collectTrace(bytes, this.cipherText_Global);
    }

    private async void collect_Click(object sender, EventArgs e)
    {
      if (this.scopeOpened)
      {
        this.button_updateKey.Enabled = false;
        await this.DoTraceCollection(true);
        this.button_updateKey.Enabled = true;
      }
      else
        this.richTextBox_Console.AppendText("\nOscilloscope not connected! Please connect oscilloscope from 'Oscilloscope Settings' tab.\n");
    }

    private async Task DoTraceCollection(bool collect)
    {
      int length = (int) this.numOfTraces.Value;
      await Task.Run((Action) (() =>
      {
        for (int i = 0; i < length; ++i)
        {
          Random random = new Random();
          byte[] bytes = new byte[16];
          random.NextBytes(bytes);
          this.textBox_PlainText.Invoke((Delegate) (() =>
          {
            string str = Regex.Replace(BitConverter.ToString(bytes), "-", "");
            this.richTextBox_Console.AppendText("\n " + (i + 1).ToString("00000") + " PT: " + str + "\n");
            this.richTextBox_Console.ScrollToCaret();
            this.label_Trace.Text = "Trace: " + (object) (i + 1) + " / " + (object) length;
          }));
          this.sendAndCollect(bytes, collect);
          Thread.Sleep(50);
        }
      }));
      this.textBox_PlainText.Invoke((Delegate) (() =>
      {
        this.richTextBox_Console.AppendText("\nTrace Collection Complete . . .\n");
        this.label_Trace.Text = "";
        this.richTextBox_Console.ScrollToCaret();
      }));
    }

    private string collect()
    {
      Random random = new Random();
      byte[] buffer = new byte[16];
      random.NextBytes(buffer);
      return Regex.Replace(BitConverter.ToString(buffer), "-", "");
    }

    private async void button_Test_Click(object sender, EventArgs e)
    {
      this.button_updateKey.Enabled = false;
      await this.DoTraceCollection(false);
      this.button_updateKey.Enabled = true;
    }

    private void button_CLS_Click(object sender, EventArgs e)
    {
      this.richTextBox_Console.Clear();
    }

    private void button_updateKey_Click(object sender, EventArgs e)
    {
      this.SendToBoard(Form1.StringToByteArray(this.textBox_Key.Text.Trim()), new byte[4]
      {
        (byte) 69,
        (byte) 103,
        (byte) 171,
        (byte) 205
      });
      this.richTextBox_Console.AppendText("\nKey updated!\n");
    }

    protected override void Dispose(bool disposing)
    {
      if (disposing && this.components != null)
        this.components.Dispose();
      base.Dispose(disposing);
    }

    private void InitializeComponent()
    {
      this.components = (IContainer) new Container();
      this.statusStrip1 = new StatusStrip();
      this.StatusLabel = new ToolStripStatusLabel();
      this.tabControl1 = new TabControl();
      this.tabPage1 = new TabPage();
      this.tableLayoutPanel1 = new TableLayoutPanel();
      this.panel1 = new Panel();
      this.button_CLS = new Button();
      this.label_Trace = new Label();
      this.button_Test = new Button();
      this.label6 = new Label();
      this.button_RescanPorts = new Button();
      this.numOfTraces = new NumericUpDown();
      this.label5 = new Label();
      this.button_collect = new Button();
      this.comboBox_Baud = new ComboBox();
      this.label2 = new Label();
      this.label1 = new Label();
      this.comboBox_Port = new ComboBox();
      this.button_Open = new Button();
      this.label3 = new Label();
      this.textBox_PlainText = new TextBox();
      this.button_Send = new Button();
      this.richTextBox_Console = new RichTextBox();
      this.tabPage2 = new TabPage();
      this.buttonScope = new Button();
      this.label4 = new Label();
      this.comboBox_Scope = new ComboBox();
      this.serialPort_Main = new SerialPort(this.components);
      this.label7 = new Label();
      this.textBox_Key = new TextBox();
      this.button_updateKey = new Button();
      this.statusStrip1.SuspendLayout();
      this.tabControl1.SuspendLayout();
      this.tabPage1.SuspendLayout();
      this.tableLayoutPanel1.SuspendLayout();
      this.panel1.SuspendLayout();
      this.numOfTraces.BeginInit();
      this.tabPage2.SuspendLayout();
      this.SuspendLayout();
      this.statusStrip1.Items.AddRange(new ToolStripItem[1]
      {
        (ToolStripItem) this.StatusLabel
      });
      this.statusStrip1.Location = new Point(0, 608);
      this.statusStrip1.Name = "statusStrip1";
      this.statusStrip1.Size = new Size(921, 22);
      this.statusStrip1.TabIndex = 0;
      this.statusStrip1.Text = "statusStrip1";
      this.StatusLabel.Name = "StatusLabel";
      this.StatusLabel.Size = new Size(39, 17);
      this.StatusLabel.Text = "Ready";
      this.tabControl1.Controls.Add((Control) this.tabPage1);
      this.tabControl1.Controls.Add((Control) this.tabPage2);
      this.tabControl1.Dock = DockStyle.Fill;
      this.tabControl1.Location = new Point(0, 0);
      this.tabControl1.Name = "tabControl1";
      this.tabControl1.SelectedIndex = 0;
      this.tabControl1.Size = new Size(921, 608);
      this.tabControl1.TabIndex = 2;
      this.tabPage1.Controls.Add((Control) this.tableLayoutPanel1);
      this.tabPage1.Location = new Point(4, 22);
      this.tabPage1.Name = "tabPage1";
      this.tabPage1.Padding = new Padding(3);
      this.tabPage1.Size = new Size(913, 582);
      this.tabPage1.TabIndex = 0;
      this.tabPage1.Text = "Control";
      this.tabPage1.UseVisualStyleBackColor = true;
      this.tableLayoutPanel1.ColumnCount = 1;
      this.tableLayoutPanel1.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 100f));
      this.tableLayoutPanel1.Controls.Add((Control) this.panel1, 0, 0);
      this.tableLayoutPanel1.Controls.Add((Control) this.richTextBox_Console, 0, 1);
      this.tableLayoutPanel1.Dock = DockStyle.Fill;
      this.tableLayoutPanel1.Location = new Point(3, 3);
      this.tableLayoutPanel1.Name = "tableLayoutPanel1";
      this.tableLayoutPanel1.RowCount = 2;
      this.tableLayoutPanel1.RowStyles.Add(new RowStyle(SizeType.Absolute, 130f));
      this.tableLayoutPanel1.RowStyles.Add(new RowStyle(SizeType.Percent, 100f));
      this.tableLayoutPanel1.Size = new Size(907, 576);
      this.tableLayoutPanel1.TabIndex = 1;
      this.panel1.Controls.Add((Control) this.button_updateKey);
      this.panel1.Controls.Add((Control) this.textBox_Key);
      this.panel1.Controls.Add((Control) this.label7);
      this.panel1.Controls.Add((Control) this.button_CLS);
      this.panel1.Controls.Add((Control) this.label_Trace);
      this.panel1.Controls.Add((Control) this.button_Test);
      this.panel1.Controls.Add((Control) this.label6);
      this.panel1.Controls.Add((Control) this.button_RescanPorts);
      this.panel1.Controls.Add((Control) this.numOfTraces);
      this.panel1.Controls.Add((Control) this.label5);
      this.panel1.Controls.Add((Control) this.button_collect);
      this.panel1.Controls.Add((Control) this.comboBox_Baud);
      this.panel1.Controls.Add((Control) this.label2);
      this.panel1.Controls.Add((Control) this.label1);
      this.panel1.Controls.Add((Control) this.comboBox_Port);
      this.panel1.Controls.Add((Control) this.button_Open);
      this.panel1.Controls.Add((Control) this.label3);
      this.panel1.Controls.Add((Control) this.textBox_PlainText);
      this.panel1.Controls.Add((Control) this.button_Send);
      this.panel1.Dock = DockStyle.Fill;
      this.panel1.Location = new Point(3, 3);
      this.panel1.Name = "panel1";
      this.panel1.Size = new Size(901, 124);
      this.panel1.TabIndex = 2;
      this.button_CLS.Location = new Point(798, 87);
      this.button_CLS.Name = "button_CLS";
      this.button_CLS.Size = new Size(86, 23);
      this.button_CLS.TabIndex = 17;
      this.button_CLS.Text = "Clear Console";
      this.button_CLS.UseVisualStyleBackColor = true;
      this.button_CLS.Click += new EventHandler(this.button_CLS_Click);
      this.label_Trace.AutoSize = true;
      this.label_Trace.Font = new Font("Tahoma", 18f, FontStyle.Regular, GraphicsUnit.Point, (byte) 0);
      this.label_Trace.Location = new Point(581, 79);
      this.label_Trace.Name = "label_Trace";
      this.label_Trace.Size = new Size(81, 29);
      this.label_Trace.TabIndex = 16;
      this.label_Trace.Text = "Trace:";
      this.button_Test.Enabled = false;
      this.button_Test.Location = new Point(394, 47);
      this.button_Test.Name = "button_Test";
      this.button_Test.Size = new Size(75, 23);
      this.button_Test.TabIndex = 15;
      this.button_Test.Text = "Test";
      this.button_Test.UseVisualStyleBackColor = true;
      this.button_Test.Click += new EventHandler(this.button_Test_Click);
      this.label6.AutoSize = true;
      this.label6.Location = new Point(212, 51);
      this.label6.Name = "label6";
      this.label6.Size = new Size(80, 13);
      this.label6.TabIndex = 14;
      this.label6.Text = "(Maximum 500)";
      this.button_RescanPorts.Font = new Font("Segoe MDL2 Assets", 8.25f, FontStyle.Regular, GraphicsUnit.Point, (byte) 0);
      this.button_RescanPorts.Location = new Point(756, 20);
      this.button_RescanPorts.Name = "button_RescanPorts";
      this.button_RescanPorts.Size = new Size(21, 23);
      this.button_RescanPorts.TabIndex = 13;
      this.button_RescanPorts.UseVisualStyleBackColor = true;
      this.button_RescanPorts.Click += new EventHandler(this.button_RescanPorts_Click);
      this.numOfTraces.Location = new Point(91, 48);
      this.numOfTraces.Maximum = new Decimal(new int[4]
      {
        10000,
        0,
        0,
        0
      });
      this.numOfTraces.Name = "numOfTraces";
      this.numOfTraces.Size = new Size(114, 21);
      this.numOfTraces.TabIndex = 12;
      this.numOfTraces.Value = new Decimal(new int[4]
      {
        5,
        0,
        0,
        0
      });
      this.label5.AutoSize = true;
      this.label5.Location = new Point(13, 51);
      this.label5.Name = "label5";
      this.label5.Size = new Size(72, 13);
      this.label5.TabIndex = 11;
      this.label5.Text = "No. of Traces";
      this.button_collect.Enabled = false;
      this.button_collect.Location = new Point(313, 48);
      this.button_collect.Name = "button_collect";
      this.button_collect.Size = new Size(75, 23);
      this.button_collect.TabIndex = 10;
      this.button_collect.Text = "Collect";
      this.button_collect.UseVisualStyleBackColor = true;
      this.button_collect.Click += new EventHandler(this.collect_Click);
      this.comboBox_Baud.DropDownStyle = ComboBoxStyle.DropDownList;
      this.comboBox_Baud.FormattingEnabled = true;
      this.comboBox_Baud.Items.AddRange(new object[9]
      {
        (object) "600",
        (object) "4800",
        (object) "9600",
        (object) "38400",
        (object) "57600",
        (object) "115200",
        (object) "983600",
        (object) "1000000",
        (object) "2000000"
      });
      this.comboBox_Baud.Location = new Point(586, 49);
      this.comboBox_Baud.Name = "comboBox_Baud";
      this.comboBox_Baud.Size = new Size(168, 21);
      this.comboBox_Baud.TabIndex = 9;
      this.label2.AutoSize = true;
      this.label2.Location = new Point(513, 51);
      this.label2.Name = "label2";
      this.label2.Size = new Size(57, 13);
      this.label2.TabIndex = 8;
      this.label2.Text = "Baud Rate";
      this.label1.AutoSize = true;
      this.label1.Location = new Point(543, 24);
      this.label1.Name = "label1";
      this.label1.Size = new Size(27, 13);
      this.label1.TabIndex = 7;
      this.label1.Text = "Port";
      this.comboBox_Port.DropDownStyle = ComboBoxStyle.DropDownList;
      this.comboBox_Port.FormattingEnabled = true;
      this.comboBox_Port.Location = new Point(586, 21);
      this.comboBox_Port.Name = "comboBox_Port";
      this.comboBox_Port.Size = new Size(168, 21);
      this.comboBox_Port.TabIndex = 6;
      this.button_Open.Location = new Point(798, 20);
      this.button_Open.Name = "button_Open";
      this.button_Open.Size = new Size(86, 23);
      this.button_Open.TabIndex = 5;
      this.button_Open.Text = "Open";
      this.button_Open.UseVisualStyleBackColor = true;
      this.button_Open.Click += new EventHandler(this.button_Open_Click);
      this.label3.AutoSize = true;
      this.label3.Location = new Point(34, 24);
      this.label3.Name = "label3";
      this.label3.Size = new Size(51, 13);
      this.label3.TabIndex = 2;
      this.label3.Text = "PlainText";
      this.textBox_PlainText.Location = new Point(91, 21);
      this.textBox_PlainText.Name = "textBox_PlainText";
      this.textBox_PlainText.Size = new Size(216, 21);
      this.textBox_PlainText.TabIndex = 1;
      this.textBox_PlainText.Text = "489DB4B302172961CC2BCB4E00E28EB7";
      this.button_Send.Enabled = false;
      this.button_Send.Location = new Point(313, 19);
      this.button_Send.Name = "button_Send";
      this.button_Send.Size = new Size(75, 23);
      this.button_Send.TabIndex = 0;
      this.button_Send.Text = "Send";
      this.button_Send.UseVisualStyleBackColor = true;
      this.button_Send.Click += new EventHandler(this.buttonSend_Click);
      this.richTextBox_Console.BackColor = Color.Black;
      this.richTextBox_Console.Dock = DockStyle.Fill;
      this.richTextBox_Console.Font = new Font("Consolas", 11.25f, FontStyle.Regular, GraphicsUnit.Point, (byte) 0);
      this.richTextBox_Console.ForeColor = Color.LawnGreen;
      this.richTextBox_Console.Location = new Point(3, 133);
      this.richTextBox_Console.Name = "richTextBox_Console";
      this.richTextBox_Console.Size = new Size(901, 440);
      this.richTextBox_Console.TabIndex = 3;
      this.richTextBox_Console.Text = "";
      this.tabPage2.Controls.Add((Control) this.buttonScope);
      this.tabPage2.Controls.Add((Control) this.label4);
      this.tabPage2.Controls.Add((Control) this.comboBox_Scope);
      this.tabPage2.Location = new Point(4, 22);
      this.tabPage2.Name = "tabPage2";
      this.tabPage2.Padding = new Padding(3);
      this.tabPage2.Size = new Size(913, 582);
      this.tabPage2.TabIndex = 1;
      this.tabPage2.Text = "Oscilloscope Settings";
      this.tabPage2.UseVisualStyleBackColor = true;
      this.buttonScope.Location = new Point(188, 85);
      this.buttonScope.Name = "buttonScope";
      this.buttonScope.Size = new Size(75, 23);
      this.buttonScope.TabIndex = 2;
      this.buttonScope.Text = "Connect";
      this.buttonScope.UseVisualStyleBackColor = true;
      this.buttonScope.Click += new EventHandler(this.buttonScope_Click);
      this.label4.AutoSize = true;
      this.label4.Location = new Point(47, 47);
      this.label4.Name = "label4";
      this.label4.Size = new Size(97, 13);
      this.label4.TabIndex = 1;
      this.label4.Text = "Select Oscilloscope";
      this.comboBox_Scope.FormattingEnabled = true;
      this.comboBox_Scope.Location = new Point(188, 44);
      this.comboBox_Scope.Name = "comboBox_Scope";
      this.comboBox_Scope.Size = new Size(308, 21);
      this.comboBox_Scope.TabIndex = 0;
      this.serialPort_Main.BaudRate = 115200;
      this.serialPort_Main.PortName = "COM5";
      this.serialPort_Main.ReadBufferSize = 1024;
      this.serialPort_Main.ReadTimeout = 3000;
      this.serialPort_Main.ReceivedBytesThreshold = 20;
      this.label7.AutoSize = true;
      this.label7.Location = new Point(60, 92);
      this.label7.Name = "label7";
      this.label7.Size = new Size(25, 13);
      this.label7.TabIndex = 18;
      this.label7.Text = "Key";
      this.textBox_Key.Location = new Point(91, 87);
      this.textBox_Key.Name = "textBox_Key";
      this.textBox_Key.Size = new Size(215, 21);
      this.textBox_Key.TabIndex = 19;
      this.textBox_Key.Text = "489DB4B302172961CC2BCB4E00E28EB7";
      this.button_updateKey.Enabled = false;
      this.button_updateKey.Location = new Point(313, 85);
      this.button_updateKey.Name = "button_updateKey";
      this.button_updateKey.Size = new Size(75, 23);
      this.button_updateKey.TabIndex = 20;
      this.button_updateKey.Text = "Update Key";
      this.button_updateKey.UseVisualStyleBackColor = true;
      this.button_updateKey.Click += new EventHandler(this.button_updateKey_Click);
      this.AutoScaleDimensions = new SizeF(6f, 13f);
      this.AutoScaleMode = AutoScaleMode.Font;
      this.ClientSize = new Size(921, 630);
      this.Controls.Add((Control) this.tabControl1);
      this.Controls.Add((Control) this.statusStrip1);
      this.Font = new Font("Tahoma", 8.25f, FontStyle.Regular, GraphicsUnit.Point, (byte) 0);
      this.Name = nameof (Form1);
      this.Text = "SCA 328p Controller";
      this.Load += new EventHandler(this.Form1_Load);
      this.statusStrip1.ResumeLayout(false);
      this.statusStrip1.PerformLayout();
      this.tabControl1.ResumeLayout(false);
      this.tabPage1.ResumeLayout(false);
      this.tableLayoutPanel1.ResumeLayout(false);
      this.panel1.ResumeLayout(false);
      this.panel1.PerformLayout();
      this.numOfTraces.EndInit();
      this.tabPage2.ResumeLayout(false);
      this.tabPage2.PerformLayout();
      this.ResumeLayout(false);
      this.PerformLayout();
    }
  }
}
