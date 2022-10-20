package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Sala;
import co.edu.uniquindio.unicine.entidades.Teatro;

import java.util.List;

public interface AdminTeatroServicio {


    //--------------------------------------- Gestion Horario -------------------------------------------------

    Horario crearHorario(Horario horario);
    List<Horario> listarHorarios();
    Horario obtenerHorario(Integer codigo) throws Exception;
    void eliminarHorario(Integer codigoHorario) throws Exception;


    //--------------------------------------- Gestion Funcion --------------------------------------------------

    Funcion crearFuncion(Funcion funcion);
    Funcion actualizarFuncion(Funcion funcion) throws Exception;
    void eliminarFuncion(Integer codigoFuncion) throws Exception;
    List<Funcion> listarFunciones();
    Funcion obtenerFuncion(Integer codigo) throws Exception;


    //--------------------------------------- Gestion Sala -----------------------------------------------------

    Sala crearSala(Sala sala);
    Sala actualizarSala(Sala sala) throws Exception;
    void eliminarSala(Integer codigo) throws Exception;
    List<Sala> listarSalas();
    Sala obtenerSala(Integer codigo) throws Exception;


    //--------------------------------------- Gestion Teatro ---------------------------------------------------
    Teatro crearTeatro(Teatro teatro);
    Teatro actualizarTeatro(Teatro teatro) throws Exception;
    void eliminarTeatro(Integer codigoTeatro) throws Exception;
    List<Teatro> listarTeatros();
    Teatro obtenerTeatro(Integer codigo) throws Exception;

}
