package com.usuarios.ms_usuarios;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
@RequestMapping("/usuario")
public class UsuarioController {


    private final UsuarioRepository usuarioRepository;
    
    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Listar todos los Usuarios
    @GetMapping
    public List<Usuario> obteneUsuarios() {
        return usuarioRepository.findAll();
    }
    //Listar  Usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Integer id) {
        return usuarioRepository.findById(id).map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build()); // NOS TRAE EL OBJETO POR ID 
    }
    //Registro Usuario
    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario); // GUARDA EL OBJETO EN LA BASE DE DATOS
    }
    //Actualizar Usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario datos) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(datos.getNombre());
            usuario.setApellido(datos.getApellido());
            usuario.setRut(datos.getRut());
            usuario.setRol(datos.getRol());
            usuario.setNombreUsuario(datos.getNombreUsuario());
            usuario.setContrasenia(datos.getContrasenia());            
            usuario.setCalle(datos.getCalle());
            usuario.setNumeroVivienda(datos.getNumeroVivienda());
            usuario.setComuna(datos.getComuna());
            usuario.setRegion(datos.getRegion());
            usuario.setReferenciaDireccion(datos.getReferenciaDireccion());
            usuario.setCodigoPostal(datos.getCodigoPostal());   
            usuario.setMail(datos.getMail());
            usuario.setNumeroContacto(datos.getNumeroContacto());             

            return ResponseEntity.ok(usuarioRepository.save(usuario));
        }).orElse(ResponseEntity.notFound().build()); // ACTUALIZA EL OBJETO POR ID
    }
    //Elimina Usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // ELIMINA EL OBJETO POR ID
        }
        return ResponseEntity.notFound().build();
    }
    
  
    
}
