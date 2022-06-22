import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
import PrimeVue from 'primevue/config';
import 'primevue/resources/themes/saga-green/theme.css';
import ProgressBar from 'primevue/progressbar';
import "primevue/resources/primevue.min.css";
import "primeicons/primeicons.css";
import Chip from 'primevue/chip';

let app = createApp(App);
app.use(router);
app.use(VueSweetalert2);
app.use(PrimeVue);
app.component('ProgressBar', ProgressBar);
app.component('Chip', Chip);
app.mount('#app')
