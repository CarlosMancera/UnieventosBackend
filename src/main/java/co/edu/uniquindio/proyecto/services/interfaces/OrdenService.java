package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.cuponDTO.ResumenCuponDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.OrdenCompraDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.PagoDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.ResumenCarritoDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.ResumenOrdenDTO;
import com.mercadopago.resources.preference.Preference;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface OrdenService {

    Preference realizarPago(String idOrden) throws Exception;

    List<ResumenCarritoDTO> listarCarrito(ObjectId idCuenta) throws Exception;

    ResumenOrdenDTO aplicarDescuento(ObjectId idCuenta, String codigoDescuento) throws Exception;

    OrdenCompraDTO generarOrdenCompra(ObjectId idCuenta) throws Exception;

    void confirmarPago(ObjectId idOrden, String codigoConfirmacion) throws Exception;

    List<ResumenCuponDTO> buscarCuponesUtilizadosPorUsuario(String idUsuario) throws Exception;

    void recibirNotificacionMercadoPago(Map<String, Object> request) throws Exception;
}
