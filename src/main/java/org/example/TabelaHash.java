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
    private int tamanho;

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
     * Função hash customizada com dispersão por shift e XOR.
     * Transforma a chave (nome do usuário) em um índice válido da tabela.
     *
     * COMO ESTÁ IMPLEMENTADO AQUI:
     * - Semente inicial com número primo (31).
     * - Shift à esquerda (<< 5) para dar peso posicional a cada letra (evita colisão entre anagramas como "amor" e "roma").
     * - XOR (^) para misturar os bits da letra atual no acumulador.
     * - Bit-shift à direita (>>> 2) para espalhar (efeito avalanche).
     * - Máscara (0x7FFFFFFF) para garantir que o número seja sempre positivo antes do módulo.
     *
     * COMO ISSO SERIA MELHORADO EM UM AMBIENTE CORPORATIVO / DE PRODUÇÃO REAL:
     * 1. Hashing Criptográfico com Salt para Senhas:
     *    - Aqui a tabela hash organiza a estrutura de dados (chave -> valor). Porém, em uma empresa real,
     *      a senha NUNCA seria armazenada em texto puro. Utiliza-se algoritmos com derivação de chave lenta
     *      e proteção contra ataques de força bruta, como BCrypt, Argon2id ou PBKDF2, acompanhados de salt individual.
     * 2. Redimensionamento Dinâmico (Rehashing):
     *    - Em larga escala, quando a taxa de ocupação (fator de carga = tamanho / capacidade) ultrapassa 0.75,
     *      a capacidade da tabela é automaticamente duplicada para a próxima potência de 2 (ou número primo),
     *      recalculando os índices para manter a complexidade sempre próxima de O(1).
     * 3. Algoritmo de Hashing Não-Criptográfico de Alta Performance:
     *    - Para indexação em memória de altíssimo volume (ex: bancos de dados, roteamento de cache),
     *      utilizam-se algoritmos industriais como MurmurHash3 ou xxHash64, que processam blocos de 4 ou 8 bytes
     *      por ciclo de CPU com quase zero colisões.
     * 4. Thread-Safety e Concorrência:
     *    - Em servidores corporativos multi-thread, utilizam-se técnicas de Segmented Locks ou Read-Write Locks
     *      (como na ConcurrentHashMap do Java) para permitir milhares de leituras e escritas simultâneas sem travar a tabela.
     *
     * @param chave nome do usuário
     * @return índice calculado dentro dos limites da tabela (0 a capacidade - 1)
     */
    private int hash(String chave) {
        int h = 31; // Ponto de partida com número primo

        for (int i = 0; i < chave.length(); i++) {
            // Dá peso à posição do caractere e mistura os bits com a letra atual
            h = (h << 5) ^ chave.charAt(i);

            // Pequeno shift para espalhar os bits e provocar efeito avalanche
            h ^= (h >>> 2);
        }

        // Zera o bit de sinal para garantir número positivo e aplica o resto da divisão
        return (h & 0x7FFFFFFF) % capacidade;
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
     * Retorna o índice calculado para uma chave (útil para testes e depuração)
     * @param chave nome do usuário
     * @return índice
     */
    public int getIndice(String chave) {
        return hash(chave);
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
