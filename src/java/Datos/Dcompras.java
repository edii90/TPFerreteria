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
import java.sql.SQLException;
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

    public Dcompras() throws Exception {
        super();
    }

    public Hashtable TraerTodasCabacerasCompras() throws Exception {     
        
//      Esta consulta esta mal. Deberia ser una sola consulta asi 
//      "SELECT * FROM compras c inner join prodxcomp pc where c.idCompras=pc.idCompra" 
//      pero no se como manejar los datos
        
        try {
            super.conectar();
            Dusuarios dusr = new Dusuarios();
            Hashtable lista = new Hashtable();

            String sql = "SELECT * FROM Compras;";
            PreparedStatement ps = Sentencia(sql);
            ResultSet rows = consulta(ps);

            while (rows.next()) {
                Usuarios usr = dusr.traerXid(rows.getInt("idUsuario"));
                Compras aux = new Compras(rows.getInt("idCompras"),usr, rows.getDate("fecha"));
                aux.setTotal(rows.getFloat("total"));
                lista.put(aux.getId(), aux);
            }
            return lista;
        } catch (SQLException ex) {
            throw new SQLException("Error en traer todas las cabeceras de Compras " + ex.getMessage());
        } finally {
            super.desconectar();
        }
    }
    
    public Hashtable TraerLineasComprasPorIdCabecera(int idCabecera) throws Exception
    {
        try{
            super.conectar();
            Hashtable aux = new Hashtable();
                String sqlProd = "SELECT idLinea,idProd,nombre,precioUnit,cantidad FROM prodxcomp pc inner join productos p on p.idProductos=pc.idProd where idCompra =" + idCabecera + ";";
                PreparedStatement psProd = Sentencia(sqlProd);
                ResultSet rows = consulta(psProd);

                while (rows.next()) {
                    //int idLin,int idProd,String nombre, float CostoUnit, int Cantidad
                    LineaDeCompra pro = new LineaDeCompra(rows.getInt("idLinea"), rows.getInt("idProd"), rows.getString("nombre"), rows.getFloat("precioUnit"),rows.getInt("cantidad"));
                    aux.put(pro.getIdLinea(), pro);
                }
                return aux;
        } catch (SQLException ex) {
            throw new SQLException("Error en traer todas las lineas de compras por cabecera de Compra " + ex.getMessage());
        } finally {
            super.desconectar();
        }
    }

    public Hashtable TraerComprasXusr(Usuarios user) throws Exception {
        try {
            super.conectar();
            Dusuarios dusr = new Dusuarios();
            Hashtable lista = new Hashtable();

            String sql = "SELECT * FROM Compras where idUsuario = '" + user.getId() + "';";
            PreparedStatement ps = Sentencia(sql);
            ResultSet rows = consulta(ps);

            while (rows.next()) {
                Compras aux = new Compras(rows.getInt("idCompras"),user, rows.getDate("fecha"));
                aux.setTotal(rows.getFloat("total"));
                lista.put(aux.getId(), aux);
                }
            return lista;
        } catch (SQLException ex) {
            throw new SQLException("Error en traer todas las Compras por usuario " + ex.getMessage());
        } finally {
            super.desconectar();
        }
    }

    public void CrearCompra(Compras comp) throws Exception {
        
        String sql = "";
        
        try {
            super.conectar();
            
            sql = "delimiter // start transaction;";

            //Inserta la cabecera de la compra
            sql += "INSERT INTO Compras (idUsuario,total) values('"
                    + comp.getUsr().getId() + "','"
                    + comp.getTotal() + "'); //";

            //Recorre la lista de productos e Inserta la lista en la tabla relacinal productos X compra
            Enumeration e = comp.getLista().elements();
            LineaDeCompra aux;
            while (e.hasMoreElements()) {
                aux = (LineaDeCompra) e.nextElement();

                sql += "INSERT INTO prodxcomp (idCompra,idProd,cantidad,precioUnit) values("
                        + "(SELECT MAX(idCompras)+1 FROM compras),'"
                        + aux.getId()+ "','"
                        + aux.getCantidad() + "','"
                        + aux.getCostoUnit()
                        + "'); //";
            }
            sql += "commit";
            PreparedStatement psProd = Sentencia(sql);
            consulta(psProd);
        } catch (SQLException ex) {
            throw new SQLException("Error en crear la Compras " + ex.getMessage()+"       ***    "+sql);
        } finally {
            super.desconectar();
        }
    }

    public void EliminarCompra(Compras comp) throws Exception {
        try {
            super.conectar();
            String sql = "start transaction;"
                    + "DELETE FROM `prodxcomp` WHERE `idCompra`='"
                    + comp.getId() + "';"
                    + "delete from compras where idCompras ='"
                    + comp.getId() + "';"
                    + "commit;";
            PreparedStatement ps = Sentencia(sql);
            consulta(ps);
        } catch (SQLException ex) {
            throw new SQLException("Error en eliminar la compra " + ex.getMessage());
        } finally {
            super.desconectar();
        }
    }

    public void EliminarCompra(int id) throws Exception {
        try {
            super.conectar();
            String sql = "start transaction;"
                    + "DELETE FROM `prodxcomp` WHERE `idCompra`='"
                    + id + "';"
                    + "delete from compras where idCompras ='"
                    + id + "';"
                    + "commit;";
            PreparedStatement ps = Sentencia(sql);
            consulta(ps);
        } catch (SQLException ex) {
            throw new SQLException("Error en eliminar la compra por id " + ex.getMessage());
        } finally {
            super.desconectar();
        }
    }
}
