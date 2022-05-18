package com.sonle.azuresso;

import com.sonle.azuresso.user.domain.UserRole;
import com.sonle.azuresso.user.entity.User;
import com.sonle.azuresso.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class AzureSsoApplication implements CommandLineRunner {

	private final UserService userService;

	public AzureSsoApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(AzureSsoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!userService.existsByUsername("admin")) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword("admin");
			admin.setEmail("admin@gmail.com");
			admin.setUserRoles(Arrays.asList(UserRole.ROLE_ADMIN));
			userService.singup(admin);
		}

		if (!userService.existsByUsername("client")) {
			User client = new User();
			client.setUsername("client");
			client.setPassword("client");
			client.setEmail("client@gmail.com");
			client.setUserRoles(Arrays.asList(UserRole.ROLE_CLIENT));
			userService.singup(client);
		}
	}
}
