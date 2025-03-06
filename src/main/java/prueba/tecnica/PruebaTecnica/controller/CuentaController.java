package prueba.tecnica.PruebaTecnica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.tecnica.PruebaTecnica.model.Cuenta;
import prueba.tecnica.PruebaTecnica.service.CuentaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    // Obtener todas las cuentas
    @GetMapping
    public List<Cuenta> listarCuentas() {
        return cuentaService.listarCuentas();
    }

    // Obtener una cuenta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuenta(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaService.obtenerCuentaPorId(id);
        return cuenta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva cuenta
    @PostMapping
    public Cuenta crearCuenta(@RequestBody Cuenta cuenta) {
        return cuentaService.guardarCuenta(cuenta);
    }

    // Actualizar una cuenta existente
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuentaDetalles) {
        return cuentaService.obtenerCuentaPorId(id).map(cuenta -> {
            cuenta.setNumeroCuenta(cuentaDetalles.getNumeroCuenta());
            cuenta.setTipoCuenta(cuentaDetalles.getTipoCuenta());
            cuenta.setSaldoInicial(cuentaDetalles.getSaldoInicial());
            cuenta.setEstado(cuentaDetalles.isEstado());
            cuenta.setCliente(cuentaDetalles.getCliente());

            Cuenta cuentaActualizada = cuentaService.guardarCuenta(cuenta);
            return ResponseEntity.ok(cuentaActualizada);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una cuenta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        try {
            cuentaService.eliminarCuenta(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
