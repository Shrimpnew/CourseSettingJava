package org.fatmansoft.teach.repository.support;

import org.fatmansoft.teach.repository.base.CommonRepository;
import org.fatmansoft.teach.util.Pair;
import org.fatmansoft.teach.util.StringUtil;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.Query;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommonRepositoryImpl<T,ID> extends SimpleJpaRepository<T,ID> implements CommonRepository<T,ID> {

    private final Class<T> domainClass;
    private final EntityManager entityManager;
    private final String tableName;

    public CommonRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.domainClass = domainClass;
        this.entityManager = entityManager;
        Table annotation = domainClass.getAnnotation(Table.class);
        tableName = annotation.name();
    }

    @Override
    public Integer getMaxId(String idName) {
        return (Integer) entityManager.createNativeQuery("select max(" + StringUtil.CamelCaseToUnderLine(idName) + ") from " + tableName).getSingleResult();
    }

    private StringBuilder getQueryPrefix(){
        //使用StringBuilder创建查询字符串（使append拼接更高效）
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from ").append(tableName).append(" ");
        //使用反射遍历实体类的字段，获取关联的外表
        for (Field field : domainClass.getDeclaredFields()) {
            field.setAccessible(true);
            //判断该字段是否含有@JoinColumn注解，即是否为关联键
            if(field.isAnnotationPresent(JoinColumn.class)){
                //获取关联字段名
                String columnName = StringUtil.CamelCaseToUnderLine(field.getAnnotation(JoinColumn.class).name());
                //判断关联类是否对应一张数据库表
                if(field.getType().isAnnotationPresent(Table.class)){
                    String joinTableName = field.getType().getAnnotation(Table.class).name();
                    //拼接查询字符串，联查数据库表
                    stringBuilder.append("left outer join ").append(joinTableName).append(" on ").append(tableName).append(".").append(columnName).append("=").append(joinTableName).append(".").append(columnName).append(" ");
                }
            }
        }
        stringBuilder.append("where ");
        return stringBuilder;
    }

    private void appendQueryBody(String fieldName,Object keyword,String prefix,String suffix,StringBuilder stringBuilder){
        if(fieldName != null && keyword != null){
            if(fieldName.endsWith("Id")){
                try {
                    domainClass.getDeclaredField(fieldName.substring(0,fieldName.length()-2));
                    stringBuilder.append(tableName).append(".");
                } catch (NoSuchFieldException ignored) {
                }
            }
            else{
                try {
                    domainClass.getDeclaredField(fieldName);
                    stringBuilder.append(tableName).append(".");
                } catch (NoSuchFieldException ignored) {
                }
            }
            stringBuilder.append(StringUtil.CamelCaseToUnderLine(fieldName)).append(prefix).append(keyword).append(suffix);
        }
    }

    private void appendQueryBody(List<String> fieldNameList,Object keyword,String prefix,String suffix,StringBuilder stringBuilder){
        for (String fieldName : fieldNameList) {
            appendQueryBody(fieldName,keyword,prefix,suffix,stringBuilder);
        }
    }

    private void appendQueryBody(String fieldName,List<Object> keywordList,String prefix,String suffix,StringBuilder stringBuilder){
        for (Object keyword : keywordList) {
            appendQueryBody(fieldName,keyword,prefix,suffix,stringBuilder);
        }
    }

    private void appendQueryBody(List<Pair<String,Object>> fieldKeyList,String prefix,String suffix,StringBuilder stringBuilder){
        for (Pair<String, Object> fieldKey : fieldKeyList) {
            appendQueryBody(fieldKey.getFirst(),fieldKey.getSecond(),prefix,suffix,stringBuilder);
        }
    }

    private Query createQuery(StringBuilder stringBuilder){
        String queryString = stringBuilder.toString();
        if(queryString.endsWith(" and ")){
            queryString = queryString.substring(0,queryString.length() - 5);
        }
        if(queryString.endsWith(" or ")){
            queryString = queryString.substring(0,queryString.length() - 4);
        }
        if(queryString.endsWith(" where ")){
            queryString = queryString.substring(0,queryString.length() - 7);
        }
        System.out.println("sql query:" + queryString);
        return entityManager.createNativeQuery(queryString,domainClass);
    }

    @Override
    public List<T> findListByKeyword(String fieldName,Object keyword) {
        try {
            StringBuilder stringBuilder = getQueryPrefix();
            appendQueryBody(fieldName,keyword,"='","' and ",stringBuilder);
            return (List<T>) createQuery(stringBuilder).getResultList();
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public T findEntityByKeyword(String fieldName, Object keyword) {
        try {
            StringBuilder stringBuilder = getQueryPrefix();
            appendQueryBody(fieldName,keyword,"='","' and ",stringBuilder);
            return (T) createQuery(stringBuilder).getSingleResult();
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public List<T> findListByKeywords(List<Pair<String, Object> > fieldKeyList) {
        try {
            StringBuilder stringBuilder = getQueryPrefix();
            appendQueryBody(fieldKeyList,"='","' and ",stringBuilder);
            return (List<T>) createQuery(stringBuilder).getResultList();
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<T> findListLikeKeyword(Object keyword, List<String> fieldNameList) {
        try {
            StringBuilder stringBuilder = getQueryPrefix();
            appendQueryBody(fieldNameList,keyword," like '%","%' or ",stringBuilder);
            return (List<T>) createQuery(stringBuilder).getResultList();
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<T> findListLikeKeywords(List<Pair<String, Object> > fieldKeyList) {
        try {
            StringBuilder stringBuilder = getQueryPrefix();
            appendQueryBody(fieldKeyList," like '%","%' or ",stringBuilder);
            return (List<T>) createQuery(stringBuilder).getResultList();
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<?> findListNotKeyword(String fieldName, Object keyword) {
        try {
            StringBuilder stringBuilder = getQueryPrefix();
            appendQueryBody(fieldName,keyword,"!='","' and ",stringBuilder);
            return (List<T>) createQuery(stringBuilder).getResultList();
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<?> findListNotKeywords(String fieldName, List<Object> keywordList) {
        try {
            StringBuilder stringBuilder = getQueryPrefix();
            appendQueryBody(fieldName,keywordList,"!='","' and ",stringBuilder);
            return (List<T>) createQuery(stringBuilder).getResultList();
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<?> findListNotKeywords(List<Pair<String, Object>> fieldKeyList) {
        try {
            StringBuilder stringBuilder = getQueryPrefix();
            appendQueryBody(fieldKeyList,"!='","' and ",stringBuilder);
            return (List<T>) createQuery(stringBuilder).getResultList();
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<?> findListByLikeKeyword(String fieldName, Object keyword, List<String> fieldList, Object keyword2) {
        try {
            StringBuilder stringBuilder = getQueryPrefix();
            appendQueryBody(fieldName,keyword,"='","' and ",stringBuilder);
            StringBuilder queryBody = new StringBuilder();
            appendQueryBody(fieldList,keyword2," like '%","%' or ",queryBody);
            String str = queryBody.toString();
            if(str.endsWith(" or ")){
                str = str.substring(0,str.length() - 4);
                stringBuilder.append("(").append(str).append(")");
            }
            return (List<T>) createQuery(stringBuilder).getResultList();
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<?> findListByNotKeyword(String fieldName, Object keyword,String fieldName2,List<Object> keywordList) {
        try {
            StringBuilder stringBuilder = getQueryPrefix();
            appendQueryBody(fieldName,keyword,"='","' and ",stringBuilder);
            appendQueryBody(fieldName2,keywordList,"!='","' and ",stringBuilder);
            return (List<T>) createQuery(stringBuilder).getResultList();
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteByKeyword(String fieldName, Object keyword) {
        if(fieldName == null || keyword == null)return;
        StringBuilder stringBuilder = new StringBuilder("delete from ").append(tableName).append(" where ").append(StringUtil.CamelCaseToUnderLine(fieldName)).append("='").append(keyword).append("'");
        System.out.println("sql query : " + stringBuilder);
        entityManager.createNativeQuery(stringBuilder.toString(), domainClass).executeUpdate();
    }
}
