package kbase.repository;

import kbase.domain.Registro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Registro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long>, JpaSpecificationExecutor<Registro> {

    @Query(value = "select distinct registro from Registro registro left join fetch registro.rotulos",
        countQuery = "select count(distinct registro) from Registro registro")
    Page<Registro> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct registro from Registro registro left join fetch registro.rotulos")
    List<Registro> findAllWithEagerRelationships();

    @Query("select registro from Registro registro left join fetch registro.rotulos where registro.id =:id")
    Optional<Registro> findOneWithEagerRelationships(@Param("id") Long id);

}
