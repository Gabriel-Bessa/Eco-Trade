<template>
  <div
    @click="redirectAnuncio(AnuncioInfo.id)"
    :class="[ImgOnSide ? 'myCardPesquisa' : 'myCard']"
  >
    <img
      v-if="AnuncioInfo"
      :src="AnuncioInfo?.photosUrl[0]"
      class="card-img-top"
      alt="..."
    />
    <div class="card-body">
      <h5 class="card-title">{{ AnuncioInfo.title }}</h5>
      <p class="card-description">
        {{ AnuncioInfo.description }}
      </p>
      <span v-if="BigAnuncio"> Produto: {{ AnuncioInfo.product }} </span>
      <p v-if="BigAnuncio">Status: {{ translateStatus(AnuncioInfo.status) }}</p>
    </div>
    <a
      @click="(e) => saveEditingId(e, AnuncioInfo.id)"
      v-if="EditButton"
      class="ButtonStyle"
      ><i class="bi bi-pencil-square"></i
    ></a>
    <div class="trash-button">
      <a @click="(e) => deleteById(e, AnuncioInfo.id)">
        <i class="bi bi-trash-fill"></i>
      </a>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import axios from "axios";
export default {
  name: "CardAnuncio",
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

    saveEditingId(e, id) {
      e.stopPropagation();
      document.cookie = "id=" + id + ";";
      window.location.href = "/#/editar-anuncio";
    },

    deleteById(e, id) {
      e.stopPropagation();
      Swal.fire({
        title: "Você tem certeza?",
        text: "Isso não poderá ser desfeito!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Deletar",
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            await axios({
              method: "DELETE",
              url: process.env.VUE_APP_TRADE_API_URL + `/advertisement/${id}`,
            });
            Swal.fire("Deletado!", "Seu anúncio foi deletado", "success");
          } catch (error) {
            Swal.fire("Oops!", "Algo de errado ocorreu!", "danger");
          }
        }
      });
    },

    translateStatus(value) {
      switch (value) {
        case "OPEN":
          return "Em aberto";
        case "RECIVED":
          return "Recebido";
        case "CLOSE":
          return "Fechado";
        case "ACCEPTED":
          return "Aceita";
        default:
          return value;
      }
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
  position: relative;
}
.trash-button {
  padding: 8px;
  position: absolute;
  bottom: 0;
  right: 0;
  font-size: 1.5rem;
}
.trash-button a {
  padding: 3px;
  border-radius: 8px;
}
.trash-button a:hover {
  background-color: #ddd;
}
.myCard {
  flex: 1;
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
  position: relative;
}
.card-img-top {
  height: 100px;
  object-fit: contain;
  max-width: 150px;
  margin-top: 10px;
}
.myCard:hover,
.myCardPesquisa:hover {
  box-shadow: rgb(0 0 0 / 200%) 0px 2px 6px;
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
  font-size: 30px;
  border: 0;
  background-color: transparent;
  color: black;
  cursor: pointer;
  padding: 3px;
  border-radius: 8px;
}

.ButtonStyle:hover {
  background-color: #ddd;
}
</style>



