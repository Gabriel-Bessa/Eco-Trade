<template>
  <div class="container">
    <div class="divProposta">
      <div class="row divImgCard">
        <div class="divImagemPrincipal">
          <img :src="imagemProposta[0]" />
        </div>
        <div class="divImagem">
          <img
            v-for="imagem in imagemProposta.slice(1)"
            :key="imagem"
            v-on:click="expandImage(imagem)"
            :src="imagem"
            data-bs-toggle="modal"
            data-bs-target="#PropostaModal"
          />
        </div>
      </div>
      <div class="divDados">
        <div class="divTitleProp">
          <span>
            <strong>{{ dadosProposta.product }} </strong>
          </span>
        </div>
        <div class="divDescProp">
          <span>{{ dadosProposta.description }}</span>
        </div>
      </div>
    </div>
  </div>
  <div
    class="modal fade"
    id="PropostaModal"
    tabindex="-1"
    aria-labelledby="PropostaModalLabel"
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
  name: "Proposta",
  methods: {
    expandImage(src) {
      this.imageModal = src;
    },
  },
  props: {
    MostrarStatus: Boolean,
    dadosProposta: {},
    imagemProposta: Array,
  },
  data() {
    return {
      imageModal: "",
    };
  },
};
</script>

<style scoped>
.divProposta {
  width: 100%;
  box-shadow: rgb(0 0 0 / 20%) 0px 2px 6px;
  background-color: white;
  border-radius: 5px;
  padding: 15px 50px;
  height: 100%;
  display: flex;
  align-items: center;
}
.divImgCard img {
  max-height: 10rem;
  border-radius: 5px;
  object-fit: contain;
}
.divTitleProp {
  margin-top: 8px;
}
.divTitleProp span {
  font-size: 20px;
  text-align: justify;
  margin-bottom: 0;
}
.divDescProp {
  text-align: left;
  margin: 10px 0px;
}
.divImagemPrincipal img {
  max-width: 300px;
  width: 80%;
}
.divImagemPrincipal {
  padding: 5px;
  background-color: whitesmoke;
}
.modal-body img {
  width: 100%;
  max-width: 450px;
}
.modal-header h5 {
  margin: 0px;
}
.divDados {
  height: 100%;
}
.divImgCard {
  display: grid;
}

@media (max-width: 999px) {
  .divImgCard {
    display: grid;
    grid-template-columns: 1fr;
  }
  .divImagem {
    display: flex;
    flex-direction: row;
    grid-gap: 5px;
    grid-row: 2;
    overflow-x: scroll;
    width: 100%;
  }
  .divImagem img {
    width: 20%;
  }
  .divProposta {
    display: grid;
    grid-template-columns: 3fr 2fr;
    gap: 5%;
  }
}
@media (min-width: 1000px) {
  .divProposta {
    display: grid;
    grid-template-columns: 3fr 2fr;
    grid-gap: 20px;
  }
  .divTitleProp {
    text-align: left;
    grid-column: 2;
  }
  .divDescProp {
    grid-column: 2;
  }
  .divImgCard {
    grid-column: 1;
    display: grid;
    grid-template-columns: 2fr 1fr;
  }
  .divImagem {
    grid-column: 2;
    max-width: 50%;
  }
  .divImagem img {
    margin: 3px 0px;
  }
}
</style>