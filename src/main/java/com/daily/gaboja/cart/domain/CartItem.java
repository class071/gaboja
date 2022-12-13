package com.daily.gaboja.cart.domain;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class CartItem {

   private Long productId;

   @ColumnDefault("0")
   private int cost;

   @ColumnDefault("0")
   private int quantity;
}
