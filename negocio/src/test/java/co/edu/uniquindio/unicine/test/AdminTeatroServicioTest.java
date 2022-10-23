package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.*;
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
import java.util.Optional;

@SpringBootTest
@Transactional
public class AdminTeatroServicioTest {

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    //------------------------------------------------- Admin Teatro -------------------------------------------------

    @Test
    @Sql("classpath:dataset.sql")
    public void loginTest(){
        try {
            AdministradorTeatro administradorTeatro = adminTeatroServicio.loginAdmin("adminTeatro1@email.com","grfrf");
            Assertions.assertNotNull(administradorTeatro);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    @Sql("classpath:dataset.sql")
    public void recuperarPassword()  {

        try {
            adminTeatroServicio.recuperarPassword("adminTeatro1@email.com");
            Assertions.assertTrue(true);
        }catch (Exception e){

            throw new RuntimeException(e);

        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarPassword()  {

        try {
            AdministradorTeatro administradorTeatro = adminTeatroServicio.obtenerAdministradorTeatro(1);
            adminTeatroServicio.actualizarPassword(administradorTeatro.getCodigo(),"12345","grfrf");
            Assertions.assertEquals("12345",administradorTeatro.getPassword());
        }catch (Exception e){

            throw new RuntimeException(e);

        }

    }

    //--------------------------------------- Teatro --------------------------------------------------
    @Test
    @Sql("classpath:dataset.sql")
    public void crearTeatroTest() {
        try {

            AdministradorTeatro administradorTeatro = adminTeatroServicio.obtenerAdministradorTeatro(1);
            Ciudad ciudad = adminTeatroServicio.obtenerCiudad(1);
            Teatro teatro = Teatro.builder().direccion("Carrera 33 # 2-3").telefono("7314346").administrador(administradorTeatro).ciudad(ciudad).build();
            Teatro nuevo = adminTeatroServicio.crearTeatro(teatro);
            Assertions.assertEquals(nuevo, teatro);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
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

    //--------------------------------------- Sala --------------------------------------------------

    @Test
    @Sql("classpath:dataset.sql")
    public void crearSalaTest(){
        try {
            Teatro teatro = adminTeatroServicio.obtenerTeatro(1);
            DistribucionSillas distribucionSillas = adminTeatroServicio.obtenerDistribucionSilla(1);

            Sala sala = Sala.builder().teatro(teatro).nombre("Sala A XD").distribucionSillas(distribucionSillas).build();
            Sala nuevo = adminTeatroServicio.crearSala(sala);
            Assertions.assertEquals(nuevo,sala);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerSalaTest(){

        try {
            Sala sala = adminTeatroServicio.obtenerSala(1);
            Assertions.assertTrue(true);
        }catch (Exception e){
            throw new RuntimeException();
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarSalaTest(){
        List<Sala> salaList = adminTeatroServicio.listarSalas();
        Assertions.assertEquals(6, salaList.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarSalaTest(){

        try {
            adminTeatroServicio.eliminarSala(1);
        }catch (Exception e){
            Assertions.assertTrue(false);
        }

        try {
            adminTeatroServicio.obtenerSala(1);
        }catch (Exception e){
            Assertions.assertTrue(true);
        }

    }
    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarSalaTest() {

        try {
            Sala sala = adminTeatroServicio.obtenerSala(1);
            sala.setNombre("Sala AA");
            Sala nuevo = adminTeatroServicio.actualizarSala(sala);
            Assertions.assertEquals("Sala AA", nuevo.getNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
