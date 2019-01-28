package com.rent.kris.easyrent.entity;

public class UserProfile {

    /**
     * username : admin321
     * userid : 4
     * key : c258147d49c5fb1b348925c0c31ac8c0
     */

   public String username;
   public int userid;
   public String key;
   public int state;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
