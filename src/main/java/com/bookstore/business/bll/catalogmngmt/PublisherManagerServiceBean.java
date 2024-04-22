/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bookstore.business.bll.catalogmngmt;

import com.bookstore.business.persistence.catalog.Publisher;
import jakarta.ejb.*;

import jakarta.inject.Inject;
import jakarta.persistence.*;

/**
 *
 * service local de gestion des éditeurs (vue sans interface)
 */
@Stateless(name="PublisherManager")//nom EJB du session bean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@LocalBean
public class PublisherManagerServiceBean {

    @PersistenceContext(unitName = "bsPU")
    private EntityManager em;

    /**
     * 
     * Sauvegarder un éditeur
     * @param publisher éditeur nouvellement créé
     * @return éditeur managé par le contexte de persistance
     */
    public Publisher savePublisher(Publisher publisher) {
        em.persist(publisher);
        return publisher;
//        return null;
    }

    /**
     * 
     * Trouver un éditeur en fonction de son id
     * @param publisherId identité de l'éditeur à retrouver en base
     * @return l'éditeur recherché
     */
    public Publisher findPublisherById(Long publisherId) {
        return em.find(Publisher.class, publisherId);
    }
  
}
