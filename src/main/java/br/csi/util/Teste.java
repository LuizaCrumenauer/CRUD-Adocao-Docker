package br.csi.util;

import br.csi.dao.AdocaoDAO;
import br.csi.dao.CachorroDAO;
import br.csi.dao.UsuarioDAO;
import br.csi.model.Adocao;
import br.csi.model.Cachorro;
import br.csi.model.Usuario;

import java.util.ArrayList;

public class Teste {
    public static void main(String[] args) {
        //testesUsuarioDAO ();
        //TesteCachorroDAO ();
        //TesteAdocaoDAO();
    }
    public static void testesUsuarioDAO() {
        UsuarioDAO dao = new UsuarioDAO();

        //Criar usuario
        Usuario usuario = new Usuario();

        usuario.setNome("Usuario 1");
        usuario.setEndereco ( "Endereco 1" );
        usuario.setCpf ( "000.000.000-00" );
        usuario.setCelular ( "(55)999999999" );
        usuario.setEmail("teste@teste");
        usuario.setSenha("123");
        usuario.setAdmin ( false );

        dao.inserir(usuario);


        //imprimirUsuarios(dao.listar());

        //usuario.setId ( 3 );
        //dao.excluir ( usuario );

        //imprimirUsuarios(dao.listar());

        //Alterar usuario
//        usuario.setId(2);
//        usuario.setNome ( "João da Silva" );
//        usuario.setEndereco ( "Rua do Silva" );
//        usuario.setEmail("joao@teste.editado");
//        usuario.setSenha("123");
//        usuario.setCelular ( "(00) 999999999" );
//        usuario.setCpf ( "111.222.333-44" );
//        usuario.setAdmin ( false );
//
//
//        dao.alterar(usuario);
//
//        //Listar usuario
//        imprimirUsuarios(dao.listar());
    }

    public static void TesteCachorroDAO() {
        //CachorroDAO cachorroDAO = new CachorroDAO();
       // UsuarioDAO usuarioDAO = new UsuarioDAO();

        //Criar cachorro
      //  Cachorro cachorro = new Cachorro();
        //Usuario usuario = usuarioDAO.buscar("admin@admin.com");

//        cachorro.setId ( 1 );
//        cachorro.setNome("Tobby");
//       cachorro.setPorte ( "M" );
//        cachorro.setRaca ( "SRD" );
//        cachorro.setSexo ( "Macho" );
//        cachorro.setAdotado (false);

        //cachorroDAO.inserirCachorro ( cachorro );
        //cachorroDAO.alterarCachorro ( cachorro );

        //cachorro.setId ( 1 );
        //cachorroDAO.excluirCachorro ( cachorro );

       // imprimirCachorros ( cachorroDAO.listarCachorro () );
    }

    public static void TesteAdocaoDAO() {
        AdocaoDAO adocaoDAO = new AdocaoDAO();
        CachorroDAO cachorroDAO = new CachorroDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Ajuste esses IDs de acordo com registros existentes no banco
        int idCachorro = 1;
        int idUsuario = 1;

        // Criar adoção
        Adocao adocao = new Adocao();
        adocao.setCachorroId(idCachorro);
        adocao.setAdotanteId(idUsuario);
        adocao.setInformacoes ("Descricao 1");

        //adocaoDAO.inserirAdocao(adocao);


        // Listar todas as adoções
        System.out.println("Lista de adocoes:");
        for (Adocao a : adocaoDAO.listarAdocoes()) {
            System.out.println(
                    "Id: " + a.getId() +
                            ", CachorroId: " + a.getCachorroId () +
                            ", AdotanteId: " + a.getAdotanteId() +
                            ", Data: " + a.getInformacoes ()
            );
        }



        // Excluir adoção
        adocao.setId ( 1 );
        adocaoDAO.excluirAdocao(adocao.getId());

    }

    public static void imprimirUsuario(Usuario usuario) {
        System.out.println(
                "Id: " + usuario.getId()
                        + " email: " + usuario.getEmail()
                        + " senha: " + usuario.getSenha());

    }
    public static void imprimirUsuarios( ArrayList<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            imprimirUsuario(usuario);
        }
    }

    public static void imprimirCachorro(Cachorro cachorro) {
        System.out.println (
                "Id: " + cachorro.getId()
                + " nome: " + cachorro.getNome()
                + " porte: " + cachorro.getPorte()
                + " raca: " + cachorro.getRaca()
                + " sexo: " + cachorro.getSexo()
                + " adotado: " + cachorro.isAdotado()
        );
    }

    public static void imprimirCachorros(ArrayList<Cachorro> cachorros) {
        for (Cachorro cachorro : cachorros) {
            imprimirCachorro(cachorro);
        }
    }
}
