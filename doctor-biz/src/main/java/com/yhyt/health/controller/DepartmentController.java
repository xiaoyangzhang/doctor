package com.yhyt.health.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhyt.health.model.Department;
import com.yhyt.health.model.DeptDisease;
import com.yhyt.health.model.DeptDoctor;
import com.yhyt.health.model.dto.DepartmentDTO;
import com.yhyt.health.model.query.DiseaseQuery;
import com.yhyt.health.model.vo.DepartmentVO;
import com.yhyt.health.model.vo.DeptCategoryVO;
import com.yhyt.health.model.vo.DeptDiseaseVO;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {

    private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;
    @PostMapping("/addDepartment")
    public int addDepartment(@RequestBody DepartmentDTO departmentDTO) {

//        department.setUpdateTime(new Date());
//        department.setCreateTime(new Date());
        return departmentService.addDepartment(departmentDTO);
    }

    @PutMapping("/updateDepartment")
    public int updateDepartment(@RequestBody DepartmentDTO departmentDTO) {
//        department.setUpdateTime(new Date());
        return departmentService.updateDepartment(departmentDTO);
    }

    @GetMapping("/getDepartmentList/{id}")
    public List<DepartmentVO> getDepartmentListByHospitalId(@PathVariable("id") long id){
        return departmentService.getDepartmentsByHospitalId(id);
    }

    /**
     * 查看科室信息和合作科室信息
     * @param id
     * @return
     */
    @GetMapping("/getDepartRelateInfo/{id}")
    public DepartmentVO getDepartRelateInfo(@PathVariable("id")long id) {
        return departmentService.viewDepartmentRelateInfo(id);
    }

    /**
     * 获取科室已添加疾病列表
     * @param query
     * @return
     */
    @GetMapping("/departDiseaseList")
    public PageInfo<DeptDiseaseVO> getDepartDiseaseListPage(String query) {
        return departmentService.getDepartDiseaseListPage(JSON.parseObject(query,DiseaseQuery.class));
    }

    /**
     * 批量关联科室和疾病
     * @param deptDiseases
     * @return
     */
    @PostMapping("/relateDepartWithDisease")
    public int relateDepartWithDisease(@RequestBody List<DeptDisease> deptDiseases) {
        return departmentService.relateDepartWithDisease(deptDiseases);
    }

    /**
     * 单个关联科室和疾病
     * @param deptDisease
     * @return
     */
    @PostMapping("/addDisease2Dept")
    public AppResult addDisease2Dept(@RequestBody DeptDisease deptDisease) {
        return departmentService.addDisease2Dept(deptDisease);
    }

    @DeleteMapping("/deptDisease/delete/{id}")
    public int deleteDeptDisease(@PathVariable("id") long id) {
        return departmentService.deleteDeptDiseaseById(id);
    }

    /**
     * 添加科室成员（批量）
     * @param deptDoctors
     * @return
     */
    @PostMapping("/relateDepartWithDoctor")
    public int relateDepartWithDoctor(@RequestBody List<DeptDoctor> deptDoctors) {
        return departmentService.relateDepartWithDoctor(deptDoctors);
    }

    @DeleteMapping("/deleteDeptDoctor/{doctorId}")
    public int deleteDeptDoctor(@PathVariable("doctorId")long id) {
        return departmentService.deleteByDoctorId(id);
    }

    @GetMapping("/getDepartment/{id}")
    public DepartmentDTO getDepartment(@PathVariable("id")long id){
        return departmentService.selectDeptById(id);
    }


    @PatchMapping("/deptServiceState/update/{id}/{isFree}/{operator}")
    public int updateDeptServiceState(@PathVariable("id") long id,@PathVariable("isFree") byte isFree,@PathVariable("operator")String operator){
        return departmentService.updateDeptServiceState(id,isFree,operator);
    }

    @PatchMapping("/deptNotice/update")
    public int updateDeptNotice(@RequestBody Department department){
        return departmentService.updateDeptNotice(department);
    }

    @DeleteMapping("/deptCat/delete/{catId}/{deptId}")
    public int deleteDeptCategory(@PathVariable("catId")long catId,@PathVariable("deptId")long deptId){
        return departmentService.deleteDeptCategory(catId, deptId);
    }

    @GetMapping("/category/dept/{deptId}")
    public DeptCategoryVO getDeptCategoryByDeptId(@PathVariable("deptId")long id){
        return departmentService.getDeptCategoryByDeptId(id) ;
    }

    /**
     * 科室一键添加疾病
     * @param id
     * @return
     */
    @GetMapping("/oneKeys/add/{deptId}")
    public int oneKeyAddDisease(@PathVariable("deptId")long id){

        return departmentService.oneKeyAddDisease(id);
    }

    @GetMapping("/dept/get/{id}")
    public DepartmentVO getDeptInfo(@PathVariable("id")long id){
        return departmentService.getDeptInfo(id);
    }
}
