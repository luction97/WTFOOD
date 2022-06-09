package com.wtfood.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ingrediente")
public class IngredienteControlador {

    @GetMapping("/")
    public String ingrediente() {
        return "ingrediente";
    }
}
