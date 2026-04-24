package menus;

import entidades.Curso.Curso;
import entidades.Usuario.Usuario;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ListaCursos {

    public static void menu(ArrayList<Curso> lista, Usuario user) {
        Scanner sc = new Scanner(System.in);

        if (lista == null || lista.isEmpty()) {
            System.out.println("Nenhum curso encontrado.");
            return;
        }

        int pagina = 0;
        int porPagina = 10;
        int totalPaginas = (int) Math.ceil((double) lista.size() / porPagina);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {

            // HEADER
            System.out.println("EntrePares 1.0");
            System.out.println("--------------");
            System.out.println("> Início > Minhas inscrições > Lista de cursos");
            System.out.println();
            System.out.println("Página " + (pagina + 1) + " de " + totalPaginas);
            System.out.println();

            int inicio = pagina * porPagina;
            int fim = Math.min(inicio + porPagina, lista.size());

            int cont = 1;

            for (int i = inicio; i < fim; i++) {
                Curso c = lista.get(i);

                String numero = String.valueOf(cont); // Numero do curso

                //Print certo
                System.out.println("(" + numero + ") " + c.getNome() + " - " + c.getInicio().format(formato));

                //Print com código
                /*System.out.println("(" + cont + ") " + c.getNome() + " - " + c.getInicio().format(formato)+ " | Código: " + c.getCodigo());*/

                cont++;
            }

            //OPÇÕES
            System.out.println();
            System.out.println("(A) Página anterior");
            System.out.println("(B) Próxima página");
            System.out.println("(R) Retornar ao menu anterior");
            System.out.print("Opção: ");

            String opcao = sc.next().toUpperCase();

            if (opcao.equals("A")) {
                if (pagina > 0) {
                    pagina--;
                } else {
                    System.out.println("Você já está na primeira página.");
                }

            } else if (opcao.equals("B")) {
                if (pagina < totalPaginas - 1) {
                    pagina++;
                } else {
                    System.out.println("Você já está na última página.");
                }

            } else if (opcao.equals("R")) {
                break;

            } else {
                System.out.println("Opção inválida.");
            }
        }
    }
}