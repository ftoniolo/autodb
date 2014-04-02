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
import br.com.autodb.model.dao.TipoCabecote;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class TipoCabecoteJpaController implements Serializable {

    public TipoCabecoteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCabecote tipoCabecote) {
        if (tipoCabecote.getMotorCollection() == null) {
            tipoCabecote.setMotorCollection(new ArrayList<Motor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Motor> attachedMotorCollection = new ArrayList<Motor>();
            for (Motor motorCollectionMotorToAttach : tipoCabecote.getMotorCollection()) {
                motorCollectionMotorToAttach = em.getReference(motorCollectionMotorToAttach.getClass(), motorCollectionMotorToAttach.getCodigoMotor());
                attachedMotorCollection.add(motorCollectionMotorToAttach);
            }
            tipoCabecote.setMotorCollection(attachedMotorCollection);
            em.persist(tipoCabecote);
            for (Motor motorCollectionMotor : tipoCabecote.getMotorCollection()) {
                TipoCabecote oldCodigoTipoCabecoteOfMotorCollectionMotor = motorCollectionMotor.getCodigoTipoCabecote();
                motorCollectionMotor.setCodigoTipoCabecote(tipoCabecote);
                motorCollectionMotor = em.merge(motorCollectionMotor);
                if (oldCodigoTipoCabecoteOfMotorCollectionMotor != null) {
                    oldCodigoTipoCabecoteOfMotorCollectionMotor.getMotorCollection().remove(motorCollectionMotor);
                    oldCodigoTipoCabecoteOfMotorCollectionMotor = em.merge(oldCodigoTipoCabecoteOfMotorCollectionMotor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoCabecote tipoCabecote) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCabecote persistentTipoCabecote = em.find(TipoCabecote.class, tipoCabecote.getCodigoTipoCabecote());
            Collection<Motor> motorCollectionOld = persistentTipoCabecote.getMotorCollection();
            Collection<Motor> motorCollectionNew = tipoCabecote.getMotorCollection();
            Collection<Motor> attachedMotorCollectionNew = new ArrayList<Motor>();
            for (Motor motorCollectionNewMotorToAttach : motorCollectionNew) {
                motorCollectionNewMotorToAttach = em.getReference(motorCollectionNewMotorToAttach.getClass(), motorCollectionNewMotorToAttach.getCodigoMotor());
                attachedMotorCollectionNew.add(motorCollectionNewMotorToAttach);
            }
            motorCollectionNew = attachedMotorCollectionNew;
            tipoCabecote.setMotorCollection(motorCollectionNew);
            tipoCabecote = em.merge(tipoCabecote);
            for (Motor motorCollectionOldMotor : motorCollectionOld) {
                if (!motorCollectionNew.contains(motorCollectionOldMotor)) {
                    motorCollectionOldMotor.setCodigoTipoCabecote(null);
                    motorCollectionOldMotor = em.merge(motorCollectionOldMotor);
                }
            }
            for (Motor motorCollectionNewMotor : motorCollectionNew) {
                if (!motorCollectionOld.contains(motorCollectionNewMotor)) {
                    TipoCabecote oldCodigoTipoCabecoteOfMotorCollectionNewMotor = motorCollectionNewMotor.getCodigoTipoCabecote();
                    motorCollectionNewMotor.setCodigoTipoCabecote(tipoCabecote);
                    motorCollectionNewMotor = em.merge(motorCollectionNewMotor);
                    if (oldCodigoTipoCabecoteOfMotorCollectionNewMotor != null && !oldCodigoTipoCabecoteOfMotorCollectionNewMotor.equals(tipoCabecote)) {
                        oldCodigoTipoCabecoteOfMotorCollectionNewMotor.getMotorCollection().remove(motorCollectionNewMotor);
                        oldCodigoTipoCabecoteOfMotorCollectionNewMotor = em.merge(oldCodigoTipoCabecoteOfMotorCollectionNewMotor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoCabecote.getCodigoTipoCabecote();
                if (findTipoCabecote(id) == null) {
                    throw new NonexistentEntityException("The tipoCabecote with id " + id + " no longer exists.");
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
            TipoCabecote tipoCabecote;
            try {
                tipoCabecote = em.getReference(TipoCabecote.class, id);
                tipoCabecote.getCodigoTipoCabecote();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCabecote with id " + id + " no longer exists.", enfe);
            }
            Collection<Motor> motorCollection = tipoCabecote.getMotorCollection();
            for (Motor motorCollectionMotor : motorCollection) {
                motorCollectionMotor.setCodigoTipoCabecote(null);
                motorCollectionMotor = em.merge(motorCollectionMotor);
            }
            em.remove(tipoCabecote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoCabecote> findTipoCabecoteEntities() {
        return findTipoCabecoteEntities(true, -1, -1);
    }

    public List<TipoCabecote> findTipoCabecoteEntities(int maxResults, int firstResult) {
        return findTipoCabecoteEntities(false, maxResults, firstResult);
    }

    private List<TipoCabecote> findTipoCabecoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCabecote.class));
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

    public TipoCabecote findTipoCabecote(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCabecote.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCabecoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCabecote> rt = cq.from(TipoCabecote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
