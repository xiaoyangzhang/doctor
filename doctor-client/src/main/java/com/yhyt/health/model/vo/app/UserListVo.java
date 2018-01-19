package com.yhyt.health.model.vo.app;

import java.io.Serializable;

/**
 * 患者app端使用@ 获取医生列表使用的entity
 * @version 1.0
 * @author wangzhan
 * date：2017－12-07
 */
public class UserListVo implements Serializable {

    /**
     * Id
     */
    private Long userId;

    /**
     * 用户头像
     */
    private String headImage;

    /**
     * 职称 患者为空
     */
    private String title;

    /**
     * 类型1医生 2患者
     */
    private String type;

    /**
     * 用户名
     */
    private String realName;

    /**
     * 是否管理员1:否2是
     */
    private String isAdmin;



    /**
     *
     * @return Id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     *
     * @return 用户头像
     */
    public String getHeadImage() {
        return headImage;
    }

    /**
     *
     * @return 职称 患者为空
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return 类型1医生 2患者
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return 用户名
     */
    public String getRealName() {
        return realName;
    }

    /**
     *
     * @return 是否管理员1:否2是
     */
    public String getIsAdmin() {
        return isAdmin;
    }

    /**
     * Id
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 用户头像
     * @param headImage
     */
    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    /**
     * 职称 患者为空
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 类型1医生 2患者
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 用户名
     * @param realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 是否管理员1:否2是
     * @param isAdmin
     */
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
}
