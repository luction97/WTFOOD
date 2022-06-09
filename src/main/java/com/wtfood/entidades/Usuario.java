package com.wtfood.entidades;

import com.wtfood.enumeraciones.Rol;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Usuario{
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String clave;
    
   @Column(nullable =false)
    private Boolean alta;
    
    @OneToMany
    private List<Receta> receta; 
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    @Override
    public String toString(){
        return"Usuario(" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido +  ", email=" + email + ", nickname=" + nickname + ", clave=" + clave + ", rol=" + rol;
    }
}



