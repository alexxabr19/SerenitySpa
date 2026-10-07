package edu.itm.SerenitySpa.repositories;

import edu.itm.SerenitySpa.identities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
}
