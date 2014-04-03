/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.autodb.model.controller;

import br.com.autodb.model.controller.exceptions.IllegalOrphanException;
import br.com.autodb.model.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.autodb.model.dao.StatusProduto;
import br.com.autodb.model.dao.LinhaProduto;
import br.com.autodb.model.dao.Aplicacao;
import java.util.ArrayList;
import java.util.Collection;
import br.com.autodb.model.dao.QualificadorProduto;
import br.com.autodb.model.dao.FabricanteProduto;
import br.com.autodb.model.dao.ComponenteProduto;
import br.com.autodb.model.dao.Produto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ftoniolo
 */
public class ProdutoJpaController1 implements Serializable {

    public ProdutoJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produto produto) {
        if (produto.getAplicacaoCollection() == null) {
            produto.setAplicacaoCollection(new ArrayList<Aplicacao>());
        }
        if (produto.getQualificadorProdutoCollection() == null) {
            produto.setQualificadorProdutoCollection(new ArrayList<QualificadorProduto>());
        }
        if (produto.getFabricanteProdutoCollection() == null) {
            produto.setFabricanteProdutoCollection(new ArrayList<FabricanteProduto>());
        }
        if (produto.getComponenteProdutoCollection() == null) {
            produto.setComponenteProdutoCollection(new ArrayList<ComponenteProduto>());
        }
        if (produto.getComponenteProdutoCollection1() == null) {
            produto.setComponenteProdutoCollection1(new ArrayList<ComponenteProduto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            StatusProduto codigoStatusProduto = produto.getCodigoStatusProduto();
            if (codigoStatusProduto != null) {
                codigoStatusProduto = em.getReference(codigoStatusProduto.getClass(), codigoStatusProduto.getCodigoStatusProduto());
                produto.setCodigoStatusProduto(codigoStatusProduto);
            }
            LinhaProduto codigoLinhaProduto = produto.getCodigoLinhaProduto();
            if (codigoLinhaProduto != null) {
                codigoLinhaProduto = em.getReference(codigoLinhaProduto.getClass(), codigoLinhaProduto.getCodigoLinhaProduto());
                produto.setCodigoLinhaProduto(codigoLinhaProduto);
            }
            Collection<Aplicacao> attachedAplicacaoCollection = new ArrayList<Aplicacao>();
            for (Aplicacao aplicacaoCollectionAplicacaoToAttach : produto.getAplicacaoCollection()) {
                aplicacaoCollectionAplicacaoToAttach = em.getReference(aplicacaoCollectionAplicacaoToAttach.getClass(), aplicacaoCollectionAplicacaoToAttach.getCodigoAplicacao());
                attachedAplicacaoCollection.add(aplicacaoCollectionAplicacaoToAttach);
            }
            produto.setAplicacaoCollection(attachedAplicacaoCollection);
            Collection<QualificadorProduto> attachedQualificadorProdutoCollection = new ArrayList<QualificadorProduto>();
            for (QualificadorProduto qualificadorProdutoCollectionQualificadorProdutoToAttach : produto.getQualificadorProdutoCollection()) {
                qualificadorProdutoCollectionQualificadorProdutoToAttach = em.getReference(qualificadorProdutoCollectionQualificadorProdutoToAttach.getClass(), qualificadorProdutoCollectionQualificadorProdutoToAttach.getCodigoQualificadorProduto());
                attachedQualificadorProdutoCollection.add(qualificadorProdutoCollectionQualificadorProdutoToAttach);
            }
            produto.setQualificadorProdutoCollection(attachedQualificadorProdutoCollection);
            Collection<FabricanteProduto> attachedFabricanteProdutoCollection = new ArrayList<FabricanteProduto>();
            for (FabricanteProduto fabricanteProdutoCollectionFabricanteProdutoToAttach : produto.getFabricanteProdutoCollection()) {
                fabricanteProdutoCollectionFabricanteProdutoToAttach = em.getReference(fabricanteProdutoCollectionFabricanteProdutoToAttach.getClass(), fabricanteProdutoCollectionFabricanteProdutoToAttach.getCodigoFabricanteProduto());
                attachedFabricanteProdutoCollection.add(fabricanteProdutoCollectionFabricanteProdutoToAttach);
            }
            produto.setFabricanteProdutoCollection(attachedFabricanteProdutoCollection);
            Collection<ComponenteProduto> attachedComponenteProdutoCollection = new ArrayList<ComponenteProduto>();
            for (ComponenteProduto componenteProdutoCollectionComponenteProdutoToAttach : produto.getComponenteProdutoCollection()) {
                componenteProdutoCollectionComponenteProdutoToAttach = em.getReference(componenteProdutoCollectionComponenteProdutoToAttach.getClass(), componenteProdutoCollectionComponenteProdutoToAttach.getCodigoProdutoProduto());
                attachedComponenteProdutoCollection.add(componenteProdutoCollectionComponenteProdutoToAttach);
            }
            produto.setComponenteProdutoCollection(attachedComponenteProdutoCollection);
            Collection<ComponenteProduto> attachedComponenteProdutoCollection1 = new ArrayList<ComponenteProduto>();
            for (ComponenteProduto componenteProdutoCollection1ComponenteProdutoToAttach : produto.getComponenteProdutoCollection1()) {
                componenteProdutoCollection1ComponenteProdutoToAttach = em.getReference(componenteProdutoCollection1ComponenteProdutoToAttach.getClass(), componenteProdutoCollection1ComponenteProdutoToAttach.getCodigoProdutoProduto());
                attachedComponenteProdutoCollection1.add(componenteProdutoCollection1ComponenteProdutoToAttach);
            }
            produto.setComponenteProdutoCollection1(attachedComponenteProdutoCollection1);
            em.persist(produto);
            if (codigoStatusProduto != null) {
                codigoStatusProduto.getProdutoCollection().add(produto);
                codigoStatusProduto = em.merge(codigoStatusProduto);
            }
            if (codigoLinhaProduto != null) {
                codigoLinhaProduto.getProdutoCollection().add(produto);
                codigoLinhaProduto = em.merge(codigoLinhaProduto);
            }
            for (Aplicacao aplicacaoCollectionAplicacao : produto.getAplicacaoCollection()) {
                Produto oldCodigoProdutoOfAplicacaoCollectionAplicacao = aplicacaoCollectionAplicacao.getCodigoProduto();
                aplicacaoCollectionAplicacao.setCodigoProduto(produto);
                aplicacaoCollectionAplicacao = em.merge(aplicacaoCollectionAplicacao);
                if (oldCodigoProdutoOfAplicacaoCollectionAplicacao != null) {
                    oldCodigoProdutoOfAplicacaoCollectionAplicacao.getAplicacaoCollection().remove(aplicacaoCollectionAplicacao);
                    oldCodigoProdutoOfAplicacaoCollectionAplicacao = em.merge(oldCodigoProdutoOfAplicacaoCollectionAplicacao);
                }
            }
            for (QualificadorProduto qualificadorProdutoCollectionQualificadorProduto : produto.getQualificadorProdutoCollection()) {
                Produto oldCodigoProdutoOfQualificadorProdutoCollectionQualificadorProduto = qualificadorProdutoCollectionQualificadorProduto.getCodigoProduto();
                qualificadorProdutoCollectionQualificadorProduto.setCodigoProduto(produto);
                qualificadorProdutoCollectionQualificadorProduto = em.merge(qualificadorProdutoCollectionQualificadorProduto);
                if (oldCodigoProdutoOfQualificadorProdutoCollectionQualificadorProduto != null) {
                    oldCodigoProdutoOfQualificadorProdutoCollectionQualificadorProduto.getQualificadorProdutoCollection().remove(qualificadorProdutoCollectionQualificadorProduto);
                    oldCodigoProdutoOfQualificadorProdutoCollectionQualificadorProduto = em.merge(oldCodigoProdutoOfQualificadorProdutoCollectionQualificadorProduto);
                }
            }
            for (FabricanteProduto fabricanteProdutoCollectionFabricanteProduto : produto.getFabricanteProdutoCollection()) {
                Produto oldCodigoProdutoOfFabricanteProdutoCollectionFabricanteProduto = fabricanteProdutoCollectionFabricanteProduto.getCodigoProduto();
                fabricanteProdutoCollectionFabricanteProduto.setCodigoProduto(produto);
                fabricanteProdutoCollectionFabricanteProduto = em.merge(fabricanteProdutoCollectionFabricanteProduto);
                if (oldCodigoProdutoOfFabricanteProdutoCollectionFabricanteProduto != null) {
                    oldCodigoProdutoOfFabricanteProdutoCollectionFabricanteProduto.getFabricanteProdutoCollection().remove(fabricanteProdutoCollectionFabricanteProduto);
                    oldCodigoProdutoOfFabricanteProdutoCollectionFabricanteProduto = em.merge(oldCodigoProdutoOfFabricanteProdutoCollectionFabricanteProduto);
                }
            }
            for (ComponenteProduto componenteProdutoCollectionComponenteProduto : produto.getComponenteProdutoCollection()) {
                Produto oldCodigoProdutoFilhoOfComponenteProdutoCollectionComponenteProduto = componenteProdutoCollectionComponenteProduto.getCodigoProdutoFilho();
                componenteProdutoCollectionComponenteProduto.setCodigoProdutoFilho(produto);
                componenteProdutoCollectionComponenteProduto = em.merge(componenteProdutoCollectionComponenteProduto);
                if (oldCodigoProdutoFilhoOfComponenteProdutoCollectionComponenteProduto != null) {
                    oldCodigoProdutoFilhoOfComponenteProdutoCollectionComponenteProduto.getComponenteProdutoCollection().remove(componenteProdutoCollectionComponenteProduto);
                    oldCodigoProdutoFilhoOfComponenteProdutoCollectionComponenteProduto = em.merge(oldCodigoProdutoFilhoOfComponenteProdutoCollectionComponenteProduto);
                }
            }
            for (ComponenteProduto componenteProdutoCollection1ComponenteProduto : produto.getComponenteProdutoCollection1()) {
                Produto oldCodigoProdutoPaiOfComponenteProdutoCollection1ComponenteProduto = componenteProdutoCollection1ComponenteProduto.getCodigoProdutoPai();
                componenteProdutoCollection1ComponenteProduto.setCodigoProdutoPai(produto);
                componenteProdutoCollection1ComponenteProduto = em.merge(componenteProdutoCollection1ComponenteProduto);
                if (oldCodigoProdutoPaiOfComponenteProdutoCollection1ComponenteProduto != null) {
                    oldCodigoProdutoPaiOfComponenteProdutoCollection1ComponenteProduto.getComponenteProdutoCollection1().remove(componenteProdutoCollection1ComponenteProduto);
                    oldCodigoProdutoPaiOfComponenteProdutoCollection1ComponenteProduto = em.merge(oldCodigoProdutoPaiOfComponenteProdutoCollection1ComponenteProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produto produto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto persistentProduto = em.find(Produto.class, produto.getCodigoProduto());
            StatusProduto codigoStatusProdutoOld = persistentProduto.getCodigoStatusProduto();
            StatusProduto codigoStatusProdutoNew = produto.getCodigoStatusProduto();
            LinhaProduto codigoLinhaProdutoOld = persistentProduto.getCodigoLinhaProduto();
            LinhaProduto codigoLinhaProdutoNew = produto.getCodigoLinhaProduto();
            Collection<Aplicacao> aplicacaoCollectionOld = persistentProduto.getAplicacaoCollection();
            Collection<Aplicacao> aplicacaoCollectionNew = produto.getAplicacaoCollection();
            Collection<QualificadorProduto> qualificadorProdutoCollectionOld = persistentProduto.getQualificadorProdutoCollection();
            Collection<QualificadorProduto> qualificadorProdutoCollectionNew = produto.getQualificadorProdutoCollection();
            Collection<FabricanteProduto> fabricanteProdutoCollectionOld = persistentProduto.getFabricanteProdutoCollection();
            Collection<FabricanteProduto> fabricanteProdutoCollectionNew = produto.getFabricanteProdutoCollection();
            Collection<ComponenteProduto> componenteProdutoCollectionOld = persistentProduto.getComponenteProdutoCollection();
            Collection<ComponenteProduto> componenteProdutoCollectionNew = produto.getComponenteProdutoCollection();
            Collection<ComponenteProduto> componenteProdutoCollection1Old = persistentProduto.getComponenteProdutoCollection1();
            Collection<ComponenteProduto> componenteProdutoCollection1New = produto.getComponenteProdutoCollection1();
            List<String> illegalOrphanMessages = null;
            for (Aplicacao aplicacaoCollectionOldAplicacao : aplicacaoCollectionOld) {
                if (!aplicacaoCollectionNew.contains(aplicacaoCollectionOldAplicacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Aplicacao " + aplicacaoCollectionOldAplicacao + " since its codigoProduto field is not nullable.");
                }
            }
            for (QualificadorProduto qualificadorProdutoCollectionOldQualificadorProduto : qualificadorProdutoCollectionOld) {
                if (!qualificadorProdutoCollectionNew.contains(qualificadorProdutoCollectionOldQualificadorProduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain QualificadorProduto " + qualificadorProdutoCollectionOldQualificadorProduto + " since its codigoProduto field is not nullable.");
                }
            }
            for (FabricanteProduto fabricanteProdutoCollectionOldFabricanteProduto : fabricanteProdutoCollectionOld) {
                if (!fabricanteProdutoCollectionNew.contains(fabricanteProdutoCollectionOldFabricanteProduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FabricanteProduto " + fabricanteProdutoCollectionOldFabricanteProduto + " since its codigoProduto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoStatusProdutoNew != null) {
                codigoStatusProdutoNew = em.getReference(codigoStatusProdutoNew.getClass(), codigoStatusProdutoNew.getCodigoStatusProduto());
                produto.setCodigoStatusProduto(codigoStatusProdutoNew);
            }
            if (codigoLinhaProdutoNew != null) {
                codigoLinhaProdutoNew = em.getReference(codigoLinhaProdutoNew.getClass(), codigoLinhaProdutoNew.getCodigoLinhaProduto());
                produto.setCodigoLinhaProduto(codigoLinhaProdutoNew);
            }
            Collection<Aplicacao> attachedAplicacaoCollectionNew = new ArrayList<Aplicacao>();
            for (Aplicacao aplicacaoCollectionNewAplicacaoToAttach : aplicacaoCollectionNew) {
                aplicacaoCollectionNewAplicacaoToAttach = em.getReference(aplicacaoCollectionNewAplicacaoToAttach.getClass(), aplicacaoCollectionNewAplicacaoToAttach.getCodigoAplicacao());
                attachedAplicacaoCollectionNew.add(aplicacaoCollectionNewAplicacaoToAttach);
            }
            aplicacaoCollectionNew = attachedAplicacaoCollectionNew;
            produto.setAplicacaoCollection(aplicacaoCollectionNew);
            Collection<QualificadorProduto> attachedQualificadorProdutoCollectionNew = new ArrayList<QualificadorProduto>();
            for (QualificadorProduto qualificadorProdutoCollectionNewQualificadorProdutoToAttach : qualificadorProdutoCollectionNew) {
                qualificadorProdutoCollectionNewQualificadorProdutoToAttach = em.getReference(qualificadorProdutoCollectionNewQualificadorProdutoToAttach.getClass(), qualificadorProdutoCollectionNewQualificadorProdutoToAttach.getCodigoQualificadorProduto());
                attachedQualificadorProdutoCollectionNew.add(qualificadorProdutoCollectionNewQualificadorProdutoToAttach);
            }
            qualificadorProdutoCollectionNew = attachedQualificadorProdutoCollectionNew;
            produto.setQualificadorProdutoCollection(qualificadorProdutoCollectionNew);
            Collection<FabricanteProduto> attachedFabricanteProdutoCollectionNew = new ArrayList<FabricanteProduto>();
            for (FabricanteProduto fabricanteProdutoCollectionNewFabricanteProdutoToAttach : fabricanteProdutoCollectionNew) {
                fabricanteProdutoCollectionNewFabricanteProdutoToAttach = em.getReference(fabricanteProdutoCollectionNewFabricanteProdutoToAttach.getClass(), fabricanteProdutoCollectionNewFabricanteProdutoToAttach.getCodigoFabricanteProduto());
                attachedFabricanteProdutoCollectionNew.add(fabricanteProdutoCollectionNewFabricanteProdutoToAttach);
            }
            fabricanteProdutoCollectionNew = attachedFabricanteProdutoCollectionNew;
            produto.setFabricanteProdutoCollection(fabricanteProdutoCollectionNew);
            Collection<ComponenteProduto> attachedComponenteProdutoCollectionNew = new ArrayList<ComponenteProduto>();
            for (ComponenteProduto componenteProdutoCollectionNewComponenteProdutoToAttach : componenteProdutoCollectionNew) {
                componenteProdutoCollectionNewComponenteProdutoToAttach = em.getReference(componenteProdutoCollectionNewComponenteProdutoToAttach.getClass(), componenteProdutoCollectionNewComponenteProdutoToAttach.getCodigoProdutoProduto());
                attachedComponenteProdutoCollectionNew.add(componenteProdutoCollectionNewComponenteProdutoToAttach);
            }
            componenteProdutoCollectionNew = attachedComponenteProdutoCollectionNew;
            produto.setComponenteProdutoCollection(componenteProdutoCollectionNew);
            Collection<ComponenteProduto> attachedComponenteProdutoCollection1New = new ArrayList<ComponenteProduto>();
            for (ComponenteProduto componenteProdutoCollection1NewComponenteProdutoToAttach : componenteProdutoCollection1New) {
                componenteProdutoCollection1NewComponenteProdutoToAttach = em.getReference(componenteProdutoCollection1NewComponenteProdutoToAttach.getClass(), componenteProdutoCollection1NewComponenteProdutoToAttach.getCodigoProdutoProduto());
                attachedComponenteProdutoCollection1New.add(componenteProdutoCollection1NewComponenteProdutoToAttach);
            }
            componenteProdutoCollection1New = attachedComponenteProdutoCollection1New;
            produto.setComponenteProdutoCollection1(componenteProdutoCollection1New);
            produto = em.merge(produto);
            if (codigoStatusProdutoOld != null && !codigoStatusProdutoOld.equals(codigoStatusProdutoNew)) {
                codigoStatusProdutoOld.getProdutoCollection().remove(produto);
                codigoStatusProdutoOld = em.merge(codigoStatusProdutoOld);
            }
            if (codigoStatusProdutoNew != null && !codigoStatusProdutoNew.equals(codigoStatusProdutoOld)) {
                codigoStatusProdutoNew.getProdutoCollection().add(produto);
                codigoStatusProdutoNew = em.merge(codigoStatusProdutoNew);
            }
            if (codigoLinhaProdutoOld != null && !codigoLinhaProdutoOld.equals(codigoLinhaProdutoNew)) {
                codigoLinhaProdutoOld.getProdutoCollection().remove(produto);
                codigoLinhaProdutoOld = em.merge(codigoLinhaProdutoOld);
            }
            if (codigoLinhaProdutoNew != null && !codigoLinhaProdutoNew.equals(codigoLinhaProdutoOld)) {
                codigoLinhaProdutoNew.getProdutoCollection().add(produto);
                codigoLinhaProdutoNew = em.merge(codigoLinhaProdutoNew);
            }
            for (Aplicacao aplicacaoCollectionNewAplicacao : aplicacaoCollectionNew) {
                if (!aplicacaoCollectionOld.contains(aplicacaoCollectionNewAplicacao)) {
                    Produto oldCodigoProdutoOfAplicacaoCollectionNewAplicacao = aplicacaoCollectionNewAplicacao.getCodigoProduto();
                    aplicacaoCollectionNewAplicacao.setCodigoProduto(produto);
                    aplicacaoCollectionNewAplicacao = em.merge(aplicacaoCollectionNewAplicacao);
                    if (oldCodigoProdutoOfAplicacaoCollectionNewAplicacao != null && !oldCodigoProdutoOfAplicacaoCollectionNewAplicacao.equals(produto)) {
                        oldCodigoProdutoOfAplicacaoCollectionNewAplicacao.getAplicacaoCollection().remove(aplicacaoCollectionNewAplicacao);
                        oldCodigoProdutoOfAplicacaoCollectionNewAplicacao = em.merge(oldCodigoProdutoOfAplicacaoCollectionNewAplicacao);
                    }
                }
            }
            for (QualificadorProduto qualificadorProdutoCollectionNewQualificadorProduto : qualificadorProdutoCollectionNew) {
                if (!qualificadorProdutoCollectionOld.contains(qualificadorProdutoCollectionNewQualificadorProduto)) {
                    Produto oldCodigoProdutoOfQualificadorProdutoCollectionNewQualificadorProduto = qualificadorProdutoCollectionNewQualificadorProduto.getCodigoProduto();
                    qualificadorProdutoCollectionNewQualificadorProduto.setCodigoProduto(produto);
                    qualificadorProdutoCollectionNewQualificadorProduto = em.merge(qualificadorProdutoCollectionNewQualificadorProduto);
                    if (oldCodigoProdutoOfQualificadorProdutoCollectionNewQualificadorProduto != null && !oldCodigoProdutoOfQualificadorProdutoCollectionNewQualificadorProduto.equals(produto)) {
                        oldCodigoProdutoOfQualificadorProdutoCollectionNewQualificadorProduto.getQualificadorProdutoCollection().remove(qualificadorProdutoCollectionNewQualificadorProduto);
                        oldCodigoProdutoOfQualificadorProdutoCollectionNewQualificadorProduto = em.merge(oldCodigoProdutoOfQualificadorProdutoCollectionNewQualificadorProduto);
                    }
                }
            }
            for (FabricanteProduto fabricanteProdutoCollectionNewFabricanteProduto : fabricanteProdutoCollectionNew) {
                if (!fabricanteProdutoCollectionOld.contains(fabricanteProdutoCollectionNewFabricanteProduto)) {
                    Produto oldCodigoProdutoOfFabricanteProdutoCollectionNewFabricanteProduto = fabricanteProdutoCollectionNewFabricanteProduto.getCodigoProduto();
                    fabricanteProdutoCollectionNewFabricanteProduto.setCodigoProduto(produto);
                    fabricanteProdutoCollectionNewFabricanteProduto = em.merge(fabricanteProdutoCollectionNewFabricanteProduto);
                    if (oldCodigoProdutoOfFabricanteProdutoCollectionNewFabricanteProduto != null && !oldCodigoProdutoOfFabricanteProdutoCollectionNewFabricanteProduto.equals(produto)) {
                        oldCodigoProdutoOfFabricanteProdutoCollectionNewFabricanteProduto.getFabricanteProdutoCollection().remove(fabricanteProdutoCollectionNewFabricanteProduto);
                        oldCodigoProdutoOfFabricanteProdutoCollectionNewFabricanteProduto = em.merge(oldCodigoProdutoOfFabricanteProdutoCollectionNewFabricanteProduto);
                    }
                }
            }
            for (ComponenteProduto componenteProdutoCollectionOldComponenteProduto : componenteProdutoCollectionOld) {
                if (!componenteProdutoCollectionNew.contains(componenteProdutoCollectionOldComponenteProduto)) {
                    componenteProdutoCollectionOldComponenteProduto.setCodigoProdutoFilho(null);
                    componenteProdutoCollectionOldComponenteProduto = em.merge(componenteProdutoCollectionOldComponenteProduto);
                }
            }
            for (ComponenteProduto componenteProdutoCollectionNewComponenteProduto : componenteProdutoCollectionNew) {
                if (!componenteProdutoCollectionOld.contains(componenteProdutoCollectionNewComponenteProduto)) {
                    Produto oldCodigoProdutoFilhoOfComponenteProdutoCollectionNewComponenteProduto = componenteProdutoCollectionNewComponenteProduto.getCodigoProdutoFilho();
                    componenteProdutoCollectionNewComponenteProduto.setCodigoProdutoFilho(produto);
                    componenteProdutoCollectionNewComponenteProduto = em.merge(componenteProdutoCollectionNewComponenteProduto);
                    if (oldCodigoProdutoFilhoOfComponenteProdutoCollectionNewComponenteProduto != null && !oldCodigoProdutoFilhoOfComponenteProdutoCollectionNewComponenteProduto.equals(produto)) {
                        oldCodigoProdutoFilhoOfComponenteProdutoCollectionNewComponenteProduto.getComponenteProdutoCollection().remove(componenteProdutoCollectionNewComponenteProduto);
                        oldCodigoProdutoFilhoOfComponenteProdutoCollectionNewComponenteProduto = em.merge(oldCodigoProdutoFilhoOfComponenteProdutoCollectionNewComponenteProduto);
                    }
                }
            }
            for (ComponenteProduto componenteProdutoCollection1OldComponenteProduto : componenteProdutoCollection1Old) {
                if (!componenteProdutoCollection1New.contains(componenteProdutoCollection1OldComponenteProduto)) {
                    componenteProdutoCollection1OldComponenteProduto.setCodigoProdutoPai(null);
                    componenteProdutoCollection1OldComponenteProduto = em.merge(componenteProdutoCollection1OldComponenteProduto);
                }
            }
            for (ComponenteProduto componenteProdutoCollection1NewComponenteProduto : componenteProdutoCollection1New) {
                if (!componenteProdutoCollection1Old.contains(componenteProdutoCollection1NewComponenteProduto)) {
                    Produto oldCodigoProdutoPaiOfComponenteProdutoCollection1NewComponenteProduto = componenteProdutoCollection1NewComponenteProduto.getCodigoProdutoPai();
                    componenteProdutoCollection1NewComponenteProduto.setCodigoProdutoPai(produto);
                    componenteProdutoCollection1NewComponenteProduto = em.merge(componenteProdutoCollection1NewComponenteProduto);
                    if (oldCodigoProdutoPaiOfComponenteProdutoCollection1NewComponenteProduto != null && !oldCodigoProdutoPaiOfComponenteProdutoCollection1NewComponenteProduto.equals(produto)) {
                        oldCodigoProdutoPaiOfComponenteProdutoCollection1NewComponenteProduto.getComponenteProdutoCollection1().remove(componenteProdutoCollection1NewComponenteProduto);
                        oldCodigoProdutoPaiOfComponenteProdutoCollection1NewComponenteProduto = em.merge(oldCodigoProdutoPaiOfComponenteProdutoCollection1NewComponenteProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produto.getCodigoProduto();
                if (findProduto(id) == null) {
                    throw new NonexistentEntityException("The produto with id " + id + " no longer exists.");
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
            Produto produto;
            try {
                produto = em.getReference(Produto.class, id);
                produto.getCodigoProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Aplicacao> aplicacaoCollectionOrphanCheck = produto.getAplicacaoCollection();
            for (Aplicacao aplicacaoCollectionOrphanCheckAplicacao : aplicacaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produto (" + produto + ") cannot be destroyed since the Aplicacao " + aplicacaoCollectionOrphanCheckAplicacao + " in its aplicacaoCollection field has a non-nullable codigoProduto field.");
            }
            Collection<QualificadorProduto> qualificadorProdutoCollectionOrphanCheck = produto.getQualificadorProdutoCollection();
            for (QualificadorProduto qualificadorProdutoCollectionOrphanCheckQualificadorProduto : qualificadorProdutoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produto (" + produto + ") cannot be destroyed since the QualificadorProduto " + qualificadorProdutoCollectionOrphanCheckQualificadorProduto + " in its qualificadorProdutoCollection field has a non-nullable codigoProduto field.");
            }
            Collection<FabricanteProduto> fabricanteProdutoCollectionOrphanCheck = produto.getFabricanteProdutoCollection();
            for (FabricanteProduto fabricanteProdutoCollectionOrphanCheckFabricanteProduto : fabricanteProdutoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produto (" + produto + ") cannot be destroyed since the FabricanteProduto " + fabricanteProdutoCollectionOrphanCheckFabricanteProduto + " in its fabricanteProdutoCollection field has a non-nullable codigoProduto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            StatusProduto codigoStatusProduto = produto.getCodigoStatusProduto();
            if (codigoStatusProduto != null) {
                codigoStatusProduto.getProdutoCollection().remove(produto);
                codigoStatusProduto = em.merge(codigoStatusProduto);
            }
            LinhaProduto codigoLinhaProduto = produto.getCodigoLinhaProduto();
            if (codigoLinhaProduto != null) {
                codigoLinhaProduto.getProdutoCollection().remove(produto);
                codigoLinhaProduto = em.merge(codigoLinhaProduto);
            }
            Collection<ComponenteProduto> componenteProdutoCollection = produto.getComponenteProdutoCollection();
            for (ComponenteProduto componenteProdutoCollectionComponenteProduto : componenteProdutoCollection) {
                componenteProdutoCollectionComponenteProduto.setCodigoProdutoFilho(null);
                componenteProdutoCollectionComponenteProduto = em.merge(componenteProdutoCollectionComponenteProduto);
            }
            Collection<ComponenteProduto> componenteProdutoCollection1 = produto.getComponenteProdutoCollection1();
            for (ComponenteProduto componenteProdutoCollection1ComponenteProduto : componenteProdutoCollection1) {
                componenteProdutoCollection1ComponenteProduto.setCodigoProdutoPai(null);
                componenteProdutoCollection1ComponenteProduto = em.merge(componenteProdutoCollection1ComponenteProduto);
            }
            em.remove(produto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produto> findProdutoEntities() {
        return findProdutoEntities(true, -1, -1);
    }

    public List<Produto> findProdutoEntities(int maxResults, int firstResult) {
        return findProdutoEntities(false, maxResults, firstResult);
    }

    private List<Produto> findProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produto.class));
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

    public Produto findProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produto> rt = cq.from(Produto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
