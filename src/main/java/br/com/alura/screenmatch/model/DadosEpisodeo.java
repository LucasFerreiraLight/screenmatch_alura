package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)// Ignorar tudo oque não foi pedido de atributo!!
public record DadosEpisodeo(
        @JsonAlias("Title") String titulo,

        @JsonAlias("Episode") Integer numeroEpisodeo,

        @JsonAlias("imdbRating") String avaliacao,

        @JsonAlias("Released") String dataDeLancamento
) {
}
