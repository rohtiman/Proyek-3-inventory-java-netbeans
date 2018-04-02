/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Tools.KoneksiDB;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Atsutane
 */
public class IfrLaporanBarang extends javax.swing.JInternalFrame {

    
    KoneksiDB getCnn = new KoneksiDB();
    Connection _Cnn;
    String sqlselect, vid_ktg, vthn_buat;
    SimpleDateFormat tglinput = new SimpleDateFormat("yy-MM-dd");
    /**
     * Creates new form IfrLaporanBarang
     */
    public IfrLaporanBarang() {
        initComponents();
        
        listKategori();
    }
    
    String[] KeyKtg;
    private void listKategori(){
        try {
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select ID_KTG, TAHUN_PEMBUATAN"
                    + " from tb_kategori order by ID_KTG asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbKtg.removeAllItems();
            cmbKtg.repaint();
            cmbKtg.addItem("-- Pilih --");
            int i = 1;
            while(res.next()){
                cmbKtg.addItem(res.getString(2));
                i++;
            }
            res.first();
            KeyKtg = new String[i+1];
            for(Integer x = 1;x < i;x++){
                KeyKtg[x] = res.getString(1);
                res.next();
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error method listKategori() : "
            + ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cetakLaporan1(){
        String pth = System.getProperty("user.dir") + "/laporan/rpBarangMotor.jrxml";
        String logo = System.getProperty("user.dir") + "/laporan/";
        try {
            Map<String, Object> parameters = new HashMap<>();
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            parameters.put("parLogo", logo);
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, _Cnn);
            
            JasperViewer.viewReport(jprint, false);
            
        } catch (SQLException | JRException ex) {
            JOptionPane.showMessageDialog(this, "Error method cetakLaporan1() : "
            + ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cetakLaporan2(){
        String pth = System.getProperty("user.dir") + "/laporan/rpBarangSparepart.jrxml";
        String logo = System.getProperty("user.dir") + "/laporan/";
        try {
            Map<String, Object> parameters = new HashMap<>();
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            parameters.put("parLogo", logo);
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, _Cnn);
            
            JasperViewer.viewReport(jprint, false);
            
        } catch (SQLException | JRException ex) {
            JOptionPane.showMessageDialog(this, "Error method cetakLaporan2() : "
            + ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cetakLaporan3(){
        String pth = System.getProperty("user.dir") + "/laporan/rpBarangMotor2.jrxml";
        String logo = System.getProperty("user.dir") + "/laporan/";
        vid_ktg = KeyKtg[cmbKtg.getSelectedIndex()];
        try {
            Map<String, Object> parameters = new HashMap<>();
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            parameters.put("parLogo", logo);
            parameters.put("parKtg", vid_ktg);
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, _Cnn);
            
            JasperViewer.viewReport(jprint, false);
            
        } catch (SQLException | JRException ex) {
            JOptionPane.showMessageDialog(this, "Error method cetakLaporan3() : "
            + ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cetakLaporan4(){
        String pth = System.getProperty("user.dir") + "/laporan/rpBarangSparepart2.jrxml";
        String logo = System.getProperty("user.dir") + "/laporan/";
        vid_ktg = KeyKtg[cmbKtg.getSelectedIndex()];
        try {
            Map<String, Object> parameters = new HashMap<>();
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            parameters.put("parLogo", logo);
            parameters.put("parKtg", vid_ktg);
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, _Cnn);
            
            JasperViewer.viewReport(jprint, false);
            
        } catch (SQLException | JRException ex) {
            JOptionPane.showMessageDialog(this, "Error method cetakLaporan3() : "
            + ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cmbKtg = new javax.swing.JComboBox<>();
        btnCetak3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        rbMotor = new javax.swing.JRadioButton();
        rbSparepart = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        btnCetak1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Laporan Barang");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/EXY.png"))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo_xyz.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Laporan Barang");

        jLabel3.setText("Form ini digunakan untuk cetak data barang");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Cetak Berdasarkan Kategori"));

        cmbKtg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));

        btnCetak3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/print inventory.png"))); // NOI18N
        btnCetak3.setText("Cetak");
        btnCetak3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetak3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbKtg, 0, 185, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnCetak3)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbKtg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCetak3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Pilih Barang"));

        buttonGroup1.add(rbMotor);
        rbMotor.setText("Motor");
        rbMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMotorActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbSparepart);
        rbSparepart.setText("Sparepart");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(rbMotor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbSparepart)
                .addGap(34, 34, 34))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbMotor)
                    .addComponent(rbSparepart))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Cetak Semua Data Barang"));

        btnCetak1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/print inventory.png"))); // NOI18N
        btnCetak1.setText("Cetak Semua");
        btnCetak1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetak1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnCetak1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnCetak1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMotorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbMotorActionPerformed

    private void btnCetak1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetak1ActionPerformed
        if(rbMotor.isSelected()){
            cetakLaporan1();
        }else if(rbSparepart.isSelected()){
            cetakLaporan2();
        }else{
            JOptionPane.showMessageDialog(this, "Anda Belum Memilih Barang");
        }
    }//GEN-LAST:event_btnCetak1ActionPerformed

    private void btnCetak3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetak3ActionPerformed
        if(rbMotor.isSelected()){
            cetakLaporan3();
            
        }else if(rbSparepart.isSelected()){
            cetakLaporan4();
            
        }else{
            JOptionPane.showMessageDialog(this, "Anda Belum Memilih Barang");
        }
        
    }//GEN-LAST:event_btnCetak3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetak1;
    private javax.swing.JButton btnCetak3;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbKtg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton rbMotor;
    private javax.swing.JRadioButton rbSparepart;
    // End of variables declaration//GEN-END:variables
}
