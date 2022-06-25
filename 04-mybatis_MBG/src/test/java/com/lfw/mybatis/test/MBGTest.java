package com.lfw.mybatis.test;

import com.lfw.mybatis.mapper.EmpMapper;
import com.lfw.mybatis.pojo.Emp;
import com.lfw.mybatis.pojo.EmpExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MBGTest {

    @Test
    public void testMBG01() {
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
            //查询所有数据
            List<Emp> list = mapper.selectByExample(null);
            list.forEach(emp -> System.out.println(emp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMBG02() {
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
            //根据条件查询
            EmpExample example = new EmpExample();
            example.createCriteria().andEmpNameEqualTo("张三").andAgeGreaterThanOrEqualTo(20);
            example.or().andDidIsNotNull();
            List<Emp> list = mapper.selectByExample(example);
            list.forEach(emp -> System.out.println(emp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMBG03() {
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
            mapper.updateByPrimaryKeySelective(new Emp(1, "admin", null, 22, "456@qq.com", 3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
