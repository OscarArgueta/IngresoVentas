/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviconvi.ingresoventas.controlador;

import com.serviconvi.ingresoventas.controlador.exceptions.NonexistentEntityException;
import com.serviconvi.ingresoventas.controlador.exceptions.PreexistingEntityException;
import com.serviconvi.scentidades.CatClienteVenta;
import com.serviconvi.scentidades.CatClienteVentaPK;
import com.serviconvi.util.MyLogger;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author oscar
 */
public class CatClienteVentaJpaController implements Serializable {

    static Logger log = MyLogger.getLogger(CatClienteVentaJpaController.class);
    
    public CatClienteVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CatClienteVenta catClienteVenta) throws PreexistingEntityException, Exception {
        if (catClienteVenta.getCatClienteVentaPK() == null) {
            catClienteVenta.setCatClienteVentaPK(new CatClienteVentaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(catClienteVenta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCatClienteVenta(catClienteVenta.getCatClienteVentaPK()) != null) {
                throw new PreexistingEntityException("CatClienteVenta " + catClienteVenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CatClienteVenta catClienteVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            catClienteVenta = em.merge(catClienteVenta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CatClienteVentaPK id = catClienteVenta.getCatClienteVentaPK();
                if (findCatClienteVenta(id) == null) {
                    throw new NonexistentEntityException("The catClienteVenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CatClienteVentaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CatClienteVenta catClienteVenta;
            try {
                catClienteVenta = em.getReference(CatClienteVenta.class, id);
                catClienteVenta.getCatClienteVentaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catClienteVenta with id " + id + " no longer exists.", enfe);
            }
            em.remove(catClienteVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CatClienteVenta> findCatClienteVentaEntities() {
        return findCatClienteVentaEntities(true, -1, -1);
    }

    public List<CatClienteVenta> findCatClienteVentaEntities(int maxResults, int firstResult) {
        return findCatClienteVentaEntities(false, maxResults, firstResult);
    }

    private List<CatClienteVenta> findCatClienteVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CatClienteVenta.class));
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

    public CatClienteVenta findCatClienteVenta(Integer codigoCliente) {
        EntityManager em = getEntityManager();
        MyLogger.debug("codigoCliente", codigoCliente);
        try {
            Query qryClienteVenta = em.createNamedQuery("CatClienteVenta.findByCodCliente");
            qryClienteVenta.setParameter("codCliente", codigoCliente);
            return (CatClienteVenta)qryClienteVenta.getSingleResult();
        }catch(NoResultException e){
            return null;
        }catch (Exception e) {
            log.error(("Ocurrio un problema en \"CatClienteVenta.findByCodCliente\".  "+ e.getMessage()));
            return null;
        }
    }
    
    public CatClienteVenta findCatClienteVenta(CatClienteVentaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CatClienteVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatClienteVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CatClienteVenta> rt = cq.from(CatClienteVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
