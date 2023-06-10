package com.lab.application;

import com.lab.application.entity.Apartment;
import com.lab.application.entity.Client;
import com.lab.application.entity.Resources;
import com.lab.application.entity.User;
import com.lab.application.repository.ApartmentRepository;
import com.lab.application.repository.ClientRepository;
import com.lab.application.repository.ResourcesRepository;
import com.lab.application.repository.UserRepository;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "lab2")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(ApartmentRepository apartmentRepository, UserRepository users, PasswordEncoder encoder, ClientRepository clientRepository, ResourcesRepository resourcesRepository) {
        return args -> {
            users.save(new User(1L, "user", encoder.encode("password"), "ROLE_ADMIN", "Enis", "Halilaj", LocalDate.of(1998, 1, 1), "test@gmail.com"));

            apartmentRepository.save(new Apartment(1L, "Berlin", 1200, "Germany", 12L, true));
            apartmentRepository.save(new Apartment(2L, "Berlin", 1200, "Germany", 12L, true));
            apartmentRepository.save(new Apartment(3L, "Japan", 1200, "Japan", 12L, true));
            apartmentRepository.save(new Apartment(4L, "Japan", 1200, "Japan", 12L, true));
            apartmentRepository.save(new Apartment(5L, "New York", 1200, "US", 12L, true));
            apartmentRepository.save(new Apartment(6L, "New York", 1200, "US", 12L, true));
            apartmentRepository.save(new Apartment(7L, "New York", 1200, "US", 12L, false));
            apartmentRepository.save(new Apartment(8L, "New York", 1200, "US", 12L, false));
            apartmentRepository.save(new Apartment(9L, "Berlin", 1200, "Germany", 12L, false));
            apartmentRepository.save(new Apartment(10L, "Japan", 1200, "Japan", 12L, false));
            apartmentRepository.save(new Apartment(11L, "London", 1200, "UK", 12L, true));
            apartmentRepository.save(new Apartment(12L, "London", 1200, "UK", 12L, true));
            apartmentRepository.save(new Apartment(13L, "Chicago", 1200, "US", 12L, true));
            apartmentRepository.save(new Apartment(14L, "Chicago", 1200, "US", 12L, false));
            apartmentRepository.save(new Apartment(15L, "Venezia", 1200, "Italy", 12L, true));
            apartmentRepository.save(new Apartment(16L, "Venezia", 1200, "Italy", 12L, false));

            clientRepository.save(new Client(1L,  "Enis Halilaj", 1200, "Success", "2019-02-21"));
            clientRepository.save(new Client(2L, "Leon Leka", 1200, "Success", "2019-02-21"));
            clientRepository.save(new Client(3L, "Avdi Vrella", 1200, "Success", "2019-02-21"));
            clientRepository.save(new Client(4L,  "Luan Leka", 1200, "Success", "2019-02-21"));
            clientRepository.save(new Client(5L,  "Art Leka", 1200, "Success", "2019-02-21"));
            clientRepository.save(new Client(6L,  "Jon Leka", 1200, "Success", "2019-02-21"));
            clientRepository.save(new Client(7L,  "Meridian Danko", 1200, "Success", "2019-02-21"));
            clientRepository.save(new Client(8L,  "Ali Halilaj", 1200, "Success", "2019-02-21"));
            clientRepository.save(new Client(9L,  "Don Danko", 1200, "Success", "2019-02-21"));
            clientRepository.save(new Client(10L,  "Leke Leka", 1200, "Success", "2019-02-21"));
            clientRepository.save(new Client(11L,  "Ron Halilaj", 1200, "Success", "2019-02-21"));

            resourcesRepository.save(new Resources(1L, 12312L));


        };
    }

}
