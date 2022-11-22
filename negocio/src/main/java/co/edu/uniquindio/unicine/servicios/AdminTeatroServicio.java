package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;

import java.util.List;

public interface AdminTeatroServicio {

    //-------------------------------------------- Admin Teatro ------------------------------------------

    AdministradorTeatro loginAdmin(String correo, String password) throws Exception;
    void recuperarPassword(String correo) throws Exception;
    boolean actualizarPassword(Integer codigo, String passwordNueva, String passwordActual) throws Exception;
    AdministradorTeatro obtenerAdministradorTeatro(Integer codigo) throws Exception;

    //--------------------------------------- Gestion Horario -------------------------------------------------

    Horario crearHorario(Horario horario) throws Exception;
    List<Horario> listarHorarios();
    Horario obtenerHorario(Integer codigo) throws Exception;
    void eliminarHorario(Integer codigoHorario) throws Exception;
    Horario actualizarHorario(Horario horario) throws Exception;

    //--------------------------------------- Gestion Funcion --------------------------------------------------

    Funcion crearFuncion(Funcion funcion) throws Exception;
    Funcion actualizarFuncion(Funcion funcion) throws Exception;
    void eliminarFuncion(Integer codigoFuncion) throws Exception;
    List<Funcion> listarFunciones();
    Funcion obtenerFuncion(Integer codigo) throws Exception;


    //--------------------------------------- Gestion Sala -----------------------------------------------------

    Sala crearSala(Sala sala) throws Exception;
    Sala actualizarSala(Sala sala) throws Exception;
    void eliminarSala(Integer codigo) throws Exception;
    List<Sala> listarSalas();
    Sala obtenerSala(Integer codigo) throws Exception;


    //--------------------------------------- Gestion Teatro ---------------------------------------------------
    Teatro crearTeatro(Teatro teatro) throws Exception;
    Teatro actualizarTeatro(Teatro teatro) throws Exception;
    void eliminarTeatro(Integer codigoTeatro) throws Exception;
    List<Teatro> listarTeatros();
    Teatro obtenerTeatro(Integer codigo) throws Exception;

    //--------------------------------------- DistribucionSillas  ---------------------------------------------------
    DistribucionSillas crearDistribucionSilla(DistribucionSillas distribucionSillas) throws Exception;
    DistribucionSillas actualizarDistribucionSilla(DistribucionSillas distribucionSillas) throws Exception;
    void eliminarDistribucionSilla(Integer codigoDistribucionSilla) throws Exception;
    List<DistribucionSillas> listarDistribucionSilla();
    DistribucionSillas obtenerDistribucionSilla(Integer codigo) throws Exception;

    //--------------------------------------- Ciudad  ---------------------------------------------------
    Ciudad obtenerCiudad(Integer codigo) throws Exception;
    List<Funcion> obtenerFuncionesCiudad(Integer codigoCiudad, Integer codigoPelicula) throws Exception;
}