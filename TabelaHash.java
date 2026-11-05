/**
 * Tabela hash em que cada posição do vetor é uma lista simplesmente
 * encadeada (encadeamento separado para tratar colisões).
 * Chave = nome do usuário, valor = senha.
 */

public class TabelaHash {
    private ListaEncadeada[] tabela;
    private int capacidade;
    private int tamanho; // total de usuários cadastrados

    public TabelaHash(int capacidade) {
        this.capacidade = capacidade;
        this.tabela = new ListaEncadeada[capacidade];
        // cada item do vetor começa como uma lista vazia
        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new ListaEncadeada();
        }
        this.tamanho = 0;
    }

    // Função hash: transforma o nome em um índice do vetor
   private int hash(String chave) {
        int h = 0;
        for (int i = 0; i < chave.length(); i++) {
            // rotação de 5 bits à esquerda (shift) combinada com XOR do caractere
            h = (h << 5) ^ (h >>> 27) ^ chave.charAt(i);
        }
        // zera o bit de sinal para garantir índice não negativo
        return (h & 0x7FFFFFFF) % capacidade;
    }

    // Cadastro: retorna false se o usuário já existe
    public boolean inserir(String chave, String valor) {
        ListaEncadeada lista = tabela[hash(chave)];
        if (lista.contem(chave)) {
            return false;
        }
        lista.inserirInicio(chave, valor);
        tamanho++;
        return true;
    }

    // Verificação de existência
    public boolean existe(String chave) {
        return tabela[hash(chave)].contem(chave);
    }

    // Autenticação: usuário existe E senha confere
    public boolean autenticar(String chave, String senha) {
        No no = tabela[hash(chave)].buscar(chave);
        return no != null && no.valor.equals(senha);
    }

    // Remoção: retorna false se o usuário não existe
    public boolean remover(String chave) {
        boolean removido = tabela[hash(chave)].remover(chave);
        if (removido) {
            tamanho--;
        }
        return removido;
    }

    public int getTamanho() {
        return tamanho;
    }

    // Mostra cada índice com sua lista (bom para evidenciar colisões nos testes)
    public void exibir() {
        for (int i = 0; i < capacidade; i++) {
            System.out.print("[" + i + "] ");
            tabela[i].exibir();
        }
    }
}