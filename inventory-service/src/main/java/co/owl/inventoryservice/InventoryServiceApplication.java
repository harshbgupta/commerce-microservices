package co.owl.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    //Run it for only first time to store some data in DB, As many times it runs following entries will be created in DB
    /*@Bean
    public CommandLineRunner loaData(InventoryRepository inventoryRepository) {
        return args -> {
            Inventory inventory1 = Inventory.builder()
                    .skuCode("iPhone_13")
                    .quantity(100)
                    .build();

            Inventory inventory2 = Inventory.builder()
                    .skuCode("iPhone_13_red")
                    .quantity(0)
                    .build();

            inventoryRepository.save(inventory1);
            inventoryRepository.save(inventory2);
        };
    }*/

}
