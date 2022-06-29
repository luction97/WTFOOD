package com.wtfood.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortalControlador {

    @GetMapping("/")
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
}
