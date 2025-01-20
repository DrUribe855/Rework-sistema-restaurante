/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.*;

/**
 *
 * @author lalzate
 */
public class Database {
    Statement manipulateDB;
    Connection connection;
    
    
    /* Conexión a base de datos */
    
    public void DatabaseConnection(){
        String hostname = "localhost";
        String port = "3306";
        String databaseName = "restaurante";
        String databaseUser = "root";
        String databasePassword = "password";
        String url = "jdbc:mysql://" + hostname + ":" + port + "/" + databaseName;
        
        try{
            connection = DriverManager.getConnection(url, databaseUser, databasePassword);
            this.manipulateDB = connection.createStatement();
            System.out.println("Conexión exitosa con: " + databaseName );
        }catch(SQLException e){
            System.out.println("Error en conexión:" + e.getMessage());
            this.manipulateDB = null;
        } 
    }
    
    /* Validación de datos al iniciar sesión */
    
    public boolean userValidation(String user, String password){
        
        String data [] = new String [2];
        
        /* Se parametriza la consulta para evitar SQL injections */
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT usuario, password FROM usuarios WHERE usuario = ? AND password = ?"
            )){
            
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            
            ResultSet query = preparedStatement.executeQuery();
            
            /* Se valida si la query tiene registros para saber si los datos diligenciados coinciden */
            if(query.next()){
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            System.out.println("Error en consulta de usuario" + e.getMessage());
            return false;
        }
    }
}
