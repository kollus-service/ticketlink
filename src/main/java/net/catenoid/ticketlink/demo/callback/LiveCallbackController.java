package net.catenoid.ticketlink.demo.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.catenoid.ticketlink.demo.ws.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.*;

@RestController
public class LiveCallbackController {
    @Autowired
    private WebSocketHandler webSocketHandler;


    @PostMapping(value = "/callback/live", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String liveCallback(
            @RequestBody MultiValueMap<String, String> req) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LiveCallbackReq msg = new LiveCallbackReq();
        msg.setChannel_key(req.get("channel_key").get(0));
        msg.setBroadcast_state(req.get("broadcast_state").get(0));
        webSocketHandler.sendMessage(objectMapper.writeValueAsString(msg));
        return "";
    }
}
