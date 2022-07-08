
package com.wtfood.repositorios;

import com.wtfood.entidades.Ingrediente;
import com.wtfood.entidades.Receta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaRepositorio extends JpaRepository<Receta, String> {

    @Query("SELECT r FROM Receta r")
    public List<Receta> listarRecetas();
    
    @Query("SELECT r FROM Receta r WHERE r.id LIKE :id")
    public Receta buscarPorId(@Param("id") String id);
    
    @Query("SELECT r FROM Receta r WHERE r.usuario.nombre LIKE :nombre")
    public List<Receta> buscarRecetaPorNombreUsuario(@Param("nombre") String nombre);
    
    @Query("SELECT r FROM Receta r WHERE r.calificaciones = :calificacion")
    public List<Receta> buscarRecetaPorCalificaciones(@Param("calificacion") Integer calificacion);
    
    @Query("SELECT r FROM Receta r WHERE r.cantidadIngredientes = :cantidadIngredientes")
    public List<Receta> buscarRecetaPorCantidadIngredientes(@Param("cantidadIngredientes") Integer cantidadIngredientes);
    
    @Query("SELECT r.pasoAPaso FROM Receta r")
    public List<String> listarPasos();
    
    @Query("DELETE FROM Receta WHERE id LIKE :id")
    public void eliminarRecetaPorId(@Param("id") String id);
    
     @Query("SELECT r FROM Receta r WHERE r.nombre LIKE %:nombre%")
    public List<Receta> buscarRecetaPorNombre(@Param("nombre") String nombre);
    
}
