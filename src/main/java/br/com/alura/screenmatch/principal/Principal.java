package br.com.alura.screenmatch.principal;


import br.com.alura.screenmatch.model.DadosEpisodeo;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=aa649666";
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i<=dados.totalTemporadas(); i++){
            json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") +"&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

//        for(int i = 0; i < dados.totalTemporadas(); i++) {
//            List<DadosEpisodeo> episodiosTemporada = temporadas.get(i).episodeos();
//
//            for (int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }
//         Isso tudo aqui encima é apenas essa linha de baixo... wtf

        temporadas.forEach(t -> t.episodeos().forEach(e -> System.out.println(e.titulo())));


        List<DadosEpisodeo> dadosEpisodeos = temporadas.stream()
                .flatMap(t -> t.episodeos().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 5 episódeos");
        dadosEpisodeos.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodeo::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);




//------------------------------------------------------------------------------------

//        List<String> nomes = Arrays.asList("Lucas", "Luana", "Maria", "José", "Priscila");
//
//        nomes.stream()
//                .sorted() // Devolve a lista em ordem alfabética.
//                .limit(4) // Devolve a Lista, com o limite fornecido
//                .filter(n -> n.startsWith("M")) // Filtra com a função Lambda, baseado na Letra fornecida.
//                .map(n -> n.toUpperCase())
//                .forEach(System.out::println);


//        List<Integer> numeros = new ArrayList<>();
//
//        for (int i = 0; i < 100; i++) {
//            numeros.add(i);
//        }
//
//        List<Integer> numerosPares = numeros.stream()
//                .filter(n -> n % 2 == 0)
//                .collect(Collectors.toList());
//
//        List<Integer> somarNumeros = Collections.singletonList(numeros.stream()
//                .mapToInt(Integer::intValue)
//                .sum());
//
//        List<Integer> numerosImpares = numeros.stream()
//                .filter(n -> n % 2 != 0)
//                .collect(Collectors.toList());
//
//        System.out.println(numerosPares);
//
//        System.out.println(numerosImpares);
//
//        System.out.println(somarNumeros);

    }
}