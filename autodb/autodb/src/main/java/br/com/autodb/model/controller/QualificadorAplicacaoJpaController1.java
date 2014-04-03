/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.UnidadeMedida;
import br.com.autodb.model.dao.Qualificador;
import br.com.autodb.model.dao.Aplicacao;
import br.com.autodb.model.dao.QualificadorAplicacao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class QualificadorAplicacaoJpaController1 implements Serializable {

    public QualificadorAplicacaoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QualificadorAplicacao qualificadorAplicacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadeMedida codigoUnidadeMedida = qualificadorAplicacao.getCodigoUnidadeMedida();
            if (codigoUnidadeMedida != null) {
                codigoUnidadeMedida = em.getReference(codigoUnidadeMedida.getClass(), codigoUnidadeMedida.getCodigoUnidadeMedida());
                qualificadorAplicacao.setCodigoUnidadeMedida(codigoUnidadeMedida);
            }
            Qualificador codigoQualificador = qualificadorAplicacao.getCodigoQualificador();
            if (codigoQualificador != null) {
                codigoQualificador = em.getReference(codigoQualificador.getClass(), codigoQualificador.getCodigoQualificador());
                qualificadorAplicacao.setCodigoQualificador(codigoQualificador);
            }
            Aplicacao codigoAplicacao = qualificadorAplicacao.getCodigoAplicacao();
            if (codigoAplicacao != null) {
                codigoAplicacao = em.getReference(codigoAplicacao.getClass(), codigoAplicacao.getCodigoAplicacao());
                qualificadorAplicacao.setCodigoAplicacao(codigoAplicacao);
            }
            em.persist(qualificadorAplicacao);
            if (codigoUnidadeMedida != null) {
                codigoUnidadeMedida.getQualificadorAplicacaoCollection().add(qualificadorAplicacao);
                codigoUnidadeMedida = em.merge(codigoUnidadeMedida);
            }
            if (codigoQualificador != null) {
                codigoQualificador.getQualificadorAplicacaoCollection().add(qualificadorAplicacao);
                codigoQualificador = em.merge(codigoQualificador);
            }
            if (codigoAplicacao != null) {
                codigoAplicacao.getQualificadorAplicacaoCollection().add(qualificadorAplicacao);
                codigoAplicacao = em.merge(codigoAplicacao);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QualificadorAplicacao qualificadorAplicacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QualificadorAplicacao persistentQualificadorAplicacao = em.find(QualificadorAplicacao.class, qualificadorAplicacao.getCodigoQualificadorAplicacao());
            UnidadeMedida codigoUnidadeMedidaOld = persistentQualificadorAplicacao.getCodigoUnidadeMedida();
            UnidadeMedida codigoUnidadeMedidaNew = qualificadorAplicacao.getCodigoUnidadeMedida();
            Qualificador codigoQualificadorOld = persistentQualificadorAplicacao.getCodigoQualificador();
            Qualificador codigoQualificadorNew = qualificadorAplicacao.getCodigoQualificador();
            Aplicacao codigoAplicacaoOld = persistentQualificadorAplicacao.getCodigoAplicacao();
            Aplicacao codigoAplicacaoNew = qualificadorAplicacao.getCodigoAplicacao();
            if (codigoUnidadeMedidaNew != null) {
                codigoUnidadeMedidaNew = em.getReference(codigoUnidadeMedidaNew.getClass(), codigoUnidadeMedidaNew.getCodigoUnidadeMedida());
                qualificadorAplicacao.setCodigoUnidadeMedida(codigoUnidadeMedidaNew);
            }
            if (codigoQualificadorNew != null) {
                codigoQualificadorNew = em.getReference(codigoQualificadorNew.getClass(), codigoQualificadorNew.getCodigoQualificador());
                qualificadorAplicacao.setCodigoQualificador(codigoQualificadorNew);
            }
            if (codigoAplicacaoNew != null) {
                codigoAplicacaoNew = em.getReference(codigoAplicacaoNew.getClass(), codigoAplicacaoNew.getCodigoAplicacao());
                qualificadorAplicacao.setCodigoAplicacao(codigoAplicacaoNew);
            }
            qualificadorAplicacao = em.merge(qualificadorAplicacao);
            if (codigoUnidadeMedidaOld != null && !codigoUnidadeMedidaOld.equals(codigoUnidadeMedidaNew)) {
                codigoUnidadeMedidaOld.getQualificadorAplicacaoCollection().remove(qualificadorAplicacao);
                codigoUnidadeMedidaOld = em.merge(codigoUnidadeMedidaOld);
            }
            if (codigoUnidadeMedidaNew != null && !codigoUnidadeMedidaNew.equals(codigoUnidadeMedidaOld)) {
                codigoUnidadeMedidaNew.getQualificadorAplicacaoCollection().add(qualificadorAplicacao);
                codigoUnidadeMedidaNew = em.merge(codigoUnidadeMedidaNew);
            }
            if (codigoQualificadorOld != null && !codigoQualificadorOld.equals(codigoQualificadorNew)) {
                codigoQualificadorOld.getQualificadorAplicacaoCollection().remove(qualificadorAplicacao);
                codigoQualificadorOld = em.merge(codigoQualificadorOld);
            }
            if (codigoQualificadorNew != null && !codigoQualificadorNew.equals(codigoQualificadorOld)) {
                codigoQualificadorNew.getQualificadorAplicacaoCollection().add(qualificadorAplicacao);
                codigoQualificadorNew = em.merge(codigoQualificadorNew);
            }
            if (codigoAplicacaoOld != null && !codigoAplicacaoOld.equals(codigoAplicacaoNew)) {
                codigoAplicacaoOld.getQualificadorAplicacaoCollection().remove(qualificadorAplicacao);
                codigoAplicacaoOld = em.merge(codigoAplicacaoOld);
            }
            if (codigoAplicacaoNew != null && !codigoAplicacaoNew.equals(codigoAplicacaoOld)) {
                codigoAplicacaoNew.getQualificadorAplicacaoCollection().add(qualificadorAplicacao);
                codigoAplicacaoNew = em.merge(codigoAplicacaoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = qualificadorAplicacao.getCodigoQualificadorAplicacao();
                if (findQualificadorAplicacao(id) == null) {
                    throw new NonexistentEntityException("The qualificadorAplicacao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QualificadorAplicacao qualificadorAplicacao;
            try {
                qualificadorAplicacao = em.getReference(QualificadorAplicacao.class, id);
                qualificadorAplicacao.getCodigoQualificadorAplicacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The qualificadorAplicacao with id " + id + " no longer exists.", enfe);
            }
            UnidadeMedida codigoUnidadeMedida = qualificadorAplicacao.getCodigoUnidadeMedida();
            if (codigoUnidadeMedida != null) {
                codigoUnidadeMedida.getQualificadorAplicacaoCollection().remove(qualificadorAplicacao);
                codigoUnidadeMedida = em.merge(codigoUnidadeMedida);
            }
            Qualificador codigoQualificador = qualificadorAplicacao.getCodigoQualificador();
            if (codigoQualificador != null) {
                codigoQualificador.getQualificadorAplicacaoCollection().remove(qualificadorAplicacao);
                codigoQualificador = em.merge(codigoQualificador);
            }
            Aplicacao codigoAplicacao = qualificadorAplicacao.getCodigoAplicacao();
            if (codigoAplicacao != null) {
                codigoAplicacao.getQualificadorAplicacaoCollection().remove(qualificadorAplicacao);
                codigoAplicacao = em.merge(codigoAplicacao);
            }
            em.remove(qualificadorAplicacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QualificadorAplicacao> findQualificadorAplicacaoEntities() {
        return findQualificadorAplicacaoEntities(true, -1, -1);
    }

    public List<QualificadorAplicacao> findQualificadorAplicacaoEntities(int maxResults, int firstResult) {
        return findQualificadorAplicacaoEntities(false, maxResults, firstResult);
    }

    private List<QualificadorAplicacao> findQualificadorAplicacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QualificadorAplicacao.class));
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

    public QualificadorAplicacao findQualificadorAplicacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QualificadorAplicacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getQualificadorAplicacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QualificadorAplicacao> rt = cq.from(QualificadorAplicacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
