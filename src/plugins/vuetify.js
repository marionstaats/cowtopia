import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        dark: true,
        themes: {
            dark: {
                primary: '#313638',
                secondary: '#E0DFD5',
                error: '#F06543',
                accent: '#F09D51',
                textcolor: '#E8E9EB'
            }
        }
    }
});
