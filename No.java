
public class No {
    String chave;   // nome do usuário
    String valor;   // senha
    No proximo;     // próximo nó da lista no mesmo índice
 
    public No(String chave, String valor) {
        this.chave = chave;
        this.valor = valor;
        this.proximo = null;
    }
}
 