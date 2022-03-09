package br.com.letscode.java;

public class Premio {

    private final int idade;
    private final String nomeDoFilme;
    private final int anoDaPremiacao;

    public Premio(int idade, String nomeDoFilme, int anoDaPremiacao) {
        this.idade = idade;
        this.nomeDoFilme = nomeDoFilme;
        this.anoDaPremiacao = anoDaPremiacao;
    }

    @Override
    public String toString() {
        return "Idade: " + idade +
                ", Nome do Filme: '" + nomeDoFilme + '\'' +
                ", Ano da Premiação: " + anoDaPremiacao;
    }
}
