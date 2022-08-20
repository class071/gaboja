package com.daily.gaboja.order.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Embeddable
@NoArgsConstructor
public class ShippingInfo {

    @Embedded
    private Address address;

    @Embedded
    private Receiver receiver;
}
