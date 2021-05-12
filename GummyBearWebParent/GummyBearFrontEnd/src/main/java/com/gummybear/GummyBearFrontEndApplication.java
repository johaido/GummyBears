package com.gummybear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.gummybear.common.entity", "com.gummybear.admin.user"})
public class GummyBearFrontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(GummyBearFrontEndApplication.class, args);
	}

}
