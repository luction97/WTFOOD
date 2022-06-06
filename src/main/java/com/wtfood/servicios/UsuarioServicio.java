package com.wtfood.servicios;

import com.wtfood.entidades.Usuario;
import com.wtfood.errores.ErrorServicio;
import com.wtfood.repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional(propagation = Propagation.NESTED)
    public Usuario registrar(String nombre, String clave, String email, Boolean alta) throws Exception {
        validacion(nombre, clave, email);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setClave(clave);
        usuario.setEmail(email);
        usuario.setAlta(true);

        return usuarioRepositorio.save(usuario);

    }

    @Transactional(propagation = Propagation.NESTED)
    public void eliminar(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuarioRepositorio.delete(usuario);
        } else {
            throw new ErrorServicio("No se encontró id de usuario");
        }
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarPorNombre(String nombre) throws ErrorServicio {
        try {
            return usuarioRepositorio.buscarPorNombre(nombre);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void modificar(String id , String nombre, String clave, String email, Boolean alta) throws ErrorServicio {
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setClave(clave);
            usuario.setEmail(email);
            usuario.setAlta(alta);
                    
            usuarioRepositorio.save(usuario);
        }
    }

    public void validacion(String nombre, String clave, String email) throws ErrorServicio {
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 3) {
            throw new ErrorServicio("El nombre no puede estar vacío y debe contener 3 caracteres o más");
        }
        if (clave == null || clave.trim().isEmpty() || clave.length() < 6) {
            throw new ErrorServicio("La clave no puede estar vacía y debe contener 6 caracteres o más");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ErrorServicio("El email no puede estar vacío");
        }
    }

}