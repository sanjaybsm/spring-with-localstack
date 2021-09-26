package com.sanjay.springwithlocalstack.springwithlocalstack;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
@ConfigurationProperties("aws")
@Data
public class AWSConfiguration {
	URI dynomoUri;
}
