
package com.wtfood.repositorios;

import com.wtfood.entidades.Ingrediente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRepositorio extends JpaRepository<Ingrediente, String>{
    
    @Query("SELECT c FROM Ingrediente c WHERE c.id = :id")
    public Ingrediente buscarPorId(@Param("id") String id);
    
    @Query("SELECT c FROM Ingrediente c WHERE c.nombre LIKE %:nombre%")
    public List<Ingrediente> buscarPorNombre(@Param("nombre") String nombre); 
    
    @Query("SELECT c FROM Ingrediente c")
    public List<Ingrediente> buscarTodos();

}
