/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.IllegalOrphanException;
import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.Modelo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.VeiculoBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class ModeloJpaController implements Serializable {

    public ModeloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modelo modelo) {
        if (modelo.getVeiculoBaseCollection() == null) {
            modelo.setVeiculoBaseCollection(new ArrayList<VeiculoBase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<VeiculoBase> attachedVeiculoBaseCollection = new ArrayList<VeiculoBase>();
            for (VeiculoBase veiculoBaseCollectionVeiculoBaseToAttach : modelo.getVeiculoBaseCollection()) {
                veiculoBaseCollectionVeiculoBaseToAttach = em.getReference(veiculoBaseCollectionVeiculoBaseToAttach.getClass(), veiculoBaseCollectionVeiculoBaseToAttach.getCodigoVeiculoBase());
                attachedVeiculoBaseCollection.add(veiculoBaseCollectionVeiculoBaseToAttach);
            }
            modelo.setVeiculoBaseCollection(attachedVeiculoBaseCollection);
            em.persist(modelo);
            for (VeiculoBase veiculoBaseCollectionVeiculoBase : modelo.getVeiculoBaseCollection()) {
                Modelo oldCodigoModeloOfVeiculoBaseCollectionVeiculoBase = veiculoBaseCollectionVeiculoBase.getCodigoModelo();
                veiculoBaseCollectionVeiculoBase.setCodigoModelo(modelo);
                veiculoBaseCollectionVeiculoBase = em.merge(veiculoBaseCollectionVeiculoBase);
                if (oldCodigoModeloOfVeiculoBaseCollectionVeiculoBase != null) {
                    oldCodigoModeloOfVeiculoBaseCollectionVeiculoBase.getVeiculoBaseCollection().remove(veiculoBaseCollectionVeiculoBase);
                    oldCodigoModeloOfVeiculoBaseCollectionVeiculoBase = em.merge(oldCodigoModeloOfVeiculoBaseCollectionVeiculoBase);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modelo modelo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modelo persistentModelo = em.find(Modelo.class, modelo.getCodigoModelo());
            Collection<VeiculoBase> veiculoBaseCollectionOld = persistentModelo.getVeiculoBaseCollection();
            Collection<VeiculoBase> veiculoBaseCollectionNew = modelo.getVeiculoBaseCollection();
            List<String> illegalOrphanMessages = null;
            for (VeiculoBase veiculoBaseCollectionOldVeiculoBase : veiculoBaseCollectionOld) {
                if (!veiculoBaseCollectionNew.contains(veiculoBaseCollectionOldVeiculoBase)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VeiculoBase " + veiculoBaseCollectionOldVeiculoBase + " since its codigoModelo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<VeiculoBase> attachedVeiculoBaseCollectionNew = new ArrayList<VeiculoBase>();
            for (VeiculoBase veiculoBaseCollectionNewVeiculoBaseToAttach : veiculoBaseCollectionNew) {
                veiculoBaseCollectionNewVeiculoBaseToAttach = em.getReference(veiculoBaseCollectionNewVeiculoBaseToAttach.getClass(), veiculoBaseCollectionNewVeiculoBaseToAttach.getCodigoVeiculoBase());
                attachedVeiculoBaseCollectionNew.add(veiculoBaseCollectionNewVeiculoBaseToAttach);
            }
            veiculoBaseCollectionNew = attachedVeiculoBaseCollectionNew;
            modelo.setVeiculoBaseCollection(veiculoBaseCollectionNew);
            modelo = em.merge(modelo);
            for (VeiculoBase veiculoBaseCollectionNewVeiculoBase : veiculoBaseCollectionNew) {
                if (!veiculoBaseCollectionOld.contains(veiculoBaseCollectionNewVeiculoBase)) {
                    Modelo oldCodigoModeloOfVeiculoBaseCollectionNewVeiculoBase = veiculoBaseCollectionNewVeiculoBase.getCodigoModelo();
                    veiculoBaseCollectionNewVeiculoBase.setCodigoModelo(modelo);
                    veiculoBaseCollectionNewVeiculoBase = em.merge(veiculoBaseCollectionNewVeiculoBase);
                    if (oldCodigoModeloOfVeiculoBaseCollectionNewVeiculoBase != null && !oldCodigoModeloOfVeiculoBaseCollectionNewVeiculoBase.equals(modelo)) {
                        oldCodigoModeloOfVeiculoBaseCollectionNewVeiculoBase.getVeiculoBaseCollection().remove(veiculoBaseCollectionNewVeiculoBase);
                        oldCodigoModeloOfVeiculoBaseCollectionNewVeiculoBase = em.merge(oldCodigoModeloOfVeiculoBaseCollectionNewVeiculoBase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = modelo.getCodigoModelo();
                if (findModelo(id) == null) {
                    throw new NonexistentEntityException("The modelo with id " + id + " no longer exists.");
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
            Modelo modelo;
            try {
                modelo = em.getReference(Modelo.class, id);
                modelo.getCodigoModelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modelo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<VeiculoBase> veiculoBaseCollectionOrphanCheck = modelo.getVeiculoBaseCollection();
            for (VeiculoBase veiculoBaseCollectionOrphanCheckVeiculoBase : veiculoBaseCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Modelo (" + modelo + ") cannot be destroyed since the VeiculoBase " + veiculoBaseCollectionOrphanCheckVeiculoBase + " in its veiculoBaseCollection field has a non-nullable codigoModelo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(modelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modelo> findModeloEntities() {
        return findModeloEntities(true, -1, -1);
    }

    public List<Modelo> findModeloEntities(int maxResults, int firstResult) {
        return findModeloEntities(false, maxResults, firstResult);
    }

    private List<Modelo> findModeloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modelo.class));
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

    public Modelo findModelo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modelo.class, id);
        } finally {
            em.close();
        }
    }

    public int getModeloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modelo> rt = cq.from(Modelo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
