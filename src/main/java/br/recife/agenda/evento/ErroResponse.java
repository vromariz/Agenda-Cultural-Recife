package br.recife.agenda.evento;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroResponse {
    private int status;
    private String error;
    private String message;
}
