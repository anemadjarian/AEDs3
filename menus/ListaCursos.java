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

        //Formatar data
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        //Header
        System.out.println("EntrePares 1.0");
        System.out.println("--------------");
        System.out.println("> Início > Minhas inscrições > Lista de cursos");
        System.out.println();
        System.out.println("Página " + (pagina + 1) + " de " + totalPaginas);
        System.out.println();

        int inicio = pagina * porPagina;
        int fim = Math.min(inicio + porPagina, lista.size());
        int cont = 1;
        String numero;

        for (int i = inicio; i < fim; i++) {
            Curso c = lista.get(i);

            if (cont == 10) {
                numero = "0";
            } else {
                numero = String.valueOf(cont);
            }

            System.out.println("(" + numero + ") " + c.getNome() + " - " + c.getInicio().format(formato));

            cont++;
        }

        System.out.println("(A) Página anterior");
        System.out.println("(B) Próxima página");
        System.out.println("(R) Retornar ao menu anterior");
        System.out.print("Opção: ");

        String opcao = sc.next();

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
            return;

        } else {
            System.out.println("Opção inválida.");
        }
    }
}