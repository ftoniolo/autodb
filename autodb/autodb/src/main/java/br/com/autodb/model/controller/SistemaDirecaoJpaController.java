/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.SistemaDirecao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.Veiculo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class SistemaDirecaoJpaController implements Serializable {

    public SistemaDirecaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SistemaDirecao sistemaDirecao) {
        if (sistemaDirecao.getVeiculoCollection() == null) {
            sistemaDirecao.setVeiculoCollection(new ArrayList<Veiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Veiculo> attachedVeiculoCollection = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionVeiculoToAttach : sistemaDirecao.getVeiculoCollection()) {
                veiculoCollectionVeiculoToAttach = em.getReference(veiculoCollectionVeiculoToAttach.getClass(), veiculoCollectionVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollection.add(veiculoCollectionVeiculoToAttach);
            }
            sistemaDirecao.setVeiculoCollection(attachedVeiculoCollection);
            em.persist(sistemaDirecao);
            for (Veiculo veiculoCollectionVeiculo : sistemaDirecao.getVeiculoCollection()) {
                SistemaDirecao oldCodigoSistemaDirecaoOfVeiculoCollectionVeiculo = veiculoCollectionVeiculo.getCodigoSistemaDirecao();
                veiculoCollectionVeiculo.setCodigoSistemaDirecao(sistemaDirecao);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
                if (oldCodigoSistemaDirecaoOfVeiculoCollectionVeiculo != null) {
                    oldCodigoSistemaDirecaoOfVeiculoCollectionVeiculo.getVeiculoCollection().remove(veiculoCollectionVeiculo);
                    oldCodigoSistemaDirecaoOfVeiculoCollectionVeiculo = em.merge(oldCodigoSistemaDirecaoOfVeiculoCollectionVeiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SistemaDirecao sistemaDirecao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SistemaDirecao persistentSistemaDirecao = em.find(SistemaDirecao.class, sistemaDirecao.getCodigoSistemaDirecao());
            Collection<Veiculo> veiculoCollectionOld = persistentSistemaDirecao.getVeiculoCollection();
            Collection<Veiculo> veiculoCollectionNew = sistemaDirecao.getVeiculoCollection();
            Collection<Veiculo> attachedVeiculoCollectionNew = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionNewVeiculoToAttach : veiculoCollectionNew) {
                veiculoCollectionNewVeiculoToAttach = em.getReference(veiculoCollectionNewVeiculoToAttach.getClass(), veiculoCollectionNewVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollectionNew.add(veiculoCollectionNewVeiculoToAttach);
            }
            veiculoCollectionNew = attachedVeiculoCollectionNew;
            sistemaDirecao.setVeiculoCollection(veiculoCollectionNew);
            sistemaDirecao = em.merge(sistemaDirecao);
            for (Veiculo veiculoCollectionOldVeiculo : veiculoCollectionOld) {
                if (!veiculoCollectionNew.contains(veiculoCollectionOldVeiculo)) {
                    veiculoCollectionOldVeiculo.setCodigoSistemaDirecao(null);
                    veiculoCollectionOldVeiculo = em.merge(veiculoCollectionOldVeiculo);
                }
            }
            for (Veiculo veiculoCollectionNewVeiculo : veiculoCollectionNew) {
                if (!veiculoCollectionOld.contains(veiculoCollectionNewVeiculo)) {
                    SistemaDirecao oldCodigoSistemaDirecaoOfVeiculoCollectionNewVeiculo = veiculoCollectionNewVeiculo.getCodigoSistemaDirecao();
                    veiculoCollectionNewVeiculo.setCodigoSistemaDirecao(sistemaDirecao);
                    veiculoCollectionNewVeiculo = em.merge(veiculoCollectionNewVeiculo);
                    if (oldCodigoSistemaDirecaoOfVeiculoCollectionNewVeiculo != null && !oldCodigoSistemaDirecaoOfVeiculoCollectionNewVeiculo.equals(sistemaDirecao)) {
                        oldCodigoSistemaDirecaoOfVeiculoCollectionNewVeiculo.getVeiculoCollection().remove(veiculoCollectionNewVeiculo);
                        oldCodigoSistemaDirecaoOfVeiculoCollectionNewVeiculo = em.merge(oldCodigoSistemaDirecaoOfVeiculoCollectionNewVeiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sistemaDirecao.getCodigoSistemaDirecao();
                if (findSistemaDirecao(id) == null) {
                    throw new NonexistentEntityException("The sistemaDirecao with id " + id + " no longer exists.");
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
            SistemaDirecao sistemaDirecao;
            try {
                sistemaDirecao = em.getReference(SistemaDirecao.class, id);
                sistemaDirecao.getCodigoSistemaDirecao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sistemaDirecao with id " + id + " no longer exists.", enfe);
            }
            Collection<Veiculo> veiculoCollection = sistemaDirecao.getVeiculoCollection();
            for (Veiculo veiculoCollectionVeiculo : veiculoCollection) {
                veiculoCollectionVeiculo.setCodigoSistemaDirecao(null);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
            }
            em.remove(sistemaDirecao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SistemaDirecao> findSistemaDirecaoEntities() {
        return findSistemaDirecaoEntities(true, -1, -1);
    }

    public List<SistemaDirecao> findSistemaDirecaoEntities(int maxResults, int firstResult) {
        return findSistemaDirecaoEntities(false, maxResults, firstResult);
    }

    private List<SistemaDirecao> findSistemaDirecaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SistemaDirecao.class));
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

    public SistemaDirecao findSistemaDirecao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SistemaDirecao.class, id);
        } finally {
            em.close();
        }
    }

    public int getSistemaDirecaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SistemaDirecao> rt = cq.from(SistemaDirecao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
