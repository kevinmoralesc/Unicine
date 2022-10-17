package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServicioImpl implements AdminServicio {

    private final CiudadRepo ciudadRepo;
    private final PeliculaRepo peliculaRepo;
    private final CuponRepo cuponRepo;
    private final ConfiteriaRepo confiteriaRepo;
    private final AdministradorTeatroRepo administradorTeatroRepo;

    public AdminServicioImpl(CiudadRepo ciudadRepo, PeliculaRepo peliculaRepo, CuponRepo cuponRepo, ConfiteriaRepo confiteriaRepo, AdministradorTeatroRepo administradorTeatroRepo) {
        this.ciudadRepo = ciudadRepo;
        this.peliculaRepo = peliculaRepo;
        this.cuponRepo = cuponRepo;
        this.confiteriaRepo = confiteriaRepo;
        this.administradorTeatroRepo = administradorTeatroRepo;
    }

    @Override
    public Ciudad crearCiudad(Ciudad ciudad) {
        return ciudadRepo.save(ciudad);
    }

    @Override
    public Ciudad obtenerCiudad(Integer codigo) throws Exception {

        Optional<Ciudad> ciudad = ciudadRepo.findById(codigo);

        if(ciudad.isEmpty()){

            throw new Exception("No hay una ciudad con ese codigo");

        }

        return ciudad.get();

    }

    @Override
    public Pelicula crearPelicula(Pelicula pelicula) {
        return null;
    }

    @Override
    public Pelicula ActualizarPelicula(Pelicula pelicula) throws Exception {
        return null;
    }

    @Override
    public void eliminarPelicula(Integer codigo) throws Exception {

    }

    @Override
    public List<Pelicula> listarPeliculas() {
        return null;
    }

    @Override
    public Pelicula obtenerPelicula(Integer codigo) throws Exception {
        return null;
    }

    @Override
    public Cupon crearCupon(Cupon cupon) {
        return null;
    }

    @Override
    public Cupon actualizarCupon(Cupon cupon) throws Exception {
        return null;
    }

    @Override
    public void eliminarCupon(Integer codigo) throws Exception {

    }

    @Override
    public List<Cupon> listarCupones() {
        return null;
    }

    @Override
    public Cupon obtenerCupon(Integer codigo) throws Exception {
        return null;
    }

    @Override
    public Confiteria crearConfiteria(Confiteria confiteria) {
        return null;
    }

    @Override
    public Confiteria actualizarConfiteria(Confiteria confiteria) throws Exception {
        return null;
    }

    @Override
    public void eliminarConfiteria(Integer codigo) throws Exception {

    }

    @Override
    public List<Confiteria> listarConfiteria() {
        return null;
    }

    @Override
    public Confiteria obtenerConfiteria(Integer codigo) throws Exception {
        return null;
    }

    @Override
    public AdministradorTeatro crearAdminTeatro(AdministradorTeatro administradorTeatro) {
        return null;
    }

    @Override
    public AdministradorTeatro actualizarAdminTeatro(AdministradorTeatro administradorTeatro) throws Exception {
        return null;
    }

    @Override
    public void eliminarAdminTeatro(Integer codigo) throws Exception {

    }

    @Override
    public List<AdministradorTeatro> listarAdminsTeatro() {
        return null;
    }

    @Override
    public AdministradorTeatro obtenerAdminTeatro(Integer codigo) throws Exception {
        return null;
    }
}
