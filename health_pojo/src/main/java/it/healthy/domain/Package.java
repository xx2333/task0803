package it.healthy.domain;

import java.io.Serializable;
import java.util.List;

public class Package implements Serializable {
 private Integer id          ;
 private String name        ;
 private String code        ;
 private String helpCode    ;
 private String sex         ;
 private String age         ;
 private Float price       ;
 private String remark      ;
 private String attention   ;
 private String img         ;
 private List<CheckGroup> checkGroups;//体检套餐对应的检查组，多对多关系

    public List<CheckGroup> getCheckGroups() {
        return checkGroups;
    }

    public void setCheckGroups(List<CheckGroup> checkGroups) {
        this.checkGroups = checkGroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Package() {
    }

    public Package(Integer id, String name, String code, String helpCode, String sex, String age, Float price, String remark, String attention, String img, List<CheckGroup> checkGroups) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.helpCode = helpCode;
        this.sex = sex;
        this.age = age;
        this.price = price;
        this.remark = remark;
        this.attention = attention;
        this.img = img;
        this.checkGroups = checkGroups;
    }
}
