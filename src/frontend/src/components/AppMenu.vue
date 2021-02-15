<template>
  <b-container fluid>
    <b-row>
      <b-col cols="2">
        <b-nav vertical>
          <b-nav-item>
            <router-link to="/live" tag="li">Live</router-link>
            <router-link to="/vod" tag="li">Vod</router-link>
          </b-nav-item>
        </b-nav>
      </b-col>
      <b-col cols="10">
        <router-view/>
      </b-col>
    </b-row>


  </b-container>
</template>

<script>



export default {
  name: 'AppMenu',
  components: {
  },
  data() {
    return {
      channel: {},
    }
  },
  methods: {
    selectChannel: function (channel) {
      this.channel = channel;
    },
    websocketConnect: function () {
      if (window.location.protocol === 'http:') {
        this.socket = new WebSocket('ws://' + window.location.host + '/ws')
      } else {
        this.socket = new WebSocket('wss://' + window.location.host + '/ws')
      }

      this.socket.onopen = () => {
        console.log('WSS CONNECTED')
        this.socket.onmessage = (msg) => {
          let msgdata = JSON.parse(msg.data)
          const channel_key = msgdata['channel_key'];
          const state = msgdata['broadcast_state'];

          console.log(this.channel.key, this.channel.onair)
          console.log(channel_key, state)
          if (this.channel.key === channel_key) {
            let ch = {};
            ch.key = channel_key;
            ch.name = this.channel.name
            ch.chatting = this.channel.chatting
            if (state === 'start' || state === 'resume') {
              ch.onair = true
              console.log('start')

            } else {
              ch.onair = false
              console.log('end')

            }
            console.log(ch);
            this.channel = ch;
          }


        }
      }
    },
    websocketDisconnect: function () {
      this.socket.close();
    },

  },
  beforeCreate() {
    let vgcontroller = document.getElementById('vgcontroller');
    if(vgcontroller === null){
      let vgControllerScript = document.createElement('script');
      vgControllerScript.id = 'vgcontroller';
      vgControllerScript.setAttribute('src', 'https://file.kollus.com/vgcontroller/vg-controller-client.latest.min.js')
      document.head.appendChild(vgControllerScript);
    }
  },
  created() {

    this.websocketConnect()
  },
  beforeDestroy() {
    this.websocketDisconnect()
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
