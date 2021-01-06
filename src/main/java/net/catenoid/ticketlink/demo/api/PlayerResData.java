package net.catenoid.ticketlink.demo.api;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class PlayerResData {

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String payload;
}
