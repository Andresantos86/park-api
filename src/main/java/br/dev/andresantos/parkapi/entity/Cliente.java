package br.dev.andresantos.parkapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "clientes")
@EntityListeners(AuditingEntityListener.class)
public class Cliente implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "nome", nullable = false, length = 100)
  private String nome;
  @Column(name = "cpf", nullable = false, unique = true, length = 11)
  private String cpf;
  @OneToOne
  @JoinColumn(name = "id_usuario", nullable = false)
  private Usuario usuario;

  @CreatedDate
  @Column(name = "dataCriacao")
  private LocalDateTime dataCriacao;

  @LastModifiedDate
  @Column(name = "dataModificacao")
  private LocalDateTime dataModificacao;

  @CreatedBy
  @Column(name = "criadoPor")
  private String criadoPor;

  @LastModifiedBy
  @Column(name = "modificadoPor")
  private String modificadoPor;
}
