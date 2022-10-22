package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@Transactional
public class AdminTeatroServicioTest {

    @Autowired
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

    //--------------------------------------- Horario --------------------------------------------------

    @Test
    @Sql("classpath:dataset.sql")
    public void crearHorarioTest(){

        Horario horario = Horario.builder().dia("LJV").fechaFin(LocalDate.parse("2022-11-11")).fechaInicio(LocalDate.parse("2022-10-11")).hora(LocalTime.parse("13:00")).build();
        Horario nuevo   = adminTeatroServicio.crearHorario(horario);
        Assertions.assertEquals(nuevo, horario);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerHorarioTest(){

        try {
            Horario horario = adminTeatroServicio.obtenerHorario(1);
            Assertions.assertTrue(true);
        }catch (Exception e){
            throw new RuntimeException();
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarHorarioTest(){
        List<Horario> horarioList = adminTeatroServicio.listarHorarios();
        Assertions.assertEquals(5, horarioList.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarHorarioTest(){

        try {
            adminTeatroServicio.eliminarHorario(1);
        }catch (Exception e){
            Assertions.assertTrue(false);
        }

        try {
            adminTeatroServicio.obtenerHorario(1);
        }catch (Exception e){
            Assertions.assertTrue(true);
        }

    }
}
