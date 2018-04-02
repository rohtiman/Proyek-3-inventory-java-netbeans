/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Atsutane
 */
public class IfrBarangMotorMasuk extends javax.swing.JInternalFrame {

    Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB();
    
    String sqlinsert, sqlselect, sqldelete;
    String vno_barang_masuk, vid_user, vtipe_model, vtgl_masuk, 
            vjml_masuk;
    
    DefaultTableModel tblbarangmotormasuk;
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    
    /**
     * Creates new form IfrBarangMasuk
     */
    public IfrBarangMotorMasuk() {
        initComponents();
        
        clearInput(); disableInput(); listBarang(); listUser();
        setTabelBarangMotorMasuk(); showDataBarangMotorMasuk();
    }
    
    private void clearInput(){
        txtNoBrgMasuk.setText("");
        dtTglMasuk.setDate(new Date());
        cmbBarang.setSelectedIndex(0);
        txtJumlah.setText("");
        cmbUser.setSelectedIndex(0);
    }
    
     private void disableInput(){
        txtNoBrgMasuk.setEnabled(false);
        dtTglMasuk.setEnabled(false);
        cmbBarang.setEnabled(false);
        txtJumlah.setEnabled(false);
        cmbUser.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
     
    private void enableInput(){
        txtNoBrgMasuk.setEnabled(true);
        dtTglMasuk.setEnabled(true);
        cmbBarang.setEnabled(true);
        txtJumlah.setEnabled(true);
        cmbUser.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
    }
    
    private void setTabelBarangMotorMasuk(){
        String[] kolom1 = {"No. Barang Masuk", "Tanggal Masuk", "Barang", "Jumlah", "User"};
        tblbarangmotormasuk = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };
            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }
            
            // agar tabel tidak bisa diedit
            public boolean isCellEditable(int row, int col){
                int cola = tblbarangmotormasuk.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataBrgMasuk.setModel(tblbarangmotormasuk);
        tbDataBrgMasuk.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbDataBrgMasuk.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataBrgMasuk.getColumnModel().getColumn(2).setPreferredWidth(250);
        tbDataBrgMasuk.getColumnModel().getColumn(3).setPreferredWidth(25);
        tbDataBrgMasuk.getColumnModel().getColumn(4).setPreferredWidth(75);
    }
    
    private void clearTabelBarangMotorMasuk(){
        int row = tblbarangmotormasuk.getRowCount(); //variabel row diberi nilai jumlah baris pada tabel(model) jurusan
        for(int i=0; i<row; i++){
            tblbarangmotormasuk.removeRow(0); //menghapus record/baris
        }
    }
    
    private void showDataBarangMotorMasuk(){
        try {
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbmotor_masuk a, tbmotor b, tb_user c where a.TIPE_MODEL=b.TIPE_MODEL and"
                    + " a.ID_USER=c.ID_USER ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            clearTabelBarangMotorMasuk();
            while(res.next()){
                vno_barang_masuk = res.getString("NO_BARANG_MASUK");
                vtgl_masuk = tglview.format(res.getDate("TGL_MASUK"));
                String vtipe_model = res.getString("NAMA_MOTOR");
                vjml_masuk = res.getString("JML_MASUK");
                String vid_user = res.getString("NAMA_USER");
                
                Object[] data = {vno_barang_masuk, vtgl_masuk, vtipe_model, vjml_masuk, vid_user};
                tblbarangmotormasuk.addRow(data);
            }
            lblRecord.setText("Record : "+ tbDataBrgMasuk.getRowCount());
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error method showDataBarangMotorMasuk :"+ ex);
        }
    }
    
    private void cariNomorBarangMasuk(){
        try{
             _Cnn = null;
             _Cnn = getCnn.getConnection();
             clearTabelBarangMotorMasuk();
             sqlselect = "select * from tbmotor like '%"+txtCari.getText()+"%'"
                     +"order by NAMA_MOTOR asc ";
             Statement stat = _Cnn.createStatement();
             ResultSet res = stat.executeQuery(sqlselect);
             while(res.next()){
                vno_barang_masuk = res.getString("NO_BARANG_MASUK");
                vtgl_masuk = tglview.format(res.getDate("TGL_MASUK"));
                String vtipe_model = res.getString("NAMA_MOTOR");
                vjml_masuk = res.getString("JML_MASUK");
                String vid_user = res.getString("NAMA_USER");
                
                Object[] data = {vno_barang_masuk, vtgl_masuk, vtipe_model, vjml_masuk, vid_user};
                tblbarangmotormasuk.addRow(data);
                 }
                 lblRecord.setText("Record : "+tbDataBrgMasuk.getRowCount());
           } catch(SQLException ex){
               JOptionPane.showMessageDialog(this, "Error method cariNama() : "+ex);
               
           }
    }
    
    String[] KeyBar;
    private void listBarang(){
        try {
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbmotor";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            
            cmbBarang.removeAllItems();
            cmbBarang.repaint();
            cmbBarang.addItem("--Pilih--");
            int i=1;
            while(res.next()){
                cmbBarang.addItem(res.getString("NAMA_MOTOR"));
                i++;
            }
            res.first();
            KeyBar = new String[i+1]; // mengatur primary key yang disimpan
            for(Integer x=1; x<i; x++){
                KeyBar[x] = res.getString(1); //res.getString(i) ->res.getString("kd_jur")
                res.next();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error method listBarang() : " + ex);
        }
    }
    
    String[] KeyUser;
    private void listUser(){
        try {
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tb_user";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            
            cmbUser.removeAllItems();
            cmbUser.repaint();
            cmbUser.addItem("-- Pilih --");
            int i=1;
            while(res.next()){
                cmbUser.addItem(res.getString("NAMA_USER"));
                i++;
            }
            res.first();
            KeyUser = new String[i+1]; // mengatur primary key yang disimpan
            for(Integer x=1; x<i; x++){
                KeyUser[x] = res.getString(1); //res.getString(i) ->res.getString("kd_jur")
                res.next();
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, "Error method listUser() : " + ex);
        }
    }
    
    private void aksiSimpan(){
            vno_barang_masuk = txtNoBrgMasuk.getText();
            vtipe_model = KeyBar[cmbBarang.getSelectedIndex()];
            vtgl_masuk = tglinput.format(dtTglMasuk.getDate());
            vjml_masuk = txtJumlah.getText();
            vid_user = KeyUser[cmbUser.getSelectedIndex()];
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                if(btnSimpan.getText().equals("Simpan")){
                    sqlinsert = "insert into tbmotor_masuk set NO_BARANG_MASUK='"+vno_barang_masuk+"', "
                            + " TGL_MASUK='"+vtgl_masuk+"', TIPE_MODEL='"+vtipe_model+"', JML_MASUK='"+vjml_masuk+"', "
                            + " ID_USER='"+vid_user+"' ";
                    Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil disimpan", "Informasi",
                         JOptionPane.INFORMATION_MESSAGE);
                btnHapus.setText("Hapus");
                btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
                }else if(btnSimpan.getText().equals("simpan")){
                    sqlinsert = "update tbmotor_masuk set"
                            + " TGL_MASUK='"+vtgl_masuk+"', TIPE_MODEL='"+vtipe_model+"', JML_MASUK='"+vjml_masuk+"', "
                            + " ID_USER='"+vid_user+"' "
                            + " where NO_BARANG_MASUK='"+vno_barang_masuk+"' ";
                    Statement stat =_Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil diubah","Informasi",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
                showDataBarangMotorMasuk(); clearInput(); disableInput();
                btnTambah.setText("Tambah");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
            }catch(SQLException ex){
                JOptionPane.showConfirmDialog(this, "Error method aksiSimpan() : " + ex);
                txtNoBrgMasuk.requestFocus(true);
            }
            
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah Anda akan menghapus data ini? kode"+vno_barang_masuk, 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelete = "delete from tbmotor_masuk where NO_BARANG_MASUK = '"+vno_barang_masuk+"'";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus", 
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                showDataBarangMotorMasuk(); clearInput(); disableInput();
                btnTambah.setText("Tambah");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method aksiHapus() : " + ex);
            }
        }else{
        
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNoBrgMasuk = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        cmbUser = new javax.swing.JComboBox<>();
        cmbBarang = new javax.swing.JComboBox<>();
        dtTglMasuk = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataBrgMasuk = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setClosable(true);
        setTitle("Form Barang Motor Masuk");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/EXY.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("No. Barang Masuk");

        jLabel2.setText("Tanggal Masuk");

        jLabel3.setText("Barang");

        jLabel4.setText("Jumlah");

        jLabel5.setText("User");

        txtNoBrgMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoBrgMasukActionPerformed(evt);
            }
        });

        cmbUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));

        cmbBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save-black.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.setPreferredSize(new java.awt.Dimension(70, 30));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/trans-hapus.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.setPreferredSize(new java.awt.Dimension(70, 30));
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/trans-add.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.setPreferredSize(new java.awt.Dimension(70, 30));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(65, 65, 65)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNoBrgMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dtTglMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtNoBrgMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtTglMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jLabel6.setText("Klik 2x untuk mengubah dan mengahpus data !");

        jLabel7.setText("Cari nomor barang masuk");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        tbDataBrgMasuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "No. Barang Masuk", "Tanggal Masuk", "Barang", "Jumlah", "User"
            }
        ));
        tbDataBrgMasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataBrgMasukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataBrgMasuk);

        lblRecord.setText("Record : ");

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-icon.png"))); // NOI18N
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Form Barang Motor Masuk");

        jLabel9.setText("Form ini digunakan untuk mengolah data motor masuk");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo_xyz.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel10)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9))
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        aksiSimpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(btnTambah.getText().equals("Tambah")){
            clearInput();
            enableInput();
            txtNoBrgMasuk.requestFocus(true);
            btnTambah.setText("Batal");
            btnHapus.setText("Bersihkan");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/Icons/btn_delete.png")));
            btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/bersihkan.png")));
            //createAutoID();
            //txtIdUser.setText(Integer.toString(ID_USER));
        }else if(btnTambah.getText().equals("Batal")){
            clearInput();
            disableInput();
            btnTambah.setText("Tambah");
            btnSimpan.setText("Simpan");
            btnHapus.setText("Hapus");
            btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/Icons/trans-add.png")));
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(btnHapus.getText().equals("Bersihkan")){
            clearInput();
        }else if(txtNoBrgMasuk.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus","Informasi",
                 JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiHapus();
        }
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataBrgMasukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataBrgMasukMouseClicked
        if(evt.getClickCount() == 2){
            int row = tbDataBrgMasuk.getSelectedRow();
            vno_barang_masuk = tbDataBrgMasuk.getValueAt(row, 0).toString();
            vtgl_masuk = tbDataBrgMasuk.getValueAt(row, 1).toString();
            String vtipe_model = tbDataBrgMasuk.getValueAt(row, 2).toString();
            vjml_masuk = tbDataBrgMasuk.getValueAt(row, 3).toString();
            String vid_user = tbDataBrgMasuk.getValueAt(row, 4).toString();
            
            txtNoBrgMasuk.setText(vno_barang_masuk);
            dtTglMasuk.setDateFormatString(vtgl_masuk);
            cmbBarang.setSelectedItem(vtipe_model);
            txtJumlah.setText(vjml_masuk);
            cmbUser.setSelectedItem(vid_user);
            enableInput();
            btnTambah.setText("Batal");
           btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
           btnHapus.setEnabled(true);
           txtNoBrgMasuk.setEnabled(false);
           btnSimpan.setText("simpan");
           btnHapus.setText("Hapus");
           btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
        }
    }//GEN-LAST:event_tbDataBrgMasukMouseClicked

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        cariNomorBarangMasuk();
    }//GEN-LAST:event_btnCariActionPerformed

    private void txtNoBrgMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoBrgMasukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoBrgMasukActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        cariNomorBarangMasuk();
    }//GEN-LAST:event_txtCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbBarang;
    private javax.swing.JComboBox<String> cmbUser;
    private com.toedter.calendar.JDateChooser dtTglMasuk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataBrgMasuk;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNoBrgMasuk;
    // End of variables declaration//GEN-END:variables
}
