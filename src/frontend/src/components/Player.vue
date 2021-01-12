<template>
  <div>
    <b-embed type="iframe" aspect="16by9" :src="playerUrl" allow="autoplay" allowfullscreen>
    </b-embed>
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
            v-model="anonymousChat"
            value="true"
            unchecked-value="false"
        >
          Guest 모드 채팅 참여자
        </b-form-checkbox>
        <b-form-checkbox
            v-model="overlayChat"
            value="true"
            unchecked-value="false"
        >
          가로 라이브 오버레이 강제
        </b-form-checkbox>

        <b-form-checkbox
            v-model="disableBlock"
            value="true"
            unchecked-value="false"
        >
          참여자 차단방지
        </b-form-checkbox>
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
      playerUrl: '',
      payload: {},
      anonymousChat: false,
      overlayChat: false,
      disableBlock: false
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
    }
  },
  methods: {
    getPlayerUrl: function () {
      let params = {}
      params['client_user_id'] = Math.random().toString(36).substr(2, 11);
      params['live_media_channel_key'] = this.channel.key;
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
        this.playerUrl = player;
        this.payload = JSON.parse(result.data.payload);
      });
    }
  },
  updated() {
    console.log('update!!!')
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
