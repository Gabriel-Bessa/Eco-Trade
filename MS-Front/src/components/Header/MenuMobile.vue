<template>
  <div class="menu">
    <!-- USER LOGADO -->
    <div v-if="isLogado == true" class="profile">
      <router-link @click="toggleMenu()" to="/perfil">
        <div class="image_profile">
          <img :src="userInfo?.url" alt="" />
        </div>
        <div class="data_profile">
          <p>Olá, {{ userInfo?.name }}</p>
          <p>Ecopoints: {{ userInfo.ecopoints }}</p>
        </div>
      </router-link>
    </div>

    <!-- USER DESLOGADO -->
    <div v-if="isLogado == false" class="profile">
      <div class="image_profile">
        <i class="bi bi-person-circle"></i>
      </div>
      <div class="data_profile">
        <router-link @click="toggleMenu()" to="/login">entrar</router-link>
        <router-link @click="toggleMenu()" to="/cadastro"
          >cadastrar</router-link
        >
      </div>
    </div>
    <div v-if="isLogado == true" class="section">
      <router-link @click="toggleMenu()" to="/ecopoints"
        ><i class="bi bi-droplet-fill"></i>ECOPOINTS</router-link
      >
    </div>

    <div class="section">
      <router-link @click="toggleMenu()" to="/"
        ><i class="bi bi-house-fill"></i>INICIO</router-link
      >
      <router-link
        @click="toggleMenu()"
        to="/meus-anuncios"
        v-if="isLogado == true"
        ><i class="bi bi-megaphone-fill"></i>ANÚNCIOS</router-link
      >
      <router-link
        @click="toggleMenu()"
        to="/minhas-propostas"
        v-if="isLogado == true"
        ><i class="bi bi-graph-up"></i>PROPOSTAS</router-link
      >
      <router-link @click="toggleMenu()" to="/sobre"
        ><i class="bi bi-info-circle"></i>SOBRE</router-link
      >
      <a href="/" @click="sair()" v-if="isLogado == true"
        ><i class="bi bi-box-arrow-right"></i>SAIR</a
      >
    </div>

    <div class="section">
      <p>CATEGORIAS</p>
      <a
        @click="
          toggleMenu();
          redirectCategoria('antiguidade');
        "
        ><i class="bi bi-chevron-compact-right"></i>Antiguidades</a
      >
      <a
        @click="
          toggleMenu();
          redirectCategoria('brinquedo');
        "
        ><i class="bi bi-chevron-compact-right"></i>Brinquedos</a
      >
      <a
        @click="
          toggleMenu();
          redirectCategoria('casa');
        "
        ><i class="bi bi-chevron-compact-right"></i>Casa</a
      >
      <a
        @click="
          toggleMenu();
          redirectCategoria('celular');
        "
        ><i class="bi bi-chevron-compact-right"></i>Celulares</a
      >
      <a
        @click="
          toggleMenu();
          redirectCategoria('eletrodomestico');
        "
        ><i class="bi bi-chevron-compact-right"></i>Eletrodomesticos</a
      >
      <a
        @click="
          toggleMenu();
          redirectCategoria('eletronico');
        "
        ><i class="bi bi-chevron-compact-right"></i>Eletrônicos</a
      >
      <a
        @click="
          toggleMenu();
          redirectCategoria('esporte');
        "
        ><i class="bi bi-chevron-compact-right"></i>Esporte</a
      >
      <a
        @click="
          toggleMenu();
          redirectCategoria('ferramenta');
        "
        ><i class="bi bi-chevron-compact-right"></i>Ferramentas</a
      >
      <a
        @click="
          toggleMenu();
          redirectCategoria('game');
        "
        ><i class="bi bi-chevron-compact-right"></i>Games</a
      >
      <a
        @click="
          toggleMenu();
          redirectCategoria('informatica');
        "
        ><i class="bi bi-chevron-compact-right"></i>Informática</a
      >
      <a
        @click="
          toggleMenu();
          redirectCategoria('moda');
        "
        ><i class="bi bi-chevron-compact-right"></i>Moda</a
      >
      <a
        @click="
          toggleMenu();
          redirectCategoria('musica');
        "
        ><i class="bi bi-chevron-compact-right"></i>Música</a
      >
    </div>
  </div>
</template>

<script>
export default {
  props: {
    userInfo: {},
    isLogado: Boolean,
    toggleMenu: { type: Function },
  },
  methods: {
    sair() {
      window.localStorage.removeItem("currentToken");
      window.localStorage.removeItem("infoUser");
      location.reload();
    },
    async redirectCategoria(categoria) {
      document.cookie = (await "search=") + categoria + ";";
      document.cookie = (await "searchType=") + "category" + ";";

      if (document.URL.includes("pesquisa")) {
        location.reload();
      } else {
        location.href = "/#/pesquisa";
      }
    },
  },
};
</script>

<style scoped>
a {
  color: #fff;
  text-decoration: none;
  cursor: pointer;
  padding: 0.5rem 0;
  transition: all 0.3s;
}

a:hover {
  color: #fff;
  background: var(--color1);
}

i {
  margin-right: 10px;
  font-size: 1rem;
}

.menu {
  padding: 0.5rem 0.5rem;
  font-size: 0.9rem;
  color: #fff;
  white-space: nowrap;
  margin-top: 10px;
}

.profile {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0.7rem 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  text-align: left;
}

.profile a {
  display: flex;
  width: 100%;
}

.profile p {
  margin: 0px;
}

.image_profile img {
  width: 3rem;
  height: 3rem;
  margin-right: 10px;
  clip-path: circle();
  object-fit: cover;
}

.image_profile i {
  font-size: 2rem;
}

.data_profile a {
  padding: 0;
}

.data_profile a:hover {
  color: var(--color1);
  background: transparent;
}

.section {
  display: flex;
  flex-direction: column;
  height: auto;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  text-align: left;
}

.section p {
  margin: 0;
  padding: 0.5rem 0;
}
</style>