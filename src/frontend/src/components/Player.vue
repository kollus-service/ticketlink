<template>
  <div>


    <div style="position: relative">

      <b-embed :id="playerId" type="iframe" aspect="16by9" :src="playerUrl" allow="autoplay" allowfullscreen
               style="z-index: -1;">
      </b-embed>
      <b-img
          style="z-index: 999; position: absolute; top: 0; left: 0; width: 100%; object-fit: cover;"
          :hidden="hideEndImage"
          src="https://mblogthumb-phinf.pstatic.net/MjAxNzAzMTZfMTA4/MDAxNDg5NjcyOTUzOTc2.BV87bXTpxENcTBg9ovkIrjULnYSVWX4UMgiFI9i_OC4g.BU2qzDQJSSSy0tpGs8dJNRk5RDpWt0SGStrVPnDuP-sg.PNG.johnoh125/%25ED%2585%258C%25EC%258A%25A4%25ED%258A%25B8_%25ED%258C%25A8%25ED%2584%25B4.png?type=w800">
      </b-img>
    </div>

    <b-row>
      <b-col>
        <b-card title="플레이어 주소">
          <b-card-text>
            {{ playerUrl }}
          </b-card-text>
        </b-card>
      </b-col>
      <b-col>
        <b-card title="토큰 페이로드">
          <b-card-text>
            {{ payload }}
          </b-card-text>
        </b-card>
      </b-col>
      <b-col>
        <b-form-checkbox
            v-model="errorImage"
            value="true"
            unchecked-value="false"
        >
          3012 발생시 에러 이미지 사용
        </b-form-checkbox>
        <!--        <b-form-checkbox-->
        <!--            v-model="anonymousChat"-->
        <!--            value="true"-->
        <!--            unchecked-value="false"-->
        <!--        >-->
        <!--          Guest 모드 채팅 참여자(&anonymous_chat)-->
        <!--        </b-form-checkbox>-->
        <b-form-checkbox
            v-model="overlayChat"
            value="true"
            unchecked-value="false"
        >
          가로 라이브 오버레이 강제(landscape_overlay)
        </b-form-checkbox>
        <b-form-checkbox
            v-model="all_overlay"
            value="true"
            unchecked-value="false"
        >
          모바일에서 영상 상관없이 채팅 오버레이(all_overlay)
        </b-form-checkbox>

        <b-form-checkbox
            v-model="disableBlock"
            value="true"
            unchecked-value="false"
        >
          참여자 차단방지(disable_block)
        </b-form-checkbox>
        <b-form-checkbox
            v-model="nophoto"
            value="true"
            unchecked-value="false"
        >
          채팅 이미지 제거(nophoto)
        </b-form-checkbox>
        <b-form-checkbox
            v-model="nochat"
            value="true"
            unchecked-value="false"
        >
          채팅 UI 제거 / Chatting SDK 사용(nochat)
        </b-form-checkbox>
        <b-form-checkbox
            v-model="nochat"
            value="true"
            unchecked-value="false"
        >
          Noto Sans KR 폰트 사용(notokr)
        </b-form-checkbox>
        <b-form-group
            label="채팅 UI 색변경">
          <b-form-input v-model="pointcolor" type="color"></b-form-input>
        </b-form-group>
      </b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  name: 'Player',
  props: {
    channel: Object
  },
  data() {
    return {
      playerId: 'player',
      playerUrl: '',
      isReady: false,
      isPlayed: false,
      hideEndImage: true,
      payload: {},
      anonymousChat: false,
      overlayChat: false,
      disableBlock: false,
      pointcolor: '#FFFFFF',
      nochat: false,
      nophoto: false,
      all_overlay: false,
      notokr: false,
      controller: {},
      errorImage: false,
    }
  },
  watch: {
    channel: function (newval, oldval) {
      if (newval.key !== oldval.key ||
          newval.onair !== oldval.onair) {
        this.getPlayerUrl();
      }
    },
    anonymousChat: function (newval, oldval) {
      if (newval !== oldval) {
        this.getPlayerUrl();
      }
    },
    overlayChat: function (newval, oldval) {
      if (newval !== oldval) {
        this.getPlayerUrl();
      }
    },
    disableBlock: function (newval, oldval) {
      if (newval !== oldval) {
        this.getPlayerUrl();
      }
    },
    pointcolor: function (newval, oldval) {
      if (newval !== oldval) {
        this.getPlayerUrl();
      }
    },
    nochat: function (newval, oldval) {
      if (newval !== oldval) {
        this.getPlayerUrl();
      }
    },
    nophoto: function (newval, oldval) {
      if (newval !== oldval) {
        this.getPlayerUrl();
      }
    },
    all_overlay: function (newval, oldval) {
      if (newval !== oldval) {
        this.getPlayerUrl();
      }
    },
    notokr: function (newval, oldval) {
      if (newval !== oldval) {
        this.getPlayerUrl();
      }
    }
  },
  methods: {
    getPlayerUrl: function () {
      let params = {}
      params['client_user_id'] = Math.random().toString(36).substr(2, 11);
      params['live_meddkrl ia_channel_key'] = this.channel.key;
      if (this.channel.chatting) {
        params['client_user_name'] = '채팅사용자_' + params['client_user_id'];
        params['client_user_image'] = 'https://mblogthumb-phinf.pstatic.net/20150202_133/chaejhh_1422874976243x0rSA_JPEG/%253F%253F%253F%25CE%25B1%253F3_edit.jpg?type=w800'
      }
      this.$http.get('/api/player/live', {
        params
      }).then((result) => {
        let player = result.data.url;
        if (this.anonymousChat) player += '&anonymous_chat';
        if (this.overlayChat) player += '&landscape_overlay';
        if (this.disableBlock) player += '&disable_block';
        if (this.nochat) player += '&nochat';
        if (this.nophoto) player += '&nophoto';
        if (this.all_overlay) player += '&all_overlay';
        if (this.notokr) player += '&notokr';
        if (this.pointcolor !== '#FFFFFF') player += '&pointcolor=' + this.pointcolor.replace('#', '');
        this.playerUrl = player;
        this.payload = JSON.parse(result.data.payload);
        this.initVgController();
      });
    },
    initVgController() {
      let self = this;
      self.controller = {};
      try {
        self.controller = new window.Kollus.VideogatewayController({
          target_window: document.getElementById('player').contentWindow
        });
        self.controller.on('ready', function () {
          console.log('Player Ready');
          self.isReady = true;
        });
        self.controller.on('play', function () {
          console.log('Player Play');
          self.isPlayed = true;
        });
        self.controller.on('pause', function () {
          console.log('Player Paused');
        });
        self.controller.on('progress', function (percent, position, duration) {
          console.log('Percent: ', percent, ' Position: ', position, ' Durtions: ', duration);
        })
        self.controller.on('error', function (error_code) {
          console.log(error_code);
          if (error_code == 3012) {
            if (self.isReady && self.isPlayed) {
              console.log('방송이 종료 되었습니다.')
              self.controller.set_custom_error(3012, '방송이 종료 되었습니다.', '방송이종료 되었습니다.');

            } else {
              console.log('방송 전입니다.')
              self.controller.set_custom_error(3012, '방송이 시작전입니다.', '아직 방송을 시작 하지 않았습니다.');
            }
            self.isReady = false;
            self.isPlayed = false;
            if (self.errorImage) {


              self.hideEndImage = false;
            }
          }
        });
      } catch (e) {
        console.log(e);
      }

    }
  },
  updated() {
    console.log('update!!!')
  },
  mounted() {
    let vgControllerScript = document.createElement('script');
    vgControllerScript.setAttribute('src', 'https://file.kollus.com/vgcontroller/vg-controller-client.latest.min.js')
    document.head.appendChild(vgControllerScript);
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
