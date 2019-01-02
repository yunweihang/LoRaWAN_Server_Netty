package com.ywh.LoRaWANServer.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
  private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
  private static ObjectMapper mapper = new ObjectMapper();

  private JsonUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static ObjectMapper getObjectMapper() {
    return mapper;
  }

  public static <T> T fromJson(String json, Class<T> t) {
    if (json == null) {
      return null;
    } else {
      try {
        return mapper.readValue(json, t);
      } catch (Exception arg2) {
        logger.info("Cannot parse json string to Object. Json: <" + json + ">, Object class: <" + t.getName()
            + ">.", arg2);
        return null;
      }
    }
  }

  public static <T> List<T> toList(String json, Class<T> clazz) throws IOException {
    JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, new Class[]{clazz});
    return (List) mapper.readValue(json, javaType);
  }

  public static <T> T fromMap(Map<?, ?> map, Class<T> t) {
    if (map == null) {
      return null;
    } else {
      try {
        return mapper.readValue(toJsonString(map), t);
      } catch (Exception arg2) {
        logger.info("Cannot parse map to Object. Map: <" + map + ">, Object class: <" + t.getName() + ">.",
            arg2);
        return null;
      }
    }
  }

  public static <T> Map<String, T> toMap(String jsonText, Class<T> clazz) throws IOException {
    JavaType javaType = mapper.getTypeFactory().constructParametricType(Map.class,
        new Class[]{String.class, clazz});
    return (Map) mapper.readValue(jsonText, javaType);
  }

  public static Map toMap(String jsonText) {
    return (Map) fromJson(jsonText, Map.class);
  }

  public static String toJsonString(Object obj) {
    try {
      if (obj != null) {
        return mapper.writeValueAsString(obj);
      }
    } catch (Exception arg1) {
      logger.warn("Cannot convert to json " + obj);
    }

    return "{}";
  }

  public static String toJsonStr(Object obj, boolean ignoreError) {
    try {
      if (obj != null) {
        return mapper.writeValueAsString(obj);
      }
    } catch (Exception arg2) {
      logger.debug("convert to json error for object: {}", obj, arg2);
      if (!ignoreError) {
        throw new IllegalArgumentException("convert to json error for object", arg2);
      }
    }

    return null;
  }

  public static String toJson(Object o) {
    try {
      return mapper.writeValueAsString(o);
    } catch (JsonProcessingException arg1) {
      logger.warn("toJson exception", arg1);
      return null;
    }
  }

  public static <T> T load(String filePath, Class<T> clazz) {
    FileInputStream is = null;

    try {
      is = new FileInputStream(new File(filePath));
    } catch (FileNotFoundException arg3) {
      logger.error(arg3.getMessage());
    }

    return load((InputStream) is, clazz);
  }

  public static <T> T load(InputStream is, Class<T> clazz) {
    try {
      return mapper.readValue(is, clazz);
    } catch (JsonParseException arg2) {
      logger.warn("JsonParseException", arg2);
    } catch (JsonMappingException arg3) {
      logger.warn("JsonMappingException", arg3);
    } catch (IOException arg4) {
      logger.warn("IOException", arg4);
    }

    return null;
  }

  public static <T> T parser(String json, Class<T> clzz) {
    try {
      return mapper.readValue(json, clzz);
    } catch (IOException arg2) {
      logger.error(arg2.getMessage());
      return null;
    }
  }

  public static final boolean isJSONValid(String test) {
    try {
      JSONObject.parseObject(test);
    } catch (JSONException arg3) {
      try {
        JSONObject.parseArray(test);
      } catch (JSONException arg2) {
        return false;
      }
    }

    return true;
  }

  static {
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
}