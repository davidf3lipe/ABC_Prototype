/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ABC_Prototype.dao;

import com.mycompany.ABC_Prototype.models.Cliente;
import com.mysql.jdbc.StringUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Felipe
 */
public class ClienteDao {
    
    public Connection conectar (){
        String baseDeDatos = "db_prueba";
        String usuario = "root";
        String password = "";
        String host = "localhost";
        String puerto = "3306";
        String driver = "com.mysql.jdbc.Driver";
        String conexionUrl = "jdbc:mysql://"+host+":"+puerto+"/"+baseDeDatos+"?useSSL=false";
        Connection conexion = null;
        
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(conexionUrl,usuario,password);
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
    
    public void agregar(Cliente cliente){

        try {
            Connection conexion = conectar();
   
            String SQL = "INSERT INTO `resoluciones` (`id`, `nombre`, `apellido`, `telefono`, `email`) VALUES (NULL, '" 
                    + cliente.getNombre() +"', '"
                    + cliente.getApellido()+"', '"+cliente.getTelefono()+"', '"+cliente.getEmail()+"')";
            Statement statement = conexion.createStatement();
            statement.execute(SQL);
          
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
        public List<Cliente> listar(){

        List<Cliente> listado = new ArrayList<>();
        
        
        try {
            Connection conexion = conectar();
            String SQL = "SELECT * FROM `resoluciones`";
            
            
            Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery(SQL);
            
            while(resultado.next()){
                Cliente cliente = new Cliente();
                cliente.setId(resultado.getString("id"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellido(resultado.getString("apellido"));
                cliente.setTelefono(resultado.getString("telefono"));
                cliente.setEmail(resultado.getString("email"));
                listado.add(cliente);
                
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listado;
        }
        
    public void eliminar(String id){
        try {
            Connection conexion = conectar();
            String SQL = "DELETE FROM resoluciones WHERE `resoluciones`.`id` = " + id;

            Statement statement = conexion.createStatement();
            statement.execute(SQL);
          
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actualizar(Cliente client){

        try {
            Connection conexion = conectar();
            String dao = "";
            String SQL = "UPDATE `resoluciones` SET `nombre` = '"+client.getNombre()
                    +"', `apellido` = '"+client.getApellido()
                    +"', `telefono` = '"+client.getTelefono()
                    +"', `email` = '"+client.getEmail()
                    +"' WHERE `resoluciones`.`id` = "+client.getId()+";";
            
            Statement statement = conexion.createStatement();
            statement.execute(SQL);
          
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }

    public void guardar(Cliente client) {
        if(StringUtils.isEmptyOrWhitespaceOnly(client.getId())){
            agregar(client);
        }else{
            actualizar(client);
        }
    }
    
}
