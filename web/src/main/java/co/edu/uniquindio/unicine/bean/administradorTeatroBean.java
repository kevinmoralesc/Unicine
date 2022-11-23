package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.AdministradorTeatro;
import co.edu.uniquindio.unicine.entidades.Horario;
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
public class administradorTeatroBean {

    @Getter
    @Setter
    private AdministradorTeatro administradorTeatro;

    @Autowired
    private AdminServicio adminServicio;
    @Getter @Setter
    private List<AdministradorTeatro> administradoresTeatros;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String correo;

    @Getter
    @Setter
    private String password;

    private boolean editar;
    @Getter @Setter
    private List<AdministradorTeatro> adminisSeleccionados;

    @PostConstruct
    public void inicializar(){
        administradorTeatro = new AdministradorTeatro();
        editar = false;
        administradoresTeatros = adminServicio.listarAdminsTeatro();
        adminisSeleccionados = new ArrayList<>();

    }

    public void crearAdminTeatro(){
        try {
            if(!editar) {
                //Nos ayuda para actualizar las listas
                AdministradorTeatro administradorTeatro1 = adminServicio.crearAdminTeatro(administradorTeatro);
                administradoresTeatros.add(administradorTeatro1);
                administradorTeatro = new AdministradorTeatro();
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Administrador teatro creado correctamente");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);

            }else {
                adminServicio.actualizarAdminTeatro(administradorTeatro);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Administrador teatro actualizado correctamente");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
        }catch (Exception e){
            FacesMessage fm  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarAdminTeatro(){
        try {
            for (AdministradorTeatro at: adminisSeleccionados ){
                adminServicio.eliminarAdminTeatro(at.getCodigo());
                administradoresTeatros.remove(at);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Administrador teatro eliminado correctamente");
                PrimeFaces.current().dialog().showMessageDynamic(fm);
            }
            adminisSeleccionados.clear();

        }catch (Exception e){
            FacesMessage fm  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            PrimeFaces.current().dialog().showMessageDynamic(fm);
        }
    }

    public String getMensajeBorrar(){
        if(administradoresTeatros.size()==0){
            return "Borrar";
        }else{
            return adminisSeleccionados.size() == 1 ? "Borrar 1 elemento": "Borrar "+administradoresTeatros.size()+" elementos";
        }
    }
    public String getMensajeCrear(){
        return editar ? "Editar AdminTeatro" : "Crear AdminTeatro";
    }
    public void seleccionarAdminTeatro(AdministradorTeatro adminTeatroSeleccionado){
        this.administradorTeatro = adminTeatroSeleccionado;
        editar = true;
    }
    public void crearAdminTeatroDialigo(){
        this.administradorTeatro = new AdministradorTeatro();
        editar = false;
    }
}
