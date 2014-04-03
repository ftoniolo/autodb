/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.IllegalOrphanException;
import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.Marca;
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
public class MarcaJpaController1 implements Serializable {

    public MarcaJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Marca marca) {
        if (marca.getVeiculoBaseCollection() == null) {
            marca.setVeiculoBaseCollection(new ArrayList<VeiculoBase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<VeiculoBase> attachedVeiculoBaseCollection = new ArrayList<VeiculoBase>();
            for (VeiculoBase veiculoBaseCollectionVeiculoBaseToAttach : marca.getVeiculoBaseCollection()) {
                veiculoBaseCollectionVeiculoBaseToAttach = em.getReference(veiculoBaseCollectionVeiculoBaseToAttach.getClass(), veiculoBaseCollectionVeiculoBaseToAttach.getCodigoVeiculoBase());
                attachedVeiculoBaseCollection.add(veiculoBaseCollectionVeiculoBaseToAttach);
            }
            marca.setVeiculoBaseCollection(attachedVeiculoBaseCollection);
            em.persist(marca);
            for (VeiculoBase veiculoBaseCollectionVeiculoBase : marca.getVeiculoBaseCollection()) {
                Marca oldCodigoMarcaOfVeiculoBaseCollectionVeiculoBase = veiculoBaseCollectionVeiculoBase.getCodigoMarca();
                veiculoBaseCollectionVeiculoBase.setCodigoMarca(marca);
                veiculoBaseCollectionVeiculoBase = em.merge(veiculoBaseCollectionVeiculoBase);
                if (oldCodigoMarcaOfVeiculoBaseCollectionVeiculoBase != null) {
                    oldCodigoMarcaOfVeiculoBaseCollectionVeiculoBase.getVeiculoBaseCollection().remove(veiculoBaseCollectionVeiculoBase);
                    oldCodigoMarcaOfVeiculoBaseCollectionVeiculoBase = em.merge(oldCodigoMarcaOfVeiculoBaseCollectionVeiculoBase);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Marca marca) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca persistentMarca = em.find(Marca.class, marca.getCodigoMarca());
            Collection<VeiculoBase> veiculoBaseCollectionOld = persistentMarca.getVeiculoBaseCollection();
            Collection<VeiculoBase> veiculoBaseCollectionNew = marca.getVeiculoBaseCollection();
            List<String> illegalOrphanMessages = null;
            for (VeiculoBase veiculoBaseCollectionOldVeiculoBase : veiculoBaseCollectionOld) {
                if (!veiculoBaseCollectionNew.contains(veiculoBaseCollectionOldVeiculoBase)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VeiculoBase " + veiculoBaseCollectionOldVeiculoBase + " since its codigoMarca field is not nullable.");
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
            marca.setVeiculoBaseCollection(veiculoBaseCollectionNew);
            marca = em.merge(marca);
            for (VeiculoBase veiculoBaseCollectionNewVeiculoBase : veiculoBaseCollectionNew) {
                if (!veiculoBaseCollectionOld.contains(veiculoBaseCollectionNewVeiculoBase)) {
                    Marca oldCodigoMarcaOfVeiculoBaseCollectionNewVeiculoBase = veiculoBaseCollectionNewVeiculoBase.getCodigoMarca();
                    veiculoBaseCollectionNewVeiculoBase.setCodigoMarca(marca);
                    veiculoBaseCollectionNewVeiculoBase = em.merge(veiculoBaseCollectionNewVeiculoBase);
                    if (oldCodigoMarcaOfVeiculoBaseCollectionNewVeiculoBase != null && !oldCodigoMarcaOfVeiculoBaseCollectionNewVeiculoBase.equals(marca)) {
                        oldCodigoMarcaOfVeiculoBaseCollectionNewVeiculoBase.getVeiculoBaseCollection().remove(veiculoBaseCollectionNewVeiculoBase);
                        oldCodigoMarcaOfVeiculoBaseCollectionNewVeiculoBase = em.merge(oldCodigoMarcaOfVeiculoBaseCollectionNewVeiculoBase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marca.getCodigoMarca();
                if (findMarca(id) == null) {
                    throw new NonexistentEntityException("The marca with id " + id + " no longer exists.");
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
            Marca marca;
            try {
                marca = em.getReference(Marca.class, id);
                marca.getCodigoMarca();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marca with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<VeiculoBase> veiculoBaseCollectionOrphanCheck = marca.getVeiculoBaseCollection();
            for (VeiculoBase veiculoBaseCollectionOrphanCheckVeiculoBase : veiculoBaseCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Marca (" + marca + ") cannot be destroyed since the VeiculoBase " + veiculoBaseCollectionOrphanCheckVeiculoBase + " in its veiculoBaseCollection field has a non-nullable codigoMarca field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(marca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Marca> findMarcaEntities() {
        return findMarcaEntities(true, -1, -1);
    }

    public List<Marca> findMarcaEntities(int maxResults, int firstResult) {
        return findMarcaEntities(false, maxResults, firstResult);
    }

    private List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marca.class));
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

    public Marca findMarca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marca.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marca> rt = cq.from(Marca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
