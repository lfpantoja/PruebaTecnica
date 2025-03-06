package prueba.tecnica.PruebaTecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prueba.tecnica.PruebaTecnica.model.Movimiento;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query("SELECT m FROM Movimiento m " +
            "JOIN m.cuenta c " +
            "JOIN c.cliente cl " +
            "WHERE cl.clienteId = :clienteId " +
            "AND DATE(m.fecha) = :fecha")
    List<Movimiento> findByFechaAndClienteId(@Param("fecha") LocalDate fecha, @Param("clienteId") Long clienteId);
}
