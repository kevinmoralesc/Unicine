package co.edu.uniquindio.unicine.dto;

import co.edu.uniquindio.unicine.entidades.Funcion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class InformacionCompraDTO {

    @Positive
    private Float precioTotal;
    private LocalDateTime fecha;
    private Funcion funcion;
    private Double precioEntradas;
    private Double precioConfiteria;
}
