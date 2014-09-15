/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class Compras {
    private int id;
    private Usuarios usr;
    private Date fecha;
    private float total;
    private Hashtable lista;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Usuarios getUsr() {
        return usr;
    }
    public void setUsr(Usuarios usr) {
        this.usr = usr;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public float getTotal()    {
        Enumeration e = getLista().elements();
        LineaDeCompra aux;
        while(e.hasMoreElements())
        {
            aux=(LineaDeCompra) e.nextElement();
            total+=aux.getCostoUnit()*aux.getCantidad();
        }
        return total;   
    }
    public void setTotal(float total) {
        this.total = total;
    }
    public void agregarprod(LineaDeCompra pro){
            getLista().put(pro.getIdLinea(), pro);
    }    
    public void eliminarprod(LineaDeCompra pro)   {
        getLista().remove(pro.getIdLinea());
    }
    public Hashtable getLista() {
        return lista;
    }
    public void setLista(Hashtable lista) {
        this.lista = lista;
    }
    
    public Compras(Usuarios user, Date fech){
        usr = user;
        fecha = fech;
    }
    public Compras(int idCompra,Usuarios user, Date fech){
        id = idCompra;
        usr = user;
        fecha = fech;
    }
    public Compras(int idCompra,Usuarios User, Date Fecha, Hashtable Lista){
        id = idCompra;
        usr = User;
        fecha = Fecha;
        lista = Lista;
    }
    
}
