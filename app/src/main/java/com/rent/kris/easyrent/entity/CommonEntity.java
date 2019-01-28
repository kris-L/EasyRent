package com.rent.kris.easyrent.entity;

/**
 * Created by lsz  on 2019-01-28
 */
public class CommonEntity {

    /**
     * result : {"status":1}
     * msg : 发送成功
     */
    private ResultEntity result;
    private String msg;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultEntity getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public class ResultEntity {
        /**
         * status : 1
         */
        private int status;

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }
}
