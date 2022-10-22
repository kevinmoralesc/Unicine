package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import co.edu.uniquindio.unicine.repo.PeliculaRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio{

    private final ClienteRepo clienteRepo;
    private final PeliculaRepo peliculaRepo;
    private final EmailServicio emailServicio;

    //--------------------------------------- Gestion Cliente -------------------------------------------------

    public ClienteServicioImpl(ClienteRepo clienteRepo, PeliculaRepo peliculaRepo, EmailServicio emailServicio) {
        this.clienteRepo = clienteRepo;
        this.peliculaRepo = peliculaRepo;
        this.emailServicio = emailServicio;
    }

    @Override
    public Cliente obtenerCliente(Integer codigoCliente) throws Exception {

        Optional<Cliente> guardado = clienteRepo.findById(codigoCliente);

        if(guardado.isEmpty()){

            throw new Exception("El cliente no existe");

        }

        return guardado.get();
    }

    @Override
    public Cliente login(String correo, String password) throws Exception{
        Cliente cliente = clienteRepo.comprobarAutenticacion(correo,password);

        if(cliente == null){
          throw new Exception("Los datos de autenticación son incorrectos");
        }
        return cliente;
    }

    @Override
    public Cliente registrarCliente(Cliente cliente) throws Exception {

        boolean correoExiste = esRepetido(cliente.getCorreo());

        if(correoExiste){

            throw new Exception("El correo ya se encuentra registrado");

        }
        emailServicio.enviarEmail("Registro en unicine", "Hola, debe ir al siguiente enlace para activar la cuenta", cliente.getCorreo());
        return clienteRepo.save(cliente);
    }

    @Override
    public Cliente activarCuentaCliente(Cliente cliente) throws Exception {

        Optional<Cliente> guardado = clienteRepo.findById(cliente.getCodigo());

        if(guardado.isEmpty()){

            throw new Exception("El cliente no existe");

        }

        cliente.setEstado(true);
        return clienteRepo.save(cliente);
    }

    public void asisgnarCuponRegistro(Cliente cliente,CuponCliente cuponRegistro){

        cliente.getCuponClientes().add(cuponRegistro);

    }

    private boolean esRepetido(String correo){
        return clienteRepo.findByCorreo(correo).orElse(null) != null;
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) throws Exception {

        Optional<Cliente> guardado = clienteRepo.findById(cliente.getCodigo());

        if(guardado.isEmpty()){

            throw new Exception("El cliente no existe");

        }

        return clienteRepo.save(cliente);
    }

    @Override
    public void eliminarCliente(Integer codigoCliente) throws Exception{

        Optional<Cliente> guardado = clienteRepo.findById(codigoCliente);

        if(guardado.isEmpty()){

            throw new Exception("El cliente no existe");

        }

        clienteRepo.delete(guardado.get());

    }

    @Override
    public List<Cliente> listarCliente(){
        return clienteRepo.findAll();
    }

    @Override
    public boolean cambiarPassword(Integer codigo,String passwordNueva, String passwordActual) throws Exception {
        Optional<Cliente> guardado = clienteRepo.findById(codigo);

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


    //---------------------------------------------- Compra ----------------------------------------------------

    @Override
    public List<Compra> listarHistorial(Integer codigoCliente) throws Exception {

        Optional<Cliente> guardado = clienteRepo.findById(codigoCliente);

        if(guardado.isEmpty()){

            throw new Exception("El cliente no existe");

        }

        List<Compra> compras = clienteRepo.obtenerCompras(codigoCliente);
        return compras;
    }

    @Override
    public Compra hacerCompra(Compra compra) throws Exception{

        Optional<Cliente> guardado = clienteRepo.findById(compra.getCliente().getCodigo());

        if(guardado.isEmpty()){

            throw new Exception("El cliente no existe");

        }


        emailServicio.enviarEmail("Se ha realizado una compra", "Hola, ha comprado x entradas para ver la pelicula x ....", compra.getCliente().getCorreo());
        return null;
    }

    @Override
    public boolean redirCupon(Integer codigoCupon) throws Exception{
        return false;
    }

    @Override
    public List<Pelicula> buscarPelicula(String nombre) {

        List<Pelicula> peliculas = peliculaRepo.buscarPelicula(nombre);

        return peliculas;

    }

}
