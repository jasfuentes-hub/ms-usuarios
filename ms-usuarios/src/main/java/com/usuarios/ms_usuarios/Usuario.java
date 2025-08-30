package com.usuarios.ms_usuarios;

public class Usuario {
    private int    id;
    private String nombre;
    private String apellido;
    private String rut;
    private String rol;
    private String nombreUsuario;
    private String contrasenia; 
    private String calle;
    private int    numeroVivienda;
    private String comuna;
    private String region;
    private String referenciaDireccion;
    private int    codigoPostal;
    private String mail;
    private int numeroContacto;

    public Usuario(int id, String nombre, String apellido, String rut, String rol, String nombreUsuario, String contrasenia, String calle, int numeroVivienda, String comuna, String region, String referenciaDireccion, int codigoPostal, String mail, int numeroContacto){ 
        this.id=id;
        this.nombre=nombre;
        this.apellido=apellido;
        this.rut=rut;
        this.rol=rol;
        this.nombreUsuario=nombreUsuario;
        this.contrasenia=contrasenia;
        this.calle=calle;
        this.numeroVivienda=numeroVivienda;
        this.comuna=comuna;
        this.region=region;
        this.referenciaDireccion=referenciaDireccion;
        this.codigoPostal=codigoPostal;
        this.mail=mail;
        this.numeroContacto=numeroContacto;
    }

    public int    getId() { return id;}
    public String getNombre() { return nombre;}
    public String getApellido() { return apellido;} 
    public String getRut() { return rut;}
    public String getRol() { return rol;}
    public String getNombreUsuario() { return nombreUsuario;}
    public String getContrasenia() { return contrasenia;}
    public String getCalle() { return calle;}
    public int    getNumeroVivienda() { return numeroVivienda;}
    public String getComuna() { return comuna;}
    public String getRegion() { return region;}
    public String getReferenciaDireccion() { return referenciaDireccion;}
    public int    getCodigoPostal() { return codigoPostal;}
    public String getMail() { return mail;}
    public int    getNumeroContacto() { return numeroContacto;}   
}
