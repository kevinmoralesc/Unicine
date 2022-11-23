package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.AdministradorTeatro;
import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class horarioBean {

    @Getter
    @Setter
    private Horario horario;
    @Autowired
    private AdminTeatroServicio adminTeatroServicio;
    @Autowired
    private AdminServicio adminServicio;
    @Getter @Setter
    private List<Horario> horarios;

    @Getter
    @Setter
    private String dia;

    @Getter
    @Setter
    private String hora;

    @Getter
    @Setter
    private LocalDate fechaInicio;

    @Getter
    @Setter
    private LocalDate fechaFin;


    private boolean editar;
    @Getter @Setter
    private List<Horario> horariosSeleccionados;

    @PostConstruct
    public void inicializar(){
        horario = new Horario();
        editar = false;
        horarios = adminTeatroServicio.listarHorarios();
        horariosSeleccionados = new ArrayList<>();

    }

    public void crearHorario(){
        try {
            if(!editar) {
                        //Nos ayuda para actualizar las listas
                        Horario horario1 = adminTeatroServicio.crearHorario(horario);
                        horarios.add(horario1);
                        horario = new Horario();
                        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Teatro creado correctamente");
                        FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);

            }else {
                adminTeatroServicio.actualizarHorario(horario);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Horario actualizado correctamente");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
        }catch (Exception e){
            FacesMessage fm  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarHorario(){
        try {
            for (Horario h: horariosSeleccionados ){
                adminTeatroServicio.eliminarTeatro(h.getCodigo());
                horarios.remove(h);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Horario eliminado correctamente");
                PrimeFaces.current().dialog().showMessageDynamic(fm);
            }
            horariosSeleccionados.clear();

        }catch (Exception e){
            FacesMessage fm  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            PrimeFaces.current().dialog().showMessageDynamic(fm);
        }
    }

    public String getMensajeBorrar(){
        if(horarios.size()==0){
            return "Borrar";
        }else{
            return horariosSeleccionados.size() == 1 ? "Borrar 1 elemento": "Borrar "+horariosSeleccionados.size()+" elementos";
        }
    }
    public String getMensajeCrear(){
        return editar ? "Editar Horario" : "Crear Horario";
    }
    public void seleccionarHorario(Horario horarioSeleccionado){
        this.horario = horarioSeleccionado;
        editar = true;
    }
    public void crearHorarioDialigo(){
        this.horario = new Horario();
        editar = false;
    }
}

