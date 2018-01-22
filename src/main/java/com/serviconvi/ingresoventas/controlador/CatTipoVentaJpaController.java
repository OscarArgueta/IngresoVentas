/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviconvi.ingresoventas.controlador;

import com.serviconvi.ingresoventas.controlador.exceptions.NonexistentEntityException;
import com.serviconvi.scentidades.CatTipoVenta;
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
public class CatTipoVentaJpaController implements Serializable {

    public CatTipoVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CatTipoVenta catTipoVenta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(catTipoVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CatTipoVenta catTipoVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            catTipoVenta = em.merge(catTipoVenta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = catTipoVenta.getCodigo();
                if (findCatTipoVenta(id) == null) {
                    throw new NonexistentEntityException("The catTipoVenta with id " + id + " no longer exists.");
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
            CatTipoVenta catTipoVenta;
            try {
                catTipoVenta = em.getReference(CatTipoVenta.class, id);
                catTipoVenta.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catTipoVenta with id " + id + " no longer exists.", enfe);
            }
            em.remove(catTipoVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CatTipoVenta> findCatTipoVentaEntities() {
        return findCatTipoVentaEntities(true, -1, -1);
    }

    public List<CatTipoVenta> findCatTipoVentaEntities(int maxResults, int firstResult) {
        return findCatTipoVentaEntities(false, maxResults, firstResult);
    }

    private List<CatTipoVenta> findCatTipoVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CatTipoVenta.class));
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

    public CatTipoVenta findCatTipoVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CatTipoVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatTipoVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CatTipoVenta> rt = cq.from(CatTipoVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
