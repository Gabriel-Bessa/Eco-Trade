<template>
  <div class="container">
    <div class="divTitulo">
      <h4>Anúncio</h4>
    </div>
    <div class="main">
      <div>
        <Anuncio
          :MostrarStatus="false"
          :MostrarUsuario="false"
          :dadosAnuncio="Anuncio"
          :imagemAnuncio="ImagensAnuncio"
        />
      </div>
      <div class="userInfo">
        <div class="user">
          <InfoUsuario :isAnunciante="true" :dadosUsuario="donoAnuncio" />
        </div>
        <div class="button">
          <a href="/#/nova-proposta" class="btnFazerProposta">Fazer Proposta</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Header/Navbar.vue";
import Anuncio from "@/components/Anuncio.vue";
import InfoUsuario from "@/components/InfoUsuario.vue";
import axios from "axios";

export default {
  name: "AnuncioView",
  components: {
    Navbar,
    Anuncio,
    InfoUsuario,
  },
  data() {
    return {
      id: 0,
      Anuncio: {},
      donoAnuncio: {},
      ImagensAnuncio: [],
    };
  },
  methods: {
    async dadosAnuncio() {
      this.id = this.getCookie("idAnuncio");
      axios({
        method: "GET",
        url:
          process.env.VUE_APP_TRADE_API_URL +
          "/public/advertisement/" +
          this.id,
      }).then(
        (resp) => (
          (this.Anuncio = resp.data),
          (this.ImagensAnuncio = resp.data.photosUrl),
          (this.donoAnuncio = resp.data.advertiser)
        )
      );
    },
    getCookie(name) {
      const value = `; ${document.cookie}`;
      const parts = value.split(`; ${name}=`);
      if (parts.length === 2) return parts.pop().split(";").shift();
    },
  },
  mounted() {
    this.dadosAnuncio();
  },
};
</script>

<style scoped>
h4 {
  margin: 0;
}

.divTitulo {
  padding: 10px;
  margin: 10px;
}

.userInfo {
  position: relative;
  margin-bottom: 60px;
}

.button {
  display: flex;
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100vw;
}

.btnFazerProposta {
  width: 100%;
  padding: 15px 10px;
  color: white;
  background: var(--color2);
  border: none;
  box-shadow: 0 0 0.4rem var(--color1);
  transition: all 0.3s;
}

.btnFazerProposta:hover {
  background: var(--color1);
}

@media (min-width: 800px) {
  .userInfo {
    display: flex;
    margin: 10px 0;
    align-items: center;
  }

  .user {
    width: 70%;
  }

  .divInfoUsuario {
    flex-direction: row;
  }

  .button {
    display: flex;
    position: relative;
    justify-content: center;
    width: 30%;
  }

  .btnFazerProposta {
    border-radius: 5px;
    box-shadow: none;
  }
}
</style>