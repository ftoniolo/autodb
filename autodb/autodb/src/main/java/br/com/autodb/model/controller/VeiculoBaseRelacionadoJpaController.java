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
import br.com.autodb.model.dao.VeiculoBase;
import br.com.autodb.model.dao.VeiculoBaseRelacionado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class VeiculoBaseRelacionadoJpaController implements Serializable {

    public VeiculoBaseRelacionadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VeiculoBaseRelacionado veiculoBaseRelacionado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VeiculoBase codigoVeiculoBaseB = veiculoBaseRelacionado.getCodigoVeiculoBaseB();
            if (codigoVeiculoBaseB != null) {
                codigoVeiculoBaseB = em.getReference(codigoVeiculoBaseB.getClass(), codigoVeiculoBaseB.getCodigoVeiculoBase());
                veiculoBaseRelacionado.setCodigoVeiculoBaseB(codigoVeiculoBaseB);
            }
            VeiculoBase codigoVeiculoBaseA = veiculoBaseRelacionado.getCodigoVeiculoBaseA();
            if (codigoVeiculoBaseA != null) {
                codigoVeiculoBaseA = em.getReference(codigoVeiculoBaseA.getClass(), codigoVeiculoBaseA.getCodigoVeiculoBase());
                veiculoBaseRelacionado.setCodigoVeiculoBaseA(codigoVeiculoBaseA);
            }
            em.persist(veiculoBaseRelacionado);
            if (codigoVeiculoBaseB != null) {
                codigoVeiculoBaseB.getVeiculoBaseRelacionadoCollection().add(veiculoBaseRelacionado);
                codigoVeiculoBaseB = em.merge(codigoVeiculoBaseB);
            }
            if (codigoVeiculoBaseA != null) {
                codigoVeiculoBaseA.getVeiculoBaseRelacionadoCollection().add(veiculoBaseRelacionado);
                codigoVeiculoBaseA = em.merge(codigoVeiculoBaseA);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VeiculoBaseRelacionado veiculoBaseRelacionado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VeiculoBaseRelacionado persistentVeiculoBaseRelacionado = em.find(VeiculoBaseRelacionado.class, veiculoBaseRelacionado.getCodigoVeiculoBaseRelacionado());
            VeiculoBase codigoVeiculoBaseBOld = persistentVeiculoBaseRelacionado.getCodigoVeiculoBaseB();
            VeiculoBase codigoVeiculoBaseBNew = veiculoBaseRelacionado.getCodigoVeiculoBaseB();
            VeiculoBase codigoVeiculoBaseAOld = persistentVeiculoBaseRelacionado.getCodigoVeiculoBaseA();
            VeiculoBase codigoVeiculoBaseANew = veiculoBaseRelacionado.getCodigoVeiculoBaseA();
            if (codigoVeiculoBaseBNew != null) {
                codigoVeiculoBaseBNew = em.getReference(codigoVeiculoBaseBNew.getClass(), codigoVeiculoBaseBNew.getCodigoVeiculoBase());
                veiculoBaseRelacionado.setCodigoVeiculoBaseB(codigoVeiculoBaseBNew);
            }
            if (codigoVeiculoBaseANew != null) {
                codigoVeiculoBaseANew = em.getReference(codigoVeiculoBaseANew.getClass(), codigoVeiculoBaseANew.getCodigoVeiculoBase());
                veiculoBaseRelacionado.setCodigoVeiculoBaseA(codigoVeiculoBaseANew);
            }
            veiculoBaseRelacionado = em.merge(veiculoBaseRelacionado);
            if (codigoVeiculoBaseBOld != null && !codigoVeiculoBaseBOld.equals(codigoVeiculoBaseBNew)) {
                codigoVeiculoBaseBOld.getVeiculoBaseRelacionadoCollection().remove(veiculoBaseRelacionado);
                codigoVeiculoBaseBOld = em.merge(codigoVeiculoBaseBOld);
            }
            if (codigoVeiculoBaseBNew != null && !codigoVeiculoBaseBNew.equals(codigoVeiculoBaseBOld)) {
                codigoVeiculoBaseBNew.getVeiculoBaseRelacionadoCollection().add(veiculoBaseRelacionado);
                codigoVeiculoBaseBNew = em.merge(codigoVeiculoBaseBNew);
            }
            if (codigoVeiculoBaseAOld != null && !codigoVeiculoBaseAOld.equals(codigoVeiculoBaseANew)) {
                codigoVeiculoBaseAOld.getVeiculoBaseRelacionadoCollection().remove(veiculoBaseRelacionado);
                codigoVeiculoBaseAOld = em.merge(codigoVeiculoBaseAOld);
            }
            if (codigoVeiculoBaseANew != null && !codigoVeiculoBaseANew.equals(codigoVeiculoBaseAOld)) {
                codigoVeiculoBaseANew.getVeiculoBaseRelacionadoCollection().add(veiculoBaseRelacionado);
                codigoVeiculoBaseANew = em.merge(codigoVeiculoBaseANew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = veiculoBaseRelacionado.getCodigoVeiculoBaseRelacionado();
                if (findVeiculoBaseRelacionado(id) == null) {
                    throw new NonexistentEntityException("The veiculoBaseRelacionado with id " + id + " no longer exists.");
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
            VeiculoBaseRelacionado veiculoBaseRelacionado;
            try {
                veiculoBaseRelacionado = em.getReference(VeiculoBaseRelacionado.class, id);
                veiculoBaseRelacionado.getCodigoVeiculoBaseRelacionado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The veiculoBaseRelacionado with id " + id + " no longer exists.", enfe);
            }
            VeiculoBase codigoVeiculoBaseB = veiculoBaseRelacionado.getCodigoVeiculoBaseB();
            if (codigoVeiculoBaseB != null) {
                codigoVeiculoBaseB.getVeiculoBaseRelacionadoCollection().remove(veiculoBaseRelacionado);
                codigoVeiculoBaseB = em.merge(codigoVeiculoBaseB);
            }
            VeiculoBase codigoVeiculoBaseA = veiculoBaseRelacionado.getCodigoVeiculoBaseA();
            if (codigoVeiculoBaseA != null) {
                codigoVeiculoBaseA.getVeiculoBaseRelacionadoCollection().remove(veiculoBaseRelacionado);
                codigoVeiculoBaseA = em.merge(codigoVeiculoBaseA);
            }
            em.remove(veiculoBaseRelacionado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VeiculoBaseRelacionado> findVeiculoBaseRelacionadoEntities() {
        return findVeiculoBaseRelacionadoEntities(true, -1, -1);
    }

    public List<VeiculoBaseRelacionado> findVeiculoBaseRelacionadoEntities(int maxResults, int firstResult) {
        return findVeiculoBaseRelacionadoEntities(false, maxResults, firstResult);
    }

    private List<VeiculoBaseRelacionado> findVeiculoBaseRelacionadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VeiculoBaseRelacionado.class));
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

    public VeiculoBaseRelacionado findVeiculoBaseRelacionado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VeiculoBaseRelacionado.class, id);
        } finally {
            em.close();
        }
    }

    public int getVeiculoBaseRelacionadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VeiculoBaseRelacionado> rt = cq.from(VeiculoBaseRelacionado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
