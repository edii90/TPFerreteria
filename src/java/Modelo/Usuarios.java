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
public class Usuarios {
    private int id;
    private String usuario;
    private String contrasenia;
    private int DNI;
    private String nombre;
    private String apellido;
    private int tipoUsr;
    private boolean estado;
    
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContrasenia() {
        return contrasenia;
    }
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    public int getDNI() {
        return DNI;
    }
    public void setDNI(int DNI) {
        this.DNI = DNI;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public int getTipoUsr() {
        return tipoUsr;
    }
    public void setTipoUsr(int tipoUsr) {
        this.tipoUsr = tipoUsr;
    }
    
    public Usuarios(String usr,String pass,int doc,String nomb,String ape,int tipo)
    {
        usuario = usr;
        contrasenia = pass;
        DNI = doc;
        nombre = nomb;
        apellido = ape;
        tipoUsr = tipo;
    }    
    public Usuarios(int id,String usr,String pass,int doc,String nomb,String ape,int tipo)
    {
        this.id = id;
        usuario = usr;
        contrasenia = pass;
        DNI = doc;
        nombre = nomb;
        apellido = ape;
        tipoUsr = tipo;
    }  
    public Usuarios(int id,String usr,String pass,int doc,String nomb,String ape,int tipo,boolean estado)
    {
        this.id = id;
        this.estado = estado;
        usuario = usr;
        contrasenia = pass;
        DNI = doc;
        nombre = nomb;
        apellido = ape;
        tipoUsr = tipo;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
