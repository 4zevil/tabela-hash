/**
 * Lista simplesmente encadeada de pares usuário -> senha.
 * Cada posição da tabela hash guarda uma instância desta lista.
 */
public class ListaEncadeada {
    private No inicio;
    private int tamanho;

    public ListaEncadeada() {
        this.inicio = null;
        this.tamanho = 0;
    }

    // Insere no início da lista (O(1)). Não verifica duplicidade.
    public void inserirInicio(String chave, String valor) {
        No novo = new No(chave, valor);
        novo.proximo = inicio;
        inicio = novo;
        tamanho++;
    }

    // Retorna o nó com a chave informada, ou null se não existir
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

    public boolean contem(String chave) {
        return buscar(chave) != null;
    }

    // Remove o nó com a chave informada. Retorna false se não encontrou.
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

    public boolean estaVazia() {
        return inicio == null;
    }

    public int getTamanho() {
        return tamanho;
    }

    // Mostra a lista no formato: ana -> bruno -> null
    public void exibir() {
        No atual = inicio;
        while (atual != null) {
            System.out.print(atual.chave + " -> ");
            atual = atual.proximo;
        }
        System.out.println("null");
    }
}