package kbase.repository;

import kbase.domain.Secao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Secao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecaoRepository extends JpaRepository<Secao, Long>, JpaSpecificationExecutor<Secao> {

}
