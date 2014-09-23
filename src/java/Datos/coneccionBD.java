/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Edii
 */
public class coneccionBD {
    
    private String strCon;
    private Connection conexion;
    
    public coneccionBD() throws Exception
    {
        strCon = "jdbc:mysql://localhost:3306/ringadamboriana";
    }
    
    public void conectar() throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver");
        try
        {
            setConexion(DriverManager.getConnection(getStrCon(),"root",""));
        } catch (SQLException e)
        {
            throw new Exception("Error en la conexion "+e.getMessage());
        }
    }
    public void desconectar()
    {
        try
        {
            getConexion().close();
        } catch (SQLException e)
        {
            setConexion(null);
        }
    }
    public PreparedStatement Sentencia(String txt) throws Exception
    {
        try
        {
            PreparedStatement ps = getConexion().prepareStatement(txt);
            return ps;
        } catch (SQLException e)
        {
            throw new SQLException("Error al ejecutar sentencia "+e.getMessage());
        }
    }
    public ResultSet ConsultaConResultado(PreparedStatement sentencia) throws Exception
    {
        try
        {
            ResultSet filas = sentencia.executeQuery();
            return filas;
        } catch (SQLException e)
        {
            throw new SQLException("Error al ejecutar consulta con resultado "+e.getMessage());
        }
    }
    public void ConsultaSinResultado(PreparedStatement sentencia) throws Exception
    {
        try
        {
            sentencia.execute();
        } catch (SQLException e)
        {
            throw new SQLException("Error al ejecutar consulta sin resultado "+e.getMessage());
        }
    }
    
    public String getStrCon() {
        return strCon;
    }
    public void setStrCon(String strCon) {
        this.strCon = strCon;
    }
    public Connection getConexion() {
        return conexion;
    }
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}
