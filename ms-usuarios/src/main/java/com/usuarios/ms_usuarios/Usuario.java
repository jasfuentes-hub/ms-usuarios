package com.usuarios.ms_usuarios;

import jakarta.persistence.*;


@Entity
@Table(name= "USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @Column(name = "APELLIDO", nullable = false)
    private String apellido;

    @Column(name = "RUT", nullable = false)
    private String rut;

    @Column(name = "ROL", nullable = false)
    private String rol;

    @Column(name = "NOMBREUSUARIO", nullable = false, unique = true)
    private String nombreUsuario;

    @Column(name = "CONTRASENIA", nullable = false)
    private String contrasenia; 
    
    @Column(name = "CALLE", nullable = false)
    private String calle;

    @Column(name = "NUMEROVIVIENDA", nullable = false)
    private int numeroVivienda;

    @Column(name = "COMUNA", nullable = false)
    private String comuna;

    @Column(name = "REGION", nullable = false)
    private String region;

    @Column(name = "REFERENCIADIRECCION", nullable = false)
    private String referenciaDireccion;

    @Column(name = "CODIGOPOSTAL", nullable = false)
    private int codigoPostal;

    @Column(name = "MAIL", nullable = false, unique = true)
    private String mail;
    
    @Column(name = "NUMEROCONTACTO", nullable = false)
    private int numeroContacto;
    
    // Constructor sin argumentos, necesario para JPA/Hibernate
    public Usuario() {
    }

    // Constructor con todos los atributos para crear un objeto
    public Usuario(int id, String nombre, String apellido, String rut, String rol, String nombreUsuario, String contrasenia, String calle, int numeroVivienda, String comuna, String region, String referenciaDireccion, int codigoPostal, String mail, int numeroContacto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rut = rut;
        this.rol = rol;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.calle = calle;
        this.numeroVivienda = numeroVivienda;
        this.comuna = comuna;
        this.region = region;
        this.referenciaDireccion = referenciaDireccion;
        this.codigoPostal = codigoPostal;
        this.mail = mail;
        this.numeroContacto = numeroContacto;
    }
    // Métodos Getter que necesitas para la serialización
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; } 
    public String getRut() { return rut; }
    public String getRol() { return rol; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getContrasenia() { return contrasenia; }
    public String getCalle() { return calle; }
    public int getNumeroVivienda() { return numeroVivienda; }
    public String getComuna() { return comuna; }
    public String getRegion() { return region; }
    public String getReferenciaDireccion() { return referenciaDireccion; }
    public int getCodigoPostal() { return codigoPostal; }
    public String getMail() { return mail; }
    public int getNumeroContacto() { return numeroContacto; }
    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setRut(String rut) { this.rut = rut; }
    public void setRol(String rol) { this.rol = rol; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
    public void setCalle(String calle) { this.calle = calle; }
    public void setNumeroVivienda(int numeroVivienda) { this.numeroVivienda = numeroVivienda; }
    public void setComuna(String comuna) { this.comuna = comuna; }
    public void setRegion(String region) { this.region = region; }
    public void setReferenciaDireccion(String referenciaDireccion) { this.referenciaDireccion = referenciaDireccion; }
    public void setCodigoPostal(int codigoPostal) { this.codigoPostal = codigoPostal; }
    public void setMail(String mail) { this.mail = mail; }
    public void setNumeroContacto(int numeroContacto) { this.numeroContacto = numeroContacto; }
}