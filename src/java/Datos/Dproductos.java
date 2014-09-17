/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

import Modelo.Productos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;

/**
 *
 * @author Edii
 */
public class Dproductos extends coneccionBD{  
    public Dproductos() throws Exception {
        super();
    }
    
    public Hashtable TraerProductos() throws Exception
    {
        try
        {
            super.conectar();
        Hashtable lista = new Hashtable();
            String sql = "SELECT * FROM productos;";
        PreparedStatement ps = Sentencia(sql);
        ResultSet rows = ConsultaConResultado(ps);
        while (rows.next()){
            Productos aux = new Productos(rows.getInt("idProductos"),rows.getString("nombre"),rows.getFloat("precio"),rows.getInt("stock"));
            lista.put(aux.getId(), aux);
        }        
        return lista;}
        finally
        { super.desconectar();}
    } 
    
    public void ModificarProducto(Productos pro) throws Exception
    {
        //UPDATE `productos` SET `nombre`='Clavosa', `precio`='12a', `stock`='a' WHERE `idProductos`='1';
        String sql = "UPDATE `productos` SET `nombre`='"
                +pro.getNombre()
                + "', `precio`='"
                + pro.getPrecio()
                + "', `stock`='"
                + pro.getStock()
                + "' WHERE `idProductos`="
                + pro.getId()
                + ";";
        PreparedStatement ps = Sentencia(sql);
        ConsultaSinResultado(ps);
    }
    
    public void CrearProducto (String name,float precio,int stoc) throws Exception
    {
        //INSERT INTO `ringadamboriana`.`productos` (`idProductos`, `nombre`, `precio`, `stock`) VALUES ('11', 'asdf', '1', '2');
        String sql = "INSERT INTO `productos` (`nombre`, `precio`, `stock`) VALUES ('"
                +name + "', '"+ precio + "', '" + stoc + "');";
        PreparedStatement ps = Sentencia(sql);
        ConsultaSinResultado(ps);
    }
    
    public void EliminarProducto (Productos pro) throws Exception
    {
        //DELETE FROM `productos` WHERE `idProductos`='10';
        String sql = "DELETE FROM `productos` WHERE `idProductos`='"
                +pro.getId() + "';";
        PreparedStatement ps = Sentencia(sql);
        ConsultaSinResultado(ps);
    }
    
    public void EliminarProducto (int id) throws Exception
    {
        String sql = "DELETE FROM `productos` WHERE `idProductos`='"
                +id + "';";
        PreparedStatement ps = Sentencia(sql);
        ConsultaSinResultado(ps);
    }
    
    public Hashtable TraerProductosPorTexto(String texto) throws Exception
    {
        Hashtable lista = new Hashtable();
        String sql = "SELECT * FROM productos where nombre like '%"
                + texto+ "%';";
        PreparedStatement ps = Sentencia(sql);
        ResultSet rows = super.ConsultaConResultado(ps);
        while (rows.next()){
            Productos aux = new Productos(rows.getInt("idProductos"),rows.getString("nombre"),rows.getFloat("precio"),rows.getInt("stock"));
            lista.put(aux.getId(), aux);
        }        
        return lista;
    } 
}
