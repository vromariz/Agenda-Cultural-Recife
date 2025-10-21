package br.recife.agenda.evento;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String nome;

    private String descricao;

    private String local;

    @Column(name = "data_evento")
    private String dataEvento;

    private String categoria;
}
