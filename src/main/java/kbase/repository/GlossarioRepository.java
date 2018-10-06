package kbase.repository;

import kbase.domain.Glossario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Glossario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GlossarioRepository extends JpaRepository<Glossario, Long> {

}
