package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminTeatroServicioImpl implements AdminTeatroServicio {

    private final AdministradorTeatroRepo adminTeatroRepo;
    private final HorarioRepo horarioRepo;
    private final PeliculaRepo peliculaRepo;
    private final FuncionRepo funcionRepo;
    private final SalaRepo salaRepo;
    private final TeatroRepo teatroRepo;
    private final CiudadRepo ciudadRepo;
    private final DistribucionSillasRepo distribucionSillasRepo;
    private final EmailServicio emailServicio;
    public AdminTeatroServicioImpl(AdministradorTeatroRepo adminTeatroRepo, HorarioRepo horarioRepo, PeliculaRepo peliculaRepo, FuncionRepo funcionRepo, SalaRepo salaRepo, TeatroRepo teatroRepo, CiudadRepo ciudadRepo, DistribucionSillasRepo distribucionSillasRepo, EmailServicio emailServicio) {
        this.adminTeatroRepo = adminTeatroRepo;
        this.horarioRepo = horarioRepo;
        this.peliculaRepo = peliculaRepo;
        this.funcionRepo = funcionRepo;
        this.salaRepo = salaRepo;
        this.teatroRepo = teatroRepo;
        this.ciudadRepo = ciudadRepo;
        this.distribucionSillasRepo = distribucionSillasRepo;
        this.emailServicio = emailServicio;
    }

    //------------------------------------------------- Admin Teatro -------------------------------------------------
    @Override
    public AdministradorTeatro loginAdmin(String correo, String password) throws Exception {
        AdministradorTeatro administradorTeatro = adminTeatroRepo.findByCorreo(correo).orElse(null);

        if(administradorTeatro == null){
            throw new Exception("El correo no existe");
        }
        StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
        if(!spe.checkPassword(password, administradorTeatro.getPassword())){
            throw new Exception("La contraseña es incorrecta");
        }
        return administradorTeatro;
    }
    @Override
    public void recuperarPassword(String correo) throws Exception {
        boolean correoExiste = esRepetidoAdminTeatro(correo);

        if(correoExiste){
            throw new Exception("El correo no esta registrado");
        }

        Optional<AdministradorTeatro> administradorTeatro = adminTeatroRepo.findByCorreo(correo);
        emailServicio.enviarEmail("Cambio de contraseña unicine", "Hola, debe ir al siguiente enlace para ingresar la nueva contraseña", (administradorTeatro.get()).getCorreo());

    }
    @Override
    public boolean actualizarPassword(Integer codigo, String passwordNueva, String passwordActual) throws Exception {
        Optional<AdministradorTeatro> guardado = adminTeatroRepo.findById(codigo);

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
        adminTeatroRepo.save(guardado.get());
        return true;
    }
    @Override
    public AdministradorTeatro obtenerAdministradorTeatro(Integer codigo) throws Exception {
        Optional<AdministradorTeatro> administradorTeatro = adminTeatroRepo.findById(codigo);

        if(administradorTeatro.isEmpty()){

            throw new Exception("No hay un administrador con ese codigo");

        }

        return administradorTeatro.get();
    }
    private boolean esRepetidoAdminTeatro(String correo){
        return adminTeatroRepo.findByCorreo(correo).orElse(null) == null;
    }

    //--------------------------------------- Gestion Horario --------------------------------------------------
    @Override
    public Horario crearHorario(Horario horario) throws Exception{

        Horario guardado = horarioRepo.comprobarExistencia(horario.getDia(), horario.getHora());

        if(horario.getFechaFin().isAfter(horario.getFechaInicio())){
            if (guardado == null){
                return horarioRepo.save(horario);
            }else{
                throw new Exception("Ya se encuentra registrado");
            }
        }else{
            throw new Exception("La fecha de fin no es posterior a la de inicio");
        }
    }
    @Override
    public Horario actualizarHorario(Horario horario) throws Exception{

        Optional<Horario> guardado = horarioRepo.findById(horario.getCodigo());

        if (!guardado.isEmpty()){
            if(horario.getFechaFin().isAfter(horario.getFechaInicio())){
                return horarioRepo.save(horario);
            }else{ throw new Exception("La fecha de fin no es posterior a la de inicio"); }

        }else{ throw new Exception("El horario no existe"); }
    }
    @Override
    public List<Horario> listarHorarios() { return horarioRepo.findAll(); }

    @Override
    public Horario obtenerHorario(Integer codigo) throws Exception {

        Optional<Horario> guardado = horarioRepo.findById(codigo);

        if(guardado.isEmpty()){
            throw new Exception("El codigo del horario no se encuentra registrado");
        }
        return guardado.get();
    }

    @Override
    public void eliminarHorario(Integer codigoHorario) throws Exception {

        Optional<Horario> guardado = horarioRepo.findById(codigoHorario);

        if (guardado.isEmpty()){ throw new Exception("El horario no existe"); }

        horarioRepo.delete(guardado.get());
    }

    //--------------------------------------- Gestion Funcion --------------------------------------------------

    @Override
    public Funcion crearFuncion(Funcion funcion) throws Exception{
        Funcion guardado = funcionRepo.comprobarFuncion(funcion.getHorario(), funcion.getSala());
        if (guardado == null){
            return funcionRepo.save(funcion);
        }else {
            throw new Exception("Ya se encuentra registrado");
        }
    }

    @Override
    public Funcion actualizarFuncion(Funcion funcion) throws Exception {
        Optional<Funcion> guardado = funcionRepo.findById(funcion.getCodigo());

        if(guardado.isEmpty()){
            throw new Exception("La funcion no existe");
        }
        return funcionRepo.save(funcion);
    }

    @Override
    public void eliminarFuncion(Integer codigoFuncion) throws Exception {

        Optional<Funcion> guardado = funcionRepo.findById(codigoFuncion);

        if (guardado.isEmpty()){ throw new Exception("La función no existe"); }

        funcionRepo.delete(guardado.get());
    }

    @Override
    public List<Funcion> listarFunciones() {
        return funcionRepo.findAll();
    }

    @Override
    public Funcion obtenerFuncion(Integer codigo) throws Exception {

        Optional<Funcion> guardado = funcionRepo.findById(codigo);

        if (guardado.isEmpty()){
            throw new Exception("El codigo de la función no se encuentra registrada");
        }

        return guardado.get();
    }

    //--------------------------------------- Gestion Sala -----------------------------------------------------

    @Override
    public Sala crearSala(Sala sala) throws Exception{
        Sala guardado = salaRepo.comprobarSala(sala.getTeatro(), sala.getNombre());
        if (guardado == null){
            return salaRepo.save(sala);
        }else {
            throw new Exception("Ya se encuentra registrado");
        }
    }

    @Override
    public Sala actualizarSala(Sala sala) throws Exception {
        Optional<Sala> guardado = salaRepo.findById(sala.getCodigo());

        if(guardado.isEmpty()){
            throw new Exception("La Sala no existe");
        }
        return salaRepo.save(sala);
    }

    @Override
    public void eliminarSala(Integer codigo) throws Exception {
        Optional<Sala> guardado = salaRepo.findById(codigo);
        if(guardado.isEmpty()){
            throw new Exception("La Sala no existe");
        }

        salaRepo.delete(guardado.get());
    }

    @Override
    public List<Sala> listarSalas() { return salaRepo.findAll(); }

    @Override
    public Sala obtenerSala(Integer codigo) throws Exception {
        Optional<Sala> guardado = salaRepo.findById(codigo);

        if(guardado.isEmpty()){
            throw new Exception("El codigo de la sala no se encuentra registrado");
        }
        return guardado.get();
    }

    //--------------------------------------- Gestion Teatro ---------------------------------------------------

    @Override
    public Teatro crearTeatro(Teatro teatro) throws Exception{
        //Optional<Ciudad> ciudad = ciudadRepo.findById(teatro.getCiudad().getCodigo());
        Teatro guardado = teatroRepo.comprobarExistencia(teatro.getCiudad(), teatro.getDireccion());
        if (guardado == null){
            return teatroRepo.save(teatro);
        }else {
            throw new Exception("Ya se encuentra registrado");
        }
    }

    @Override
    public Teatro actualizarTeatro(Teatro teatro) throws Exception {

        Optional<Teatro> guardado = teatroRepo.findById(teatro.getCodigo());

        if(guardado.isEmpty()){
            throw new Exception("El Teatro no existe");
        }
        return teatroRepo.save(teatro);
    }

    @Override
    public void eliminarTeatro(Integer codigoTeatro) throws Exception {

        Optional<Teatro> guardado = teatroRepo.findById(codigoTeatro);

        if(guardado.isEmpty()){
            throw new Exception("El Teatro no existe");
        }

        teatroRepo.delete(guardado.get());
    }

    @Override
    public List<Teatro> listarTeatros() {
        return teatroRepo.findAll();
    }

    @Override
    public Teatro obtenerTeatro(Integer codigo) throws Exception {

        Optional<Teatro> teatro = teatroRepo.findById(codigo);

        if (teatro.isEmpty()){
            throw new Exception("No existe un Teatro con ese codigo");
        }
        return teatro.get();
    }
    //--------------------------------------- Gestion Distribución Silla ---------------------------------------------------
    @Override
    public DistribucionSillas crearDistribucionSilla(DistribucionSillas distribucionSillas) throws Exception{
        DistribucionSillas guardado = distribucionSillasRepo.comprobarDistribucion(distribucionSillas.getColumnas(), distribucionSillas.getEsquema(), distribucionSillas.getFilas(), distribucionSillas.getTotalSillas());
        if (guardado == null){
            return distribucionSillasRepo.save(distribucionSillas);
        }else {
            throw new Exception("Ya se encuentra registrado");
        }
    }
    @Override
    public DistribucionSillas actualizarDistribucionSilla(DistribucionSillas distribucionSillas) throws Exception{
        Optional<DistribucionSillas> guardado = distribucionSillasRepo.findById(distribucionSillas.getCodigo());

        if(guardado.isEmpty()){
            throw new Exception("La distribucion no existe");
        }
        return distribucionSillasRepo.save(distribucionSillas);
    }
    @Override
    public void eliminarDistribucionSilla(Integer codigoDistribucionSilla) throws Exception{
        Optional<DistribucionSillas> guardado = distribucionSillasRepo.findById(codigoDistribucionSilla);

        if(guardado.isEmpty()){
            throw new Exception("La distribución no existe");
        }

        distribucionSillasRepo.delete(guardado.get());
    }
    @Override
    public List<DistribucionSillas> listarDistribucionSilla(){
        return distribucionSillasRepo.findAll();
    }
    @Override
    public DistribucionSillas obtenerDistribucionSilla(Integer codigo) throws Exception {

        Optional<DistribucionSillas> guardado = distribucionSillasRepo.findById(codigo);

        if(guardado.isEmpty()){
            throw new Exception("El codigo de Distribucion de silla no se encuentra registrado");
        }
        return guardado.get();
    }
    //--------------------------------------- Gestion Ciudad  ---------------------------------------------------
    @Override
    public Ciudad obtenerCiudad(Integer codigo) throws Exception {

        Optional<Ciudad> guardado = ciudadRepo.findById(codigo);

        if(guardado.isEmpty()){
            throw new Exception("El codigo de la Ciudad no Existe");
        }
        return guardado.get();
    }

    @Override
    public List<Funcion> obtenerFuncionesCiudad(Integer codigoCiudad, Integer codigoPelicula)  throws Exception{

        List<Funcion> funciones;

        if(codigoCiudad != null ){
            if(codigoPelicula != null){

                funciones = peliculaRepo.listarPeliculaCiudad(codigoCiudad,codigoPelicula);

            }else{
                throw new Exception("El codigo de la Pelicula no Existe");
            }
        }else{
            throw new Exception("El codigo de la Ciudad no Existe");
        }

        return  funciones;
    }
}