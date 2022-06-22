<template>
  <div class="menu">
    <router-link href="#" to="/">INICIO</router-link>
    <div class="menu_categoria">
      <button
        class="dropdown-toggle"
        type="button"
        id="dropdownMenuButton1"
        data-bs-toggle="dropdown"
        aria-expanded="false"
      >
        CATEGORIAS
      </button>
      <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
        <li>
          <a @click="redirectCategoria('antiguidade')" class="dropdown-item"
            >Antiguidades</a
          >
        </li>
        <li>
          <a @click="redirectCategoria('brinquedo')" class="dropdown-item"
            >Brinquedos</a
          >
        </li>
        <li>
          <a @click="redirectCategoria('casa')" class="dropdown-item">Casa</a>
        </li>
        <li>
          <a @click="redirectCategoria('celular')" class="dropdown-item"
            >Celulares</a
          >
        </li>
        <li>
          <a @click="redirectCategoria('eletrodomestico')" class="dropdown-item"
            >Eletrodomesticos</a
          >
        </li>
        <li>
          <a @click="redirectCategoria('eletronico')" class="dropdown-item"
            >Eletrônicos</a
          >
        </li>
        <li>
          <a @click="redirectCategoria('esporte')" class="dropdown-item"
            >Esporte</a
          >
        </li>
        <li>
          <a @click="redirectCategoria('ferramenta')" class="dropdown-item"
            >Ferramentas</a
          >
        </li>
        <li>
          <a @click="redirectCategoria('game')" class="dropdown-item">Games</a>
        </li>
        <li>
          <a @click="redirectCategoria('informatica')" class="dropdown-item"
            >Informática</a
          >
        </li>
        <li>
          <a @click="redirectCategoria('moda')" class="dropdown-item">Moda</a>
        </li>
        <li>
          <a @click="redirectCategoria('musica')" class="dropdown-item"
            >Música</a
          >
        </li>
      </ul>
    </div>
    <router-link href="#" to="/sobre">SOBRE</router-link>

    <!-- USER DESLOGADO -->
    <div v-if="isLogado == false" class="desk_acesso">
      <div class="links">
        <a href="/#/login">entrar</a>
        <a href="/#/cadastro">cadastrar</a>
      </div>
      <i class="bi bi-person-circle"></i>
    </div>

    <!-- USER LOGADO -->
    <div v-if="isLogado == true" class="menu_acesso">
      <button
        class="dropdown-toggle usuario"
        type="button"
        id="dropdownMenuButton1"
        data-bs-toggle="dropdown"
        aria-expanded="false"
      >
        <div class="desk_acesso">
          <div class="links">
            <p>Olá, {{ userInfo?.name }}</p>
          </div>
          <img :src="userInfo?.url" alt="" />
        </div>
      </button>
      <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
        <li>
          <a class="dropdown-item" href="/#/ecopoints"
            ><i class="bi bi-droplet-fill"></i>ECOPOINTS:
            {{ userInfo.ecopoints }}</a
          >
        </li>
        <li>
          <a class="dropdown-item" href="/"
            ><i class="bi bi-house-fill"></i>INICIO</a
          >
        </li>
        <li>
          <a class="dropdown-item" href="/#/perfil"
            ><i class="bi bi-person-fill"></i>PERFIL</a
          >
        </li>
        <li>
          <a class="dropdown-item" href="/#/meus-anuncios"
            ><i class="bi bi-megaphone-fill"></i>ANÚNCIOS</a
          >
        </li>
        <li>
          <a class="dropdown-item" href="/#/minhas-propostas"
            ><i class="bi bi-graph-up"></i>PROPOSTAS</a
          >
        </li>
        <li>
          <a class="dropdown-item" href="/" @click="sair()"
            ><i class="bi bi-box-arrow-right"></i>SAIR</a
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    userInfo: {},
    isLogado: Boolean,
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
button {
  margin: 0;
  padding: 0;
}

.menu_categoria {
  position: relative;
  transition: color 0.3s;
}

.menu_categoria button:hover {
  color: var(--color1);
}

.menu_categoria button::after {
  content: "";
  width: 0%;
  height: 2px;
  background: var(--color1);
  position: absolute;
  left: 0;
  bottom: -0.5rem;
  transition: width 0.2s;
  margin: 0;
  border: 0;
}

.menu_categoria button:hover::after {
  width: 100%;
}

.menu_acesso .dropdown-menu i {
  margin-right: 10px;
}

.dropdown-toggle::after {
  content: none;
}

.dropdown-toggle {
  background: transparent;
  border: none;
  color: #fff;
}

.dropdown-menu {
  background: var(--color2);
  font-size: 0.8rem;
  padding: 5px;
}

.dropdown-menu a {
  background: var(--color2);
  transition: 0.3s;
}

.dropdown-menu a:hover {
  background: var(--color1);
  color: #fff;
}

.dropdown-menu a:hover::after {
  width: 0%;
}

a {
  text-decoration: none;
  color: #fff;
  cursor: pointer;
  transition: color 0.3s;
  position: relative;
}

a:hover {
  color: var(--color1);
}

a:after {
  content: "";
  width: 0%;
  height: 2px;
  background: var(--color1);
  position: absolute;
  left: 0;
  bottom: -0.5rem;
  transition: width 0.2s;
}

a:hover::after {
  width: 100%;
}

p {
  color: #fff;
  margin: 0;
}

.menu {
  display: flex;
  align-items: center;
  gap: 10px;
}

.desk_acesso {
  display: flex;
  align-items: center;
}

.desk_acesso i {
  width: 100px;
  height: 100px;
}

.desk_acesso img {
  clip-path: circle();
  width: 2.5rem;
  height: 2.5rem;
  object-fit: cover;
}

.desk_acesso i {
  color: #fff;
  width: auto;
  height: auto;
  font-size: 2rem;
}

.links {
  display: flex;
  flex-direction: column;
  text-align: right;
  margin: 0 10px;
}

.links a::after {
  bottom: -0.01rem;
}
</style>