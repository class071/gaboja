package com.daily.gaboja.payment.service;

import com.daily.gaboja.order.domain.Order;
import com.daily.gaboja.order.exception.OrderNotExistException;
import com.daily.gaboja.order.repository.OrderRepository;
import com.daily.gaboja.payment.domain.Payment;
import com.daily.gaboja.payment.dto.response.ApproveResponse;
import com.daily.gaboja.payment.dto.response.ReadyResponse;
import com.daily.gaboja.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    @Value("${kakao.apikey}")
    private String KAKAO_APIKEY;

    @Value("${kakao.cid}")
    private String KAKAO_CID;


    public ReadyResponse ready(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotExistException::new);

        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", KAKAO_APIKEY);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        int totalAmount = order.getTotalAmount();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("cid", KAKAO_CID);
        parameters.add("partner_order_id", String.valueOf(orderId));
        parameters.add("partner_user_id", "userId"); // 현재 로그인 한 사용자의 ID 값
        parameters.add("item_name", "name");
        parameters.add("quantity", "1");
        parameters.add("total_amount", String.valueOf(totalAmount));
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:8080/api/payment/approve?orderId=" + orderId);
        parameters.add("cancel_url", "http://localhost:8080/api/payment/cancel");
        parameters.add("fail_url", "http://localhost:8080/api/payment/fail");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);
        RestTemplate template = new RestTemplate();
        String url = "https://kapi.kakao.com/v1/payment/ready";
        ReadyResponse readyResponse = template.postForObject(url, requestEntity, ReadyResponse.class);

        String tid = readyResponse.getTid();

        Payment payment = Payment.builder()
                .totalAmount(totalAmount)
                .tid(tid)
                .orderId(orderId)
                .build();

        paymentRepository.save(payment);

        return readyResponse;
    }

    public ApproveResponse approve(Long orderId, String pgToken) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", KAKAO_APIKEY);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        parameters.add("cid", KAKAO_CID);
        parameters.add("tid", payment.getTid());
        parameters.add("partner_order_id", String.valueOf(payment.getOrderId()));
        parameters.add("partner_user_id", "userId");
        parameters.add("pg_token", pgToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        RestTemplate template = new RestTemplate();
        String url = "https://kapi.kakao.com/v1/payment/approve";

        ApproveResponse approveResponse = template.postForObject(url, requestEntity, ApproveResponse.class);

        return approveResponse;
    }
}
