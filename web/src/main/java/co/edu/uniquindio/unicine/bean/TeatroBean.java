package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.AdministradorTeatro;
import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class TeatroBean implements Serializable {

    @Getter @Setter
    private Teatro teatro;
    @Autowired
    private AdminTeatroServicio adminTeatroServicio;
    @Autowired
    private AdminServicio adminServicio;
    @Getter @Setter
    private List<Teatro> teatros;
    private boolean editar;
    @Getter @Setter
    private List<Ciudad> ciudades;
    @Getter @Setter
    private List<Teatro> teatrosSeleccionados;
    @PostConstruct
    public void inicializar(){
        teatro = new Teatro();
        editar = false;
        ciudades = adminServicio.listarCiudades();
        teatros = adminTeatroServicio.listarTeatros();
        teatrosSeleccionados = new ArrayList<>();
    }

    public void crearTeatro(){
        try {
            //Esto debe borrar cuando se implemente la sesion
            AdministradorTeatro administradorTeatro = adminServicio.obtenerAdminTeatro(1);
            teatro.setAdministrador(administradorTeatro);
            //Nos ayuda para actualizar las listas
            Teatro registro = adminTeatroServicio.crearTeatro(teatro);
            teatros.add(registro);
            teatro = new Teatro();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Teatro creado correctamente");
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }catch (Exception e){
            FacesMessage fm  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarTeatro(){
        try {
            for (Teatro t: teatrosSeleccionados ){
                adminTeatroServicio.eliminarTeatro(t.getCodigo());
                teatros.remove(t);
            }
            teatrosSeleccionados.clear();
        }catch (Exception e){
            FacesMessage fm  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public String getMensajeBorrar(){
        if(teatrosSeleccionados.size()==0){
            return "Borrar";
        }else{
            return teatrosSeleccionados.size() == 1 ? "Borrar 1 elemento": "Borrar "+teatrosSeleccionados.size()+" elementos";
        }
    }
    public void seleccionarTeatro(Teatro teatroSeleccionado){
        this.teatro = teatroSeleccionado;
        editar = true;
    }
}
