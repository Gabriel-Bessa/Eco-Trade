<template>
  <div class="divAnuncio">
    <div class="divPhotoAnuncio">
      <div class="grid-container">
        <div class="principalPhoto">
          <img :src="imagemAnuncio[0]" />
        </div>
        <div class="divPhoto">
          <img
            v-for="imagem in imagemAnuncio.slice(1)"
            :key="imagem"
            v-on:click="expandImage(imagem)"
            :src="imagem"
            data-bs-toggle="modal"
            data-bs-target="#AnuncioModal"
          />
        </div>
      </div>
    </div>
    <div class="divInfoAnuncio">
      <h4 class="titulo">{{ dadosAnuncio.title }}</h4>
      <h5 class="produto">{{ dadosAnuncio.product }}</h5>
      <p v-if="MostrarStatus" class="status">
        Status: <strong> {{ dadosAnuncio.status }} </strong>
      </p>
      <p v-if="MostrarUsuario" class="usuario">
        Ofertante: <strong> {{ dadosUsuario.name }}</strong>
      </p>
      <!-- <p v-if="!MostrarStatus" class="status">
        <strong>Ofertante:</strong> Fulano de Tal
      </p> -->
      <p class="descricao">
        {{ dadosAnuncio.description }}
      </p>
    </div>
  </div>

  <div
    class="modal fade"
    id="AnuncioModal"
    tabindex="-1"
    aria-labelledby="AnuncioModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-xl modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <img :src="imageModal" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Anuncio",
  methods: {
    expandImage(src) {
      this.imageModal = src;
    },
  },
  props: {
    MostrarStatus: Boolean,
    MostrarUsuario: Boolean,
    dadosAnuncio: {},
    dadosUsuario: {},
    imagemAnuncio: Array,
  },
  data() {
    return {
      imageModal: "",
    };
  },
};
</script>

<style scoped>
.divAnuncio {
  margin: 10px;
  box-shadow: rgb(0 0 0 / 20%) 0px 2px 6px;
  background-color: white;
  border-radius: 5px;
}
.divPhotoAnuncio {
  margin: 20px;
}
.grid-container {
  display: grid;
  grid-gap: 0;
  grid-template-columns: 5fr 1fr;
  flex-wrap: wrap;
}
.principalPhoto {
  padding: 20px;
  grid-column-start: 1;
}
.principalPhoto img {
  width: 100%;
  max-width: 450px;
}
.divPhoto {
  grid-column-start: 2;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
}
.divPhoto img {
  margin: 10px 0px;
  width: 100%;
  max-width: 100px;
}
.divInfoAnuncio {
  margin: 10px 20px;
  padding: 10px;
  text-align: left;
}
.titulo {
  font-weight: bold;
}
.modal-body img {
  width: 100%;
  max-width: 450px;
}
.modal-header h5 {
  margin: 0px;
}
@media (max-width: 900px) {
  .grid-container {
    grid-template-columns: 1fr;
  }
  .divPhoto {
    grid-row: 2;
    grid-column-start: 1;
    flex-direction: row;
    overflow-x: scroll;
    grid-gap: 5px;
  }
}
@media (min-width: 900px) {
  .divAnuncio {
    display: grid;
    grid-template-columns: 1fr 1fr;
  }
}
</style>
