package com.reflection.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionConstructor {
    public static void parse(Class cls, Object obj, JSONObject jsonObject) {
        Field[] fields = cls.getDeclaredFields();

        if (fields.length > 0) {
            for (Field field : fields) {
                try {

                    String key;
                    SerialName serialName = field.getAnnotation(SerialName.class);

                    if (serialName != null) {
                        key = serialName.value();
                    } else {
                        key = field.getName();
                    }

                    final Class type = field.getType();
                    field.setAccessible(true);


                    if (type.isAssignableFrom(String.class)) {
                        field.set(obj, JSONParse.getString(jsonObject, key));
                    } else if (type.isAssignableFrom(int.class)) {
                        field.set(obj, JSONParse.getInt(jsonObject, key, 0));
                    } else if (type.isAssignableFrom(List.class)) {
                        List<Object> instance = new ArrayList<>();
                        parseList(instance, JSONParse.getJSONArray(jsonObject, key));
                        field.set(obj, instance);
                    } else if (type.isAssignableFrom(Long.class)) {
                        field.set(obj, JSONParse.getLong(jsonObject, key, 0));
                    } else {
                        Object instance = type.newInstance();
                        parse(instance.getClass(), instance, JSONParse.getJSONObject(jsonObject, key));
                        field.set(obj, instance);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            }
        } else {
            System.out.println("Not a class with a field.");
        }
    }

    private static void parseList(List<Object> list, JSONArray jsonArray) {
        if (list != null && jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    list.add(jsonArray.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, e.getMessage());
                }
            }
        }
    }

}
