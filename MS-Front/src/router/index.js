import { createRouter, createWebHashHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/sobre",
    name: "sobre",
    component: () => import("../views/SobreView.vue"),
  },
  {
    path: "/perfil",
    name: "perfil",
    component: () => import("../views/PerfilView.vue"),
  },
  {
    path: "/meus-anuncios",
    name: "meusAnuncios",
    component: () => import("../views/MeusAnunciosView.vue"),
  },
  {
    path: "/minhas-propostas",
    name: "minhasPropostas",
    component: () => import("../views/MinhasPropostasView.vue"),
  },
  {
    path: "/ecopoints",
    name: "ecopoints",
    component: () => import("../views/EcopointsView.vue"),
  },
  {
    path: "/cadastro",
    name: "cadastro",
    component: () => import("../views/Cadastro.vue"),
  },
  {
    path: "/login",
    name: "login",
    component: () => import("../views/Login.vue"),
  },
  {
    path: "/proposta",
    name: "proposta",
    component: () => import("../views/PropostaView.vue"),
  },
  {
    path: "/anuncio",
    name: "anuncio",
    component: () => import("../views/AnuncioView.vue"),
  },
  {
    path: "/pesquisa",
    name: "pesquisa",
    component: () => import("../views/PesquisaView.vue"),
  },
  {
    path: "/novo-anuncio",
    name: "novoanuncio",
    component: () => import("../views/NovoAnuncioView.vue"),
  },
  {
    path: "/editar-anuncio",
    name: "editaranuncio",
    component: () => import("../views/EditarAnuncioView.vue"),
  },
  {
    path: "/nova-proposta",
    name: "novaproposta",
    component: () => import("../views/NovaPropostaView.vue"),
  },
  {
    path: "/editar-proposta",
    name: "editarproposta",
    component: () => import("../views/EditarPropostaView.vue"),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
