package com.usuarios.ms_usuarios;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {


    private final UsuarioRepository usuarioRepository;
    
    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Listar todos los Usuarios
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> obtenerUsuarios() {
        // 1. Obtener la lista de entidades
        List<Usuario> usuarios = usuarioRepository.findAll();

        // 2. Convertir cada Usuario a EntityModel
        List<EntityModel<Usuario>> usuarioModels = usuarios.stream()
            .map(usuario -> EntityModel.of(usuario, 
                linkTo(methodOn(UsuarioController.class).buscar(usuario.getId())).withRel("buscar usuario por ID"),
                linkTo(methodOn(UsuarioController.class).eliminar(usuario.getId())).withRel("eliminar"),
                linkTo(methodOn(UsuarioController.class).actualizar(usuario.getId(), null)).withRel("actualizar")
            ))
            .collect(Collectors.toList());

        // 3. Envolver la lista en CollectionModel y añadir el enlace 'self' para la colección
        CollectionModel<EntityModel<Usuario>> collectionModel = CollectionModel.of(usuarioModels, 
            linkTo(methodOn(UsuarioController.class).obtenerUsuarios()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    //Listar  Usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Integer id) {
        return usuarioRepository.findById(id).map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build()); 
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
