package prueba.tecnica.PruebaTecnica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba.tecnica.PruebaTecnica.exception.ResourceNotFoundException;
import prueba.tecnica.PruebaTecnica.model.Cuenta;
import prueba.tecnica.PruebaTecnica.repository.CuentaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    // Obtener todas las cuentas
    public List<Cuenta> listarCuentas() {
        return cuentaRepository.findAll();
    }

    // Obtener cuenta por ID
    public Optional<Cuenta> obtenerCuentaPorId(Long id) {
        return cuentaRepository.findById(id);
    }

    // Crear o actualizar una cuenta
    public Cuenta guardarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    // Eliminar una cuenta
    public void eliminarCuenta(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cuenta con ID " + id + " no encontrada");
        }
        cuentaRepository.deleteById(id);
    }
}

