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
public class IfrUser extends javax.swing.JInternalFrame {

    /**
     * Creates new form IfrSupplier
     */
    public IfrUser() {
        initComponents();
        clearInput(); disableInput();
        setTabelUser(); showDataUser();   
    }
    //Koneksi
    Connection _Cnn;
    KoneksiDB getCnn=new KoneksiDB();
    public String query;
    public PreparedStatement stat;
    public ResultSet res;
    public int ID_USER;
    
    String vid_user, vnama, vlev, vpass, vkonf, valamat, vnotelp;
    String sqlselect, sqlinsert, sqldelete;
    DefaultTableModel tbluser;
    
    public void clearInput(){
        txtIdUser.setText("");
        txtNama.setText("");
        txtKataSandi.setText("");
        txtAlamat.setText("");
        txtNoTelp.setText("");
    }
    
    public void disableInput(){
        txtIdUser.setEnabled(false);
        txtNama.setEnabled(false);
        txtKataSandi.setEnabled(false);
        txtAlamat.setEnabled(false);
        txtNoTelp.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    public void enableInput(){
        txtIdUser.setEnabled(true);
        txtNama.setEnabled(true);
        txtKataSandi.setEnabled(true);
        txtAlamat.setEnabled(true);
        txtNoTelp.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
    }
    
    public void setTabelUser(){
        String[] kolom1 = {"ID. User", "Nama User", "Level User", "Alamat", "No. Telepon"};
        tbluser = new DefaultTableModel(null, kolom1){
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
            //agar tidak bisa diedit
            public boolean isCellEditable(int row, int col){
                int cola= tbluser.getColumnCount();
                return(col < cola)?false : true;
            }
        };
        
        tbDataUser.setModel(tbluser);
        tbDataUser.getColumnModel().getColumn(0).setPreferredWidth(10);
        tbDataUser.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbDataUser.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbDataUser.getColumnModel().getColumn(3).setPreferredWidth(100);

    }
    
    public void clearTabelUser(){//mengkosongkan isi tabel
        int row = tbluser.getRowCount();//variable row diberi jumlah baris pada tabel(model)jurusan
        for(int i=0; i<row;i++){
            tbluser.removeRow(0);
        }
    }
    
    private void showDataUser(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();          //MEMBUKA koneksi
            sqlselect = "select * from tb_user";      //query sql select 
            Statement stat = _Cnn.createStatement();    // membuat statement untuk menjalankan query
            ResultSet res = stat.executeQuery(sqlselect); //menjalankan query sqlselect ynag hasilnya ditampung pada variabel res
            clearTabelUser();
            while(res.next()){                  //perulangan while untuk menampilkan data hasil query select 
                vid_user = res.getString("ID_USER");      //memberikan nilai pada variabel vkd_prodi dimana nilainya adalah kolom kd_jur pada tabel jurusan
                vnama = res.getString("NAMA_USER");
                vlev = res.getString("LEV_USER");
                valamat = res.getString("ALAMAT_USER");
                vnotelp = res.getString("NOTELP_USER");
                
                Object[] data = {vid_user, vnama, vlev, valamat, vnotelp};    //memebuat object array untuk menampung data record 
                tbluser.addRow(data);      //menyisipkan baris yang nilainya sesuai array data
            }
            lblRecord.setText("Record : " + tbDataUser.getRowCount()); //menampilkan jumlah baris
            
        }catch(SQLException ex){
            //JOptionPane.showMessageDialog(this, "Error Method showDataProdi() : "+ex);
            JOptionPane.showMessageDialog(this,"Data tidak ada / Kosong","Informasi",
                        JOptionPane.INFORMATION_MESSAGE);
        }  
    
    }
    
    private void aksiSimpan(){
        
        if(txtIdUser.getText().equals("") || txtNama.getText().equals("") || txtKataSandi.getText().equals("")
                || txtAlamat.getText().equals("") || txtNoTelp.getText().equals("")){
            JOptionPane.showMessageDialog(this,  "Mohon lengkapi data!","informasi",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            vid_user = txtIdUser.getText();
            vnama = txtNama.getText();
            vlev = "Admin";
            vpass = txtKataSandi.getText();
            valamat = txtAlamat.getText();
            vnotelp = txtNoTelp.getText();
            
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                if(btnSimpan.getText().equals("Simpan")){
                    sqlinsert = "insert into tb_user set ID_USER='"+vid_user+"', "
                            + " PASS_USER=MD5('"+vpass+"'), " 
                            + " NAMA_USER='"+vnama+"', "
                            + " LEV_USER='"+vlev+"', "
                            + " ALAMAT_USER='"+valamat+"', " 
                            + " NOTELP_USER='"+vnotelp+"' " ;
                    Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil disimpan", "Informasi",
                         JOptionPane.INFORMATION_MESSAGE);
                btnHapus.setText("Hapus");
                btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
                }else if(btnSimpan.getText().equals("simpan")){
                    sqlinsert = "update tb_user set"
                            + " PASS_USER=MD5('"+vpass+"'), " 
                            + " NAMA_USER='"+vnama+"', "
                            + " LEV_USER='"+vlev+"', "
                            + " ALAMAT_USER='"+valamat+"', " 
                            + " NOTELP_USER='"+vnotelp+"' where ID_USER='"+vid_user+"' ";
                    Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this,"Data berhasil diubah","Informasi",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                showDataUser();clearInput(); disableInput();
                btnTambah.setText("Tambah");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
                
            }catch(SQLException ex){
                //JOptionPane.showMessageDialog(this, "Error method aksiSimpan() : "+ex);
                JOptionPane.showMessageDialog(this, "Data dengan ID ini sudah ada!");
                JOptionPane.showMessageDialog(this, "untuk manambah data yang sama anda bisa ganti dengan ID yang berbeda dari data sebelumnya!");
                txtIdUser.requestFocus(true);
            }
        }
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah anda ingin menghapus data ini? Kode "+vid_user,
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelete = "delete from tb_user where ID_USER='"+vid_user+"' ";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus", "Informasi",
                         JOptionPane.INFORMATION_MESSAGE);
                showDataUser(); clearInput(); disableInput();
                btnTambah.setText("Tambah");
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-add.png")));
            }catch(SQLException ex){
                //JOptionPane.showMessageDialog(this, "Error method aksiHapus() : "+ex);
                 JOptionPane.showMessageDialog(this, "Data ini sudah di pakai");
            }
        }
    }
        //cari nama belum bener
        private void cariNama(){
            if(txtCari.getText().equals("")){
                 showDataUser();
            }else{
                try{
             _Cnn = null;
                _Cnn = getCnn.getConnection();
                clearTabelUser();
                sqlselect = "select * from tb_user where NAMA_USER like '"+txtCari.getText()+"'"
                        +"order by NAMA_USER asc ";
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                while(res.next()){
                   vid_user = res.getString("ID_USER");   
                   vnama = res.getString("NAMA_USER");
                   vlev = res.getString("LEV_USER");
                   valamat = res.getString("ALAMAT_USER");
                   vnotelp = res.getString("NOTELP_USER");
                    Object[]data = {vid_user, vnama, valamat, vnotelp};
                    tbluser.addRow(data);
                    }
                    lblRecord.setText("Record : "+tbDataUser.getRowCount());
              } catch(SQLException ex){
                  JOptionPane.showMessageDialog(this, "Error method cariNama() : "+ex);

              }
            }
            
        }
        
        public void createAutoID(){
        try{
            _Cnn = getCnn.getConnection();
            query = "select max(ID_USER) from tb_user";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            if(res.first()){
                ID_USER = res.getInt(1)+1;
            }else{
                ID_USER = 1;
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

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txtKataSandi = new javax.swing.JPasswordField();
        txtNama = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtIdUser = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNoTelp = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataUser = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();

        setClosable(true);
        setResizable(true);
        setTitle("Form User");
        setAutoscrolls(true);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/EXY.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true)));
        jPanel1.setOpaque(false);

        jLabel3.setText("Form ini digunakan untuk mengolah data user");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo_xyz.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Form User");

        jLabel6.setText("Kata Sandi");

        txtKataSandi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKataSandiActionPerformed(evt);
            }
        });

        jLabel7.setText("Nama");

        jLabel5.setText("ID. User");

        jLabel10.setText("Alamat");

        jLabel9.setText("No. Telepon");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 444, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNama)
                                    .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKataSandi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                                .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtKataSandi, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(35, 35, 35)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setText(" Tabel Data User : Klik 2x untuk mengubah/menghapus data");

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record :");

        tbDataUser.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tbDataUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "ID. User", "Nama User", "Level User", "Alamat", "No. Telepon"
            }
        ));
        tbDataUser.setOpaque(false);
        tbDataUser.setRowHeight(24);
        tbDataUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataUser);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(lblRecord)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbDataUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataUserMouseClicked
        if(evt.getClickCount() == 2){
           int row = tbDataUser.getSelectedRow();
           vid_user = tbDataUser.getValueAt(row, 0).toString();
           vnama = tbDataUser.getValueAt(row, 1).toString();
           vpass = tbDataUser.getValueAt(row, 2).toString();
           valamat = tbDataUser.getValueAt(row, 3).toString();
           vnotelp = tbDataUser.getValueAt(row, 4).toString();
           
           txtIdUser.setText(vid_user); txtNama.setText(vnama);
           txtKataSandi.setText(vpass); txtAlamat.setText(valamat);
           txtNoTelp.setText(vnotelp); 
           enableInput();
           btnTambah.setText("Batal");
           btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/btn_delete.png")));
           btnHapus.setEnabled(true);
           txtIdUser.setEnabled(false);
           txtNama.requestFocus(true);
           btnSimpan.setText("simpan");
           btnHapus.setText("Hapus");
           btnHapus.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icons/trans-hapus.png")));
       }
    }//GEN-LAST:event_tbDataUserMouseClicked

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(btnTambah.getText().equals("Tambah")){
            clearInput();
            enableInput();
            txtIdUser.requestFocus(true);
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

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        aksiSimpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(btnHapus.getText().equals("Bersihkan")){
            clearInput();
        }else if(txtIdUser.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus","Informasi",
                 JOptionPane.INFORMATION_MESSAGE);
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

    private void txtKataSandiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKataSandiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKataSandiActionPerformed


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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataUser;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtIdUser;
    private javax.swing.JPasswordField txtKataSandi;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoTelp;
    // End of variables declaration//GEN-END:variables
}
