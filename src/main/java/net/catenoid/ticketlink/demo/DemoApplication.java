package net.catenoid.ticketlink.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.catenoid.ticketlink.demo.constant.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HashMap<String, Object> fileMap = objectMapper.readValue(new File("oauth.json"), new TypeReference<HashMap<String, Object>>() {
            });

            Constant.OAUTH_ACCESS_TOKEN = fileMap.get("OAUTH_ACCESS_TOKEN").toString();
            Constant.OAUTH_REFRESH_TOKEN = fileMap.get("OAUTH_REFRESH_TOKEN").toString();
            Constant.OAUTH_TOKEN_EXPIRE_TIME = new Date(Long.parseLong(fileMap.get("OAUTH_TOKEN_EXPIRE_TIME").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpringApplication.run(DemoApplication.class, args);
    }

}
