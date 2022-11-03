package ch.zli.eb.m223.finalproject.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.eb.m223.finalproject.model.Group;

@ApplicationScoped
public class GroupService {
    
    @Inject
    EntityManager entityManager;

    @Transactional
    public Group createGroup(Group group){
        return entityManager.merge(group);
    }

    @Transactional
    public Group updateGroup(Long id,Group group){
        group.setId(id);
        return entityManager.merge(group);
    }

    @Transactional
    public void deleteGroup(Long id){
        var entity = entityManager.find(Group.class, id);
        entityManager.remove(entity);
    }

    public List<Group> findAll(){
        var query = entityManager.createQuery("FROM Group", Group.class);
        return query.getResultList();
    }
}
