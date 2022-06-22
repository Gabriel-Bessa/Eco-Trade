<template>
  <div v-if="!TodasCategorias" class="divCategorias categorias-cel">
    <div v-on:click="redirectCategoria('antiguidade')" class="categoria">
      <img src="@/assets/categorias/antiguidade.png" />
      <span>Antiguidades</span>
    </div>
    <div v-on:click="redirectCategoria('brinquedo')" class="categoria">
      <img src="@/assets/categorias/brinquedo.png" />
      <span>Brinquedos</span>
    </div>
    <div v-on:click="redirectCategoria('casa')" class="categoria">
      <img src="@/assets/categorias/casa.png" />
      <span>Casa</span>
    </div>
    <div
      class="categoria"
      data-bs-toggle="modal"
      data-bs-target="#exampleModal"
    >
      <img src="@/assets/categorias/vermais.png" />
      <span>Ver mais</span>
    </div>
  </div>
  <div v-if="!TodasCategorias" class="divCategorias categorias-desktop">
    <div v-on:click="redirectCategoria('antiguidade')" class="categoria">
      <img src="@/assets/categorias/antiguidade.png" />
      <span>Antiguidades</span>
    </div>
    <div v-on:click="redirectCategoria('brinquedo')" class="categoria">
      <img src="@/assets/categorias/brinquedo.png" />
      <span>Brinquedos</span>
    </div>
    <div v-on:click="redirectCategoria('casa')" class="categoria">
      <img src="@/assets/categorias/casa.png" />
      <span>Casa</span>
    </div>
    <div v-on:click="redirectCategoria('celular')" class="categoria">
      <img src="@/assets/categorias/celulares.png" />
      <span>Celulares</span>
    </div>
    <div v-on:click="redirectCategoria('eletrodomestico')" class="categoria">
      <img src="@/assets/categorias/eletrodomesticos.png" />
      <span>Eletrodomesticos</span>
    </div>
    <div v-on:click="redirectCategoria('eletronico')" class="categoria">
      <img src="@/assets/categorias/eletronico.png" />
      <span>Eletrônicos</span>
    </div>
    <div v-on:click="redirectCategoria('esporte')" class="categoria">
      <img src="@/assets/categorias/esporte.png" />
      <span>Esporte</span>
    </div>
    <div
      class="categoria"
      data-bs-toggle="modal"
      data-bs-target="#exampleModal"
    >
      <img src="@/assets/categorias/vermais.png" />
      <span>Ver mais</span>
    </div>
  </div>
  <div v-if="TodasCategorias" class="TodasCategorias">
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('antiguidade')"
      class="categoria"
    >
      <img src="@/assets/categorias/antiguidade.png" />
      <span>Antiguidades</span>
    </div>
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('brinquedo')"
      class="categoria"
    >
      <img src="@/assets/categorias/brinquedo.png" />
      <span>Brinquedos</span>
    </div>
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('casa')"
      class="categoria"
    >
      <img src="@/assets/categorias/casa.png" />
      <span>Casa</span>
    </div>
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('celular')"
      class="categoria"
    >
      <img src="@/assets/categorias/celulares.png" />
      <span>Celulares</span>
    </div>
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('eletrodomestico')"
      class="categoria"
    >
      <img src="@/assets/categorias/eletrodomesticos.png" />
      <span>Eletrodomesticos</span>
    </div>
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('eletronico')"
      class="categoria"
    >
      <img src="@/assets/categorias/eletronico.png" />
      <span>Eletrônicos</span>
    </div>
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('esporte')"
      class="categoria"
    >
      <img src="@/assets/categorias/esporte.png" />
      <span>Esporte</span>
    </div>
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('ferramenta')"
      class="categoria"
    >
      <img src="@/assets/categorias/ferramentas.png" />
      <span>Ferramentas</span>
    </div>
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('game')"
      class="categoria"
    >
      <img src="@/assets/categorias/games.png" />
      <span>Games</span>
    </div>
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('informatica')"
      class="categoria"
    >
      <img src="@/assets/categorias/informatica.png" />
      <span>Informática</span>
    </div>
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('moda')"
      class="categoria"
    >
      <img src="@/assets/categorias/moda.png" />
      <span>Moda</span>
    </div>
    <div
      data-bs-dismiss="modal"
      v-on:click="redirectCategoria('musica')"
      class="categoria"
    >
      <img src="@/assets/categorias/musica.png" />
      <span>Música</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Categorias",
  props: {
    TodasCategorias: Boolean,
  },
  methods: {
    async redirectCategoria(categoria) {
      document.cookie = (await "search=") + categoria + ";";
      document.cookie = (await "searchType=") + "category" + ";";
      location.href = "/#/pesquisa";
    },
  },
};
</script>

<style scoped>
.categoria {
  font-size: 14px;
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
}
.categoria img:hover {
  -webkit-filter: brightness(60%);
  transition: all 0.5s ease;
}
.divCategorias,
.TodasCategorias {
  margin: 10px 20px;
  display: flex;
  justify-content: space-evenly;
}
.divCategorias img,
.TodasCategorias img {
  max-width: 75px;
  border-radius: 50%;
}
.TodasCategorias {
  flex-wrap: wrap;
  grid-gap: 15px;
}
.categorias-desktop {
  display: none;
}
@media (min-width: 800px) {
  .categorias-cel {
    display: none;
  }
  .categorias-desktop {
    display: flex;
  }
}
</style>