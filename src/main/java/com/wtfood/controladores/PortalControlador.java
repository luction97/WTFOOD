package com.wtfood.controladores;

import com.wtfood.errores.ErrorServicio;
import com.wtfood.servicios.RecetaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortalControlador {

    @Autowired
    RecetaServicio recetaServicio;
    
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/loginRegistro")
    public String loginRegistro() {
        return "loginRegistro";
    }

    @GetMapping("/modificarUsuario")
    public String modificarUsuario() {
        return "modificarUsuario";
    }

    @GetMapping("/paginaPrincipal")
    public String paginaPrincipal() {
        return "paginaPrincipal";
    }
@GetMapping("/team")
    public String team() {
        return "team";
    }
    
    @GetMapping("/paginaPrincipal")
    public String mostrarRecetas(ModelMap modelo) {

        modelo.addAttribute("receta", recetaServicio.consultarTodas());

        return "paginaPrincipal";
    }
}
