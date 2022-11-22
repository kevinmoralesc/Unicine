package co.edu.uniquindio.unicine.converter;

import co.edu.uniquindio.unicine.entidades.AdministradorTeatro;
import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Component
public class adminTeatroConverter implements Converter<AdministradorTeatro> {

    @Autowired
    private AdminServicio administradorServicio;

    @Override
    public AdministradorTeatro getAsObject(FacesContext context, UIComponent component, String value) {

        AdministradorTeatro administradorTeatro ;

        try {
            administradorTeatro = administradorServicio.obtenerAdminTeatro(Integer.parseInt(value));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return administradorTeatro;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, AdministradorTeatro value) {
        if(value != null){ return ""+value.getCodigo();}
        return "";
    }

}
