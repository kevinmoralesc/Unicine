package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.AdministradorTeatro;
import co.edu.uniquindio.unicine.entidades.Ciudad;
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
    private List<AdministradorTeatro> administradoresTeatros;
    @Getter @Setter
    private List<Teatro> teatrosSeleccionados;
    @PostConstruct
    public void inicializar(){
        teatro = new Teatro();
        editar = false;
        ciudades = adminServicio.listarCiudades();
        teatros = adminTeatroServicio.listarTeatros();
        teatrosSeleccionados = new ArrayList<>();
        administradoresTeatros = adminServicio.listarAdminsTeatro();
    }

    public void crearTeatro(){
        try {
            if(!editar) {
                //Esto debe borrar cuando se implemente la sesion

                AdministradorTeatro administradorTeatro = adminServicio.obtenerAdminTeatro(teatro.getAdministrador().getCodigo());
                boolean centinela = false;
                for (Teatro teatro1 : teatros )

                    if(administradorTeatro.getCodigo() == teatro1.getAdministrador().getCodigo()  )
                         centinela = true;
                     {

                        if(centinela==false) {
                            //Nos ayuda para actualizar las listas
                            Teatro registro = adminTeatroServicio.crearTeatro(teatro);
                            teatros.add(registro);
                            teatro = new Teatro();
                            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Teatro creado correctamente");
                            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
                        }else {
                            FacesMessage fm  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "Elija un administradorTeatro sin teatro ");
                            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
                        }
                }

            }else {
                adminTeatroServicio.actualizarTeatro(teatro);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Teatro actualizado correctamente");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
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
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Teatro eliminado correctamente");
                PrimeFaces.current().dialog().showMessageDynamic(fm);
            }
            teatrosSeleccionados.clear();

        }catch (Exception e){
            FacesMessage fm  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            PrimeFaces.current().dialog().showMessageDynamic(fm);
        }
    }

    public String getMensajeBorrar(){
        if(teatrosSeleccionados.size()==0){
            return "Borrar";
        }else{
            return teatrosSeleccionados.size() == 1 ? "Borrar 1 elemento": "Borrar "+teatrosSeleccionados.size()+" elementos";
        }
    }
    public String getMensajeCrear(){
        return editar ? "Editar Teatro" : "Crear Teatro";
    }
    public void seleccionarTeatro(Teatro teatroSeleccionado){
        this.teatro = teatroSeleccionado;
        editar = true;
    }
    public void crearTeatroDialigo(){
        this.teatro = new Teatro();
        editar = false;
    }
}
