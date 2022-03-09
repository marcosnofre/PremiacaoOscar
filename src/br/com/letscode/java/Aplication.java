package br.com.letscode.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aplication {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        final var premiacao = new Aplication();

        System.out.println("1 - Qual foi o ator mais jovem a ganhar um Oscar?");

        var atorMaisNovo = new Aplication().lerArquivo(Paths.get("oscar_age_male.csv"))
                .stream()
                .min(Comparator.comparing(ator -> ator.split("; ")[2]))
                .map(linha -> linha.split("; ")[3] + " " + linha.split("; ")[2]);

        atorMaisNovo.ifPresent(System.out::println);
        System.out.println();

        System.out.println("2 - Qual atriz que mais vezes foi vencedora?");

        int maiorQuantidadeDePremios = premiacao.lerArquivo(Paths.get("oscar_age_female.csv"))
                .stream()
                .map(linha -> linha.split("; "))
                .filter(linha -> Integer.parseInt(linha[2]) >= 20 && Integer.parseInt(linha[2]) <= 30)
                .collect(Collectors.groupingBy(linha -> linha[3]))
                .entrySet()
                .stream()
                .max(Comparator.comparing(entry -> entry.getValue().size()))
                .map(it -> it.getValue().size())
                .orElse(-1);

        var atrizMaisPremiada = premiacao.lerArquivo(Paths.get("oscar_age_female.csv"))
                .stream()
                .map(linha -> linha.split("; "))
                .collect(Collectors.groupingBy(linha -> linha[3]))
                .entrySet()
                .stream()
                .filter(it -> it.getValue().size() == maiorQuantidadeDePremios)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println(atrizMaisPremiada);
        System.out.println();

        System.out.println("3 - Qual atriz entre 20 e 30 anos que mais vezes foi vencedora?");

        var atrizMaisPremiadaEntre20e30 = premiacao.lerArquivo(Paths.get("oscar_age_female.csv"))
                .stream()
                .map(linha -> linha.split("; "))
                .filter(linha -> Integer.parseInt(linha[2]) >= 20 && Integer.parseInt(linha[2]) <= 30)
                .collect(Collectors.groupingBy(linha -> linha[3]))
                .entrySet()
                .stream()
                .filter(it -> it.getValue().size() == maiorQuantidadeDePremios)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println(atrizMaisPremiadaEntre20e30);
        System.out.println();

        System.out.println("4 - Quais atores ou atrizes receberam mais de um Oscar?");

        var vencedoresMaisDeUmaVez = Stream.concat(premiacao.lerArquivo(Paths.get("oscar_age_female.csv")).stream(),
                        premiacao.lerArquivo(Paths.get("oscar_age_male.csv")).stream())
                .map(linha -> linha.split("; "))
                .collect(Collectors.groupingBy(linha -> linha[3]))
                .entrySet()
                .stream()
                .filter(it -> it.getValue().size() >= 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println(vencedoresMaisDeUmaVez);
        System.out.println();

        System.out.println("5 - Quando informado o nome de um ator ou atriz, dê um resumo de quantos prêmios ele/ela recebeu e liste ano, idade e nome de cada filme pelo qual foi premiado(a).");
        System.out.print("Informe o ator ou atriz desejado: ");
        var nome = sc.nextLine();
        var premiosDaAtriz = Stream.concat(premiacao.lerArquivo(Paths.get("oscar_age_female.csv")).stream(),
                        premiacao.lerArquivo(Paths.get("oscar_age_male.csv")).stream())
                .map(linha -> linha.split("; "))
                .filter(it -> it[3].equals(nome))
                .collect(Collectors.toList());

        var numeroDePremios = premiosDaAtriz.size();

        var premios = premiosDaAtriz.stream()
                .map(it -> new Premio(Integer.parseInt(it[2]), it[4], Integer.parseInt(it[1])))
                .collect(Collectors.toList());

        System.out.println("Quantidade de premios: " + numeroDePremios + " " + premios);
        sc.close();
    }

    public List<String> lerArquivo(Path path) throws IOException {
        try (var linhas = Files.lines(path).skip(1)) {
            return linhas.collect(Collectors.toList());
        }
    }
}
