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
public class IfrReturPembelianMotor extends javax.swing.JInternalFrame {

    Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB(); 
    public String query;
    public PreparedStatement stat;
    public ResultSet res;
    public int NO_RETUR_PEMBELIAN_MOTOR;
    
    String vNoRetur, vdtTglRetur, vid_brg, vid_supplier, vJmlRetur, vStatusRetur, vid_user, vCariNoRetur;
     String sqlselect, sqlinsert, sqldelete;
    DefaultTableModel tblReturPembelianMotor; 
            
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Creates new form IfrReturPembelian
     */
    public IfrReturPembelianMotor() {
        initComponents();
        clearInput(); disableInput(); listBarangMotor(); listUser();
         setTabelReturBeli(); showDataReturPembelianMotor();
    }
    
    private void clearInput(){
        txtNoRetur.setText("");
        dtTglRetur.setDate(new Date()); 
        cmbBarang.setSelectedIndex(0);
        txtJmlRetur.setText(""); 
        txtStatusRetur.setText(""); 
        cmbUser.setSelectedIndex(0);
        txtCariNoRetur.setText("");
    }
    
    private void disableInput(){
        txtNoRetur.setEnabled(false);
        dtTglRetur.setEnabled(false);
        cmbBarang.setEnabled(false);
        txtJmlRetur.setEnabled(false);
        txtStatusRetur.setEnabled(false);
        cmbUser.setEnabled(false);
        txtCariNoRetur.setEnabled(true);
        btnSimpan.setEnabled(false); 
    }
     
     private void enableInput(){
        txtNoRetur.setEnabled(true);
        dtTglRetur.setEnabled(true);
        cmbBarang.setEnabled(true);
        txtJmlRetur.setEnabled(true);
        txtStatusRetur.setEnabled(true);
        cmbUser.setEnabled(true); 
        txtCariNoRetur.setEnabled(false);
        btnSimpan.setEnabled(true);
      }
     
     private void setTabelReturBeli(){
        String[] kolom1 = {"No Retur", "Tanggal Retur", "Barang", "Supplier", "Jumlah Retur", "Status Retur", "User"};
        tblReturPembelianMotor = new DefaultTableModel(null, kolom1){
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
            
            //agar tabel tidak bisa diedit
            public boolean isCellEditable(int row, int col){
                int cola = tblReturPembelianMotor.getColumnCount();
                return (col < cola) ? false : true;
               
            }
        };
        tbDataReturBeli.setModel(tblReturPembelianMotor);
        tbDataReturBeli.getColumnModel().getColumn(0).setPreferredWidth(80);
        tbDataReturBeli.getColumnModel().getColumn(1).setPreferredWidth(110);
        tbDataReturBeli.getColumnModel().getColumn(2).setPreferredWidth(80);
        tbDataReturBeli.getColumnModel().getColumn(3).setPreferredWidth(80);
        tbDataReturBeli.getColumnModel().getColumn(4).setPreferredWidth(110);
        tbDataReturBeli.getColumnModel().getColumn(5).setPreferredWidth(110);
        tbDataReturBeli.getColumnModel().getColumn(6).setPreferredWidth(80);
    }
      
     private void clearTabelReturBeli(){
        int row = tblReturPembelianMotor.getRowCount(); //variabel row diberi nilai jumlah record/baris pada tabel(model) jurusan
        for(int i=0; i<row; i++){    
            tblReturPembelianMotor.removeRow(0); //menghapus record/baris dari tabel jurusan maksud dari method removeRow
        } 
    }
     
     private void showDataReturPembelianMotor(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();          
            sqlselect = "select * from tbretur_pembelian_motor a, tbmotor b, tb_user c, tbsupplier d"
                    + " where a.TIPE_MODEL=b.TIPE_MODEL and a.ID_USER=c.ID_USER and b.ID_SUPPLIER=d.ID_SUPPLIER";      //query sql select 
             Statement stat = _Cnn.createStatement();    
             ResultSet res = stat.executeQuery(sqlselect); 
            clearTabelReturBeli();
            while(res.next()){                  
               String vNoRetur = res.getString("NO_RETUR_PEMBELIAN_MOTOR");
                vdtTglRetur = tglview.format(res.getDate("TGL_RETUR_PEMBELIAN_MOTOR"));   
                vid_brg = res.getString("NAMA_MOTOR");
                vid_supplier = res.getString("NAMA_SUPPLIER");
                vJmlRetur = res.getString("JML_RETUR_PEMBELIAN_MOTOR");
                vStatusRetur = res.getString("STATUS_RETUR_PEMBELIAN_MOTOR");
                vid_user = res.getString("NAMA_USER");
                
                Object[] data = {vNoRetur, vdtTglRetur, vid_brg, vid_supplier, vJmlRetur, vStatusRetur, vid_user};    //memebuat object array untuk menampung data record 
                tblReturPembelianMotor.addRow(data);      //menyisipkan baris yang nilainya sesuai array data
            }
            
            lblRecord.setText("Record : " + tbDataReturBeli.getRowCount()); //menampilkan jumlah baris
            
        }catch(SQLException ex){
             JOptionPane.showMessageDialog(this, "Error Method showDataReturPembelianMotor() : "+ex);
            
        }  
    
    }
     
     private void cariReturBeliMotor(){
         try {
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelReturBeli();
            sqlselect = "select * from tbretur_pembelian_motor a, tbmotor b, tb_user c, tbsupplier d "
                    + " where a.TIPE_MODEL=b.TIPE_MODEL and a.ID_USER=c.ID_USER and b.ID_SUPPLIER=d.ID_SUPPLIER and a.NO_RETUR_PEMBELIAN_MOTOR like '%"+txtCariNoRetur.getText()+"%'"
                    + " order by  a.TIPE_MODEL asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vNoRetur = res.getString("NO_RETUR_PEMBELIAN_MOTOR");
               vdtTglRetur = tglview.format(res.getDate("TGL_RETUR_PEMBELIAN_MOTOR"));
                vid_brg = res.getString("NAMA_MOTOR");
                vJmlRetur = res.getString("JML_RETUR_PEMBELIAN_MOTOR");
                vStatusRetur = res.getString("STATUS_RETUR_PEMBELIAN_MOTOR");
                 vid_user = res.getString("NAMA_USER");
                 vid_supplier = res.getString("NAMA_SUPPLIER");
                Object[]data = {vNoRetur, vdtTglRetur, vid_brg, vid_supplier,vJmlRetur, 
                    vStatusRetur, vid_user};
                tblReturPembelianMotor.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataReturBeli.getRowCount()); 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error Method cariReturBeliMotor() : "+ex);
        }
    }
     
     private void aksiSimpan(){
          if (txtNoRetur.getText().equals("")) { 
              JOptionPane.showMessageDialog(this, "Nomor Retur harus diisi !");
          }else if (tglinput.format(dtTglRetur.getDate()).equals("")) { 
              JOptionPane.showMessageDialog(this, "Tanggal Retur harus diisi !");
          }else if(cmbBarang.getSelectedIndex()<=0){
              JOptionPane.showMessageDialog(this, "Anda belum memilih Barang!",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
          }else if(txtJmlRetur.getText().equals("")){
              JOptionPane.showMessageDialog(this, "Jumlah Retur harus Diisi!",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
          }else if(txtStatusRetur.getText().equals("")){
              JOptionPane.showMessageDialog(this, "Status Retur harus Diisi!",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
          }else if(cmbUser.getSelectedIndex()<=0){
              JOptionPane.showMessageDialog(this, "Anda belum memilih User!",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }else{
        vNoRetur = txtNoRetur.getText();
        vdtTglRetur = tglinput.format(dtTglRetur.getDate());
        vid_brg = KeyBrg[cmbBarang.getSelectedIndex()];
        vJmlRetur = txtJmlRetur.getText();
        vStatusRetur = txtStatusRetur.getText();
        vid_user = KeyUser[cmbUser.getSelectedIndex()];
        try{ 
            int stok = 0; 
            String sqls; 
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            if(btnSimpan.getText().equals("Simpan")){
                sqls = "select STOK_MOTOR from tbmotor where TIPE_MODEL='"+vid_brg+"' ";
                Statement st = _Cnn.createStatement();
                ResultSet rs = st.executeQuery(sqls);
                while(rs.next()){
                    stok = Integer.parseInt(rs.getString(1));
                }
                if (Integer.parseInt(vJmlRetur)>stok){
                    JOptionPane.showMessageDialog(this, "Data Inputan Melebihi Stok");
                }else if(Integer.parseInt(vJmlRetur) == stok){
                    JOptionPane.showMessageDialog(this, "Data Harus Kurang dari Stok");
                }else {
                sqlinsert = "insert into tbretur_pembelian_motor set NO_RETUR_PEMBELIAN_MOTOR='"+vNoRetur+"', ID_USER='"+vid_user+"', TIPE_MODEL='"+vid_brg+"', TGL_RETUR_PEMBELIAN_MOTOR='"+vdtTglRetur+"', " 
                        + " JML_RETUR_PEMBELIAN_MOTOR='"+vJmlRetur+"', STATUS_RETUR_PEMBELIAN_MOTOR='"+vStatusRetur+"' ";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil disimpan", 
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                }else if(btnSimpan.getText().equals("simpan")){
                sqls = "select STOK_MOTOR from tbmotor where TIPE_MODEL='"+vid_brg+"' ";
                Statement st = _Cnn.createStatement();
                ResultSet rs = st.executeQuery(sqls);
                while(rs.next()){
                    stok = Integer.parseInt(rs.getString(1));
                }
                if (Integer.parseInt(vJmlRetur)>stok){
                    JOptionPane.showMessageDialog(this, "Data Inputan Melebihi Stok");
                }else if(Integer.parseInt(vJmlRetur) == stok){
                    JOptionPane.showMessageDialog(this, "Data Harus Kurang dari Stok");
                }else {
                sqlinsert = "update tbretur_pembelian_motor set "
                        + " ID_USER='"+vid_user+"', TIPE_MODEL='"+vid_brg+"', TGL_RETUR_PEMBELIAN_MOTOR='"+vdtTglRetur+"', JML_RETUR_PEMBELIAN_MOTOR='"+vJmlRetur+"', STATUS_RETUR_PEMBELIAN_MOTOR='"+vStatusRetur+"' "
                        + " where NO_RETUR_PEMBELIAN_MOTOR='"+vNoRetur+"' ";
                 btnSimpan.setText("simpan");
                            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
                            Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil diubah",
                        "Informasi",
                         JOptionPane.INFORMATION_MESSAGE);

                }
                            }
            
            showDataReturPembelianMotor(); clearInput(); disableInput();
            btnTambah.setText("Tambah");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Isikan Data dengan benar ! ");
        }
       }
    }
     
     String[] KeyBrg;
    private void listBarangMotor(){         //membuat dinamis combobox
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
            JOptionPane.showMessageDialog(this, "Error Method listBarangMotor() : " +ex);
            
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
            query = "select max(NO_RETUR_PEMBELIAN_MOTOR) from tbretur_pembelian_motor";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            if(res.first()){
                NO_RETUR_PEMBELIAN_MOTOR = res.getInt(1)+1;
            }else{
                NO_RETUR_PEMBELIAN_MOTOR = 1;
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNoRetur = new javax.swing.JTextField();
        txtJmlRetur = new javax.swing.JTextField();
        txtStatusRetur = new javax.swing.JTextField();
        dtTglRetur = new com.toedter.calendar.JDateChooser();
        cmbBarang = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cmbUser = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDataReturBeli = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCariNoRetur = new javax.swing.JTextField();
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
        setTitle(":. Form Retur Pembelian Motor");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Retur Pembelian Motor"));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true)));

        jLabel1.setText("No. Retur");

        jLabel2.setText("Tanggal Retur");

        jLabel4.setText("Barang");

        jLabel5.setText("Status Retur");

        jLabel6.setText("Jumlah Retur");

        txtJmlRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJmlReturKeyTyped(evt);
            }
        });

        cmbBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));

        jLabel7.setText("User");

        cmbUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih -- " }));
        cmbUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUserActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true)));

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnTambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSimpan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnSimpan))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dtTglRetur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNoRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbUser, 0, 142, Short.MAX_VALUE)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtJmlRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtStatusRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNoRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dtTglRetur, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJmlRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStatusRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        tbDataReturBeli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No. Retur", "Tanggal Retur", "Barang", "Supplier", "Jumlah Retur", "Status Retur", "User"
            }
        ));
        tbDataReturBeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataReturBeliMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbDataReturBeli);

        jLabel8.setText("Klik 2x untuk mengubah data !");

        jLabel11.setText("Cari Nomor Retur");

        txtCariNoRetur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariNoReturActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCariNoRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtCariNoRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
       aksiSimpan(); // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void cmbUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUserActionPerformed
   
    }//GEN-LAST:event_cmbUserActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(btnTambah.getText().equals("Tambah")){
            clearInput();
            enableInput();
            dtTglRetur.requestFocus(true);
            btnTambah.setText("Batal");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
            createAutoID();
            txtNoRetur.setText(Integer.toString(NO_RETUR_PEMBELIAN_MOTOR));
        }else if(btnTambah.getText().equals("Batal")){
            clearInput();
            disableInput();
            btnTambah.setText("Tambah");
            btnSimpan.setText("Simpan");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void tbDataReturBeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataReturBeliMouseClicked
        if(evt.getClickCount() == 2){
            int row = tbDataReturBeli.getSelectedRow();
            vNoRetur = tbDataReturBeli.getValueAt(row, 0).toString();
            
            txtNoRetur.setText(vNoRetur); 
            try{
            _Cnn = null;
            _Cnn  = getCnn.getConnection();
            sqlselect = "select * from tbretur_pembelian_motor a, tb_user b, tbmotor c, tbsupplier d "
                    + " where a.ID_USER = b.ID_USER and a.TIPE_MODEL=c.TIPE_MODEL and c.ID_SUPPLIER=d.ID_SUPPLIER and NO_RETUR_PEMBELIAN_MOTOR= '" +vNoRetur+ "' order by a.TIPE_MODEL asc ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            if(res.first()){
                cmbBarang.setSelectedItem(res.getString("NAMA_MOTOR"));
                cmbUser.setSelectedItem(res.getString("NAMA_USER"));
                txtJmlRetur.setText(res.getString("JML_RETUR_PEMBELIAN_MOTOR"));
                dtTglRetur.setDate(res.getDate("TGL_RETUR_PEMBELIAN_MOTOR"));
                txtStatusRetur.setText(res.getString("STATUS_RETUR_PEMBELIAN_MOTOR"));
                
                
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method tbDataReturBeliMouseClicked(java.awt.event.MouseEvent evt) : "+ex,
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
            enableInput();
           btnTambah.setText("Batal");
           btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
          txtNoRetur.setEnabled(false);
           dtTglRetur.requestFocus(true);
           btnSimpan.setText("simpan");
}
    }//GEN-LAST:event_tbDataReturBeliMouseClicked

    private void txtCariNoReturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariNoReturActionPerformed
       cariReturBeliMotor();
    }//GEN-LAST:event_txtCariNoReturActionPerformed

    private void txtJmlReturKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJmlReturKeyTyped
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtJmlReturKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbBarang;
    private javax.swing.JComboBox<String> cmbUser;
    private com.toedter.calendar.JDateChooser dtTglRetur;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataReturBeli;
    private javax.swing.JTextField txtCariNoRetur;
    private javax.swing.JTextField txtJmlRetur;
    private javax.swing.JTextField txtNoRetur;
    private javax.swing.JTextField txtStatusRetur;
    // End of variables declaration//GEN-END:variables
}
