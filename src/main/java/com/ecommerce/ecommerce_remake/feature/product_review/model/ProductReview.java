package com.ecommerce.ecommerce_remake.feature.product_review.model;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_reviews")
public class ProductReview extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productReviewId;
    private Integer productId;
    private Integer rating;
    private String customerReview;
    private String sellerResponse;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
