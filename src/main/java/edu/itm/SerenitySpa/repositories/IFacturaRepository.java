package edu.itm.SerenitySpa.repositories;

import edu.itm.SerenitySpa.identities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacturaRepository extends JpaRepository<Factura, Integer> {

    boolean existsByCitaIdCita(int idCita);
}