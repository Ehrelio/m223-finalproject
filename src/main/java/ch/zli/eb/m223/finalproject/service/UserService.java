package ch.zli.eb.m223.finalproject.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.eb.m223.finalproject.model.User;

@ApplicationScoped
public class UserService {
    @Inject
    EntityManager entityManager;

    @Transactional
    public User createUser(User user) {
        return entityManager.merge(user);
    }

    @Transactional
    public void deleteUser(Long id){
        var entity = entityManager.find(User.class, id);
        entityManager.remove(entity);
    }

    @Transactional
    public User updateUser(Long id, User user){
        user.setId(id);
        return entityManager.merge(user);
    }

    public List<User> findAll(){
        var query = entityManager.createQuery("FROM User", User.class);
        return query.getResultList();
    }
}
