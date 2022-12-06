package com.example.firstAndroid.functions.logic.testHermes;

import xiaofei.library.hermes.annotation.ClassId;
import xiaofei.library.hermes.annotation.MethodId;

//@ClassId("Singleton")
//public interface ISingleton {
//    @MethodId("setValue")
//    void setValue(String value);
//
//    @MethodId("getValue")
//    String getValue();
//}


@ClassId("Singleton")
public class Singleton {
    private static Singleton sInstance;
    private String value;

    private Singleton(){}

    public static Singleton getInstance(){
        if(sInstance==null){
            synchronized (Singleton.class){
                if(sInstance==null){
                    sInstance = new Singleton();
                }
            }
        }
        return sInstance;
    }

    @MethodId("setValue")
    public void setValue(String value) {
        this.value = value;
    }

    @MethodId("getValue")
    public String getValue() {
        return value;
    }
}
