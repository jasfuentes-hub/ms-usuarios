package com.usuarios.ms_usuarios;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private List<Usuario> listaUsuarios =new ArrayList<>();

    public UsuarioController() {
        listaUsuarios.add(new Usuario(1, "Ana", "García", "15.456.789-K", "Administrador", "anag", "pass123", "Calle Las Condes", 555, "Las Condes", "Región Metropolitana", "Edificio azul al lado del metro", 7550000, "ana.g@example.com", 987654321));
        listaUsuarios.add(new Usuario(2, "María", "Pérez", "12.987.654-3", "Vendedor", "mariap", "ventas789", "Calle Arturo Prat", 800, "Santiago", "Región Metropolitana", "Departamento 501, piso 5", 8320000, "maria.p@example.com", 955554444));    
        listaUsuarios.add(new Usuario(3, "Pedro", "López", "18.123.456-7", "Cliente", "pedrol", "clave456", "Avenida Providencia", 123, "Providencia", "Región Metropolitana", "Casa de dos pisos con reja blanca", 7500000, "pedro.l@example.com", 912345678));
        listaUsuarios.add(new Usuario(4, "Juan", "Gómez", "20.555.444-2", "Cliente", "juang", "password22", "Calle Varas", 210, "Temuco", "Región de la Araucanía", "Frente a la plaza principal", 4780000, "juan.g@example.com", 977778888));
        listaUsuarios.add(new Usuario(5, "Valentina", "Palma", "17.777.666-5", "Cliente", "valep", "nunoaPass", "Calle Irarrázaval", 987, "Ñuñoa", "Región Metropolitana", "Departamento 403, Edificio Los Jardines", 7790000, "valentina.p@example.com", 965431234));
        listaUsuarios.add(new Usuario(6, "Diego", "Herrera", "19.333.444-5", "Cliente", "diegoh", "providenciaPass", "Calle Manuel Montt", 1500, "Providencia", "Región Metropolitana", "A dos cuadras del metro Manuel Montt", 7500000, "diego.h@example.com", 944445555));
    }

    //Listar todos los Usuarios
    @GetMapping
    public List<Usuario> listaUsuarios() {
        return listaUsuarios;
    }

    //Listar  Usuario por ID
    @GetMapping("/{id}")
    public Usuario obtenerPorID(@PathVariable int id) {
        return listaUsuarios.stream()
                .filter(u -> u.getId()==id)
                .findFirst()
                .orElse(null);
    }
    
  
    
}
