package com.ecommerce.ecommerce_remake.feature.store_review.model;

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
@Table(name = "store_reviews")
public class StoreReview extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeReviewId;
    private Integer storeId;
    private Integer rating;
    private String customerReview;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
