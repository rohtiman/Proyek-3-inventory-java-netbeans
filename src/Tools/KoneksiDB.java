/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author hp
 */
public class KoneksiDB {
    
    
    public Connection getConnection() throws SQLException{ //method koneksi
      Connection cnn; //deklarasi class koneksi
      try{
          String server = "jdbc:mysql://localhost/db_inventory"; //nama server
          String drever = "com.mysql.jdbc.Driver"; //nama driver koneksi DB MySQL
          Class.forName(drever); //Eksekusi driver koneksi DB
          cnn = DriverManager.getConnection(server,"root",""); //inisialisasi variabel cnn
          return cnn;
          }catch(SQLException | ClassNotFoundException se ){ //fungsi catch :menasmpilkan sintak error didalam try 
              JOptionPane.showMessageDialog(null, "Error koneksi database : "+se);
              return null;
    
    }
    
}

}
