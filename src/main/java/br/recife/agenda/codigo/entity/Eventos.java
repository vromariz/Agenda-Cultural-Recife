package br.recife.agenda.codigo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Eventos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do evento é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "Local é obrigatório")
    private String local;

    @Column(name = "data_evento", nullable = false)
    private LocalDate data;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;
    
    private String bairro;
    
    @Column(name = "preco")
    private Double preco;

    // 🧠 Relacionamento com o usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;
}
