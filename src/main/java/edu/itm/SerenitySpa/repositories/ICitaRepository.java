package edu.itm.SerenitySpa.repositories;

import edu.itm.SerenitySpa.identities.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ICitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByEmpleadoIdEmpleadoAndFecha(
            int idEmpleado, LocalDate fecha);

    List<Cita> findByClienteIdClienteAndFecha(
            int idCliente, LocalDate fecha);
}
