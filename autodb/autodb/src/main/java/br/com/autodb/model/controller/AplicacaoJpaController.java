/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.IllegalOrphanException;
import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.Aplicacao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.Veiculo;
import br.com.autodb.model.dao.Produto;
import br.com.autodb.model.dao.Posicao;
import br.com.autodb.model.dao.Pais;
import br.com.autodb.model.dao.QualificadorAplicacao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class AplicacaoJpaController implements Serializable {

    public AplicacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aplicacao aplicacao) {
        if (aplicacao.getQualificadorAplicacaoCollection() == null) {
            aplicacao.setQualificadorAplicacaoCollection(new ArrayList<QualificadorAplicacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veiculo codigoVeiculo = aplicacao.getCodigoVeiculo();
            if (codigoVeiculo != null) {
                codigoVeiculo = em.getReference(codigoVeiculo.getClass(), codigoVeiculo.getCodigoVeiculo());
                aplicacao.setCodigoVeiculo(codigoVeiculo);
            }
            Produto codigoProduto = aplicacao.getCodigoProduto();
            if (codigoProduto != null) {
                codigoProduto = em.getReference(codigoProduto.getClass(), codigoProduto.getCodigoProduto());
                aplicacao.setCodigoProduto(codigoProduto);
            }
            Posicao codigoPosicao = aplicacao.getCodigoPosicao();
            if (codigoPosicao != null) {
                codigoPosicao = em.getReference(codigoPosicao.getClass(), codigoPosicao.getCodigoPosicao());
                aplicacao.setCodigoPosicao(codigoPosicao);
            }
            Pais codigoPais = aplicacao.getCodigoPais();
            if (codigoPais != null) {
                codigoPais = em.getReference(codigoPais.getClass(), codigoPais.getCodigoPais());
                aplicacao.setCodigoPais(codigoPais);
            }
            Collection<QualificadorAplicacao> attachedQualificadorAplicacaoCollection = new ArrayList<QualificadorAplicacao>();
            for (QualificadorAplicacao qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach : aplicacao.getQualificadorAplicacaoCollection()) {
                qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach = em.getReference(qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach.getClass(), qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach.getCodigoQualificadorAplicacao());
                attachedQualificadorAplicacaoCollection.add(qualificadorAplicacaoCollectionQualificadorAplicacaoToAttach);
            }
            aplicacao.setQualificadorAplicacaoCollection(attachedQualificadorAplicacaoCollection);
            em.persist(aplicacao);
            if (codigoVeiculo != null) {
                codigoVeiculo.getAplicacaoCollection().add(aplicacao);
                codigoVeiculo = em.merge(codigoVeiculo);
            }
            if (codigoProduto != null) {
                codigoProduto.getAplicacaoCollection().add(aplicacao);
                codigoProduto = em.merge(codigoProduto);
            }
            if (codigoPosicao != null) {
                codigoPosicao.getAplicacaoCollection().add(aplicacao);
                codigoPosicao = em.merge(codigoPosicao);
            }
            if (codigoPais != null) {
                codigoPais.getAplicacaoCollection().add(aplicacao);
                codigoPais = em.merge(codigoPais);
            }
            for (QualificadorAplicacao qualificadorAplicacaoCollectionQualificadorAplicacao : aplicacao.getQualificadorAplicacaoCollection()) {
                Aplicacao oldCodigoAplicacaoOfQualificadorAplicacaoCollectionQualificadorAplicacao = qualificadorAplicacaoCollectionQualificadorAplicacao.getCodigoAplicacao();
                qualificadorAplicacaoCollectionQualificadorAplicacao.setCodigoAplicacao(aplicacao);
                qualificadorAplicacaoCollectionQualificadorAplicacao = em.merge(qualificadorAplicacaoCollectionQualificadorAplicacao);
                if (oldCodigoAplicacaoOfQualificadorAplicacaoCollectionQualificadorAplicacao != null) {
                    oldCodigoAplicacaoOfQualificadorAplicacaoCollectionQualificadorAplicacao.getQualificadorAplicacaoCollection().remove(qualificadorAplicacaoCollectionQualificadorAplicacao);
                    oldCodigoAplicacaoOfQualificadorAplicacaoCollectionQualificadorAplicacao = em.merge(oldCodigoAplicacaoOfQualificadorAplicacaoCollectionQualificadorAplicacao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Aplicacao aplicacao) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aplicacao persistentAplicacao = em.find(Aplicacao.class, aplicacao.getCodigoAplicacao());
            Veiculo codigoVeiculoOld = persistentAplicacao.getCodigoVeiculo();
            Veiculo codigoVeiculoNew = aplicacao.getCodigoVeiculo();
            Produto codigoProdutoOld = persistentAplicacao.getCodigoProduto();
            Produto codigoProdutoNew = aplicacao.getCodigoProduto();
            Posicao codigoPosicaoOld = persistentAplicacao.getCodigoPosicao();
            Posicao codigoPosicaoNew = aplicacao.getCodigoPosicao();
            Pais codigoPaisOld = persistentAplicacao.getCodigoPais();
            Pais codigoPaisNew = aplicacao.getCodigoPais();
            Collection<QualificadorAplicacao> qualificadorAplicacaoCollectionOld = persistentAplicacao.getQualificadorAplicacaoCollection();
            Collection<QualificadorAplicacao> qualificadorAplicacaoCollectionNew = aplicacao.getQualificadorAplicacaoCollection();
            List<String> illegalOrphanMessages = null;
            for (QualificadorAplicacao qualificadorAplicacaoCollectionOldQualificadorAplicacao : qualificadorAplicacaoCollectionOld) {
                if (!qualificadorAplicacaoCollectionNew.contains(qualificadorAplicacaoCollectionOldQualificadorAplicacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain QualificadorAplicacao " + qualificadorAplicacaoCollectionOldQualificadorAplicacao + " since its codigoAplicacao field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoVeiculoNew != null) {
                codigoVeiculoNew = em.getReference(codigoVeiculoNew.getClass(), codigoVeiculoNew.getCodigoVeiculo());
                aplicacao.setCodigoVeiculo(codigoVeiculoNew);
            }
            if (codigoProdutoNew != null) {
                codigoProdutoNew = em.getReference(codigoProdutoNew.getClass(), codigoProdutoNew.getCodigoProduto());
                aplicacao.setCodigoProduto(codigoProdutoNew);
            }
            if (codigoPosicaoNew != null) {
                codigoPosicaoNew = em.getReference(codigoPosicaoNew.getClass(), codigoPosicaoNew.getCodigoPosicao());
                aplicacao.setCodigoPosicao(codigoPosicaoNew);
            }
            if (codigoPaisNew != null) {
                codigoPaisNew = em.getReference(codigoPaisNew.getClass(), codigoPaisNew.getCodigoPais());
                aplicacao.setCodigoPais(codigoPaisNew);
            }
            Collection<QualificadorAplicacao> attachedQualificadorAplicacaoCollectionNew = new ArrayList<QualificadorAplicacao>();
            for (QualificadorAplicacao qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach : qualificadorAplicacaoCollectionNew) {
                qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach = em.getReference(qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach.getClass(), qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach.getCodigoQualificadorAplicacao());
                attachedQualificadorAplicacaoCollectionNew.add(qualificadorAplicacaoCollectionNewQualificadorAplicacaoToAttach);
            }
            qualificadorAplicacaoCollectionNew = attachedQualificadorAplicacaoCollectionNew;
            aplicacao.setQualificadorAplicacaoCollection(qualificadorAplicacaoCollectionNew);
            aplicacao = em.merge(aplicacao);
            if (codigoVeiculoOld != null && !codigoVeiculoOld.equals(codigoVeiculoNew)) {
                codigoVeiculoOld.getAplicacaoCollection().remove(aplicacao);
                codigoVeiculoOld = em.merge(codigoVeiculoOld);
            }
            if (codigoVeiculoNew != null && !codigoVeiculoNew.equals(codigoVeiculoOld)) {
                codigoVeiculoNew.getAplicacaoCollection().add(aplicacao);
                codigoVeiculoNew = em.merge(codigoVeiculoNew);
            }
            if (codigoProdutoOld != null && !codigoProdutoOld.equals(codigoProdutoNew)) {
                codigoProdutoOld.getAplicacaoCollection().remove(aplicacao);
                codigoProdutoOld = em.merge(codigoProdutoOld);
            }
            if (codigoProdutoNew != null && !codigoProdutoNew.equals(codigoProdutoOld)) {
                codigoProdutoNew.getAplicacaoCollection().add(aplicacao);
                codigoProdutoNew = em.merge(codigoProdutoNew);
            }
            if (codigoPosicaoOld != null && !codigoPosicaoOld.equals(codigoPosicaoNew)) {
                codigoPosicaoOld.getAplicacaoCollection().remove(aplicacao);
                codigoPosicaoOld = em.merge(codigoPosicaoOld);
            }
            if (codigoPosicaoNew != null && !codigoPosicaoNew.equals(codigoPosicaoOld)) {
                codigoPosicaoNew.getAplicacaoCollection().add(aplicacao);
                codigoPosicaoNew = em.merge(codigoPosicaoNew);
            }
            if (codigoPaisOld != null && !codigoPaisOld.equals(codigoPaisNew)) {
                codigoPaisOld.getAplicacaoCollection().remove(aplicacao);
                codigoPaisOld = em.merge(codigoPaisOld);
            }
            if (codigoPaisNew != null && !codigoPaisNew.equals(codigoPaisOld)) {
                codigoPaisNew.getAplicacaoCollection().add(aplicacao);
                codigoPaisNew = em.merge(codigoPaisNew);
            }
            for (QualificadorAplicacao qualificadorAplicacaoCollectionNewQualificadorAplicacao : qualificadorAplicacaoCollectionNew) {
                if (!qualificadorAplicacaoCollectionOld.contains(qualificadorAplicacaoCollectionNewQualificadorAplicacao)) {
                    Aplicacao oldCodigoAplicacaoOfQualificadorAplicacaoCollectionNewQualificadorAplicacao = qualificadorAplicacaoCollectionNewQualificadorAplicacao.getCodigoAplicacao();
                    qualificadorAplicacaoCollectionNewQualificadorAplicacao.setCodigoAplicacao(aplicacao);
                    qualificadorAplicacaoCollectionNewQualificadorAplicacao = em.merge(qualificadorAplicacaoCollectionNewQualificadorAplicacao);
                    if (oldCodigoAplicacaoOfQualificadorAplicacaoCollectionNewQualificadorAplicacao != null && !oldCodigoAplicacaoOfQualificadorAplicacaoCollectionNewQualificadorAplicacao.equals(aplicacao)) {
                        oldCodigoAplicacaoOfQualificadorAplicacaoCollectionNewQualificadorAplicacao.getQualificadorAplicacaoCollection().remove(qualificadorAplicacaoCollectionNewQualificadorAplicacao);
                        oldCodigoAplicacaoOfQualificadorAplicacaoCollectionNewQualificadorAplicacao = em.merge(oldCodigoAplicacaoOfQualificadorAplicacaoCollectionNewQualificadorAplicacao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aplicacao.getCodigoAplicacao();
                if (findAplicacao(id) == null) {
                    throw new NonexistentEntityException("The aplicacao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aplicacao aplicacao;
            try {
                aplicacao = em.getReference(Aplicacao.class, id);
                aplicacao.getCodigoAplicacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aplicacao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<QualificadorAplicacao> qualificadorAplicacaoCollectionOrphanCheck = aplicacao.getQualificadorAplicacaoCollection();
            for (QualificadorAplicacao qualificadorAplicacaoCollectionOrphanCheckQualificadorAplicacao : qualificadorAplicacaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Aplicacao (" + aplicacao + ") cannot be destroyed since the QualificadorAplicacao " + qualificadorAplicacaoCollectionOrphanCheckQualificadorAplicacao + " in its qualificadorAplicacaoCollection field has a non-nullable codigoAplicacao field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Veiculo codigoVeiculo = aplicacao.getCodigoVeiculo();
            if (codigoVeiculo != null) {
                codigoVeiculo.getAplicacaoCollection().remove(aplicacao);
                codigoVeiculo = em.merge(codigoVeiculo);
            }
            Produto codigoProduto = aplicacao.getCodigoProduto();
            if (codigoProduto != null) {
                codigoProduto.getAplicacaoCollection().remove(aplicacao);
                codigoProduto = em.merge(codigoProduto);
            }
            Posicao codigoPosicao = aplicacao.getCodigoPosicao();
            if (codigoPosicao != null) {
                codigoPosicao.getAplicacaoCollection().remove(aplicacao);
                codigoPosicao = em.merge(codigoPosicao);
            }
            Pais codigoPais = aplicacao.getCodigoPais();
            if (codigoPais != null) {
                codigoPais.getAplicacaoCollection().remove(aplicacao);
                codigoPais = em.merge(codigoPais);
            }
            em.remove(aplicacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aplicacao> findAplicacaoEntities() {
        return findAplicacaoEntities(true, -1, -1);
    }

    public List<Aplicacao> findAplicacaoEntities(int maxResults, int firstResult) {
        return findAplicacaoEntities(false, maxResults, firstResult);
    }

    private List<Aplicacao> findAplicacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aplicacao.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Aplicacao findAplicacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aplicacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getAplicacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aplicacao> rt = cq.from(Aplicacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
