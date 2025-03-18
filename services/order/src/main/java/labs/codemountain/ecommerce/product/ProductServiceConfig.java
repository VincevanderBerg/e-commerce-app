package labs.codemountain.ecommerce.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductServiceConfig {

    @Bean
    public final RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
