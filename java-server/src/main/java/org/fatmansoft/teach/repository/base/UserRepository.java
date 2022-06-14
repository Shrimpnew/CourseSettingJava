package org.fatmansoft.teach.repository.base;


import java.util.Optional;

import org.fatmansoft.teach.models.base.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CommonRepository<User,Integer> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByUserId(Integer userId);

    Boolean existsByUserName(String userName);
}