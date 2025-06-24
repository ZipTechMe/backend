package com.jybeomss1.realestateauction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RealEstateAuctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealEstateAuctionApplication.class, args);
    }

}
