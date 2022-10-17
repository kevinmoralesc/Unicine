package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.Cupon;
import co.edu.uniquindio.unicine.entidades.CuponCliente;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente,Integer> {

    @Query("select c from Cliente c where c.correo = ?1 ")
    Cliente obtener(String email);

    Optional<Cliente> findByCorreo(String Correo);

    @Query("select c from Cliente c where c.correo = :correo and c.password = :password")
    Cliente comprobarAutenticacion(String correo, String password);

    Cliente findByCorreoAndPassword(String email, String clave);

    @Query("select c from Cliente c where c.estado = ?1")
    List<Cliente> obtenerPorEstado(boolean estado, Pageable paginador);
    @Query("select comp from Cliente cli, in (cli.compras) comp where cli.correo = :correo")
    List<Compra> obtenerCompras(String correo);

    @Query("select comp from Cliente cli join cli.compras comp where cli.correo = :correo")
    List<Compra> obtenerComprasJoin(String correo);

    @Query("select cup from Cliente cli join cli.cuponClientes cup where cli.correo = :correo ")
    List<CuponCliente> obtenerCuponesJoin (String correo);

    @Query("select cli.cuponClientes from Cliente cli where cli.correo = :correo ")
    List<CuponCliente> obtenerCupones (String correo);

    @Query("select cli.nombre, cli.correo, comp from Cliente cli left join cli.compras comp")
    List<Object[]> obtenerComprasTodos ();


}
