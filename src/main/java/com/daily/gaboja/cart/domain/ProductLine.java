package com.daily.gaboja.cart.domain;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Embeddable;

@Embeddable
@Getter
 public class ProductLine {

    private Long productId;
    // productId 추가 필요 ProductLine 은 product ID, quantity를 request dto로 생성 요청을 보내면,
    // productId를 이용하여 cost를 넣어주고,  amounts 를 계산하여 넣어준 후
    // cart 의 products 에 add 해주는 기능을 넣어준다.

    @ColumnDefault("0")
    private int cost; // Product 에서 cost 값 가져오기

    @ColumnDefault("0")
    private int quantity;
}
