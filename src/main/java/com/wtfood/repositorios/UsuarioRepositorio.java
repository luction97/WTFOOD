
package com.wtfood.repositorios;

import com.wtfood.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    public Usuario buscarPorId(@Param("id") String id);
    
    @Query("SELECT u FROM Usuario u")
    public List<Usuario> listarUsuario();
    
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario encontrarPorEmail(@Param("email") String email);
    
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:nombre%")
    public List<Usuario> buscarPorNombre(@Param("nombre") String nombre);
    
}
