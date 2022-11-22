package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.EstadoPelicula;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class InicioBean implements Serializable {

    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private AdminServicio adminServicio;
    @Getter @Setter
    private List<Pelicula> peliculasCartelera;
    @Getter @Setter
    private List<Pelicula> peliculasProximas;
    @Getter @Setter
    private List<Ciudad> ciudades;
    @Getter @Setter
    private Ciudad ciudad;
    @Getter @Setter
    private List<String> imagenes;
    @PostConstruct
    public void inicializar(){
        imagenes = new ArrayList<>();
        imagenes.add("");
        imagenes.add("");
        imagenes.add("");
        peliculasCartelera = new ArrayList<>();
        peliculasProximas = new ArrayList<>();
        ciudades = adminServicio.listarCiudades();
    }
    public void elegirCiudad(){
        if(ciudad!=null){
            peliculasCartelera = adminServicio.listarPeliculasCartelera(ciudad.getCodigo(), EstadoPelicula.CARTELERA);
            peliculasProximas = adminServicio.listarPeliculasProximas(ciudad.getCodigo(),EstadoPelicula.PROXIMAMENTE);
        }
    }
}
