package com.lfw.mybatis.test;

import com.lfw.mybatis.mapper.EmpMapper;
import com.lfw.mybatis.pojo.Emp;
import com.lfw.mybatis.utils.SqlSessionUtils;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class ResultMapTest {

  /**
   * 解决字段名和属性名不一致的情况： a>为字段起别名，保持和属性名的一致 b>设置全局配置，将_自动映射为驼峰
   * <setting name="mapUnderscoreToCamelCase" value="true"/>
   * c>通过resultMap设置自定义的映射关系
   * <resultMap id="empResultMap" type="Emp">
   * <id property="eid" column="eid"></id>
   * <result property="empName" column="emp_name"></result>
   * <result property="age" column="age"></result>
   * <result property="sex" column="sex"></result>
   * <result property="email" column="email"></result>
   * </resultMap>
   * <p>
   * 处理多对一的映射关系： a>级联属性赋值 b>association c>分步查询
   * <p>
   * 处理一对多的映射关系 a>collection b>分步查询
   */

  @Test
  public void testGetAllEmp() {
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
    List<Emp> list = mapper.getAllEmp();
    list.forEach(System.out::println);
  }

  @Test
  public void testGetEmpAndDept() {
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
    Emp emp = mapper.getEmpAndDept(3);
    System.out.println(emp);
  }

  @Test
  public void testGetEmpAndDeptByStep() {
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
    Emp emp = mapper.getEmpAndDeptByStepOne(3);
    System.out.println(emp.getEmpName());
    System.out.println("++++++++++++++++++++++++++++++");
    System.out.println(emp.getDept());
  }

  @Test
  public void testGetDeptAndEmp() {
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
  }
}
