package kbase.repository;

import kbase.domain.ChaveErro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ChaveErro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChaveErroRepository extends JpaRepository<ChaveErro, Long>, JpaSpecificationExecutor<ChaveErro> {

}
