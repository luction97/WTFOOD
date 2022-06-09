
package com.wtfood.controladores;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortalControlador {
    
    
     @GetMapping("/")
    public String index() {
        return "loginRegistro";
    }
    
//        @GetMapping("/loginRegistro")
//    public String loginRegistro() {
//        return "loginRegistro.html";
//    }
    
     
    
}

