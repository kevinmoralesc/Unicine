package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@ViewScoped
public class compraBean implements Serializable{

    @Autowired
    private AdminServicio adminServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Value("#{param['d']}")
    private String diaSeleccionado;

    @Value("#{param['m']}")
    private String mesSeleccionado;

    @Value("#{param['a']}")
    private String anioSeleccionado;

    @Getter @Setter
    private LocalDateTime fechaSeleccionada;

    @Getter @Setter
    private List<Confiteria> confiterias;

    @Getter @Setter
    private ArrayList<CompraConfiteria> confiteriasFormulario;

    @Getter @Setter
    private ArrayList<Entrada> entradas;

    @Getter @Setter
    private ArrayList<Entrada> seleccionadas;
    @Getter @Setter
    private Cliente cliente;

    @Getter @Setter
    private DistribucionSillas distribucionSillas;

    @Getter @Setter
    private Funcion funcion;

    @Getter @Setter
    private ArrayList<Integer> filas, columnas;

    @Getter @Setter
    private Double precioCompra;

    @Getter @Setter
    private String funcionCodigo;

    @Getter @Setter
    private List<MedioPago> medioPagos;

    @Getter @Setter
    private MedioPago medioPago;

    @Getter @Setter
    private MedioPago medioPagoSeleccionado;

    @Getter @Setter
    private Integer codigoCupon;

    @PostConstruct
    public void init(){

        seleccionadas = new ArrayList<>();
        confiterias = adminServicio.listarConfiteria();
        confiteriasFormulario = new ArrayList<>();
        medioPagos = Arrays.asList(MedioPago.values());
        filas = new ArrayList<>();
        columnas = new ArrayList<>();
        entradas = new ArrayList<>();
        precioCompra = 0D;
        funcionCodigo = "1";
        confiterias.forEach(c -> {
            confiteriasFormulario.add(CompraConfiteria.builder().confiteria(c).precio(c.getPrecio()).unidades(0).build()); // revisar
        });

        try {
            cliente = clienteServicio.obtenerCliente(1);
            if (funcionCodigo != null && !funcionCodigo.isEmpty()){
                funcion = adminTeatroServicio.obtenerFuncion(Integer.parseInt(funcionCodigo));
                crearDistribucionSala();
            }
            if(diaSeleccionado != null && mesSeleccionado != null & anioSeleccionado != null){
                String[] hora = String.valueOf(funcion.getHorario().getHora()).split(":");
                fechaSeleccionada = LocalDateTime.of(Integer.parseInt(anioSeleccionado),Integer.parseInt(mesSeleccionado), Integer.parseInt(diaSeleccionado), Integer.parseInt(hora[0]),Integer.parseInt(hora[1]));
            }
        }catch (Exception e){
            throw  new RuntimeException();
        }
    }

    private void crearDistribucionSala() {
        distribucionSillas = funcion.getSala().getDistribucionSillas();
        String esquema = distribucionSillas.getEsquema();
        for(int i = 0; i < distribucionSillas.getFilas(); i++){
            filas.add(i);
        }
        for(int i = 0; i < distribucionSillas.getColumnas() ; i++){
            columnas.add(i);
        }
    }

    public void seleccionarSilla(Integer fila, Integer columna){
        if(!buscarSilla(fila, columna)) {
            Entrada entrada = new Entrada();
            entrada.setFila(fila);
            entrada.setColumna(columna);
            seleccionadas.add(entrada);
        }
        else{
            for (int i = 0; i < seleccionadas.size(); i++) {
                Entrada e = seleccionadas.get(i);

                if(e.getColumna() == columna && e.getFila() == fila){
                    seleccionadas.remove(i);
                }
            }
        }
    }

    /**
     * busca una silla
     * @param fila
     * @param columna
     * @return true si la entrada existe, false si no
     */
    public boolean buscarSilla(Integer fila, Integer columna){

        for(Entrada e : seleccionadas){
            if(e.getColumna() == columna && e.getFila() == fila){
                return true;
            }
        }
        return false;
    }

    public void restarUnidades(Integer cantidad){

    }

    public void sumarUnidades(Integer cantidad){

    }

    public void obtenerDetalleEntradas(){

    }

    public void obtenerDetalleConfites(){

    }

    public void hacerCompra(){

    }

    public void obtenerUnidadesConfites(Integer cantidad){

    }


}
