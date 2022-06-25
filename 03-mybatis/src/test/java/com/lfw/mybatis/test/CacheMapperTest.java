package com.lfw.mybatis.test;

import com.lfw.mybatis.mapper.CacheMapper;
import com.lfw.mybatis.pojo.Emp;
import com.lfw.mybatis.utils.SqlSessionUtils;
import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class CacheMapperTest {

  // 一级缓存
  @Test
  public void testOneCache() {
    SqlSession sqlSession1 = SqlSessionUtils.getSqlSession();
    CacheMapper mapper1 = sqlSession1.getMapper(CacheMapper.class);
    Emp emp1 = mapper1.getEmpByEid(1);
    System.out.println(emp1);
    //执行增删改查语句会清除缓存
    //mapper1.insertEmp(new Emp(null,"abc",23,"男","123@qq.com"));
    sqlSession1.clearCache();  //清除缓存
    Emp emp2 = mapper1.getEmpByEid(1);
    System.out.println(emp2);
    /*
      SqlSession sqlSession2 = SqlSessionUtils.getSqlSession();
      CacheMapper mapper2 = sqlSession2.getMapper(CacheMapper.class);
      Emp emp2 = mapper2.getEmpByEid(1);
      System.out.println(emp2);
    */
  }

  // 二级缓存
  @Test
  public void testTwoCache() {
    try {
      InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
      //开启自动提交
      SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
      CacheMapper mapper1 = sqlSession1.getMapper(CacheMapper.class);
      System.out.println(mapper1.getEmpByEid(1));
      sqlSession1.close();
      SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
      CacheMapper mapper2 = sqlSession2.getMapper(CacheMapper.class);
      System.out.println(mapper2.getEmpByEid(1));
      sqlSession2.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
