/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

import Modelo.Compras;
import Modelo.LineaDeCompra;
import Modelo.Usuarios;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Edii
 */
public class Dcompras extends coneccionBD {
    public Dcompras () throws Exception{
        super();
    }
    
    public Hashtable TraerTodasCompras() throws Exception
    {
        Dusuarios dusr = new Dusuarios();
        Hashtable lista = new Hashtable();
        
        String sql = "SELECT * FROM Compras;";
        PreparedStatement ps = Sentencia(sql);
        ResultSet rows = consulta(ps);
        
        while (rows.next()){
            Usuarios usr = dusr.traerXid(rows.getInt("idUsuario"));
            Compras aux = new Compras(usr,rows.getDate("fecha"));
            aux.setId(rows.getInt("idCompras"));
            
            String sqlProd = "SELECT * FROM prodxcomp where idCompra ="+aux.getId()+";";
            PreparedStatement psProd = Sentencia(sqlProd);
            ResultSet rowsProd = consulta(psProd);
           
            while (rowsProd.next()){
                LineaDeCompra pro = new LineaDeCompra(rows.getInt("idProd"),rows.getString("nombre"),rows.getInt("cantidad"),rows.getFloat("precioUnit"));
                aux.agregarprod(pro);
            }
            lista.put(aux.getId(), aux);
        }        
        return lista;
    } 
    
    public Hashtable TraerComprasXusr(Usuarios user) throws Exception
    {
        Dusuarios dusr = new Dusuarios();
        Hashtable lista = new Hashtable();
        
        String sql = "SELECT * FROM Compras where idUsuario = '"+ user.getId()+";";
        PreparedStatement ps = Sentencia(sql);
        ResultSet rows = consulta(ps);
        
        while (rows.next()){
            Usuarios usr = dusr.traerXid(rows.getInt("idUsuario"));
            Compras aux = new Compras(usr,rows.getDate("fecha"));
            aux.setId(rows.getInt("idCompras"));
            
            String sqlProd = "SELECT * FROM SELECT * FROM prodxcomp where idCompra ="+aux.getId()+";";
            PreparedStatement psProd = Sentencia(sqlProd);
            ResultSet rowsProd = consulta(psProd);
           
            while (rowsProd.next()){
                LineaDeCompra pro = new LineaDeCompra(rows.getInt("idProd"),rows.getString("nombre"),rows.getInt("cantidad"),rows.getFloat("precioUnit"));
                aux.agregarprod(pro);
            }
            lista.put(aux.getId(), aux);
        }        
        return lista;
    }
    
    public Hashtable TraerComprasXusr(int id) throws Exception
    {
        Dusuarios dusr = new Dusuarios();
        Hashtable lista = new Hashtable();
        
        String sql = "SELECT * FROM Compras where idUsuario = '"+ id+";";
        PreparedStatement ps = Sentencia(sql);
        ResultSet rows = consulta(ps);
        
        while (rows.next()){
            Usuarios usr = dusr.traerXid(rows.getInt("idUsuario"));
            Compras aux = new Compras(usr,rows.getDate("fecha"));
            aux.setId(rows.getInt("idCompras"));
            
            String sqlProd = "SELECT * FROM SELECT * FROM prodxcomp where idCompra ="+aux.getId()+";";
            PreparedStatement psProd = Sentencia(sqlProd);
            ResultSet rowsProd = consulta(psProd);
           
            while (rowsProd.next()){
                LineaDeCompra pro = new LineaDeCompra(rows.getInt("idProd"),rows.getString("nombre"),rows.getInt("cantidad"),rows.getFloat("precioUnit"));
                aux.agregarprod(pro);
            }
            lista.put(aux.getId(), aux);
        }        
        return lista;
    }
    
    public void CrearCompra(Compras comp) throws Exception
    {
        //Comando para obtener la fecha y hora actual
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        
        String sql = "delimiter // start transaction;";

        //Inserta la cabecera de la compra
        sql += "INSERT INTO Compras (idUsuario,fecha,total) values('"
                + comp.getUsr().getId() + "','"
                + df.format(dateobj)+"','"
                + comp.getTotal() + "'); //";
        
        //Recorre la lista de productos e Inserta la lista en la tabla relacinal productos X compra
        Enumeration e = comp.getLista().elements();
        LineaDeCompra aux;
        while(e.hasMoreElements())
        {
            aux=(LineaDeCompra) e.nextElement();
            
        sql += "INSERT INTO prodxcomp (idCompra,idProd,cantidad,precioUnit) values("
                + "(SELECT MAX(idCompras) FROM compras),'"
                + aux.getIdLinea()+"','"
                + aux.getCantidad()+"','"
                + aux.getCostoUnit()
                + "'); //";
        }
        sql += "commit";
            PreparedStatement psProd = Sentencia(sql);
            consulta(psProd);
    }
    
    public void EliminarCompra(Compras comp) throws Exception
    {
         String sql = "start transaction;"
                 + "DELETE FROM `prodxcomp` WHERE `idCompra`='"
                +comp.getId() + "';"
                 + "delete from compras where idCompras ='"
                 + comp.getId()+ "';"
                 + "commit;";
        PreparedStatement ps = Sentencia(sql);
        consulta(ps); 
    }
    public void EliminarCompra(int id) throws Exception
    {
         String sql = "start transaction;"
                 + "DELETE FROM `prodxcomp` WHERE `idCompra`='"
                +id + "';"
                 + "delete from compras where idCompras ='"
                 + id+ "';"
                 + "commit;";
        PreparedStatement ps = Sentencia(sql);
        consulta(ps); 
    }
}
