package prueba.tecnica.PruebaTecnica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.tecnica.PruebaTecnica.dto.MovimientoDTO;
import prueba.tecnica.PruebaTecnica.exception.SaldoInsuficienteException;
import prueba.tecnica.PruebaTecnica.model.Movimiento;
import prueba.tecnica.PruebaTecnica.service.MovimientoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<?> registrarMovimiento(@RequestBody Movimiento movimiento) {
        try {
            Movimiento nuevoMovimiento = movimientoService.registrarMovimiento(movimiento);
            return ResponseEntity.ok(nuevoMovimiento);
        } catch (SaldoInsuficienteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error interno al registrar el movimiento"));
        }
    }


    @GetMapping("/buscar")
    public List<MovimientoDTO> obtenerMovimientos(
            @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
            @RequestParam("clienteId") Long clienteId) {
        return movimientoService.obtenerMovimientosPorFechaYClienteId(fecha, clienteId);
    }
}

