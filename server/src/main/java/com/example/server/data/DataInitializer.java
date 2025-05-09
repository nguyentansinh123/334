package com.example.server.data;

import com.example.server.enums.Roles;
import com.example.server.model.Role;
import com.example.server.model.User;
import com.example.server.repository.RoleRepository;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<Roles> defaultRoles = Set.of(Roles.ADMIN, Roles.USER, Roles.CLIENT, Roles.WORKER);
        createDefaultRoleIfNotExits(defaultRoles);
        createDefaultUserIfNotExits();
        createDefaultAdminIfNotExits();
        createDefaultClientIfNotExits();
        createDefaultWorkerIfNotExits();
    }

    private void createDefaultUserIfNotExits() {
        Role userRole = roleRepository.findByName(Roles.USER).orElseThrow();
        for (int i = 1; i <= 3; i++) {
            String defaultEmail = "user" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default user " + i + " created successfully.");
        }
    }


    private void createDefaultAdminIfNotExits() {
        Role adminRole = roleRepository.findByName(Roles.ADMIN).orElseThrow();
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin " + i + " created successfully.");
        }
    }

    private void createDefaultClientIfNotExits() {
        Role clientRole = roleRepository.findByName(Roles.CLIENT).orElseThrow();
        for (int i = 1; i <= 3; i++) {
            String defaultEmail = "client" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User client = new User();
            client.setFirstName("Client");
            client.setLastName("Client" + i);
            client.setEmail(defaultEmail);
            client.setPassword(passwordEncoder.encode("123456"));
            client.setRoles(Set.of(clientRole));
            userRepository.save(client);
            System.out.println("Default client " + i + " created successfully.");
        }
    }

    private void createDefaultWorkerIfNotExits() {
        Role workerRole = roleRepository.findByName(Roles.WORKER).orElseThrow();
        for (int i = 1; i <= 3; i++) {
            String defaultEmail = "worker" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User worker = new User();
            worker.setFirstName("Worker");
            worker.setLastName("Worker" + i);
            worker.setEmail(defaultEmail);
            worker.setPassword(passwordEncoder.encode("123456"));
            worker.setRoles(Set.of(workerRole));
            userRepository.save(worker);
            System.out.println("Default worker " + i + " created successfully.");
        }
    }



    private void createDefaultRoleIfNotExits(Set<Roles> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new)
                .forEach(roleRepository::save);
    }
}