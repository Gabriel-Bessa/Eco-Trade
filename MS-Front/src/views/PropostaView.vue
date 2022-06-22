<template>
  <div class="container">
    <div class="divTitulo">
      <h4><strong> Análise de proposta</strong></h4>
    </div>
    <Anuncio
      :MostrarStatus="false"
      :MostrarUsuario="true"
      :dadosAnuncio="Anuncio"
      :imagemAnuncio="ImagensAnuncio"
      :dadosUsuario="donoAnuncio"
    />
    <div class="divProposta">
      <Proposta :dadosProposta="Proposta" :imagemProposta="ImagensProposta" />
      <InfoUsuario :dadosUsuario="donoProposta" />
    </div>
    <div class="divStatus">
      <span
        >Status: <strong> {{ statusProposta }} </strong></span
      >
      <div v-if="exibirBotoes" class="divButtons">
        <button @click="aceitarProposta('REJECTED')" class="btnRecusar">
          Recusar
        </button>
        <button @click="aceitarProposta('ACCEPTED')" class="btnAceitar">
          Aceitar
        </button>
      </div>
      <div v-if="exibirConfirmação" class="divButtons">
        <button @click="confirmarRecebimento()" class="btnConfirmar">
          Confirmar recebimento
        </button>
      </div>
    </div>
    <h5>Chat aqui!</h5>
  </div>
</template>

<script>
import Navbar from "@/components/Header/Navbar.vue";
import Anuncio from "@/components/Anuncio.vue";
import Proposta from "@/components/Proposta.vue";
import InfoUsuario from "@/components/InfoUsuario.vue";
import Chat from "@/components/Chat.vue";
import axios from "axios";
import Swal from 'sweetalert2/dist/sweetalert2.js'

export default {
  name: "PropostaPage",
  components: {
    Navbar,
    Anuncio,
    Proposta,
    InfoUsuario,
    Chat,
  },
  data() {
    return {
      idAnuncio: 0,
      idProposta: 0,
      Anuncio: {},
      Proposta: {},
      ImagensAnuncio: [],
      ImagensProposta: [],
      donoProposta: {},
      donoAnuncio: {},
      statusProposta: "",
      exibirBotoes: false,
      exibirConfirmação: false,
      usuarioLogado: "",
    };
  },
  mounted() {
    this.usuarioLogado = JSON.parse(localStorage.getItem("user"));
    this.dadosOferta();
  },
  methods: {
    dadosOferta() {
      this.idProposta = this.getCookie("idProposta");
      this.idAnuncio = this.getCookie("idAnuncio");
      axios({
        method: "GET",
        url:
          process.env.VUE_APP_TRADE_API_URL +
          "/advertisement/" +
          this.idAnuncio +
          "/offer/" +
          this.idProposta,
      }).then((resp) => {
        this.Proposta = resp.data.offer;
        this.ImagensProposta = resp.data.offer.photosUrl;
        this.donoProposta = resp.data.offer.offerer;

        this.Anuncio = resp.data;
        this.ImagensAnuncio = resp.data.photosUrl;
        this.donoAnuncio = resp.data.advertiser;

        switch (String(this.Proposta.status)) {
          case "OPEN":
            this.statusProposta = "Em análise";
            break;
          case "APPROVED":
            this.statusProposta = "Aceita";
            this.exibirConfirmação = true;
            break;
          case "REJECTED":
            this.statusProposta = "Rejeitada";
            break;
          case "RECIVED":
            this.statusProposta = "Recebida";
            break;
          default:
            this.statusProposta = this.Proposta.status;
            break;
        }

        this.checkToShowButtons();
      });
    },
    aceitarProposta(status) {
      axios({
        method: "POST",
        url:
          process.env.VUE_APP_TRADE_API_URL +
          "/advertisement/" +
          this.idAnuncio +
          "/status/offer/" +
          this.idProposta +
          "?status=" +
          status,
      }).then((resp) => {
        Swal.fire("Sucesso!", "A proposta foi aceita com sucesso!", "success");
        location.reload();
      });
    },
    confirmarRecebimento() {
      axios({
        method: "POST",
        url:
          process.env.VUE_APP_TRADE_API_URL +
          "/receivement/advertisement/" +
          this.idAnuncio +
          "/offer/" +
          this.idProposta,
      }).then((resp) => {
        Swal.fire("Sucesso!", "A confirmação foi realizada com sucesso!", "success");
        location.reload();
      });
    },
    checkToShowButtons() {
      if (
        this.usuarioLogado.email == this.Anuncio.advertiser.email &&
        this.Proposta.status == "OPEN"
      ) {
        this.exibirBotoes = true;
      } else {
        this.exibirBotoes = false;
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
.divTitulo {
  margin: 20px 15px;
  text-align: left;
}
.divStatus {
  padding: 10px;
  margin: 10px;
  background-color: white;
  box-shadow: rgb(0 0 0 / 20%) 0px 2px 6px;
  border-radius: 5px;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
}
.divButtons {
  display: flex;
  align-items: center;
  flex-direction: column;
  grid-gap: 5px;
}
.divButtons button {
  color: white;
  border-radius: 5px;
}
.btnRecusar {
  background-color: red;
  width: 100px;
}
.btnAceitar {
  background-color: green;
  width: 100px;
}
.btnConfirmar {
  background-color: green;
}
@media (max-width: 800px) {
  .container {
    padding: 0px;
  }
}
@media (min-width: 900px) {
  .divProposta {
    display: flex;
  }
  .divButtons {
    display: flex;
    flex-direction: row;
    grid-gap: 20px;
  }
}
</style>