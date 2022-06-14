# README

## 一、简介

## 二、项目开发注意事项

1. 主键类型必须为Integer

2. 字段名与列名必须符合（驼峰-下划线）对应命名

3. 字段前缀-实体类名-repository名必须一致，service名controller名尽量一致（repository类名必须为实体类名+Repository）

4. 两张表间不能互存主键，但其他字段可以重复以减少查询复杂度

5. repository必须继承commonRepository而不是直接继承JpaRepository

6. 注意inf表与其主体表的关系 (inf表应该作为主体表的依赖表（如：studentInf是student的依赖表）)

7. yaml书写规范：

   a.page的name与页面对应类名一致（首字母大写）

   b.prop的value值与字段名对应（首字母小写）

## 三、项目YAML书写规范

#### 1.整体结构

```yaml
uims:
  menu:
    - label: 学生信息
      subMenu:
        - name: StudentInit
          label: 学生管理
    - label: 学习管理
      subMenu:
        - name: CourseInit
          label: 课程管理
        - name:
          label: 选课管理
        - name:
          label: 资料管理
        - name:
          label: 考勤管理
        - name:
          label: 作业管理
        - name:
          label: 成绩管理
  page:
    #学生信息管理init
    - name: StudentInit
      url: information/studentInf/init
      type: table
      title: 学生管理
      addPage: StudentEdit
      item:
        - type: text
          prop: studentNum
          label: 学号
        - type: text
          prop: studentName
          label: 姓名
        - type: text
          prop: sex
          label: 性别
        - type: text
          prop: age
          label: 年龄
        - type: text
          prop: birthday
          label: 出生日期
        - type: opers
          label: 操作
          opers:
            - name: edit
              label: 编辑
              page: StudentEdit
            - name: delete
              label: 删除
              url: information/studentInf/delete
      query:
        - type: input
          prop: keyword
          label: 请输入学号或姓名
          field: studentNum,studentName
          url: information/studentInf/query
    #学生信息管理edit
    - name: StudentEdit
      url: informatin/studentInf/editInit
      type: form
      title: 学生信息
      item:
        - type: input
          prop: studentNum
          label: 学号
        - type: input
          prop: studentName
          label: 姓名
        - type: select
          prop: sex
          label: 性别
          option:
            - value: 1
              label: 男
            - value: 2
              label: 女
        - type: input
          prop: age
          label: 年龄
        - type: date
          prop: birthday
          label: 出生日期
        - opers:
            - name: submit
              label: 提交
              url: information/studentInf/editSubmit
```



#### 2.书写规范

1. 菜单标签menu

   ```yaml
   menu:
     - label: #菜单组名
       subMenu:
         - name: Student #对应一个page名
           label: #子菜单名
   ```

   

2. 页面page

   ```yaml
   page:
     - name: #页面名
       url: #访问地址
       field: #查询字段名，不写默认为主键
       type:
       ...
   ```

3. page的type组件dynamicTable

   ```yaml
   page:
     - name:
     ...
       type: dynamicTable
       label: courseName #list组件标签,对应字段名
       item:
         - type:
           prop:
           label:
           ...
   ```

4. query组件需要注明查询哪些字段，不写则默认**查询所有item中的字段（需要在前端或后端中实现）**

   ```yaml
   query:
     - type: input
       prop: keyword #必须为keyword，与后端中代码对应
       label: 请输入...
       field: f1,f2,f3... #不写则默认为所有字段
       url: #query访问的后端地址
   ```

5. 页面跳转按钮

   ```yaml
   opers:
     - name: edit #按钮名
     	label: 编辑 #按钮标签
     	page: Student #转至页面名
     - name: delete
     	label: 删除
     	url: /information/student/delete
   
   showAdd: 1 #现已弃用
   addPage: StudentEdit #要跳转到的页面name
     	
   ```

6. 选项框select

   固定选项select
   
   ```yaml
   - type: select
     prop: sex #对应字段
     label: 性别
     option:
       - value: 1
         label: 男
       - value: 2
         label: 女
   ```
   
   后端传入选项select
   
   ```yaml
   - type: dynamicSelect
     prop: courseId #对应字段
     label: 课程
     table: Course
     optionLabel: courseName
     
     #后端传来一个
     {
     first:
     second:
     }
   ```
   
7. 列表信息计算

   ```yaml
   calc:
       - label: 绩点
         type: avg #计算类型，平均或求和
         prop: scoreLogDetail 
         weight: coursePoint #权值，不写默认为1
       - label: 学分
         type: sum
         prop: coursePoint
   ```

   

## 四、项目结构

### 1.业务结构

#### 学生信息：

学生个人信息

学生在校信息

#### 学习信息：

课程信息

课程中心

选课信息

考勤信息

作业信息

成绩信息

### 2.后端结构

![image-20220406144207096](C:\Users\xypyf\AppData\Roaming\Typora\typora-user-images\image-20220406144207096.png)

### 3.前端结构

#### （1）页面结构

一个前端页面对应一个后端controller的Mapping方法



## 五、项目数据库表信息

### 1.学生信息

学生信息（student_inf)：

	student_inf_id  学生信息id
	student_phone	学生联系方式
	student_background  学生入学前信息
	student_family	学生家庭信息
	student_relation  学生社会关系


学生表（student):

	student_id	学生id
	si_id	学生信息id
	student_num	学号
	student_name	学生名



### 2.学习信息

课程表（course）:

	course_id		课程id
	course_name	课程名

资料类别（material_category）:

	material_category_id	类别id
	material_category_name	类别名

课程材料	(material）

	material_id	材料id
	mc_id		类别id 
	material_detail	材料详情

选课信息(course_log):

	course_log_id	选课信息id 
	s_id		学生id
	c_id		课程id

考勤信息（check_Log）:

	check_log_id	考勤记录id
	cl_id		选课信息id
	check_date	考勤时间
	check_detail	考勤详情

作业表（homework):

	homework_id	作业id
	c_id		课程id
	homework_date	作业时间
	homework_detail	作业详情

作业信息（homework_log):

	homework_log_id	作业信息id
	cl_id		选课信息id
	h_id        作业id
	homework_log_status作业完成状态

成绩信息（score_log）：

	score_log_id	成绩信息id 
	cl_id		选课信息id
	score_detail	成绩信息

## 六、其他

### 1.项目原有文档

Spring Boot Service with Mysql to Login

Reference:
https://www.bezkoder.com/spring-boot-jwt-authentication/

https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication

Dependency:
mysql

How to run local:
mvn spring-boot:run

Build jar
mvn spring-boot:build-image

https://www.heidisql.com/
https://sqlitebrowser.org/

cd C:\teach\java-server\target
java -jar -Dfile.encoding=utf-8 teach-1.0-SNAPSHOT.jar

insert into user_type ( name,id) values( 'ROLE_ADMIN',1);
insert into user_type ( name,id) values( 'ROLE_USER',2);
insert into person (per_num, per_name,per_type,person_id) values( 'admin','admin','0',1);
insert into user (user_name, password,person_id,user_type_id,user_id) values( 'admin','$2a$10$FV5lm..jdQWmV7hFguxKDeTrGyiWg1u6HYD2QiQc0tRROrNtSQVOy',1,1,1);

