package org.fatmansoft.teach.service.base;

import lombok.Data;
import org.fatmansoft.teach.repository.base.CommonRepository;
import org.fatmansoft.teach.util.*;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Data
public class CommonService<R extends CommonRepository >{

    protected static final List<Node> nodes = new ArrayList<>();
    protected static final Map<Class<?>,Integer> entityToId = new HashMap<>();
    protected static final Map<String,Integer> idNameToId = new HashMap<>();
    protected static final Map<String,Integer> classNameToId = new HashMap<>();
    protected static final Map<Pair<Integer,Integer>,String> edgeToField = new HashMap<>();


    /**
     * @Description: 节点信息，一个节点对应一张表
     * @Date: 2022-04-04 21:42
     **/
    @Data
    protected static class Node{
        protected Integer id;
        protected String className;
        protected String idName;
        protected Class<?> entityClass;
        protected CommonRepository<?,?> repository;
        protected List<Integer> dependence = new ArrayList<>();
        protected List<Integer> derivative = new ArrayList<>();

        public Node(Integer id, String className, String idName, Class<?> entityClass, CommonRepository<?,?> repository) {
            this.id = id;
            this.className = className;
            this.idName = idName;
            this.entityClass = entityClass;
            this.repository = repository;
        }
    }

    /**
     * @Description: 扫描实体类并预处理、建图
     * @Date: 2022-04-04 21:40

     * @return: void
     **/
    public static void setup(){
        Set<Class<?>> classSet = ClassUtil.scanPackage("org.fatmansoft.teach.models");

        //第一次遍历实体类，查询主键、类信息
        for(Class<?> entityClass:classSet){
            if(!entityClass.isAnnotationPresent(Table.class))continue;
            String[] split = entityClass.getName().split("\\.");
            String className = split[split.length - 1];
            Object repositoryBean = SpringUtil.getBean(StringUtil.toLowerHead(className) + "Repository");
            if(!(repositoryBean instanceof CommonRepository))continue;
            CommonRepository<?,?> repository = (CommonRepository<?,?>) repositoryBean;
            for (Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);
                if(field.getAnnotation(Id.class) != null){
                    String idName = field.getName();
                    Integer id = nodes.size();
                    idNameToId.put(idName,id);
                    classNameToId.put(className,id);
                    //System.out.println("idName = " + idName + " nodeId = " + id);
                    nodes.add(new Node(id,className,idName,entityClass,repository));
                    entityToId.put(entityClass,id);
                    break;
                }
            }
        }

        //第二次遍历实体类，进行建图
        for(Class<?> entityClass:classSet){
            Integer id = entityToId.get(entityClass);
            if(id != null){
                for (Field field : entityClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    if(entityToId.containsKey(field.getType())){
                        Integer fId = entityToId.get(field.getType());
                        nodes.get(id).dependence.add(fId);
                        nodes.get(fId).derivative.add(id);
                        String fieldName;
                        if(field.isAnnotationPresent(JoinColumn.class)){
                            fieldName = field.getAnnotation(JoinColumn.class).name();
                        }
                        else{
                            fieldName = nodes.get(fId).idName;
                        }
                        edgeToField.put(new Pair<>(id,fId),fieldName);
                        edgeToField.put(new Pair<>(fId,id),fieldName);
                    }
                }
            }
        }
    }


    protected final Class<?> eClass;
    protected final Class<R> rClass;
    protected final String idName;
    protected final String className;

    public CommonService(){
        rClass = ClassUtil.getGenericClass(getClass(),0);
        eClass = ClassUtil.getGenericInterface(rClass,0);
        idName = findIdName();
        className = findClassName();
    }

    /**
    * @Description: 获取小写开头的类名前缀
    * @Date: 2022-04-03 18:49

    * @return: java.lang.String
    **/
    protected String findClassName(){
        String[] split = eClass.getName().split("\\.");
        return split[split.length-1];
    }

    /**
    * @Description: 获取Id字段名
    * @Date: 2022-04-03 18:50

    * @return: java.lang.String
    **/
    protected String findIdName(){
        for(Field field : eClass.getDeclaredFields()){
            field.setAccessible(true);
            if(field.getAnnotation(Id.class) != null)return field.getName();
        }
        return null;
    }


    /**
    * @Description: 创建一个map并放入key-value
    * @Date: 2022-04-05 20:51
    * @Param key:
    * @Param value:
    * @return: java.util.Map
    **/
    protected Map<Object,Object> newMap(Object key,Object value){
        Map<Object,Object> map = new HashMap<>();
        map.put(key,value);
        return map;
    }


    /**
    * @Description: 把字段内容转换格式后放入map
    * @Date: 2022-04-13 15:52
    * @Param field:
    * @Param entity:
    * @Param map:
    * @return: void
    **/
    protected void putFieldToMap(Field field,Object entity,Map map){
        if(field == null || entity == null)return;
        try {
//            if(field.getType() == Date.class){
//                map.put(field.getName(),DateTimeTool.parseDateTime((Date)field.get(entity),"yyyy-MM-dd"));
//            }
//            else{
                map.put(field.getName(),field.get(entity));
//            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 获取单个对象自身表及依赖表信息返回map
     * @Date: 2022-04-05 12:39
     * @Param id:
     * @Param entity:
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    protected Map<String,Object> getFullTableMap(Integer id, Object entity){
        Map<String,Object> map = new HashMap<>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(entityToId.containsKey(field.getType())){
                for (Field dependField : field.getType().getDeclaredFields()) {
                    dependField.setAccessible(true);
                    try {
                        putFieldToMap(dependField,field.get(entity),map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                putFieldToMap(field,entity,map);
            }
        }
        return map;
    }


    /**
    * @Description: 获取某个表的所有实体表map放入list返回
    * @Date: 2022-04-07 21:04
    * @Param id:
    * @return: java.util.List
    **/
    protected List getListByAll(Integer id){
        List list = new ArrayList();
        Node table = nodes.get(id);
        if(table == null) return list;
        for (Object entity : table.repository.findAll()) {
            list.add(getFullTableMap(id,entity));
        }
        return list;
    }

    /**
     * @Description: 获取对象List自身表及依赖表信息返回List
     * @Date: 2022-04-05 12:49
     * @Param id:
     * @Param entityList:
     * @return: java.util.List<?>
     **/
    protected List<Map> getFullTableList(int id, List<?> entityList){
        List<Map> datalist = new ArrayList<>();
        for (Object entity : entityList) {
            datalist.add(getFullTableMap(id,entity));
        }
        return datalist;
    }

    /**
    * @Description: 通过主键keyword获取完全信息
    * @Date: 2022-04-05 14:50
    * @Param keyword:
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    **/
    public Map<String,Object> getMapById(Object keyword){
        if(keyword == null)return new HashMap<>();
        Integer id = entityToId.get(eClass);
        return getFullTableMap(id,nodes.get(id).repository.findEntityByKeyword(idName,keyword));
    }



    /**
    * @Description: 返回一个带依赖表全实体表信息的Pair
    * @Date: 2022-04-07 21:08
    * @Param keyword:
    * @return: org.fatmansoft.teach.util.Pair
    **/
    public Pair getMapWithSelectOption(Object keyword){
        Map map = new HashMap();
        Node table = nodes.get(entityToId.get(eClass));
        for (Integer dependId : table.dependence) {
            map.put(nodes.get(dependId).className,getListByAll(dependId));
        }
        return new Pair(getMapById(keyword),map);
    }


    /**
    * @Description: 通过主键keyword查询完全信息，关键字为空时select all
    * @Date: 2022-04-05 14:46
    * @Param fieldName:
    * @Param keyword:
    * @return: java.util.List<?>
    **/
    public List<Map> getListByKeyword(String fieldName,Object keyword){
        Integer id = entityToId.get(eClass);
        return getFullTableList(id,nodes.get(id).repository.findListByKeyword(fieldName,keyword));
    }


    /**
    * @Description: 通过主键keyword查询完全信息，必须确定结果唯一，通常使用主键进行唯一查询
    * @Date: 2022-04-05 21:31
    * @Param fieldName:
    * @Param keyword:
    * @return: java.util.List<?>
    **/
    public List<?> getEntityByKeyword(String fieldName, Object keyword){
        Integer id = entityToId.get(eClass);
        List datalist = new ArrayList<>();
        datalist.add(nodes.get(id).repository.findEntityByKeyword(fieldName,keyword));
        return getFullTableList(id,datalist);
    }

    /**
    * @Description: 通过主键keyword查询完全信息，关键字为空时select all
    * @Date: 2022-04-05 21:16
    * @Param fieldKeyList:
    * @return: java.util.List<?>
    **/
    public List<?> getListByKeywords(List<Pair<String,Object>> fieldKeyList){
        Integer id = entityToId.get(eClass);
        return getFullTableList(id,nodes.get(id).repository.findListByKeywords(fieldKeyList));
    }

    /**
    * @Description: 通过给定字段名和关键字模糊搜索
    * @Date: 2022-04-05 21:21
    * @Param keyword:
    * @Param fieldName:
    * @return: java.util.List<?>
    **/
    public List<?> getListLikeKeyword(Object keyword, List<String> fieldName){
        Integer id = entityToId.get(eClass);
        return getFullTableList(id,nodes.get(id).repository.findListLikeKeyword(keyword,fieldName));
    }


    /**
    * @Description: 通过给定字段名和关键字模糊搜索
    * @Date: 2022-04-05 21:22
    * @Param fieldKeyList:
    * @return: java.util.List<?>
    **/
    public List<?> getListLikeKeywords(List<Pair<String,Object>> fieldKeyList){
        Integer id = entityToId.get(eClass);
        return getFullTableList(id,nodes.get(id).repository.findListLikeKeywords(fieldKeyList));
    }


    /**
    * @Description: 通过列名和keyword进行不等于查询
    * @Date: 2022-04-05 20:37
    * @Param fieldName:
    * @Param keyword:
    * @return: java.util.List<?>
    **/
    public List<?> getListNotKeyword(String fieldName,Object keyword){
        Integer id = entityToId.get(eClass);
        return getFullTableList(id,nodes.get(id).repository.findListNotKeyword(fieldName,keyword));
    }


    /**
    * @Description: 通过列名和keyword进行不等于查询
    * @Date: 2022-04-05 21:25
    * @Param fieldName:
    * @Param keywordList:
    * @return: java.util.List<?>
    **/
    public List<?> getListNotKeywords(String fieldName,List<Object> keywordList){
        Integer id = entityToId.get(eClass);
        return getFullTableList(id,nodes.get(id).repository.findListNotKeywords(fieldName,keywordList));
    }

    /**
    * @Description: 通过列名和keyword进行不等于查询
    * @Date: 2022-04-05 21:12
    * @Param fieldKeyList:
    * @return: java.util.List<?>
    **/
    public List<?> getListNotKeywords(List<Pair<String,Object>> fieldKeyList){
        Integer id = entityToId.get(eClass);
        return getFullTableList(id,nodes.get(id).repository.findListNotKeywords(fieldKeyList));
    }


    /**
    * @Description: 通过字段名和关键字删除记录
    * @Date: 2022-04-05 21:25
    * @Param fieldName:
    * @Param keyword:
    * @return: void
    **/
    void deleteByKeyword(String fieldName, Object keyword){
        Integer id = entityToId.get(eClass);
        nodes.get(id).repository.deleteByKeyword(fieldName,keyword);
    }


    /**
     * @Description: 通过给定字段名和关键字模糊查询某表及其依赖表
     * @Date: 2022-04-05 1:02
     * @Param entityClass:
     * @Param fieldNameList:
     * @Param keyword:
     * @return: java.util.List
     **/
    public List<?> getListLikeKeyword(List<String> fieldNameList,Object keyword){
        Node table = nodes.get(entityToId.get(eClass));
        return getFullTableList(table.id,table.repository.findListLikeKeyword(keyword,fieldNameList));
    }

    /**
    * @Description: 通过指定字段和模糊字段获取查询列表
    * @Date: 2022-04-28 16:03
    * @Param fieldName:
    * @Param keyword:
    * @Param queryFieldList:
    * @Param queryKeyword:
    * @return: java.util.List<?>
    **/
    public List<?> getQueryList(String fieldName,Object keyword,List<String>queryFieldList,Object queryKeyword){
        Node table = nodes.get(entityToId.get(eClass));
        return getFullTableList(table.id,table.repository.findListByLikeKeyword(fieldName,keyword,queryFieldList,queryKeyword));
    }

//
//
//    /**
//    * @Description: 获得依赖表的所有记录，忽略ignoreClass，返回map
//    * @Date: 2022-04-05 19:20
//    * @Param ignoreClasses:
//    * @return: java.util.Map
//    **/
//    public Map<String,List<?>> getDependMap(Class<?>... ignoreClasses){
//        Map<String,List<?>> map = new HashMap<>();
//        Set<Class<?>> ignore = new HashSet<>();
//        Collections.addAll(ignore, ignoreClasses);
//
//        Node table = nodes.get(entityToId.get(eClass));
//        for (Integer dependId : table.dependence) {
//            Node dependTable = nodes.get(dependId);
//            if(!ignore.contains(dependTable.entityClass)){
//                map.put(dependTable.className,getFullTableList(dependId,dependTable.repository.findAll()));
//            }
//        }
//        return map;
//    }
//
//
//    /**
//    * @Description: 查询本表信息，并附带依赖表中没有引用的信息
//    * @Date: 2022-04-05 20:56
//    * @Param fieldName: 查询的字段名
//    * @Param keyword:
//    * @Param dependClass: 本表的一个依赖表class
//    * @return: java.util.List<?>
//    **/
//    public List<?> getDependList(String fieldName,Object keyword,Class<?> dependClass){
//        List<Pair<?,?>> datalist = new ArrayList<>();
//        Integer id = entityToId.get(eClass);
//        Integer dependId = entityToId.get(dependClass);
//        Node table = nodes.get(id);
//        Node dependTable = nodes.get(dependId);
//        if(dependTable == null)return datalist;
//        String dependFieldName = edgeToField.get(new Pair<>(dependId,id));
//        List<?> entityList = table.repository.findListByKeyword(fieldName,keyword);
//        datalist.add(new Pair<>(newMap("className",table.className),entityList));
//        try {
//            Field field = table.entityClass.getDeclaredField(dependFieldName);
//            field.setAccessible(true);
//            List<Object> keywordList = new ArrayList<>();
//            for (Object entity : entityList) keywordList.add(field.get(entity));
//            datalist.add(new Pair<>(newMap("className",dependTable.className),dependTable.repository.findListNotKeywords(dependFieldName,keywordList)));
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return datalist;
//    }

    /**
    * @Description: 将form信息存储到指定nodeId的表中
    * @Date: 2022-04-05 15:35
    * @Param id:
    * @Param form:
    * @return: java.lang.Integer
    **/
    public Integer save(Integer id,Map<String,Object> form){
        Node table = nodes.get(id);
        String idName  = table.idName;
        Class<?> entityClass = table.entityClass;
        CommonRepository repository = table.repository;

        Object entity = null;
        if(form.containsKey(idName))entity = repository.findEntityByKeyword(idName, form.get(idName));
        Integer maxId;
        if(entity != null){
            try {
                Method getId = entityClass.getMethod("get" + StringUtil.toUpperHead(idName));
                maxId = (Integer) getId.invoke(entity);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return -1;
            }
        }
        else{
            try {
                entity = entityClass.getDeclaredConstructor().newInstance();
                maxId = repository.getMaxId(idName);
                maxId = maxId == null ? 1 : maxId + 1;
                Method setId = entityClass.getMethod("set" + StringUtil.toUpperHead(idName), Integer.class);
                setId.invoke(entity,maxId);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                return -1;
            }
        }
        for (Field field : entityClass.getDeclaredFields()) {
            try {
                String fieldName = field.getName();
                Method setField = entityClass.getMethod("set" + StringUtil.toUpperHead(fieldName), field.getType());
                if(form.get(fieldName) == null)continue;
                if(form.get(fieldName).getClass().equals(field.getType())){
//                    System.out.println("set " + fieldName + " = " + form.get(fieldName));
                    setField.invoke(entity,form.get(field.getName()));
                }
                else if(field.getType().equals(Date.class)){
                    //System.out.println("Date = " + (Date)form.get(field.getName()));
                    Date date = new Date();
                    if(form.get(fieldName).getClass().equals(String.class)){
                        try {
                            date.setTime(Long.parseLong((String) form.get(fieldName)));
                            setField.invoke(entity, date);
                        } catch (Exception e){
                            System.out.println("Wrong date type!");
                        }
                    }
                    else if(form.get(fieldName).getClass().equals(Long.class)){
                        date.setTime((Long) form.get(fieldName));
                        setField.invoke(entity,date);
                    }
                }
                else{
                    System.out.println(fieldName + " Type not matching! " + field.getType() + " " + form.get(fieldName).getClass() + " " + form.get(fieldName));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        repository.save(entity);
        form.put(StringUtil.toLowerHead(nodes.get(id).className),entity);
        return maxId;
    }

    /**
    * @Description: 将form信息存储到本表
    * @Date: 2022-04-05 15:34
    * @Param form:
    * @return: java.lang.Integer
    **/
    public Integer saveEntity(Map<String,Object> form){
        return save(entityToId.get(eClass),form);
    }

    /**
    * @Description: 将form信息存储到本表和inf表(inf表只支持一层)
    * @Date: 2022-04-05 15:34
    * @Param form:
    * @return: java.lang.Integer
    **/
    public Integer saveEntityToDependTable(Map<String,Object> form){
        Integer id = entityToId.get(eClass);
        for (Integer dependId : nodes.get(id).dependence) {
            Node dependTable = nodes.get(dependId);
            String idName = dependTable.idName;
            if(form.containsKey(idName)){
                form.put(StringUtil.toLowerHead(dependTable.className),dependTable.repository.findEntityByKeyword(idName,form.get(idName)));
            }
            else{
//                System.out.println("save " + nodes.get(dependId).className);
                save(dependId,form);
            }
        }
        return save(id,form);
    }

    /**
     * @Description: 递归删除衍生表
     * @Date: 2022-04-04 23:02
     * @Param id:
     * @Param fieldName:
     * @Param keyword:
     * @return: void
     **/
    protected void deleteDfs(Integer id,String fieldName,Object keyword){
        Node table = nodes.get(id);
        List<?> entityList = table.repository.findListByKeyword(fieldName, keyword);
        for (Integer deriveId : table.derivative) {
            String deriveFieldName = edgeToField.get(new Pair<>(id, deriveId));
            try {
                Field deriveField = table.entityClass.getDeclaredField(deriveFieldName);
                deriveField.setAccessible(true);
                for(Object entity:entityList){
                    Object deriveKeyword = deriveField.get(entity);
                    if(deriveKeyword!=null){
                        deleteDfs(deriveId,deriveFieldName,deriveKeyword);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        table.repository.deleteByKeyword(fieldName,keyword);
    }

    /**
     * @Description: 删除一张表及依赖该表的子表
     * @Date: 2022-04-04 23:03
     * @Param entityClass:
     * @Param fieldName:
     * @Param keyword:
     * @return: void
     **/
    public void deleteEntity(String fieldName, Object keyword){
        deleteDfs(entityToId.get(eClass),fieldName,keyword);
    }

}
