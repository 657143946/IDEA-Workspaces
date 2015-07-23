package com.shulianxunying.haierLuceneWeb.controller.haier;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ChrisLee.
 */
public class Result {
    private String uid;
    private String data_source;
    private String user_img;
    private String update_time;
    private String location;
    private String recent_work;
    private String description;
    private String target_work_area;
    private String target_salary;
    private String work_experience;
    private String degree;
    private String target_work_type;
    private List<String> tags;
    private String sex;
    private String user_name;
    private float match;
    private String category;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getData_source() {
        return data_source;
    }

    public void setData_source(String data_source) {
        this.data_source = data_source;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRecent_work() {
        return recent_work;
    }

    public void setRecent_work(String recent_work) {
        this.recent_work = recent_work;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTarget_work_area() {
        return target_work_area;
    }

    public void setTarget_work_area(String target_work_area) {
        this.target_work_area = target_work_area;
    }

    public String getTarget_salary() {
        return target_salary;
    }

    public void setTarget_salary(String target_salary) {
        this.target_salary = target_salary;
    }

    public String getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(String work_experience) {
        this.work_experience = work_experience;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getTarget_work_type() {
        return target_work_type;
    }

    public void setTarget_work_type(String target_work_type) {
        this.target_work_type = target_work_type;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public float getMatch() {
        return match;
    }

    public void setMatch(float match) {
        this.match = match;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Result(){

    }
    public Result(DBObject resume){
        uid = (String)resume.get("cv_id");
        data_source = (String)resume.get("cv_origin");
        user_img = (String)resume.get("user_img");
        update_time = (String)resume.get("last_refresh");
        location = (String)resume.get("living");
        recent_work = (String)resume.get("recent_work");
        description = (String)resume.get("self_eval");
        try{
            target_work_area = (String)((BasicDBList)resume.get("exp_location")).get(0);
        } catch (Exception e){
            target_work_area = "";
        }
        target_salary = (String)resume.get("exp_m_salary");
        work_experience = (String)resume.get("work_time");
        degree = (String)resume.get("academic_name");
        target_work_type = "";
        tags = new LinkedList<>();
        sex = (String)resume.get("gender");
        user_name = (String)resume.get("name");
        category = (String)resume.get("category");
    }
}
