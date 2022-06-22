<template>
  <div class="main container">
    <div class="MeusAnuncios">
      <div class="BarraSuperior">
        Meus Anúncios:
        <a href="/#/novo-anuncio" class="btn btn-success">Criar Novo</a>
      </div>
      <div class="Cards">
        <CardAnuncio
          v-for="anuncio in arrayAnuncios"
          :key="anuncio"
          :BigAnuncio="true"
          :ImgOnSide="true"
          :AnuncioInfo="anuncio"
          :EditButton="true"
        />
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Header/Navbar.vue";
import CardAnuncio from "@/components/CardAnuncio.vue";
import axios from "axios";

export default {
  name: "MeusAnuncios",
  components: {
    Navbar,
    CardAnuncio,
  },

  data() {
    return {
      imageModal: "",
      isModalOpen: true,
      arrayAnuncios: [],
    };
  },

  methods: {
    async pegarAnuncios() {
      const token = localStorage.getItem("currentToken");
      axios({
        method: "GET",
        url: process.env.VUE_APP_TRADE_API_URL + "/advertisement/my-advertisement",
      }).then((resp) => (this.arrayAnuncios = resp.data));
    },
  },
  mounted() {
    this.pegarAnuncios();
  },
};
</script>

<style scoped>
.MeusAnuncios {
  justify-content: flex;
  display: flex;
  flex-direction: column;
  grid-gap: 5px;
  padding: 10px;
}

.btn {
  background-color: #38a169;
  justify-content: space-between;
}

.BarraSuperior {
  display: flex;
  justify-content: space-between;
  align-self: flex-start;
  width: 100%;
  padding: 10px;
  font-size: 20px;
}

.Cards {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 10px;
}

@media (min-width: 800px) {
  .maincontainer {
    display: flex;
    justify-content: space-between;
    gap: 10px;
    flex-wrap: nowrap;
  }
}

@media (max-width: 1400px) {
  .Cards {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
}
</style>
