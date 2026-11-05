package org.example;

/**
 * Tabela hash em que cada posição do vetor é uma lista simplesmente
 * encadeada (encadeamento separado para tratar colisões).
 */
public class TabelaHash {
    /**
     * Objeto com os valores do usuário
     */
    private ListaEncadeada[] tabela;
    /**
     * Tamanho da tabela hash
     */
    private int capacidade;
    /**
     * Quantidade total de usuários cadastrados
     */
    private int tamanho; //
    /**
     * Construtor da tabela hash
     * @param capacidade tamanho da tabela hash
     */
    public TabelaHash(int capacidade) {
        this.capacidade = capacidade;
        this.tabela = new ListaEncadeada[capacidade];
        // cada item do vetor começa como uma lista vazia
        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new ListaEncadeada();
        }
        this.tamanho = 0;
    }

    /**
     * Função hash: transforma o nome em um índice do vetor
     * @param chave nome do usuário
     * @return soma
     */
    private int hash(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma = (soma * 31 + chave.charAt(i)) % capacidade;
        }
        return soma;
    }

    /**
     * Cadastro: retorna false se o usuário já existe
     * @param chave nome do usuário
     * @param valor senha do usuário
     * @return bool
     */
    public boolean inserir(String chave, String valor) {
        ListaEncadeada lista = tabela[hash(chave)];
        if (lista.contem(chave)) {
            return false;
        }
        lista.inserirInicio(chave, valor);
        tamanho++;
        return true;
    }

    /**
     * Verificação de existência
     * @param chave nome do usuário
     * @return bool
     */
    public boolean existe(String chave) {
        return tabela[hash(chave)].contem(chave);
    }

    /**
     * Autenticação: usuário existe E senha confere
     * @param chave nome do usuário
     * @param senha senha do usuário
     * @return bool
     */
    public boolean autenticar(String chave, String senha) {
        No no = tabela[hash(chave)].buscar(chave);
        return no != null && no.valor.equals(senha);
    }

    /**
     * Remoção: retorna false se o usuário não existe
     * @param chave nome do usuário
     * @return bool
     */
    public boolean remover(String chave) {
        boolean removido = tabela[hash(chave)].remover(chave);
        if (removido) {
            tamanho--;
        }
        return removido;
    }

    /**
     * Mostra quantos índices foram preenchidos
     * @return tamanho
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * Exibição: mostra cada índice com sua lista
     * (bom para evidenciar colisões nos testes)
     */
    public void exibir() {
        for (int i = 0; i < capacidade; i++) {
            System.out.print("[" + i + "] ");
            tabela[i].exibir();
        }
    }
}

