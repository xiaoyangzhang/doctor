package com.yhyt.health.mapper;

import com.yhyt.health.model.DialogAppointmentNotice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DialogAppointmentNoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DialogAppointmentNotice record);

    int insertSelective(DialogAppointmentNotice record);

    DialogAppointmentNotice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DialogAppointmentNotice record);

    int updateByPrimaryKey(DialogAppointmentNotice record);

    DialogAppointmentNotice selectDialogAppointmentNotice(Map<String, Object> map);

    int updateDialogAppointmentNoticeState(Map<String, Object> map);

    List<DialogAppointmentNotice> selectByUserIdForDoctor(@Param("doctorId")Long doctorId,@Param("roomId") String roomId,@Param("rows") int rows);
    List<DialogAppointmentNotice> selectByUserIdForPatient(@Param("roomId") String roomId,@Param("rows") int rows);
}