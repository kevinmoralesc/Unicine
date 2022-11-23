package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.dto.PeliculaFuncion;
import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio{

    private final ClienteRepo clienteRepo;
    private final PeliculaRepo peliculaRepo;
    private final EmailServicio emailServicio;
    private final CuponRepo cuponRepo;

    private final CompraConfiteriaRepo compraConfiteriaRepo;
    private final EntradaRepo entradaRepo;
    private final CompraRepo compraRepo;
    private final FuncionRepo funcionRepo;
    private final CuponClienteRepo cuponClienteRepo;

    public ClienteServicioImpl(ClienteRepo clienteRepo, PeliculaRepo peliculaRepo, EmailServicio emailServicio, CuponRepo cuponRepo, CompraConfiteriaRepo compraConfiteriaRepo, EntradaRepo entradaRepo, CompraRepo compraRepo, FuncionRepo funcionRepo, CuponClienteRepo cuponClienteRepo) {
        this.clienteRepo = clienteRepo;
        this.peliculaRepo = peliculaRepo;
        this.emailServicio = emailServicio;
        this.cuponRepo = cuponRepo;
        this.compraConfiteriaRepo = compraConfiteriaRepo;
        this.entradaRepo = entradaRepo;
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
    public List<Pelicula> listarPeliculasEstado(EstadoPelicula estadoPelicula) {
        return peliculaRepo.listarPeliculasEstado(estadoPelicula);
    }


    @Override
    public Cliente registrarCliente(Cliente cliente) throws Exception {

        boolean correoExiste = esRepetido(cliente.getCorreo());

        if(correoExiste){

            throw new Exception("El correo ya se encuentra registrado");

        }
        StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
        cliente.setPassword(spe.encryptPassword(cliente.getPassword()));
        Cliente registro = clienteRepo.save(cliente);
        //emailServicio.enviarEmail("Registro en unicine", "Hola, debe ir al siguiente enlace para activar la cuenta", cliente.getCorreo());
        return registro;
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

    /**
     *
     * @param compra
     * @return
     * @throws Exception
     */
    @Override
    public Compra hacerCompra(Compra compra) throws Exception {
        Cliente cliente            = obtenerCliente(compra.getCliente().getCodigo());
        Funcion funcion            = compra.getFuncion();
        String factura = "";
        double total               = 0.0;
        double descuentoCupon           = 1.0;
        Double subTotalConfiterias = 0.0;
        Double subTotalEntradas    = 0.0;
        double valorFuncion        = 0.0;

        if(funcion != null){
            if(compra.getMedioPago() != null) {
                if (verificarEstadoEntradas(compra.getEntradas(), funcion.getHorario())) {
                    if (redimirCupon(compra.getCuponCliente(), compra.getFecha())) {
                        descuentoCupon = (100 - (compra.getCuponCliente().getCupon().getDescuento())) / 100;
                        compra.getCuponCliente().setEstado(false);
                    }
                    subTotalEntradas = obtenerSubtotalEntradas(compra.getEntradas());
                    subTotalConfiterias = obtenerSubtotalConfiterias(compra.getCompraConfiterias());
                    valorFuncion = funcion.getPrecio();
                    total = (subTotalEntradas + subTotalConfiterias + valorFuncion) * descuentoCupon;
                    compra.setValorTotal((float) total);
                    asignarElementosCompra(compra,compra.getCuponCliente(),compra.getFuncion(),compra.getCliente(),compra.getCompraConfiterias(),compra.getEntradas());
                    factura = realizarFacturaCompra(compra);
                    System.out.println(factura);
                    emailServicio.enviarEmail("Compra Unicine", "Informacion compra: \n" + factura, cliente.getCorreo());
                    return compraRepo.save(compra);

                } else {
                    throw new Exception("Las sillas elegidas ya estan ocupadas");
                }
            }
            else{
                throw new Exception("Medio de pago inexistente o no soportado");
            }
        }
        else{
            throw new Exception("La funcion no existe");
        }
    }

    private void asignarElementosCompra(Compra compra, CuponCliente cuponCliente, Funcion funcion, Cliente cliente, List<CompraConfiteria> compraConfiterias, List<Entrada> entradas) {

        funcion.getCompras().add(compra);
        cuponClienteRepo.save(compra.getCuponCliente());

        cuponCliente.setCompra(compra);
        funcionRepo.save(funcion);

        cliente.getCompras().add(compra);
        clienteRepo.save(cliente);

        for (CompraConfiteria compraConfiteria : compraConfiterias) {
            compraConfiteria.setCompra(compra);
            compraConfiteriaRepo.save(compraConfiteria);
        }

        for (Entrada entrada : entradas) {
            entrada.setCompra(compra);
            entradaRepo.save(entrada);
        }
    }

    private String realizarFacturaCompra(Compra compra) {

        String mensaje ="";

        mensaje += "" + compra.getFecha()+"\n";
        mensaje += "" + compra.getCliente().getCorreo()+"\n";
        mensaje += "Pelicula : " + compra.getFuncion().getPelicula()+"\n";
        mensaje += "Hora funcion :" + compra.getFuncion().getHorario().getHora()+"\n";
        mensaje += "Entradas :" + compra.getEntradas().size()+"\n";
        mensaje += "Confiterias :" + compra.getEntradas().size()+"\n";

        if(compra.getCuponCliente() != null){

            mensaje += "Total a pagar con cupon :" + compra.getValorTotal()+"\n";
            mensaje += "Cantidad ahorrada :" + (obtenerSubtotalConfiterias(compra.getCompraConfiterias())+obtenerSubtotalEntradas(compra.getEntradas())-compra.getValorTotal())+"\n";


        }else{

            mensaje += "Total a pagar :" + compra.getValorTotal()+"\n";
        }

        return mensaje;

    }

    private Double obtenerSubtotalConfiterias(List<CompraConfiteria> compraConfiterias) {

        double valor = 0;

        for (CompraConfiteria compraConfiteria : compraConfiterias) {

            valor += compraConfiteria.getPrecio();
        }
        return valor;
    }

    private Double obtenerSubtotalEntradas(List<Entrada> entradas) {

        double valor = 0;

        for (Entrada entrada : entradas) {

            valor += entrada.getPrecio();
        }

        return valor;
    }


    private boolean verificarEstadoEntradas(List<Entrada> entradas, Horario horario) {

        for (Entrada entrada : entradas) {

            if(entradaRepo.obtenerCompra(entrada).getFuncion().getHorario().equals(horario) == true){

                if(entrada.getCompra() != null){

                    return false;

                }
            }
        }
        return true;
    }



    @Override
    public boolean redimirCupon(CuponCliente cuponCliente, LocalDate fechaCompra){

        if(cuponCliente.getEstado() == true){

            if(fechaCompra.isAfter(cuponCliente.getCupon().getFechaVencimiento())){

                return false;
            }

            return true;

        }
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

    @Override
    public List<PeliculaFuncion> listarFuncionesPelicula(String nombre) {
        return peliculaRepo.buscarPeliculas(nombre);
    }

}
