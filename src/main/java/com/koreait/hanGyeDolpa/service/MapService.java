package com.koreait.hanGyeDolpa.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MapService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 카카오 장소 ID를 조회해 place.map.kakao.com/{id} URL을 구성해 반환
	 * 
	 *  placeName 검색할 장소 이름
	 *  Xposition 경도
	 *  Yposition 위도
	 *  place.kakao.com/{placeId} 형태의 URL
	 */
	public String getPlaceID(String placeName, double Xposition, double Yposition) {
		
		String placeID = "http://place.map.kakao.com/";
		
		final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";
		final String KAKAO_API_KEY = [본인 키];
		

		// 요처URL 작성
		String getSearchKeyWord = placeName;
		String reqUrl = KAKAO_API_URL +
        		"?page=1" +
        		"&size=10" + 
        		"&sort=accuracy" +
                "&query=" + getSearchKeyWord +
                "&y=" + Yposition +
                "&x=" + Xposition
                ;
				
		log.info("Request URL: {}", reqUrl);
		
		// 헤더 설정 
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);
		HttpEntity<Void> entity = new HttpEntity<>(headers);
		
		// API 호출
		ResponseEntity<Map> response = restTemplate.exchange(reqUrl, HttpMethod.GET, entity, Map.class);
		log.info("Resp-> " + response.toString());
		
		try {
			List<Map<String, Object>> documents = (List<Map<String, Object>>) response.getBody().get("documents");
			
			if(documents != null && !documents.isEmpty()) {
//				if(documents.size() > 1) {
					String placeData = documents.stream()
				            .map(doc -> (String) doc.get("id"))
				            .filter(data -> data != null) // null 값 방지
				            .findFirst() // 첫 번째 값 반환
				            .orElse(null); // 값이 없을 경우 null 반환
					log.info("placeData: "+ placeData);
					// TODO 너무 별로인 코드. 좀 더 다듬기
					placeID+= placeData;
			}
		}
		catch (ClassCastException e) {
            log.error("Failed to cast response documents to List<Map<String, Object>>", e);
        } catch (Exception e) {
            log.error("An error occurred while processing the response", e);
        }
		
		log.info("placeID: " + placeID);
		
		return placeID;
	}
}
/**
 * 아래코드로 확인해보고 대체 
public String getPlaceID(String placeName, double x, double y) {
    final String API_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";
    final String KAKAO_API_KEY = "[본인 키]";

    String url = API_URL +
        "?page=1&size=10&sort=accuracy" +
        "&query=" + placeName +
        "&x=" + x +
        "&y=" + y;

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    try {
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        List<Map<String, Object>> docs = (List<Map<String, Object>>) response.getBody().get("documents");

        if (docs != null && !docs.isEmpty()) {
            String placeId = (String) docs.get(0).get("id");
            return "http://place.map.kakao.com/" + placeId;
        }
    } catch (Exception e) {
        log.error("카카오 장소 ID 조회 실패", e);
    }

    return "http://place.map.kakao.com/";
}

 */
