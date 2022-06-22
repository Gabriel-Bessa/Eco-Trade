<template>
  <div class="divInfoUsuario">
    <div class="imagem">
      <img :src="dadosUsuario.url" alt="" />
    </div>
    <div class="info">
      <div class="interessado">
        <strong v-if="isAnunciante == true">Anunciante: </strong>
        <strong v-if="isAnunciante == false">Interessado: </strong>
        <span>{{ dadosUsuario.name }}</span>
      </div>
      <div class="confiabilidade">
        <strong>Confiabilidade: </strong>
        <span>{{ dadosUsuario.reliability }}%</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "InfoUsuario",
  props: {
    dadosUsuario: {},
    isAnunciante: Boolean,
  },
  methods: {
    abrirPerfil() {
    },
  },
};
</script>

<style scoped>
.divInfoUsuario {
  padding: 10px;
  margin: 10px;
  background-color: white;
  box-shadow: rgb(0 0 0 / 20%) 0px 2px 6px;
  border-radius: 5px;
  display: flex;
  justify-content: space-evenly;
  font-size: 15px;
  text-decoration: none;
  color: black;
}
.imagem img {
  clip-path: circle();
  max-width: 80px;
  object-fit: cover;
}
.interessado,
.confiabilidade {
  display: flex;
  grid-gap: 10px;
  width: fit-content;
}
.row {
  margin: 15px 0px 10px 0px;
}
.info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100%;
  margin-top: 15px;
}
@media (min-width: 900px) {
  .divInfoUsuario {
    flex-direction: column;
    margin: 0px 10px;
  }
}
</style>
