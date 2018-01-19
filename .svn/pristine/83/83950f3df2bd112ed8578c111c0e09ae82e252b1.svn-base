package com.yhyt.health.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.DoctorDisease;
import com.yhyt.health.model.DoctorReview;
import com.yhyt.health.model.dto.SysBlacklistDTO;
import com.yhyt.health.model.query.*;
import com.yhyt.health.model.vo.*;
import com.yhyt.health.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class DoctorController {

    private static Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private DoctorService doctorService;

//    @Autowired
//    private SysService sysService;

    @PostMapping("/doctor/add")
    public int addDoctor2Dept(@RequestBody Doctor doctor){
        return doctorService.insert(doctor);
    }

    @GetMapping("/doctor/{id}")
    public Doctor getDoctorById(@PathVariable("id") long id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/doctorList")
    public PageInfo<DoctorVO> getDoctorListPage(@RequestParam("doctorQueryStr") String doctorQueryStr) {
        DoctorQuery doctorQuery = JSON.parseObject(doctorQueryStr, DoctorQuery.class);
        return doctorService.getDoctorListPage(doctorQuery);
    }

    @GetMapping("/disease/doctorList")
    public PageInfo<DoctorDiseaseVO> getDiseaseDoctorListPage(@RequestParam("doctorQueryStr") String doctorQueryStr) {
        DoctorQuery doctorQuery = JSON.parseObject(doctorQueryStr, DoctorQuery.class);
        return doctorService.getDiseaseDoctorListPage(doctorQuery);
    }

    @GetMapping("/toEditDoctor/{id}")
    public DoctorVO toEditDoctor(@PathVariable("id") long id) {
        return doctorService.getDoctorInfoById(id);
    }
    @PutMapping("/updateDoctor")
    public int updateDoctor(@RequestParam("params") String params) throws ParseException {
        Doctor doctor = JSON.parseObject(params, Doctor.class);
        return  doctorService.update(doctor);
    }

    @GetMapping("/getDoctorExtInfo/{id}")
    public DoctorExtVO getDoctorExtInfo(@PathVariable("id") long id) {
        return doctorService.getDoctorExtInfo(id);
    }

    //TODO 参数传递
    @PatchMapping("/updatePassword")
    public int updatePassword(@RequestParam("params") String params) {
        Doctor doctor = JSON.parseObject(params,Doctor.class);
        return doctorService.updatePassword(doctor);
    }

    @GetMapping("/doctorReviewList")
    public PageInfo<DoctorReviewVO> getDoctorReviewList(@RequestParam("queryStr")String queryStr) {
        DoctorReviewQuery doctorReviewQuery = JSON.parseObject(queryStr, DoctorReviewQuery.class);
        return doctorService.getDoctorReviewListPage(doctorReviewQuery);
    }

    @GetMapping("/viewDoctorReviewInfo/{id}/{operator}")
    public DoctorReviewVO viewDoctorReviewInfo(@PathVariable("id")long id,@PathVariable("operator")String operator){
        return doctorService.viewDoctorApplyInfo(id,operator);
    }

    @PatchMapping("/updateReviewState")
    public int updateReviewState(@RequestParam("id") long id){
        return doctorService.updateReviewState(id);
    }

    @PatchMapping("/releaseTask")
    public int releaseTask(@RequestParam("operator") String operator){
        return doctorService.releaseTaskByOperator(operator);
    }

    @PatchMapping("/changeLockStateReviewRecord")
    public int updateDoctorReview(@RequestBody DoctorReview doctorReview) {
//        DoctorReview doctorReview = JSON.parseObject(params,DoctorReview.class);
//        doctorReview.setId(Long.parseLong(params.get("id")));
//        doctorReview.setOperator(params.get("operator"));
//        doctorReview.setIsLock((byte) 1);
        return doctorService.updateDoctorReview(doctorReview);
    }
    @PatchMapping("/refuseApply")
    public int refuseApply(@RequestParam("params") String params) {
//        DoctorReview doctorReview = new DoctorReview();
//        doctorReview.setState(Byte.parseByte(params.get("state")));
//        doctorReview.setReason(params.get("reason"));
        DoctorReview doctorReview = JSON.parseObject(params, DoctorReview.class);
        return doctorService.refuseApply(doctorReview.getId(),doctorReview.getReason());

    }
    @PatchMapping("/agreeApply")
    public int agreeApply(@RequestParam("id") long id) {
//        DoctorReview doctorReview = new DoctorReview();
//        doctorReview.setState(Byte.parseByte(params.get("state")));
//        doctorReview.setReason(params.get("reason"));
        return doctorService.agreeApply(id);
    }

    /**
     * 名医管理
     * @param queryStr
     * @return
     */
    @GetMapping("/getDoctorDisease")
    public PageInfo<DoctorDiseaseVO> getDoctorDiseaseListPage(@RequestParam("queryStr") String queryStr) {
        DoctorDiseaseQuery doctorDiseaseQuery = JSON.parseObject(queryStr, DoctorDiseaseQuery.class);
        return doctorService.getDoctorDiseaseListPage(doctorDiseaseQuery);
    }

    @PostMapping("/relateDoctorWithDisease")
    public int relateDoctorWithDisease(@RequestBody List<DoctorDisease> list) {
        return doctorService.relateDoctorWithDisease(list);
    }

    @PostMapping("/relateDiseaseWithDoctor")
    public int relateDiseaseWithDoctor(@RequestBody List<DoctorDisease> list) {
        return doctorService.relateDiseaseWithDoctor(list);
    }

    @PostMapping("/doctorDisease/add")
    public String addDoctorDisease(@RequestBody DoctorDisease doctorDisease){
        return doctorService.addOneDiseaseDoctor(doctorDisease);
    }

    /**
     * 新建科室成员
     * @param doctor
     * @return
     */
    @PostMapping("/createDepartDoctor")
    public int createDepartDoctor(@RequestBody Doctor doctor) throws Exception {
        return doctorService.addDepartDoctor(doctor);
    }
//    @PostMapping("/addDeptDoctor")
//    public int addDeptDoctor(@RequestBody List<DeptDoctor> deptDoctors){
//
//    }

    @GetMapping("/getDoctorsByDiseaseId/{id}")
    public List<DoctorExtVO> getDoctorsByDiseaseId(@PathVariable("id") long id) {
        return doctorService.getDoctorsByDiseaseId(id);
    }

    @GetMapping("/addedDisease/query")
    public PageInfo<DoctorDiseaseVO> queryAddedDisease(String queryStr){
        DoctorDiseaseQuery doctorQuery = JSON.parseObject(queryStr, DoctorDiseaseQuery.class);
        return  doctorService.queryAddedDiseaseByDoctorId(doctorQuery);
    }

    @GetMapping("/getDoctorBlacklist")
    public PageInfo<DoctorBlacklistVO> getDoctorBlacklist(@RequestParam("queryStr")String queryStr) {
        DoctorBlackQuery doctorBlackQuery = JSON.parseObject(queryStr, DoctorBlackQuery.class);
        return doctorService.getDoctorBlacklistPage(doctorBlackQuery);
    }

    @PostMapping("/blacklist/operate")
    public int operateBlacklist(@RequestBody SysBlacklistDTO sysBlacklistDTO){
        return doctorService.operateBlacklist(sysBlacklistDTO);
    }
    @GetMapping("/deptDoctorList/query")
    public PageInfo<DoctorVO> queryDeptDoctorListPage(String queryStr) {
        return doctorService.getDeptDoctorListPage(JSON.parseObject(queryStr,DeptDoctorQuery.class));
    }

    @DeleteMapping("/deptDoctor/delete/{id}")
    public int deleteDeptDoctor(@PathVariable("id")long id){
        return doctorService.deleteDeptDoctorById(id);
    }

    @PatchMapping("/dismissAdmin/{id}")
    public int dismissAdmin(@PathVariable("id")long id) {
        return doctorService.dismisssAdmin(id);
    }
    @PatchMapping("/admin/appoint/{id}")
    public int appointAdmin(@PathVariable("id")long id){
        return doctorService.appointAdmin(id);
    }

    @GetMapping("/deptDoctors/get/{id}")
    public List<DoctorVO> getDeptDoctors(@PathVariable("id")long id){
        return doctorService.getDeptDoctorList(id);
    }
    @PostMapping("/diseaseDoctor/add")
    public String addOneDiseaseDoctor(@RequestBody DoctorDisease doctorDisease){
        return doctorService.addOneDiseaseDoctor(doctorDisease);
    }

    @PatchMapping("/doctorDisease/update")
    public int updateDoctorDisease(@RequestBody List<DoctorDisease> doctorDiseases){
        return doctorService.updateDoctorDisease(doctorDiseases);
    }

    /**
     * 给医生关联疾病的列表
     * @param queryStr
     * @return
     */
    @GetMapping("/doctorDisease/query")
    public PageInfo<DoctorExtVO> queryDoctorDiseaseListPage(String queryStr){
        DoctorQuery doctorQuery = JSON.parseObject(queryStr, DoctorQuery.class);
        return doctorService.getDoctorDiseaseListPage(doctorQuery);
    }

    @DeleteMapping("/doctorDisease/delete/{id}")
    public int deleteDoctorDisease(@PathVariable("id")long id){
        return doctorService.deleteDoctorDisease(id);
    }

    @GetMapping("/doctor/get/{name}")
    public Doctor queryDoctorByName(@PathVariable("name")String name){
        return doctorService.queryDoctorByName(name);
    }


    /*@GetMapping("/upgradelist/get")
    public PageInfo<SysUpgrade> getUpgradeList(@RequestParam("queryStr") String queryStr){
        UpgradeQuery upgradeQuery = JSON.parseObject(queryStr, UpgradeQuery.class);
        return sysService.selectUpgradeListPage(upgradeQuery);
    }

    @PostMapping("/upgrade/add")
    public int addUpgrade(@RequestBody SysUpgrade sysUpgrade){

        return sysService.addUpgrade(sysUpgrade);
    }

    @GetMapping("/upgrade/get/{id}")
    public SysUpgrade getUpgradeById(@PathVariable("id")long id){

        return sysService.selectById(id);
    }

    @PutMapping("/upgrade/edit")
    public int editUpgrade(@RequestBody SysUpgrade sysUpgrade){

        return sysService.editUpgrade(sysUpgrade);
    }*/
}
