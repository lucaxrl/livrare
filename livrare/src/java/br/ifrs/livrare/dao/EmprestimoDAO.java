package br.ifrs.livrare.dao;

import br.ifrs.livrare.model.Emprestimo;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class EmprestimoDAO {
    
    private EntityManager em;
    
     public void salvar(Emprestimo emprestimo) throws Exception {
        this.em = EntityManagerProvider.getInstance();
        this.em.getTransaction().begin();
        this.em.persist(emprestimo);
        this.em.getTransaction().commit();
        this.em.close();
    }
 
    public void atualizar(Emprestimo emprestimo) throws Exception {
        this.em = EntityManagerProvider.getInstance();
        this.em.getTransaction().begin();
        this.em.merge(emprestimo);
        this.em.getTransaction().commit();
        this.em.close();
    }

    public void excluir(int id) throws Exception {
        this.em = EntityManagerProvider.getInstance();
        this.em.getTransaction().begin();
        Emprestimo entity = this.em.find(Emprestimo.class, id);
        if (entity != null) {
            this.em.remove(entity);
        } 
        this.em.getTransaction().commit();
        this.em.close();
    }

    public Emprestimo obter(int id) throws Exception {
        this.em = EntityManagerProvider.getInstance();
        Emprestimo emprestimo = this.em.find(Emprestimo.class, id);
        this.em.close();
        return emprestimo;
    }
    
    public List<Emprestimo> pesquisar() throws Exception {
        this.em = EntityManagerProvider.getInstance();
        TypedQuery<Emprestimo> query = this.em.createQuery("Select c from Emprestimo c order by c.id DESC", Emprestimo.class);
        List<Emprestimo> emprestimos = query.getResultList();
        this.em.close();
        return emprestimos;
    }
    
    public List<Emprestimo> atrasados() throws Exception {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        this.em = EntityManagerProvider.getInstance();
        TypedQuery<Emprestimo> query = this.em.createQuery("Select c from Emprestimo c where c.anoate < '"+year+"'", Emprestimo.class);
        List<Emprestimo> emprestimos = query.getResultList();
        this.em.close();
        return emprestimos;
    }
    
    public List<Emprestimo> pesquisar(String nome) throws Exception {
        this.em = EntityManagerProvider.getInstance();
        TypedQuery<Emprestimo> query = this.em.createQuery(" Select c from Emprestimo c left join aluno a on a.id = c.aluno_id left join livro l on l.id = c.livro_unidade_id where lower(a.nome) like :nome or lower(l.nome) like :nome",Emprestimo.class);
        List<Emprestimo> emprestimos = query.getResultList();
        this.em.close();
        return emprestimos;
    }
    
}
