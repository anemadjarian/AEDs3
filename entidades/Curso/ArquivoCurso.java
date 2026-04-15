package entidades.Curso;
import aed3.*;
import java.util.ArrayList;

public class ArquivoCurso extends Arquivo<Curso> {
    private final int TAM_CABECALHO = 4;

    ArvoreBMais<ParIdId> relacionamentoUsuarioCurso;
    ArvoreBMais<ParNomeId> indiceNome;
    
    public ArquivoCurso() throws Exception {
        super("curso", Curso.class.getConstructor());

        relacionamentoUsuarioCurso = new ArvoreBMais<>(ParIdId.class.getConstructor(), TAM_CABECALHO, "./dados/curso/relacionamentoUsuarioCurso.db");
        indiceNome = new ArvoreBMais<>(ParNomeId.class.getConstructor(), TAM_CABECALHO, "./dados/curso/indiceNome.db");
    }

    @Override
    public int create(Curso c) throws Exception {
        int id = super.create(c);
        relacionamentoUsuarioCurso.create(new ParIdId(c.getIdUsuario(), id));
        indiceNome.create(new ParNomeId(c.getNome(), id));
        return id;
    }

    public Curso[] readCursosPorUsuario(int idUsuario) throws Exception {
        ArrayList<ParIdId> piis = relacionamentoUsuarioCurso.read(new ParIdId(idUsuario, -1));
        
        if(piis.isEmpty()) {
            return new Curso[0];
        }

        Curso[] cursos = new Curso[piis.size()];
        int i = 0;
        for(ParIdId pii : piis) {
            cursos[i++] = super.read(pii.getId2());
        }

        return cursos;
    }

    public String[] readNomeCursosPorUsuario(int idUsuario) throws Exception {
        ArrayList<ParIdId> lista = relacionamentoUsuarioCurso.read(new ParIdId(idUsuario, -1));

        String[] nomes = new String[lista.size()];
        int i = 0;

        for(ParIdId p : lista) {
            Curso c = super.read(p.getId2());
            nomes[i++] = c.getEstado() == 3 ? c.getNome() + " [Cancelado]": c.getNome();
        }

        return nomes;
    }

    @Override
    public boolean update(Curso novo) throws Exception {
        Curso c = read(novo.getID());
        if(c==null) {
            return false;
        } 

        if(super.update(novo)) {
            if(c.getNome().compareTo(novo.getNome()) != 0) {
                indiceNome.delete(new ParNomeId(c.getNome(), c.getID()));
                indiceNome.create(new ParNomeId(novo.getNome(), novo.getID()));
            } 

            if(c.getIdUsuario() != novo.getIdUsuario()) {
                relacionamentoUsuarioCurso.delete(new ParIdId(c.getIdUsuario(), c.getID()));
                relacionamentoUsuarioCurso.create(new ParIdId(novo.getIdUsuario(), novo.getID()));
            } 

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Curso c = read(id);
        if(c != null) {
            if(super.delete(id)) {
                indiceNome.delete(new ParNomeId(c.getNome(), c.getID()));
                relacionamentoUsuarioCurso.delete(new ParIdId(c.getIdUsuario(), c.getID()));
                return true;
            }
        }

        return false;
    }

    public void deleteTudoPorUsuario(int userId) throws Exception {
        Curso[] cursos = readCursosPorUsuario(userId);
        for(Curso curso : cursos) {
            delete(curso.getID());
        }
    }
}