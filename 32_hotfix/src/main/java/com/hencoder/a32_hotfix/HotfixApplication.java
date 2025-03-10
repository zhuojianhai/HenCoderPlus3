package com.hencoder.a32_hotfix;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.PathClassLoader;

public class HotfixApplication extends Application {
    File apk;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        apk = new File(getCacheDir() + "/hotfix.dex");
        if (apk.exists()) {
            try {
                ClassLoader classLoader = getClassLoader();
                Class loaderClass = BaseDexClassLoader.class;
                Field pathListField = loaderClass.getDeclaredField("pathList");
                pathListField.setAccessible(true);
                Object pathListObject = pathListField.get(classLoader); // getClassLoader().pathList
//                DexPathList  这个类是 hide的，因此获取类名也只能通过反射
                Class pathListClass = pathListObject.getClass();
                Field dexElementsField = pathListClass.getDeclaredField("dexElements");
                dexElementsField.setAccessible(true);
                Object dexElementsObject = dexElementsField.get(pathListObject); // getClassLoader().pathList.dexElements

                // classLoader.pathList.dexElements = ???;  新的补丁包，将修复的dex放在 最前面
                PathClassLoader newClassLoader = new PathClassLoader(apk.getPath(), null);
                Object newPathListObject = pathListField.get(newClassLoader); // newClassLoader.pathList
                Object newDexElementsObject = dexElementsField.get(newPathListObject); // newClassLoader.pathList.dexElements

                int oldLength = Array.getLength(dexElementsObject);
                int newLength = Array.getLength(newDexElementsObject);
                Object concatDexElementsObject = Array.newInstance(dexElementsObject.getClass().getComponentType(), oldLength + newLength);
                for (int i = 0; i < newLength; i++) {
                    Array.set(concatDexElementsObject, i, Array.get(newDexElementsObject, i));
                }
                for (int i = 0; i < oldLength; i++) {
                    Array.set(concatDexElementsObject, newLength + i, Array.get(dexElementsObject, i));
                }

                dexElementsField.set(pathListObject, concatDexElementsObject);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
