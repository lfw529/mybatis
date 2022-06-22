package com.lfw.mybatis.test;

import com.lfw.mybatis.mapper.ParameterMapper;
import com.lfw.mybatis.pojo.User;
import com.lfw.mybatis.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterMapperTest {

  /**
   * MyBatis获取参数值的两种方式：${}和#{} ${}本质字符串拼接 #{}本质占位符赋值 MyBatis获取参数值的各种情况： 1、mapper接口方法的参数为单个的字面量类型
   * 可以通过${}和#{}以任意的名称获取参数值，但是需要注意${}的单引号问题
   * <p>
   * 2、mapper接口方法的参数为多个时 此时MyBatis会将这些参数放在一个map集合中，以两种方式进行存储 a>以arg0,arg1...为键，以参数为值
   * b>以param1,param2...为键，以参数为 值 因此只需要通过#{}和${}以键的方式访问值即可，但是需要注意${}的单引号问题
   * <p>
   * 3、若mapper接口方法的参数有多个时，可以手动将这些参数放在一个map中存储 只需要通过#{}和${}以键的方式访问值即可，但是需要注意${}的单引号问题
   * <p>
   * 4、mapper接口方法的参数是实体类类型的参数 只需要通过#{}和${}以属性的方式访问属性值即可，但是需要注意${}的单引号问题
   * <p>
   * 5、使用@Param注解命名参数 此时MyBatis会将这些参数放在一个map集合中，以两种方式进行存储 a>以@Param注解的值为键，以参数为值
   * b>以param1,param2...为键，以参数为值 因此只需要通过#{}和${}以键的方式访问值即可，但是需要注意${}的单引号问题
   */

  @Test
  public void testCheckLoginByParam() {
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
    User user = mapper.checkLoginByParam("张三", "123456");
    System.out.println(user);
  }

  @Test
  public void testInsertUser() {
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
    int result = mapper.insertUser(new User(null, "李四", "123", 23, "男", "123@qq.com"));
    System.out.println(result);
  }

  @Test
  public void testCheckLoginByMap() {
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
    Map<String, Object> map = new HashMap<>();
    map.put("username", "张三");
    map.put("password", "123456");
    User user = mapper.checkLoginByMap(map);
    System.out.println(user);
  }

  @Test
  public void testCheckLogin() {
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
    User user = mapper.checkLogin("张三", "123456");
    System.out.println(user);
  }

  @Test
  public void testGetUserByUsername() {
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
    User user = mapper.getUserByUsername("张三");
    System.out.println(user);
  }

  @Test
  public void testGetAllUser() {
    SqlSession sqlSession = SqlSessionUtils.getSqlSession();
    ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
    List<User> list = mapper.getAllUser();
    list.forEach(user -> System.out.println(user));
  }

  //JDBC 占位符查询方式回忆
//    @Test
//    public void testJDBC() throws Exception {
//        String username = "admin";
//        Class.forName("");
//        Connection connection = DriverManager.getConnection("", "", "");
//        //PreparedStatement ps = connection.prepareStatement("select * from t_user where username = '" + username + "'");
//        PreparedStatement ps = connection.prepareStatement("selelt * from t_user where username = ?");
//        ps.setString(1, username);
//    }

  /**
   * 相关报错：org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 4
   *  从日志反馈中，可以得出接收数据出现问题。猜测是因为数据库中的数据有冗余，返回多条记录，而接收方法只定义了接收一个对象。
   *  查找代码，果然如此。其中有一个selectByEmail方法，由于一个用户可以用不同的邮箱多次注册，导致按邮箱查询时返回的用户记录有多条，且是同一个用户。
   *  基本推断是数据库查询语句有问题，需要去重。或者说，删除数据库中的冗余数据。
   *
   * 解决办法
   *  避免修改数据库表，只能修改代码。在使用含有like语句时，用list接收。=语句时，确保数据不冗余。
   */
}
