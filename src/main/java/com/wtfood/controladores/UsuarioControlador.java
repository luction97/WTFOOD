/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wtfood.controladores;

import com.wtfood.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Vanesa
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @PostMapping("/registro")
    private String guardar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String nickname, @RequestParam String clave){
        try{
            usuarioServicio.guardar(nombre, apellido, email, nickname, clave, true);
            modelo.put("Exito", "Usuario guardado con éxito");
        }catch(Exception e){
            modelo.put("Error", "Error al registrarse");
        }
        return "loginRegistro";
    }
    
    
    
}
