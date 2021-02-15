<template>
  <b-row>
    <VueVodPlayerList v-bind:list="vodList"></VueVodPlayerList>
  </b-row>
</template>

<script>
import VueVodPlayerList from "@/components/VueVodPlayerList";

export default {
  name: "VodSample",
  components: {
    VueVodPlayerList
  },
  data() {
    return {
      vodList: [{ mckey: 'u2Er6tAJ', playUrl: 'https://v.kr.kollus.com/u2Er6tAJ', posterUrl:'https://v.kr.kollus.com/poster/u2Er6tAJ'},
        { mckey: '9jzk6oun', playUrl: 'https://v.kr.kollus.com/9jzk6oun', posterUrl:'https://v.kr.kollus.com/poster/9jzk6oun'},
        { mckey: 'cnxy6oun', playUrl: 'https://v.kr.kollus.com/cnxy6oun', posterUrl:'https://v.kr.kollus.com/poster/cnxy6oun'},
        { mckey: 'c5y46oun', playUrl: 'https://v.kr.kollus.com/c5y46oun', posterUrl:'https://v.kr.kollus.com/poster/c5y46oun'},
        { mckey: 'LQJY6oun', playUrl: 'https://v.kr.kollus.com/LQJY6oun', posterUrl:'https://v.kr.kollus.com/poster/LQJY6oun'},
        { mckey: 'MPoZ6oum', playUrl: 'https://v.kr.kollus.com/MPoZ6oum', posterUrl:'https://v.kr.kollus.com/poster/MPoZ6oum'},
        { mckey: 'qwcz6oum', playUrl: 'https://v.kr.kollus.com/qwcz6oum', posterUrl:'https://v.kr.kollus.com/poster/qwcz6oum'},
        { mckey: 'cG7v6oum', playUrl: 'https://v.kr.kollus.com/cG7v6oum', posterUrl:'https://v.kr.kollus.com/poster/cG7v6oum'},
        { mckey: 'PvjM65gD', playUrl: 'https://v.kr.kollus.com/PvjM65gD', posterUrl:'https://v.kr.kollus.com/poster/PvjM65gD'},
        { mckey: 'bW4W65gD', playUrl: 'https://v.kr.kollus.com/bW4W65gD', posterUrl:'https://v.kr.kollus.com/poster/bW4W65gD'}]
    }
  }

}
</script>

<style scoped>

</style>



