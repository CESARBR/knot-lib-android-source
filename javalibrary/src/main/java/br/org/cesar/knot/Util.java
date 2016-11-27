package br.org.cesar.knot;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Util {

    private static final Gson sGson = new Gson();

    private Util() {
    }

    public static <T> void fromJson(T obj, String json, Class<T> clazz) {
        T jsonObject = sGson.fromJson(json, clazz);
        try {
            Field[] fields = jsonObject.getClass().getDeclaredFields();
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                try {
                    if (!Modifier.isTransient(field.getModifiers())) {
                        field.setAccessible(true);
                        field.set(obj, field.get(jsonObject));
                    }
                } finally {
                    field.setAccessible(accessible);
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println("Unable to parse from json: " + json);
        }
    }

    public static <E extends ThingDevice<? extends ThingData>> void fromJson2(E device, String json) {
        E jsonObject = sGson.fromJson(json, new TypeToken<E>() {
        }.getType());
        try {
            Field[] fields = jsonObject.getClass().getDeclaredFields();
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                try {
                    if (!Modifier.isTransient(field.getModifiers())) {
                        field.setAccessible(true);
                        field.set(device, field.get(jsonObject));
                    }
                } finally {
                    field.setAccessible(accessible);
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println("Unable to parse from json: " + json);
        }
    }

    public static <T> void fromJson(T obj, String json) {
        T jsonObject = (T) sGson.fromJson(json, obj.getClass());
        try {
            Field[] fields = jsonObject.getClass().getDeclaredFields();
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                try {
                    if (!Modifier.isTransient(field.getModifiers())) {
                        field.setAccessible(true);
                        field.set(obj, field.get(jsonObject));
                    }
                } finally {
                    field.setAccessible(accessible);
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println("Unable to parse from json: " + json);
        }
    }

    public static String toJson(Object obj) {
        return sGson.toJson(obj);
    }
}
