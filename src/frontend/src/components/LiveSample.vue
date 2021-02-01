<template>
  <div>

  </div>
  <LiveChannelList v-on:selectChannel="selectChannel"/>
  <Player :channel="channel"></Player>
</template>

<script>
export default {
  name: "LiveSample"
}
</script>

<style scoped>

</style>