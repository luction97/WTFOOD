
package com.wtfood.entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Receta {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private Integer calificaciones;
    private Integer cantidadIngredientes;
    @OneToOne
    private Usuario usuario;
    @OneToOne
    private Foto foto;
    
    private ArrayList<String> pasoAPaso;
    
    @ManyToMany
    private List<Ingrediente> ingredientes;

}
