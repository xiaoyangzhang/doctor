package com.yhyt.health.mapper;

import com.yhyt.health.model.DeptDictDept;
import com.yhyt.health.model.vo.DepartmentVO;
import com.yhyt.health.model.vo.DeptCategoryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptDictDeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeptDictDept record);

    int insertSelective(DeptDictDept record);

    DeptDictDept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeptDictDept record);

    int updateByPrimaryKey(DeptDictDept record);

    List<DepartmentVO> getDepartCagegoryByDepartId(long departId);

    int deleteByDeptId(@Param("deptId") long deptId);

    int deleteDeptCategory(long categoryId, long deptId);

    List<DeptDictDept> selectByDeptId(@Param("deptId") long deptId);
    DeptCategoryVO getDeptCategoryByDeptId(@Param("deptId") long id);

    List<DeptCategoryVO> getDiseaseIdsByDeptId(long deptId);

    int selectDiseaseCountByDeptId(long deptId);



}