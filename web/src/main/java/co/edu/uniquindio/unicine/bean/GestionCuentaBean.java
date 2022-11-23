package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

@Component
@ViewScoped
public class GestionCuentaBean implements Serializable {

    @Autowired
    private ClienteServicio clienteServicio;

    @Value("#{param['p1']}")
    private String param1;

    @Value("#{param['p2']}")
    private String param2;

    @Getter @Setter
    private String mensaje = "Verificando su cuenta";

    @PostConstruct
    public void inicializar(){
        System.out.printf(""+param1);
        System.out.println(""+param2);
        if (param1 != null && !param1.isEmpty() && param2 != null && !param2.isEmpty()){
            System.out.println("entro");
            try {
                clienteServicio.activarCuentaCliente(param1, param2);
                mensaje = "Cuenta verificada con exito";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
