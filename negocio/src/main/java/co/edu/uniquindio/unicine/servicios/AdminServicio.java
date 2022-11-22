package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface AdminServicio {

    //------------------------------------------------- Admin -------------------------------------------------
    Administrador loginAdmin(String correo, String password) throws Exception;

    void recuperarPassword(String correo) throws Exception;

    boolean actualizarPassword(Integer codigo, String passwordNueva, String passwordActual) throws Exception;

    Administrador obtenerAdministrador(Integer codigo) throws Exception;

    //--------------------------------------- Gestion Ciudad --------------------------------------------------

    Ciudad crearCiudad(Ciudad ciudad);

    Ciudad actualizarCiudad(Ciudad ciudad) throws Exception;

    void eliminarCiudad(Integer codigo) throws Exception;

    Ciudad obtenerCiudad (Integer codigo) throws Exception;

    List<Ciudad> listarCiudades();


    //--------------------------------------- Gestion Pelicula ------------------------------------------------

    Pelicula crearPelicula(Pelicula pelicula);

    Pelicula actualizarPelicula(Pelicula pelicula) throws Exception;

    void eliminarPelicula(Integer codigo) throws Exception;

    List<Pelicula> listarPeliculasProximas(Integer codigo, EstadoPelicula estadoPelicula);
    List<Pelicula> listarPeliculasCartelera(Integer codigo, EstadoPelicula estadoPelicula);

    List<Pelicula> listarPeliculas();

    Pelicula obtenerPelicula(Integer codigo) throws Exception;


    //--------------------------------------- Gestion Cupon ---------------------------------------------------

    Cupon crearCupon(Cupon cupon);

    Cupon actualizarCupon(Cupon cupon) throws Exception;

    void eliminarCupon(Integer codigo) throws Exception;

    List<Cupon> listarCupones();

    Cupon obtenerCupon(Integer codigo) throws Exception ;


    //--------------------------------------- Gestion Confiteria -----------------------------------------------

    Confiteria crearConfiteria(Confiteria confiteria);

    Confiteria actualizarConfiteria(Confiteria confiteria) throws Exception;

    void eliminarConfiteria(Integer codigo) throws Exception;

    List<Confiteria> listarConfiteria();

    Confiteria obtenerConfiteria(Integer codigo) throws Exception;


    //--------------------------------------- Gestion Adminteatro ---------------------------------------------

    AdministradorTeatro crearAdminTeatro(AdministradorTeatro administradorTeatro) throws Exception;

    AdministradorTeatro actualizarAdminTeatro(AdministradorTeatro administradorTeatro) throws Exception;

    void eliminarAdminTeatro(Integer codigo) throws Exception;

    List<AdministradorTeatro> listarAdminsTeatro();

    AdministradorTeatro obtenerAdminTeatro(Integer codigo) throws Exception;

}
