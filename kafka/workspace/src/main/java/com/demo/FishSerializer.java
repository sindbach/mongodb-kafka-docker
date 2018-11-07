package com.demo;

import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class FishSerializer implements Serializer {
    @Override public void configure(Map map, boolean b) {
    }
  
    @Override public byte[] serialize(String arg0, Object arg1) {
      byte[] retVal = null;
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        retVal = objectMapper.writeValueAsString(arg1).getBytes();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return retVal;
    }

    @Override public void close() {
    }
  
  }