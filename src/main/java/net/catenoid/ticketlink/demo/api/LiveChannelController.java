package net.catenoid.ticketlink.demo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.catenoid.ticketlink.demo.constant.Constant;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class LiveChannelController {

    @GetMapping("/api/live/channel")
    public List getChannels() throws JsonProcessingException {


        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + Constant.OAUTH_ACCESS_TOKEN);
        HttpEntity entity = new HttpEntity(headers);
        String url = String.format("https://api-live-kr.kollus.com/api/v1/live/service-accounts/%s/channels?per_page=100", Constant.KOLLUS_SERVICE_ACCOUNT);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> resMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<HashMap<String, Object>>() {
        });
        List<HashMap<String, Object>> rawList = (List) resMap.get("data");
        List<LiveChannelInfo> channelInfos = new ArrayList<>();
        for (HashMap<String, Object> item : rawList) {
            LiveChannelInfo channelInfo = new LiveChannelInfo();
            channelInfo.setName(item.get("title").toString());
            channelInfo.setKey(item.get("key").toString());
            HashMap<String, Object> lastest_broadcast = (HashMap<String, Object>) item.get("latest_broadcast");
            channelInfo.setOnair(Boolean.parseBoolean(lastest_broadcast.get("is_onair").toString()));
            channelInfo.setChatting(Boolean.parseBoolean(item.get("use_chatting_service").toString()));
            channelInfos.add(channelInfo);

        }
        return channelInfos;
    }
}
