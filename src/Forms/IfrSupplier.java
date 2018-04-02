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
/**
 *
 * @author ASUS
 */
public class IfrSupplier extends javax.swing.JInternalFrame {

    /**
     * Creates new form IfrSupplier
     */
    public IfrSupplier() {
        initComponents();
        clearInput(); disableInput();
        setTabelSupplier(); showDataSupplier();   
    }
    //Koneksi
    Connection _Cnn;
    KoneksiDB getCnn=new KoneksiDB();
    public String query;
    public PreparedStatement stat;
    public ResultSet res;
    public int ID_SUPPLIER;
    
    String vid_supplier, vnama, valamat, vnotelp;
    String sqlselect, sqlinsert, sqldelete;
    DefaultTableModel tblsupplier;
    
    public void clearInput(){
        txtIdSupplier.setText("");
        txtNmSupplier.setText("");
        txtAlamat.setText("");
        txtNoTelp.setText("");
    }
    
    public void disableInput(){
        txtIdSupplier.setEnabled(false);
        txtNmSupplier.setEnabled(false);
        txtAlamat.setEnabled(false);
        txtNoTelp.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    public void enableInput(){
        txtIdSupplier.setEnabled(true);
        txtNmSupplier.setEnabled(true);
        txtAlamat.setEnabled(true);
        txtNoTelp.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
    }
    
    public void setTabelSupplier(){
        String[] kolom1 = {"ID. Supplier", "Nama Supplier", "Alamat", "No. Telepon"};
        tblsupplier = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
                
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };
            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }
            //agar tidak bisa diedit
            public boolean isCellEditable(int row, int col){
                int cola= tblsupplier.getColumnCount();
                return(col < cola)?false : true;
            }
        };
        
        tblDataSupplier.setModel(tblsupplier);
        tblDataSupplier.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblDataSupplier.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblDataSupplier.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblDataSupplier.getColumnModel().getColumn(3).setPreferredWidth(100);

    }
    
    public void clearTabelSupplier(){//mengkosongkan isi tabel
        int row = tblsupplier.getRowCount();//variable row diberi jumlah baris pada tabel(model)jurusan
        for(int i=0; i<row;i++){
            tblsupplier.removeRow(0);
        }
    }
    
    private void showDataSupplier(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();          //MEMBUKA koneksi
            sqlselect = "select * from tbsupplier";      //query sql select 
            Statement stat = _Cnn.createStatement();    // membuat statement untuk menjalankan query
            ResultSet res = stat.executeQuery(sqlselect); //menjalankan query sqlselect ynag hasilnya ditampung pada variabel res
            clearTabelSupplier();
            while(res.next()){                  //perulangan while untuk menampilkan data hasil query select 
                vid_supplier = res.getString("ID_SUPPLIER");      //memberikan nilai pada variabel vkd_prodi dimana nilainya adalah kolom kd_jur pada tabel jurusan
                vnama = res.getString("NAMA_SUPPLIER");
                valamat = res.getString("ALAMAT_SUPPLIER");
                vnotelp = res.getString("NO_HP_SUPPLIER");
                
                Object[] data = {vid_supplier, vnama, valamat, vnotelp};    //memebuat object array untuk menampung data record 
                tblsupplier.addRow(data);      //menyisipkan baris yang nilainya sesuai array data
            }
            lblRecord.setText("Record : " + tblDataSupplier.getRowCount()); //menampilkan jumlah baris
            
        }catch(SQLException ex){
            //JOptionPane.showMessageDialog(this, "Error Method showDataProdi() : "+ex);
            JOptionPane.showMessageDialog(this,"Data tidak ada / Kosong","Informasi",
                        JOptionPane.INFORMATION_MESSAGE);
        }  
    
    }
    
    private void aksiSimpan(){
        if(txtIdSupplier.getText().equals("") || txtNmSupplier.getText().equals("")
                || txtAlamat.getText().equals("") || txtNoTelp.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Mohon lengkapi data!","informasi",
                     JOptionPane.INFORMATION_MESSAGE);
        }else{
            vid_supplier = txtIdSupplier.getText();
            vnama = txtNmSupplier.getText();
            valamat = txtAlamat.getText();
            vnotelp = txtNoTelp.getText();
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                if(btnSimpan.getText().equals("Simpan")){
                    sqlinsert = "insert into tbsupplier set ID_SUPPLIER='"+vid_supplier+"', "
                            + " NAMA_SUPPLIER='"+vnama+"', " 
                            + " ALAMAT_SUPPLIER='"+valamat+"', " 
                            + " NO_HP_SUPPLIER='"+vnotelp+"' " ;
                    Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil disimpan", 
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE);
                btnHapus.setText("Hapus");
                btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
                }else if(btnSimpan.getText().equals("simpan")){
                    sqlinsert = "update tbsupplier set"
                            + " NAMA_SUPPLIER='"+vnama+"', "
                            + " ALAMAT_SUPPLIER='"+valamat+"', "
                            + " NO_HP_SUPPLIER='"+vnotelp+"' where ID_SUPPLIER='"+vid_supplier+"' ";
                            btnSimpan.setText("Simpan");
                            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
                            Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil diubah",
                        "Informasi",
                         JOptionPane.INFORMATION_MESSAGE);
                }
                
                showDataSupplier();clearInput(); disableInput();
                btnTambah.setText("Tambah");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
                
            }catch(SQLException ex){
                //JOptionPane.showMessageDialog(this, "Error method aksiSimpan() : "+ex);
                JOptionPane.showMessageDialog(this, "Data dengan ID ini sudah ada!");
                JOptionPane.showMessageDialog(this, "untuk manambah data yang sama anda bisa ganti dengan ID yang berbeda dari data sebelumnya!");
            }
        }
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this,
                "Apakah anda ingin menghapus data ini? Kode "+vid_supplier,
                "Konfirmasi",
                 JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelete = "delete from tbsupplier where ID_SUPPLIER='"+vid_supplier+"' ";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus","Informasi",
                         JOptionPane.INFORMATION_MESSAGE);
                showDataSupplier(); clearInput(); disableInput();
                btnTambah.setText("Tambah");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
                
            }catch(SQLException ex){
                //JOptionPane.showMessageDialog(this, "Error method aksiHapus() : "+ex);
                 JOptionPane.showMessageDialog(this, "Data ini sudah di pakai");
            }
        }
    }
    
    public void createAutoID(){
        try{
            _Cnn = getCnn.getConnection();
            query = "select max(ID_SUPPLIER) from tbsupplier";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            if(res.first()){
                ID_SUPPLIER = res.getInt(1)+1;
            }else{
                ID_SUPPLIER = 1;
            }
            stat.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error method createAutoID() : "
                + ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //cari nama belum bener
        private void cariNama(){
            if(txtCari.getText().equals("")){
                 showDataSupplier();
            }else{
                try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                clearTabelSupplier();
                sqlselect = "select * from tbsupplier where NAMA_SUPPLIER like '"+txtCari.getText()+"'"
                        +"order by NAMA_SUPPLIER asc ";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                while(res.next()){
                   vid_supplier = res.getString("ID_SUPPLIER");   
                   vnama = res.getString("NAMA_SUPPLIER");
                   valamat = res.getString("ALAMAT_SUPPLIER");
                   vnotelp = res.getString("NO_HP_SUPPLIER");
                    Object[]data = {vid_supplier, vnama, valamat, vnotelp};
                    tblsupplier.addRow(data);
                    }
                    lblRecord.setText("Record : "+tblDataSupplier.getRowCount());
              } catch(SQLException ex){
                  JOptionPane.showMessageDialog(this, "Error method cariNama() : "+ex);

              }
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
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtIdSupplier = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtAlamat = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNoTelp = new javax.swing.JTextField();
        txtNmSupplier = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataSupplier = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();

        setClosable(true);
        setResizable(true);
        setTitle("Form Supplier");
        setAutoscrolls(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/EXY.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true)));
        jPanel1.setOpaque(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true)));

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel5.setText("ID. Supplier");

        txtIdSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdSupplierActionPerformed(evt);
            }
        });

        jLabel6.setText("Alamat");

        jLabel7.setText("Nama Supplier");

        jLabel10.setText("No. Telepon");

        txtNoTelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoTelpActionPerformed(evt);
            }
        });
        txtNoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoTelpKeyTyped(evt);
            }
        });

        jLabel3.setText("Form ini digunakan untuk mengolah data supplier");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo_xyz.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Form Supplier");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 444, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtAlamat)
                                    .addComponent(txtIdSupplier)
                                    .addComponent(txtNmSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNmSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setText(" Tabel Data Supplier : Klik 2x untuk mengubah/menghapus data");

        jLabel4.setText("Cari Nama");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search-icon.png"))); // NOI18N
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        tblDataSupplier.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tblDataSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID. Supplier", "Nama Supplier", "Alamat", "No. Telepon"
            }
        ));
        tblDataSupplier.setOpaque(false);
        tblDataSupplier.setRowHeight(24);
        tblDataSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataSupplierMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDataSupplier);

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblRecord)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(btnTambah.getText().equals("Tambah")){
            clearInput();
            enableInput();
            txtNmSupplier.requestFocus(true);
            btnTambah.setText("Batal");
            btnHapus.setText("Bersihkan");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
            btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/bersihkan.png")));
            createAutoID();
            txtIdSupplier.setText(Integer.toString(ID_SUPPLIER));
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

    private void tblDataSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataSupplierMouseClicked
        if(evt.getClickCount() == 2){
           int row = tblDataSupplier.getSelectedRow();
           vid_supplier = tblDataSupplier.getValueAt(row, 0).toString();
           vnama = tblDataSupplier.getValueAt(row, 1).toString();
           valamat = tblDataSupplier.getValueAt(row, 2).toString();
           vnotelp = tblDataSupplier.getValueAt(row, 3).toString();
           
           txtIdSupplier.setText(vid_supplier); txtNmSupplier.setText(vnama);
           txtAlamat.setText(valamat); txtNoTelp.setText(vnotelp);
           enableInput();
           btnTambah.setText("Batal");
           btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
           btnHapus.setEnabled(true);
           txtIdSupplier.setEnabled(false);
           txtNmSupplier.requestFocus(true);
           btnSimpan.setText("simpan");
           btnHapus.setText("Hapus");
           btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
       }
    }//GEN-LAST:event_tblDataSupplierMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(btnHapus.getText().equals("Bersihkan")){
            clearInput();
       }else if(txtIdSupplier.getText().equals("")){
           JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus",
                   "Informasi", JOptionPane.INFORMATION_MESSAGE);
       }else{
           aksiHapus();
       }
       btnSimpan.setText("Simpan"); 
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
         cariNama();
    }//GEN-LAST:event_txtCariActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
         cariNama();
    }//GEN-LAST:event_btnCariActionPerformed

    private void txtIdSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdSupplierActionPerformed

    private void txtNoTelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoTelpActionPerformed
        aksiSimpan();
    }//GEN-LAST:event_txtNoTelpActionPerformed

    private void txtNoTelpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoTelpKeyTyped
        char enter=evt.getKeyChar();
        if(txtNoTelp.getText().length()==12){
            evt.consume();
        }else if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_txtNoTelpKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tblDataSupplier;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtIdSupplier;
    private javax.swing.JTextField txtNmSupplier;
    private javax.swing.JTextField txtNoTelp;
    // End of variables declaration//GEN-END:variables
}
