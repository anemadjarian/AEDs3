package entidades.Usuario;

import aed3.*;
import entidades.Curso.ArquivoCurso;

public class ArquivoUsuario extends Arquivo<Usuario> {
    
    HashExtensivel<ParEmailId> indiceEmail;
    final int TAM_CABECALHO = 4;

    public ArquivoUsuario() throws Exception {
        super("usuario", Usuario.class.getConstructor());
        
    
        indiceEmail = new HashExtensivel<>(
            ParEmailId.class.getConstructor(), 
            TAM_CABECALHO,
            "./dados/usuario/indiceEmail.d.db",
            "./dados/usuario/indiceEmail.c.db"
        );

     
    }

    // CREATE
    public int create(Usuario u) throws Exception {
        int id = super.create(u);
        indiceEmail.create(new ParEmailId(u.getEmail(), id));
        return id;
    }

    // LOGIN (busca sequencial por enquanto)
    public Usuario encontrarPorEmailSafe(String email) throws Exception {
        ParEmailId pei = indiceEmail.read(Math.abs(email.hashCode()));
        Usuario usuarioEncontrado = null;

        if(pei != null && pei.getEmail().equals(email)) {
            usuarioEncontrado = read(pei.getId());
        }

        usuarioEncontrado.setHashSenha(null);
        usuarioEncontrado.setHashRespostaSecreta(null);
    
        return usuarioEncontrado; 
    } 

    /*public boolean update(Usuario new_user) throws Exception {
        int var2 = new_user.getID();
        ParIDEndereco var3 = (ParIDEndereco)this.indiceDireto.read(var2);
        if (var3 == null) {
            return false;
        } else {
            long var4 = var3.getEndereco();
            this.arquivo.seek(var4);
            byte var6 = this.arquivo.readByte();
            short var7 = this.arquivo.readShort();
            if (var6 == 32) {
                byte[] var8 = new byte[var7];
                this.arquivo.read(var8);
                InterfaceEntidade var9 = (InterfaceEntidade)this.construtor.newInstance();
                var9.fromByteArray(var8);
                if (var9.getID() == var1.getID()) {
                    byte[] var10 = var1.toByteArray();
                    short var11 = (short)var10.length;
                    if (var11 <= var7) {
                        this.arquivo.seek(var4 + 3L);
                        this.arquivo.write(var10);

                        for(int var12 = 0; var12 < var7 - var11; ++var12) {
                            this.arquivo.writeByte(0);
                        }
                    } else {
                        this.arquivo.seek(var4);
                        this.arquivo.writeByte(42);
                        this.arquivo.skipBytes(2);

                        for(int var14 = 0; var14 < var7; ++var14) {
                            this.arquivo.writeByte(0);
                        }
                    }

                    this.insereEspacoVazio(var4, var7);
                    long var15 = this.encontraEspacoVazio(var11);
                    if (var15 == -1L) {
                        var15 = this.arquivo.length();
                        this.arquivo.seek(this.arquivo.length());
                        this.arquivo.writeByte(32);
                        this.arquivo.writeShort(var11);
                    } else {
                        this.arquivo.seek(var15);
                        this.arquivo.writeByte(32);
                        this.arquivo.skipBytes(2);
                    }

                    this.arquivo.write(var10);
                    this.indiceDireto.update(new ParIDEndereco(var2, var15));
                }

                return true;
            }
        }
            
        return false;
    }*/

    public Usuario encontrarPorEmailUnsafe(String email) throws Exception {
        ParEmailId pei = indiceEmail.read(Math.abs(email.hashCode()));
        Usuario usuarioEncontrado = null;

        if(pei != null && pei.getEmail().equals(email)) {
            usuarioEncontrado = read(pei.getId());
        }

        return usuarioEncontrado; 
    } 

      public boolean responderPergunta(Usuario user, String resposta) {
        boolean resp = false;
        try {
            if(user.getHashRespostaSecreta().equals(Usuario.gerarHash(resposta))) {
                resp = true;
            }
        } catch (Exception e) {
            System.out.println("Olha, usuário não encontrado.");
        }

        return resp;
    }


    public Usuario login(String email, String senha) throws Exception {
        ParEmailId pei = indiceEmail.read(Math.abs(email.hashCode()));
        
        if(pei == null || !pei.getEmail().equals(email))
            return null;

        Usuario u = read(pei.getId());

        String hash = Usuario.gerarHash(senha);

        if(u != null && u.getHashSenha().equals(hash)) {
            return u;
        }

        return null;
    }

    public boolean delete(int id) throws Exception {
        Usuario u = read(id);
        if(u != null) {
            if(super.delete(id)) {
                ArquivoCurso arqCurso = new ArquivoCurso();
                arqCurso.deleteTudoPorUsuario(id);
                indiceEmail.delete(Math.abs(u.getEmail().hashCode()));
                super.delete(id);
                return true;
            }
        }

        return false;
    }
}