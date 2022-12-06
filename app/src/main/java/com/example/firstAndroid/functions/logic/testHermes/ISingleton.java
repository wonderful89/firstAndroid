package com.example.firstAndroid.functions.logic.testHermes;

import xiaofei.library.hermes.annotation.ClassId;
import xiaofei.library.hermes.annotation.MethodId;

@ClassId("Singleton")
public interface ISingleton {
    @MethodId("setValue")
    void setValue(String value);

    @MethodId("getValue")
    String getValue();
}

