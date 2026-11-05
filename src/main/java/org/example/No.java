package org.example;

/**
 * Classe que guarda os valores do nó (usuário)
 */
public class No {
    String chave;   // nome do usuário
    String valor;   // senha
    No proximo;     // próximo nó da lista no mesmo índice

    /**
     * @param chave
     * Chave = nome do usuário
     * @param valor
     * Valor = senha
     * */
    public No(String chave, String valor) {
        this.chave = chave;
        this.valor = valor;
        this.proximo = null;
    }
}
