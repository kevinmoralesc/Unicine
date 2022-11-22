package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class detallePeliculaBean implements Serializable {
    @Value("#{param['pelicula_id']}")
    private String peliculaCodigo;

    @Autowired
    AdminServicio administradorServicio;

    @Autowired
    AdminTeatroServicio administradorTeatroServicio;

    @Getter @Setter
    private Pelicula pelicula;

    @Getter @Setter
    private List<Funcion> funciones;

    @Getter @Setter
    private List<Teatro> teatros;

    @Getter @Setter
    private List<Ciudad> ciudades;

    @Getter @Setter
    private List<LocalDate> fechas;

    @Getter @Setter
    private List<String> diasSemana;

    @Getter @Setter
    private Ciudad ciudad;

    @PostConstruct
    public void init(){
        funciones  = new ArrayList<>();
        fechas     = new ArrayList<>();
        teatros    = new ArrayList<>();
        inicializarDiasSemana();

        if(peliculaCodigo != null && !peliculaCodigo.isEmpty()){
            try {
                pelicula = administradorServicio.obtenerPelicula(Integer.parseInt(peliculaCodigo));
                ciudades = administradorServicio.listarCiudades();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Inicializa dias de la semana para mostrar en la ui
     */
    private void inicializarDiasSemana() {
        diasSemana = new ArrayList<>();
        diasSemana.add("Lunes");
        diasSemana.add("Martes");
        diasSemana.add("Miércoles");
        diasSemana.add("Jueves");
        diasSemana.add("Viernes");
        diasSemana.add("Sábado");
        diasSemana.add("Domingo");
    }

    /**
     * Lista las funciones que se van a mostrar en la ui de detallePelicula
     */
    public void listarFunciones(){
        if(ciudad != null){
            try {
                funciones = administradorTeatroServicio.obtenerFuncionesCiudad(ciudad.getCodigo(), pelicula.getCodigo());
                establecerFechasFunciones();
                establecerTeatrosFunciones();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Establece teatros de las funciones para agregarlas a
     * la lista de teatros y mostrarlas en la ui
     */
    private void establecerTeatrosFunciones() {
        teatros.clear();
        for (Funcion f: funciones) {
            if (f != null) {
                if(!verificarTeatro(f.getSala().getTeatro().getCodigo())) {
                    teatros.add(f.getSala().getTeatro());
                }
            }
        }
    }

    private boolean verificarTeatro(Integer codigo) {
        for (Teatro t: teatros) {
            if(t != null){
                if(t.getCodigo() == codigo){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Establece fechas de las funciones para agregarlas a
     * la lista de fechas y mostrarlas en la ui
     */
    private void establecerFechasFunciones() {
        fechas.clear();
        for (Funcion f: funciones) {
            if(f != null){
                fechas.add(f.getHorario().getFechaInicio());
            }
        }
    }

    /**
     * Redirección al proceso de compra
     * @param funcionCodigo
     * @return String redirección
     */
    public String redirectToProcesoCompra(String funcionCodigo){
        if(!funcionCodigo.isEmpty())
            return "/cliente/proceso_compra?faces-redirect=true&amp;funcion_codigo=" + funcionCodigo;
        return "";
    }
}
