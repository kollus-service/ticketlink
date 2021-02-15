<template>
  <div class="playerWrapper">
    <iframe
        v-if="!videoHidden"
        :id="mckey"
        :src="playerUrl"
        allow="autoplay; fullscreen"
        allowfullscreen
        webkitallowfullscreen
        msallowfullscreen
        @load="initVgController"
    >
    </iframe>
    <div class="overlay" v-if="videoHidden">
      <img :src="posterUrl" v-on:click="posterClicked">
    </div>
  </div>


</template>

<script>


export default {
  name: "VueVodPlayer",
  // props: {
  //   mckey: String,
  //   playerUrl: String,
  //   posterUrl: String,
  // },
  data() {
    return {
      mckey: '',
      playerUrl: '',
      posterUrl: '',
      videoHidden: true,
      controller: {},
      lastat : 0,
    }
  },
  computed: {},
  methods: {

    posterClicked() {
      this.videoHidden = false;
      // this.initVgController();

    },
    initVgController() {
      let self = this;
      self.controller = {};
      console.log(self.mckey);
      console.log(document.getElementById(self.mckey))
      try {
        self.controller = new window.Kollus.VideogatewayController({
          target_window: document.getElementById(self.mckey).contentWindow
        });
        self.controller.on('ready', function () {
          console.log('Player Ready', self.mckey);
          self.isReady = true;
          if(self.lastat > 0) {
            self.controller.play(self.lastat);
          }
          else {
            self.controller.play();
          }
        });
        self.controller.on('play', function () {
          console.log('Player Play');
          self.isPlayed = true;
          window.parent.postMessage({method: 'play', mckey: self.mckey}, '*');
        });
        self.controller.on('pause', function () {
          console.log('Player Paused');
          self.videoHidden = true;
        });
        self.controller.on('progress', function (percent, position, _duration) {
          // console.log('Percent: ', percent, ' Position: ', position, ' Durtions: ', duration);
          self.lastat = parseInt(position);
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
        window.addEventListener('message', (e) => {
          if(e.data.method === 'pause'){
            self.controller.pause();
          }
        });

      } catch (e) {
        console.log(e);
      }
    }
  },
  beforeCreate() {
    let vgcontroller = document.getElementById('vgcontroller');
    if (vgcontroller === null) {
      let vgControllerScript = document.createElement('script');
      vgControllerScript.id = 'vgcontroller';
      vgControllerScript.setAttribute('src', 'https://file.kollus.com/vgcontroller/vg-controller-client.latest.min.js')
      document.head.appendChild(vgControllerScript);
    }
  },
  created() {


  },
  mounted() {
    this.mckey = this.$route.params.mckey;
    this.playerUrl = 'http://v.kr.kollus.com/' + this.$route.params.mckey + '?controls_activation=none';
    this.posterUrl = 'http://v.kr.kollus.com/poster/' + this.$route.params.mckey;
  }
}
</script>

<style scoped>
* {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  padding: 0;
}

.playerWrapper {

}

iframe {
  position: relative;
  border: none;
  width: 100%;
  height: 100%;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 999;
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

</style>
