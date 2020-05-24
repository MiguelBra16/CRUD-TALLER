package taller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Postgres {
    Connection conexion;
    
    public void conexionDB(){
        
        try {
            Class.forName("org.postgresql.Driver") ;
              conexion = DriverManager.getConnection(
                      "jdbc:postgresql://127.0.0.1:5432/taller", 
                      "postgres", "Herrera16");
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "ERROR DE CONEXION!!!");
        }
    }
    
    public void cargarRCarro(DefaultTableModel modeloCarro, JTable TCarro){
        try {
            Statement stmt = this.conexion.createStatement();
            String query = "SELECT * FROM carros ORDER BY id_carro;" ;
            ResultSet rs = stmt.executeQuery(query);
            
            modeloCarro = (DefaultTableModel) TCarro.getModel();
            modeloCarro.setRowCount(0);
            Object Registro[] = new Object[5];
            
            while (rs.next())
            {
                for (int i=0;i<4;i++) Registro[i]=rs.getObject(i+1).toString().trim();
              modeloCarro.addRow(Registro);
            }
            
            TCarro.setModel(modeloCarro);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ERROR X: "+e.toString() + JOptionPane.ERROR_MESSAGE);  
        }
    }
    
    public void insertarCarro(String marca, String modelo, String color){
        try {
            String query = "INSERT INTO carros (Marca, Modelo, Color)" + " VALUES (?, ?, ?)"; 
            
            PreparedStatement pst = conexion.prepareStatement(query);
            
            pst.setString(1, modelo);
            pst.setString(2, marca);
            pst.setString(3, color);
            
            pst.execute();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ERROR X: "+e.toString() + JOptionPane.ERROR_MESSAGE);  
        }
    }
    
    public void actualizarCarro(JTable TablaCarro, String marca, String modelo, String color, int idCarro){
        
        int Indice= TablaCarro.getSelectedRow();
        if(Indice>-1)
        {
            try
            {
                String query = "UPDATE carros SET Marca=?, Modelo=?, Color=?"
                        + " WHERE id_carro="+idCarro;
                                
                PreparedStatement pst = conexion.prepareStatement(query);
               
                pst.setString(1, marca);
                pst.setString(2, modelo);
                pst.setString(3, color);
                                          
                pst.execute();
      
            }
            catch(Exception e) 
            {
                 JOptionPane.showMessageDialog(null,"ERROR X: "+e.toString() + JOptionPane.ERROR_MESSAGE); 
            }
        }
        else JOptionPane.showMessageDialog(null,"ELIJA UN REGISTRO..." + JOptionPane.WARNING_MESSAGE);     
    }
    
    public void eliminarCarro(int id_carro){
        try
            {
                Statement stmt = this.conexion.createStatement();
                String query = "DELETE FROM carros WHERE id_carro="+id_carro;
                ResultSet rs = stmt.executeQuery(query) ; 
            }
        catch(Exception e) 
        {
            JOptionPane.showMessageDialog(null,"EL REGISTRO SELECCIONADO A SIDO BORRADO DE MANERA EXITOSA");  
        }
    }

    void actualizarCarro(DefaultTableModel modeloTabla, String text, String text0, String text1, int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
