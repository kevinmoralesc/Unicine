package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.Entrada;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepo extends JpaRepository<Compra,Integer> {

    @Query("select c from Compra c where c.cliente.correo = :correo")
    List<Compra> obtenerCompras(String correo);
    @Query("select e from Compra c join c.entradas e where c.codigo = :codigoCompra ")
    List<Entrada> obtenerEntradas (Integer codigoCompra);
    @Query("select c.cliente.codigo, count (c) from Compra c where c.cuponCliente is not null group by c.cliente")
    List<Object[]> contarCuponesRedimidos();
    @Query("select sum(c.valorTotal) from Compra c where c.cliente.codigo = :codigoUsuario")
    Float calcularTotalGastado(Integer codigoUsuario);
    @Query("select c.cliente, c from Compra c order by c.valorTotal desc ")
    List<Object[]> obtenerCompraMasCostosa1();
    @Query("select  c1.cliente, c1 from Compra c1 where c1.valorTotal = (select max(c.valorTotal) from Compra c)  ")
    List<Object[]> obtenerCompraMasCostosa2();
   // @Query("select new co.edu.uniquindio.unicine.dto.InformacionCompraDTO(c.valorTotal, c.fecha, c.funcion,(select sum(e.precio) from Entrada e where e.compra.codigo = c.codigo ),(select sum(cc.precio*cc.unidades) from CompraConfiteria cc where cc.compra.codigo = c.codigo )) from Compra c where c.cliente.codigo = :codigoCliente")
    //List<Object[]> obtenerInformacionCompras(Integer codigoCliente);
    @Query("select c.funcion.pelicula,count(c) from Compra c where c.funcion.sala.teatro.ciudad.codigo = :codigoCiudad group by c.funcion.pelicula")
    List<Object[]> obtenerPeliculaMasVista(Integer codigoCiudad);
}
