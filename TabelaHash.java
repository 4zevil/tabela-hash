/**
 * Tabela hash com tratamento de colisões por encadeamento.
 * Chave = nome do usuário, valor = senha.
 */
public class TabelaHash {
    private No[] tabela;
    private int capacidade;
    private int tamanho; // quantidade de usuários cadastrados

    public TabelaHash(int capacidade) {
        this.capacidade = capacidade;
        this.tabela = new No[capacidade];
        this.tamanho = 0;
    }

    // Função hash: transforma o nome em um índice do vetor
    private int hash(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma = (soma * 31 + chave.charAt(i)) % capacidade;
        }
        return soma;
    }

    // Cadastro: retorna false se o usuário já existe
    public boolean inserir(String chave, String valor) {
        if (existe(chave)) {
            return false;
        }
        int indice = hash(chave);
        No novo = new No(chave, valor);
        // insere no início da lista do índice (colisão tratada aqui)
        novo.proximo = tabela[indice];
        tabela[indice] = novo;
        tamanho++;
        return true;
    }

    // Busca o nó de um usuário (ou null se não existir)
    private No buscarNo(String chave) {
        No atual = tabela[hash(chave)];
        while (atual != null) {
            if (atual.chave.equals(chave)) {
                return atual;
            }
            atual = atual.proximo;
        }
        return null;
    }

    // Verificação de existência
    public boolean existe(String chave) {
        return buscarNo(chave) != null;
    }

    // Autenticação: usuário existe E senha confere
    public boolean autenticar(String chave, String senha) {
        No no = buscarNo(chave);
        return no != null && no.valor.equals(senha);
    }

    // Remoção: retorna false se o usuário não existe
    public boolean remover(String chave) {
        int indice = hash(chave);
        No atual = tabela[indice];
        No anterior = null;

        while (atual != null) {
            if (atual.chave.equals(chave)) {
                if (anterior == null) {
                    tabela[indice] = atual.proximo; // era o primeiro da lista
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

    public int getTamanho() {
        return tamanho;
    }

    // Útil para depuração e para mostrar as colisões nos testes
    public void exibir() {
        for (int i = 0; i < capacidade; i++) {
            System.out.print("[" + i + "] ");
            No atual = tabela[i];
            while (atual != null) {
                System.out.print(atual.chave + " -> ");
                atual = atual.proximo;
            }
            System.out.println("null");
        }
    }
}