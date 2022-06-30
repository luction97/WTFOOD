
package com.wtfood.servicios;

import com.wtfood.entidades.Foto;
import com.wtfood.entidades.Ingrediente;
import com.wtfood.entidades.Receta;
import com.wtfood.entidades.Usuario;
import com.wtfood.errores.ErrorServicio;
import com.wtfood.repositorios.FotoRepositorio;
import com.wtfood.repositorios.RecetaRepositorio;
import com.wtfood.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
public class RecetaServicio {
    
    @Autowired
    private RecetaRepositorio recetaRepositorio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private FotoRepositorio fotoRepositorio;
      
    @Transactional(propagation = Propagation.NESTED)
    public void registrarReceta(String nombre, Integer calificaciones, List<Ingrediente> ingredientes, Usuario usuario, Foto foto, ArrayList<String> pasoAPaso) throws ErrorServicio {
        
        validar(nombre, calificaciones, ingredientes, usuario, foto, pasoAPaso);
        
        Receta receta = new Receta();
        
        receta.setNombre(nombre);
        receta.setCalificaciones(calificaciones);
        receta.setCantidadIngredientes(ingredientes.size());
        receta.setUsuario(usuario);
        receta.setFoto(foto);
        receta.setIngredientes(ingredientes);
        receta.setPasoAPaso(pasoAPaso);
        
        recetaRepositorio.save(receta);
        
    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void modificarReceta(String id, String nombre, Integer calificaciones, Integer cantidadIngredientes, List<Ingrediente> ingredientes, String idUsuario, String idFoto, ArrayList<String> pasoAPaso) throws ErrorServicio {
        
        Usuario usuario = usuarioRepositorio.buscarPorId(idUsuario);
        Foto foto = fotoRepositorio.buscarPorId(idFoto);
        
        validar(nombre, calificaciones, ingredientes, usuario, foto, pasoAPaso);
        
        Optional<Receta> respuesta = recetaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Receta receta = new Receta();
            receta = respuesta.get();
            
            receta.setNombre(nombre);
            receta.setCalificaciones(calificaciones);
            receta.setCantidadIngredientes(cantidadIngredientes);
            receta.setIngredientes(ingredientes);
            receta.setUsuario(usuario);
            receta.setFoto(foto);
            receta.setPasoAPaso(pasoAPaso);
            
            recetaRepositorio.save(receta);
        } else {
            throw new ErrorServicio("No se encontro la receta solicitada.");
        }
        
    }
    
    @Transactional(readOnly = true)
    public ArrayList<Receta>  listarRecetas() throws ErrorServicio {
        
        ArrayList<Receta> recetas = recetaRepositorio.listarRecetas();
//        if (!recetas.isEmpty()) {
//            return recetas;
//        } else {
//            throw new ErrorServicio("No se ha encontrado ninguna receta.");
//        }
        return recetas;
    }
    
    @Transactional(readOnly = true)
    public ArrayList<String>  listarPasos() throws ErrorServicio {
        
        ArrayList<String> pasos = recetaRepositorio.listarPasos();
        return pasos;
    }
    
    @Transactional(readOnly = true)
    public Receta buscarRecetaPorId(String id) throws ErrorServicio {
        
        if (id == null) {
            throw new ErrorServicio("El id no puede ser nulo");
        }
        
        if (id.isEmpty()) {
            throw new ErrorServicio("El id no puede estar vacio.");
        }
        
        Receta receta = recetaRepositorio.buscarPorId(id);
        if(receta != null) {
            return receta;
        } else {
            throw new ErrorServicio("No se ah encontrado la receta solicitada.");
        }
        
    }
    
    private void validar(String nombre, Integer calificaciones, List<Ingrediente> ingredientes, Usuario usuario, Foto foto, ArrayList<String> pasoAPaso) throws ErrorServicio {
        
        if (nombre == null) {
            throw new ErrorServicio("El nombre de la receta no puede ser nulo.");
        }
        
        if (nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la receta no puede estar vacio.");
        }
        
        if (calificaciones == null) {
            throw new ErrorServicio("Las calificaciones no pueden ser nulas.");
        }
        
        if (calificaciones < 0) {
            throw new ErrorServicio("Las calificaciones no pueden ser menor a 0 (cero).");
        }
        
        if (usuario == null) {
            throw new ErrorServicio("El usuario no puede ser nulo.");
        }
        
        if (ingredientes == null) {
            throw new ErrorServicio("Los ingredientes no pueden ser nulo.");
        }
        
        if (foto == null) {
            throw new ErrorServicio("La foto no puede ser nula.");
        }
        
         if (pasoAPaso == null) {
            throw new ErrorServicio("Hay que indicar el paso a paso de la receta.");
        }
        
    }
    
}
