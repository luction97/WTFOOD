/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wtfood.configuracion;

import com.wtfood.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Vanesa
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadConfiguracion extends WebSecurityConfigurerAdapter {
    
    @Autowired
    public UsuarioServicio usuarioServicio;
    
    @Autowired
    public void globalConfigure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServicio)
               .passwordEncoder(new BCryptPasswordEncoder());
    }
    
    protected void configure (HttpSecurity http) throws Exception{
        http.authorizeRequests()
              .antMatchers("/admin/'")
              .hasRole("ADMINISTRADOR")
              .antMatchers("/css/'", "/js/'", "/img/'" , "/'").permitAll()
              .and()
              .formLogin()
                .loginPage("/loginRegistro")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("clave")
                .defaultSuccessUrl("/paginaPrincipal")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .permitAll()
                .and()
                .csrf().disable();
    }
            
    
}
