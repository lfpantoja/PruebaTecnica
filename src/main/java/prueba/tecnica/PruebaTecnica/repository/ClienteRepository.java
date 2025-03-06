package prueba.tecnica.PruebaTecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prueba.tecnica.PruebaTecnica.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}