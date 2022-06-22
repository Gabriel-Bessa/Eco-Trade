<template>
  <div class="main container">
    <div class="NovaProposta">
      <div class="BarraSuperior">
        Nova Proposta:
        <button
          :disabled="IsLoading"
          type="button"
          class="btn btn-success"
          v-on:click="submit()"
        >
          <span
            :class="IsLoading && 'spinner-border spinner-border-sm'"
            role="status"
            aria-hidden="true"
          ></span>
          Enviar
        </button>
      </div>
      <div class="FormGroup">
        <input
          type="text"
          class="form-control"
          id="product"
          placeholder="Produto:"
        />
        <div class="BigForm">
          <textarea
            class="form-control"
            id="description"
            rows="4"
            cols="50"
            placeholder="Descrição:"
          ></textarea>
        </div>
      </div>
      <UploadFile />
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Header/Navbar.vue";
import UploadFile from "@/components/UploadFile/UploadFile.vue";
import axios from "axios";
import Swal from "sweetalert2";
import CardPropostas from "@/components/CardPropostas.vue";

export default {
  name: "EditarProposta",
  components: {
    Navbar,
    UploadFile,
    CardPropostas,
  },

  data() {
    return {
      IsLoading: false,
      AnuncioData: {},
    };
  },

  methods: {
    createblob(Imagem) {
      return new Blob([Imagem], { type: "multipart/form-data" });
    },

    async submit() {
      const id = this.getCookie("idAnuncio");
      const Imagem = document.getElementById("upload").files;
      const Produto = document.getElementById("product").value;
      const Descrição = document.getElementById("description").value;

      this.IsLoading = true;
      const formData = new FormData();
      formData.append(
        "form",
        new Blob(
          [
            JSON.stringify({
              product: Produto,
              description: Descrição,
              advertisementId: id,
            }),
          ],
          { type: "application/json" }
        )
      );

      for (let i = 0; i < Imagem.length; i++) {
        formData.append("files", this.createblob(Imagem[i]));
      }
      try {
        await axios({
          method: "POST",
          url: process.env.VUE_APP_TRADE_API_URL + "/offer",
          data: formData,
        });
        Swal.fire("Sucesso!", "A proposta foi criada com sucesso!", "success");
      } finally {
        this.IsLoading = false;
      }
    },

    async pegarAnuncio() {
      const id = this.getCookie("idAnuncio");
      if (id)
        axios({
          method: "GET",
          url:
            process.env.VUE_APP_TRADE_API_URL +
            "/public/advertisement/" +
            this.getCookie("idAnuncio"),
        }).then((resp) => (this.AnuncioData = resp.data));
    },

    getCookie(name) {
      const value = `; ${document.cookie}`;
      const parts = value.split(`; ${name}=`);
      if (parts.length === 2) return parts.pop().split(";").shift();
    },
  },

  mounted() {
    this.pegarAnuncio();
  },
};
</script>

<style scoped>
.NovaProposta {
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

.FormGroup {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.BigForm {
  display: flex;
  flex-direction: column;
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
</style>