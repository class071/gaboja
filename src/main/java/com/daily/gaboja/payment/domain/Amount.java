package com.daily.gaboja.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Amount {

    private int total;

    private int tax_free;

    private int vat;

    private int point;

    private int discount;

}