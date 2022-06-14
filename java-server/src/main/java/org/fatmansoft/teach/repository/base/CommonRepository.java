package org.fatmansoft.teach.repository.base;

import org.fatmansoft.teach.util.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommonRepository<T,ID> extends JpaRepository<T,ID> {
    Integer getMaxId(String idName);

    List<T> findListByKeyword(String fieldName,Object keyword);

    T findEntityByKeyword(String fieldName, Object keyword);

    List<T> findListByKeywords(List<Pair<String,Object>> fieldKeyList);

    List<T> findListLikeKeyword(Object keyword, List<String> fieldName);

    List<T> findListLikeKeywords(List<Pair<String,Object>> fieldKeyList);

    List<?> findListNotKeyword(String fieldName, Object keyword);

    List<?> findListNotKeywords(String fieldName,List<Object> keywordList);

    List<?> findListNotKeywords(List<Pair<String,Object>> fieldKeyList);

    List<?> findListByLikeKeyword(String fieldName,Object keyword,List<String> fieldList,Object keyword2);

    List<?> findListByNotKeyword(String fieldName,Object keyword,String fieldName2,List<Object> keywordList);

    void deleteByKeyword(String fieldName, Object keyword);
}
