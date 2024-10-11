package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.ResumenCuponDTO;
import co.edu.uniquindio.proyecto.dto.deseoDTO.AgregarDeseoDTO;
import co.edu.uniquindio.proyecto.dto.deseoDTO.ResumenDeseoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.FiltroEventoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.InformacionEventoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.ItemEventoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.ResumenEventoDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.AgregarAlCarritoDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.OrdenCompraDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.ResumenCarritoDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.ResumenOrdenDTO;
import co.edu.uniquindio.proyecto.model.docs.Valoracion;
import co.edu.uniquindio.proyecto.services.interfaces.*;
import com.mercadopago.resources.preference.Preference;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cliente")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    @Autowired
    private CarritoService carritoService;
    @Autowired
    private DeseoService deseoService;
    @Autowired
    private EventoService eventoService;
    @Autowired
    private OrdenService ordenService;
    @Autowired
    private ValoracionService valoracionService;

    @PostMapping("/agregar-carrito")
    public ResponseEntity<MensajeDTO<String>> agregarAlCarrito(@Valid @RequestBody AgregarAlCarritoDTO agregarAlCarritoDTO) throws Exception {
        String mensaje = carritoService.agregarAlCarrito(agregarAlCarritoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @DeleteMapping("/eliminar-carrito/{idCuenta}/{idEntrada}")
    public ResponseEntity<MensajeDTO<String>> eliminarDelCarrito(@PathVariable ObjectId idCuenta, @PathVariable ObjectId idEntrada) throws Exception {
        carritoService.eliminarDelCarrito(idCuenta, idEntrada);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Ítem eliminado del carrito exitosamente"));
    }

    @GetMapping("/listar-carrito/{idCuenta}")
    public ResponseEntity<MensajeDTO<List<ResumenCarritoDTO>>> listarCarrito(@PathVariable ObjectId idCuenta) throws Exception {
        List<ResumenCarritoDTO> lista = carritoService.listarCarrito(idCuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }


    @PostMapping("/agregar-deseo")
    public ResponseEntity<MensajeDTO<String>> agregarDeseo(@Valid @RequestBody AgregarDeseoDTO deseoDTO) throws Exception {
        String mensaje = deseoService.agregarDeseo(deseoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @DeleteMapping("/eliminar-deseo/{idCuenta}/{idEvento}")
    public ResponseEntity<MensajeDTO<String>> eliminarDeseo(@PathVariable ObjectId idCuenta, @PathVariable ObjectId idEvento) throws Exception {
        deseoService.eliminarDeseo(idCuenta, idEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Deseo eliminado exitosamente"));
    }

    @GetMapping("/listar-deseo/{idCuenta}")
    public ResponseEntity<MensajeDTO<List<ResumenDeseoDTO>>> listarDeseos(@PathVariable ObjectId idCuenta) throws Exception {
        List<ResumenDeseoDTO> lista = deseoService.listarDeseos(idCuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @GetMapping("/buscar-deseo/{idCuenta}/{nombreEvento}")
    public ResponseEntity<MensajeDTO<List<ResumenDeseoDTO>>> buscarDeseos(@PathVariable ObjectId idCuenta, @PathVariable String nombreEvento) throws Exception {
        List<ResumenDeseoDTO> lista = deseoService.buscarDeseos(idCuenta, nombreEvento);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }
    @GetMapping("/obtener-info-evento/{id}")
    public ResponseEntity<MensajeDTO<InformacionEventoDTO>> obtenerInformacionEvento(@PathVariable String id) throws Exception {
        InformacionEventoDTO info = eventoService.obtenerInformacionEvento(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, info));
    }

    @GetMapping("/listar-eventos")
    public ResponseEntity<MensajeDTO<List<ResumenEventoDTO>>> listarEventos() throws Exception {
        List<ResumenEventoDTO> lista = eventoService.listarEventos();
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @PostMapping("/filtrar-eventos")
    public ResponseEntity<MensajeDTO<List<ItemEventoDTO>>> filtrarEventos(@Valid @RequestBody FiltroEventoDTO filtroDTO) throws Exception {
        List<ItemEventoDTO> lista = eventoService.filtrarEventos(filtroDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @GetMapping("/buscar-evento/{nombre}")
    public ResponseEntity<MensajeDTO<List<ResumenEventoDTO>>> buscarEventosPorNombre(@PathVariable String nombre) {
        List<ResumenEventoDTO> lista = eventoService.buscarEventosPorNombre(nombre);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @PostMapping("/enviar-notificaciones")
    public ResponseEntity<MensajeDTO<String>> enviarNotificaciones() {
        deseoService.enviarNotificaciones();
        return ResponseEntity.ok(new MensajeDTO<>(false, "Notificaciones enviadas exitosamente"));
    }

   /* @PostMapping("/realizar-pago/{idOrden}")
    public ResponseEntity<MensajeDTO<Preference>> realizarPago(@PathVariable String idOrden) throws Exception {
        Preference preference = ordenService.realizarPago(idOrden);
        return ResponseEntity.ok(new MensajeDTO<>(false, preference));
    } */
    @PostMapping("/realizar-pago")
    public ResponseEntity<MensajeDTO<Preference>> realizarPago(@RequestParam("idOrden") String idOrden) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, ordenService.realizarPago(idOrden)));
    }



    @PostMapping("/aplicar-descuento/{idCuenta}")
    public ResponseEntity<MensajeDTO<ResumenOrdenDTO>> aplicarDescuento(@PathVariable ObjectId idCuenta, @RequestParam String codigoDescuento) throws Exception {
        ResumenOrdenDTO resumen = ordenService.aplicarDescuento(idCuenta, codigoDescuento);
        return ResponseEntity.ok(new MensajeDTO<>(false, resumen));
    }

    @PostMapping("/generar-orden/{idCuenta}")
    public ResponseEntity<MensajeDTO<OrdenCompraDTO>> generarOrdenCompra(@PathVariable ObjectId idCuenta) throws Exception {
        OrdenCompraDTO orden = ordenService.generarOrdenCompra(idCuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, orden));
    }

    @PostMapping("/confirmar-pago/{idOrden}")
    public ResponseEntity<MensajeDTO<String>> confirmarPago(@PathVariable ObjectId idOrden, @RequestParam String codigoConfirmacion) throws Exception {
        ordenService.confirmarPago(idOrden, codigoConfirmacion);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Pago confirmado exitosamente"));
    }

    @GetMapping("/cupones-utilizados/{idUsuario}")
    public ResponseEntity<MensajeDTO<List<ResumenCuponDTO>>> buscarCuponesUtilizadosPorUsuario(@PathVariable String idUsuario) throws Exception {
        List<ResumenCuponDTO> cupones = ordenService.buscarCuponesUtilizadosPorUsuario(idUsuario);
        return ResponseEntity.ok(new MensajeDTO<>(false, cupones));
    }

    @PostMapping("/notificacion-mercadopago")
    public ResponseEntity<MensajeDTO<String>> recibirNotificacionMercadoPago(@RequestBody Map<String, Object> request) throws Exception {
        ordenService.recibirNotificacionMercadoPago(request);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Notificación recibida exitosamente"));
    }
   /* @PostMapping("/notificacion-pago")
    public void recibirNotificacionMercadoPago(@RequestBody Map<String, Object> requestBody) {
        ordenService.recibirNotificacionMercadoPago(requestBody);
    } */


    @GetMapping("/listar-valoracion/{nombre}")
    public ResponseEntity<MensajeDTO<List<Valoracion>>> listarValoracionByNombre(@PathVariable String nombre) {
        List<Valoracion> valoraciones = valoracionService.listarValoracionByNombre(nombre);
        return ResponseEntity.ok(new MensajeDTO<>(false, valoraciones));
    }

    @PostMapping("/crear-valoracion")
    public ResponseEntity<MensajeDTO<String>> crearValoracion(@Valid @RequestBody Valoracion valoracion) {
        valoracionService.crearValoracion(valoracion);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Valoración creada exitosamente"));
    }

    @GetMapping("/obtener-valoracion/{usuarioId}")
    public ResponseEntity<MensajeDTO<List<Valoracion>>> obtenerValoracionByUsuarioID(@PathVariable String usuarioId) {
        List<Valoracion> valoraciones = valoracionService.obtenerValoracionByUsuarioID(usuarioId);
        return ResponseEntity.ok(new MensajeDTO<>(false, valoraciones));
    }
}
