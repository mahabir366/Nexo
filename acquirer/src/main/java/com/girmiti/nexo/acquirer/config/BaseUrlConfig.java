package com.girmiti.nexo.acquirer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@Component
@ConfigurationProperties("base.url")
public class BaseUrlConfig {

  private String zuul;

  private String acquirerService;

}
