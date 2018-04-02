/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Tools.KoneksiDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jdk.internal.instrumentation.Logger;

/**
 *
 * @author user
 */
public class IfrSparepart extends javax.swing.JInternalFrame {
    Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB(); 
    public String query;
    public PreparedStatement stat;
    public ResultSet res;
    public int NOMOR_SERI;
    
    String vnoseri, vid_ktg, vid_supplier, vnama_sparepart, vstok, vharga;
    String sqlselect, sqlinsert, sqldelete;
    DefaultTableModel tblsparepart;
    
    public IfrSparepart() {
        initComponents();
         clearInput(); disableInput(); listKategori(); listSupplier();
         setTabelSparepart(); showDataSparepart();
    }

     private void clearInput(){
         txtNoSeri.setText("");
         cmbKategori.setSelectedIndex(0); 
         cmbSupplier.setSelectedIndex(0); 
         txtNamaSparepart.setText("");
         txtStok.setText("");
         txtHarga.setText(""); 
         txtCari.setText(""); 
           
    }
     
    private void disableInput(){
        txtNoSeri.setEnabled(false);
        cmbKategori.setEnabled(false);
        cmbSupplier.setEnabled(false);
        txtNamaSparepart.setEnabled(false);
        txtStok.setEnabled(false);
        txtHarga.setEnabled(false);
        txtCari.setEnabled(true);
        btnSimpan.setEnabled(false); 
        btnHapus.setEnabled(false); 
    
    }
      
      private void enableInput(){
        txtNoSeri.setEnabled(true);
        cmbKategori.setEnabled(true);
        cmbSupplier.setEnabled(true);
        txtNamaSparepart.setEnabled(true);
        txtStok.setEnabled(true);
        txtHarga.setEnabled(true);
        txtCari.setEnabled(false);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
      }
     
      private void setTabelSparepart(){
        String[] kolom1 = {"Nomor Seri", "Kategori", "Supplier", "Nama Sparepart", "Stok", "Harga"};
        tblsparepart = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
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
            
            //agar tabel tidak bisa diedit
            public boolean isCellEditable(int row, int col){
                int cola = tblsparepart.getColumnCount();
                return (col < cola) ? false : true;
               
            }
        };
        tbDataSparepart.setModel(tblsparepart);
        tbDataSparepart.getColumnModel().getColumn(0).setPreferredWidth(80);
        tbDataSparepart.getColumnModel().getColumn(1).setPreferredWidth(110);
        tbDataSparepart.getColumnModel().getColumn(2).setPreferredWidth(80);
        tbDataSparepart.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbDataSparepart.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbDataSparepart.getColumnModel().getColumn(5).setPreferredWidth(75);
    }
      
      private void clearTabelSparepart(){
        int row = tblsparepart.getRowCount(); //variabel row diberi nilai jumlah record/baris pada tabel(model) jurusan
        for(int i=0; i<row; i++){    
            tblsparepart.removeRow(0); //menghapus record/baris dari tabel jurusan maksud dari method removeRow
        } 
    }
      
      private void showDataSparepart(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();          
            sqlselect = "select * from tbsparepart a, tb_kategori b, tbsupplier c where a.ID_KTG=b.ID_KTG and a.ID_SUPPLIER=c.ID_SUPPLIER";      //query sql select 
             Statement stat = _Cnn.createStatement();    
             ResultSet res = stat.executeQuery(sqlselect); 
            clearTabelSparepart();
            while(res.next()){                  
                vnoseri = res.getString("NOMOR_SERI");
                vid_ktg = res.getString("TAHUN_PEMBUATAN");     
                vid_supplier = res.getString("NAMA_SUPPLIER");
                vnama_sparepart = res.getString("NAMA_SPAREPART");
                vstok = res.getString("STOK_SPAREPAR");
                vharga = res.getString("HARGA_SPAREPART");
                
                Object[] data = {vnoseri, vid_ktg, vid_supplier, vnama_sparepart, vstok, vharga};    //memebuat object array untuk menampung data record 
                tblsparepart.addRow(data);      //menyisipkan baris yang nilainya sesuai array data
            }
            
            lblRecord.setText("Record : " + tbDataSparepart.getRowCount()); //menampilkan jumlah baris
            
        }catch(SQLException ex){
             JOptionPane.showMessageDialog(this, "Error Method showDataSparepart() : "+ex);
            
        }  
    
    }
      private void cariSparepart(){
         try {
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelSparepart();
            sqlselect = "select * from tbsparepart a, tb_kategori b, tbsupplier c "
                    + " where a.ID_KTG=b.ID_KTG and a.ID_SUPPLIER=c.ID_SUPPLIER and a.NAMA_SPAREPART like '%"+txtCari.getText()+"%'"
                    + " order by a.NOMOR_SERI asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vnoseri = res.getString("NOMOR_SERI");
                vid_ktg = res.getString("TAHUN_PEMBUATAN");
                vid_supplier = res.getString("NAMA_SUPPLIER");
                vnama_sparepart = res.getString("NAMA_SPAREPART");
                vstok = res.getString("STOK_SPAREPAR");
                vharga = res.getString("HARGA_SPAREPART");
                Object[]data = {vnoseri, vid_ktg, vid_supplier, vnama_sparepart, 
                    vstok, vharga};
                tblsparepart.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataSparepart.getRowCount()); 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error Method cariSparepart() : "+ex);
        }
    }
     
      
      private void aksiSimpan(){
          if (txtNoSeri.getText().equals("")) { 
              JOptionPane.showMessageDialog(this, "Nomor Seri harus diisi !");
          }else if (cmbKategori.getSelectedIndex()<=0) {
              JOptionPane.showMessageDialog(this, "Anda belum memilih kategori !");
          }else if(cmbSupplier.getSelectedIndex()<=0){
              JOptionPane.showMessageDialog(this, "Anda belum memilih supplier!",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
          }else if(txtNamaSparepart.getText().equals("")){
              JOptionPane.showMessageDialog(this, "Nama Sparepart harus Diisi!",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
          }else if(txtStok.getText().equals("")){
              JOptionPane.showMessageDialog(this, "Stok harus Diisi!",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
          }else if(txtHarga.getText().equals("")){
              JOptionPane.showMessageDialog(this, "Harga harus Diisi!",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }else{
        vnoseri = txtNoSeri.getText();
        vid_ktg = KeyKtg[cmbKategori.getSelectedIndex()];
        vid_supplier = Keysupplier[cmbSupplier.getSelectedIndex()];
        vnama_sparepart = txtNamaSparepart.getText();
        vstok = txtStok.getText();
        vharga = txtHarga.getText();
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            if(btnSimpan.getText().equals("Simpan")){
                sqlinsert = "insert into tbsparepart set NOMOR_SERI='"+vnoseri+"', ID_KTG='"+vid_ktg+"', ID_SUPPLIER='"+vid_supplier+"', NAMA_SPAREPART='"+vnama_sparepart+"', " 
                        + " STOK_SPAREPAR='"+vstok+"', HARGA_SPAREPART='"+vharga+"' ";
                 Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil disimpan", 
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE);
                btnHapus.setText("Hapus");
                btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
            }else if(btnSimpan.getText().equals("simpan")){
                sqlinsert = "update tbsparepart set "
                        + " ID_KTG='"+vid_ktg+"', ID_SUPPLIER='"+vid_supplier+"', NAMA_SPAREPART='"+vnama_sparepart+"', STOK_SPAREPAR='"+vstok+"', HARGA_SPAREPART='"+vharga+"' "
                        + " where NOMOR_SERI='"+vnoseri+"' ";
                            btnSimpan.setText("simpan");
                            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
                            Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil diubah",
                        "Informasi",
                         JOptionPane.INFORMATION_MESSAGE);
            
            }
            showDataSparepart(); clearInput(); disableInput(); 
            btnTambah.setText("Tambah");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
        }catch(SQLException ex){
             JOptionPane.showMessageDialog(this, "Isikan Data dengan benar ! ");
                  }
       }
    }
      
      private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah Anda Akan Menghapus Data Ini ? Kode "+vnoseri,
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelete = "delete from tbsparepart where NOMOR_SERI='"+vnoseri+"' ";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(this, "Informasi",
                        "Data Berhasil dihapus!", JOptionPane.INFORMATION_MESSAGE);
                showDataSparepart(); clearInput(); disableInput();
                btnTambah.setText("Tambah");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Data ini sudah di pakai : "+ex);
            }
        }   
    }
      
      String[] KeyKtg;
    private void listKategori(){         //membuat dinamis combobox
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tb_kategori";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbKategori.removeAllItems();
            cmbKategori.repaint();
            cmbKategori.addItem("--Pilih--");
            int i = 1;
            while(res.next()){
                cmbKategori.addItem(res.getString("TAHUN_PEMBUATAN")); 
                i++; 
            }
            res.first();
            KeyKtg = new String[i+1];       //mengatur primary key yang disimpan
            for (Integer x = 1 ; x<i ; x++){
                KeyKtg[x] = res.getString(1); // res.getstring(1)=> res.getstring("kd_jur");
                res.next();
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listKategori() : " +ex);
            
        }
    
    }
    
    String[] Keysupplier;
    private void listSupplier(){         //membuat dinamis combobox
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbsupplier";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbSupplier.removeAllItems();
            cmbSupplier.repaint();
            cmbSupplier.addItem("--Pilih--");
            int i = 1;
            while(res.next()){
                cmbSupplier.addItem(res.getString("NAMA_SUPPLIER")); 
                i++; 
            }
            res.first();
            Keysupplier = new String[i+1];       //mengatur primary key yang disimpan
            for (Integer x = 1 ; x<i ; x++){
                Keysupplier[x] = res.getString(1); // res.getstring(1)=> res.getstring("kd_jur");
                res.next();
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listsupplier() : " +ex);
            
        }
    
    }
    
     public void createAutoID(){
        try{
            _Cnn = getCnn.getConnection();
            query = "select max(NOMOR_SERI) from tbsparepart";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            if(res.first()){
                NOMOR_SERI = res.getInt(1)+1;
            }else{
                NOMOR_SERI = 1;
            }
            stat.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error method createAutoID() : "
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNoSeri = new javax.swing.JTextField();
        cmbSupplier = new javax.swing.JComboBox<>();
        cmbKategori = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        txtStok = new javax.swing.JTextField();
        txtNamaSparepart = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataSparepart = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        lblRecord = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setClosable(true);
        setTitle(":. Form Data Sparepart");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Data Barang"));

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel3.setText("Kategori");

        jLabel4.setText("Nomor Seri");

        jLabel5.setText("Supplier");

        txtNoSeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoSeriActionPerformed(evt);
            }
        });

        cmbSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", " " }));

        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", " " }));
        cmbKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKategoriActionPerformed(evt);
            }
        });

        jLabel6.setText("Stok");

        jLabel7.setText("Harga");

        jLabel8.setText("Nama Sparepart");

        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });
        txtHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaKeyTyped(evt);
            }
        });

        txtStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStokActionPerformed(evt);
            }
        });
        txtStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStokKeyTyped(evt);
            }
        });

        txtNamaSparepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaSparepartActionPerformed(evt);
            }
        });

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

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

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/trans-hapus.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSimpan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHapus)
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNoSeri)
                    .addComponent(cmbSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbKategori, 0, 141, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtStok)
                    .addComponent(txtHarga)
                    .addComponent(txtNamaSparepart, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNoSeri, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaSparepart)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStok, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );

        tbDataSparepart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nomor Seri", "Kategori", "Supplier", "Nama Sparepart", "Stok", "Harga"
            }
        ));
        tbDataSparepart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataSparepartMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataSparepart);
        if (tbDataSparepart.getColumnModel().getColumnCount() > 0) {
            tbDataSparepart.getColumnModel().getColumn(0).setResizable(false);
        }

        jLabel9.setText("Klik 2x untuk mengubah dan menghaspus data");

        jLabel11.setText("Cari Sparepart");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblRecord.setText("Record :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNoSeriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoSeriActionPerformed
       
    }//GEN-LAST:event_txtNoSeriActionPerformed

    private void cmbKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKategoriActionPerformed
        
    }//GEN-LAST:event_cmbKategoriActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
      aksiSimpan();
    }//GEN-LAST:event_txtHargaActionPerformed

    private void txtStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStokActionPerformed

    }//GEN-LAST:event_txtStokActionPerformed

    private void txtNamaSparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSparepartActionPerformed
    }//GEN-LAST:event_txtNamaSparepartActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
         if(btnTambah.getText().equals("Tambah")){
            clearInput();
            enableInput();
            cmbKategori.requestFocus(true);
            btnTambah.setText("Batal");
            btnHapus.setText("Bersihkan");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
            btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/bersihkan.png")));
            createAutoID();
            txtNoSeri.setText(Integer.toString(NOMOR_SERI));
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

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
       aksiSimpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
     if(btnHapus.getText().equals("Bersihkan")){
            clearInput();
       }else if(txtNoSeri.getText().equals("")){
           JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus",
                   "Informasi", JOptionPane.INFORMATION_MESSAGE);
       }else{
           aksiHapus();
       }
       btnSimpan.setText("Simpan"); 
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataSparepartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataSparepartMouseClicked
        if(evt.getClickCount() == 2){
            int row = tbDataSparepart.getSelectedRow();
            vnoseri = tbDataSparepart.getValueAt(row, 0).toString();
            vid_ktg = tbDataSparepart.getValueAt(row, 1).toString();
            vid_supplier = tbDataSparepart.getValueAt(row, 2).toString(); 
            vnama_sparepart = tbDataSparepart.getValueAt(row, 3).toString();
            vstok = tbDataSparepart.getValueAt(row, 4).toString();
            vharga = tbDataSparepart.getValueAt(row, 5).toString();
            
            txtNoSeri.setText(vnoseri);
            cmbKategori.setSelectedItem(vid_ktg);
            cmbSupplier.setSelectedItem(vid_supplier);
            txtNamaSparepart.setText(vnama_sparepart);
            txtStok.setText(vstok);
            txtHarga.setText(vharga); 
            
            enableInput();
           btnTambah.setText("Batal");
           btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
           btnHapus.setEnabled(true);
           txtNoSeri.setEnabled(false);
           cmbKategori.requestFocus(true);
           btnSimpan.setText("simpan");
           btnHapus.setText("Hapus");
           btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
        }
    }//GEN-LAST:event_tbDataSparepartMouseClicked

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
      cariSparepart();
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtStokKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStokKeyTyped
    if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtStokKeyTyped

    private void txtHargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaKeyTyped
    if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbKategori;
    private javax.swing.JComboBox<String> cmbSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataSparepart;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtNamaSparepart;
    private javax.swing.JTextField txtNoSeri;
    private javax.swing.JTextField txtStok;
    // End of variables declaration//GEN-END:variables
}
