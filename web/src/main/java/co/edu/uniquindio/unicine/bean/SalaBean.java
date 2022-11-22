package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class SalaBean implements Serializable {

    @Autowired
    private AdminTeatroServicio administradorTeatroServicio;

    @Getter
    @Setter
    private Sala sala;

    @Getter
    @Setter
    private List<Sala> salas;

    @Getter
    @Setter
    private List<Sala> salasSeleccionadas;

    @Getter @Setter
    private List<DistribucionSillas> distribucionSillas;
    private Boolean editar;

    @Getter
    @Setter
    private List<Teatro> teatros;

    public SalaBean(AdminTeatroServicio administradorTeatroServicio) {
        this.administradorTeatroServicio = administradorTeatroServicio;
    }

    @PostConstruct
    public void init() {
        editar = false;
        sala = new Sala();
        teatros = administradorTeatroServicio.listarTeatros();
        salas = administradorTeatroServicio.listarSalas();
        salasSeleccionadas = new ArrayList<>();
        distribucionSillas = administradorTeatroServicio.listarDistribucionSilla();
    }

    public void crearSala() {
        try {
            if (!editar) {
                Sala salac = administradorTeatroServicio.crearSala(sala);
                salas.add(sala);
                sala = new Sala();
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Registro de sala exitoso");
                PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
            } else {
                administradorTeatroServicio.actualizarSala(sala);
                salas = administradorTeatroServicio.listarSalas();
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Sala actualizada");
                PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
            }

        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", e.getMessage());
            PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
        }
    }

    public void eliminarSala() {
        try {
            for (Sala sal: salasSeleccionadas) {
                administradorTeatroServicio.eliminarSala(sal.getCodigo());
                salas.remove(sal);
            }
            salasSeleccionadas.clear();
            salas = administradorTeatroServicio.listarSalas();
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Sala eliminada");
            PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "No se puede eliminar la sala porque está asociada a otro objeto");
            PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
        }
    }

    public String getTextoBtnBorrar() {
        if (salasSeleccionadas.size() == 0) {
            return "Borrar";
        } else {
            return salasSeleccionadas.size() == 1 ? "Borrar 1 elemento" : "Borrar " + salasSeleccionadas.size() + " elementos";
        }
    }

    public String getMensajeCrear() {
        return editar ? "Actualizar sala" : "Crear sala";
    }

    public String getMensaje2Crear() {
        return editar ? "Actualizar" : "Crear";
    }

    public void botonAgregarEditar() {
        this.sala = new Sala();
        editar = false;
    }

    public void seleccionarSala(Sala sala) {
        this.sala = sala;
        editar = true;
    }
}