package com.ecommerce.ecommerce_remake.feature.store_following.model;

import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "store_following")
public class StoreFollowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeFollowingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;


    public StoreFollowing(User user, Store store) {
        this.user = user;
        this.store = store;
    }
}
