package es.dispenser.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import es.dispenser.domain.Dispenser;

public interface IDispenserRepository extends JpaRepository<Dispenser, UUID>{

}
