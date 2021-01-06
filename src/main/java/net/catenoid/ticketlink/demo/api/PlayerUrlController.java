package net.catenoid.ticketlink.demo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kollus.live.ChattingPolicy;
import com.kollus.live.LiveTokenGenerator;
import com.kollus.url.QueryString;
import com.kollus.url.StreamType;
import com.kollus.url.UrlFactory;
import com.kollus.url.UrlType;
import com.kollus.vod.VodTokenGenerator;
import com.kollus.vod.mediacontent.Mc;
import com.kollus.vod.mediacontent.McGenerator;
import net.catenoid.ticketlink.demo.constant.Constant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@RestController
public class PlayerUrlController {

    @GetMapping("/api/player/live")
    public PlayerResData getLivePlayer(
            @RequestParam(value = "client_user_id", required = true) String client_user_id,
            @RequestParam(value = "client_user_name", required = false) String client_user_name,
            @RequestParam(value = "client_user_image", required = false) String client_user_image,
            @RequestParam(value = "live_media_channel_key", required = true) String live_media_channel_key,
            @RequestParam(value = "live_media_profile_key", required = false) String live_media_profile_key
    ) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {


        Long now = (new Date()).getTime() / 1000;



        LiveTokenGenerator generator = new LiveTokenGenerator();
        generator.client_user_id(client_user_id)
                .expire_time(now + Constant.EXPIRE_TIME_SECOND)
                .client_user_image(client_user_image)
                .client_user_name(client_user_name)
                .live_media_channel_key(live_media_channel_key)
                .live_media_profile_key(live_media_profile_key)
                .secret_key(Constant.KOLLUS_LIVE_SECRET_KEY);
        QueryString queryString = null;
        String playerUrl = UrlFactory.create(UrlType.LIVE, StreamType.STREAMING, generator, Constant.KOLLUS_LIVE_USER_KEY, queryString);
        PlayerResData result = new PlayerResData();
        result.setUrl(playerUrl);
        result.setPayload(generator.toJson());
        return result;
    }

    @GetMapping("/api/player/vod")
    public PlayerResData getVodPlayer(@RequestParam(value = "cuid", required = true) String cuid,
                               @RequestParam(value = "mckey", required = true) String mckey
    ) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Long now = (new Date()).getTime();
        VodTokenGenerator generator = new VodTokenGenerator();
        Mc mc = (new McGenerator())
                .mckey(mckey).build();
        generator.cuid(cuid)
                .expt(now + Constant.EXPIRE_TIME_SECOND)
                .mc(mc)
                .secret_key(Constant.KOLLUS_VOD_SECRET_KEY);
        String playerUrl = UrlFactory.create(UrlType.VOD, StreamType.STREAMING, generator, Constant.KOLLUS_VOD_USER_KEY, null);
        PlayerResData result = new PlayerResData();
        result.setUrl(playerUrl);
        result.setPayload(generator.toJson());
        return result;
    }
}
