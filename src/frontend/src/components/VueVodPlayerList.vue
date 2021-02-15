<template>
  <b-container>
    <b-row style="height: 100%;">
      <b-col cols="3" v-for="(item) in list"
             v-bind:key="item.mckey"
      >
        <!--        <VueVodPlayer v-bind:mckey="item.mckey"-->
        <!--                      v-bind:player-url="item.playUrl"-->
        <!--                      v-bind:poster-url="item.posterUrl"-->
        <!--        >-->
        <!--        </VueVodPlayer>-->

        <iframe :id="item.mckey" class="vodplayer" :src="'/vodplayer/' + item.mckey" allowfullscreen
                allow="autoplay; fullscreen"></iframe>
      </b-col>
    </b-row>


  </b-container>

</template>

<script>
// import VueVodPlayer from "@/components/VueVodPlayer";

export default {
  name: "VueVodPlayerList",
  // components: {VueVodPlayer},
  props: {
    list: Array
  },
  beforeCreate() {

  },
  created() {
    window.addEventListener('message', (e) => {
      console.log(e);
      if (e.data.method === 'play') {
        let mckeys = this.list.filter(item => {
          return item.mckey !== e.data.mckey
        }).map(item => item.mckey);
        mckeys.forEach(mckey => {
          document.getElementById(mckey).contentWindow.postMessage({method: 'pause'}, '*')
        });
      }
    });
  },
  mounted() {

  }

}
</script>

<style scoped>
b-row {
  height: 100%;
}

.vodplayer {
  border: none;
  width: 100%;
  height: 100%;
  overflow-x: hidden;
}

</style>
