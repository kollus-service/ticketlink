package net.catenoid.ticketlink.demo.api;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class LiveChannelInfo {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String key;

    @Getter
    @Setter
    private boolean onair;

    @Getter
    @Setter
    private boolean chatting;

}
