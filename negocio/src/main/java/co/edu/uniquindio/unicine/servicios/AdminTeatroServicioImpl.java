package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Sala;
import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.repo.FuncionRepo;
import co.edu.uniquindio.unicine.repo.HorarioRepo;
import co.edu.uniquindio.unicine.repo.SalaRepo;
import co.edu.uniquindio.unicine.repo.TeatroRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminTeatroServicioImpl implements AdminTeatroServicio {

    private final HorarioRepo horarioRepo;
    private final FuncionRepo funcionRepo;
    private final SalaRepo salaRepo;
    private final TeatroRepo teatroRepo;

    public AdminTeatroServicioImpl(HorarioRepo horarioRepo, FuncionRepo funcionRepo, SalaRepo salaRepo, TeatroRepo teatroRepo) {
        this.horarioRepo = horarioRepo;
        this.funcionRepo = funcionRepo;
        this.salaRepo = salaRepo;
        this.teatroRepo = teatroRepo;
    }

    //--------------------------------------- Gestion Funcion --------------------------------------------------
    @Override
    public Horario crearHorario(Horario horario) {
        return null;
    }

    @Override
    public List<Horario> listarHorarios() {
        return null;
    }

    @Override
    public Horario obtenerHorario(Integer codigo) throws Exception {
        return null;
    }

    @Override
    public void eliminarHorario(Integer codigoHorario) throws Exception {

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
        return null;
    }

    @Override
    public Sala actualizarSala(Sala sala) throws Exception {
        return null;
    }

    @Override
    public void eliminarSala(Integer codigo) throws Exception {

    }

    @Override
    public List<Sala> listarSalas() {
        return null;
    }

    @Override
    public Sala obtenerSala(Integer codigo) throws Exception {
        return null;
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
}
