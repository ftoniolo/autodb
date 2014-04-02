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
import br.com.autodb.model.dao.Aplicacao;
import java.util.ArrayList;
import java.util.Collection;
import br.com.autodb.model.dao.Veiculo;
import br.com.autodb.model.dao.Fabricante;
import br.com.autodb.model.dao.Pais;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) {
        if (pais.getAplicacaoCollection() == null) {
            pais.setAplicacaoCollection(new ArrayList<Aplicacao>());
        }
        if (pais.getVeiculoCollection() == null) {
            pais.setVeiculoCollection(new ArrayList<Veiculo>());
        }
        if (pais.getFabricanteCollection() == null) {
            pais.setFabricanteCollection(new ArrayList<Fabricante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Aplicacao> attachedAplicacaoCollection = new ArrayList<Aplicacao>();
            for (Aplicacao aplicacaoCollectionAplicacaoToAttach : pais.getAplicacaoCollection()) {
                aplicacaoCollectionAplicacaoToAttach = em.getReference(aplicacaoCollectionAplicacaoToAttach.getClass(), aplicacaoCollectionAplicacaoToAttach.getCodigoAplicacao());
                attachedAplicacaoCollection.add(aplicacaoCollectionAplicacaoToAttach);
            }
            pais.setAplicacaoCollection(attachedAplicacaoCollection);
            Collection<Veiculo> attachedVeiculoCollection = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionVeiculoToAttach : pais.getVeiculoCollection()) {
                veiculoCollectionVeiculoToAttach = em.getReference(veiculoCollectionVeiculoToAttach.getClass(), veiculoCollectionVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollection.add(veiculoCollectionVeiculoToAttach);
            }
            pais.setVeiculoCollection(attachedVeiculoCollection);
            Collection<Fabricante> attachedFabricanteCollection = new ArrayList<Fabricante>();
            for (Fabricante fabricanteCollectionFabricanteToAttach : pais.getFabricanteCollection()) {
                fabricanteCollectionFabricanteToAttach = em.getReference(fabricanteCollectionFabricanteToAttach.getClass(), fabricanteCollectionFabricanteToAttach.getCodigoFabricante());
                attachedFabricanteCollection.add(fabricanteCollectionFabricanteToAttach);
            }
            pais.setFabricanteCollection(attachedFabricanteCollection);
            em.persist(pais);
            for (Aplicacao aplicacaoCollectionAplicacao : pais.getAplicacaoCollection()) {
                Pais oldCodigoPaisOfAplicacaoCollectionAplicacao = aplicacaoCollectionAplicacao.getCodigoPais();
                aplicacaoCollectionAplicacao.setCodigoPais(pais);
                aplicacaoCollectionAplicacao = em.merge(aplicacaoCollectionAplicacao);
                if (oldCodigoPaisOfAplicacaoCollectionAplicacao != null) {
                    oldCodigoPaisOfAplicacaoCollectionAplicacao.getAplicacaoCollection().remove(aplicacaoCollectionAplicacao);
                    oldCodigoPaisOfAplicacaoCollectionAplicacao = em.merge(oldCodigoPaisOfAplicacaoCollectionAplicacao);
                }
            }
            for (Veiculo veiculoCollectionVeiculo : pais.getVeiculoCollection()) {
                Pais oldCodigoPaisFabricacaoOfVeiculoCollectionVeiculo = veiculoCollectionVeiculo.getCodigoPaisFabricacao();
                veiculoCollectionVeiculo.setCodigoPaisFabricacao(pais);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
                if (oldCodigoPaisFabricacaoOfVeiculoCollectionVeiculo != null) {
                    oldCodigoPaisFabricacaoOfVeiculoCollectionVeiculo.getVeiculoCollection().remove(veiculoCollectionVeiculo);
                    oldCodigoPaisFabricacaoOfVeiculoCollectionVeiculo = em.merge(oldCodigoPaisFabricacaoOfVeiculoCollectionVeiculo);
                }
            }
            for (Fabricante fabricanteCollectionFabricante : pais.getFabricanteCollection()) {
                Pais oldCodigoPaisOfFabricanteCollectionFabricante = fabricanteCollectionFabricante.getCodigoPais();
                fabricanteCollectionFabricante.setCodigoPais(pais);
                fabricanteCollectionFabricante = em.merge(fabricanteCollectionFabricante);
                if (oldCodigoPaisOfFabricanteCollectionFabricante != null) {
                    oldCodigoPaisOfFabricanteCollectionFabricante.getFabricanteCollection().remove(fabricanteCollectionFabricante);
                    oldCodigoPaisOfFabricanteCollectionFabricante = em.merge(oldCodigoPaisOfFabricanteCollectionFabricante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getCodigoPais());
            Collection<Aplicacao> aplicacaoCollectionOld = persistentPais.getAplicacaoCollection();
            Collection<Aplicacao> aplicacaoCollectionNew = pais.getAplicacaoCollection();
            Collection<Veiculo> veiculoCollectionOld = persistentPais.getVeiculoCollection();
            Collection<Veiculo> veiculoCollectionNew = pais.getVeiculoCollection();
            Collection<Fabricante> fabricanteCollectionOld = persistentPais.getFabricanteCollection();
            Collection<Fabricante> fabricanteCollectionNew = pais.getFabricanteCollection();
            Collection<Aplicacao> attachedAplicacaoCollectionNew = new ArrayList<Aplicacao>();
            for (Aplicacao aplicacaoCollectionNewAplicacaoToAttach : aplicacaoCollectionNew) {
                aplicacaoCollectionNewAplicacaoToAttach = em.getReference(aplicacaoCollectionNewAplicacaoToAttach.getClass(), aplicacaoCollectionNewAplicacaoToAttach.getCodigoAplicacao());
                attachedAplicacaoCollectionNew.add(aplicacaoCollectionNewAplicacaoToAttach);
            }
            aplicacaoCollectionNew = attachedAplicacaoCollectionNew;
            pais.setAplicacaoCollection(aplicacaoCollectionNew);
            Collection<Veiculo> attachedVeiculoCollectionNew = new ArrayList<Veiculo>();
            for (Veiculo veiculoCollectionNewVeiculoToAttach : veiculoCollectionNew) {
                veiculoCollectionNewVeiculoToAttach = em.getReference(veiculoCollectionNewVeiculoToAttach.getClass(), veiculoCollectionNewVeiculoToAttach.getCodigoVeiculo());
                attachedVeiculoCollectionNew.add(veiculoCollectionNewVeiculoToAttach);
            }
            veiculoCollectionNew = attachedVeiculoCollectionNew;
            pais.setVeiculoCollection(veiculoCollectionNew);
            Collection<Fabricante> attachedFabricanteCollectionNew = new ArrayList<Fabricante>();
            for (Fabricante fabricanteCollectionNewFabricanteToAttach : fabricanteCollectionNew) {
                fabricanteCollectionNewFabricanteToAttach = em.getReference(fabricanteCollectionNewFabricanteToAttach.getClass(), fabricanteCollectionNewFabricanteToAttach.getCodigoFabricante());
                attachedFabricanteCollectionNew.add(fabricanteCollectionNewFabricanteToAttach);
            }
            fabricanteCollectionNew = attachedFabricanteCollectionNew;
            pais.setFabricanteCollection(fabricanteCollectionNew);
            pais = em.merge(pais);
            for (Aplicacao aplicacaoCollectionOldAplicacao : aplicacaoCollectionOld) {
                if (!aplicacaoCollectionNew.contains(aplicacaoCollectionOldAplicacao)) {
                    aplicacaoCollectionOldAplicacao.setCodigoPais(null);
                    aplicacaoCollectionOldAplicacao = em.merge(aplicacaoCollectionOldAplicacao);
                }
            }
            for (Aplicacao aplicacaoCollectionNewAplicacao : aplicacaoCollectionNew) {
                if (!aplicacaoCollectionOld.contains(aplicacaoCollectionNewAplicacao)) {
                    Pais oldCodigoPaisOfAplicacaoCollectionNewAplicacao = aplicacaoCollectionNewAplicacao.getCodigoPais();
                    aplicacaoCollectionNewAplicacao.setCodigoPais(pais);
                    aplicacaoCollectionNewAplicacao = em.merge(aplicacaoCollectionNewAplicacao);
                    if (oldCodigoPaisOfAplicacaoCollectionNewAplicacao != null && !oldCodigoPaisOfAplicacaoCollectionNewAplicacao.equals(pais)) {
                        oldCodigoPaisOfAplicacaoCollectionNewAplicacao.getAplicacaoCollection().remove(aplicacaoCollectionNewAplicacao);
                        oldCodigoPaisOfAplicacaoCollectionNewAplicacao = em.merge(oldCodigoPaisOfAplicacaoCollectionNewAplicacao);
                    }
                }
            }
            for (Veiculo veiculoCollectionOldVeiculo : veiculoCollectionOld) {
                if (!veiculoCollectionNew.contains(veiculoCollectionOldVeiculo)) {
                    veiculoCollectionOldVeiculo.setCodigoPaisFabricacao(null);
                    veiculoCollectionOldVeiculo = em.merge(veiculoCollectionOldVeiculo);
                }
            }
            for (Veiculo veiculoCollectionNewVeiculo : veiculoCollectionNew) {
                if (!veiculoCollectionOld.contains(veiculoCollectionNewVeiculo)) {
                    Pais oldCodigoPaisFabricacaoOfVeiculoCollectionNewVeiculo = veiculoCollectionNewVeiculo.getCodigoPaisFabricacao();
                    veiculoCollectionNewVeiculo.setCodigoPaisFabricacao(pais);
                    veiculoCollectionNewVeiculo = em.merge(veiculoCollectionNewVeiculo);
                    if (oldCodigoPaisFabricacaoOfVeiculoCollectionNewVeiculo != null && !oldCodigoPaisFabricacaoOfVeiculoCollectionNewVeiculo.equals(pais)) {
                        oldCodigoPaisFabricacaoOfVeiculoCollectionNewVeiculo.getVeiculoCollection().remove(veiculoCollectionNewVeiculo);
                        oldCodigoPaisFabricacaoOfVeiculoCollectionNewVeiculo = em.merge(oldCodigoPaisFabricacaoOfVeiculoCollectionNewVeiculo);
                    }
                }
            }
            for (Fabricante fabricanteCollectionOldFabricante : fabricanteCollectionOld) {
                if (!fabricanteCollectionNew.contains(fabricanteCollectionOldFabricante)) {
                    fabricanteCollectionOldFabricante.setCodigoPais(null);
                    fabricanteCollectionOldFabricante = em.merge(fabricanteCollectionOldFabricante);
                }
            }
            for (Fabricante fabricanteCollectionNewFabricante : fabricanteCollectionNew) {
                if (!fabricanteCollectionOld.contains(fabricanteCollectionNewFabricante)) {
                    Pais oldCodigoPaisOfFabricanteCollectionNewFabricante = fabricanteCollectionNewFabricante.getCodigoPais();
                    fabricanteCollectionNewFabricante.setCodigoPais(pais);
                    fabricanteCollectionNewFabricante = em.merge(fabricanteCollectionNewFabricante);
                    if (oldCodigoPaisOfFabricanteCollectionNewFabricante != null && !oldCodigoPaisOfFabricanteCollectionNewFabricante.equals(pais)) {
                        oldCodigoPaisOfFabricanteCollectionNewFabricante.getFabricanteCollection().remove(fabricanteCollectionNewFabricante);
                        oldCodigoPaisOfFabricanteCollectionNewFabricante = em.merge(oldCodigoPaisOfFabricanteCollectionNewFabricante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pais.getCodigoPais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getCodigoPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            Collection<Aplicacao> aplicacaoCollection = pais.getAplicacaoCollection();
            for (Aplicacao aplicacaoCollectionAplicacao : aplicacaoCollection) {
                aplicacaoCollectionAplicacao.setCodigoPais(null);
                aplicacaoCollectionAplicacao = em.merge(aplicacaoCollectionAplicacao);
            }
            Collection<Veiculo> veiculoCollection = pais.getVeiculoCollection();
            for (Veiculo veiculoCollectionVeiculo : veiculoCollection) {
                veiculoCollectionVeiculo.setCodigoPaisFabricacao(null);
                veiculoCollectionVeiculo = em.merge(veiculoCollectionVeiculo);
            }
            Collection<Fabricante> fabricanteCollection = pais.getFabricanteCollection();
            for (Fabricante fabricanteCollectionFabricante : fabricanteCollection) {
                fabricanteCollectionFabricante.setCodigoPais(null);
                fabricanteCollectionFabricante = em.merge(fabricanteCollectionFabricante);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
