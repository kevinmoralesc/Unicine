package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
@Component
@Scope("session")
public class SeguridadBean implements Serializable {
    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private AdminServicio adminServicio;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Getter @Setter
    private boolean autenticado;

    @Getter @Setter
    private String correo, password;

    @Getter @Setter
    private Persona persona;

    @Getter @Setter
    private String tipoSesion;

    @Getter @Setter
    private Ciudad ciudadSeleccionada;

    @PostConstruct
    public void init(){
        autenticado = false;
    }

    public String iniciarSesionCliente(){

        if (!correo.isEmpty() && !password.isEmpty()){
            try {
                persona = clienteServicio.login(correo, password);
                if(clienteServicio.obtenerCliente(persona.getCodigo()).isEstado() == true) {
                    tipoSesion = "cliente";
                    autenticado = true;
                    return "/index?faces-redirect=true";
                }else{
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Alerta","Por favor active la cuenta");
                    FacesContext.getCurrentInstance().addMessage("login-bean", fm);
                    return null;
                }
            } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Alerta",e.getMessage());
                FacesContext.getCurrentInstance().addMessage("login-bean", fm);
            }
        }else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Alerta","El correo y la contraseña son necesarios");
            FacesContext.getCurrentInstance().addMessage("login-bean", fm);
        }
        return null;
    }

    public String iniciarSesionAdmins(){

        if (!correo.isEmpty() && !password.isEmpty()){
            try {
                persona = adminServicio.loginAdmin(correo,password);
                if (persona == null){
                    persona = adminTeatroServicio.loginAdmin(correo,password);
                    tipoSesion = "admin_teatro";
                }else {
                    tipoSesion="admin";
                }
                autenticado =true;
                return "/index_admin?faces-redirect=true";
            } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Alerta",e.getMessage());
                FacesContext.getCurrentInstance().addMessage("Login-bean", fm);
            }
        }else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Alerta","El correo y la contraseña son necesarios");
            FacesContext.getCurrentInstance().addMessage("Login-bean", fm);
        }
        return null;
    }

    public void seleccionarCiudad(Ciudad ciudad){
        this.ciudadSeleccionada = ciudad;
    }

    public String cerrarSesion(){
        String tipo = tipoSesion;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        if (tipo.equals("cliente")){
            return "/index?faces-redirect=true";
        }
        return "/index?faces-redirect=true";
    }


}
