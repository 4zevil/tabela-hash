package org.example;

import  java.util.Scanner;

/**
 * Classe para a execução da aplicação
 */
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean rodando = true;

        TabelaHash tabelaHash = new TabelaHash(10);
        while (rodando){
            System.out.println("-----Lista simplesmente encadeada-----");
            System.out.println("");
            System.out.println("1 - Inserir");
            System.out.println("2 - Remover");
            System.out.println("3 - Buscar");
            System.out.println("4 - Login");
            System.out.println("5 - lista");
            System.out.println("0 - Sair");

            int valor  = scan.nextInt();
            scan.nextLine();
            switch (valor) {
                case 1:
                    System.out.println("insira o nome do novo usuário");
                    String nome = scan.nextLine();
                    System.out.println("insira a senha do novo usuário");
                    String senha  = scan.nextLine();
                    tabelaHash.inserir(nome,senha);
                    break;
                case 2:
                    System.out.println("insira o nome do usuario a ser removido");
                    nome = scan.nextLine();
                    tabelaHash.remover(nome);
                    break;
                case 3:
                    System.out.println("insira o nome do usuário");
                    nome = scan.nextLine();
                    tabelaHash.existe(nome);
                    break;
                case 4:
                    System.out.println("insira o nome do usuário");
                    nome = scan.nextLine();
                    System.out.println("insira a senha do usuário");
                    senha  = scan.nextLine();
                    tabelaHash.autenticar(nome, senha);
                    break;
                case 5:
                    tabelaHash.exibir();
                    tabelaHash.getTamanho();
                    break;
                case 0:
                    rodando=false;
            }
        }
    }
}
