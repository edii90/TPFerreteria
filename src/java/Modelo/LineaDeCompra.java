/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo;

/**
 *
 * @author Edii
 */
public class LineaDeCompra{

    private int idLinea;
    private String nombre;
    private float costoUnit;
    private int cantidad;
       
    public LineaDeCompra(){
        
    }
    
    public LineaDeCompra(int IdLinea, String Nombre, int Cantidad, float CostotUnit){
        
        idLinea = IdLinea;
        nombre = Nombre;
        cantidad = Cantidad;
        costoUnit = CostotUnit;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCostoUnit() {
        return costoUnit;
    }

    public void setCostoUnit(float costoUnit) {
        this.costoUnit = costoUnit;
    }

    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
