
package com.wtfood.controladores;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortalControlador {
    
    
//  Página principal:  Login-Registro
     @GetMapping("/")
    public String index() {
        return "index.html";
    }
    
   
    
}

