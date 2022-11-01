package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ClienteServicio {

    //-------------------------------------------- Gestion Cliente ---------------------------------------------

    Cliente registrarCliente(Cliente cliente) throws Exception;
    Cliente activarCuentaCliente(Cliente cliente) throws Exception;
    Cliente obtenerCliente(Integer codigoCliente)throws Exception;

    CuponCliente asisgnarCupon(Integer codigo, CuponCliente cuponCliente) throws Exception;

    Cliente actualizarCliente(Cliente cliente) throws Exception;
    void eliminarCliente(Integer codigoCliente) throws Exception;
    Cliente login(String correo, String password)throws Exception;
    List<Cliente> listarCliente();
    boolean cambiarPassword(Integer codigo,String passwordNueva, String passwordActual) throws Exception;
    void recuperarPassword(String correo) throws Exception;
    //---------------------------------------------- Compra ----------------------------------------------------



    List<Compra> listarHistorial(Integer codigoCliente) throws Exception;
    Compra hacerCompra(Compra compra) throws Exception;
    boolean redimirCupon(CuponCliente cuponCliente, LocalDate fechaCompra) throws Exception;
    List<Pelicula> buscarPelicula(String nombre);
    List<Pelicula> buscarPeliculaGenero(String nombre, Genero genero);
}
