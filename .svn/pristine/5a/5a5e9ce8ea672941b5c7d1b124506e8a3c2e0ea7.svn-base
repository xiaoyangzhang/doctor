package com.yhyt.health.util;

import com.alibaba.fastjson.JSON;
import com.yhyt.health.model.vo.XmppMessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * xmpp消息发送
 *
 * @author gsh
 * @create 2017-10-16 14:26
 **/
@Component
public class XmppMessageUtil {

    static Logger logger = LoggerFactory.getLogger(XmppMessageUtil.class);

    private static XmppMessageUtil xmppMessageUtil;

    @Resource(name = "loadBalanced")
    private RestTemplate loadBalanced;

    @PostConstruct
    public void init() {
        xmppMessageUtil=this;
        xmppMessageUtil.loadBalanced = this.loadBalanced;
    }

    public static void sendXmppMessage(String userFromId, String password, String userId, XmppMessageBody body) {
        try {
            body.setTimeSend(new Date());
            Map<String, Object> mapPara = new HashMap<String, Object>();
            mapPara.put("userId", userId);
            mapPara.put("body", JSON.toJSONString(body));
            mapPara.put("userFromId", userFromId);
            mapPara.put("password", password);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
            com.yhyt.health.spring.AppResult result = xmppMessageUtil.loadBalanced.postForObject("http://dialog/dialog/sendMessage?userId={userId}&body={body}", httpEntity, com.yhyt.health.spring.AppResult.class, mapPara);
            if (null != result && "200".equals(result.getStatus().getCode())) {
                logger.info("推送下线成功");
            }
        } catch (Exception e) {
            logger.error("发送xmpp失败，用户id"+userId,e);
        }
    }



}
