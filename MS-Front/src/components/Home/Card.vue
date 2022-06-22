<template>
  <div
    @click="redirectAnuncio(AnuncioInfo.id)"
    :class="[ImgOnSide ? 'myCardPesquisa' : 'myCard']"
  >
    <img
      v-if="AnuncioInfo"
      :src="AnuncioInfo.photosUrl[0]"
      class="card-img-top"
    />
    <div class="card-body">
      <h5 class="card-title">{{ AnuncioInfo.title }}</h5>
      <!-- <p class="card-description">
        {{ AnuncioInfo.description }}
      </p> -->
      <span v-if="BigAnuncio"> Produto: {{ AnuncioInfo.product }} </span>
      <p v-if="BigAnuncio">Status: Finalizado</p>
    </div>
    <a v-if="EditButton" href="#" class="ButtonStyle"
      ><i class="bi bi-pencil-square"></i
    ></a>
  </div>
</template>

<script>
export default {
  name: "Card",
  props: {
    BigAnuncio: Boolean,
    ImgOnSide: Boolean,
    AnuncioInfo: {},
    EditButton: Boolean,
  },
  methods: {
    redirectAnuncio(id) {
      document.cookie = "idAnuncio=" + id + ";";
      location.href = "/#/anuncio";
    },
  },
};
</script>

<style scoped>
.myCardPesquisa {
  border-radius: 8px;
  display: flex;
  max-width: 100%;
  padding: 5px;
  text-align: left;
  align-items: center;
  flex-direction: row;
  cursor: pointer;
  background-color: white;
  box-shadow: rgb(0 0 0 / 20%) 0px 2px 6px;
  transition: all 0.3s;
}
.myCard {
  flex-wrap: wrap;
  border-radius: 8px;
  display: flex;
  padding: 5px;
  text-align: left;
  align-items: center;
  flex-direction: column;
  cursor: pointer;
  background-color: white;
  box-shadow: rgb(0 0 0 / 20%) 0px 2px 6px;
  transition: all 0.3s;
  min-height: 18.75rem;
}
.myCard:hover,
.myCardPesquisa:hover {
  box-shadow: rgb(0 0 0 / 200%) 0px 2px 6px;
}
.card-img-top {
  height: 180px;
  object-fit: contain;
  max-width: 70%;
  margin-top: 10px;
  background-color: whitesmoke;
  padding: 5px;
  border-radius: 5px;
}
.card-title {
  font-size: 16px;
  font-weight: bold;
}
.card-description {
  font-size: 13px;
}
.card-body {
  padding: 10px;
}

.ButtonStyle {
  align-self: flex-start;
  height: 100%;
  font-size: 30px;
  border: 0;
  background-color: transparent;
  color: black;
  cursor: pointer;
}
</style>



