<template>
  <div class="main container">
    <div class="EditarAnuncio">
      <div class="BarraSuperior">
        Editar Anúncio:
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
          id="title"
          placeholder="Título:"
        />
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
          <textarea
            class="form-control"
            id="interests"
            rows="4"
            cols="50"
            placeholder="Interesses de troca:"
          ></textarea>
        </div>
        <div class="Categorias">
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Antiguidades" type="checkbox" /> Antiguidades
            </label>
          </div>
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Brinquedos" type="checkbox" /> Brinquedos
            </label>
          </div>
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Casa" type="checkbox" /> Casa
            </label>
          </div>
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Celulares" type="checkbox" /> Celulares
            </label>
          </div>
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Eletrodomesticos" type="checkbox" /> Eletrodomesticos
            </label>
          </div>
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Eletrônicos" type="checkbox" /> Eletrônicos
            </label>
          </div>
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Esporte" type="checkbox" /> Esporte
            </label>
          </div>
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Ferramentas" type="checkbox" /> Ferramentas
            </label>
          </div>
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Games" type="checkbox" /> Games
            </label>
          </div>
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Informática" type="checkbox" /> Informática
            </label>
          </div>
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Moda" type="checkbox" /> Moda
            </label>
          </div>
          <div class="btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary active">
              <input name="Música" type="checkbox" /> Música
            </label>
          </div>
        </div>
      </div>
    </div>
    <UploadFile />
  </div>
</template>

<script>
import Navbar from "@/components/Header/Navbar.vue";
import CardAnuncio from "@/components/CardAnuncio.vue";
import axios from "axios";
import UploadFile from "@/components/UploadFile/UploadFile.vue";
import Swal from "sweetalert2";

export default {
  name: "EditarAnuncio",
  components: {
    Navbar,
    CardAnuncio,
    UploadFile,
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
      const Imagem = document.getElementById("upload").files;
      const Título = document.getElementById("title").value;
      const Produto = document.getElementById("product").value;
      const Descrição = document.getElementById("description").value;
      const InteressesDeTroca = document.getElementById("interests").value;
      const TodasCategorias = document.querySelectorAll("input[type=checkbox]");
      const Categorias = Array.from(TodasCategorias)
        .filter((categoria) => categoria.checked)
        .map((categoria) => categoria.name);

      this.IsLoading = true;
      const formData = new FormData();
      formData.append(
        "form",
        new Blob(
          [
            JSON.stringify({
              id: this.AnuncioData.id,
              title: Título,
              product: Produto,
              tradePossibility: InteressesDeTroca,
              description: Descrição,
              categories: Categorias,
            }),
          ],
          { type: "application/json" }
        )
      );
      console.log(formData);
      if (Imagem.lenght > 0) {
        for (let i = 0; i < Imagem.length; i++) {
          formData.append("files", this.createblob(Imagem[i]));
        }
      }
      console.log(formData);
      try {
        await axios({
          method: "PUT",
          url: process.env.VUE_APP_TRADE_API_URL + "/advertisement",
          data: formData,
        });
        Swal.fire("Sucesso!", "O anúncio foi editado com sucesso!", "success");
      } finally {
        this.IsLoading = false;
      }
    },

    async pegarAnuncio() {
      const id = this.getCookie("id");
      if (id)
        axios({
          method: "GET",
          url:
            process.env.VUE_APP_TRADE_API_URL +
            "/advertisement/" +
            this.getCookie("id"),
        }).then((resp) => {
          this.AnuncioData = resp.data;
          document.getElementById("title").value = resp.data.title;
          document.getElementById("product").value = resp.data.product;
          document.getElementById("description").value = resp.data.description;
          document.getElementById("interests").value =
            resp.data.tradePossibility;
          const TodasCategorias = document.querySelectorAll(
            "input[type=checkbox]"
          );
          const Categorias = Array.from(TodasCategorias).filter((categoria) =>
            resp.data.categories.includes(categoria.name)
          );
          for (const categoria of Categorias) {
            document.querySelector(`[name=${categoria.name}]`).checked = true;
          }
        });
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
.EditarAnuncio {
  justify-content: flex;
  display: flex;
  flex-direction: column;
  grid-gap: 5px;
  padding: 10px;
}

.btn {
  background-color: #38a169;
  justify-content: center;
  display: flex;
  align-items: center;
  gap: 10px;
}

.Categorias {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 10px;
  padding: 10px;
  border: 1px solid #ced4da;
  background: white;
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

@media (max-width: 1400px) {
  .Cards {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
}
</style>
