/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.Submodelo;
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
public class SubmodeloJpaController1 implements Serializable {

    public SubmodeloJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Submodelo submodelo) {
        if (submodelo.getVeiculoCollection() == null) {
            submodelo.setVeiculoCollection(new ArrayList<Veiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Veiculo> attachedVeiculoCollection = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionVeiculoToAttach : submodelo.getVeiculoCollection()) {
                veiculoCollectionVeiculoToAttach = em.getReference(veiculoCollectionVeiculoToAttach.getClass(), veiculoCollectionVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollection.add(veiculoCollectionVeiculoToAttach);
            }
            submodelo.setVeiculoCollection(attachedVeiculoCollection);
            em.persist(submodelo);
            for (Veiculo veiculoCollectionVeiculo : submodelo.getVeiculoCollection()) {
                Submodelo oldCodigoSubmodeloOfVeiculoCollectionVeiculo = veiculoCollectionVeiculo.getCodigoSubmodelo();
                veiculoCollectionVeiculo.setCodigoSubmodelo(submodelo);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
                if (oldCodigoSubmodeloOfVeiculoCollectionVeiculo != null) {
                    oldCodigoSubmodeloOfVeiculoCollectionVeiculo.getVeiculoCollection().remove(veiculoCollectionVeiculo);
                    oldCodigoSubmodeloOfVeiculoCollectionVeiculo = em.merge(oldCodigoSubmodeloOfVeiculoCollectionVeiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Submodelo submodelo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Submodelo persistentSubmodelo = em.find(Submodelo.class, submodelo.getCodigoSubmodelo());
            Collection<Veiculo> veiculoCollectionOld = persistentSubmodelo.getVeiculoCollection();
            Collection<Veiculo> veiculoCollectionNew = submodelo.getVeiculoCollection();
            Collection<Veiculo> attachedVeiculoCollectionNew = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionNewVeiculoToAttach : veiculoCollectionNew) {
                veiculoCollectionNewVeiculoToAttach = em.getReference(veiculoCollectionNewVeiculoToAttach.getClass(), veiculoCollectionNewVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollectionNew.add(veiculoCollectionNewVeiculoToAttach);
            }
            veiculoCollectionNew = attachedVeiculoCollectionNew;
            submodelo.setVeiculoCollection(veiculoCollectionNew);
            submodelo = em.merge(submodelo);
            for (Veiculo veiculoCollectionOldVeiculo : veiculoCollectionOld) {
                if (!veiculoCollectionNew.contains(veiculoCollectionOldVeiculo)) {
                    veiculoCollectionOldVeiculo.setCodigoSubmodelo(null);
                    veiculoCollectionOldVeiculo = em.merge(veiculoCollectionOldVeiculo);
                }
            }
            for (Veiculo veiculoCollectionNewVeiculo : veiculoCollectionNew) {
                if (!veiculoCollectionOld.contains(veiculoCollectionNewVeiculo)) {
                    Submodelo oldCodigoSubmodeloOfVeiculoCollectionNewVeiculo = veiculoCollectionNewVeiculo.getCodigoSubmodelo();
                    veiculoCollectionNewVeiculo.setCodigoSubmodelo(submodelo);
                    veiculoCollectionNewVeiculo = em.merge(veiculoCollectionNewVeiculo);
                    if (oldCodigoSubmodeloOfVeiculoCollectionNewVeiculo != null && !oldCodigoSubmodeloOfVeiculoCollectionNewVeiculo.equals(submodelo)) {
                        oldCodigoSubmodeloOfVeiculoCollectionNewVeiculo.getVeiculoCollection().remove(veiculoCollectionNewVeiculo);
                        oldCodigoSubmodeloOfVeiculoCollectionNewVeiculo = em.merge(oldCodigoSubmodeloOfVeiculoCollectionNewVeiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = submodelo.getCodigoSubmodelo();
                if (findSubmodelo(id) == null) {
                    throw new NonexistentEntityException("The submodelo with id " + id + " no longer exists.");
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
            Submodelo submodelo;
            try {
                submodelo = em.getReference(Submodelo.class, id);
                submodelo.getCodigoSubmodelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The submodelo with id " + id + " no longer exists.", enfe);
            }
            Collection<Veiculo> veiculoCollection = submodelo.getVeiculoCollection();
            for (Veiculo veiculoCollectionVeiculo : veiculoCollection) {
                veiculoCollectionVeiculo.setCodigoSubmodelo(null);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
            }
            em.remove(submodelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Submodelo> findSubmodeloEntities() {
        return findSubmodeloEntities(true, -1, -1);
    }

    public List<Submodelo> findSubmodeloEntities(int maxResults, int firstResult) {
        return findSubmodeloEntities(false, maxResults, firstResult);
    }

    private List<Submodelo> findSubmodeloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Submodelo.class));
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

    public Submodelo findSubmodelo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Submodelo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubmodeloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Submodelo> rt = cq.from(Submodelo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
