/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.jfree.ui.RefineryUtilities
 */
package pat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import org.jfree.ui.RefineryUtilities;
import pat.About;
import pat.HammingCalculator;
import pat.MyCustomFilter;
import pat.MyCustomFilter1;
import pat.PATMain;

public class MainFrame
extends JFrame {
    PATMain ob;
    private int shf = 0;
    private int fileChosen = 0;
    private File trace = null;
    private boolean save = false;
    private File saveFile = null;
    private int par1 = 0;
    private int par2 = 2;
    private int par3 = 0;
    private String par4 = "";
    HammingCalculator hm;
    private JMenuItem aboutMenu;
    private JMenuItem closeMenu;
    private JButton compute;
    private JMenuItem computeMenu;
    private JTextArea console;
    private JFileChooser fileChooser;
    private JMenuItem fileOpen;
    private JMenu filemenu;
    private JButton jButton1;
    private JComboBox<String> jComboBoxCipher;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JMenu jMenu3;
    private JMenu jMenu4;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem6;
    private JMenuItem jMenuItem9;
    private JMenuItem jMenuItemSave;
    private JMenuItem jMenuItemSaveAs;
    private JScrollPane jScrollPane1;
    private JPopupMenu.Separator jSeparator1;
    private JSeparator jSeparator10;
    private JSeparator jSeparator11;
    private JSeparator jSeparator12;
    private JPopupMenu.Separator jSeparator2;
    private JPopupMenu.Separator jSeparator3;
    private JToolBar.Separator jSeparator4;
    private JToolBar.Separator jSeparator5;
    private JToolBar.Separator jSeparator6;
    private JSeparator jSeparator7;
    private JSeparator jSeparator8;
    private JSeparator jSeparator9;
    private JToolBar jToolBar1;
    private JButton open;
    private JTextField plotnumber;
    private JButton saveButton;
    private JButton saveasButton;
    private JButton sh;
    private JSpinner spin1;
    private JSpinner spin2;
    private JSpinner spin3;
    private JLabel status;
    private JButton updateButton;

    public MainFrame() {
        this.initComponents();
        this.setLocationRelativeTo(null);
        this.console.append("Welcome to Power Analysis Tool (PAT) \nOpen a trace file to begin\n\n");
    }

    private void initComponents() {
        this.fileChooser = new JFileChooser();
        this.jSeparator8 = new JSeparator();
        this.jToolBar1 = new JToolBar();
        this.open = new JButton();
        this.jSeparator6 = new JToolBar.Separator();
        this.saveButton = new JButton();
        this.saveasButton = new JButton();
        this.jSeparator4 = new JToolBar.Separator();
        this.compute = new JButton();
        this.sh = new JButton();
        this.jSeparator5 = new JToolBar.Separator();
        this.plotnumber = new JTextField();
        this.jButton1 = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.console = new JTextArea();
        this.jLabel1 = new JLabel();
        this.status = new JLabel();
        this.jSeparator7 = new JSeparator();
        this.jLabel2 = new JLabel();
        this.jSeparator9 = new JSeparator();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.spin1 = new JSpinner();
        this.spin2 = new JSpinner();
        this.spin3 = new JSpinner();
        this.updateButton = new JButton();
        this.jSeparator10 = new JSeparator();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jComboBoxCipher = new JComboBox();
        this.jSeparator11 = new JSeparator();
        this.jLabel8 = new JLabel();
        this.jSeparator12 = new JSeparator();
        this.jMenuBar1 = new JMenuBar();
        this.filemenu = new JMenu();
        this.fileOpen = new JMenuItem();
        this.jSeparator1 = new JPopupMenu.Separator();
        this.jMenuItemSave = new JMenuItem();
        this.jMenuItemSaveAs = new JMenuItem();
        this.jSeparator2 = new JPopupMenu.Separator();
        this.closeMenu = new JMenuItem();
        this.jMenuItem6 = new JMenuItem();
        this.jMenu3 = new JMenu();
        this.computeMenu = new JMenuItem();
        this.jMenu4 = new JMenu();
        this.jMenuItem9 = new JMenuItem();
        this.jSeparator3 = new JPopupMenu.Separator();
        this.aboutMenu = new JMenuItem();
        this.fileChooser.setDialogTitle("Select trace file");
        this.fileChooser.setFileFilter(new MyCustomFilter());
        this.setDefaultCloseOperation(3);
        this.setTitle("Power Analysis Tool");
        this.setName("mainFrame");
        this.setResizable(false);
        this.jToolBar1.setFloatable(false);
        this.jToolBar1.setRollover(true);
        this.open.setIcon(new ImageIcon(this.getClass().getResource("/pat/resources/open.png")));
        this.open.setToolTipText("Open Trace");
        this.open.setFocusable(false);
        this.open.setHorizontalTextPosition(0);
        this.open.setVerticalTextPosition(3);
        this.open.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.openActionPerformed(evt);
            }
        });
        this.jToolBar1.add(this.open);
        this.jToolBar1.add(this.jSeparator6);
        this.saveButton.setIcon(new ImageIcon(this.getClass().getResource("/pat/resources/save.png")));
        this.saveButton.setToolTipText("Save Analysis report");
        this.saveButton.setFocusable(false);
        this.saveButton.setHorizontalTextPosition(0);
        this.saveButton.setVerticalTextPosition(3);
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.saveButtonActionPerformed(evt);
            }
        });
        this.jToolBar1.add(this.saveButton);
        this.saveasButton.setIcon(new ImageIcon(this.getClass().getResource("/pat/resources/saveas.png")));
        this.saveasButton.setToolTipText("Save Analysis report to new file");
        this.saveasButton.setFocusable(false);
        this.saveasButton.setHorizontalTextPosition(0);
        this.saveasButton.setVerticalTextPosition(3);
        this.saveasButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.saveasButtonActionPerformed(evt);
            }
        });
        this.jToolBar1.add(this.saveasButton);
        this.jToolBar1.add(this.jSeparator4);
        this.compute.setIcon(new ImageIcon(this.getClass().getResource("/pat/resources/start.png")));
        this.compute.setToolTipText("Start Analysis");
        this.compute.setEnabled(false);
        this.compute.setFocusable(false);
        this.compute.setHorizontalTextPosition(0);
        this.compute.setVerticalTextPosition(3);
        this.compute.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent evt) {
                MainFrame.this.computeMouseEntered(evt);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                MainFrame.this.computeMouseExited(evt);
            }
        });
        this.compute.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.computeActionPerformed(evt);
            }
        });
        this.jToolBar1.add(this.compute);
        this.sh.setText("Show/Hide Plot");
        this.sh.setToolTipText("Enter Plot number in the adjacent box");
        this.sh.setEnabled(false);
        this.sh.setFocusable(false);
        this.sh.setHorizontalTextPosition(0);
        this.sh.setVerticalTextPosition(3);
        this.sh.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.shActionPerformed(evt);
            }
        });
        this.jToolBar1.add(this.sh);
        this.jToolBar1.add(this.jSeparator5);
        this.plotnumber.setText("1");
        this.plotnumber.setPreferredSize(new Dimension(40, 25));
        this.jToolBar1.add(this.plotnumber);
        this.jButton1.setBackground(new Color(153, 255, 153));
        this.jButton1.setText("Hamming Calculator");
        this.jButton1.setFocusable(false);
        this.jButton1.setHorizontalTextPosition(0);
        this.jButton1.setVerticalTextPosition(3);
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.jButton1ActionPerformed(evt);
            }
        });
        this.jToolBar1.add(this.jButton1);
        this.console.setBackground(new Color(0, 0, 0));
        this.console.setColumns(20);
        this.console.setForeground(new Color(0, 204, 0));
        this.console.setLineWrap(true);
        this.console.setRows(5);
        this.jScrollPane1.setViewportView(this.console);
        this.jLabel1.setText("Status:");
        this.status.setText("idle");
        this.jLabel2.setText("Trace File Settings");
        this.jLabel3.setText("Plaintext Column no. (0..N)");
        this.jLabel4.setText("Irrelevant columns in front");
        this.jLabel5.setText("Irrelevant columns in rear");
        this.spin1.setModel(new SpinnerNumberModel((Number)0, Integer.valueOf(0), null, (Number)1));
        this.spin2.setModel(new SpinnerNumberModel((Number)2, Integer.valueOf(0), null, (Number)1));
        this.spin2.setToolTipText("");
        this.spin3.setModel(new SpinnerNumberModel((Number)0, Integer.valueOf(0), null, (Number)1));
        this.updateButton.setText("Update All Settings");
        this.updateButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.updateButtonActionPerformed(evt);
            }
        });
        this.jLabel6.setText("Console");
        this.jLabel7.setText("Cipher");
        this.jComboBoxCipher.setModel(new DefaultComboBoxModel<String>(new String[]{"AES128"}));
        this.jLabel8.setText("Encryption Algorithm Settings");
        this.filemenu.setText("File");
        this.fileOpen.setAccelerator(KeyStroke.getKeyStroke(79, 2));
        this.fileOpen.setText("Open Trace");
        this.fileOpen.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.fileOpenActionPerformed(evt);
            }
        });
        this.filemenu.add(this.fileOpen);
        this.filemenu.add(this.jSeparator1);
        this.jMenuItemSave.setAccelerator(KeyStroke.getKeyStroke(83, 2));
        this.jMenuItemSave.setText("Save");
        this.jMenuItemSave.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.jMenuItemSaveActionPerformed(evt);
            }
        });
        this.filemenu.add(this.jMenuItemSave);
        this.jMenuItemSaveAs.setAccelerator(KeyStroke.getKeyStroke(123, 2));
        this.jMenuItemSaveAs.setText("Save As");
        this.jMenuItemSaveAs.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.jMenuItemSaveAsActionPerformed(evt);
            }
        });
        this.filemenu.add(this.jMenuItemSaveAs);
        this.filemenu.add(this.jSeparator2);
        this.closeMenu.setAccelerator(KeyStroke.getKeyStroke(87, 2));
        this.closeMenu.setText("Close");
        this.closeMenu.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.closeMenuActionPerformed(evt);
            }
        });
        this.filemenu.add(this.closeMenu);
        this.jMenuItem6.setAccelerator(KeyStroke.getKeyStroke(88, 2));
        this.jMenuItem6.setText("Exit");
        this.jMenuItem6.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.jMenuItem6ActionPerformed(evt);
            }
        });
        this.filemenu.add(this.jMenuItem6);
        this.jMenuBar1.add(this.filemenu);
        this.jMenu3.setText("Control");
        this.computeMenu.setAccelerator(KeyStroke.getKeyStroke(67, 2));
        this.computeMenu.setText("Compute");
        this.computeMenu.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.computeMenuActionPerformed(evt);
            }
        });
        this.jMenu3.add(this.computeMenu);
        this.jMenuBar1.add(this.jMenu3);
        this.jMenu4.setText("Help");
        this.jMenuItem9.setText("Documentation");
        this.jMenu4.add(this.jMenuItem9);
        this.jMenu4.add(this.jSeparator3);
        this.aboutMenu.setText("About");
        this.aboutMenu.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.aboutMenuActionPerformed(evt);
            }
        });
        this.jMenu4.add(this.aboutMenu);
        this.jMenuBar1.add(this.jMenu4);
        this.setJMenuBar(this.jMenuBar1);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jToolBar1, -1, -1, 32767).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(this.jSeparator9, -2, 26, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jSeparator7, -2, 300, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel8).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jSeparator12, -1, 80, 32767)).addGroup(layout.createSequentialGroup().addComponent(this.jSeparator11, -2, 27, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jSeparator10)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.status)).addGroup(layout.createSequentialGroup().addGap(29, 29, 29).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.jLabel5).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.spin1, -1, 142, 32767).addComponent(this.spin2).addComponent(this.spin3)).addGap(112, 112, 112).addComponent(this.jLabel7).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.updateButton, -1, 149, 32767).addComponent(this.jComboBoxCipher, 0, -1, 32767)))).addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jToolBar1, -2, 25, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jSeparator9, GroupLayout.Alignment.TRAILING, -2, 6, -2)).addComponent(this.jLabel8).addComponent(this.jSeparator7, -2, 6, -2))).addGroup(layout.createSequentialGroup().addGap(25, 25, 25).addComponent(this.jSeparator12, -2, 5, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.spin1, -2, -1, -2).addComponent(this.jLabel7).addComponent(this.jComboBoxCipher, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel4).addComponent(this.spin2, -2, -1, -2)).addGap(6, 6, 6).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.spin3, -2, -1, -2).addComponent(this.updateButton))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jSeparator10, GroupLayout.Alignment.TRAILING, -2, 7, -2).addComponent(this.jLabel6).addComponent(this.jSeparator11, GroupLayout.Alignment.TRAILING, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -1, 297, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.status)).addContainerGap()));
        this.pack();
    }

    private void jMenuItem6ActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void computeActionPerformed(ActionEvent evt) {
        this.status.setText("Computing...");
        if (this.trace != null) {
            this.sh.setEnabled(true);
            try {
                this.ob = new PATMain(this.par1, this.par2, this.par3, this.par4);
                this.ob.init(this.trace);
                HashMap<Object, Object> values = this.ob.CPA(16);
                this.console.append("\n***********************************************\n");
                this.console.append("Obtained key: " + values.get("key").toString() + "\n");
                this.console.append("\n***********************************************\n");
                this.console.append("Time taken: " + values.get("time").toString() + "second(s)\n");
                this.console.append("\n");
                this.console.append("choose another file or show/hide original traces");
            }
            catch (IOException e) {
                this.console.append(e.getMessage());
            }
        }
        this.status.setText("idle");
    }

    private void shActionPerformed(ActionEvent evt) {
        if (this.shf == 0) {
            try {
                if (this.plotnumber.getText() != "" || this.plotnumber.getText() != null) {
                    if (Integer.parseInt(this.plotnumber.getText()) < this.ob.getNumberOfTraces() && Integer.parseInt(this.plotnumber.getText()) > 0) {
                        this.ob.plot(Integer.parseInt(this.plotnumber.getText()));
                    } else {
                        this.ob.plot(1);
                    }
                } else {
                    this.ob.plot(1);
                }
            }
            catch (Exception e) {
                this.status.setText(e.getMessage());
            }
            this.ob.pack();
            RefineryUtilities.centerFrameOnScreen((Window)((Object)this.ob));
            this.ob.setVisible(true);
            this.shf = 1;
        } else {
            this.ob.setVisible(false);
            this.shf = 0;
        }
        this.status.setText("idle");
    }

    private void openActionPerformed(ActionEvent evt) {
        this.status.setText("choosing a trace...");
        int returnVal = this.fileChooser.showOpenDialog(this);
        if (returnVal == 0) {
            File file = this.fileChooser.getSelectedFile();
            this.compute.setEnabled(true);
            this.trace = file;
            this.console.append("File chosen: " + file.getName().toString() + "\n");
            this.console.append("Press the tick(compute) button or select from Control menu, to Analyse trace\n");
            this.console.append("\n");
        } else {
            System.out.println("File access cancelled by user.");
        }
        this.status.setText("idle");
    }

    private void fileOpenActionPerformed(ActionEvent evt) {
        this.status.setText("choosing a trace...");
        int returnVal = this.fileChooser.showOpenDialog(this);
        if (returnVal == 0) {
            File file = this.fileChooser.getSelectedFile();
            this.compute.setEnabled(true);
            this.trace = file;
            this.console.append("File chosen: " + file.getName().toString() + "\n");
            this.console.append("Press the tick(compute) button or select from Control menu, to Analyse trace");
            this.console.append("\n");
        } else {
            System.out.println("File access cancelled by user.");
        }
        this.status.setText("idle");
    }

    private void computeMenuActionPerformed(ActionEvent evt) {
        this.status.setText("Computing...");
        if (this.trace != null) {
            this.sh.setEnabled(true);
            try {
                this.ob = new PATMain(this.par1, this.par2, this.par3, this.par4);
                this.ob.init(this.trace);
                HashMap<Object, Object> values = this.ob.CPA(16);
                this.console.append("\n***********************************************\n");
                this.console.append("Obtained key: " + values.get("key").toString() + "\n");
                this.console.append("\n***********************************************\n");
                this.console.append("Time taken: " + values.get("time").toString() + "second(s)\n");
                this.console.append("\n");
                this.console.append("choose another file or show/hide original traces");
            }
            catch (IOException e) {
                this.console.append(e.getMessage());
            }
        }
        this.status.setText("idle");
    }

    private void closeMenuActionPerformed(ActionEvent evt) {
        this.console.setText("Welcome to Power Analysis Tool (PAT) \nOpen a trace file to begin\n\n");
        this.trace = null;
        this.fileChosen = 0;
        this.compute.setEnabled(false);
        this.sh.setEnabled(false);
        this.status.setText("idle");
        this.save = false;
    }

    private void computeMouseEntered(MouseEvent evt) {
        this.status.setText("Perform Analysis");
    }

    private void computeMouseExited(MouseEvent evt) {
        this.status.setText("idle");
    }

    private void aboutMenuActionPerformed(ActionEvent evt) {
        About obj = new About();
        obj.setLocationRelativeTo(null);
        obj.setVisible(true);
    }

    private void updateButtonActionPerformed(ActionEvent evt) {
        this.par1 = (Integer)this.spin1.getValue();
        this.par2 = (Integer)this.spin2.getValue();
        this.par3 = (Integer)this.spin3.getValue();
        this.par4 = (String)this.jComboBoxCipher.getSelectedItem();
        System.out.println(this.par1 + " " + this.par2 + " " + this.par3 + " " + this.par4);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveButtonActionPerformed(ActionEvent evt) {
        if (!this.save) {
            JFileChooser saver = new JFileChooser();
            saver.setFileFilter(new MyCustomFilter1());
            saver.setDialogTitle("Save console output");
            saver.setApproveButtonText("Save");
            int actionDialog = saver.showOpenDialog(this);
            if (actionDialog != 0) {
                return;
            }
            File fileName = new File(saver.getSelectedFile() + ".txt");
            System.out.println(fileName);
            fileName.delete();
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(fileName, false));
                this.console.write(outFile);
            }
            catch (IOException ex) {
                this.status.setText(ex.getMessage());
            }
            finally {
                if (outFile != null) {
                    try {
                        outFile.close();
                    }
                    catch (IOException ex) {
                        this.status.setText(ex.getMessage());
                    }
                }
            }
            try {
                outFile.close();
            }
            catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.save = true;
            this.saveFile = fileName;
        } else {
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(this.saveFile, false));
                this.console.write(outFile);
            }
            catch (IOException ex) {
                this.status.setText(ex.getMessage());
            }
            finally {
                if (outFile != null) {
                    try {
                        outFile.close();
                    }
                    catch (IOException ex) {
                        this.status.setText(ex.getMessage());
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveasButtonActionPerformed(ActionEvent evt) {
        JFileChooser saver = new JFileChooser();
        saver.setDialogTitle("Save console output");
        saver.setApproveButtonText("Save");
        saver.setFileFilter(new MyCustomFilter1());
        int actionDialog = saver.showOpenDialog(this);
        if (actionDialog != 0) {
            return;
        }
        File fileName = new File(saver.getSelectedFile() + ".txt");
        BufferedWriter outFile = null;
        try {
            outFile = new BufferedWriter(new FileWriter(fileName));
            this.console.write(outFile);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (outFile != null) {
                try {
                    outFile.close();
                }
                catch (IOException ex) {}
            }
        }
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.hm = new HammingCalculator();
        this.hm.setLocationRelativeTo(null);
        this.hm.setVisible(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void jMenuItemSaveActionPerformed(ActionEvent evt) {
        if (!this.save) {
            JFileChooser saver = new JFileChooser();
            saver.setDialogTitle("Save console output");
            saver.setApproveButtonText("Save");
            saver.setFileFilter(new MyCustomFilter1());
            int actionDialog = saver.showOpenDialog(this);
            if (actionDialog != 0) {
                return;
            }
            File fileName = new File(saver.getSelectedFile() + ".txt");
            System.out.println(fileName);
            fileName.delete();
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(fileName, false));
                this.console.write(outFile);
            }
            catch (IOException ex) {
                this.status.setText(ex.getMessage());
            }
            finally {
                if (outFile != null) {
                    try {
                        outFile.close();
                    }
                    catch (IOException ex) {
                        this.status.setText(ex.getMessage());
                    }
                }
            }
            try {
                outFile.close();
            }
            catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.save = true;
            this.saveFile = fileName;
        } else {
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(this.saveFile, false));
                this.console.write(outFile);
            }
            catch (IOException ex) {
                this.status.setText(ex.getMessage());
            }
            finally {
                if (outFile != null) {
                    try {
                        outFile.close();
                    }
                    catch (IOException ex) {
                        this.status.setText(ex.getMessage());
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void jMenuItemSaveAsActionPerformed(ActionEvent evt) {
        JFileChooser saver = new JFileChooser();
        saver.setDialogTitle("Save console output");
        saver.setApproveButtonText("Save");
        saver.setFileFilter(new MyCustomFilter1());
        int actionDialog = saver.showOpenDialog(this);
        if (actionDialog != 0) {
            return;
        }
        File fileName = new File(saver.getSelectedFile() + ".txt");
        BufferedWriter outFile = null;
        try {
            outFile = new BufferedWriter(new FileWriter(fileName));
            this.console.write(outFile);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (outFile != null) {
                try {
                    outFile.close();
                }
                catch (IOException ex) {}
            }
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (!"Windows".equals(info.getName())) continue;
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

}

