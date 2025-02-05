package com.ecommerce.ecommerce_remake.feature.product_review.model;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "product_reviews")
public class ProductReview extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productReviewId;
    private Integer productId;
    private Integer storeId;
    private Integer rating;
    private String customerReview;
    private String sellerResponse;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
