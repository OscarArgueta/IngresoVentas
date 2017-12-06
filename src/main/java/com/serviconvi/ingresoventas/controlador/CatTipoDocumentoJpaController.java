/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviconvi.ingresoventas.controlador;

import com.serviconvi.ingresoventas.controlador.exceptions.NonexistentEntityException;
import com.serviconvi.scentidades.CatTipoDocumento;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author oscar
 */
public class CatTipoDocumentoJpaController implements Serializable {

    public CatTipoDocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CatTipoDocumento catTipoDocumento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(catTipoDocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CatTipoDocumento catTipoDocumento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            catTipoDocumento = em.merge(catTipoDocumento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = catTipoDocumento.getCodigo();
                if (findCatTipoDocumento(id) == null) {
                    throw new NonexistentEntityException("The catTipoDocumento with id " + id + " no longer exists.");
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
            CatTipoDocumento catTipoDocumento;
            try {
                catTipoDocumento = em.getReference(CatTipoDocumento.class, id);
                catTipoDocumento.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catTipoDocumento with id " + id + " no longer exists.", enfe);
            }
            em.remove(catTipoDocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CatTipoDocumento> findCatTipoDocumentoEntities() {
        return findCatTipoDocumentoEntities(true, -1, -1);
    }

    public List<CatTipoDocumento> findCatTipoDocumentoEntities(int maxResults, int firstResult) {
        return findCatTipoDocumentoEntities(false, maxResults, firstResult);
    }

    private List<CatTipoDocumento> findCatTipoDocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CatTipoDocumento.class));
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

    public CatTipoDocumento findCatTipoDocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CatTipoDocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatTipoDocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CatTipoDocumento> rt = cq.from(CatTipoDocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
