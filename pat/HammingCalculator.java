/*
 * Decompiled with CFR 0.145.
 */
package pat;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class HammingCalculator
extends JFrame {
    private JButton calHamming;
    private JLabel distance;
    private JTextField hexA;
    private JTextField hexB;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel sumHD;

    public HammingCalculator() {
        this.initComponents();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.hexA = new JTextField();
        this.hexB = new JTextField();
        this.jLabel3 = new JLabel();
        this.distance = new JLabel();
        this.calHamming = new JButton();
        this.jLabel5 = new JLabel();
        this.jLabel4 = new JLabel();
        this.sumHD = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("HEX A (obtained)");
        this.jLabel2.setText("HEX B (original)");
        this.hexA.setFont(new Font("Consolas", 0, 11));
        this.hexB.setFont(new Font("Consolas", 0, 11));
        this.hexB.setText("48 9D B4 B3 F3 17 29 61 CC 2B CB 4E D2 E2 8E B7");
        this.jLabel3.setText("Distance (nibble wise)");
        this.distance.setFont(new Font("Consolas", 0, 11));
        this.distance.setText("0");
        this.calHamming.setText("Calculate");
        this.calHamming.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HammingCalculator.this.calHammingActionPerformed(evt);
            }
        });
        this.jLabel5.setFont(new Font("Tahoma", 1, 12));
        this.jLabel5.setText("Enter space separated 128-bit hexadecimal values");
        this.jLabel4.setText("Sum (unmatched bits)");
        this.sumHD.setFont(new Font("Consolas", 0, 11));
        this.sumHD.setText("0");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addGap(0, 0, 32767)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel4)).addGap(9, 9, 9).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.sumHD, -1, -1, 32767).addGroup(layout.createSequentialGroup().addComponent(this.calHamming).addGap(0, 0, 32767)).addComponent(this.hexB, -1, 315, 32767).addComponent(this.hexA).addComponent(this.distance, -1, -1, 32767)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel5).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.hexA, -2, -1, -2)).addGap(20, 20, 20).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.hexB, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.distance)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.sumHD)).addGap(18, 18, 18).addComponent(this.calHamming).addContainerGap(-1, 32767)));
        this.pack();
    }

    private void calHammingActionPerformed(ActionEvent evt) {
        String[] a = this.hexA.getText().trim().split(" ");
        String[] b = this.hexB.getText().trim().split(" ");
        String dist = "";
        int sum = 0;
        for (int i = 0; i < a.length; ++i) {
            int temp = this.hammingDistance(a[i], b[i]);
            if (temp < 10) {
                dist = dist + "0" + temp + " ";
                sum += temp;
                continue;
            }
            dist = dist + temp + " ";
            sum += Integer.sum(temp % 10, temp / 10);
        }
        this.distance.setText(dist);
        this.sumHD.setText(sum + "");
    }

    private int hammingDistance(String a, String b) {
        int dist = 0;
        int tdist = 0;
        char[] A = a.toCharArray();
        char[] B = b.toCharArray();
        System.out.println(A[0] + "" + A[1] + " " + B[0] + "" + B[1]);
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        map.put(Character.valueOf('0'), 0);
        map.put(Character.valueOf('1'), 1);
        map.put(Character.valueOf('2'), 2);
        map.put(Character.valueOf('3'), 3);
        map.put(Character.valueOf('4'), 4);
        map.put(Character.valueOf('5'), 5);
        map.put(Character.valueOf('6'), 6);
        map.put(Character.valueOf('7'), 7);
        map.put(Character.valueOf('8'), 8);
        map.put(Character.valueOf('9'), 9);
        map.put(Character.valueOf('A'), 10);
        map.put(Character.valueOf('B'), 11);
        map.put(Character.valueOf('C'), 12);
        map.put(Character.valueOf('D'), 13);
        map.put(Character.valueOf('E'), 14);
        map.put(Character.valueOf('F'), 15);
        tdist = Integer.bitCount(((Integer)map.get(Character.valueOf(A[0])) | (Integer)map.get(Character.valueOf(B[0]))) - ((Integer)map.get(Character.valueOf(A[0])) & (Integer)map.get(Character.valueOf(B[0]))));
        dist = dist * 10 + tdist;
        System.out.println(tdist + " ");
        tdist = Integer.bitCount(((Integer)map.get(Character.valueOf(A[1])) | (Integer)map.get(Character.valueOf(B[1]))) - ((Integer)map.get(Character.valueOf(A[1])) & (Integer)map.get(Character.valueOf(B[1]))));
        dist = dist * 10 + tdist;
        System.out.println(tdist + " ");
        return dist;
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
            Logger.getLogger(HammingCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(HammingCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(HammingCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(HammingCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                new HammingCalculator().setVisible(true);
            }
        });
    }

}

