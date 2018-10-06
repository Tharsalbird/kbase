package kbase.repository;

import kbase.domain.Rotulo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rotulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RotuloRepository extends JpaRepository<Rotulo, Long> {

}
