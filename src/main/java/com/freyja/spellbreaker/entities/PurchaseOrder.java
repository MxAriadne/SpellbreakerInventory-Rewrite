package com.freyja.spellbreaker.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "status")
    private String status;

    @ColumnDefault("current_timestamp()")
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "retailer")
    private String retailer;

    @Column(name = "order_no")
    private String orderNo;

}