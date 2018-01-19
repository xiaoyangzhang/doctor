package com.yhyt.health.service;

import java.beans.PropertyDescriptor;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhyt.health.mapper.DepartmentObstetricsDangersMapper;
import com.yhyt.health.mapper.DepartmentObstetricsMapper;
import com.yhyt.health.mapper.DoctorReviewMapper;
import com.yhyt.health.model.DepartmentObstetrics;
import com.yhyt.health.model.DepartmentObstetricsDangers;
import com.yhyt.health.model.DepartmentObstetricsPatient;
import com.yhyt.health.model.DeptDoctor;
import com.yhyt.health.model.Device;
import com.yhyt.health.model.Dictionary;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.DoctorDisease;
import com.yhyt.health.model.DoctorReview;
import com.yhyt.health.model.PatientlObstetrics;
import com.yhyt.health.model.dto.SysBlacklistDTO;
import com.yhyt.health.model.query.DeptDoctorQuery;
import com.yhyt.health.model.query.DoctorBlackQuery;
import com.yhyt.health.model.query.DoctorDiseaseQuery;
import com.yhyt.health.model.query.DoctorQuery;
import com.yhyt.health.model.query.DoctorReviewQuery;
import com.yhyt.health.model.query.ObstetricsQuery;
import com.yhyt.health.model.vo.DoctorBlacklistVO;
import com.yhyt.health.model.vo.DoctorDiseaseVO;
import com.yhyt.health.model.vo.DoctorExtVO;
import com.yhyt.health.model.vo.DoctorReviewVO;
import com.yhyt.health.model.vo.DoctorVO;
import com.yhyt.health.result.AppResult;
@Service
public class ObstetricsServiceImpl implements ObstetricsService {


    @Autowired
    private DepartmentObstetricsMapper departmentObstetricsMapper;
    
    @Autowired
    private DepartmentObstetricsDangersMapper departmentObstetricsDangersMapper;
    
	
	@Override
	public PageInfo<PatientlObstetrics> getObstetricsList(ObstetricsQuery doctorDiseaseQuery) {
		// TODO Auto-generated method stub
		
		 Page<ObstetricsQuery> page = PageHelper.startPage(doctorDiseaseQuery.getPageIndex(), doctorDiseaseQuery.getPageSize());

        List<PatientlObstetrics> doctorReviewListPage = departmentObstetricsMapper.getObstetricsList(doctorDiseaseQuery);
        List<PatientlObstetrics> list=new ArrayList();
        Set t=new HashSet();
        if(null!=doctorReviewListPage&&doctorReviewListPage.size()!=0) {
        for(int ist=0;ist<doctorReviewListPage.size();ist++) {
        	if(t.contains(doctorReviewListPage.get(ist).getId())) {
        		continue;
        	}
        	else {
        		t.add(doctorReviewListPage.get(ist).getId());
        	String flag=doctorReviewListPage.get(ist).getHasinsurance();
        	if(flag.equals("1")) {
        		doctorReviewListPage.get(ist).setHasinsurance("是");
        	}
        	if(flag.equals("2")) {
        		doctorReviewListPage.get(ist).setHasinsurance("否");
        	}
        	list.add(doctorReviewListPage.get(ist));
        	}
        }
        }
        PageInfo<PatientlObstetrics> pageInfo = new PageInfo<>(list);
        pageInfo.setSize(list.size());
        pageInfo.setList(list);
        return pageInfo;
	}
	
	@Override
	public PageInfo<PatientlObstetrics> getObstetricsListSingle(ObstetricsQuery doctorDiseaseQuery) {
		// TODO Auto-generated method stub
		
		 Page<ObstetricsQuery> page = PageHelper.startPage(doctorDiseaseQuery.getPageIndex(), doctorDiseaseQuery.getPageSize());

       List<PatientlObstetrics> doctorReviewListPage = departmentObstetricsMapper.getObstetricsListSingle(doctorDiseaseQuery);
     
       if(null!=doctorReviewListPage&&doctorReviewListPage.size()!=0) {
       for(int ist=0;ist<doctorReviewListPage.size();ist++) {
       	String flag=doctorReviewListPage.get(ist).getHasinsurance();
       	if(flag.equals("1")) {
       		doctorReviewListPage.get(ist).setHasinsurance("是");
       	}
       	if(flag.equals("2")) {
       		doctorReviewListPage.get(ist).setHasinsurance("否");
       	}
       }
       }
       PageInfo<PatientlObstetrics> pageInfo = new PageInfo<>(doctorReviewListPage);
       return pageInfo;
	}


	@Override
	public List<Object> getObstetricsDetailList(ObstetricsQuery doctorDiseaseQuery) {
		 List<Object> ar=new ArrayList<Object>();
		 String[] st=doctorDiseaseQuery.getUsername().split(",");
		 for(int j=0;j<st.length;j++) {
			 String id=st[j];
			 Map ap=new HashMap();
			 ap.put("id", id);
	        List<DepartmentObstetricsPatient> doctorReviewListPage = departmentObstetricsMapper.getObstetricsDetailList(ap);
	        if(null!=doctorReviewListPage&&doctorReviewListPage.size()!=0) {
	        	DepartmentObstetricsPatient sc=doctorReviewListPage.get(0);
	        	 ap.put("id", sc.getDeptPatientSignId());
	            List<Dictionary> docto=departmentObstetricsDangersMapper.getObstetricsDetailLists(ap);
		        if(null!=docto&&docto.size()!=0) {
		        	String dan="";
		        	for(int di=0;di<docto.size();di++) {
		        		if(di==0) {
		        			dan+=docto.get(di).getItemName();
		        		}
		        		else {
		        		dan+=","+docto.get(di).getItemName();
		        		}
		        	}
		        sc.setDangerous(dan);
		        }
		        String flag=sc.getSex();
		        if(flag.equals("1")) {
		        	sc.setSex("男");
		        }
		        if(flag.equals("2")) {
		        	sc.setSex("女");
		        }
		        Map ma=beanToMap(sc);
	        	ar.add(ma);
	        	
	        }
	        
	   
		 }
	        return ar;
	}


	@Override
	public PageInfo<PatientlObstetrics> getObstetricsListExport(ObstetricsQuery doctorDiseaseQuery) {
		// TODO Auto-generated method stub
		
		 Page<ObstetricsQuery> page = PageHelper.startPage(doctorDiseaseQuery.getPageIndex(), doctorDiseaseQuery.getPageSize());

       List<PatientlObstetrics> doctorReviewListPage = departmentObstetricsMapper.getObstetricsListExport(doctorDiseaseQuery);
       PageInfo<PatientlObstetrics> pageInfo = new PageInfo<>(doctorReviewListPage);
       return pageInfo;
	}


	@Override
	public List getObstetricsDetailListMail() {
		 List<Object> ar=new ArrayList<Object>();
			 Map ap=new HashMap();
			
			 
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				Date date = cal.getTime();
				DateFormat f3 = new SimpleDateFormat("yyyy-MM-dd");
				 ap.put("date", f3.format(date));
	        List<DepartmentObstetricsPatient> doctorReviewListPage = departmentObstetricsMapper.getObstetricsDetailListMail(ap);
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        for(int da=0;da<doctorReviewListPage.size();da++) {
	        
	        
	        if(null!=doctorReviewListPage&&doctorReviewListPage.size()!=0) {
	        	DepartmentObstetricsPatient sc=doctorReviewListPage.get(da);
	        	ap.put("id", sc.getDeptPatientSignId());
	            List<Dictionary> docto=departmentObstetricsDangersMapper.getObstetricsDetailLists(ap);
		        if(null!=docto&&docto.size()!=0) {
		        	String dan="";
		        	for(int di=0;di<docto.size();di++) {
		        		if(di==0) {
		        			dan+=docto.get(di).getItemName();
		        		}
		        		else {
		        		dan+=","+docto.get(di).getItemName();
		        		}
		        	}
		        sc.setDangerous(dan);
		        }
	        	
		        Map ma=beanToMap(sc);
		        if(null!=doctorReviewListPage.get(da).getCreateTime()) {
		        ma.put("createTime", doctorReviewListPage.get(da).getCreateTime().getTime());
		        }
		        else {
		        	ma.put("createTime", "");
				       
		        }
		        if(null!=doctorReviewListPage.get(da).getExpectBirthDate()) {
		        ma.put("expectBirthDate", doctorReviewListPage.get(da).getExpectBirthDate().getTime());
		        }
		        else {
		        	ma.put("expectBirthDate", "");
		        }
	        	ar.add(ma);
	        	
	        }
	        }
	   
	        return ar;
	}
	
    public static Map<String, Object> beanToMap(Object obj) { 
        Map<String, Object> params = new HashMap<String, Object>(0); 
        try { 
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) { 
                String name = descriptors[i].getName(); 
                if (!"class".equals(name)) { 
                	if(null==propertyUtilsBean.getNestedProperty(obj, name)){
                		 params.put(name, ""); 
                	}
                	else {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                	}
                } 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return params; 
}


	

}
