package com.yhyt.health.mapper;

import com.yhyt.health.model.Dialog;
import com.yhyt.health.model.vo.DepartmentPatientVo;
import com.yhyt.health.model.vo.DialogRecordVo;
import com.yhyt.health.model.vo.app.UserListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DialogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dialog record);

    int insertSelective(Dialog record);

    Dialog selectByPrimaryKey(Long id);

    Dialog selectByRoomId(String roomId);

    int updateByPrimaryKeySelective(Dialog record);

    int updateByPrimaryKey(Dialog record);

    Dialog queryBydeptIdAndpatientId(Long departmentId, Long patientId);

    List<DialogRecordVo> querypatientRecords(Map<String,Object> map);
    
    List<Dialog> findPersistableList(Map<String,Object> params);
    
    int updateRoom(Dialog record);

    /**
     * 根据传入的字段名精确获取dialog
     * @param map
     * @return
     */
    Dialog findDialogByAppointFiel(Map<String, Object> map);

    /**
     * 根据房间id获取患者信息
     * @param roomId
     * @return
     */
    UserListVo getUserListVoByRoomId(final @Param("roomId") String roomId);

    DepartmentPatientVo getDialogByTaskId(@Param("taskId") Long taskId);


}