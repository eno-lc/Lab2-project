package com.lab.application;

import com.lab.application.entity.*;
import com.lab.application.repository.*;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
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
    CommandLineRunner commandLineRunner(ApartmentRepository apartmentRepository, UserRepository users, PasswordEncoder encoder, ClientRepository clientRepository, ResourcesRepository resourcesRepository, ImageCardRepository imageCardRepository) {
        return args -> {
            users.save(new User(1L, "user", encoder.encode("password"), "ROLE_MANAGER", "Enis", "Halilaj", LocalDate.of(1998, 1, 1), "test@gmail.com"));
            users.save(new User(2L, "admin", encoder.encode("password"), "ROLE_ADMIN", "Enis", "Halilaj", LocalDate.of(1998, 1, 1), "test@gmail.com"));

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

            clientRepository.save(new Client(1L,  "Enis Halilaj", 1200, "Success",LocalDate.of(1998, 1, 1)));
            clientRepository.save(new Client(2L,  "Enis Halilaj", 1200, "Success",LocalDate.of(1998, 1, 1)));
            clientRepository.save(new Client(3L,  "Enis Halilaj", 1200, "Success",LocalDate.of(1998, 1, 1)));
            clientRepository.save(new Client(4L,  "Enis Halilaj", 1200, "Success",LocalDate.of(1998, 1, 1)));
            clientRepository.save(new Client(5L,  "Enis Halilaj", 1200, "Success",LocalDate.of(1998, 1, 1)));
            clientRepository.save(new Client(6L,  "Enis Halilaj", 1200, "Success",LocalDate.of(1998, 1, 1)));
            clientRepository.save(new Client(7L,  "Enis Halilaj", 1200, "Success",LocalDate.of(1998, 1, 1)));
            clientRepository.save(new Client(8L,  "Enis Halilaj", 1200, "Success",LocalDate.of(1998, 1, 1)));
            clientRepository.save(new Client(9L,  "Enis Halilaj", 1200, "Success",LocalDate.of(1998, 1, 1)));
            clientRepository.save(new Client(10L,  "Enis Halilaj", 1200, "Success",LocalDate.of(1998, 1, 1)));

            resourcesRepository.save(new Resources(1L, 12312L));

            imageCardRepository.save(new ImageCard(1L, "https://a0.muscache.com/im/pictures/miso/Hosting-546923400311446203/original/4c43b516-a11b-49bb-a2ed-d4b0c8cc34a5.jpeg?im_w=1200", "Penthouse in La Juarez - 34$ a night", "Room in a rental unit hosted by Enis"));
            imageCardRepository.save(new ImageCard(2L, "https://a0.muscache.com/im/pictures/miso/Hosting-576599054777073307/original/53e611cc-b827-4cec-850e-c1db1274de43.jpeg?im_w=1200", "Private suite in charming house close to lake/train station - 72$ a night", "Room in a home hosted by All'In Renting"));
            imageCardRepository.save(new ImageCard(3L, "https://a0.muscache.com/im/pictures/2fd67464-8da1-419d-b8f7-dcb840a1be0a.jpg?im_w=1200", "Suite in a Charming Colonial Villa in San Angel - 55$ a night", "Room in a rental unit hosted by Werner"));
            imageCardRepository.save(new ImageCard(4L, "https://a0.muscache.com/im/pictures/miso/Hosting-10989371/original/46c0c87f-d9bc-443c-9b64-24d9e594b54c.jpeg?im_w=1200", "Enjoy historic Valencia and close to the beach. - 44$ a night", "Room in a rental unit hosted by Sagrario"));



        };
    }

}
