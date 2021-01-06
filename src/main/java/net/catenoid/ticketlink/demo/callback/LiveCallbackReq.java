package net.catenoid.ticketlink.demo.callback;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LiveCallbackReq {

    private String request_id;
    private String version;
    private String service_account_key;
    private String channel_key;
    private String stream_keyS;
    private String broadcast_key;
    private String broadcast_state;
}
