/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bookstore.business.bll.catalogmngmt;


import com.bookstore.business.persistence.catalog.Category;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import java.util.List;

/**
 * service local de gestion des catégories exposé via une vue sans interface <br>
 * mêmes remarques que pour BookManager<br>
 * Service local ayant une granularité fine.
 * Les méthodes s'exécutent au sein d'une tsx active propagée / créée "par" le client appelant.
 * Les méthodes doivent obligatoirement s'exécuter dans une tsx active cliente.
 */
@Stateless(name="CategoryManager")//nom du stateless 
//toutes les méthodes doivent s'éxecuter dans une transaction parente
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@LocalBean
public class CategoryManagerServiceBean {

    @PersistenceContext(unitName = "bsPU")
    private EntityManager em;
    
    /**
     * sauvegarder une catégorie dans la base
     * @param category la catégorie nouvellement créée à persister dans la base
     * @return identité de la catégorie persistée
     */
    public Long saveCategory(Category category) {
        em.persist(category);
        em.flush();
        em.refresh(category);
     return category.getId();
    }
   
    /**
     * trouver une catégorie en fonction de son identité / sa clé primaire.
     * Comportement transactionnel redéfini (SUPPORTS) :Méthode pouvant s'exécuter dans le contexte transactionnel de l'appelant.
     * @param categoryId identité de la catégorie recherchée
     * @return la catégorie trouvée en base
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)//comportement transactionnel redéfini
    public Category findCategoryById(Long categoryId){
        if(categoryId == null){
            throw new IllegalArgumentException("id de catégorie null");
        }
        return em.find(Category.class, categoryId);
    }

    /**
     *
     * Lister les catégories racines
     * @return la liste de catégories n'ayant pas de parents.
     */
    public List<Category> getRootCategories() {
        String jpql = "SELECT c from Category c where c.parentCategory is null";

        List result;
        try {
            Query query = em.createQuery(jpql);
            result = query.getResultList();
            return result;
        } catch (IllegalArgumentException e) {
            throw new AssertionError("requête [" + jpql + "] est mal formée");
        }
    }

   /**
    *
    * Lister des catégories enfants
    * @param parentId identifiant de la catégorie pour laquelle on recherche les catégories filles
    * @return la liste des catégorie filles d'une catégorie
    * retourne null si aucune catégorie enfant n'est  retrouvée en fonction de parentId
    */
    public List<Category> getchildrenCategories(Long parentId) {

        String jpql = "SELECT c from Category c where c.parentCategory = :category";
        try {
            Category cat = em.find(Category.class, parentId);
            Query query = em.createQuery(jpql);
            query.setParameter("category", cat);
            return query.getResultList();
        } catch (IllegalArgumentException e) {
            throw new AssertionError("requête [" + jpql + "] est mal formée");
        }
    }

}
