
package com.wtfood.controladores;

import com.wtfood.entidades.Receta;
import com.wtfood.errores.ErrorServicio;
import com.wtfood.repositorios.RecetaRepositorio;
import com.wtfood.servicios.RecetaServicio;
import com.wtfood.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/foto")
public class FotoControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private RecetaRepositorio recetaRepositorio;
    
    @GetMapping("/receta/{id}")
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable String id) throws ErrorServicio {

        Receta receta = recetaRepositorio.buscarPorId(id);
        if (receta.getFoto() == null) {
            throw new ErrorServicio("La receta no tiene una foto asignada.");
        }
        byte[] foto = receta.getFoto().getContenido(); 

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(foto, headers, HttpStatus.OK);

    }

}