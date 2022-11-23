package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServicioImpl implements AdminServicio {
    @Autowired
    private final AdministradorRepo adminRepo;
    private final CiudadRepo ciudadRepo;
    private final PeliculaRepo peliculaRepo;
    private final CuponRepo cuponRepo;
    private final ConfiteriaRepo confiteriaRepo;
    private final AdministradorTeatroRepo administradorTeatroRepo;
    private final EmailServicio emailServicio;

    public AdminServicioImpl(AdministradorRepo adminRepo, CiudadRepo ciudadRepo, PeliculaRepo peliculaRepo, CuponRepo cuponRepo, ConfiteriaRepo confiteriaRepo, AdministradorTeatroRepo administradorTeatroRepo, EmailServicio emailServicio) {
        this.adminRepo = adminRepo;
        this.ciudadRepo = ciudadRepo;
        this.peliculaRepo = peliculaRepo;
        this.cuponRepo = cuponRepo;
        this.confiteriaRepo = confiteriaRepo;
        this.administradorTeatroRepo = administradorTeatroRepo;
        this.emailServicio = emailServicio;
    }

    //------------------------------------------------- Admin -------------------------------------------------

    @Override
    public Administrador loginAdmin(String correo, String password) throws Exception{
        Administrador admin = adminRepo.findByCorreo(correo).orElse(null);

        if(admin != null){
            StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
            if(!spe.checkPassword(password, admin.getPassword())){
                throw new Exception("La contraseña es incorrecta");
            }
        }
        return admin;
    }

    @Override
    public void recuperarPassword(String correo) throws Exception {

        boolean correoExiste = esRepetidoAdmin(correo);

        if(correoExiste){
            throw new Exception("El correo no esta registrado");
        }
        Optional<Administrador> administrador = adminRepo.findByCorreo(correo);
        emailServicio.enviarEmail("Cambio de contraseña unicine", "Hola, debe ir al siguiente enlace para ingresar la nueva contraseña", (administrador.get()).getCorreo());
    }

    @Override
    public boolean actualizarPassword(Integer codigo, String passwordNueva, String passwordActual) throws Exception {

        Optional<Administrador> guardado = adminRepo.findById(codigo);

        if(guardado.isEmpty()){

            throw new Exception("El administrador no existe");

        }else{
            if(guardado.get().getPassword().equals(passwordActual)) {
                if (guardado.get().getPassword().equals(passwordNueva)) {

                    throw new Exception("Ingrese una contraseña distinta a la actual");

                }
            }else{

                throw new Exception("Ingrese la contraseña correcta");
            }
        }
        guardado.get().setPassword(passwordNueva);
        return true;
    }

    @Override
    public Administrador obtenerAdministrador(Integer codigo) throws Exception {

        Optional<Administrador> administrador = adminRepo.findById(codigo);

        if(administrador.isEmpty()){

            throw new Exception("No hay un administrador con ese codigo");

        }

        return administrador.get();

    }



    private boolean esRepetidoAdmin(String correo){
        return adminRepo.findByCorreo(correo).orElse(null) == null;
    }

    private boolean esRepetidoAdminTeatro(String correo){
        return administradorTeatroRepo.findByCorreo(correo).orElse(null) == null;
    }

    //------------------------------------------- Gestion Ciudad -----------------------------------------------
    @Override
    public Ciudad crearCiudad(Ciudad ciudad) {
        return ciudadRepo.save(ciudad);
    }

    @Override
    public Ciudad actualizarCiudad(Ciudad ciudad) throws Exception {
        Optional<Ciudad> guardada = ciudadRepo.findById(ciudad.getCodigo());

        if(guardada.isEmpty()){

            throw new Exception("La ciudad no existe");

        }

        return  ciudadRepo.save(ciudad);
    }

    @Override
    public void eliminarCiudad(Integer codigo) throws Exception {

        Optional<Ciudad> guardada = ciudadRepo.findById(codigo);

        if(guardada.isEmpty()){

            throw new Exception("La ciudad no existe");

        }

        ciudadRepo.delete(guardada.get());

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
    public List<Ciudad> listarCiudades() {return ciudadRepo.findAll();}

    @Override
    public List<Pelicula> busquePeliculaNombre(String nombre) {
        List<Pelicula> peliculas = new ArrayList<>();
        if(nombre != null){
            peliculas = peliculaRepo.buscarPelicula(nombre);
        }

        return peliculas;
    }

    //--------------------------------------- Gestion Pelicula ------------------------------------------------
    @Override
    public Pelicula crearPelicula(Pelicula pelicula) { return peliculaRepo.save(pelicula); }

    @Override
    public Pelicula actualizarPelicula(Pelicula pelicula) throws Exception {

        Optional<Pelicula> guardada = peliculaRepo.findById(pelicula.getCodigo());

        if(guardada.isEmpty()){

            throw new Exception("La pelicula no existe");

        }

        return peliculaRepo.save(pelicula);
    }

    @Override
    public void eliminarPelicula(Integer codigo) throws Exception {

        Optional<Pelicula> guardada = peliculaRepo.findById(codigo);

        if(guardada.isEmpty()){

            throw new Exception("La pelicula no existe");

        }

        peliculaRepo.delete(guardada.get());

    }

    @Override
    public List<Pelicula> listarPeliculasProximas(Integer codigo, EstadoPelicula estadoPelicula) {
        return peliculaRepo.listarPeliculasEstado(codigo,estadoPelicula);
    }

    @Override
    public List<Pelicula> listarPeliculasCartelera(Integer codigo, EstadoPelicula estadoPelicula) {
        return peliculaRepo.listarPeliculasEstado(codigo,estadoPelicula);
    }

    @Override
    public List<Pelicula> listarPeliculas() {
        return peliculaRepo.findAll();}

    @Override
    public Pelicula obtenerPelicula(Integer codigo) throws Exception {

        Optional<Pelicula> pelicula = peliculaRepo.findById(codigo);

        if(pelicula.isEmpty()){

            throw new Exception("No hay una pelicula con ese codigo");

        }

        return pelicula.get();

    }

    //--------------------------------------- Gestion Cupon ---------------------------------------------------

    @Override
    public Cupon crearCupon(Cupon cupon) { return cuponRepo.save(cupon);}

    @Override
    public Cupon actualizarCupon(Cupon cupon) throws Exception {

        Optional<Cupon> guardado = cuponRepo.findById(cupon.getCodigo());

        if(guardado.isEmpty()){

            throw new Exception("El cupon no existe");

        }

        return cuponRepo.save(cupon);
    }

    @Override
    public void eliminarCupon(Integer codigo) throws Exception {

        Optional<Cupon> guardado = cuponRepo.findById(codigo);

        if(guardado.isEmpty()){

            throw new Exception("El cupon no existe");

        }

        cuponRepo.delete(guardado.get());

    }

    @Override
    public List<Cupon> listarCupones() { return cuponRepo.findAll();}

    @Override
    public Cupon obtenerCupon(Integer codigo) throws Exception {

        Optional<Cupon> cupon = cuponRepo.findById(codigo);

        if(cupon.isEmpty()){

            throw new Exception("No hay un cupon con ese codigo");

        }

        return cupon.get();
    }

    //--------------------------------------- Gestion Confiteria -----------------------------------------------

    @Override
    public Confiteria crearConfiteria(Confiteria confiteria) {
        return confiteriaRepo.save(confiteria);
    }

    @Override
    public Confiteria actualizarConfiteria(Confiteria confiteria) throws Exception {

        Optional<Confiteria> guardada = confiteriaRepo.findById(confiteria.getCodigo());

        if(guardada.isEmpty()){

            throw new Exception("La confiteria no existe");

        }

        return confiteriaRepo.save(confiteria);
    }

    @Override
    public void eliminarConfiteria(Integer codigo) throws Exception {

        Optional<Confiteria> guardada = confiteriaRepo.findById(codigo);

        if(guardada.isEmpty()){

            throw new Exception("La confiteria no existe");

        }

        confiteriaRepo.delete(guardada.get());


    }
    @Override
    public List<Confiteria> listarConfiteria() {
        return confiteriaRepo.findAll();
    }

    @Override
    public Confiteria obtenerConfiteria(Integer codigo) throws Exception {

        Optional<Confiteria> confiteria = confiteriaRepo.findById(codigo);

        if(confiteria.isEmpty()){

            throw new Exception("No hay una confiteria con ese codigo");

        }

        return confiteria.get();
    }

    //--------------------------------------- Gestion Adminteatro ---------------------------------------------

    @Override
    public AdministradorTeatro crearAdminTeatro(AdministradorTeatro administradorTeatro) throws Exception {

        boolean correoExiste = esRepetidoAdminTeatro(administradorTeatro.getCorreo());

        if(correoExiste == false){
            throw new Exception("El correo ya esta registrado");
        }
           return administradorTeatroRepo.save(administradorTeatro);
    }

    @Override
    public AdministradorTeatro actualizarAdminTeatro(AdministradorTeatro administradorTeatro) throws Exception {

        Optional<AdministradorTeatro> guardado = administradorTeatroRepo.findById(administradorTeatro.getCodigo());

        if(guardado.isEmpty()){

            throw new Exception("El administradorTeatro no existe");

        }

        return administradorTeatroRepo.save(administradorTeatro);
    }

    @Override
    public void eliminarAdminTeatro(Integer codigo) throws Exception {

        Optional<AdministradorTeatro> guardado = administradorTeatroRepo.findById(codigo);

        if(guardado.isEmpty()){

            throw new Exception("El administradorTeatro no existe");

        }

        administradorTeatroRepo.delete(guardado.get());

    }

    @Override
    public List<AdministradorTeatro> listarAdminsTeatro() {
        return administradorTeatroRepo.findAll();
    }

    @Override
    public AdministradorTeatro obtenerAdminTeatro(Integer codigo) throws Exception {

        Optional<AdministradorTeatro> administradorTeatro = administradorTeatroRepo.findById(codigo);

        if(administradorTeatro.isEmpty()){

            throw new Exception("No hay un administrador con ese codigo");

        }

        return administradorTeatro.get();
    }

    @Override
    public Pelicula obtenerPeliculaNombre(String nombrePelicula) throws Exception{

        Optional<Pelicula> guardado = peliculaRepo.obtenerPeliculaNombre(nombrePelicula);

        if (guardado.isEmpty()){
            throw new Exception("La pelicula no existe");
        }
        return guardado.get();
    }
}
