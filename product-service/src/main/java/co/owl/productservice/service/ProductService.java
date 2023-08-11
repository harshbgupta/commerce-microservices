package co.owl.productservice.service;

import co.owl.productservice.model.Product;
import co.owl.productservice.model.dto.ProductRequest;
import co.owl.productservice.model.dto.ProductResponse;
import co.owl.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    //    @Autowired
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }


    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepository.findAll();
        log.info("Products fetched of size {}", products.size());
        //products.stream().map(product-> mapToProductResponse(product)); //instead of lambda, I am using method reference, a new one
        return products.stream().map(this::mapToProductResponse)
                .toList();

    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
