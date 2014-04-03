/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.Injecao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.Motor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class InjecaoJpaController1 implements Serializable {

    public InjecaoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Injecao injecao) {
        if (injecao.getMotorCollection() == null) {
            injecao.setMotorCollection(new ArrayList<Motor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Motor> attachedMotorCollection = new ArrayList<Motor>();
            for (Motor motorCollectionMotorToAttach : injecao.getMotorCollection()) {
                motorCollectionMotorToAttach = em.getReference(motorCollectionMotorToAttach.getClass(), motorCollectionMotorToAttach.getCodigoMotor());
                attachedMotorCollection.add(motorCollectionMotorToAttach);
            }
            injecao.setMotorCollection(attachedMotorCollection);
            em.persist(injecao);
            for (Motor motorCollectionMotor : injecao.getMotorCollection()) {
                Injecao oldCodigoInjecaoOfMotorCollectionMotor = motorCollectionMotor.getCodigoInjecao();
                motorCollectionMotor.setCodigoInjecao(injecao);
                motorCollectionMotor = em.merge(motorCollectionMotor);
                if (oldCodigoInjecaoOfMotorCollectionMotor != null) {
                    oldCodigoInjecaoOfMotorCollectionMotor.getMotorCollection().remove(motorCollectionMotor);
                    oldCodigoInjecaoOfMotorCollectionMotor = em.merge(oldCodigoInjecaoOfMotorCollectionMotor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Injecao injecao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Injecao persistentInjecao = em.find(Injecao.class, injecao.getCodigoInjecao());
            Collection<Motor> motorCollectionOld = persistentInjecao.getMotorCollection();
            Collection<Motor> motorCollectionNew = injecao.getMotorCollection();
            Collection<Motor> attachedMotorCollectionNew = new ArrayList<Motor>();
            for (Motor motorCollectionNewMotorToAttach : motorCollectionNew) {
                motorCollectionNewMotorToAttach = em.getReference(motorCollectionNewMotorToAttach.getClass(), motorCollectionNewMotorToAttach.getCodigoMotor());
                attachedMotorCollectionNew.add(motorCollectionNewMotorToAttach);
            }
            motorCollectionNew = attachedMotorCollectionNew;
            injecao.setMotorCollection(motorCollectionNew);
            injecao = em.merge(injecao);
            for (Motor motorCollectionOldMotor : motorCollectionOld) {
                if (!motorCollectionNew.contains(motorCollectionOldMotor)) {
                    motorCollectionOldMotor.setCodigoInjecao(null);
                    motorCollectionOldMotor = em.merge(motorCollectionOldMotor);
                }
            }
            for (Motor motorCollectionNewMotor : motorCollectionNew) {
                if (!motorCollectionOld.contains(motorCollectionNewMotor)) {
                    Injecao oldCodigoInjecaoOfMotorCollectionNewMotor = motorCollectionNewMotor.getCodigoInjecao();
                    motorCollectionNewMotor.setCodigoInjecao(injecao);
                    motorCollectionNewMotor = em.merge(motorCollectionNewMotor);
                    if (oldCodigoInjecaoOfMotorCollectionNewMotor != null && !oldCodigoInjecaoOfMotorCollectionNewMotor.equals(injecao)) {
                        oldCodigoInjecaoOfMotorCollectionNewMotor.getMotorCollection().remove(motorCollectionNewMotor);
                        oldCodigoInjecaoOfMotorCollectionNewMotor = em.merge(oldCodigoInjecaoOfMotorCollectionNewMotor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = injecao.getCodigoInjecao();
                if (findInjecao(id) == null) {
                    throw new NonexistentEntityException("The injecao with id " + id + " no longer exists.");
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
            Injecao injecao;
            try {
                injecao = em.getReference(Injecao.class, id);
                injecao.getCodigoInjecao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The injecao with id " + id + " no longer exists.", enfe);
            }
            Collection<Motor> motorCollection = injecao.getMotorCollection();
            for (Motor motorCollectionMotor : motorCollection) {
                motorCollectionMotor.setCodigoInjecao(null);
                motorCollectionMotor = em.merge(motorCollectionMotor);
            }
            em.remove(injecao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Injecao> findInjecaoEntities() {
        return findInjecaoEntities(true, -1, -1);
    }

    public List<Injecao> findInjecaoEntities(int maxResults, int firstResult) {
        return findInjecaoEntities(false, maxResults, firstResult);
    }

    private List<Injecao> findInjecaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Injecao.class));
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

    public Injecao findInjecao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Injecao.class, id);
        } finally {
            em.close();
        }
    }

    public int getInjecaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Injecao> rt = cq.from(Injecao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
