<template>
  <Navbar :user="User" :isLogado="isLogado" />
  <ProgressBar v-if="isLoading" mode="indeterminate" style="height: .5em" />
  <router-view />
</template>

<script>
import axios from "axios";
import Navbar from "@/components/Header/Navbar.vue";
import Footer from "@/components/Footer/Footer.vue"
import ErrorToast from '@/services/notifyService'

const token = localStorage.getItem("currentToken");
const infoUser = localStorage.getItem("user")

export default {
  name: "App",
  components: {
    Navbar,
    Footer,
  },
  data() {
    return {
      User: {},
      isLogado: false,
      isLoading: false
    };
  },
  methods: {
    dadosUser() {
      if(!token){
        this.isLogado = false;
        window.localStorage.removeItem("user");
      }
      else if(token && !infoUser){
        axios({
          method: "GET",
          url: process.env.VUE_APP_TRADE_API_URL + "/user/me",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }).then((resp) => ((this.User = resp.data), window.localStorage.setItem("user", JSON.stringify(this.User))));
        this.isLogado = true;
      }
      else if(token && infoUser){
        this.isLogado = true;
        this.User = JSON.parse(infoUser);
      }
    },
    alterLoading(value) {
     this.isLoading = value;
    }
  },
  beforeMount() {
    axios.defaults.withCredentials=false
    axios.interceptors.request.use(
      config => {
        this.alterLoading(true)
        const token = localStorage.getItem('currentToken');
        if (token) {
          config.headers.common['Authorization'] = `Bearer ${token}`;
        }
        return config;
      },
      error => {
        this.alterLoading(true)
        cleanToken();
        return Promise.reject(error);
      }
    );

    axios.interceptors.response.use(
      response => {
        this.alterLoading(false)
        return response;
      },
      error => {
        this.alterLoading(false)
        let body = error.response;
        printCorrectDialog(body);
        return  Promise.reject (error)
      }
    );

    function printCorrectDialog(body) {
      let status = body.status
      body = body.data;
      switch(status) {
        case 400:
          let fieldError = body.length ? body[0] : body;
          ErrorToast.ErrorToast(fieldError.field, fieldError.msg)
          break;
        case 401:
          cleanToken();
          ErrorToast.ErrorToast('Sem autorização', 'Para o recurso acessado você não possui autorização')
          break;
        case 403:
          ErrorToast.ErrorToast('Permissão negado', 'Você não está atorizado a acessar esse recursos!')
          break;
      };

    }

    function cleanToken () {
      window.localStorage.removeItem('currentToken');
      window.localStorage.removeItem('user');
      window.location.href = "/";
    }
  },
  mounted() {
    this.dadosUser();
  },
};
</script>


<style>
:root {
  --color1: #2f855a;
  --color2: #38a169;
  --color3: #276749;
}

#app {
  font-family: Poppins, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #f1f1f1;
  height: 100%;
  width: 100%;
}
</style>
