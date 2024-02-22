package es.dispenser.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import es.dispenser.domain.Dispenser;
import es.dispenser.domain.Draft;

public interface IDraftRepository extends JpaRepository<Draft, UUID>{

}
