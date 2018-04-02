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

public class IfrBarangMotorKeluar extends javax.swing.JInternalFrame {

    
    Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB();
    
    String sqlinsert, sqlselect, sqldelete;
    String vno_order, vid_user, vtipe_model, vtgl_keluar, 
            vjml_keluar;
    
    DefaultTableModel tblbarangmotorkeluar;
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    
    /**
     * Creates new form IfrBarangMasuk
     */
    public IfrBarangMotorKeluar() {
        initComponents();
        
        clearInput(); disableInput(); listBarang(); listUser();
        setTabelBarangMotorKeluar(); showDataBarangMotorKeluar();
    }
    
    private void clearInput(){
        txtNoOrder.setText("");
        dtTglKeluar.setDate(new Date());
        cmbBarang.setSelectedIndex(0);
        txtJumlah.setText("");
        cmbUser.setSelectedIndex(0);
    }
    
     private void disableInput(){
        txtNoOrder.setEnabled(false);
        dtTglKeluar.setEnabled(false);
        cmbBarang.setEnabled(false);
        txtJumlah.setEnabled(false);
        cmbUser.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
     
    private void enableInput(){
        txtNoOrder.setEnabled(true);
        dtTglKeluar.setEnabled(true);
        cmbBarang.setEnabled(true);
        txtJumlah.setEnabled(true);
        cmbUser.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
    }
    
    private void setTabelBarangMotorKeluar(){
        String[] kolom1 = {"No. Order", "Tanggal Keluar", "Barang", "Jumlah", "User"};
        tblbarangmotorkeluar = new DefaultTableModel(null, kolom1){
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
                int cola = tblbarangmotorkeluar.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataBrgKeluar.setModel(tblbarangmotorkeluar);
        tbDataBrgKeluar.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataBrgKeluar.getColumnModel().getColumn(1).setPreferredWidth(250);
        tbDataBrgKeluar.getColumnModel().getColumn(2).setPreferredWidth(25);
        tbDataBrgKeluar.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataBrgKeluar.getColumnModel().getColumn(4).setPreferredWidth(75);
    }
    
    private void clearTabelBarangMotorKeluar(){
        int row = tblbarangmotorkeluar.getRowCount(); //variabel row diberi nilai jumlah baris pada tabel(model) jurusan
        for(int i=0; i<row; i++){
            tblbarangmotorkeluar.removeRow(0); //menghapus record/baris
        }
    }
    
    private void showDataBarangMotorKeluar(){
        try {
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbmotor_keluar a, tbmotor b, tb_user c where a.TIPE_MODEL=b.TIPE_MODEL and"
                    + " a.ID_USER=c.ID_USER ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            clearTabelBarangMotorKeluar();
            while(res.next()){
                vno_order = res.getString("NO_BARANG_KELUAR");
                vtgl_keluar = tglview.format(res.getDate("TGL_KELUAR"));
                String vtipe_model = res.getString("NAMA_MOTOR");
                vjml_keluar = res.getString("JML_BARANG");
                String vid_user = res.getString("NAMA_USER");
                
                Object[] data = {vno_order, vtgl_keluar, vtipe_model, vjml_keluar, vid_user};
                tblbarangmotorkeluar.addRow(data);
            }
            lblRecord.setText("Record : "+ tbDataBrgKeluar.getRowCount());
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error method showDataBarangMotorKeluar :"+ ex);
        }
    }
    
    private void cariNomorBarangKeluar(){
        try{
             _Cnn = null;
             _Cnn = getCnn.getConnection();
             clearTabelBarangMotorKeluar();
             sqlselect = "select * from tbmotor like '%"+txtCari.getText()+"%'"
                     +"order by NAMA_MOTOR asc ";
             Statement stat = _Cnn.createStatement();
             ResultSet res = stat.executeQuery(sqlselect);
             while(res.next()){
                vno_order = res.getString("NO_BARANG_KELUAR");
                vtgl_keluar = tglview.format(res.getDate("TGL_KELUAR"));
                String vtipe_model = res.getString("NAMA_MOTOR");
                vjml_keluar = res.getString("JML_KELUAR");
                String vid_user = res.getString("NAMA_USER");
                
                Object[] data = {vno_order, vtgl_keluar, vtipe_model, vjml_keluar, vid_user};
                tblbarangmotorkeluar.addRow(data);
                 }
                 lblRecord.setText("Record : "+tbDataBrgKeluar.getRowCount());
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
        if(txtNoOrder.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nomor Order harus diisi !");
        }else if(tglinput.format(dtTglKeluar.getDate()).equals("")){
            JOptionPane.showMessageDialog(this, "Tanggal Masuk harus diisi !");
        }else if(cmbBarang.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Anda belum memilh barang !");
        }else if(txtJumlah.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Jumlah Barang harus diisi !");
        }else if(cmbUser.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Anda belum memilh user !");
        }else{
            vno_order= txtNoOrder.getText();
            vtipe_model = KeyBar[cmbBarang.getSelectedIndex()];
            vtgl_keluar = tglinput.format(dtTglKeluar.getDate());
            vjml_keluar = txtJumlah.getText();
            vid_user = KeyUser[cmbUser.getSelectedIndex()];
            
            try{
                int stok = 0;
                String sqls;
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                if(btnSimpan.getText().equals("Simpan")){
                    sqls = "select STOK_MOTOR from tbmotor where TIPE_MODEL='"+vtipe_model+"' ";
                    Statement st = _Cnn.createStatement();
                    ResultSet rs = st.executeQuery(sqls);
                    while(rs.next()){
                        stok = Integer.parseInt(rs.getString(1));
                    }
                    if(Integer.parseInt(vjml_keluar)>stok){
                        JOptionPane.showMessageDialog(this, "Data Inputan Melebihi Stok");
                    }else if(Integer.parseInt(vjml_keluar) == stok){
                        JOptionPane.showMessageDialog(this, "Data harus kurang dari Stok");
                    }else{
                    sqlinsert = "insert into tbmotor_keluar set NO_BARANG_KELUAR='"+vno_order+"', "
                            + " TGL_KELUAR='"+vtgl_keluar+"', TIPE_MODEL='"+vtipe_model+"', JML_BARANG='"+vjml_keluar+"', "
                            + " ID_USER='"+vid_user+"' ";
                    Statement stat = _Cnn.createStatement();
                    stat.executeUpdate(sqlinsert);
                    JOptionPane.showMessageDialog(this,"Data berhasil disimpan", "Informasi",
                            JOptionPane.INFORMATION_MESSAGE);
                    btnHapus.setText("simpan");
                    btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                            getResource("/Icons/trans-hapus.png")));
                    }
                }else if(btnSimpan.getText().equals("Ubah")){
                    sqlinsert = "update tbmotor_keluar set"
                            + " TGL_KELUAR='"+vtgl_keluar+"', TIPE_MODEL='"+vtipe_model+"', JML_BARANG='"+vjml_keluar+"', "
                            + " ID_USER='"+vid_user+"' "
                            + " where NO_BARANG_KELUAR='"+vno_order+"' ";
                    Statement stat =_Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this, "Data berhasil diubah", 
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }
                
                showDataBarangMotorKeluar(); clearInput(); disableInput();
                btnTambah.setText("Tambah");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
            }catch(SQLException ex){
                JOptionPane.showConfirmDialog(this, "Error method aksiSimpan() : " + ex);
           
        }
        }
        
            
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah Anda akan menghapus data ini? kode"+vno_order, 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelete = "delete from tbmotor_keluar where NO_BARANG_KELUAR = '"+vno_order+"'";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus", 
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                showDataBarangMotorKeluar(); clearInput(); disableInput();
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
        txtNoOrder = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        cmbUser = new javax.swing.JComboBox<>();
        cmbBarang = new javax.swing.JComboBox<>();
        dtTglKeluar = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        btnHapus = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataBrgKeluar = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setClosable(true);
        setTitle("Form Barang Motor Keluar");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/EXY.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("No. Order");

        jLabel2.setText("Tanggal Keluar");

        jLabel3.setText("Barang");

        jLabel4.setText("Jumlah");

        jLabel5.setText("User");

        cmbUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));

        cmbBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save-black.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.setPreferredSize(new java.awt.Dimension(70, 30));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtTglKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNoOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(87, 87, 87))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNoOrder))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtTglKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jLabel6.setText("Klik 2x untuk mengubah dan mengahpus data !");

        jLabel7.setText("Cari nomor barang keluar");

        tbDataBrgKeluar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "No. Order", "Tanggal Keluar", "Barang", "Jumlah", "User"
            }
        ));
        tbDataBrgKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataBrgKeluarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataBrgKeluar);

        lblRecord.setText("Record :");

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-icon.png"))); // NOI18N
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Form Barang Motor Keluar");

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6))
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

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(btnHapus.getText().equals("Bersihkan")){
            clearInput();
        }else if(txtNoOrder.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus","Informasi",
                JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiHapus();
        }
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(btnTambah.getText().equals("Tambah")){
            clearInput();
            enableInput();
            txtNoOrder.requestFocus(true);
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

    private void tbDataBrgKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataBrgKeluarMouseClicked
        if(evt.getClickCount() == 2){
            int row = tbDataBrgKeluar.getSelectedRow();
            vno_order = tbDataBrgKeluar.getValueAt(row, 0).toString();
            vtgl_keluar = tbDataBrgKeluar.getValueAt(row, 1).toString();
            String vtipe_model = tbDataBrgKeluar.getValueAt(row, 2).toString();
            vjml_keluar = tbDataBrgKeluar.getValueAt(row, 3).toString();
            String vid_user = tbDataBrgKeluar.getValueAt(row, 4).toString();

            txtNoOrder.setText(vno_order);
            dtTglKeluar.setDateFormatString(vtgl_keluar);
            cmbBarang.setSelectedItem(vtipe_model);
            txtJumlah.setText(vjml_keluar);
            cmbUser.setSelectedItem(vid_user);
            enableInput();
            txtNoOrder.setEnabled(false);
            btnTambah.setText("Batal");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/Icons/btn_delete.png")));
        btnHapus.setEnabled(true);
        txtNoOrder.setEnabled(false);
        btnSimpan.setText("simpan");
        btnHapus.setText("Hapus");
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
            getResource("/Icons/trans-hapus.png")));
        }
    }//GEN-LAST:event_tbDataBrgKeluarMouseClicked

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        cariNomorBarangKeluar();
    }//GEN-LAST:event_btnCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbBarang;
    private javax.swing.JComboBox<String> cmbUser;
    private com.toedter.calendar.JDateChooser dtTglKeluar;
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
    private javax.swing.JTable tbDataBrgKeluar;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNoOrder;
    // End of variables declaration//GEN-END:variables
}
