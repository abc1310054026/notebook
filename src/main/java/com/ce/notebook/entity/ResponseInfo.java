package com.ce.notebook.entity;

import com.ce.notebook.ResponseMessage.ResponseMessage;

/**
 * 响应信息
 *
 * @author: ce
 * @create: 2018-10-28 21:00
 **/
public class ResponseInfo {

    private ResponseMessage statusCode;
    private String error;

    public ResponseInfo () {

    }

    public ResponseInfo (ResponseMessage statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseInfo (ResponseMessage statusCode, String error) {
        this(statusCode);
        this.error = error;
    }

    public ResponseMessage getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(ResponseMessage statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "statusCode=" + statusCode +
                ", error='" + error + '\'' +
                '}';
    }
}
