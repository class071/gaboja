package com.daily.gaboja.order.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    private String zipCode; // 우편번호

    private String sggAddr; // 시군구

    private String emdAddr; // 읍면동

    private String detailAddr; // 고객 상세주소

}
