package com.anuj.ecommerce.service;

import com.anuj.ecommerce.exception.ProductException;
import com.anuj.ecommerce.model.Category;
import com.anuj.ecommerce.model.Product;
import com.anuj.ecommerce.repository.CategoryRepository;
import com.anuj.ecommerce.repository.ProductRepository;
import com.anuj.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,UserService userService,CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {

        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());

        if (topLevel == null){
            Category topLevelcategory = new Category();
            topLevelcategory.setName(req.getTopLevelCategory());
            topLevelcategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelcategory);

        }
        if (secondLevel == null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);

        }
        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());


        if (thirdLevel == null){
            Category thirdLevelcategory = new Category();
            thirdLevelcategory.setName(req.getThirdLevelCategory());
            thirdLevelcategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelcategory);

        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountedPercent(req.getDiscountPercent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return  savedProduct;

    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Prod";
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {

        return null;
    }

    @Override
    public Product findProductById(Long id) throws ProductException {

        return null;
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return List.of();
    }

    @Override
    public Page<Product> getAllProducts(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        return null;
    }
}
