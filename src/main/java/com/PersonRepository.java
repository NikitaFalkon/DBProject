package com;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    /*@Query("from Person p where p.email = ?")
    Person queryByEmail(String email);*/
    //List<Person> findByNameLike(String email);
    //Person findByNameLike(String email);
    Person findByEmail(String email);

    boolean existsByEmail(String email);
}
