
package com.wtfood.controladores;

import com.wtfood.entidades.Foto;
import com.wtfood.entidades.Ingrediente;
import com.wtfood.entidades.Receta;
import com.wtfood.entidades.Usuario;
import com.wtfood.errores.ErrorServicio;
import com.wtfood.servicios.RecetaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("receta")
public class RecetaControlador {
    
    @Autowired
    private RecetaServicio recetaServicio;
    
    @GetMapping("/receta")
    public String listarRecetas(ModelMap modelo) throws ErrorServicio {
        List<Receta> recetas = recetaServicio.listarRecetas();
        if(recetas.isEmpty()) throw new ErrorServicio("No se ah encontrado ninguna receta.");
        modelo.addAttribute("recetas", recetas);
        return "receta";
    }
    
    @PostMapping("/receta")
    public String registrarReceta(ModelMap modelo, @RequestParam String nombre, @RequestParam Integer calificaciones, @RequestParam Integer cantidadIngredientes, @RequestParam List<Ingrediente> ingredientes, @RequestParam Usuario usuario, @RequestParam Foto foto) throws ErrorServicio {
        try {
            recetaServicio.registrarReceta(nombre, calificaciones, cantidadIngredientes, ingredientes, usuario, foto);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("calificaciones", calificaciones);
            modelo.put("cantidadIngredientes", cantidadIngredientes);
            modelo.addAttribute("ingredientes", ingredientes);
            modelo.put("usuario", usuario);
            modelo.put("foto", foto);
            
            return "receta";
        }
        modelo.put("titulo", "Bienvenido a Gestion de Receta.");
        modelo.put("descripcion", "La carga de datos fue hecha de manera satifactoria.");
        return "exito";
    }
    
    @GetMapping("/modificarReceta")
    public String formularioModificar(ModelMap modelo) throws ErrorServicio {
        modelo.addAttribute("recetas", recetaServicio.listarRecetas()); 
        return "modificarReceta"; 
    }
    
    @PostMapping("/modificarReceta")
    public String modificarReceta(ModelMap modelo, @RequestParam String id, @RequestParam String nombre, @RequestParam Integer calificaciones, @RequestParam Integer cantidadIngredientes, @RequestParam List<Ingrediente> ingredientes, @RequestParam Usuario usuario, @RequestParam Foto foto) throws Exception {
        
        try {
            recetaServicio.modificarReceta(id, nombre, calificaciones, cantidadIngredientes, ingredientes, usuario, foto);
            modelo.put("exito", "Receta modificada con éxito");
        } catch (Exception e) {
            modelo.put("error", "Error al modificar la Receta");
        }
        
        return "modificarReceta";
    }
    
}
