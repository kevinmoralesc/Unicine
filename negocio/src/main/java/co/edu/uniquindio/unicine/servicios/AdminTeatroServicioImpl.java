package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminTeatroServicioImpl implements AdminTeatroServicio {

    private final AdministradorTeatroRepo adminTeatroRepo;
    private final HorarioRepo horarioRepo;
    private final FuncionRepo funcionRepo;
    private final SalaRepo salaRepo;
    private final TeatroRepo teatroRepo;

    private final CiudadRepo ciudadRepo;

    private final DistribucionSillasRepo distribucionSillasRepo;
    private final EmailServicio emailServicio;

    public AdminTeatroServicioImpl(AdministradorTeatroRepo adminTeatroRepo, HorarioRepo horarioRepo, FuncionRepo funcionRepo, SalaRepo salaRepo, TeatroRepo teatroRepo, CiudadRepo ciudadRepo, DistribucionSillasRepo distribucionSillasRepo, EmailServicio emailServicio) {
        this.adminTeatroRepo = adminTeatroRepo;
        this.horarioRepo = horarioRepo;
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
        AdministradorTeatro administradorTeatro = adminTeatroRepo.comprobarAutenticacion(correo, password);

        if(administradorTeatro == null){
            throw new Exception("Los datos de autenticación son Incorrectos");
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
    public Horario crearHorario(Horario horario) { return horarioRepo.save(horario); }

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
    public Funcion crearFuncion(Funcion funcion) {
        return null;
    }

    @Override
    public Funcion actualizarFuncion(Funcion funcion) throws Exception {
        return null;
    }

    @Override
    public void eliminarFuncion(Integer codigoFuncion) throws Exception {

    }

    @Override
    public List<Funcion> listarFunciones() {
        return null;
    }

    @Override
    public Funcion obtenerFuncion(Integer codigo) throws Exception {
        return null;
    }

    //--------------------------------------- Gestion Sala -----------------------------------------------------

    @Override
    public Sala crearSala(Sala sala) {

        return salaRepo.save(sala);
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
    public List<Sala> listarSalas() {

        return salaRepo.findAll();
    }

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
    public Teatro crearTeatro(Teatro teatro) {
        return teatroRepo.save(teatro);
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

    //--------------------------------------- Distribución Silla ---------------------------------------------------
    @Override
    public DistribucionSillas obtenerDistribucionSilla(Integer codigo) throws Exception {

        Optional<DistribucionSillas> guardado = distribucionSillasRepo.findById(codigo);

        if(guardado.isEmpty()){
            throw new Exception("El codigo de Distribucion de silla no se encuentra registrado");
        }
        return guardado.get();
    }

    //--------------------------------------- Ciudad  ---------------------------------------------------
    @Override
    public Ciudad obtenerCiudad(Integer codigo) throws Exception {

        Optional<Ciudad> guardado = ciudadRepo.findById(codigo);

        if(guardado.isEmpty()){
            throw new Exception("El codigo de la Ciudad no Existe");
        }
        return guardado.get();
    }
}
