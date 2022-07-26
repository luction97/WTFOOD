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
import org.springframework.web.multipart.MultipartFile;

@Service
public class RecetaServicio {

    @Autowired
    private RecetaRepositorio recetaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private FotoRepositorio fotoRepositorio;
    
     @Autowired
    private FotoServicio fotoServicio;

    
    

    @Transactional(propagation = Propagation.NESTED)
    public void registrarReceta(String nombre, Integer calificaciones, List<Ingrediente> ingredientes, Usuario usuario, MultipartFile archivo, String pasoAPaso) throws ErrorServicio {

        validar(nombre, calificaciones, ingredientes, usuario, pasoAPaso);

        Receta receta = new Receta();

        receta.setNombre(nombre);
        receta.setCalificaciones(calificaciones);
        receta.setCantidadIngredientes(ingredientes.size());
        receta.setUsuario(usuario);
        
        Foto foto = fotoServicio.guardar(archivo); 
        receta.setFoto(foto);
        
        receta.setIngredientes(ingredientes);
        receta.setPasoAPaso(pasoAPaso);

        recetaRepositorio.save(receta);

    }

    @Transactional(propagation = Propagation.NESTED)
    public void modificarReceta(String id, String nombre, Integer calificaciones, Integer cantidadIngredientes, List<Ingrediente> ingredientes, String idUsuario, String idFoto, String pasoAPaso) throws ErrorServicio {

        Usuario usuario = usuarioRepositorio.buscarPorId(idUsuario);
        Foto foto = fotoRepositorio.buscarPorId(idFoto);

        validar(nombre, calificaciones, ingredientes, usuario, pasoAPaso);

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
    public List<Receta> listarRecetas() throws ErrorServicio {

        List<Receta> recetas = recetaRepositorio.listarRecetas();
//        if (!recetas.isEmpty()) {
//            return recetas;
//        } else {
//            throw new ErrorServicio("No se ha encontrado ninguna receta.");
//        }

        return recetas;
    }

    @Transactional(readOnly = true)
    public List<String> listarPasos() throws ErrorServicio {

        List<String> pasos = recetaRepositorio.listarPasos();
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
        if (receta != null) {
            return receta;
        } else {
            throw new ErrorServicio("No se ah encontrado la receta solicitada.");
        }

    }

    @Transactional(readOnly = true)
    public List<Receta> consultarPorNombre(String nombre) throws ErrorServicio {

        validarNombre(nombre);

        try {
            return recetaRepositorio.buscarRecetaPorNombre(nombre);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
     @Transactional(readOnly = true)
    public List<Receta> consultarTodas() {

        try {
            return recetaRepositorio.listarRecetas();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void validar(String nombre, Integer calificaciones, List<Ingrediente> ingredientes, Usuario usuario, String pasoAPaso) throws ErrorServicio {

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

//        if (foto == null) {
//            throw new ErrorServicio("La foto no puede ser nula.");
//        }
        if (pasoAPaso == null) {
            throw new ErrorServicio("Hay que indicar el paso a paso de la receta.");
        }

    }

    public void validarNombre(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la receta no puede ser nulo.");
        }
    }

}
