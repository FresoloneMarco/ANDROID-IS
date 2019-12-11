package it.porting.android_is.utility;

import java.util.HashMap;
import java.util.Map;

public class LazyInizializedSingleton {

    private static LazyInizializedSingleton instance;
    private static Map<String, Object> user = new HashMap<>();

    private LazyInizializedSingleton(){}

    public static LazyInizializedSingleton getInstance(){
        if(instance == null){
            instance = new LazyInizializedSingleton();
        }
        return instance;
    }

    public void setUser(Map<String, Object> user){
        this.user = user;
    }

    public Map<String, Object> getUser(){
        return this.user;
    }
}