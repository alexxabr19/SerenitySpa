package edu.itm.SerenitySpa.repositories;

import edu.itm.SerenitySpa.identities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer> {
}


