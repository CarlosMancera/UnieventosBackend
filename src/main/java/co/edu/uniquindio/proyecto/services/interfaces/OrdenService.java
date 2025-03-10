package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.cuponDTO.ResumenCuponDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.OrdenCompraDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.PagoDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.ResumenOrdenDTO;
import com.mercadopago.resources.preference.Preference;

import java.util.List;
import java.util.Map;

public interface OrdenService {

    Preference realizarPago(Long idOrden) throws Exception;

    ResumenOrdenDTO aplicarDescuento(Long idCuenta, String codigoDescuento) throws Exception;

    OrdenCompraDTO generarOrdenCompra(Long idCuenta) throws Exception;

    void confirmarPago(Long idOrden, String codigoConfirmacion) throws Exception;

    List<ResumenCuponDTO> buscarCuponesUtilizadosPorUsuario(Long idUsuario) throws Exception;

    void recibirNotificacionMercadoPago(Map<String, Object> request) throws Exception;
}
