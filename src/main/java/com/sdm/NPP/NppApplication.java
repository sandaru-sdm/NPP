package com.sdm.NPP;

import com.sdm.NPP.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NppApplication implements CommandLineRunner {
	private final UserService userService;

	public NppApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(NppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userService.createDefaultAdmin();
	}
}
