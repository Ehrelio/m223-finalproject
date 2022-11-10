package ch.zli.eb.m223.finalproject.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.eb.m223.finalproject.model.CwSUser;

@ApplicationScoped
public class UserService {
    @Inject
    EntityManager entityManager;

    @Transactional
    public CwSUser createUser(CwSUser user) {
        if(user.getBirthdate().isBefore(LocalDate.of(2003, 11, 2))){
        user.setAdmin(false);
        return entityManager.merge(user);
        }else{
            return user;
        }
    }

    @Transactional
    public void deleteUser(Long id){
        var entity = entityManager.find(CwSUser.class, id);
        entityManager.remove(entity);
    }

    @Transactional
    public CwSUser updateUser(Long id, CwSUser user){
        user.setId(id);
        return entityManager.merge(user);
    }

    public CwSUser getUser(Long id){
        return entityManager.find(CwSUser.class, id);
    }

    public List<CwSUser> findAll(){
        var query = entityManager.createQuery("FROM CwSUser", CwSUser.class);
        return query.getResultList();
    }
    public Optional<CwSUser> findByEmail(String email) {
        return entityManager
                .createNamedQuery("CwSUser.findByEmail", CwSUser.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }
}
