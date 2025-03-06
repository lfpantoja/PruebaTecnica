package prueba.tecnica.PruebaTecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prueba.tecnica.PruebaTecnica.model.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}