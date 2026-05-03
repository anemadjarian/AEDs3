package entidades.CursoUsuario;

import java.io.*;
import java.util.ArrayList;

public class ArquivoCursoUsuario {

    private RandomAccessFile arq;
    private final int TAM_CABECALHO = 4;

    public ArquivoCursoUsuario() throws Exception {
        arq = new RandomAccessFile("cursoUsuario.db", "rw");

        if (arq.length() == 0) {
            arq.writeInt(0); // último ID
        }
    }

    //CREATE -> Fazer uma inscrição

    public int create(CursoUsuario cu) throws Exception {

        arq.seek(0);
        int ultimoID = arq.readInt();
        int novoID = ultimoID + 1;

        cu.setIdCursoUsuario(novoID);

        arq.seek(0);
        arq.writeInt(novoID);

        arq.seek(arq.length());
        byte[] ba = cu.toByteArray();

        arq.writeByte(' ');
        arq.writeShort(ba.length);
        arq.write(ba);

        return novoID;
    }

    //READ ALL

    public ArrayList<CursoUsuario> readAll() throws Exception {
        ArrayList<CursoUsuario> lista = new ArrayList<>();

        arq.seek(TAM_CABECALHO);

        while (arq.getFilePointer() < arq.length()) {
            byte lapide = arq.readByte();
            short tam = arq.readShort();

            if (lapide != '*') {
                byte[] ba = new byte[tam];
                arq.readFully(ba);

                CursoUsuario cu = new CursoUsuario();
                cu.fromByteArray(ba);

                lista.add(cu);
            } else {
                arq.skipBytes(tam);
            }
        }

        return lista;
    }

    //Delete -> cancelar a inscrição
    public boolean delete(int id) throws Exception {

        arq.seek(TAM_CABECALHO);

        while (arq.getFilePointer() < arq.length()) {
            long pos = arq.getFilePointer();

            byte lapide = arq.readByte();
            short tam = arq.readShort();

            byte[] ba = new byte[tam];
            arq.readFully(ba);

            if (lapide != '*') {
                CursoUsuario cu = new CursoUsuario();
                cu.fromByteArray(ba);

                if (cu.getIdCursoUsuario() == id) {
                    arq.seek(pos);
                    arq.writeByte('*');
                    return true;
                }
            }
        }

        return false;
    }
}