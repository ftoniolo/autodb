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
import br.com.autodb.model.dao.Motor;
import br.com.autodb.model.dao.TipoMotor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class TipoMotorJpaController1 implements Serializable {

    public TipoMotorJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoMotor tipoMotor) {
        if (tipoMotor.getMotorCollection() == null) {
            tipoMotor.setMotorCollection(new ArrayList<Motor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Motor> attachedMotorCollection = new ArrayList<Motor>();
            for (Motor motorCollectionMotorToAttach : tipoMotor.getMotorCollection()) {
                motorCollectionMotorToAttach = em.getReference(motorCollectionMotorToAttach.getClass(), motorCollectionMotorToAttach.getCodigoMotor());
                attachedMotorCollection.add(motorCollectionMotorToAttach);
            }
            tipoMotor.setMotorCollection(attachedMotorCollection);
            em.persist(tipoMotor);
            for (Motor motorCollectionMotor : tipoMotor.getMotorCollection()) {
                TipoMotor oldCodigoTipoMotorOfMotorCollectionMotor = motorCollectionMotor.getCodigoTipoMotor();
                motorCollectionMotor.setCodigoTipoMotor(tipoMotor);
                motorCollectionMotor = em.merge(motorCollectionMotor);
                if (oldCodigoTipoMotorOfMotorCollectionMotor != null) {
                    oldCodigoTipoMotorOfMotorCollectionMotor.getMotorCollection().remove(motorCollectionMotor);
                    oldCodigoTipoMotorOfMotorCollectionMotor = em.merge(oldCodigoTipoMotorOfMotorCollectionMotor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoMotor tipoMotor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoMotor persistentTipoMotor = em.find(TipoMotor.class, tipoMotor.getCodigoTipoMotor());
            Collection<Motor> motorCollectionOld = persistentTipoMotor.getMotorCollection();
            Collection<Motor> motorCollectionNew = tipoMotor.getMotorCollection();
            Collection<Motor> attachedMotorCollectionNew = new ArrayList<Motor>();
            for (Motor motorCollectionNewMotorToAttach : motorCollectionNew) {
                motorCollectionNewMotorToAttach = em.getReference(motorCollectionNewMotorToAttach.getClass(), motorCollectionNewMotorToAttach.getCodigoMotor());
                attachedMotorCollectionNew.add(motorCollectionNewMotorToAttach);
            }
            motorCollectionNew = attachedMotorCollectionNew;
            tipoMotor.setMotorCollection(motorCollectionNew);
            tipoMotor = em.merge(tipoMotor);
            for (Motor motorCollectionOldMotor : motorCollectionOld) {
                if (!motorCollectionNew.contains(motorCollectionOldMotor)) {
                    motorCollectionOldMotor.setCodigoTipoMotor(null);
                    motorCollectionOldMotor = em.merge(motorCollectionOldMotor);
                }
            }
            for (Motor motorCollectionNewMotor : motorCollectionNew) {
                if (!motorCollectionOld.contains(motorCollectionNewMotor)) {
                    TipoMotor oldCodigoTipoMotorOfMotorCollectionNewMotor = motorCollectionNewMotor.getCodigoTipoMotor();
                    motorCollectionNewMotor.setCodigoTipoMotor(tipoMotor);
                    motorCollectionNewMotor = em.merge(motorCollectionNewMotor);
                    if (oldCodigoTipoMotorOfMotorCollectionNewMotor != null && !oldCodigoTipoMotorOfMotorCollectionNewMotor.equals(tipoMotor)) {
                        oldCodigoTipoMotorOfMotorCollectionNewMotor.getMotorCollection().remove(motorCollectionNewMotor);
                        oldCodigoTipoMotorOfMotorCollectionNewMotor = em.merge(oldCodigoTipoMotorOfMotorCollectionNewMotor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoMotor.getCodigoTipoMotor();
                if (findTipoMotor(id) == null) {
                    throw new NonexistentEntityException("The tipoMotor with id " + id + " no longer exists.");
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
            TipoMotor tipoMotor;
            try {
                tipoMotor = em.getReference(TipoMotor.class, id);
                tipoMotor.getCodigoTipoMotor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoMotor with id " + id + " no longer exists.", enfe);
            }
            Collection<Motor> motorCollection = tipoMotor.getMotorCollection();
            for (Motor motorCollectionMotor : motorCollection) {
                motorCollectionMotor.setCodigoTipoMotor(null);
                motorCollectionMotor = em.merge(motorCollectionMotor);
            }
            em.remove(tipoMotor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoMotor> findTipoMotorEntities() {
        return findTipoMotorEntities(true, -1, -1);
    }

    public List<TipoMotor> findTipoMotorEntities(int maxResults, int firstResult) {
        return findTipoMotorEntities(false, maxResults, firstResult);
    }

    private List<TipoMotor> findTipoMotorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoMotor.class));
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

    public TipoMotor findTipoMotor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoMotor.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoMotorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoMotor> rt = cq.from(TipoMotor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
