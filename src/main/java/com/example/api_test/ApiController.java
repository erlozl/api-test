package com.example.api_test;

import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

//    소요시간 api
    @GetMapping("/exchange-test")
    public ResponseEntity<String> restTemplateTeset2() {

        // 공항을 인자로 받아야 함
        String airport = "GMP";
        String myServiceKey = Define.SERVICEKEY;

        // URI 클래스를 사용하여 URL 생성
        URI uri = null;
        try {
            uri = new URI(UriComponentsBuilder
                    .fromUriString("http://openapi.airport.co.kr/service/rest/dailyExpectPassenger/dailyExpectPassenger")
                    .queryParam("ServiceKey", myServiceKey)
                    .queryParam("schAirport", airport)
                    .queryParam("schDate=20231210")
                    .build()
                    .toUriString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // RestTemplate을 사용하여 API에 요청
        RestTemplate restTemplate = new RestTemplate();

        // 결과 처리 등 추가 로직 구현
//        System.out.println(jsonString);
//        RestTemplate restTemplate = new RestTemplate();
//
//        // 자원 등록 요청 --> POST 방식 사용법
//        // 1. URI 객체 만들기
//        // https://jsonplaceholder.typicode.com/posts
////        String airport = "GMP";
//// 공항을 인자로 받아야 함
//        URI uri = null;
//        try {
//            uri = new URI("http://openapi.airport.co.kr/service/rest/dailyExpectPassenger/dailyExpectPassenger?ServiceKey="
//                    +Define.SERVICEKEY
//                    +"&schDate=20231210&schAirport=GMP");
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        String jsonString = restTemplate.getForObject(uri, String.class);
//
//        System.out.println(jsonString);

        // 2 객체 생성
        // exchange 사용 방법
        // 1. HttpHeaders 객체를 만들고 Header 메세지 구성
        // 2. body 데이터를 key=value 구조로 만들기
        // 3. HttpEntity 객체를 생성해서 Header 와 결합 후 요청

        // 헤더 구성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=UTF-8");

        // 바디 구성
        // MultiValueMap<K, V> = {"title" : "[블로그 포스트1]"}
        // {"title" : "블로그 포스트1"}
        Map<String, String> params = new HashMap<>();

        // 헤더와 바디 결합
        HttpEntity<Map<String, String>> requestMessage
                = new HttpEntity<>(params, headers);


        // HTTP 요청 처리
        // 파싱 처리 해야 한다.
        ResponseEntity<String> response
                =  restTemplate.exchange(uri, HttpMethod.GET, requestMessage,
                String.class);
        String apiTest = response.getBody();
        System.out.println("TEST : apiTest " + apiTest.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

//    인천공항 api

    @GetMapping("/api-test2")
    public ResponseEntity<String> restTemplateTeset3() {

        String myServiceKey = Define.SERVICEKEY;

        URI uri = null;
        try {
            uri = new URI(UriComponentsBuilder
                    .fromUriString("http://apis.data.go.kr/B551177/PassengerNoticeKR/getfPassengerNoticeIKR")
                    .queryParam("serviceKey", myServiceKey)
                    .queryParam("selectdate=0")
                    .queryParam("type=json")
                    .build()
                    .toUriString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        RestTemplate restTemplate = new RestTemplate();

        // 헤더 구성
        HttpHeaders headers = new HttpHeaders();
        // 바디 구성
        // MultiValueMap<K, V> = {"title" : "[블로그 포스트1]"}
        // {"title" : "블로그 포스트1"}
        Map<String, String> params = new HashMap<>();

        // 헤더와 바디 결합
        HttpEntity<Map<String, String>> requestMessage
                = new HttpEntity<>(params, headers);


        // HTTP 요청 처리
        // 파싱 처리 해야 한다.
        ResponseEntity<String> response
                =  restTemplate.exchange(uri, HttpMethod.GET, requestMessage,
                String.class);
        String apiTest = response.getBody();
        System.out.println("TEST : apiTest " + apiTest.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }


    // http://localhost:8080/airport/airport-info
    @GetMapping("/airport-info")
    public String parkingArea(Model model) {

        // 공항을 인자로 받아야 함
        String airport = "GMP";

        URI uri = null;
        String url = "http://openapi.airport.co.kr/service/rest/AirportParkingFee/parkingfee?serviceKey="
                + Define.SERVICEKEY
                + "&schAirportCode=" + airport
                + "&type=json";
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request
                = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(uri, HttpMethod.GET, request, Map.class);
        model.addAttribute("list",response.getBody());


        return "airport/airportInfo";
    }


}
