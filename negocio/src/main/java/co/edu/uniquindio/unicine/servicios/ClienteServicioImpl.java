package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio{

    private final ClienteRepo clienteRepo;
    private final PeliculaRepo peliculaRepo;
    private final EmailServicio emailServicio;
    private final CuponRepo cuponRepo;

    private final CompraRepo compraRepo;
    private final FuncionRepo funcionRepo;
    private final CuponClienteRepo cuponClienteRepo;

    public ClienteServicioImpl(ClienteRepo clienteRepo, PeliculaRepo peliculaRepo, EmailServicio emailServicio, CuponRepo cuponRepo, CompraRepo compraRepo, FuncionRepo funcionRepo, CuponClienteRepo cuponClienteRepo) {
        this.clienteRepo = clienteRepo;
        this.peliculaRepo = peliculaRepo;
        this.emailServicio = emailServicio;
        this.cuponRepo = cuponRepo;
        this.compraRepo = compraRepo;
        this.funcionRepo = funcionRepo;
        this.cuponClienteRepo = cuponClienteRepo;
    }

    //--------------------------------------- Gestion Cliente -------------------------------------------------



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

    @Override
    public CuponCliente asisgnarCupon(Integer codigo, CuponCliente cuponCliente) throws Exception{

        Optional<Cliente> cliente = clienteRepo.findById(codigo);
        if(cliente.isEmpty()){

            throw new Exception("no existe el cliente");
        }

        return cuponClienteRepo.save(cuponCliente);

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

            throw new Exception("El Cliente no existe");

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
    public void recuperarPassword(String correo) throws Exception {

        boolean correoExiste = esRepetidoCliente(correo);

        if(correoExiste){
            throw new Exception("El correo no esta registrado");
        }
        Optional<Cliente> cliente = clienteRepo.findByCorreo(correo);
        emailServicio.enviarEmail("Cambio de contraseña unicine", "Hola, debe ir al siguiente enlace para ingresar la nueva contraseña", (cliente.get()).getCorreo());
    }

    private boolean esRepetidoCliente(String correo){
        return clienteRepo.findByCorreo(correo).orElse(null) == null;
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
    public Compra hacerCompra(Compra compra) throws Exception {
        return null;
    }


    @Override
    public boolean redimirCupon(CuponCliente cuponCliente, LocalDateTime fechaCompra){
        return false;
    }

    @Override
    public List<Pelicula> buscarPelicula(String nombre) {

        List<Pelicula> peliculas = peliculaRepo.buscarPelicula(nombre);

        return peliculas;

    }

    @Override
    public List<Pelicula> buscarPeliculaGenero(String nombre,Genero genero) {

        List<Pelicula> peliculas = peliculaRepo.buscarPeliculaGenero(nombre,genero);

        return peliculas;

    }

}
