/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeliciousCrawler.gui;

import model.dao.*;

/**
 *
 * @author THANHTUNG
 */
public class DatabaseStatus extends javax.swing.JDialog {

    /**
     * Creates new form DatabaseStatus
     */
    public DatabaseStatus(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        lblLinks = new javax.swing.JLabel();
        lblUsers = new javax.swing.JLabel();
        lblTags = new javax.swing.JLabel();
        lblSaveLink = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblTagLink = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        jLabel6.setText("jLabel6");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setPreferredSize(new java.awt.Dimension(500, 500));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Number Of Links");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel5.setText("Number Of Users");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel7.setText("Number Of Tags");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, 70, 40));

        lblLinks.setText("Number Of Links");
        getContentPane().add(lblLinks, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        lblUsers.setText("Number Of Users");
        getContentPane().add(lblUsers, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, -1, -1));

        lblTags.setText("Number Of Tags");
        getContentPane().add(lblTags, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, -1, -1));

        lblSaveLink.setText("Number Of Tags");
        getContentPane().add(lblSaveLink, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, -1, -1));

        jLabel8.setText("Number Of Saves Link");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        lblTagLink.setText("Number Of Tags");
        getContentPane().add(lblTagLink, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, -1, -1));

        jLabel9.setText("Number Of Tags Link");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        LinkDAO link = new LinkDAO();
        TagDAO tag = new TagDAO();
        AuthorDAO au = new AuthorDAO();
        SaveLinkDAO sl = new SaveLinkDAO();
        TagLinkDAO tl = new TagLinkDAO();
        lblLinks.setText(link.count() + " Link");
        lblTags.setText(tag.count() + " Tag");
        lblUsers.setText(au.count() + " User");
        lblSaveLink.setText(sl.count()+ " Save Link");
        lblTagLink.setText(tl.count() + " Tags Link");
    }//GEN-LAST:event_formWindowOpened

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

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
            java.util.logging.Logger.getLogger(DatabaseStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatabaseStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatabaseStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatabaseStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DatabaseStatus dialog = new DatabaseStatus(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblLinks;
    private javax.swing.JLabel lblSaveLink;
    private javax.swing.JLabel lblTagLink;
    private javax.swing.JLabel lblTags;
    private javax.swing.JLabel lblUsers;
    // End of variables declaration//GEN-END:variables
}
