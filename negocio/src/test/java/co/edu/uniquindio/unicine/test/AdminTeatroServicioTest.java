package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

public class AdminTeatroServicioTest {


    private AdminTeatroServicio adminTeatroServicio;

    @Test
    @Sql("classpath:dataset.sql")
    public void crearTeatroTest() {

        Teatro teatro = Teatro.builder().direccion("Carrera 33 # 2-3").telefono("7314346").build();
        Teatro nuevo = adminTeatroServicio.crearTeatro(teatro);
        Assertions.assertEquals(nuevo, teatro);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTeatroTest() {

        try {
            Teatro teatro = adminTeatroServicio.obtenerTeatro(1);
            teatro.setDireccion("Carrera 21 # 10-12");
            Teatro nuevo = adminTeatroServicio.actualizarTeatro(teatro);
            Assertions.assertEquals("Carrera 21 # 10-12", nuevo.getDireccion());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void listarTeatrosTest(){

        List<Teatro> teatros = adminTeatroServicio.listarTeatros();
        Assertions.assertEquals(5,teatros.size());

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTeatrosTest(){

        try {
            adminTeatroServicio.eliminarTeatro(1);
        }catch (Exception e){
            Assertions.assertTrue(false);
        }
    }
}
