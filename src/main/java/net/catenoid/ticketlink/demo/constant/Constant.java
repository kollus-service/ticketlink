package net.catenoid.ticketlink.demo.constant;

import java.util.Date;

public class Constant {

    //Service Account는 VOD와 LIVE가 동일 하며 기본적인 연동 정보는
    // https://live-kr.kollus.com/preferences/service_account 페이지에서 확인
    public static final String KOLLUS_SERVICE_ACCOUNT = "hdyang2";

    public static final String KOLLUS_LIVE_SECRET_KEY = "hdyang2";
    public static final String KOLLUS_LIVE_USER_KEY = "7d33f532f14d0f8b5120edd46d3e9792c05fc156a51781cd8b15fccc35c63778";

    //http://kr.kollus.com/media/setting/service_account 페이지에서 확인

    public static final String KOLLUS_VOD_SECRET_KEY = "hdyang2";
    public static final String KOLLUS_VOD_USER_KEY = "a8310af6bb25b112e6e53edf787a588949adb1b3a6fa978c335e2ec9e5636d51";


    //플레이어 접속 유효 시간 초단위 현재 시간부터 60초 이내에 접속 및 재생이 이루어 져야 하며
    //해당 시간 초과시 URL을 새로 생성 해야함
    public static final int EXPIRE_TIME_SECOND = 60;


    // https://live-kr.kollus.com/preferences/service_account 페이지의
    // OAuth 클라이언트 에서 사용자 생성후 생성된 정보 입력
    public static final String OAUTH_CLIENT_ID = "185";
    public static final String OAUTH_CLIENT_SECRET = "9QovAUujUjGtcXurK1xdqyjXRKeAqbyuPeaUmJUj";

    public static final String OAUTH_SCOPE = "live:control,live:statistics";
    //빌드된 서버의 도메인이 적용된 Redirect 주소를 입력
    public static final String OAUTH_REDIRECT_URI = "https://hyeond-pc.asuscomm.com/auth/code";


    // OAuth 연동이 완료 되면 자동으로 값입력
    public static String OAUTH_ACCESS_TOKEN = "";
    public static String OAUTH_REFRESH_TOKEN = "";
    public static Date OAUTH_TOKEN_EXPIRE_TIME = new Date();


}
