package com.reflection.parser;

package com.reflection.vo.BaseResponse;

public class ParsingFactory<T extends BaseResponse> {

    public ParsingFactory() {}

    public T create(Class<T> cls, String jsonString) {
        T t = null;
        try {
            t = cls.newInstance();
            t.parse(jsonString);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
