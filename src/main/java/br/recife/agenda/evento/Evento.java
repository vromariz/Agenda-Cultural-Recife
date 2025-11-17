package br.recife.agenda.evento;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do evento é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;

    @NotBlank(message = "A descrição do evento é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotBlank(message = "O local do evento é obrigatório")
    @Column(nullable = false)
    private String local;

    @NotNull(message = "A data do evento é obrigatória")
    @Column(name = "data_evento", nullable = false)
    private String dataEvento;

    @NotBlank(message = "A categoria do evento é obrigatória")
    @Column(nullable = false)
    private String categoria;
}

