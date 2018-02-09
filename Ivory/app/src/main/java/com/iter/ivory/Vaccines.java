package com.iter.ivory;

import java.util.Date;

import java.util.Calendar;

public class Vaccines {
    String vaccineName;
    String subName;
    Date startDate;
    Date remindDate;

    static Date calculateRemindDate(Date startDate, int month, int year){
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.YEAR,year);
        cal.add(Calendar.MONTH, month);

        return cal.getTime();
    }

    Vaccines(){

    }

    Vaccines(String vaccineName, String subname, int month, int year){
        this.subName = subname;
        this.vaccineName = vaccineName;
        this.startDate = new Date();
        this.remindDate = calculateRemindDate(startDate, month, year);
    }

    public String getVaccineName(){
        return vaccineName;
    }

    public void setVaccineName(String vaccineName){
        this.vaccineName = vaccineName;
    }

    public String getSubName(){
        return subName;
    }

    public void setSubName(String subName){
        this.subName = subName;
    }

    public Date getStartDate(){
        return startDate;
    }

    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }

    public Date getRemindDate(){
        return remindDate;
    }
    public void setRemindData(Date remindDate){
        this.remindDate = remindDate;
    }


}
