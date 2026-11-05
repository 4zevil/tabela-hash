package org.example;

/**
 * Testes Visuais 
 */
public class TestesUnitAuthenticator {

    private static int totalTestes = 0;
    private static int testesAprovados = 0;

    public static void main(String[] args) {
        imprimirCabecalho("INICIANDO BATERIA DE TESTES DO UNIT AUTHENTICATOR");

        // Instanciamos uma tabela hash com capacidade 10 para tornar
        // as colisões fáceis de observar e demonstrar visualmente.
        TabelaHash tabela = new TabelaHash(10);

        // ===============================================================
        // CENÁRIO 1: Inserção Normal de Usuários
        // ===============================================================
        imprimirSecao("CENÁRIO 1: Inserção de Usuários Únicos");
        
        testarInsercao(tabela, "joao", "senhaJoao123", true, "Inserção em bucket vazio (índice 0)");
        testarInsercao(tabela, "leo", "senhaLeo456", true, "Inserção em bucket vazio (índice 2)");
        testarInsercao(tabela, "bob", "senhaBob789", true, "Inserção em bucket vazio (índice 4)");

        System.out.println("\n[ESTADO ATUAL DA TABELA]");
        tabela.exibir();
        validar("Tamanho da tabela deve ser 3", tabela.getTamanho() == 3);

        // ===============================================================
        // CENÁRIO 2: Tratamento de Colisão por Encadeamento (Lista Ligada)
        // ===============================================================
        imprimirSecao("CENÁRIO 2: Tratamento de Colisões (Múltiplos usuários no mesmo índice)");
        System.out.println("Demonstrando que nomes diferentes calculam o mesmo índice e se encadeiam:");

        // Índice 1: carlos, lucas e gui caem todos no índice 1!
        testarInsercao(tabela, "carlos", "senhaCarlos1", true, "Primeiro nó no índice 1");
        testarInsercao(tabela, "lucas", "senhaLucas2", true, "Colisão! Deve entrar no início da lista do índice 1");
        testarInsercao(tabela, "gui", "senhaGui3", true, "Segunda colisão no índice 1! Deve encadear gui -> lucas -> carlos");

        System.out.println("\n[ESTADO ATUAL DA TABELA APÓS COLISÕES]");
        tabela.exibir();
        validar("Tamanho da tabela deve ser 6", tabela.getTamanho() == 6);

        // ===============================================================
        // CENÁRIO 3: Bloqueio de Chaves Duplicadas (Mesmo usuário)
        // ===============================================================
        imprimirSecao("CENÁRIO 3: Tentativa de Cadastro Duplicado");
        System.out.println("O sistema deve recusar cadastrar um usuário que já existe:");
        
        testarInsercao(tabela, "carlos", "outraSenha123", false, "Recusar 'carlos' repetido");
        testarInsercao(tabela, "joao", "novaSenha999", false, "Recusar 'joao' repetido");
        validar("Tamanho da tabela deve permanecer 6", tabela.getTamanho() == 6);

        // ===============================================================
        // CENÁRIO 4: Busca e Verificação de Existência
        // ===============================================================
        imprimirSecao("CENÁRIO 4: Busca de Usuários (Existentes e Inexistentes)");
        
        validar("Buscar 'carlos' (existente no fim de uma lista encadeada)", tabela.existe("carlos"));
        validar("Buscar 'gui' (existente no início da lista encadeada)", tabela.existe("gui"));
        validar("Buscar 'bob' (existente em nó único)", tabela.existe("bob"));
        validar("Buscar 'pedro' (não cadastrado)", !tabela.existe("pedro"));
        validar("Buscar 'usuario_fantasma' (não cadastrado)", !tabela.existe("usuario_fantasma"));

        // ===============================================================
        // CENÁRIO 5: Autenticação (Login)
        // ===============================================================
        imprimirSecao("CENÁRIO 5: Autenticação / Login");
        
        validar("Login com 'gui' e senha correta -> SUCESSO", tabela.autenticar("gui", "senhaGui3"));
        validar("Login com 'gui' e senha INCORRETA -> FALHA", !tabela.autenticar("gui", "senhaErrada"));
        validar("Login com 'carlos' (colidido) e senha correta -> SUCESSO", tabela.autenticar("carlos", "senhaCarlos1"));
        validar("Login com usuário inexistente 'admin' -> FALHA", !tabela.autenticar("admin", "1234"));

        // ===============================================================
        // CENÁRIO 6: Remoção de Usuários e Manutenção da Corrente
        // ===============================================================
        imprimirSecao("CENÁRIO 6: Remoção com Manutenção de Encadeamento");
        System.out.println("Removeremos elementos em posições estratégicas da lista encadeada do índice 1:");
        System.out.println("Estado antes: [1] gui -> lucas -> carlos -> null");

        // 1. Remover o elemento do meio da corrente ("lucas")
        System.out.println("\n-> Removendo 'lucas' (nó intermediário da lista)...");
        boolean removidoMeio = tabela.remover("lucas");
        validar("Remover 'lucas' deve retornar true", removidoMeio);
        validar("'lucas' não deve mais existir", !tabela.existe("lucas"));
        validar("'gui' (anterior) ainda deve existir", tabela.existe("gui"));
        validar("'carlos' (posterior) ainda deve existir", tabela.existe("carlos"));

        // 2. Remover o primeiro elemento da corrente ("gui")
        System.out.println("\n-> Removendo 'gui' (primeiro nó da lista do índice 1)...");
        boolean removidoInicio = tabela.remover("gui");
        validar("Remover 'gui' deve retornar true", removidoInicio);
        validar("'gui' não deve mais existir", !tabela.existe("gui"));
        validar("'carlos' ainda deve existir", tabela.existe("carlos"));

        // 3. Tentar remover usuário que não existe
        System.out.println("\n-> Tentando remover 'fantasma'...");
        boolean removidoInexistente = tabela.remover("fantasma");
        validar("Remover inexistente deve retornar false", !removidoInexistente);

        System.out.println("\n[ESTADO FINAL DA TABELA APÓS AS REMOÇÕES]");
        tabela.exibir();
        validar("Tamanho final da tabela deve ser 4", tabela.getTamanho() == 4);

        // ===============================================================
        // RESUMO FINAL
        // ===============================================================
        imprimirResumoFinal();
    }

    private static void testarInsercao(TabelaHash tabela, String chave, String valor, boolean esperado, String descricao) {
        int indice = tabela.getIndice(chave);
        boolean resultado = tabela.inserir(chave, valor);
        String status = (resultado == esperado) ? "[OK]" : "[FALHA]";
        System.out.printf("  %s %-60s -> Indice: [%d] | Retorno: %b%n", status, descricao, indice, resultado);
        totalTestes++;
        if (resultado == esperado) testesAprovados++;
    }

    private static void validar(String descricao, boolean condicao) {
        totalTestes++;
        if (condicao) {
            testesAprovados++;
            System.out.printf("  [OK]    %s%n", descricao);
        } else {
            System.err.printf("  [ERRO]  %s%n", descricao);
        }
    }

    private static void imprimirCabecalho(String titulo) {
        System.out.println("\n================================================================================");
        System.out.println("  " + titulo);
        System.out.println("================================================================================");
    }

    private static void imprimirSecao(String titulo) {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("  " + titulo);
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void imprimirResumoFinal() {
        System.out.println("\n================================================================================");
        System.out.println("  RESUMO DOS TESTES EXECUTADOS");
        System.out.println("================================================================================");
        System.out.printf("  Total de testes realizados : %d%n", totalTestes);
        System.out.printf("  Testes com sucesso         : %d%n", testesAprovados);
        System.out.printf("  Testes com falha           : %d%n", totalTestes - testesAprovados);
        if (totalTestes == testesAprovados) {
            System.out.println("  STATUS GERAL               : >>> TODOS OS TESTES PASSARAM COM SUCESSO! <<<");
        } else {
            System.out.println("  STATUS GERAL               : >>> EXISTEM TESTES COM FALHA! <<<");
        }
        System.out.println("================================================================================\n");
    }
}
