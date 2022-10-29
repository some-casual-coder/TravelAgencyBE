package com.project.TravelAgency;

import com.project.TravelAgency.entity.ERole;
import com.project.TravelAgency.entity.Role;
import com.project.TravelAgency.service.RoleService;
import com.project.TravelAgency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashSet;

@SpringBootApplication
@EnableAsync
public class TravelAgencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelAgencyApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService){
		return args -> {
			roleService.createRole(new Role(null, ERole.ROLE_USER));
			roleService.createRole(new Role(null, ERole.ROLE_HOST));
			roleService.createRole(new Role(null, ERole.ROLE_ADMIN));
			roleService.createRole(new Role(null, ERole.ROLE_SUPER_ADMIN));
//
//			userService.registerUser(new User(null,"John", "Doe", "doe@gmail.com", "1234", 673892, false, new HashSet<>()));
//			userService.registerUser(new User(null,"Peter", "Rabbit", "rabbit@gmail.com", "1234", 341276, false, new HashSet<>()));
//			userService.registerUser(new User(null,"Gwen", "Stacy", "gwen@gmail.com", "1234", 928412, false, new HashSet<>()));
//			userService.registerUser(new User(null,"Mary", "Poppins", "mary@gmail.com", "1234", 641243, false, new HashSet<>()));
//
//			roleService.addRoleToUser("doe@gmail.com", ERole.ROLE_USER);
//			roleService.addRoleToUser("rabbit@gmail.com", ERole.ROLE_USER);
//			roleService.addRoleToUser("rabbit@gmail.com", ERole.ROLE_HOST);
//			roleService.addRoleToUser("gwen@gmail.com", ERole.ROLE_USER);
//			roleService.addRoleToUser("gwen@gmail.com", ERole.ROLE_ADMIN);
//			roleService.addRoleToUser("mary@gmail.com", ERole.ROLE_USER);
//			roleService.addRoleToUser("mary@gmail.com", ERole.ROLE_SUPER_ADMIN);
		};
	}

}
