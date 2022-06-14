package com.wtfood.repositorios;

import com.wtfood.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String> {
    
    @Query("SELECT u FROM Foto u WHERE u.id = :id")
    public Foto buscarPorId(@Param("id") String id);
    
}
