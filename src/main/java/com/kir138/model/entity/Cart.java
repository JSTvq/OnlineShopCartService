package com.kir138.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Builder.Default
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

    @Version
    @Builder.Default
    private Integer version = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public void setItems(List<CartItem> items) {
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
