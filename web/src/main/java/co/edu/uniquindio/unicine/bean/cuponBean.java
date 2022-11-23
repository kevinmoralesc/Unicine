package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.AdministradorTeatro;
import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Cupon;
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
public class cuponBean {

    @Getter
    @Setter
    private Cupon cupon;
    @Autowired
    private AdminTeatroServicio adminTeatroServicio;
    @Autowired
    private AdminServicio adminServicio;
    @Getter @Setter
    private List<Cupon> cupones;
    private boolean editar;
    @Getter @Setter
    private List<Ciudad> ciudades;

    @Getter
    @Setter
    private Float descuento;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private String criterio;

    @Getter
    @Setter
    private LocalDate fechaVencimiento;

    @Getter @Setter
    private List<Cupon> cuponesSeleccionados;

    @PostConstruct
    public void inicializar(){
        cupon = new Cupon();
        editar = false;
        cupones = adminServicio.listarCupones();
        cuponesSeleccionados = new ArrayList<>();
    }

    public void crearCupon(){
        try {
            if(!editar) {
                        Cupon cupon1 = adminServicio.crearCupon(cupon);
                        cupones.add(cupon);
                        cupon = new Cupon();
                        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Cupon creado correctamente");
                        FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);


            }else {
                adminServicio.actualizarCupon(cupon);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Cupon actualizado correctamente");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
        }catch (Exception e){
            FacesMessage fm  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarCupon(){
        try {
            for (Cupon c: cuponesSeleccionados ){
                adminTeatroServicio.eliminarTeatro(c.getCodigo());
                cupones.remove(c);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Cupon eliminado correctamente");
                PrimeFaces.current().dialog().showMessageDynamic(fm);
            }
            cuponesSeleccionados.clear();

        }catch (Exception e){
            FacesMessage fm  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            PrimeFaces.current().dialog().showMessageDynamic(fm);
        }
    }

    public String getMensajeBorrar(){
        if(cuponesSeleccionados.size()==0){
            return "Borrar";
        }else{
            return cuponesSeleccionados.size() == 1 ? "Borrar 1 elemento": "Borrar "+cuponesSeleccionados.size()+" elementos";
        }
    }
    public String getMensajeCrear(){
        return editar ? "Editar Cupon" : "Crear Cupon";
    }
    public void seleccionarCupon(Cupon cuponSeleccionado){
        this.cupon = cuponSeleccionado;
        editar = true;
    }
    public void crearCuponDialigo(){
        this.cupon = new Cupon();
        editar = false;
    }


}
