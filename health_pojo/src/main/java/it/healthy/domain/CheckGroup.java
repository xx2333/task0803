package it.healthy.domain;

import java.io.Serializable;
import java.util.List;

public class CheckGroup implements Serializable {
   private Integer id         ;
   private String code       ;
   private String name       ;
   private String helpCode   ;
   private String sex        ;
   private String remark     ;
   private String attention  ;
   private List<CheckItem> checkItems;

    public List<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }

    public CheckGroup() {
    }

    public CheckGroup(Integer id, String code, String name, String helpCode, String sex, String remark, String attention) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.helpCode = helpCode;
        this.sex = sex;
        this.remark = remark;
        this.attention = attention;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHelpCode() {
        return helpCode;
    }

    public void setHelpCode(String helpCode) {
        this.helpCode = helpCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }
}
