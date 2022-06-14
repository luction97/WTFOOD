package com.wtfood.controladores;

import com.wtfood.errores.ErrorServicio;
import com.wtfood.servicios.IngredienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ingrediente")
public class IngredienteControlador {

    @Autowired
    IngredienteServicio ingredienteServicio;
    
    @GetMapping("")
    public String ingrediente() {
        return "ingrediente";
    }
    
    @PostMapping("")
    public String ingresar(ModelMap modelo, @RequestParam String nombre, String agregar){
       
        try{
            ingredienteServicio.ingresar(nombre);
            
        }catch(Exception e){
            e.getMessage();
        }
        return "ingrediente";
    }
    
}
