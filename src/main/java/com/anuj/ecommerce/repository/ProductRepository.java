package com.anuj.ecommerce.repository;

import com.anuj.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM pRODUCT P" + "WHERE (p.category.name = :category OR :category='')" +
            "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice))"+
            "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount)" + "ORDER BY" + "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC,"
    +"CASE EHRN :sort = 'price_high' THEN p.discountedPrice END DESC")
    public List<Product>filterProducts(@Param("category") String category,
                                       @Param("minPrice")Integer minPrice,
                                       @Param("maxPrice")Integer maxPrice,
                                       @Param("minDiscount")Integer minDiscount,
                                       @Param("sort")String sort);

}
