package com.yhyt.health.util;

import com.yhyt.health.mapper.SysConfigMapper;
import com.yhyt.health.model.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.util.List;

@Component
public final class MD5Util {

    private static String salt = "salt";

    private static MD5Util md5Util;

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @PostConstruct
    public void init() {
        md5Util = this;
        md5Util.sysConfigMapper=this.sysConfigMapper;
    }

    public final static String MD5Password(String s) {
        List<SysConfig> sysConfig = md5Util.sysConfigMapper.selectSysConfig(salt);
        return MD5(sysConfig.get(0).getSalt1() + s + sysConfig.get(0).getSalt2());
    }


	
	public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}