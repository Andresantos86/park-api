package br.dev.andresantos.parkapi.repository;

import br.dev.andresantos.parkapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
