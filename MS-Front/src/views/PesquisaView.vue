<template>
  <div class="home">
    <div class="main container">
      <div class="divAnuncios">
        <div class="divTop">
          <h5 class="tituloAnuncios">
            <strong> Resultados para: </strong>
            <span class="textPesquisa">{{ userInput }}</span>
          </h5>
          <button
            v-on:click="menuFiltrar()"
            type="button"
            class="btn btn-success btnFiltrar"
          >
            Filtrar
          </button>
        </div>

        <div class="content">
          <div :class="{ showMenu: isFiltrarActive }" class="menuFiltrar">
            <strong class="titCategorias">Categorias:</strong>
            <div class="divFiltros">
              <button
                v-on:click="buscaCategoria('antiguidade')"
                type="button"
                class="btn btn-success"
              >
                Antiguidades
              </button>
              <button
                v-on:click="buscaCategoria('brinquedo')"
                type="button"
                class="btn btn-success"
              >
                Brinquedos
              </button>
              <button
                v-on:click="buscaCategoria('casa')"
                type="button"
                class="btn btn-success"
              >
                Casa
              </button>
              <button
                v-on:click="buscaCategoria('celular')"
                type="button"
                class="btn btn-success"
              >
                Celulares
              </button>
              <button
                v-on:click="buscaCategoria('eletrodomestico')"
                type="button"
                class="btn btn-success"
              >
                Eletrodomesticos
              </button>
              <button
                v-on:click="buscaCategoria('eletronico')"
                type="button"
                class="btn btn-success"
              >
                Eletrônicos
              </button>
              <button
                v-on:click="buscaCategoria('esporte')"
                type="button"
                class="btn btn-success"
              >
                Esporte
              </button>
              <button
                v-on:click="buscaCategoria('ferramenta')"
                type="button"
                class="btn btn-success"
              >
                Ferramentas
              </button>
              <button
                v-on:click="buscaCategoria('game')"
                type="button"
                class="btn btn-success"
              >
                Games
              </button>
              <button
                v-on:click="buscaCategoria('informatica')"
                type="button"
                class="btn btn-success"
              >
                Informática
              </button>
              <button
                v-on:click="buscaCategoria('moda')"
                type="button"
                class="btn btn-success"
              >
                Moda
              </button>
              <button
                v-on:click="buscaCategoria('musica')"
                type="button"
                class="btn btn-success"
              >
                Música
              </button>
            </div>
          </div>

          <div class="cardAnuncios">
            <Card
              v-for="anuncio in arrayAnuncios"
              :key="anuncio"
              :BigAnuncio="false"
              :ImgOnSide="true"
              :AnuncioInfo="anuncio"
            />
            <span v-if="arrayAnuncios == ''">Nenhum resultado encontrado.</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Header/Navbar.vue";
import Card from "@/components/Home/Card.vue";
import axios from "axios";
export default {
  name: "Pesquisa",
  components: {
    Navbar,
    Card,
  },
  data() {
    return {
      isFiltrarActive: true,
      arrayAnuncios: [],
      userInput: "",
    };
  },
  mounted() {
    this.pegarResultados();
  },
  methods: {
    menuFiltrar() {
      this.isFiltrarActive = !this.isFiltrarActive;
    },
    buscaCategoria(categoria) {
      document.cookie = "search=" + categoria + ";";
      document.cookie = "searchType=" + "category" + ";";
      this.pegarResultados();
    },
    async pegarResultados() {
      //get cookie
      this.userInput = await this.getCookie("search");
      var type = await this.getCookie("searchType");

      if (type === "title") {
        axios({
          method: "POST",
          url:
            process.env.VUE_APP_TRADE_API_URL +
            "/public/advertisement/filter?title=" +
            this.userInput,
        }).then((resp) => {
          this.arrayAnuncios = resp.data.content;
        });
      } else if (type === "category") {
        axios({
          method: "POST",
          url:
            process.env.VUE_APP_TRADE_API_URL +
            "/public/advertisement/filter?category=" +
            this.userInput,
        }).then((resp) => {
          this.arrayAnuncios = resp.data.content;
        });
      }
    },
    getCookie(name) {
      const value = `; ${document.cookie}`;
      const parts = value.split(`; ${name}=`);
      if (parts.length === 2) return parts.pop().split(";").shift();
    },
  },
};
</script>

<style scoped>
.divFiltros {
  display: flex;
  flex-wrap: wrap;
  grid-gap: 2px;
}
.menuFiltrar {
  border-radius: 8px;
  max-width: 100%;
  padding: 5px;
  text-align: left;
  margin-top: 5px;
  display: block;
  background-color: white;
  box-shadow: 0 0 0.2rem var(--color1);
}
.showMenu {
  display: none;
}
.divTop {
  display: grid;
  grid-template-columns: 3fr 1fr;
}
.textPesquisa {
  margin-right: 10%;
}
.cardAnuncios {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  margin: 20px 10px;
}
.tituloAnuncios {
  text-align: left;
  margin-top: 2%;
}
.divAnuncios {
  padding: 10px;
  margin: 5px;
  border-radius: 5px;
}
.modal-body img {
  width: 100%;
  max-width: 450px;
}
.modal-header h5 {
  margin: 0px;
}
@media (max-width: 450px) {
  .myCardPesquisa {
    width: 100%;
    flex-direction: column;
  }
}
@media (min-width: 451px) {
  .myCardPesquisa {
    width: 100%;
    flex-direction: row;
  }
}
@media (min-width: 1000px) {
  .myCardPesquisa {
    width: 32%;
    max-height: 50%;
    flex-direction: column;
  }
  .btnFiltrar {
    display: none;
  }
  .showMenu {
    display: block;
    grid-column: 1;
  }
  .cardAnuncios {
    grid-column: 2;
  }
  .content {
    display: grid;
    grid-template-columns: 1fr 3fr;
    grid-gap: 1%;
  }
  .divFiltros {
    display: flex;
    grid-gap: 5px;
    flex-direction: column;
    padding: 10%;
  }
}
</style>
