package menus;

import entidades.Curso.ArquivoCurso;
import entidades.Curso.Curso;
import entidades.Usuario.Usuario;
import entidades.Usuario.ArquivoUsuario;

import java.util.Scanner;

public class DetalheCurso {

    public static void menu(Curso c, Usuario user) throws Exception {
        Scanner sc = new Scanner(System.in);
        ArquivoUsuario arqUsuario = new ArquivoUsuario();
        ArquivoCurso arquivoCurso = new ArquivoCurso();

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

                if(c.getEstadoString() == "") {//se tiver aceitando inscrições
                    if(user.isInscrito(c.getIdCurso()) && c.isInscrito(user.getID())) { //verificar se já está inscrito
                        System.out.println("Você já está inscrito neste curso.");
                    } else { // caso contrário, inscrever
                        user.add(c.getIdCurso());
                        c.add(user.getID()); 
                        try {
                            arqUsuario.update(user);
                            arquivoCurso.update(c);
                            System.out.println("Inscrição realizada com sucesso!");
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                    
                }else{ //caso não esteja aceitando inscrições
                    System.out.println(c.getEstadoString());
                }
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
