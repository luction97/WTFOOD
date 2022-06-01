
package com.wtfood.servicios;

import com.wtfood.repositorios.IngredienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredienteServicio {
    
    @Autowired  
    private IngredienteRepositorio ingredienteRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;
    

}
