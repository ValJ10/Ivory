package com.iter.ivory;

import java.util.*;

public class User {

    String name;
    ArrayList<Vaccines> vaccinations = new ArrayList<>();

    User(){
    }

    User(String name, ArrayList<Vaccines> vaccinations){
        this.name = name;
        this.vaccinations = vaccinations;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ArrayList<Vaccines> getVaccinations(){
        return vaccinations;
    }

    public void setVaccinations(ArrayList<Vaccines> vaccinations){
        this.vaccinations = vaccinations;
    }

    public void addVaccinations(Vaccines v){
        vaccinations.add(v);
    }
}
