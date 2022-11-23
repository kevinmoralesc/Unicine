package co.edu.uniquindio.unicine.config;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DatosIniciales implements CommandLineRunner {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private AdminServicio adminServicio;

    private AdminTeatroServicio adminTeatroServicio;



    @Override
    public void run(String... args) throws Exception {

        List<Ciudad> ciudades = adminServicio.listarCiudades();

        if(ciudades.isEmpty()){

            Ciudad c1 = Ciudad.builder().nombre("Armenia").build();
            Ciudad c2 = Ciudad.builder().nombre("Pereira").build();

            adminServicio.crearCiudad(c1);
            adminServicio.crearCiudad(c2);

            adminServicio.crearAdminTeatro(AdministradorTeatro.builder().nombre("Admin teatro1").correo("adminteatro1@email.com").password("1234").build());
            adminServicio.crearAdmin(Administrador.builder().nombre("Admin 1").correo("admin1@email.com").password("1234").build());

            adminTeatroServicio.crearHorario(Horario.builder().dia("Lunes").fechaInicio(LocalDate.parse("2022-10-06")).fechaFin(LocalDate.parse("2022-12-16")).hora("20:00") .build());
            adminTeatroServicio.crearHorario(Horario.builder().dia("Martes").fechaInicio(LocalDate.parse("2022-10-06")).fechaFin(LocalDate.parse("2022-11-16")).hora("19:30") .build());

            adminTeatroServicio.crearDistribucionSilla(DistribucionSillas.builder().totalSillas(25).esquema("ruta").filas(5).columnas(25).build());
            adminTeatroServicio.crearDistribucionSilla(DistribucionSillas.builder().totalSillas(100).esquema("ruta").filas(10).columnas(100).build());
            adminTeatroServicio.crearDistribucionSilla(DistribucionSillas.builder().totalSillas(5).esquema("ruta").filas(15).columnas(75).build());
            adminTeatroServicio.crearDistribucionSilla(DistribucionSillas.builder().totalSillas(8).esquema("ruta").filas(10).columnas(80).build());
            adminTeatroServicio.crearDistribucionSilla(DistribucionSillas.builder().totalSillas(9).esquema("ruta").filas(12).columnas(108).build());







        }

    }
}
