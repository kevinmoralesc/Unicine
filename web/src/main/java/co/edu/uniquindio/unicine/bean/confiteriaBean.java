package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.CloudinaryServicio;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
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
import java.util.*;

@Component
@ViewScoped
public class confiteriaBean {

    @Getter
    @Setter
    private Confiteria confiteria;
    private Map<String, String> imagenes;

    @Getter @Setter
    private List<Confiteria> confiterias;

    @Getter @Setter
    private Double precio;

    @Autowired
    private CloudinaryServicio cloudinaryServicio;
    @Autowired
    private AdminServicio adminServicio;

    @Getter
    @Setter
    private List<Confiteria> confiteriasSeleccionadas;

    private Boolean editar;


    @PostConstruct
    public void init(){
        editar = false;
        confiteria = new Confiteria();
        confiterias = adminServicio.listarConfiteria();
        imagenes = new HashMap<>();
        confiteriasSeleccionadas = new ArrayList<>();
    }

    public void crearConfiteria(){
        try{
            if(!editar) {
                if (!imagenes.isEmpty()) {
                    confiteria.setImagen(imagenes);
                    adminServicio.crearConfiteria(confiteria);
                    confiterias.add(confiteria);
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Confiteria creada correctamente");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);

                } else {
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "Es necesario subir al menos una imagen");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);

                }
            }else{
                adminServicio.actualizarConfiteria(confiteria);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Confiteria actualizado correctamente");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        }catch (Exception e){

            FacesMessage fm = new FacesMessage( FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage()   );
            FacesContext.getCurrentInstance().addMessage("mensaje_Bean",fm);
        }

    }


    public void eliminarConfiteria() {
        try {
            for (Confiteria confite: confiteriasSeleccionadas) {
                adminServicio.eliminarPelicula(confite.getCodigo());
                confiterias.remove(confite);
            }
            confiteriasSeleccionadas.clear();
            confiterias = adminServicio.listarConfiteria();
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Confiteria eliminada");
            PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "No se puede eliminar la confiteria porque está asociada a otro objeto");
            PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
        }
    }

    public void subirImagen(FileUploadEvent event){

        try {
            UploadedFile imagen = event.getFile();
            File imagenFile = convertirUploadedFile(imagen);
            Map resultado = cloudinaryServicio.subirImagen(imagenFile, "confiterias");
            imagenes.put(resultado.get("public_id").toString(),resultado.get("url").toString());

        }catch (Exception e){
            FacesMessage fm = new FacesMessage( FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage()   );
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

    public String getTextoBtnBorrar() {
        if (confiteriasSeleccionadas.size() == 0) {
            return "Borrar";
        } else {
            return confiteriasSeleccionadas.size() == 1 ? "Borrar 1 elemento" : "Borrar " + confiteriasSeleccionadas.size() + " elementos";
        }
    }

    public String getMensajeCrear() {
        return editar ? "Actualizar confiteria" : "Crear confiteria";
    }

    public String getMensaje2Crear() {
        return editar ? "Actualizar" : "Crear";
    }

    public void botonAgregarEditar() {
        this.confiteria = new Confiteria();
        editar = false;
    }

    public void seleccionarConfiteria(Confiteria confiteria) {
        this.confiteria = confiteria;
        editar = true;
    }



}
