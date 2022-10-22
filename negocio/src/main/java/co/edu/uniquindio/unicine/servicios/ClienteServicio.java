package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.Genero;
import co.edu.uniquindio.unicine.entidades.Pelicula;

import java.util.List;

public interface ClienteServicio {

    //-------------------------------------------- Gestion Cliente ---------------------------------------------

    Cliente registrarCliente(Cliente cliente) throws Exception;
    Cliente activarCuentaCliente(Cliente cliente) throws Exception;
    Cliente obtenerCliente(Integer codigoCliente)throws Exception;
    Cliente actualizarCliente(Cliente cliente) throws Exception;
    void eliminarCliente(Integer codigoCliente) throws Exception;
    Cliente login(String correo, String password)throws Exception;
    List<Cliente> listarCliente();
    boolean cambiarPassword(Integer codigo,String passwordNueva, String passwordActual) throws Exception;

    //---------------------------------------------- Compra ----------------------------------------------------

    List<Compra> listarHistorial(Integer codigoCliente) throws Exception;
    Compra hacerCompra(Compra compra) throws Exception;
    boolean redirCupon(Integer codigoCupon) throws Exception;
    List<Pelicula> buscarPelicula(String nombre);

}
