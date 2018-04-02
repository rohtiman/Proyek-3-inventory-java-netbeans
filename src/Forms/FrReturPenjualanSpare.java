
package Forms;

import Tools.KoneksiDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrReturPenjualanSpare extends javax.swing.JInternalFrame {

    Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB();
    
    String vno_retur, vbrg, vjml, vstatus, vuser, vtgl, vsupp;
    String sqlselect, sqlinsert, sqldelete;
    DefaultTableModel tblretursp;
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    
    public FrReturPenjualanSpare() {
        initComponents();
        clearInput();disableInput();
        setTabelReturSP();showDataReturJualSP();
        listSP();listUser();
    }

    private void clearInput(){
        cmbBarang.setSelectedIndex(0);cmbUser.setSelectedIndex(0);
        txtJmlRetur.setText("");txtStatusRetur.setText("");
        txtNoRetur.setText("");
        dtTglRetur.setDate(new Date());
        txtNoRetur.requestFocus();
        
    }
    
    private void disableInput(){
        cmbBarang.setEnabled(false);cmbUser.setEnabled(false);
        txtJmlRetur.setEnabled(false);
        txtNoRetur.setEnabled(false);txtStatusRetur.setEnabled(false);
        btnSimpan.setEnabled(false);btnBersih.setEnabled(false);
        }
    
    private void enableInput(){
        cmbBarang.setEnabled(true);cmbUser.setEnabled(true);
        txtJmlRetur.setEnabled(true);txtStatusRetur.setEnabled(true);
        txtNoRetur.setEnabled(true);btnSimpan.setEnabled(true);
        btnSimpan.setEnabled(true);
    }
    
    private void setTabelReturSP(){
        String[] kolom1 = {"No.Retur", "Tanggal Retur", "Barang", "Supplier","Jumlah Retur","Status Retur","User"};
        tblretursp = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class
            };
            
            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }
            
            public boolean isCellEditable(int row, int col){
                int cola = tblretursp.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tblReturJualSP.setModel(tblretursp);
        tblReturJualSP.getColumnModel().getColumn(0).setPreferredWidth(175);
        tblReturJualSP.getColumnModel().getColumn(1).setPreferredWidth(75);
        tblReturJualSP.getColumnModel().getColumn(2).setPreferredWidth(225);
        tblReturJualSP.getColumnModel().getColumn(3).setPreferredWidth(175);
        tblReturJualSP.getColumnModel().getColumn(4).setPreferredWidth(75);
        tblReturJualSP.getColumnModel().getColumn(5).setPreferredWidth(225);
        tblReturJualSP.getColumnModel().getColumn(6).setPreferredWidth(175);
    
    }
    
    private void clearTabelReturJualSP(){
        int row = tblretursp.getRowCount();
        for(int i=0; i<row; i++){
            tblretursp.removeRow(0);
        }
    }
    
    private void showDataReturJualSP(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelReturJualSP();
            sqlselect = "select * from tbretur_penjualan_sparepart a, tb_user b, tbsparepart c, tbsupplier d "
                    + " where a.ID_USER = b.ID_USER and a.NOMOR_SERI=c.NOMOR_SERI and c.ID_SUPPLIER=d.ID_SUPPLIER order by a.NOMOR_SERI asc ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vbrg            = res.getString("NAMA_SPAREPART");
                vjml       = res.getString("JML_RETUR_PENJUALAN");
                vno_retur             = res.getString("NO_RETUR_PENJUALAN");
                vstatus      = res.getString("STATUS_RETUR_PENJUALAN");
                vtgl      = tglview.format(res.getDate("TGL_RETUR_PENJUALAN_SPAREPART"));
                vuser       = res.getString("NAMA_USER");
                vsupp       = res.getString("NAMA_SUPPLIER");
                Object[] data = {vno_retur, vtgl, vbrg, vsupp, 
                vjml, vstatus, vuser};
                tblretursp.addRow(data);
            }
            lblRecord.setText("Record : "+tblReturJualSP.getRowCount());
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method showDataReturJualSP() : "+ex);
        }
    }
    
    String[] KeySP;
    private void listSP(){
    try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbsparepart order by NOMOR_SERI asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbBarang.removeAllItems();
            cmbBarang.repaint();
            cmbBarang.addItem(" -- Pilih -- ");
            int i = 1;
            while(res.next()){
                cmbBarang.addItem(res.getString(4));
                i++;
                
            }
            res.first();
            KeySP = new String[i+1]; // mengatur primary key yg disimpan
            for(Integer x = 1; x < i; x++){
                KeySP[x] = res.getString(1); 
                res.next();
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method listMotor(); "+ex);
        }
    }
    
    String[] KeyUser;
    private void listUser(){
    try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tb_user order by ID_USER asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbUser.removeAllItems();
            cmbUser.repaint();
            cmbUser.addItem(" -- Pilih -- ");
            int i = 1;
            while(res.next()){
                cmbUser.addItem(res.getString(3));
                i++;
                
            }
            res.first();
            KeyUser = new String[i+1]; // mengatur primary key yg disimpan
            for(Integer x = 1; x < i; x++){
                KeyUser[x] = res.getString(1); 
                res.next();
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method listUser(); "+ex);
        }
    }
    
    private void aksiSimpan(){
        if(txtNoRetur.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nomor Retur harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(cmbBarang.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Anda belum memilih barang!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(tglinput.format(dtTglRetur.getDate()).equals("")){
            JOptionPane.showMessageDialog(this, "Tanggal Retur harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtJmlRetur.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Jumlah Retur harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtStatusRetur.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Status Retur harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(cmbUser.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Anda belum memilih user!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else {
            vjml = txtJmlRetur.getText();
            vbrg = KeySP[cmbBarang.getSelectedIndex()];
            vuser = KeyUser[cmbUser.getSelectedIndex()];
            vno_retur = txtNoRetur.getText();
            vtgl  = tglinput.format(dtTglRetur.getDate());
            vstatus = txtStatusRetur.getText();
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                if(btnSimpan.getText().equals("Simpan")){
                    sqlinsert = "insert into tbretur_penjualan_sparepart set NO_RETUR_PENJUALAN='"+vno_retur+"',"
                            + " ID_USER='"+vuser+"', NOMOR_SERI='"+vbrg+"', TGL_RETUR_PENJUALAN_SPAREPART='"+vtgl+"', "
                            + " JML_RETUR_PENJUALAN='"+vjml+"', STATUS_RETUR_PENJUALAN='"+vstatus+"' ";
                    Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil disimpan","Informasi",JOptionPane.INFORMATION_MESSAGE);
                btnBersih.setEnabled(false);
                }else if(btnSimpan.getText().equals("simpan")){
                    sqlinsert = "update tbretur_penjualan_sparepart set "
                            + " ID_USER='"+vuser+"', NOMOR_SERI='"+vbrg+"', TGL_RETUR_PENJUALAN_SPAREPART='"+vtgl+"', "
                            + " JML_RETUR_PENJUALAN='"+vjml+"', STATUS_RETUR_PENJUALAN='"+vstatus+"' "
                            + " where NO_RETUR_PENJUALAN='"+vno_retur+"' ";
                    btnSimpan.setText("Simpan");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/trans-add.png")));
                Statement stat = _Cnn.createStatement();stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil diubah","Informasi",JOptionPane.INFORMATION_MESSAGE);
                }
                showDataReturJualSP();clearInput();disableInput();
                btnTambah.setText("Tambah");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method aksiSimpan() : "+ex);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtNoRetur = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnBersih = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        dtTglRetur = new com.toedter.calendar.JDateChooser();
        cmbBarang = new javax.swing.JComboBox<>();
        txtJmlRetur = new javax.swing.JTextField();
        txtStatusRetur = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cmbUser = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblReturJualSP = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();

        setClosable(true);
        setTitle(".: Form Retur Penjualan");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/EXY.png"))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Retur Penjualan Sparepart"));
        jPanel2.setOpaque(false);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        txtNoRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoReturKeyTyped(evt);
            }
        });

        jLabel1.setText("No. Retur");

        jLabel2.setText("Tanggal Retur");

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/trans-add.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save-black.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBersih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bersihkan.png"))); // NOI18N
        btnBersih.setText("Bersihkan");
        btnBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBersih)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnSimpan)
                    .addComponent(btnBersih))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Barang");

        jLabel7.setText("User");

        jLabel13.setText("Jumlah Retur");

        cmbBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Barang --" }));

        txtJmlRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJmlReturKeyTyped(evt);
            }
        });

        txtStatusRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStatusReturKeyTyped(evt);
            }
        });

        jLabel14.setText("Status Retur");

        cmbUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih User --" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtNoRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(dtTglRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJmlRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStatusRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoRetur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7)
                    .addComponent(cmbUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtTglRetur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtJmlRetur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtStatusRetur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        tblReturJualSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No. Retur", "Tanggal Retur", "Barang", "Supplier", "Jumlah Retur", "Status Retur", "User"
            }
        ));
        tblReturJualSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblReturJualSPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblReturJualSP);

        jLabel3.setText("Klik 2x untuk mengubah dan menghapus data!");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        jLabel4.setText("Cari Nomor Retur");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblRecord.setText("Record : ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblRecord)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
if(btnTambah.getText().equals("Tambah")){
            clearInput();
            enableInput();
            btnTambah.setText("Batal");
            btnBersih.setText("Bersihkan");
            btnBersih.setEnabled(true);
            btnBersih.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/bersihkan.png")));
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
        }else if(btnTambah.getText().equals("Batal")){
            clearInput();
            disableInput();
            btnTambah.setText("Tambah");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
    }//GEN-LAST:event_btnTambahActionPerformed
    }
 
private void cari(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelReturJualSP();
            sqlselect = "select * from tbretur_penjualan_sparepart a, tb_user b, tbsparepart c, tbsupplier d "
                    + " where a.ID_USER = b.ID_USER and a.NOMOR_SERI=c.NOMOR_SERI and c.ID_SUPPLIER=d.ID_SUPPLIER and a.NO_RETUR_PENJUALAN  like '%"+txtCari.getText()+"%' "
                    + " order by c.NOMOR_SERI asc ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                 vbrg            = res.getString("NAMA_SPAREPART");
                vjml       = res.getString("JML_RETUR_PENJUALAN");
                vno_retur             = res.getString("NO_RETUR_PENJUALAN");
                vstatus      = res.getString("STATUS_RETUR_PENJUALAN");
                vtgl      = tglview.format(res.getDate("TGL_RETUR_PENJUALAN_SPAREPART"));
                vuser       = res.getString("NAMA_USER");
                vsupp       = res.getString("NAMA_SUPPLIER");
                Object[] data = {vno_retur, vtgl, vbrg, vsupp, 
                vjml, vstatus, vuser};
                tblretursp.addRow(data);
            }
            lblRecord.setText("Record : "+tblReturJualSP.getRowCount());
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method cari() : "+ex);
        }
    }
    private void tblReturJualSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblReturJualSPMouseClicked
        if(evt.getClickCount() == 2){
            int row = tblReturJualSP.getSelectedRow();
            vno_retur = tblReturJualSP.getValueAt(row, 0).toString();
            
            txtNoRetur.setText(vno_retur); 
            try{
            _Cnn = null;
            _Cnn  = getCnn.getConnection();
            sqlselect = "select * from tbretur_penjualan_sparepart a, tb_user b, tbsparepart c, tbsupplier d "
                    + " where a.ID_USER = b.ID_USER and a.NOMOR_SERI=c.NOMOR_SERI and c.ID_SUPPLIER=d.ID_SUPPLIER and NO_RETUR_PENJUALAN= '" +vno_retur+ "' order by a.NOMOR_SERI asc ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            if(res.first()){
                cmbBarang.setSelectedItem(res.getString("NAMA_SPAREPART"));
                cmbUser.setSelectedItem(res.getString("NAMA_USER"));
                txtJmlRetur.setText(res.getString("JML_RETUR_PENJUALAN"));
                dtTglRetur.setDate(res.getDate("TGL_RETUR_PENJUALAN_SPAREPART"));
                txtStatusRetur.setText(res.getString("STATUS_RETUR_PENJUALAN"));
                
                
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method tblReturJualMotorMouseClicked(java.awt.event.MouseEvent evt) : "+ex,
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
            enableInput();
            
            txtNoRetur.setEnabled(false);
            btnSimpan.setText("simpan");  
            
}
    }//GEN-LAST:event_tblReturJualSPMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
       aksiSimpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        clearInput();
    }//GEN-LAST:event_btnBersihActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        cari();
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtNoReturKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoReturKeyTyped
        if(txtNoRetur.getText().length()==11){
                evt.consume();
            }
            if(!Character.isDigit(evt.getKeyChar())){
                evt.consume();
            }
    }//GEN-LAST:event_txtNoReturKeyTyped

    private void txtJmlReturKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJmlReturKeyTyped
        if(txtJmlRetur.getText().length()==11){
                evt.consume();
            }
            if(!Character.isDigit(evt.getKeyChar())){
                evt.consume();
            }
    }//GEN-LAST:event_txtJmlReturKeyTyped

    private void txtStatusReturKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStatusReturKeyTyped
        if(txtStatusRetur.getText().length()==50){
                evt.consume();
            }
            
    }//GEN-LAST:event_txtStatusReturKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBersih;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbBarang;
    private javax.swing.JComboBox<String> cmbUser;
    private com.toedter.calendar.JDateChooser dtTglRetur;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tblReturJualSP;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtJmlRetur;
    private javax.swing.JTextField txtNoRetur;
    private javax.swing.JTextField txtStatusRetur;
    // End of variables declaration//GEN-END:variables
}
