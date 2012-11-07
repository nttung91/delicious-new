/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeliciousCrawler.gui;

import DeliciousCrawler.Console;
import DeliciousCrawler.GetFollowerByAuthor;
import DeliciousCrawler.GetLinkHistory;
import DeliciousCrawler.GetLinkInfo;
import DeliciousCrawler.LinkByTag;
import DeliciousCrawler.RecentTag;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author THANHTUNG
 */
public class DeliciousCrawlerGUI extends javax.swing.JFrame {

    ThreadGroup[] TGroup = new ThreadGroup[5];

    public DeliciousCrawlerGUI() {
        initComponents();
        //thread = new Thread(this, "Main");

        TGroup[0] = new ThreadGroup("Recent Link By Tag");
        TGroup[1] = new ThreadGroup("Recent Tag");
        TGroup[2] = new ThreadGroup("Link History");
        TGroup[3] = new ThreadGroup("Link Info");
        TGroup[4] = new ThreadGroup("Follower");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtOutput = new javax.swing.JTextArea();
        btnGetLinkByTag = new javax.swing.JToggleButton();
        btnGetRecentTag = new javax.swing.JToggleButton();
        btnGetLinkHistory = new javax.swing.JToggleButton();
        btnGetLinkInfo = new javax.swing.JToggleButton();
        btnGetFollower = new javax.swing.JToggleButton();
        jspNumberOfThread = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuShowData = new javax.swing.JMenuItem();
        menuDatabaseStatus = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Delicious.com Crawler");

        txtOutput.setColumns(20);
        txtOutput.setRows(5);
        jScrollPane1.setViewportView(txtOutput);

        btnGetLinkByTag.setText("Get Link By Tag");
        btnGetLinkByTag.setToolTipText("");
        btnGetLinkByTag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetLinkByTagActionPerformed(evt);
            }
        });

        btnGetRecentTag.setText("Get Recent Tag");
        btnGetRecentTag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetRecentTagActionPerformed(evt);
            }
        });

        btnGetLinkHistory.setText("Get Link History");
        btnGetLinkHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetLinkHistoryActionPerformed(evt);
            }
        });

        btnGetLinkInfo.setText("Get Link Info");
        btnGetLinkInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetLinkInfoActionPerformed(evt);
            }
        });

        btnGetFollower.setText("Get Follower");
        btnGetFollower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetFollowerActionPerformed(evt);
            }
        });

        jspNumberOfThread.setValue(3);

        jLabel1.setText("Number Of Thread :");

        jMenu1.setText("File");

        jMenuItem1.setText("Exit");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Database");

        menuShowData.setText("Show Data");
        menuShowData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuShowDataActionPerformed(evt);
            }
        });
        jMenu3.add(menuShowData);

        menuDatabaseStatus.setText("Database Status");
        menuDatabaseStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDatabaseStatusActionPerformed(evt);
            }
        });
        jMenu3.add(menuDatabaseStatus);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnGetRecentTag, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGetLinkByTag, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGetLinkInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGetLinkHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGetFollower, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jspNumberOfThread, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 24, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGetLinkByTag, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGetRecentTag, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGetLinkInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGetLinkHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGetFollower, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jspNumberOfThread, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //var

    private void btnGetRecentTagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetRecentTagActionPerformed
      try {
            new Console(this.txtOutput);
        } catch (IOException e) {
        }
        if (this.btnGetRecentTag.isSelected()) {
            RecentTag rt = new RecentTag("Recent Tag", TGroup[1]);
            rt.start();
            //JOptionPane.showMessageDialog(null, "select");
        } else {
            TGroup[1].stop();
            //JOptionPane.showMessageDialog(null, "Not select");
        }
    }//GEN-LAST:event_btnGetRecentTagActionPerformed

    private void btnGetLinkByTagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetLinkByTagActionPerformed
        try {
            new Console(this.txtOutput);
        } catch (IOException e) {
        }
        //kiem tra tham so nhap vao
        int NumberOfThread = (int) jspNumberOfThread.getModel().getValue();


        if (this.btnGetLinkByTag.isSelected()) {
            JOptionPane.showMessageDialog(null, "Starting....");
            LinkByTag[] threads = new LinkByTag[NumberOfThread];
            for (int i = 0; i < threads.length; i++) {
                if (threads[i] == null) {
                    threads[i] = new LinkByTag("Thread #" + (i + 1), i, 0, threads.length, TGroup[0]);
                }
            }
            JOptionPane.showMessageDialog(null, "Started Complete!");
        } else {
            TGroup[0].stop();
            JOptionPane.showMessageDialog(null, "Finished");
        }
    }//GEN-LAST:event_btnGetLinkByTagActionPerformed

    private void btnGetLinkInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetLinkInfoActionPerformed
        try {
            new Console(this.txtOutput);
        } catch (IOException e) {
        }
        int NumberOfThread = (int) jspNumberOfThread.getModel().getValue();

        //   JOptionPane.showMessageDialog(null,NumberOfThread+"-"+timeScan+"-"+timeToStart);
        if (this.btnGetLinkInfo.isSelected()) {
            JOptionPane.showMessageDialog(null, "Starting....");
            int duration = 1000;
            GetLinkInfo[] threads = new GetLinkInfo[NumberOfThread];
            for (int i = 0; i < threads.length; i++) {
                if (threads[i] == null) {
                    threads[i] = new GetLinkInfo(TGroup[3], i, duration, threads.length);
                }
            }
            JOptionPane.showMessageDialog(null, "Started Complete!");
        } else {
            TGroup[3].stop();
            JOptionPane.showMessageDialog(null, "Finished");
        }
    }//GEN-LAST:event_btnGetLinkInfoActionPerformed

    private void btnGetLinkHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetLinkHistoryActionPerformed
       try {
            new Console(this.txtOutput);
        } catch (IOException e) {
        }
        int NumberOfThread = (int) jspNumberOfThread.getModel().getValue();

        //   JOptionPane.showMessageDialog(null,NumberOfThread+"-"+timeScan+"-"+timeToStart);
        if (this.btnGetLinkHistory.isSelected()) {
            JOptionPane.showMessageDialog(null, "Starting....");
            int duration = 10;

            GetLinkHistory[] threads = new GetLinkHistory[NumberOfThread];
            for (int i = 0; i < threads.length; i++) {
                if (threads[i] == null) {
                    threads[i] = new GetLinkHistory(TGroup[2], i, duration, threads.length);

                }
            }
            JOptionPane.showMessageDialog(null, "Started Complete!");
        } else {
            TGroup[2].stop();
            JOptionPane.showMessageDialog(null, "Finished");
        }
    }//GEN-LAST:event_btnGetLinkHistoryActionPerformed

    private void btnGetFollowerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetFollowerActionPerformed
        try {
            new Console(this.txtOutput);
        } catch (IOException e) {
        }
        int NumberOfThread = (int) jspNumberOfThread.getModel().getValue();

        //   JOptionPane.showMessageDialog(null,NumberOfThread+"-"+timeScan+"-"+timeToStart);
        if (this.btnGetFollower.isSelected()) {
            JOptionPane.showMessageDialog(null, "Starting....");
            int duration = 1000;
            GetFollowerByAuthor[] threads = new GetFollowerByAuthor[NumberOfThread];
            for (int i = 0; i < threads.length; i++) {
                if (threads[i] == null) {

                    threads[i] = new GetFollowerByAuthor(TGroup[4], i, duration, threads.length);
                }
            }
            JOptionPane.showMessageDialog(null, "Started Complete!");
        } else {
            TGroup[4].stop();
            JOptionPane.showMessageDialog(null, "Finished");
        }
    }//GEN-LAST:event_btnGetFollowerActionPerformed

    private void menuDatabaseStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDatabaseStatusActionPerformed
        DatabaseStatus ds = new DatabaseStatus(this, true);
        ds.setVisible(true);
    }//GEN-LAST:event_menuDatabaseStatusActionPerformed

    private void menuShowDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuShowDataActionPerformed
       ShowData ds = new ShowData(this, true);
        ds.setVisible(true);
    }//GEN-LAST:event_menuShowDataActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DeliciousCrawlerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DeliciousCrawlerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DeliciousCrawlerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeliciousCrawlerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DeliciousCrawlerGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnGetFollower;
    private javax.swing.JToggleButton btnGetLinkByTag;
    private javax.swing.JToggleButton btnGetLinkHistory;
    private javax.swing.JToggleButton btnGetLinkInfo;
    private javax.swing.JToggleButton btnGetRecentTag;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jspNumberOfThread;
    private javax.swing.JMenuItem menuDatabaseStatus;
    private javax.swing.JMenuItem menuShowData;
    private javax.swing.JTextArea txtOutput;
    // End of variables declaration//GEN-END:variables
}
