package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio{

    private ClienteRepo clienteRepo;

    public ClienteServicioImpl(ClienteRepo clienteRepo) {
        this.clienteRepo = clienteRepo;
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
            throw new Exception("El correo ya esta en uso");
        }

        return clienteRepo.save(cliente);
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
    public List<Compra> listarHistorial(Integer codigoCliente) throws Exception {
        return null;
    }

    @Override
    public Compra hacerCompra(Compra compra) throws Exception{
        return null;
    }

    @Override
    public boolean redirCupon(Integer codigoCupon) throws Exception{
        return false;
    }
}
