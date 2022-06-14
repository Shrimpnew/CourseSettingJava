package org.fatmansoft.teach.repository.base;

import org.fatmansoft.teach.models.base.EUserType;
import org.fatmansoft.teach.models.base.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findByName(EUserType name);
}