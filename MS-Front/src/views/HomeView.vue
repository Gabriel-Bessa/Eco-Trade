<template>
  <div class="home">
    <div class="main container">
      <Carousel class="mr-t-1" />
      <div class="divCategorias">
        <Categorias :TodasCategorias="false" />
      </div>
      <div class="divDestaques">
        <h5 class="tituloDestaques"><strong> Destaques </strong></h5>
        <div class="cardDestaques">
          <Card
            v-for="anuncio in arrayAnuncios"
            :key="anuncio"
            :BigAnuncio="false"
            :ImgOnSide="false"
            :AnuncioInfo="anuncio"
          />
          <span v-if="arrayAnuncios == ''">Nenhum anúncio encontrado.</span>
        </div>
      </div>
    </div>

    <div
      class="modal fade"
      id="exampleModal"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-xl modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5>Categorias</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <Categorias TodasCategorias="true" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Header/Navbar.vue";
import Categorias from "@/components/Home/Categorias.vue";
import Carousel from "@/components/Home/Carousel.vue";

import Card from "@/components/Home/Card.vue";
import axios from "axios";
export default {
  name: "HomeView",
  components: {
    Navbar,
    Carousel,
    Categorias,
    Card,
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
      const res = axios({
        method: "POST",
        url:
          process.env.VUE_APP_TRADE_API_URL +
          "/public/advertisement/filter?size=20",
      }).then((resp) => (this.arrayAnuncios = resp.data.content));
    },
  },
  mounted() {
    this.pegarAnuncios();
    document.cookie = "idProposta=" + 6 + ";";
    document.cookie = "idAnuncio=" + 6 + ";";
  },
};
</script>

<style scoped>
.cardDestaques {
  display: flex;
  gap: 2rem;
  flex-wrap: wrap;
  margin: 20px 10px;
  justify-content: left;
}
.tituloDestaques {
  text-align: left;
}
.divDestaques {
  padding: 10px;
  margin: 3% 1%;
  border-radius: 5px;
}
.divCategorias {
  margin: 3% 0%;
}
.modal-body img {
  width: 100%;
  max-width: 450px;
}
.modal-header h5 {
  margin: 0px;
}
.container {
  padding: 0px;
}
.mr-t-1 {
  margin-top: 10px;
}

@media (max-width: 400px) {
  .myCard {
    width: 100%;
    flex-direction: column;
  }
  .cardDestaques {
    gap: 1rem;
  }
}
@media (min-width: 401px) {
  .myCard {
    width: 48%;
  }
  .cardDestaques {
    gap: 0.5rem;
  }
}
@media (min-width: 1000px) {
  .myCard {
    width: 23%;
  }
  .cardDestaques {
    gap: 1.5rem;
  }
}

@media (min-width: 1400px) {
  .cardDestaques {
    gap: 2rem;
  }
}
</style>
