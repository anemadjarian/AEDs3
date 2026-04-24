package menus;

import entidades.Curso.Curso;
import entidades.Usuario.Usuario;

import java.util.Scanner;

public class DetalheCurso {

    public static void menu(Curso c, Usuario user) throws Exception {
        Scanner sc = new Scanner(System.in);

        int s = 0;
        String aux = "";
        char opcao;

        while (s == 0) {

            System.out.println("EntrePares 1.0");
            System.out.println("--------------");
            System.out.println("> Início > Minhas inscrições > Lista de cursos > " + c.getNome());
            System.out.println();

            System.out.println("CÓDIGO........: " + c.getCodigo());
            System.out.println("CURSO.........: " + c.getNome());

            //autor (por enquanto placeholder)
            System.out.println("AUTOR.........: " + c.getIdUsuario());

            System.out.println("DESCRIÇÃO.....: " + c.getDescricao());
            System.out.println("DATA DE INÍCIO: " + c.getInicio());
            System.out.println();

            System.out.println("(A) Fazer minha inscrição no curso");
            System.out.println();
            System.out.println("(R) Retornar ao menu anterior");
            System.out.println();

            System.out.print("Opção: ");
            aux = sc.nextLine();

            if (aux.length() == 0) continue;

            opcao = Character.toUpperCase(aux.charAt(0));

            // voltar
            if (opcao == 'R') {
                s = 1;
                break;
            }

            // fazer inscrição
            else if (opcao == 'A') {

                //por enquanto só mensagem (lógica vem depois)
                System.out.println();
                System.out.println("Inscrição realizada com sucesso! (implementar depois)");
                System.out.println();

                System.out.println("Pressione Enter para continuar...");
                sc.nextLine();
            }

            else {
                System.out.println("Opção inválida.");
                System.out.println("Pressione Enter para continuar...");
                sc.nextLine();
            }
        }
    }
}
