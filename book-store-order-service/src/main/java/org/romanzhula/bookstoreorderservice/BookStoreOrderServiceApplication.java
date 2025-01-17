package org.romanzhula.bookstoreorderservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;


@SpringBootApplication
public class BookStoreOrderServiceApplication implements CommandLineRunner {

	@Autowired
	Environment env;

	public String getAclToken() {
		return env.getProperty("acl-token");
	}

	public static void main(String[] args) {
		SpringApplication.run(BookStoreOrderServiceApplication.class, args);
	}

	// check acl token from Vault for Consul
	@Override
	public void run(String... args) throws Exception {
		Logger logger = LoggerFactory.getLogger(WavefrontProperties.Application.class);

		logger.info("Consul ACL Token: {}", getAclToken());
	}

}
