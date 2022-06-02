
package com.wtfood.servicios;

import com.wtfood.entidades.Foto;
import com.wtfood.entidades.Ingrediente;
import com.wtfood.errores.ErrorServicio;
import com.wtfood.repositorios.IngredienteRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class IngredienteServicio {
    
    @Autowired  
    private IngredienteRepositorio ingredienteRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;
    
    
    @Transactional(propagation = Propagation.NESTED)
    public void ingresar(MultipartFile archivo, String nombre) throws ErrorServicio {

        validar(nombre);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre(nombre);
        
        Foto foto = fotoServicio.guardar(archivo);
        ingrediente.setFoto(foto);

        ingredienteRepositorio.save(ingrediente);
    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void eliminar(String id) throws ErrorServicio {

        Optional<Ingrediente> respuesta = ingredienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Ingrediente ingrediente = respuesta.get();
            ingredienteRepositorio.delete(ingrediente);
        } else {
            throw new ErrorServicio("No se encontro el ingrediente solicitado.");
        }
    }
    
      @Transactional(propagation = Propagation.NESTED)
    public void modificar(MultipartFile archivo, String id, String nombre) throws ErrorServicio {

        validar(nombre);

        Optional<Ingrediente> respuesta = ingredienteRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Ingrediente ingrediente = respuesta.get();
            ingrediente.setNombre(nombre);

             String idFoto = null;
                if (ingrediente.getFoto() != null) {
                    idFoto = ingrediente.getFoto().getId();
                }

                Foto foto = fotoServicio.actualizar(idFoto, archivo);
                ingrediente.setFoto(foto);
                
            ingredienteRepositorio.save(ingrediente);

        } else {
            throw new ErrorServicio("No se encontro el ingrediente solicitado.");
        }

    }
    
    @Transactional(readOnly = true)
    public List<Ingrediente> consultarPorNombre(String nombre) throws ErrorServicio {

        validar(nombre);

        try {
            return ingredienteRepositorio.buscarPorNombre(nombre);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
     @Transactional(readOnly = true)
    public List<Ingrediente> consultarTodos() {

        try {
            return ingredienteRepositorio.buscarTodos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void validar(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del ingrediente no puede ser nulo.");
        }
       
}
    
}

