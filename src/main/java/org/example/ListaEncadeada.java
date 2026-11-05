package org.example;

/**
 * Lista simplesmente encadeada de pares usuário -> senha.
 * Cada posição da tabela hash guarda uma instância desta lista.
 */
public class ListaEncadeada {
    private No inicio;
    private int tamanho;

    /**
     * Construtor da lista encadeada
     */
    public ListaEncadeada() {
        this.inicio = null;
        this.tamanho = 0;
    }

    /**
     * Insere no início da lista (O(1)). Não verifica duplicidade.
     * @param chave nome do usuário
     * @param valor senha do usuário
     */
    public void inserirInicio(String chave, String valor) {
        No novo = new No(chave, valor);
        novo.proximo = inicio;
        inicio = novo;
        tamanho++;
    }

    /**
     * Retorna o nó com a chave informada, ou null se não existir
     * @param chave nome do usuário
     * @return null
     */
    public No buscar(String chave) {
        No atual = inicio;
        while (atual != null) {
            if (atual.chave.equals(chave)) {
                return atual;
            }
            atual = atual.proximo;
        }
        return null;
    }

    /**
     * Verifica se a chave informada está na lista encadeada
     * @param chave nome do usuário
     * @return chave
     */
    public boolean contem(String chave) {
        return buscar(chave) != null;
    }

    /**
     * Remove o nó com a chave informada. Retorna false se não encontrou.
     * @param chave nome do usuário
     * @return bool
     */
    public boolean remover(String chave) {
        No atual = inicio;
        No anterior = null;

        while (atual != null) {
            if (atual.chave.equals(chave)) {
                if (anterior == null) {
                    inicio = atual.proximo; // removendo o primeiro
                } else {
                    anterior.proximo = atual.proximo;
                }
                tamanho--;
                return true;
            }
            anterior = atual;
            atual = atual.proximo;
        }
        return false;
    }

    /**
     * Verifica se a lista está vazia
     * @return null
     */
    public boolean estaVazia() {
        return inicio == null;
    }

    /**
     * Mostra o tamanho da lista
     * @return tamanho
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * Mostra a lista no formato: ana -> bruno -> null
     */
    public void exibir() {
        No atual = inicio;
        while (atual != null) {
            System.out.print(atual.chave + " -> ");
            atual = atual.proximo;
        }
        System.out.println("null");
    }
}
