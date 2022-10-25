package com.example.firstAndroid.functions.logic.purefunction;

import java.util.HashMap;
import java.util.WeakHashMap;

public class JavaDemo {
    public static void mainDDD() {
        hashMap();
        System.out.println("***************分割线****************");
        WeakHashMap();
    }

    private static void hashMap(){
        HashMap<String,String> map = new HashMap<>();
        String key = new String("HashMapKey");
        String value = "HashMapValue";
        map.put(key,value);
        System.out.println("第一次打印："+map);

        key = null;
        System.out.println("第二次打印，key引用赋值为null:"+map);

        WeakRef.INSTANCE.myGc();
        System.out.println("第三次打印，GC后："+map+" size:"+map.size());
    }

    private static void WeakHashMap(){
        WeakHashMap<String,String> map = new WeakHashMap<>();
        String key = new String("WeakHashMapKey");
        String value = "WeakHashMapValue";
        map.put(key,value);
        System.out.println("第一次打印："+map);

        key = null;
        System.out.println("第二次打印，key引用赋值为null:"+map);

        WeakRef.INSTANCE.myGc();
        System.out.println("第三次打印，GC后："+map+" size:"+map.size());
    }
}
