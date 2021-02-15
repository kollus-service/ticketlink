import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import {BootstrapVue} from "bootstrap-vue";
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import {router} from './router';
import VueFriendlyIframe from 'vue-friendly-iframe';

Vue.use(BootstrapVue)
Vue.use(VueFriendlyIframe);
Vue.config.productionTip = false
Vue.prototype.$http = axios
Vue.prototype.EventBus = new Vue();
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
