package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
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
import java.io.Serializable;
import java.util.*;

@Component
@ViewScoped
public class PeliculaBean implements Serializable {

    @Getter @Setter
    private Pelicula pelicula;
    @Getter @Setter
    private List<EstadoPelicula> estadoPeliculas;
    private Map<String, String> imagenes;

    @Getter @Setter
    private List<Pelicula> peliculas;

    @Autowired
    private CloudinaryServicio cloudinaryServicio;
    @Autowired
    private AdminServicio adminServicio;


    @Getter @Setter
    private List<Genero> generos;

    @Getter
    @Setter
    private List<Pelicula> peliculasSeleccionadas;

    private Boolean editar;


    @PostConstruct
    public void init(){
        editar = false;
        pelicula = new Pelicula();
        generos = Arrays.asList(Genero.values());
        estadoPeliculas = Arrays.asList(EstadoPelicula.values());
        peliculas = adminServicio.listarPeliculas();
        imagenes = new HashMap<>();
        peliculasSeleccionadas = new ArrayList<>();
    }

    public void crearPelicula(){
        try{

            if(!editar) {
                if (!imagenes.isEmpty()) {
                    pelicula.setImagenes(imagenes);
                    pelicula.setEstado(EstadoPelicula.CARTELERA);
                    adminServicio.crearPelicula(pelicula);
                    peliculas.add(pelicula);

                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Pelicula creada correctamente");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);

                } else {
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "Es necesario subir al menos una imagen");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);

                }
            }else{
                adminServicio.actualizarPelicula(pelicula);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Pelicula actualizado correctamente");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
        }catch (Exception e){

            FacesMessage fm = new FacesMessage( FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage()   );
            FacesContext.getCurrentInstance().addMessage("mensaje_Bean",fm);
        }

    }


    public void eliminarPelicula() {
        try {
            for (Pelicula peli: peliculasSeleccionadas) {
                adminServicio.eliminarPelicula(peli.getCodigo());
                peliculas.remove(peli);
            }
            peliculasSeleccionadas.clear();
            peliculas = adminServicio.listarPeliculas();
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Pelicula eliminada");
            PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "No se puede eliminar la pelicula porque está asociada a otro objeto");
            PrimeFaces.current().dialog().showMessageDynamic(facesMsg);
        }
    }

    public void subirImagen(FileUploadEvent event){

        try {
            UploadedFile imagen = event.getFile();
            File imagenFile = convertirUploadedFile(imagen);
            Map resultado = cloudinaryServicio.subirImagen(imagenFile, "peliculas");
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
        if (peliculasSeleccionadas.size() == 0) {
            return "Borrar";
        } else {
            return peliculasSeleccionadas.size() == 1 ? "Borrar 1 elemento" : "Borrar " + peliculasSeleccionadas.size() + " elementos";
        }
    }

    public String getMensajeCrear(){
        return editar ? "Editar Pelicula" : "Crear Pelicula";
    }

    public String getMensaje2Crear() {
        return editar ? "Actualizar" : "Crear";
    }

    public void botonAgregarEditar() {
        this.pelicula = new Pelicula();
        editar = false;
    }

    public void seleccionarSPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
        editar = true;
    }

}
