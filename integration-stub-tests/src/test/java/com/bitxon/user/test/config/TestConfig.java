package com.bitxon.user.test.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {"com.bitxon.user.test"})
public class TestConfig {
}
