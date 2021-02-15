<template>
  <b-row>
    <b-col cols="3">
      <LiveChannelList v-on:selectChannel="selectChannel"/>
    </b-col>
    <b-col cols="9">
      <Player :channel="channel"></Player>
    </b-col>
  </b-row>
</template>

<script>
export default {
  name: "LiveSample"
}
</script>

<style scoped>

</style>
