/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifrs.livrare.servlet;

import br.ifrs.livrare.dao.EmprestimoDAO;
import br.ifrs.livrare.model.Aluno;
import br.ifrs.livrare.model.Emprestimo;
import br.ifrs.livrare.model.LivroUnidade;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Valdir Jr
 */
@WebServlet("/EmprestimosServlet")
public class EmprestimoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmprestimoDAO dao = new EmprestimoDAO();
        Emprestimo emprestimo = new Emprestimo();

        String acao = request.getParameter("acao").trim();
        String pesquisa = request.getParameter("pesquisa") != null ? request.getParameter("pesquisa").trim() : "";
        String retorno = "false";

        int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id").trim()) : 0;
        int alunoId = request.getParameter("alunoId") != null ? Integer.parseInt(request.getParameter("alunoId")) : 0;
        Aluno aluno = new Aluno();
        aluno.setId(alunoId);
        int livroId = request.getParameter("livroId") != null ? Integer.parseInt(request.getParameter("livroId")) : 0;
        LivroUnidade livro = new LivroUnidade();
        livro.setId(livroId);
        boolean ativo = request.getParameter("ativo") != null ?Boolean.parseBoolean(request.getParameter("ativo")):false;
        String datade = request.getParameter("datade") != null ? request.getParameter("datade").trim() : "";
        String dataate = request.getParameter("dataate") != null ? request.getParameter("dataate").trim() : "";
        String estado = request.getParameter("estado") != null ? request.getParameter("estado").trim() : "";

        emprestimo.setId(id);
        emprestimo.setAluno(aluno);
        emprestimo.setLivroAlocado(livro);
        emprestimo.setAtivo(ativo);
        emprestimo.setDatade(datade);
        emprestimo.setDataate(dataate);
        emprestimo.setEstado(estado);

        if (acao.equals("salvar")) {

            if (emprestimo.getId() <= 0) {
                try {
                    dao.salvar(emprestimo);
                    retorno = "true";
                } catch (Exception ex) {
                    Logger.getLogger(EmprestimoServlet.class.getName()).log(Level.SEVERE, null, ex);
                    retorno = ex.toString();
                }
            } else {
                try {
                    dao.atualizar(emprestimo);
                    retorno = "true";
                } catch (Exception ex) {
                    Logger.getLogger(EmprestimoServlet.class.getName()).log(Level.SEVERE, null, ex);
                    retorno = ex.toString();
                }
            }

        } else if (acao.equals("buscar")) {

            retorno = "<table class='table table-striped table-bordered table-condensed table-hover'>"
                    + "                <thead class='thead-dark text-center'>"
                    + "                    <tr>"
                    + "                        <th>Livro</th>"
                    + "                        <th>Aluno</th>"
                    + "                        <th>Ação</th>"
                    + "                    </tr>"
                    + "                </thead>"
                    + "                <tbody>";

            try {
                List<Emprestimo> emprestimos = dao.pesquisar(pesquisa);
                for (Emprestimo alu : emprestimos) {
                    retorno += "<tr>"
                            + "<td>" + alu.getAluno().getNome()+ "</td>"
                            + "<td width='15%'>" + alu.getLivroAlocado().getLivro().getNome() + "</td>"
                            + "<td width='15%'>"
                            + "<a class='text-dark' href='#' onclick='alterar(" + alu.getId() + ");'>"
                            + "<i class='fa fa-edit'>"
                            + "</i>"
                            + "Alterar"
                            + "</a> | "
                            + "<a class='text-dark' href='#' onclick='excluir(" + alu.getId() + ");'>"
                            + "<i class='fa fa-trash'></i>Excluir"
                            + "</a>"
                            + "</td>"
                            + "</tr>";
                }
            } catch (Exception ex) {
                Logger.getLogger(EmprestimoServlet.class.getName()).log(Level.SEVERE, null, ex);
                retorno = ex.toString();
            }

            retorno += "</tbody>"
                    + "</table>";

        } else if (acao.equals("excluir")) {
            try {
                dao.excluir(emprestimo.getId());
                retorno = "true";
            } catch (Exception ex) {
                Logger.getLogger(EmprestimoServlet.class.getName()).log(Level.SEVERE, null, ex);
                retorno = ex.toString();
            }
        } else if (acao.equals("atualizar")) {
            try {
                emprestimo = dao.obter(emprestimo.getId());
                retorno = "true";
            } catch (Exception ex) {
                retorno = "false";
                Logger.getLogger(EmprestimoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            retorno = "Ação não definida!";
        }

        response.setContentType("text/plain");
        response.getWriter().write(retorno);
    }

}
