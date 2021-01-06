package net.catenoid.ticketlink.demo.oauth;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.catenoid.ticketlink.demo.constant.Constant;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

@RestController
public class OAuthController {


    @GetMapping("/auth/info")
    public HashMap authInfo() {
        HashMap<String, Object> info = new HashMap<String, Object>();


        info.put("KOLLUS_VOD_SECRET_KEY", Constant.KOLLUS_VOD_SECRET_KEY);
        info.put("KOLLUS_VOD_USER_KEY", Constant.KOLLUS_VOD_USER_KEY);
        info.put("KOLLUS_LIVE_SECRET_KEY", Constant.KOLLUS_LIVE_SECRET_KEY);
        info.put("KOLLUS_LIVE_USER_KEY", Constant.KOLLUS_LIVE_USER_KEY);
        info.put("EXPIRE_TIME_SECOND", Constant.EXPIRE_TIME_SECOND);
        info.put("OAUTH_CLIENT_ID", Constant.OAUTH_CLIENT_ID);
        info.put("OAUTH_CLIENT_SECRET", Constant.OAUTH_CLIENT_SECRET);
        info.put("OAUTH_SCOPE", Constant.OAUTH_SCOPE);
        info.put("OAUTH_REDIRECT_URI", Constant.OAUTH_REDIRECT_URI);
        info.put("OAUTH_ACCESS_TOKEN", Constant.OAUTH_ACCESS_TOKEN);
        info.put("OAUTH_REFRESH_TOKEN", Constant.OAUTH_REFRESH_TOKEN);
        info.put("OAUTH_TOKEN_EXPIRE_TIME", Constant.OAUTH_TOKEN_EXPIRE_TIME);


        return info;
    }

    @GetMapping("/auth")
    public RedirectView authorize() {

        if (Constant.OAUTH_CLIENT_ID.isEmpty() ||
                Constant.OAUTH_CLIENT_SECRET.isEmpty() ||
                Constant.OAUTH_REDIRECT_URI.isEmpty() ||
                Constant.OAUTH_SCOPE.isEmpty()
        ) {
            return new RedirectView("/");
        } else if (Constant.OAUTH_ACCESS_TOKEN.isEmpty()) {
            String authorize_url = String.format("https://live-kr.kollus.com/oauth/authorize?" +
                    "response_type=code" +
                    "&client_id=%s" +
                    "&redirect_uri=%s" +
                    "&scope=%s", Constant.OAUTH_CLIENT_ID, Constant.OAUTH_REDIRECT_URI, Constant.OAUTH_SCOPE);
            return new RedirectView(authorize_url);
        } else {
            return new RedirectView("/");
        }
    }

    @SneakyThrows
    @RequestMapping("/auth/code")
    public HashMap code(
            @RequestParam("code") String code
    ) throws JsonProcessingException, FileNotFoundException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("code", code);
        parameters.add("client_id", Constant.OAUTH_CLIENT_ID);
        parameters.add("client_secret", Constant.OAUTH_CLIENT_SECRET);
        parameters.add("redirect_uri", Constant.OAUTH_REDIRECT_URI);
        parameters.add("scope", Constant.OAUTH_SCOPE);

        HttpEntity entity = new HttpEntity(parameters, headers);


        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://live-kr.kollus.com/oauth/token", entity, String.class);


        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> resMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<HashMap<String, Object>>() {
        });

        Constant.OAUTH_ACCESS_TOKEN = resMap.get("access_token").toString();
        Constant.OAUTH_REFRESH_TOKEN = resMap.get("refresh_token").toString();
        Long expire_time = new Date().getTime() + Long.parseLong(resMap.get("expires_in").toString()) * 1000;
        Constant.OAUTH_TOKEN_EXPIRE_TIME = new Date(expire_time);

        HashMap<String, Object> fileMap = new HashMap<String, Object>();
        fileMap.put("OAUTH_ACCESS_TOKEN", Constant.OAUTH_ACCESS_TOKEN);
        fileMap.put("OAUTH_REFRESH_TOKEN", Constant.OAUTH_REFRESH_TOKEN);
        fileMap.put("OAUTH_TOKEN_EXPIRE_TIME", Constant.OAUTH_TOKEN_EXPIRE_TIME.getTime());
        File fout = new File("oauth.json");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(objectMapper.writeValueAsString(fileMap));
        bw.close();
        return fileMap;
    }

    @Scheduled(fixedDelay = 12 * 60 * 60 * 1000)
    public void checkApiToken() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> fileMap = objectMapper.readValue(new File("oauth.json"), new TypeReference<HashMap<String, Object>>() {
        });
        Long diff = Long.parseLong(fileMap.get("OAUTH_TOKEN_EXPIRE_TIME").toString()) - new Date().getTime();
        if(diff < 24 * 60 * 60 * 1000) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
            parameters.add("grant_type", "refresh_code");
            parameters.add("refresh_token", Constant.OAUTH_REFRESH_TOKEN);
            parameters.add("client_id", Constant.OAUTH_CLIENT_ID);
            parameters.add("client_secret", Constant.OAUTH_CLIENT_SECRET);
            parameters.add("scope", Constant.OAUTH_SCOPE);
            HttpEntity entity = new HttpEntity(parameters, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://live-kr.kollus.com/oauth/token", entity, String.class);
            HashMap<String, Object> resMap = objectMapper.readValue(responseEntity.getBody(), new TypeReference<HashMap<String, Object>>() {
            });

            Constant.OAUTH_ACCESS_TOKEN = resMap.get("access_token").toString();
            Constant.OAUTH_REFRESH_TOKEN = resMap.get("refresh_token").toString();
            Long expire_time = new Date().getTime() + Long.parseLong(resMap.get("expires_in").toString()) * 1000;
            Constant.OAUTH_TOKEN_EXPIRE_TIME = new Date(expire_time);
            fileMap.put("OAUTH_ACCESS_TOKEN", Constant.OAUTH_ACCESS_TOKEN);
            fileMap.put("OAUTH_REFRESH_TOKEN", Constant.OAUTH_REFRESH_TOKEN);
            fileMap.put("OAUTH_TOKEN_EXPIRE_TIME", Constant.OAUTH_TOKEN_EXPIRE_TIME.getTime());
            File fout = new File("oauth.json");
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(objectMapper.writeValueAsString(fileMap));
            bw.close();
        }

    }
}
