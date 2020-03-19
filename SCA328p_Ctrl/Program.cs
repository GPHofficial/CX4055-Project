// Decompiled with JetBrains decompiler
// Type: SCA328p_Ctrl.Program
// Assembly: SCA328p_Ctrl, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null
// MVID: 6CDBC530-B8A4-40B2-9F3F-0311C31A3480
// Assembly location: C:\Users\Puay Hiang\Downloads\SCA328p_Ctrl_Capture\SCA328p_Ctrl_Capture\SCA328p_Ctrl.exe

using System;
using System.Windows.Forms;

namespace SCA328p_Ctrl
{
  internal static class Program
  {
    [STAThread]
    private static void Main()
    {
      Application.EnableVisualStyles();
      Application.SetCompatibleTextRenderingDefault(false);
      Application.Run((Form) new Form1());
    }
  }
}
