/*
 * Decompiled with CFR 0.145.
 */
package pat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Point;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class About
extends JFrame {
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;

    public About() {
        this.initComponents();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setTitle("About PAT");
        this.setLocation(new Point(0, 0));
        this.setLocationByPlatform(true);
        this.setResizable(false);
        this.jLabel1.setFont(new Font("Tahoma", 0, 18));
        this.jLabel1.setForeground(new Color(51, 51, 255));
        this.jLabel1.setText("Power Analysis Tool (PAT)");
        this.jLabel2.setText("@author");
        this.jLabel3.setForeground(new Color(0, 0, 255));
        this.jLabel3.setText("Description");
        this.jLabel4.setText("Built for Academic use. PAT runs CPA in background to extract the secret KEY from a given");
        this.jLabel5.setText("set of traces in a CSV format. Refer to thedocumentation for how to use the tool.");
        this.jLabel6.setFont(new Font("Tahoma", 0, 14));
        this.jLabel6.setForeground(new Color(255, 0, 0));
        this.jLabel6.setText("Ritu Ranjan Shrivastwa");
        this.jLabel7.setHorizontalAlignment(0);
        this.jLabel7.setIcon(new ImageIcon(this.getClass().getResource("/pat/resources/ntu_logo.jpg")));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.jLabel7)).addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(24, 24, 24).addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel6).addGap(26, 26, 26).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel5).addGap(30, 30, 30).addComponent(this.jLabel7, -1, -1, 32767).addContainerGap()));
        this.pack();
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (!"Nimbus".equals(info.getName())) continue;
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                new About().setVisible(true);
            }
        });
    }

}

