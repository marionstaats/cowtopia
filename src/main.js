import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import Cloudinary from "cloudinary-vue";

Vue.config.productionTip = false

Vue.use(Cloudinary, {
  configuration: { cloudName: "dfqpsmhyl" }
});

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
