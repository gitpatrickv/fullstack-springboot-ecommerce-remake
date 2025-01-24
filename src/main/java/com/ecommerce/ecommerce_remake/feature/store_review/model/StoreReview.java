package com.ecommerce.ecommerce_remake.feature.store_review.model;

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
@Table(name = "store_reviews")
public class StoreReview extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeReviewId;
    private Integer storeId;
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
