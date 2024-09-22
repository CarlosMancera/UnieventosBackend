package co.edu.uniquindio.proyecto.services.interfaces;

import java.util.Map;
import com.mercadopago.resources.preference.Preference;
public interface OrdenService {

    //TODO Todos los demás métodos que se van a implementar…


    Preference realizarPago(String idOrden) throws Exception;
    void recibirNotificacionMercadoPago(Map<String, Object> request);
}
