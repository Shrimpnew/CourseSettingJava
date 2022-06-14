package org.fatmansoft.teach.repository.information;

import org.fatmansoft.teach.models.information.StudentInf;
import org.fatmansoft.teach.repository.base.CommonRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentInfRepository extends CommonRepository<StudentInf,Integer> {
}
