package com.usuarios.ms_usuarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias para la capa del controlador (UsuarioController).

 */
// @WebMvcTest carga solo la capa web (Controller)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula las peticiones HTTP

    @Autowired
    private ObjectMapper objectMapper; // Convierte objetos Java a JSON y viceversa

    @MockBean
    private UsuarioRepository usuarioRepository; // Mockea el repositorio para no usar la BD


    /**
     * Helper para crear un usuario de prueba con todos los campos no nulos.
     * Esto asegura que el ObjectMapper pueda deserializar/serializar sin problemas.
     */
    private Usuario createTestUser(Integer id, String nombre, String mail, int numeroVivienda, int codigoPostal, int numeroContacto) {
        Usuario user = new Usuario();
        user.setId(id != null ? id : 0); // La entidad real usa int, no Integer. Usamos 0 si es nulo (para new user).
        user.setNombre(nombre);
        user.setApellido("TestApellido");
        user.setRut("11111111-1");
        user.setRol("Cliente");
        user.setNombreUsuario("testuser" + id);
        user.setContrasenia("pass123");
        user.setCalle("Calle Test");
        user.setNumeroVivienda(numeroVivienda);
        user.setComuna("Comuna Test");
        user.setRegion("Region Test");
        user.setReferenciaDireccion("Ref Test");
        user.setCodigoPostal(codigoPostal);
        user.setMail(mail);
        user.setNumeroContacto(numeroContacto);
        return user;
    }


    // --- PRUEBAS DE LECTURA (GET) ---

    /**
     * Prueba 1: GET /usuario - Listar todos los usuarios exitosamente.
     */
    @Test
    void testObtenerUsuariosExitoso() throws Exception {
        // ARRANGE
        Usuario user1 = createTestUser(1, "Alice", "alice@test.com", 100, 10000, 911111111);
        Usuario user2 = createTestUser(2, "Bob", "bob@test.com", 200, 20000, 922222222);
        List<Usuario> usuarios = Arrays.asList(user1, user2);

        // MOCKING: Cuando se llama a findAll(), devuelve la lista simulada.
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // ACT & ASSERT
        mockMvc.perform(get("/usuario")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 200 OK
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Alice"));
    }

    /**
     * Prueba 2: GET /usuario/{id} - Buscar un usuario por ID existente.
     */
    @Test
    void testBuscarUsuarioPorIdExitoso() throws Exception {
        // ARRANGE
        Integer userId = 1;
        Usuario mockUser = createTestUser(userId, "Charlie", "charlie@test.com", 300, 30000, 933333333);

        // MOCKING: Cuando se busca el ID 1, devuelve el usuario.
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // ACT & ASSERT
        mockMvc.perform(get("/usuario/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 200 OK
                .andExpect(jsonPath("$.nombre").value("Charlie"));
    }

    /**
     * Prueba 3: GET /usuario/{id} - Buscar un usuario por ID no existente.
     */
    @Test
    void testBuscarUsuarioPorIdNoEncontrado() throws Exception {
        // ARRANGE
        Integer userId = 99;

        // MOCKING: Cuando se busca el ID 99, devuelve Optional vacío.
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        mockMvc.perform(get("/usuario/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // 404 Not Found
    }

    // --- PRUEBA DE CREACIÓN (POST) ---

    /**
     * Prueba 4: POST /usuario - Registrar un nuevo usuario exitosamente.
     */
    @Test
    void testGuardarUsuarioExitoso() throws Exception {
        // ARRANGE: Usuario que enviamos sin ID, y el que retorna la BD con ID (simulado)
        Usuario newUser = createTestUser(null, "Diana", "diana@test.com", 400, 40000, 944444444);
        
        // Simula el usuario después de ser guardado (con ID asignado)
        Usuario savedUser = createTestUser(3, "Diana", "diana@test.com", 400, 40000, 944444444);

        // MOCKING: Cuando se llama a save, devuelve el usuario con ID 3.
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(savedUser);

        // ACT & ASSERT
        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))) // Envía el objeto a JSON
                .andExpect(status().isOk()) // 200 OK (según la implementación de tu controller)
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nombre").value("Diana"))
                .andExpect(jsonPath("$.numeroVivienda").value(400));
        
        // VERIFICACIÓN: Aseguramos que el método save fue llamado.
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }


    // --- PRUEBAS DE ACTUALIZACIÓN (PUT) ---

    /**
     * Prueba 5: PUT /usuario/{id} - Actualización de un usuario existente exitosa.
     */
    @Test
    void testActualizarUsuarioExitoso() throws Exception {
        // ARRANGE
        Integer userId = 1;
        // Usuario ORIGINAL que se encuentra en la BD
        Usuario originalUser = createTestUser(userId, "Pedro", "pedro@old.com", 500, 50000, 955555555);

        // Datos de ACTUALIZACIÓN (el body de la petición PUT)
        Usuario updatedData = new Usuario();
        updatedData.setNombre("Pedro Nuevo"); 
        updatedData.setApellido("Actualizado");
        updatedData.setNumeroVivienda(600); // Cambio de int
        updatedData.setMail("pedro@new.com");

        // Usuario que sale después del save
        Usuario finalUser = createTestUser(userId, updatedData.getNombre(), updatedData.getMail(), 
                                           updatedData.getNumeroVivienda(), originalUser.getCodigoPostal(), originalUser.getNumeroContacto());
        finalUser.setApellido(updatedData.getApellido()); // Aplicamos el cambio de apellido

        
        // MOCKING:
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(originalUser));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(finalUser);

        // ACT & ASSERT
        mockMvc.perform(put("/usuario/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedData)))
                .andExpect(status().isOk()) // 200 OK
                .andExpect(jsonPath("$.nombre").value("Pedro Nuevo"))
                .andExpect(jsonPath("$.apellido").value("Actualizado")) // Verificamos el cambio de apellido
                .andExpect(jsonPath("$.numeroVivienda").value(600)); 

        // VERIFICACIÓN
        verify(usuarioRepository, times(1)).findById(userId);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }


    /**
     * Prueba 6: PUT /usuario/{id} - Intentar actualizar un usuario no existente.
     */
    @Test
    void testActualizarUsuarioNoEncontrado() throws Exception {
        // ARRANGE
        Integer userId = 99;
        Usuario updatedData = new Usuario(); // Usamos un objeto vacío, solo para el body

        // MOCKING: Cuando se busca el ID, devuelve Optional vacío.
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // ACT & ASSERT
        mockMvc.perform(put("/usuario/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedData)))
                .andExpect(status().isNotFound()); // 404 Not Found

        // VERIFICACIÓN: Aseguramos que NUNCA se llamó a save.
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }


    // --- PRUEBAS DE ELIMINACIÓN (DELETE) ---

    /**
     * Prueba 7: DELETE /usuario/{id} - Eliminar un usuario existente exitosamente.
     */
    @Test
    void testEliminarUsuarioExistente() throws Exception {
        // ARRANGE
        Integer userId = 5;

        // MOCKING: Simulamos que el usuario existe.
        when(usuarioRepository.existsById(userId)).thenReturn(true);

        // ACT & ASSERT
        mockMvc.perform(delete("/usuario/{id}", userId))
                .andExpect(status().isNoContent()); // 204 No Content

        // VERIFICACIÓN: Aseguramos que se llamó a deleteById.
        verify(usuarioRepository, times(1)).deleteById(userId);
    }

    /**
     * Prueba 8: DELETE /usuario/{id} - Intentar eliminar un usuario no existente.
     */
    @Test
    void testEliminarUsuarioNoEncontrado() throws Exception {
        // ARRANGE
        Integer userId = 99;

        // MOCKING: Simulamos que el usuario no existe.
        when(usuarioRepository.existsById(userId)).thenReturn(false);

        // ACT & ASSERT
        mockMvc.perform(delete("/usuario/{id}", userId))
                .andExpect(status().isNotFound()); // 404 Not Found

        // VERIFICACIÓN: Aseguramos que deleteById NUNCA fue llamado.
        verify(usuarioRepository, never()).deleteById(userId);
    }
}
