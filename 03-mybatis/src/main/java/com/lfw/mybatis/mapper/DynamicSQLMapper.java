package com.lfw.mybatis.mapper;

import com.lfw.mybatis.pojo.Emp;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DynamicSQLMapper {

  /**
   * 多条查询
   */
  List<Emp> getEmpByCondition(Emp emp);
  List<Emp> getEmpByConditionOne(Emp emp);
  List<Emp> getEmpByConditionTwo(Emp emp);
  /**
   * 测试choose、when、otherwise
   */
  List<Emp> getEmpByChoose(Emp emp);

  /**
   * 通过数组实现批量删除
   */
  int deleteMoreByArray(@Param("eids") Integer[] eids);

  /**
   * 通过list集合实现批量添加
   */
  int insertMoreByList(@Param("emps") List<Emp> emps);
}
