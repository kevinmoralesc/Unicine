package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import co.edu.uniquindio.unicine.servicios.CloudinaryServicio;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@ViewScoped
public class ClienteBean implements Serializable{
    @Autowired
    private CloudinaryServicio cloudinaryServicio;

    @Getter @Setter
    private LocalDate fechaNacimiento;

    @Getter @Setter
    private Map<String, String> imagenes;

    @Getter @Setter
    private Cliente cliente;

    @Setter @Getter
    private String confirmacionPassword;

    @Autowired
    private ClienteServicio clienteServicio;

    @PostConstruct
    public void init(){
        cliente = new Cliente();
        imagenes = new HashMap<>();
    }
    public void registrarCliente(){
        try {

            if(cliente.getPassword().equals(confirmacionPassword)){
                cliente.setImagenes(imagenes);
                clienteServicio.registrarCliente(cliente);
                System.out.println("funciono");
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Registro Exitoso!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean",fm);
            }else{
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "Las contraseñas no coinciden");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean",fm);
            }
        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean",fm);
        }
    }
    public void subirImagen(FileUploadEvent event){

        try {
            UploadedFile imagen = event.getFile();
            File imagenFile = convertirUploadedFile(imagen);
            Map resultado = cloudinaryServicio.subirImagen(imagenFile, "clientes");
            imagenes.put(resultado.get("public_id").toString(),resultado.get("url").toString());

        }catch (Exception e){
            FacesMessage fm = new FacesMessage( FacesMessage.SEVERITY_ERROR, "Alerta", "fallo subir imagen"   );
            FacesContext.getCurrentInstance().addMessage("mensaje_Bean",fm);
        }
    }

    private File convertirUploadedFile(UploadedFile imagen) throws IOException {
        File file = new File(imagen.getFileName());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imagen.getContent());
        fos.close();
        return file;
    }
}
