package com.wtfood.entidades;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    private String clave;
    @Column(nullable = false)
    private String email;
   @Column(nullable =false)
    private Boolean alta;
    
    @OneToMany
    private List<Receta> receta; 
    
}
