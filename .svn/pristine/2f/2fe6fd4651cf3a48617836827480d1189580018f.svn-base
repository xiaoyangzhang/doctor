package com.yhyt.health.result;

import com.yhyt.health.constant.ResultCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * app返回结果类
 *
 * @author gsh
 */
public class AppResult implements Serializable {

    private static final long serialVersionUID = -5532491536749699416L;
    private ResultStatus status = new ResultStatus();
    private ResultCode resultCode;
    private Map<String, Object> body = new HashMap<String, Object>();

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.status = new ResultStatus(resultCode.val(), resultCode.msg());
    }
}
