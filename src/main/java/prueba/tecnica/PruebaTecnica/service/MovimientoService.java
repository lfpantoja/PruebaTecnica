package prueba.tecnica.PruebaTecnica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.tecnica.PruebaTecnica.dto.MovimientoDTO;
import prueba.tecnica.PruebaTecnica.exception.SaldoInsuficienteException;
import prueba.tecnica.PruebaTecnica.model.Cuenta;
import prueba.tecnica.PruebaTecnica.model.Movimiento;
import prueba.tecnica.PruebaTecnica.repository.CuentaRepository;
import prueba.tecnica.PruebaTecnica.repository.MovimientoRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional
    public Movimiento registrarMovimiento(Movimiento movimiento) {
        // Obtener la cuenta asociada al movimiento
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // Calcular el nuevo saldo
        double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();

        // Validar saldo suficiente en caso de retiro
        if (nuevoSaldo < 0) {
            throw new SaldoInsuficienteException("Saldo no disponible");
        }

        // Actualizar el saldo de la cuenta
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        // Guardar el movimiento
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setFecha(LocalDateTime.now());
        return movimientoRepository.save(movimiento);
    }

    public List<MovimientoDTO> obtenerMovimientosPorFechaYClienteId(LocalDate fecha, Long clienteId) {
        List<Movimiento> movimientos = movimientoRepository.findByFechaAndClienteId(fecha, clienteId);

        return movimientos.stream().map(movimiento -> new MovimientoDTO(
                movimiento.getFecha().toLocalDate(),
                movimiento.getCuenta().getCliente().getNombre(),
                movimiento.getCuenta().getNumeroCuenta(),
                movimiento.getCuenta().getTipoCuenta(),
                movimiento.getCuenta().getSaldoInicial() - movimiento.getValor(), // Saldo inicial antes del movimiento
                movimiento.getCuenta().isEstado(),
                movimiento.getValor(),
                movimiento.getSaldo()
        )).collect(Collectors.toList());
    }
}
