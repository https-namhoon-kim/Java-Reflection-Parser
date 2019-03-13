package com.reflection.vo;

import com.reflection.parser.ReflectionConstructor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class BaseResponse {

  public BaseResponse() {}

  public void parse(String jsonString) {
      try {
          JSONObject jsonObject = new JSONObject(jsonString);
          parse(jsonObject);
      } catch (JSONException e) {
          e.printStackTrace();
      }
  }

  public void parse(JSONObject object) {
      parse(getClass(), object);
  }

  private void parse(Class cls, JSONObject object) {
      if (cls == BaseResponse.class) {
          ReflectionConstructor.parse(cls, this, object);
      } else {
          parse(cls.getSuperclass(), object);
          ReflectionConstructor.parse(cls, this, object);
      }
  }

}
