package co.edu.uniquindio.proyecto.config;

import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.model.enums.TipoUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class FiltroToken extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {


        String requestURI = request.getRequestURI();

        if (requestURI.equals("/")  || requestURI.startsWith("/health")
                || requestURI.startsWith("/actuator") || requestURI.startsWith("/swagger")
                || requestURI.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        String origin = request.getHeader("Origin");

        if ("http://localhost:4200".equals(origin) || "https://unieventos-site-front.netlify.app".equals(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Lógica del filtro (validación del token)
        String token = getToken(request);
        boolean error = true;

        try {
            if (requestURI.startsWith("/api/cliente")) {
                error = validarToken(token, TipoUsuario.CLIENTE);
            } else if (requestURI.startsWith("/api/administrador")) {
                error = validarToken(token, TipoUsuario.ADMINISTRADOR);
            } else {
                error = false;
            }

            if (error) {
                crearRespuestaError("No tiene permisos para acceder a este recurso", HttpServletResponse.SC_FORBIDDEN, response);
            }

        } catch (MalformedJwtException | SignatureException e) {
            crearRespuestaError("El token es incorrecto", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
        } catch (ExpiredJwtException e) {
            crearRespuestaError("El token está vencido", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
        } catch (Exception e) {
            crearRespuestaError(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
        }

        if (!error) {
            filterChain.doFilter(request, response);
        }
    }

    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        return header != null && header.startsWith("Bearer ") ? header.replace("Bearer ", "") : null;
    }


    private void crearRespuestaError(String mensaje, int codigoError, HttpServletResponse response) throws IOException {
        MensajeDTO<String> dto = new MensajeDTO<>(true, mensaje);


        response.setContentType("application/json");
        response.setStatus(codigoError);
        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
        response.getWriter().flush();
        response.getWriter().close();
    }


    private boolean validarToken(String token, TipoUsuario rol){
        boolean error = true;
        if (token != null) {
            Jws<Claims> jws = jwtUtils.parseJwt(token);
            if (TipoUsuario.valueOf(jws.getPayload().get("rol").toString()) == rol) {
                error = false;
            }
        }
        return error;
    }

}
