/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.IllegalOrphanException;
import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import br.com.autodb.model.dao.Fabricante;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.Pais;
import br.com.autodb.model.dao.FabricanteProduto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class FabricanteJpaController implements Serializable {

    public FabricanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fabricante fabricante) {
        if (fabricante.getFabricanteProdutoCollection() == null) {
            fabricante.setFabricanteProdutoCollection(new ArrayList<FabricanteProduto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais codigoPais = fabricante.getCodigoPais();
            if (codigoPais != null) {
                codigoPais = em.getReference(codigoPais.getClass(), codigoPais.getCodigoPais());
                fabricante.setCodigoPais(codigoPais);
            }
            Collection<FabricanteProduto> attachedFabricanteProdutoCollection = new ArrayList<FabricanteProduto>();
            for (FabricanteProduto fabricanteProdutoCollectionFabricanteProdutoToAttach : fabricante.getFabricanteProdutoCollection()) {
                fabricanteProdutoCollectionFabricanteProdutoToAttach = em.getReference(fabricanteProdutoCollectionFabricanteProdutoToAttach.getClass(), fabricanteProdutoCollectionFabricanteProdutoToAttach.getCodigoFabricanteProduto());
                attachedFabricanteProdutoCollection.add(fabricanteProdutoCollectionFabricanteProdutoToAttach);
            }
            fabricante.setFabricanteProdutoCollection(attachedFabricanteProdutoCollection);
            em.persist(fabricante);
            if (codigoPais != null) {
                codigoPais.getFabricanteCollection().add(fabricante);
                codigoPais = em.merge(codigoPais);
            }
            for (FabricanteProduto fabricanteProdutoCollectionFabricanteProduto : fabricante.getFabricanteProdutoCollection()) {
                Fabricante oldCodigoFabricanteOfFabricanteProdutoCollectionFabricanteProduto = fabricanteProdutoCollectionFabricanteProduto.getCodigoFabricante();
                fabricanteProdutoCollectionFabricanteProduto.setCodigoFabricante(fabricante);
                fabricanteProdutoCollectionFabricanteProduto = em.merge(fabricanteProdutoCollectionFabricanteProduto);
                if (oldCodigoFabricanteOfFabricanteProdutoCollectionFabricanteProduto != null) {
                    oldCodigoFabricanteOfFabricanteProdutoCollectionFabricanteProduto.getFabricanteProdutoCollection().remove(fabricanteProdutoCollectionFabricanteProduto);
                    oldCodigoFabricanteOfFabricanteProdutoCollectionFabricanteProduto = em.merge(oldCodigoFabricanteOfFabricanteProdutoCollectionFabricanteProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fabricante fabricante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fabricante persistentFabricante = em.find(Fabricante.class, fabricante.getCodigoFabricante());
            Pais codigoPaisOld = persistentFabricante.getCodigoPais();
            Pais codigoPaisNew = fabricante.getCodigoPais();
            Collection<FabricanteProduto> fabricanteProdutoCollectionOld = persistentFabricante.getFabricanteProdutoCollection();
            Collection<FabricanteProduto> fabricanteProdutoCollectionNew = fabricante.getFabricanteProdutoCollection();
            List<String> illegalOrphanMessages = null;
            for (FabricanteProduto fabricanteProdutoCollectionOldFabricanteProduto : fabricanteProdutoCollectionOld) {
                if (!fabricanteProdutoCollectionNew.contains(fabricanteProdutoCollectionOldFabricanteProduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FabricanteProduto " + fabricanteProdutoCollectionOldFabricanteProduto + " since its codigoFabricante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoPaisNew != null) {
                codigoPaisNew = em.getReference(codigoPaisNew.getClass(), codigoPaisNew.getCodigoPais());
                fabricante.setCodigoPais(codigoPaisNew);
            }
            Collection<FabricanteProduto> attachedFabricanteProdutoCollectionNew = new ArrayList<FabricanteProduto>();
            for (FabricanteProduto fabricanteProdutoCollectionNewFabricanteProdutoToAttach : fabricanteProdutoCollectionNew) {
                fabricanteProdutoCollectionNewFabricanteProdutoToAttach = em.getReference(fabricanteProdutoCollectionNewFabricanteProdutoToAttach.getClass(), fabricanteProdutoCollectionNewFabricanteProdutoToAttach.getCodigoFabricanteProduto());
                attachedFabricanteProdutoCollectionNew.add(fabricanteProdutoCollectionNewFabricanteProdutoToAttach);
            }
            fabricanteProdutoCollectionNew = attachedFabricanteProdutoCollectionNew;
            fabricante.setFabricanteProdutoCollection(fabricanteProdutoCollectionNew);
            fabricante = em.merge(fabricante);
            if (codigoPaisOld != null && !codigoPaisOld.equals(codigoPaisNew)) {
                codigoPaisOld.getFabricanteCollection().remove(fabricante);
                codigoPaisOld = em.merge(codigoPaisOld);
            }
            if (codigoPaisNew != null && !codigoPaisNew.equals(codigoPaisOld)) {
                codigoPaisNew.getFabricanteCollection().add(fabricante);
                codigoPaisNew = em.merge(codigoPaisNew);
            }
            for (FabricanteProduto fabricanteProdutoCollectionNewFabricanteProduto : fabricanteProdutoCollectionNew) {
                if (!fabricanteProdutoCollectionOld.contains(fabricanteProdutoCollectionNewFabricanteProduto)) {
                    Fabricante oldCodigoFabricanteOfFabricanteProdutoCollectionNewFabricanteProduto = fabricanteProdutoCollectionNewFabricanteProduto.getCodigoFabricante();
                    fabricanteProdutoCollectionNewFabricanteProduto.setCodigoFabricante(fabricante);
                    fabricanteProdutoCollectionNewFabricanteProduto = em.merge(fabricanteProdutoCollectionNewFabricanteProduto);
                    if (oldCodigoFabricanteOfFabricanteProdutoCollectionNewFabricanteProduto != null && !oldCodigoFabricanteOfFabricanteProdutoCollectionNewFabricanteProduto.equals(fabricante)) {
                        oldCodigoFabricanteOfFabricanteProdutoCollectionNewFabricanteProduto.getFabricanteProdutoCollection().remove(fabricanteProdutoCollectionNewFabricanteProduto);
                        oldCodigoFabricanteOfFabricanteProdutoCollectionNewFabricanteProduto = em.merge(oldCodigoFabricanteOfFabricanteProdutoCollectionNewFabricanteProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fabricante.getCodigoFabricante();
                if (findFabricante(id) == null) {
                    throw new NonexistentEntityException("The fabricante with id " + id + " no longer exists.");
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
            Fabricante fabricante;
            try {
                fabricante = em.getReference(Fabricante.class, id);
                fabricante.getCodigoFabricante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fabricante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<FabricanteProduto> fabricanteProdutoCollectionOrphanCheck = fabricante.getFabricanteProdutoCollection();
            for (FabricanteProduto fabricanteProdutoCollectionOrphanCheckFabricanteProduto : fabricanteProdutoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fabricante (" + fabricante + ") cannot be destroyed since the FabricanteProduto " + fabricanteProdutoCollectionOrphanCheckFabricanteProduto + " in its fabricanteProdutoCollection field has a non-nullable codigoFabricante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais codigoPais = fabricante.getCodigoPais();
            if (codigoPais != null) {
                codigoPais.getFabricanteCollection().remove(fabricante);
                codigoPais = em.merge(codigoPais);
            }
            em.remove(fabricante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fabricante> findFabricanteEntities() {
        return findFabricanteEntities(true, -1, -1);
    }

    public List<Fabricante> findFabricanteEntities(int maxResults, int firstResult) {
        return findFabricanteEntities(false, maxResults, firstResult);
    }

    private List<Fabricante> findFabricanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fabricante.class));
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

    public Fabricante findFabricante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fabricante.class, id);
        } finally {
            em.close();
        }
    }

    public int getFabricanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fabricante> rt = cq.from(Fabricante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
