<template>
  <b-navbar toggleable v-if="hasChannels" variant="primary">
    <b-navbar-brand>채널목록</b-navbar-brand>
    <b-navbar-toggle target="navbar-toggle-collapse">
      <template #default="{ expanded }">
        <b-icon v-if="expanded" icon="chevron-bar-up">감추기</b-icon>
        <b-icon v-else icon="chevron-bar-down">펼치기</b-icon>
      </template>
    </b-navbar-toggle>
    <b-collapse id="navbar-toggle-collapse" is-nav>
      <b-navbar-nav class="ml-auto">
      <b-nav-item v-for="channel in channels" :key="channel.key" v-on:click="selectChannel(channel)">
        {{ channel.name }}
        <b-badge v-if="channel.onair" variant="danger">방송중</b-badge>
        <b-badge v-if="channel.chatting" variant="warning">채팅</b-badge>
      </b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
  <span v-else v-on:click="listChannel">정보가 없습니다.</span>
</template>

<script>
export default {
  name: "LiveChannelList",
  data() {
    return {
      channels: []
    }
  },
  created() {
    this.listChannel()
  },
  computed: {
    hasChannels() {
      console.log('hasChannels', this.channels.length > 0)
      return this.channels.length > 0
    }
  },
  methods: {
    listChannel: function () {
      this.$http.get('/api/live/channel')
          .then((result) => {
            this.channels = result.data
            console.log(this.channels)

          })
    },
    selectChannel: function (event) {
      this.$emit('selectChannel', event);
    }
  },
  mounted() {

  }
}
</script>

<style scoped>

</style>
