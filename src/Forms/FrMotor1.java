/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;
import Tools.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class FrMotor1 extends javax.swing.JInternalFrame {

    Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB();
    public PreparedStatement stat;
    
    String vtipe_model, vnomor_rangka, vnomor_mesin, vktg, vsupp, vnama, vstok, vharga;
    String sqlselect, sqlinsert, sqldelete, sqlcekstok;
    DefaultTableModel tblmotor;
    
    public FrMotor1() {
        initComponents();
        setTabelMotor();showDataMotor();disableInput();
        listKategori(); listSupplier();clearInput();
    }

   private void clearInput(){
        cmbKtg.setSelectedIndex(0);
        cmbSupp.setSelectedIndex(0);
        txtNomorMesin.setText(""); 
        txtNomorRangka.setText("");
        txtStok.setText(""); 
        txtTipeModel.setText("");
        txtHarga.setText(""); 
        txtNamaMotor.setText("");
        
    }
    
    private void disableInput(){
        cmbKtg.setEnabled(false);cmbSupp.setEnabled(false);
        txtHarga.setEnabled(false);
        txtNamaMotor.setEnabled(false);
        txtNomorMesin.setEnabled(false);
        txtNomorRangka.setEnabled(false);
        txtStok.setEnabled(false);
        txtTipeModel.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableInput(){
        cmbSupp.setEnabled(true);cmbKtg.setEnabled(true);
        txtHarga.setEnabled(true);
        txtNamaMotor.setEnabled(true);
        txtNomorMesin.setEnabled(true);
        txtNomorRangka.setEnabled(true);
        txtStok.setEnabled(true);
        txtTipeModel.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
    }
    
    private void setTabelMotor(){
        String[] kolom1 = {"Tipe Model", "Nomor Rangka", "Nomor Mesin", "Kategori", "Supplier", "Nama Motor", "Stok", "Harga"};
        tblmotor = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
            java.lang.String.class,
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
                int cola = tblmotor.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tblMotor.setModel(tblmotor);
        tblMotor.getColumnModel().getColumn(0).setPreferredWidth(175);
        tblMotor.getColumnModel().getColumn(1).setPreferredWidth(75);
        tblMotor.getColumnModel().getColumn(2).setPreferredWidth(225);
        tblMotor.getColumnModel().getColumn(3).setPreferredWidth(175);
        tblMotor.getColumnModel().getColumn(4).setPreferredWidth(75);
        tblMotor.getColumnModel().getColumn(5).setPreferredWidth(225);
        tblMotor.getColumnModel().getColumn(6).setPreferredWidth(175);
        tblMotor.getColumnModel().getColumn(7).setPreferredWidth(75);
    
    }
    
    private void clearTabelMotor(){
        int row = tblmotor.getRowCount(); 
        for(int i=0; i<row; i++){
            tblmotor.removeRow(0); 
        }    
    }
    
    private void showDataMotor(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection(); 
            sqlselect = "select * from tbmotor a, tb_kategori b, tbsupplier c where a.ID_KTG=b.ID_KTG and a.ID_SUPPLIER=c.ID_SUPPLIER order by a.TIPE_MODEL asc";
            Statement stat = _Cnn.createStatement(); 
            ResultSet res = stat.executeQuery(sqlselect); 
            clearTabelMotor();
            while(res.next()){ 
                vtipe_model = res.getString("TIPE_MODEL");
                vnomor_rangka = res.getString("NOMOR_RANGKA");
                vnomor_mesin = res.getString("NOMOR_MESIN"); 
                vktg = res.getString("TAHUN_PEMBUATAN");
                vsupp = res.getString("NAMA_SUPPLIER"); 
                vnama = res.getString("NAMA_MOTOR"); 
                vstok = res.getString("STOK_MOTOR");
                vharga = res.getString("HARGA_MOTOR"); 
                
                
                Object[] data = {vtipe_model, vnomor_rangka, vnomor_mesin, vktg, vsupp, vnama, vstok, vharga}; 
                tblmotor.addRow(data); 
            }
            lblRecord.setText("Record : " + tblMotor.getRowCount()); 
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method showDataMotor() : "+ex);
        }    
    }
    
    String[] KeySupp;
    private void listSupplier(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbsupplier";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbSupp.removeAllItems();
            cmbSupp.repaint();
            cmbSupp.addItem(" -- Pilih -- ");
            int i = 1;
            while(res.next()){
                cmbSupp.addItem(res.getString("NAMA_SUPPLIER"));
                i++;
                
            }
            res.first();
            KeySupp = new String[i+1]; 
            for(Integer x=1; x<i; x++){
                KeySupp[x]=res.getString(1);
                res.next();
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method listSupplier(); "+ex);
        }
    }
    
    String[] KeyKtg;
    private void listKategori(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tb_kategori";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbKtg.removeAllItems();
            cmbKtg.repaint();
            cmbKtg.addItem(" -- Pilih -- ");
            int i = 1;
            while(res.next()){
                cmbKtg.addItem(res.getString("TAHUN_PEMBUATAN"));
                i++;
                
            }
            res.first();
            KeyKtg = new String[i+1]; 
            for(Integer x=1; x<i; x++){
                KeyKtg[x]=res.getString(1);
                res.next();
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method listKategori(); "+ex);
        }
    }
    
    private void aksiSimpan(){
        if(txtTipeModel.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Tipe model harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNamaMotor.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nama Motor harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNomorRangka.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nomor Rangka harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNomorMesin.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nomor Mesin harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(cmbKtg.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Anda belum memilih Kategori!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(cmbSupp.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Anda belum memilih Supplier!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtHarga.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Harga harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtStok.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Stok harus diisi!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else {
            vharga = txtHarga.getText();
            vnama = txtNamaMotor.getText();
            vnomor_mesin = txtNomorMesin.getText();
            vnomor_rangka = txtNomorRangka.getText();
            vtipe_model = txtTipeModel.getText();
            vstok = txtStok.getText();
            vktg = KeyKtg[cmbKtg.getSelectedIndex()];
            vsupp = KeySupp[cmbSupp.getSelectedIndex()];
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                if(btnSimpan.getText().equals("Simpan")){
                    sqlinsert = "insert into tbmotor set TIPE_MODEL='"+vtipe_model+"',"
                            + " ID_KTG='"+vktg+"', ID_SUPPLIER='"+vsupp+"', "
                            + " NOMOR_RANGKA='"+vnomor_rangka+"', NOMOR_MESIN='"+vnomor_mesin+"', "
                            + " NAMA_MOTOR='"+vnama+"', STOK_MOTOR='"+vstok+"', HARGA_MOTOR='"+vharga+"' ";
                    
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil disimpan!","Informasi",JOptionPane.INFORMATION_MESSAGE);
                btnHapus.setText("Hapus");
                btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
                }else if(btnSimpan.getText().equals("simpan")){
                    sqlinsert = "update tbmotor set "
                            + " ID_KTG='"+vktg+"', ID_SUPPLIER='"+vsupp+"', "
                            + " NOMOR_RANGKA='"+vnomor_rangka+"', NOMOR_MESIN='"+vnomor_mesin+"', "
                            + " NAMA_MOTOR='"+vnama+"', STOK_MOTOR='"+vstok+"', HARGA_MOTOR='"+vharga+"' "
                            + " where TIPE_MODEL='"+vtipe_model+"' ";
                btnSimpan.setText("Simpan");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/trans-add.png")));
                Statement stat = _Cnn.createStatement();stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil diubah!","Informasi",JOptionPane.INFORMATION_MESSAGE);
                }
                
                showDataMotor();clearInput();disableInput();
                btnTambah.setText("Tambah");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method aksiSimpan() : "+ex);
            }
        }
    }
    
    private void aksiHapus(){
    int jawab = JOptionPane.showConfirmDialog(this, "Apakah Anda akan menghapus data ini ? Tipe Model "+vtipe_model,
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from tbmotor where TIPE_MODEL='"+vtipe_model+"' ";
            Statement stat = _Cnn.createStatement();
            stat.executeUpdate(sqldelete);
            JOptionPane.showMessageDialog(this, "Informasi","Data Berhasil Disimpan!", JOptionPane.INFORMATION_MESSAGE);
            showDataMotor();clearInput();disableInput();
            btnTambah.setText("Tambah");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/trans-add.png")));
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method aksiHapus() : "+ex);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblRecord = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtTipeModel = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNomorRangka = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtNomorMesin = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNamaMotor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        cmbKtg = new javax.swing.JComboBox<>();
        cmbSupp = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMotor = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setTitle(".: Form Data Motor");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/EXY.png"))); // NOI18N

        lblRecord.setText("Record : ");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Data Barang"));
        jPanel2.setOpaque(false);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel1.setText("Tipe Model");

        jLabel2.setText("Nomor Rangka");

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus))
                .addContainerGap())
        );

        jLabel6.setText("Nomor Mesin");

        jLabel7.setText("Nama Motor");

        jLabel8.setText("Stok");

        txtStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStokKeyTyped(evt);
            }
        });

        jLabel11.setText("Harga");

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

        cmbKtg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Tahun --" }));

        cmbSupp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Supplier --" }));

        jLabel12.setText("Kategori");

        jLabel13.setText("Supplier");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomorRangka, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipeModel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbKtg, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNomorMesin, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(cmbSupp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(38, 38, 38))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNamaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTipeModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(txtNamaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNomorRangka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNomorMesin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbKtg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSupp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(28, 28, 28))))
        );

        tblMotor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Tipe Model", "Nomor Rangka", "Nomor Mesin", "Kategori", "Supplier", "Nama Motor", "Stok", "Harga"
            }
        ));
        tblMotor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMotorMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMotor);

        jLabel3.setText("Klik 2x untuk mengubah dan menghapus data!");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        jLabel4.setText("Cari Motor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
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
            txtTipeModel.requestFocus(true);
            btnTambah.setText("Batal");
            btnHapus.setText("Bersihkan");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
            btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/bersihkan.png")));
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
       }else if(txtTipeModel.getText().equals("")){
           JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus!","Informasi", JOptionPane.INFORMATION_MESSAGE);
       }else{
           aksiHapus();
       }
       btnSimpan.setText("Simpan"); 
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        aksiSimpan();
    }//GEN-LAST:event_txtHargaActionPerformed

    private void tblMotorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMotorMouseClicked
        if(evt.getClickCount() == 2){
            int row = tblMotor.getSelectedRow();
            vtipe_model = tblMotor.getValueAt(row, 0).toString();
            vnomor_rangka = tblMotor.getValueAt(row, 1).toString();
            vnomor_mesin = tblMotor.getValueAt(row, 2).toString();
            vktg = tblMotor.getValueAt(row, 3).toString();
            vsupp = tblMotor.getValueAt(row, 4).toString();
            vnama = tblMotor.getValueAt(row, 5).toString();
            vstok = tblMotor.getValueAt(row, 6).toString();
            vharga = tblMotor.getValueAt(row, 7).toString();
            
            cmbKtg.setSelectedItem(vktg);cmbSupp.setSelectedItem(vsupp);
            txtHarga.setText(vharga); txtNamaMotor.setText(vnama);
            txtNomorMesin.setText(vnomor_mesin); txtNomorRangka.setText(vnomor_rangka);
            txtStok.setText(vstok); txtTipeModel.setText(vtipe_model);
            enableInput();
            btnTambah.setText("Batal");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                     getResource("/Icons/btn_delete.png")));
            btnHapus.setEnabled(true);
            txtTipeModel.setEnabled(false);
            txtTipeModel.requestFocus(true);
            btnSimpan.setText("simpan");
            btnHapus.setText("Hapus");
            btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                     getResource("/Icons/trans-hapus.png")));
        }
    }//GEN-LAST:event_tblMotorMouseClicked

    private void cari(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelMotor();
            sqlselect = "select * from tbmotor a, tb_kategori b, tbsupplier c where a.ID_KTG=b.ID_KTG and a.ID_SUPPLIER=c.ID_SUPPLIER and "
                    + "a.NAMA_MOTOR like '%"+txtCari.getText()+"%' "
                   + " order by a.TIPE_MODEL asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vtipe_model = res.getString("TIPE_MODEL");
                vnomor_rangka = res.getString("NOMOR_RANGKA");
                vnomor_mesin = res.getString("NOMOR_MESIN"); 
                vktg = res.getString("TAHUN_PEMBUATAN");
                vsupp = res.getString("NAMA_SUPPLIER"); 
                vnama = res.getString("NAMA_MOTOR");
                vstok = res.getString("STOK_MOTOR");
                vharga = res.getString("HARGA_MOTOR"); 
                Object[] data = {vtipe_model, vnomor_rangka, vnomor_mesin, vktg, vsupp, vnama, vstok, vharga}; 
                tblmotor.addRow(data); 
            }
            lblRecord.setText("Record : "+tblMotor.getRowCount());
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method cari() : "+ex);
        }
    }
    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        cari();
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
    private javax.swing.JComboBox<String> cmbKtg;
    private javax.swing.JComboBox<String> cmbSupp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tblMotor;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtNamaMotor;
    private javax.swing.JTextField txtNomorMesin;
    private javax.swing.JTextField txtNomorRangka;
    private javax.swing.JTextField txtStok;
    private javax.swing.JTextField txtTipeModel;
    // End of variables declaration//GEN-END:variables
}
