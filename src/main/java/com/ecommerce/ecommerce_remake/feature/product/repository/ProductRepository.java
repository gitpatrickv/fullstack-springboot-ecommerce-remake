package com.ecommerce.ecommerce_remake.feature.product.repository;

import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByStoreAndStatusIn(Store store, List<Status> statusList, Pageable pageable);
    Page<Product> findAllByStatus(Status status, Pageable pageable);
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE products p " +
                    "SET total_sold = total_sold + :quantityToAdd " +
                    "WHERE p.product_id = :productId")
    void addTotalProductSoldOnOrder(@Param("productId") Integer productId, @Param("quantityToAdd") int quantityToAdd);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE products p " +
                    "SET total_sold = CASE " +
                    "WHEN total_sold >= :quantityToSubtract THEN total_sold - :quantityToSubtract " +
                    "ELSE total_sold END " +
                    "WHERE p.product_id = :productId")
    void subtractTotalProductSoldOnOrderCancellation(@Param("productId") Integer productId, @Param("quantityToSubtract") int quantityToSubtract);

}
