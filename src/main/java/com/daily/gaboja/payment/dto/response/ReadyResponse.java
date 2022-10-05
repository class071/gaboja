package com.daily.gaboja.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReadyResponse {

    private String tid;

    private String next_redirect_pc_url;

    private String partner_order_id;
}
