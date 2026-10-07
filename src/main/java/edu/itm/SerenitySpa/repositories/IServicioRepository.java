package edu.itm.SerenitySpa.repositories;

import edu.itm.SerenitySpa.identities.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServicioRepository extends JpaRepository<Servicio, Integer> {
}