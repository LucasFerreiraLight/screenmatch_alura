package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodeo;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

			var consumoApi = new ConsumoApi();
			var json = consumoApi.obterDados(
					"https://www.omdbapi.com/?t=gilmore+girls&apikey=aa649666");
			System.out.println(json);
		// Dados Serie
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

        json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=aa649666");

        // Dados Episodeo
        DadosEpisodeo dadosEpisodeo = conversor.obterDados(json, DadosEpisodeo.class);
        System.out.println(dadosEpisodeo);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<=dados.totalTemporadas(); i++) {
			json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=aa649666");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
			}
			temporadas.forEach(System.out::println);
		}
}
