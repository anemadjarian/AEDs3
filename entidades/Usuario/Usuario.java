package entidades.Usuario;

import aed3.InterfaceEntidade;
import java.io.*;
import java.util.ArrayList;

public class Usuario implements InterfaceEntidade {
    private int idUsuario;
    private String nome;
    private String email;
    private String hashSenha;
    private String perguntaSecreta;
    private String hashRespostaSecreta;
    private ArrayList<Integer> idCursos;
    
    public Usuario() {
    }

    public Usuario(int id, String nome, String email, String hashSenha, String perguntaSecreta, String hashRespostaSecreta) {
        this.idUsuario = id;
        this.nome = nome;
        this.email = email;
        this.hashSenha = hashSenha;
        this.perguntaSecreta = perguntaSecreta;
        this.hashRespostaSecreta = hashRespostaSecreta;
        this.idCursos = new ArrayList<>();
    }

    public ArrayList<Integer> getCursosInscritos() {
        return idCursos;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashSenha() {
        return hashSenha;
    }

    public void setHashSenha(String hashSenha) {
        this.hashSenha = hashSenha;
    }

    public String getPerguntaSecreta() {
        return perguntaSecreta;
    }

    public void setPerguntaSecreta(String perguntaSecreta) {
        this.perguntaSecreta = perguntaSecreta;
    }

    public String getHashRespostaSecreta() {
        return hashRespostaSecreta;
    }

    public void setHashRespostaSecreta(String hashRespostaSecreta) {
        this.hashRespostaSecreta = hashRespostaSecreta;
    }

    public int getID() {
        return idUsuario;
    }

    public void setID(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
               "\nEmail: " + email;
    }

    // hash simples (placeholder)
    public static String gerarHash(String texto) {
        return Integer.toString(texto.hashCode());
    }

    public boolean isInscrito(int idCurso) {
        return idCursos.contains(idCurso);
    }

    public void add(int idCurso) {
        if(!idCursos.contains(idCurso)) {
            idCursos.add(idCurso);
        }
    }

    // serialização
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(ba);

        dos.writeInt(idUsuario);
        dos.writeUTF(nome);
        dos.writeUTF(email);
        dos.writeUTF(hashSenha);
        dos.writeUTF(perguntaSecreta);
        dos.writeUTF(hashRespostaSecreta);
        if(idCursos != null) {
            dos.writeInt(idCursos.size());
            for(Integer id : idCursos) {
                dos.writeInt(id);
            }
        } else {
            dos.writeInt(0);
        }

        return ba.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bi = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bi);

        idUsuario = dis.readInt();
        nome = dis.readUTF();
        email = dis.readUTF();
        hashSenha = dis.readUTF();
        perguntaSecreta = dis.readUTF();
        hashRespostaSecreta = dis.readUTF();
        int qtd = dis.readInt();
        this.idCursos = new ArrayList<>();
        for(int i = 0; i < qtd; i++) {
            this.idCursos.add(dis.readInt());
        }
    }
}