package br.dev.andresantos.parkapi.repository;

import br.dev.andresantos.parkapi.entity.Cliente;
import br.dev.andresantos.parkapi.repository.projection.ClienteProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

 @Query("select c from Cliente c")
 Page<ClienteProjection> findAllPegeable(Pageable pageable);

 Cliente findByUsuarioId(Long id);
}
