package com.ywh.LoRaWANServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class LoRaWANServerApplication extends SpringBootServletInitializer  {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(LoRaWANServerApplication.class);
  }

  public static void main(String args[]) {
    SpringApplication.run(LoRaWANServerApplication.class, args);
  }
}
