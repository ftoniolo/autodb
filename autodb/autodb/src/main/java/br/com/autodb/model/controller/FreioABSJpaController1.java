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
import br.com.autodb.model.dao.ConfiguracaoFreio;
import br.com.autodb.model.dao.FreioABS;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class FreioABSJpaController1 implements Serializable {

    public FreioABSJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FreioABS freioABS) {
        if (freioABS.getConfiguracaoFreioCollection() == null) {
            freioABS.setConfiguracaoFreioCollection(new ArrayList<ConfiguracaoFreio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ConfiguracaoFreio> attachedConfiguracaoFreioCollection = new ArrayList<ConfiguracaoFreio>();
            for (ConfiguracaoFreio configuracaoFreioCollectionConfiguracaoFreioToAttach : freioABS.getConfiguracaoFreioCollection()) {
                configuracaoFreioCollectionConfiguracaoFreioToAttach = em.getReference(configuracaoFreioCollectionConfiguracaoFreioToAttach.getClass(), configuracaoFreioCollectionConfiguracaoFreioToAttach.getCodigoConfiguracaoFreio());
                attachedConfiguracaoFreioCollection.add(configuracaoFreioCollectionConfiguracaoFreioToAttach);
            }
            freioABS.setConfiguracaoFreioCollection(attachedConfiguracaoFreioCollection);
            em.persist(freioABS);
            for (ConfiguracaoFreio configuracaoFreioCollectionConfiguracaoFreio : freioABS.getConfiguracaoFreioCollection()) {
                FreioABS oldCodigoFreioABSOfConfiguracaoFreioCollectionConfiguracaoFreio = configuracaoFreioCollectionConfiguracaoFreio.getCodigoFreioABS();
                configuracaoFreioCollectionConfiguracaoFreio.setCodigoFreioABS(freioABS);
                configuracaoFreioCollectionConfiguracaoFreio = em.merge(configuracaoFreioCollectionConfiguracaoFreio);
                if (oldCodigoFreioABSOfConfiguracaoFreioCollectionConfiguracaoFreio != null) {
                    oldCodigoFreioABSOfConfiguracaoFreioCollectionConfiguracaoFreio.getConfiguracaoFreioCollection().remove(configuracaoFreioCollectionConfiguracaoFreio);
                    oldCodigoFreioABSOfConfiguracaoFreioCollectionConfiguracaoFreio = em.merge(oldCodigoFreioABSOfConfiguracaoFreioCollectionConfiguracaoFreio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FreioABS freioABS) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FreioABS persistentFreioABS = em.find(FreioABS.class, freioABS.getCodigoFreioABS());
            Collection<ConfiguracaoFreio> configuracaoFreioCollectionOld = persistentFreioABS.getConfiguracaoFreioCollection();
            Collection<ConfiguracaoFreio> configuracaoFreioCollectionNew = freioABS.getConfiguracaoFreioCollection();
            Collection<ConfiguracaoFreio> attachedConfiguracaoFreioCollectionNew = new ArrayList<ConfiguracaoFreio>();
            for (ConfiguracaoFreio configuracaoFreioCollectionNewConfiguracaoFreioToAttach : configuracaoFreioCollectionNew) {
                configuracaoFreioCollectionNewConfiguracaoFreioToAttach = em.getReference(configuracaoFreioCollectionNewConfiguracaoFreioToAttach.getClass(), configuracaoFreioCollectionNewConfiguracaoFreioToAttach.getCodigoConfiguracaoFreio());
                attachedConfiguracaoFreioCollectionNew.add(configuracaoFreioCollectionNewConfiguracaoFreioToAttach);
            }
            configuracaoFreioCollectionNew = attachedConfiguracaoFreioCollectionNew;
            freioABS.setConfiguracaoFreioCollection(configuracaoFreioCollectionNew);
            freioABS = em.merge(freioABS);
            for (ConfiguracaoFreio configuracaoFreioCollectionOldConfiguracaoFreio : configuracaoFreioCollectionOld) {
                if (!configuracaoFreioCollectionNew.contains(configuracaoFreioCollectionOldConfiguracaoFreio)) {
                    configuracaoFreioCollectionOldConfiguracaoFreio.setCodigoFreioABS(null);
                    configuracaoFreioCollectionOldConfiguracaoFreio = em.merge(configuracaoFreioCollectionOldConfiguracaoFreio);
                }
            }
            for (ConfiguracaoFreio configuracaoFreioCollectionNewConfiguracaoFreio : configuracaoFreioCollectionNew) {
                if (!configuracaoFreioCollectionOld.contains(configuracaoFreioCollectionNewConfiguracaoFreio)) {
                    FreioABS oldCodigoFreioABSOfConfiguracaoFreioCollectionNewConfiguracaoFreio = configuracaoFreioCollectionNewConfiguracaoFreio.getCodigoFreioABS();
                    configuracaoFreioCollectionNewConfiguracaoFreio.setCodigoFreioABS(freioABS);
                    configuracaoFreioCollectionNewConfiguracaoFreio = em.merge(configuracaoFreioCollectionNewConfiguracaoFreio);
                    if (oldCodigoFreioABSOfConfiguracaoFreioCollectionNewConfiguracaoFreio != null && !oldCodigoFreioABSOfConfiguracaoFreioCollectionNewConfiguracaoFreio.equals(freioABS)) {
                        oldCodigoFreioABSOfConfiguracaoFreioCollectionNewConfiguracaoFreio.getConfiguracaoFreioCollection().remove(configuracaoFreioCollectionNewConfiguracaoFreio);
                        oldCodigoFreioABSOfConfiguracaoFreioCollectionNewConfiguracaoFreio = em.merge(oldCodigoFreioABSOfConfiguracaoFreioCollectionNewConfiguracaoFreio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = freioABS.getCodigoFreioABS();
                if (findFreioABS(id) == null) {
                    throw new NonexistentEntityException("The freioABS with id " + id + " no longer exists.");
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
            FreioABS freioABS;
            try {
                freioABS = em.getReference(FreioABS.class, id);
                freioABS.getCodigoFreioABS();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The freioABS with id " + id + " no longer exists.", enfe);
            }
            Collection<ConfiguracaoFreio> configuracaoFreioCollection = freioABS.getConfiguracaoFreioCollection();
            for (ConfiguracaoFreio configuracaoFreioCollectionConfiguracaoFreio : configuracaoFreioCollection) {
                configuracaoFreioCollectionConfiguracaoFreio.setCodigoFreioABS(null);
                configuracaoFreioCollectionConfiguracaoFreio = em.merge(configuracaoFreioCollectionConfiguracaoFreio);
            }
            em.remove(freioABS);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FreioABS> findFreioABSEntities() {
        return findFreioABSEntities(true, -1, -1);
    }

    public List<FreioABS> findFreioABSEntities(int maxResults, int firstResult) {
        return findFreioABSEntities(false, maxResults, firstResult);
    }

    private List<FreioABS> findFreioABSEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FreioABS.class));
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

    public FreioABS findFreioABS(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FreioABS.class, id);
        } finally {
            em.close();
        }
    }

    public int getFreioABSCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FreioABS> rt = cq.from(FreioABS.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
