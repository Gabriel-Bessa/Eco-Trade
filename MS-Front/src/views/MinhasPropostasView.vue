<template>
  <div class="main container">
    <div class="MinhasPropostas">
      <div class="BarraSuperior">Propostas:</div>
      <div class="Cards">
      <CardPropostas
        v-for="proposta in arrayPropostas"
        :key="proposta"
        :BigProposta="true"
        :ImgOnSide="true"
        :PropostaInfo="proposta"
        :EditButton="true"
        :Chip="true"
      />
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Header/Navbar.vue";
import CardPropostas from "@/components/CardPropostas.vue";
import axios from "axios";

export default {
  name: "MinhasPropostas",
  components: {
    Navbar,
    CardPropostas,
  },

  data() {
    return {
      imageModal: "",
      isModalOpen: true,
      arrayPropostas: [],
    };
  },
  methods: {
    async pegarPropostas() {
      const res = axios({
        method: "GET",
        url: process.env.VUE_APP_TRADE_API_URL + "/offer/my-offers",
      }).then(
        (resp) => {
            this.arrayPropostas = resp.data
          }
        );
    },
  },
  mounted() {
    this.pegarPropostas();
  },
};
</script>

<style scoped>
.MinhasPropostas {
  justify-content: flex;
  display: flex;
  flex-direction: column;
  grid-gap: 5px;
  padding: 10px;
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
