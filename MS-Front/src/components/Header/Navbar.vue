<template>
  <header id="header">
    <div class="logo">
      <a href="#" to="/" id="logo">eco<span>Trade</span></a>
    </div>
    <div class="pesquisa">
      <form v-on:submit="submitSearch()">
        <input
          v-model="userInput"
          class="buscarProdutos"
          type="search"
          name="buscarProdutos"
          id="buscarProdutos"
          placeholder="buscar produto"
        />
      </form>
    </div>

    <nav>
      <div class="desktop">
        <MenuDesktop :userInfo="user" :isLogado="isLogado" />
      </div>
      <div id="menu_mobile">
        <i class="bi bi-x-lg" @click="toggleMenu()"></i>
        <MenuMobile
          :userInfo="user"
          :isLogado="isLogado"
          :toggleMenu="toggleMenu"
        />
      </div>
      <div class="mobile">
        <button
          id="btn-mobile"
          @click="toggleMenu()"
          aria-controls="menu_mobile"
          aria-haspopup="true"
        >
          <i class="bi bi-list"></i>
        </button>
      </div>
    </nav>
  </header>
</template>

<script>
import MenuMobile from "@/components/Header/MenuMobile.vue";
import MenuDesktop from "@/components/Header/MenuDesktop.vue";

export default {
  name: "Navbar",
  components: {
    MenuMobile,
    MenuDesktop,
  },
  props: {
    user: {},
    isLogado: Boolean,
  },
  data() {
    return {
      userInput: "",
    };
  },
  methods: {
    toggleMenu() {
      const menu = document.getElementById("menu_mobile");
      menu.classList.toggle("active");
    },
    submitSearch() {
      //set cookie
      document.cookie = "search=" + this.userInput + ";";
      document.cookie = "searchType=" + "title" + ";";

      //get cookie
      var cookie = this.getCookie("search");
      var cookieType = this.getCookie("searchType");

      window.location.href = "/#/pesquisa";
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
a {
  text-decoration: none;
  color: #fff;
  cursor: pointer;
}

a:hover {
  color: white;
}

#header {
  background: var(--color2);
  box-shadow: 0 0 0.4rem var(--color1);
  box-sizing: border-box;
  padding: 1rem;
  height: 4rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: sticky;
  top: 0;
  width: 100%;
  z-index: 9999;
}

#logo {
  font-size: 1.5rem;
}

#logo span {
  font-weight: bold;
}

.pesquisa {
  width: 100%;
}

.pesquisa form input::placeholder {
  color: rgba(255, 255, 255, 0.9);
}

.pesquisa form input:focus {
  outline: none;
}

.buscarProdutos {
  background: transparent;
  border: none;
  color: white;
  border-bottom: 1px solid var(--color1);
  width: min(70%, 500px);
  font-size: 0.8rem;
  padding: 5px;
}

#btn-mobile {
  color: #fff;
  cursor: pointer;
  border: none;
  background: transparent;
  font-size: 1rem;
}

.desktop {
  display: none;
  align-items: center;
  gap: 10px;
  font-size: 0.8rem;
}

.desk_acessar {
  display: flex;
  align-items: center;
}

.desk_acessar i {
  width: 100px;
  height: 100px;
}

.desk_acessar img {
  clip-path: circle();
  width: 2.5rem;
  height: 2.5rem;
}

.links {
  display: flex;
  flex-direction: column;
  text-align: right;
  margin: 0 10px;
}

#menu_mobile {
  display: block;
  visibility: hidden;
  overflow-x: hidden;
  background: var(--color2);
  position: absolute;
  width: 0px;
  height: calc(100vh - 4rem);
  top: 4rem;
  left: 0px;
  z-index: 9999;
  flex-direction: column;
  transition: 0.6s;
}

#menu_mobile.active {
  display: flex;
  visibility: visible;
  overflow-y: auto;
  width: min(70vw, 300px);
}

#menu_mobile i {
  position: absolute;
  color: #fff;
  padding: 5px;
  right: 5px;
  cursor: pointer;
}

#menu_mobile div a {
  display: flex;
  flex-direction: column;
  padding: 0.5rem 1rem;
}

#menu_mobile .section {
  display: flex;
  flex-direction: column;
  height: auto;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

@media (min-width: 600px) {
  .mobile {
    display: none;
  }

  .desktop {
    display: flex;
  }

  #menu_mobile,
  #menu_mobile.active {
    display: none;
  }
}
</style>