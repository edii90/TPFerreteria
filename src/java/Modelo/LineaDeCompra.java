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
public class LineaDeCompra extends Productos{

    private int idLinea;
    private float costoUnit;
    private int cantidad;
       
    public LineaDeCompra(){
        
    }
    
    public LineaDeCompra(int IdLinea,int idProd, String Nombre, int Cantidad, float CostotUnit){
        super(idProd,Nombre);
        idLinea = IdLinea;
        cantidad = Cantidad;
        costoUnit = CostotUnit;
    }    
    public LineaDeCompra(int idProd, String Nombre, int Cantidad, float CostotUnit){
        super(idProd,Nombre);
        cantidad = Cantidad;
        costoUnit = CostotUnit;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
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
