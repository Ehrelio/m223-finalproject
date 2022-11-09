package ch.zli.eb.m223.finalproject.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.swing.GroupLayout.Group;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ch.zli.eb.m223.finalproject.model.CwSGroup;
import ch.zli.eb.m223.finalproject.model.CwSUser;
import io.quarkus.security.User;

@ApplicationScoped
public class GroupService {
    
    @Inject
    EntityManager entityManager;

    @Transactional
    public CwSGroup find(Long id){
        return entityManager.find(CwSGroup.class, id);
    }

    @Transactional
    public CwSGroup createGroup(CwSGroup group){
        return entityManager.merge(group);
    }

    @Transactional
    public CwSGroup updateGroup(Long id,CwSGroup group){
        group.setId(id);
        return entityManager.merge(group);
    }

    @Transactional
    public void deleteGroup(Long id){
        CwSGroup entity = entityManager.find(CwSGroup.class, id);
        for(CwSUser cwSUser : entity.getMembers()){
            entityManager.remove(cwSUser);
        }
        entityManager.remove(entity);
    }

    public List<CwSGroup> findAll(){
        var query = entityManager.createQuery("FROM Group", CwSGroup.class);
        return query.getResultList();
    }
}
