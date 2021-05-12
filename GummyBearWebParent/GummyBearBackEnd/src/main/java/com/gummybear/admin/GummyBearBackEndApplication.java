package com.gummybear.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.gummybear.common.entity", "com.gummybear.admin.user", "com.gummybear.site.time"})
public class GummyBearBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(GummyBearBackEndApplication.class, args);
	}

}
