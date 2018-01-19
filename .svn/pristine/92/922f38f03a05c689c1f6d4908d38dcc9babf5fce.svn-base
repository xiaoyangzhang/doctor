package com.yhyt.health.service.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhyt.health.constant.Constants;
import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.ArticleMapper;
import com.yhyt.health.mapper.DoctorMapper;
import com.yhyt.health.mapper.SysConfigMapper;
import com.yhyt.health.model.Department;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.SysConfig;
import com.yhyt.health.model.vo.AppointmentPatientVo;
import com.yhyt.health.model.vo.ArticleAppVo;
import com.yhyt.health.model.vo.app.ArticleClickCountVo;
import com.yhyt.health.model.vo.app.ArticleTypeCountVo;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.result.ResultStatus;
import com.yhyt.health.service.DoctorService;
import com.yhyt.health.service.HospitalService;
import com.yhyt.health.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章app端需要的serviceImpl
 * @author wangzhan
 * @version 1.0
 * data 2017/12/06
 */
@Service
public class ArticleApiImpl implements  ArticleApi {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private DoctorConstant doctorConstant;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DepartmentApi departmentApi;

    @Autowired
    private SysConfigMapper sysConfigMapper;


    /**
     * 分页查询文章列表
     *
     * @param token     token从头中获取
     * @param type        类型 1培训 2课题
     * @param pageIndex 页码
     * @param pageSize  每页数量
     * @return
     */
    @Override
    public AppResult getArticleList(final String token,final String userId, final String type, Integer pageIndex, Integer pageSize) {

        Doctor doctor = null;
        try {
            //获取当前医生信息
            doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        } catch (Exception e) {
            e.printStackTrace();
            if (StringUtils.isEmpty(userId)) {
                AppResult appResult = new AppResult();
                appResult.setStatus(new ResultStatus("203", String.format("根据token［％s］从redis中获取医生信息异常" +
                        "请检查reids服务是否正常，查看token是否失效", token)));
            }
        }
        if(null == doctor){
            doctor = doctorMapper.selectByPrimaryKey(Long.parseLong(userId));
        }

        if (null == pageIndex || pageIndex == 0) {
            pageIndex = Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize == 0) {
            pageSize = Constants.PAGESIZE;
        }
        //方便起见 先查询出科室id 和医院所在地市id
        Long departmentId = doctor.getDepartmentId();    //当前所在科室id
        String cityId =doctorMapper.getCityIdBydepartmentId(departmentId);  //当前所在科室的医院对应的城市id
    //    Long departmentCode = doctorMapper.getdepartmentCode(departmentId); //根据科室id 获取二级科室编码
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Map<String,Object> map = new HashMap<>();
        map.put("departmentId",departmentId);
        map.put("cityId",cityId);
        map.put("type",type);
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        map.put("token",token);
        List<SysConfig> sysConfigs = sysConfigMapper.selectSysConfig("articleUrl");
        if (null != sysConfigs && sysConfigs.size() > 0) {
            map.put("articleUrl",sysConfigs.get(0).getSalt1());
        }
        Page page = PageHelper.startPage(pageIndex, pageSize);
        List<ArticleAppVo> list = articleMapper.getArticleAppVoLista(map);
        PageInfo<ArticleAppVo> pageInfo = new PageInfo<ArticleAppVo>(list);
        appResult.getBody().put("list", pageInfo.getList());
        return appResult;
    }

    @Override
    public AppResult increaseViewingTimes(Long id, Long count) {

        synchronized(this){
            if (null == count){
                count = 1L;
            }
            articleMapper.updateClickCountById(id,count,new Date());
        }
        ArticleClickCountVo vo = new ArticleClickCountVo();
        vo = articleMapper.getArticleClickCountVo(id);
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Map<String,Object> map =  new HashMap<>();
        map.put("vo",vo);
        appResult.setBody(map);
        return appResult;
    }

    /**
     * 根据上架状态，获取不同文章的数据条数
     * @param state
     * @return
     */
    @Override
    public  List<ArticleTypeCountVo> getArticleCount(String state) {
        return articleMapper.getArticleCount(state);
    }

    /**
     * 根据id获取医略
     * @param id
     * @return
     */
    @Override
    public AppResult getArticleDetail(Long id) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        appResult.getBody().put("article",articleMapper.selectByPrimaryKey(id));
        return appResult;
    }
}
