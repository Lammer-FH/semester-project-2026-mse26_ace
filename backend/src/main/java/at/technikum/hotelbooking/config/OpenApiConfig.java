package at.technikum.hotelbooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI hotelBookingOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("ACE Escapes Hotel Booking API")
                .description("REST API for the Boutique Hotel Technikum booking app")
                .version("1.0.0")
                .contact(new Contact()
                    .name("ACE Escapes Team")));
    }
}
