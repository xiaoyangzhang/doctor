package com.yhyt.health.util;

import com.yhyt.health.model.vo.app.PatientDiagnosePicsCaseVo;
import com.yhyt.health.model.vo.app.PatientDiagnosePicsVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gsh
 * @create 2018-01-03 15:05
 **/
public class PatientDiagnosePicsUtil {

    //转化图片信息
    public static List<PatientDiagnosePicsCaseVo> getPatientDiagnsePics(List<PatientDiagnosePicsCaseVo> patientDiagnosePicsCaseVos) {
        if (null == patientDiagnosePicsCaseVos || patientDiagnosePicsCaseVos.size() <= 0) {
            return new ArrayList<PatientDiagnosePicsCaseVo>();
        }
        //list整理
        for (PatientDiagnosePicsCaseVo patientDiagnosePicsCaseVo : patientDiagnosePicsCaseVos) {
            List<PatientDiagnosePicsVo> patientDiagnosePicsList = new ArrayList<PatientDiagnosePicsVo>();
            if (null != patientDiagnosePicsCaseVo.getPatientDiagnosePicsVos() && patientDiagnosePicsCaseVo.getPatientDiagnosePicsVos().size() > 0) {
                List<PatientDiagnosePicsVo> patientDiagnosePicsVos=patientDiagnosePicsCaseVo.getPatientDiagnosePicsVos();
                for (PatientDiagnosePicsVo patientDiagnosePicsVo : patientDiagnosePicsVos) {
                    if (patientDiagnosePicsVo.getPicUrl().indexOf(",") > 0) {
                        String[] urls = patientDiagnosePicsVo.getPicUrl().split(",");
                        for (String str : urls) {
                            PatientDiagnosePicsVo p = new PatientDiagnosePicsVo();
                            p.setPicUrl(str);
                            p.setId(patientDiagnosePicsVo.getId());
                            p.setCreateTime(patientDiagnosePicsCaseVo.getCreateDate());
                            patientDiagnosePicsList.add(p);
                        }
                    }else{
                        PatientDiagnosePicsVo p = new PatientDiagnosePicsVo();
                        p.setPicUrl(patientDiagnosePicsVo.getPicUrl());
                        p.setId(patientDiagnosePicsVo.getId());
                        p.setCreateTime(patientDiagnosePicsCaseVo.getCreateDate());
                        patientDiagnosePicsList.add(p);
                    }

                }
            }
            patientDiagnosePicsCaseVo.setPatientDiagnosePicsVos(patientDiagnosePicsList);
        }
        return patientDiagnosePicsCaseVos;
    }
}
