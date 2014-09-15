
package Modelo;

public class LineaDeCompra extends Productos{

    private float costoUnit;
    private int cantidad;
    
    public LineaDeCompra()
    {}
    
    public LineaDeCompra(int id,String nombre, float CostoUnit, int Cantidad){
        
        super(id, nombre);
        costoUnit = CostoUnit;
        cantidad = Cantidad;
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
