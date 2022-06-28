package com.wtfood.servicios;

import com.wtfood.entidades.Usuario;
import com.wtfood.enumeraciones.Rol;
import com.wtfood.errores.ErrorServicio;
import com.wtfood.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional(propagation = Propagation.NESTED)
    public Usuario guardar(String nombre, String apellido, String email, String nickname, String clave, Boolean alta) throws Exception {
        try {
            validacion(nombre, apellido, email, nickname, clave);
            if(!validarEmail(email)){
                throw new ErrorServicio("Ya existe el email");
            }
            if(!validarNickname(nickname)){
                throw new ErrorServicio("Ya existe ese nickname");
            }

            Usuario usuario = new Usuario();

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setNickname(nickname);
            usuario.setClave(clave);
            usuario.setAlta(Boolean.TRUE);
            usuario.setRol(Rol.USUARIO);

            String claveEncriptada = new BCryptPasswordEncoder().encode(clave);
            usuario.setClave(claveEncriptada);

            return usuarioRepositorio.save(usuario);
        } catch(Exception e){
            throw new Exception(e.getMessage());
        }

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
    public void modificar(String id, String nombre, String apellido, String email, String nickname, String clave, Boolean alta) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setNickname(nickname);
            usuario.setClave(clave);
            usuario.setAlta(alta);

            usuarioRepositorio.save(usuario);
        }
    }

    public void validacion(String nombre, String apellido, String email, String nickname, String clave) throws ErrorServicio {
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 3) {
            throw new ErrorServicio("El nombre no puede estar vacío y debe contener más de 3 carácteres");
        }
        if (apellido == null || apellido.trim().isEmpty() || apellido.length() < 3) {
            throw new ErrorServicio("El apellido no puede estar vacío y debe contener más de 3 carácteres");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ErrorServicio("El email no puede estar vacío");
        }
        if (nickname == null || email.trim().isEmpty() || nickname.length() < 3) {
            throw new ErrorServicio("El nombre de usuario no puede estar vacío y debe contener más de 3 carácteres");
        }
        if (clave == null || clave.trim().isEmpty() || clave.length() < 6) {
            throw new ErrorServicio("La clave no puede estar vacía y debe contener 6 caracteres o más");
        }

    }

    private boolean validarNickname(String nickname) {
        Usuario usuario = usuarioRepositorio.encontrarPorNickname(nickname);
        if (usuario == null) {
            return true;
        }
        return false;
    }

    private boolean validarEmail(String email) {
        Usuario usuario = usuarioRepositorio.encontrarPorEmail(email);
        if (usuario == null) {
            return true;
        }
        return false;
    }

    public Usuario credencialesValidas(String email, String clave)  throws ErrorServicio{
        try{
            Usuario usuario = usuarioRepositorio.encontrarPorEmail(email);
            if (usuario == null) {
            throw new ErrorServicio("Email inválido");
        }
            return usuario;
        }catch(Exception e){
            throw new ErrorServicio(e.getMessage());
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.encontrarPorEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getEmail(), usuario.getClave(), permisos);
            return user;

        } else {
            return null;
        }
    }

}
