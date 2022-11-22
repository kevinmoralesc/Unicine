package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class FuncionBean {


    @Autowired
    private AdminTeatroServicio administradorTeatroServicio;

    @Autowired
    private AdminServicio adminServicio;

    @Getter
    @Setter
    private Funcion funcion;

    @Getter
    @Setter
    private List<Funcion> funciones;

    @Getter
    @Setter
    private List<Funcion> funcionesSeleccionadas;

    @Getter @Setter
    private List<Horario> horarios;

    @Getter @Setter
    private List<Pelicula> peliculas;

    @Getter @Setter
    private List<Sala> salas;

    private Boolean editar;

    public FuncionBean(AdminTeatroServicio administradorTeatroServicio) {
        this.administradorTeatroServicio = administradorTeatroServicio;
    }

    @PostConstruct
    public void init() {
        editar = false;
        funcion = new Funcion();
        horarios = administradorTeatroServicio.listarHorarios();
        peliculas = adminServicio.listarPeliculas();
        funcionesSeleccionadas = new ArrayList<>();
        funciones = administradorTeatroServicio.listarFunciones();
        salas = administradorTeatroServicio.listarSalas();
    }

    public void crearFuncion() {
        try {
            if (!editar) {
                Funcion funcionc = administradorTeatroServicio.crearFuncion(funcion);
                funciones.add(funcion);
                funcion = new Funcion();
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Registro de la funcion exitoso");
                PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
            } else {
                administradorTeatroServicio.crearFuncion(funcion);
                funciones = administradorTeatroServicio.listarFunciones();
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "funcion actualizada");
                PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
            }

        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", e.getMessage());
            PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
        }
    }

    public void eliminarFuncion() {
        try {
            for (Funcion funcion1: funcionesSeleccionadas) {
                administradorTeatroServicio.eliminarSala(funcion1.getCodigo());
                funciones.remove(funcion1);
            }
            funcionesSeleccionadas.clear();
            funciones = administradorTeatroServicio.listarFunciones();
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Funcion eliminada");
            PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "No se puede eliminar la funcion porque está asociada a otro objeto");
            PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
        }
    }

    public String getMensajeBorrar(){
        if(funcionesSeleccionadas.size()==0){
            return "Borrar";
        }else{
            return funcionesSeleccionadas.size() == 1 ? "Borrar 1 elemento": "Borrar "+funcionesSeleccionadas.size()+" elementos";
        }
    }


    public String getMensajeCrear() {
        return editar ? "Actualizar funcion" : "Crear funcion";
    }

    public String getMensaje2Crear() {
        return editar ? "Actualizar" : "Crear";
    }

    public void botonAgregarEditar() {
        this.funcion = new Funcion();
        editar = false;
    }

    public void seleccionarFuncion(Funcion funcion) {
        this.funcion = funcion;
        editar = true;
    }
}
