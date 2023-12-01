package com.example.api_test;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ApiController {


    @GetMapping("/api-test")
    public ResponseEntity<?> apiTest() {
//	다른 서버에 자원 요청
//	url에 클래스를 만들어주어야 한다
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.odcloud.kr/api/getAPRTPsgrCongestion/v1/aprtPsgrCongestion\u200B?page=1&perPage=10&serviceKey=HKxJuVdpaLcu9XXSjSSQVh%252FZyIeZDvubbEIdvwTAMum%252BXNZFLY5ZvmiziG6MaNtXLqMn3ch9doDSDXVvI34zBw%253D%253D")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        // 다른 서버에 접근해서 자원 요청
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());

        // MIME Type
        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

}
