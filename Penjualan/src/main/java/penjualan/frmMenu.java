/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package penjualan;

/**
 *
 * @author asus
 */
public class frmMenu extends javax.swing.JFrame {
    private String role;
    
    
    public frmMenu() {
        initComponents();

    }
    
    public frmMenu(String role) {
        initComponents();
        this.role = role;
        setAccessBasedOnRole();
}

    
private void setAccessBasedOnRole() {
    if (role.equals("admin")) {
        // Tampilkan semua menu
        mnMaster.setEnabled(true);
        mnTransaksi.setEnabled(true);
    } else if (role.equals("user")) {
        // Hanya tampilkan menu transaksi
        mnMaster.setEnabled(false);
        mnTransaksi.setEnabled(true);
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        mnMaster = new javax.swing.JMenu();
        mnBarang = new javax.swing.JMenuItem();
        mnKonsumen = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        metu = new javax.swing.JMenuItem();
        mnTransaksi = new javax.swing.JMenu();
        transaksi = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        metu2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        mnMaster.setText("Data Master");

        mnBarang.setText("Data Barang");
        mnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnBarangActionPerformed(evt);
            }
        });
        mnMaster.add(mnBarang);

        mnKonsumen.setText("Data Konsumen");
        mnKonsumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnKonsumenActionPerformed(evt);
            }
        });
        mnMaster.add(mnKonsumen);
        mnMaster.add(jSeparator2);

        metu.setText("Logout");
        metu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                metuActionPerformed(evt);
            }
        });
        mnMaster.add(metu);

        jMenuBar1.add(mnMaster);

        mnTransaksi.setText("Transaksi");

        transaksi.setText("Transaksi");
        transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transaksiActionPerformed(evt);
            }
        });
        mnTransaksi.add(transaksi);
        mnTransaksi.add(jSeparator3);

        metu2.setText("Logout");
        metu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                metu2ActionPerformed(evt);
            }
        });
        mnTransaksi.add(metu2);

        jMenuBar1.add(mnTransaksi);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnBarangActionPerformed
    
    new frmBarang().setVisible(true);        
    }//GEN-LAST:event_mnBarangActionPerformed

    private void mnKonsumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnKonsumenActionPerformed
    
    //new frmKonsumen().setVisible(true);        
    }//GEN-LAST:event_mnKonsumenActionPerformed

    private void metuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_metuActionPerformed
    dispose();     
    }//GEN-LAST:event_metuActionPerformed

    private void transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transaksiActionPerformed
         new frmTransaksi().setVisible(true);    // TODO add your handling code here:
    }//GEN-LAST:event_transaksiActionPerformed

    private void metu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_metu2ActionPerformed
    dispose();
    }//GEN-LAST:event_metu2ActionPerformed

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
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuItem metu;
    private javax.swing.JMenuItem metu2;
    private javax.swing.JMenuItem mnBarang;
    private javax.swing.JMenuItem mnKonsumen;
    private javax.swing.JMenu mnMaster;
    private javax.swing.JMenu mnTransaksi;
    private javax.swing.JMenuItem transaksi;
    // End of variables declaration//GEN-END:variables
}
