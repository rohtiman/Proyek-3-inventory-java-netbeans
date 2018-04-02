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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class IfrPermintaanBarangMotor extends javax.swing.JInternalFrame {
     Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB(); 
    public String query;
    public PreparedStatement stat;
    public ResultSet res;
    public int KD_ORDER_MOTOR;
    
    String vNoOrder, vdtTglMasuk, vbrg, vjml, vuser, vCariNoOrder;
    String sqlselect, sqlinsert, sqldelete;
    DefaultTableModel tblpermintaanbrg;
    
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    
    public IfrPermintaanBarangMotor() {
        initComponents();
        clearInput(); disableInput(); listBarang(); listUser();
         setTabelPermintaanBarang(); showDataPermintaanBarangMotor();
    }
    
    private void clearInput(){
         txtNoOrder.setText("");
         dtTglMasuk.setDate(new Date()); 
         cmbBarang.setSelectedIndex(0); 
         txtJumlah.setText("");
         cmbUser.setSelectedIndex(0);
         txtCariNoOrder.setText("");   
    }
    
    private void disableInput(){
        txtNoOrder.setEnabled(false);
        dtTglMasuk.setEnabled(false);
        cmbBarang.setEnabled(false);
        txtJumlah.setEnabled(false);
        cmbUser.setEnabled(false);
        txtCariNoOrder.setEnabled(true);
        btnSimpan.setEnabled(false); 
        btnHapus.setEnabled(false);
    
    }
    
     private void enableInput(){
        txtNoOrder.setEnabled(true);
        dtTglMasuk.setEnabled(true);
        cmbBarang.setEnabled(true);
        txtJumlah.setEnabled(true);
        cmbUser.setEnabled(true);
        txtCariNoOrder.setEnabled(false);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
      }
     
     private void setTabelPermintaanBarang(){
        String[] kolom1 = {"No. Order", "Tanggal Masuk", "Barang", "Jumlah", "User"};
        tblpermintaanbrg = new DefaultTableModel(null, kolom1){
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
            
            //agar tabel tidak bisa diedit
            public boolean isCellEditable(int row, int col){
                int cola = tblpermintaanbrg.getColumnCount();
                return (col < cola) ? false : true;
               
            }
        };
        tbDataPermintaanBarang.setModel(tblpermintaanbrg);
        tbDataPermintaanBarang.getColumnModel().getColumn(0).setPreferredWidth(80);
        tbDataPermintaanBarang.getColumnModel().getColumn(1).setPreferredWidth(110);
        tbDataPermintaanBarang.getColumnModel().getColumn(2).setPreferredWidth(80);
        tbDataPermintaanBarang.getColumnModel().getColumn(3).setPreferredWidth(80);
        tbDataPermintaanBarang.getColumnModel().getColumn(4).setPreferredWidth(80);
    }
     
      private void clearTabelPermintaanBarang(){
        int row = tblpermintaanbrg.getRowCount(); //variabel row diberi nilai jumlah record/baris pada tabel(model) jurusan
        for(int i=0; i<row; i++){    
            tblpermintaanbrg.removeRow(0); //menghapus record/baris dari tabel jurusan maksud dari method removeRow
        } 
    }
      
      private void showDataPermintaanBarangMotor(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();          
            sqlselect = "select * from tborder_motor a, tbmotor b, tb_user c"
                    + " where a.TIPE_MODEL=b.TIPE_MODEL and a.ID_USER=c.ID_USER";      //query sql select 
             Statement stat = _Cnn.createStatement();    
             ResultSet res = stat.executeQuery(sqlselect); 
            clearTabelPermintaanBarang();
            while(res.next()){                  
               String vNoOrder = res.getString("KD_ORDER_MOTOR");
                vdtTglMasuk = tglview.format(res.getDate("TGL_ORDER_MOTOR"));   
                vbrg = res.getString("NAMA_MOTOR");
                vjml = res.getString("JML_ORDER_MOTOR");
                vuser = res.getString("NAMA_USER");
                
                Object[] data = {vNoOrder, vdtTglMasuk, vbrg, vjml, vuser};    //memebuat object array untuk menampung data record 
                tblpermintaanbrg.addRow(data);      //menyisipkan baris yang nilainya sesuai array data
            }
            
            lblRecord.setText("Record : " + tbDataPermintaanBarang.getRowCount()); //menampilkan jumlah baris
            
        }catch(SQLException ex){
             JOptionPane.showMessageDialog(this, "Error Method showDataPermintaanBarangMotor() : "+ex);
            
        }  
    
    }
      
      private void cariPermintaanBrgMotor(){
         try {
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelPermintaanBarang();
            sqlselect = "select * from tborder_motor a, tbmotor b, tb_user c "
                    + " where a.TIPE_MODEL=b.TIPE_MODEL and a.ID_USER=c.ID_USER and a.KD_ORDER_MOTOR like '%"+txtCariNoOrder.getText()+"%'"
                    + " order by  a.KD_ORDER_MOTOR asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vNoOrder = res.getString("KD_ORDER_MOTOR");
               vdtTglMasuk = tglview.format(res.getDate("TGL_ORDER_MOTOR"));
                vbrg = res.getString("NAMA_MOTOR");
                vjml = res.getString("JML_ORDER_MOTOR");
                 vuser = res.getString("NAMA_USER");
                Object[]data = {vNoOrder, vdtTglMasuk, vbrg, vjml, 
                     vuser};
                tblpermintaanbrg.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataPermintaanBarang.getRowCount()); 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error Method cariPermintaanBrgMotor() : "+ex);
        }
    }
      
      private void aksiSimpan(){
          if (txtNoOrder.getText().equals("")) { 
              JOptionPane.showMessageDialog(this, "Nomor Order harus diisi !");
          }else if (tglinput.format(dtTglMasuk.getDate()).equals("")) { 
              JOptionPane.showMessageDialog(this, "Tanggal Order harus diisi !");
          }else if (cmbBarang.getSelectedIndex()<=0) {
              JOptionPane.showMessageDialog(this, "Anda belum memilih Barang !");
          }else if(txtJumlah.getText().equals("")){
              JOptionPane.showMessageDialog(this, "Jumlah harus Diisi!",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
          }else if(cmbUser.getSelectedIndex()<=0){
              JOptionPane.showMessageDialog(this, "Anda belum memilih User!",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }else{
        vNoOrder = txtNoOrder.getText();
        vdtTglMasuk = tglinput.format(dtTglMasuk.getDate());
        vbrg = KeyBrg[cmbBarang.getSelectedIndex()];
        vjml = txtJumlah.getText();
        vuser = KeyUser[cmbUser.getSelectedIndex()];
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            if(btnSimpan.getText().equals("Simpan")){
                sqlinsert = "insert into tborder_motor set KD_ORDER_MOTOR='"+vNoOrder+"', TIPE_MODEL='"+vbrg+"', ID_USER='"+vuser+"', TGL_ORDER_MOTOR='"+vdtTglMasuk+"', " 
                        + " JML_ORDER_MOTOR='"+vjml+"' ";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil disimpan", 
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE);
                btnHapus.setText("Hapus");
                btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
            }else if(btnSimpan.getText().equals("simpan")){
                sqlinsert = "update tborder_motor set "
                        + " TIPE_MODEL='"+vbrg+"', ID_USER='"+vuser+"', TGL_ORDER_MOTOR='"+vdtTglMasuk+"', JML_ORDER_MOTOR='"+vjml+"' "
                        + " where KD_ORDER_MOTOR='"+vNoOrder+"' ";
                btnSimpan.setText("simpan");
                            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
                            Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil diubah",
                        "Informasi",
                         JOptionPane.INFORMATION_MESSAGE);
            }
            showDataPermintaanBarangMotor(); clearInput(); disableInput();
             btnTambah.setText("Tambah");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
        }catch(SQLException ex){
           JOptionPane.showMessageDialog(this, "Isikan Data dengan benar ! ");
        }
       }
    }
      
       private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah Anda Akan Menghapus Data Ini ? Kode "+vNoOrder,
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelete = "delete from tborder_motor where KD_ORDER_MOTOR='"+vNoOrder+"' ";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(this, "Informasi",
                        "Data Berhasil dihapus!", JOptionPane.INFORMATION_MESSAGE);
                showDataPermintaanBarangMotor(); clearInput(); disableInput();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method aksiHapus() : "+ex);
            }
        }   
    }
       
       String[] KeyBrg;
    private void listBarang(){         //membuat dinamis combobox
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbmotor";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbBarang.removeAllItems();
            cmbBarang.repaint();
            cmbBarang.addItem("--Pilih--");
            int i = 1;
            while(res.next()){
                cmbBarang.addItem(res.getString("NAMA_MOTOR")); 
                i++; 
            }
            res.first();
            KeyBrg = new String[i+1];       //mengatur primary key yang disimpan
            for (Integer x = 1 ; x<i ; x++){
                KeyBrg[x] = res.getString(1); // res.getstring(1)=> res.getstring("kd_jur");
                res.next();
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listBarang() : " +ex);
            
        }
    
    }
    
    String[] KeyUser;
    private void listUser(){         //membuat dinamis combobox
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tb_user";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbUser.removeAllItems();
            cmbUser.repaint();
            cmbUser.addItem("--Pilih--");
            int i = 1;
            while(res.next()){
                cmbUser.addItem(res.getString("NAMA_USER")); 
                i++; 
            }
            res.first();
            KeyUser = new String[i+1];       //mengatur primary key yang disimpan
            for (Integer x = 1 ; x<i ; x++){
                KeyUser[x] = res.getString(1); // res.getstring(1)=> res.getstring("kd_jur");
                res.next();
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listUser() : " +ex);
            
        }
    
    }
    
    public void createAutoID(){
        try{
            _Cnn = getCnn.getConnection();
            query = "select max(KD_ORDER_MOTOR) from tborder_motor";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            if(res.first()){
                KD_ORDER_MOTOR = res.getInt(1)+1;
            }else{
                KD_ORDER_MOTOR = 1;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNoOrder = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        dtTglMasuk = new com.toedter.calendar.JDateChooser();
        cmbBarang = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbUser = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDataPermintaanBarang = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCariNoOrder = new javax.swing.JTextField();
        lblRecord = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setTitle(":. Form Permintaan Barang Motor");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Permintaan Barang Motor"));

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel1.setText("No. Order");

        jLabel2.setText("Tanggal Masuk");

        jLabel3.setText("Jumlah");

        jLabel4.setText("Barang ");

        txtNoOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoOrderActionPerformed(evt);
            }
        });

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });
        txtJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJumlahKeyTyped(evt);
            }
        });

        cmbBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih -- " }));
        cmbBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBarangActionPerformed(evt);
            }
        });

        jLabel5.setText("User");

        cmbUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih -- " }));
        cmbUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUserActionPerformed(evt);
            }
        });

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dtTglMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(txtNoOrder)
                    .addComponent(txtJumlah)
                    .addComponent(cmbBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbUser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cmbUser, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNoOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dtTglMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBarang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        tbDataPermintaanBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "No. Order", "Tanggal Masuk", "Barang", "Jumlah", "User"
            }
        ));
        tbDataPermintaanBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataPermintaanBarangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbDataPermintaanBarang);

        jLabel6.setText("Klik 2x untuk mengubah dan menghapus data !");

        jLabel7.setText("Cari Nomor Order");

        txtCariNoOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariNoOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCariNoOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtCariNoOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblRecord.setText("Record :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 522, Short.MAX_VALUE)
                .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNoOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoOrderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoOrderActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void cmbBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbBarangActionPerformed

    private void txtCariNoOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariNoOrderActionPerformed
       cariPermintaanBrgMotor();
    }//GEN-LAST:event_txtCariNoOrderActionPerformed

    private void cmbUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUserActionPerformed
        
    }//GEN-LAST:event_cmbUserActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
       if(btnTambah.getText().equals("Tambah")){
            clearInput();
            enableInput();
            dtTglMasuk.requestFocus(true);
            btnTambah.setText("Batal");
            btnHapus.setText("Bersihkan");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
            btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/bersihkan.png")));
            createAutoID();
            txtNoOrder.setText(Integer.toString(KD_ORDER_MOTOR));
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
       }else if(txtNoOrder.getText().equals("")){
           JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus",
                   "Informasi", JOptionPane.INFORMATION_MESSAGE);
       }else{
           aksiHapus();
       }
       btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataPermintaanBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataPermintaanBarangMouseClicked
          if(evt.getClickCount() == 2){
            int row = tbDataPermintaanBarang.getSelectedRow();
            vNoOrder = tbDataPermintaanBarang.getValueAt(row, 0).toString();
            
            txtNoOrder.setText(vNoOrder); 
            try{
            _Cnn = null;
            _Cnn  = getCnn.getConnection();
            sqlselect = "select * from tborder_motor a, tb_user b, tbmotor c "
                    + " where a.ID_USER = b.ID_USER and a.TIPE_MODEL=c.TIPE_MODEL and KD_ORDER_MOTOR= '" +vNoOrder+ "' order by a.TIPE_MODEL asc ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            if(res.first()){
                cmbBarang.setSelectedItem(res.getString("NAMA_MOTOR"));
                cmbUser.setSelectedItem(res.getString("NAMA_USER"));
                txtJumlah.setText(res.getString("JML_ORDER_MOTOR"));
                dtTglMasuk.setDate(res.getDate("TGL_ORDER_MOTOR"));
                
                
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method tbDataPermintaanBarangMouseClicked(java.awt.event.MouseEvent evt) : "+ex,
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
            enableInput();
           btnTambah.setText("Batal");
           btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
           btnHapus.setEnabled(true);
           txtNoOrder.setEnabled(false);
           dtTglMasuk.requestFocus(true);
           btnSimpan.setText("simpan");
           btnHapus.setText("Hapus");
           btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
            
}
    }//GEN-LAST:event_tbDataPermintaanBarangMouseClicked

    private void txtJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahKeyTyped
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtJumlahKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbBarang;
    private javax.swing.JComboBox<String> cmbUser;
    private com.toedter.calendar.JDateChooser dtTglMasuk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataPermintaanBarang;
    private javax.swing.JTextField txtCariNoOrder;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNoOrder;
    // End of variables declaration//GEN-END:variables
}
