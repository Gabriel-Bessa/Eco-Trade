<template>
  <div class="container">
    <p class="titlePage">Login:</p>
    <p class="aviso">E-mail ou senha não correspondem!</p>
    <p class="avisoNull">Preencha todos os campos!</p>
  </div>
  <Forms />
  <div>
    <button
      v-on:click="VerificaLogin()"
      type="submit"
      class="btn btn-primary btn-lg btnLogin"
    >
      Login
    </button>
  </div>

  <hr class="dropdown-divider divisor" />

  <div class="container">
    <div class="row divSemConta">
      <div class="col-7">
        <p>Não possui uma conta?</p>
      </div>
      <div class="col-5">
        <button type="button" class="btn btn-primary btnCadastre"  v-on:click="Cadastre()">
          Cadastre-se
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/Header/Navbar.vue";
import Forms from "@/components/Forms/FormsLogin.vue";
import axios from "axios";

export default {
  name: "Login",
  components: {
    Navbar,
    Forms,
  },
  data() {
    return {
      EmailInput: String,
      PasswordInput: String,
      Aviso: String,
      AvisoNull: String,
    };
  },

  methods: {
    VerificaLogin() {
      this.EmailInput = document.getElementById("inputEmailLogin");
      this.PasswordInput = document.getElementById("inputPasswordLogin");

      this.AvisoNull = document.querySelector(".avisoNull");
      this.Aviso = document.querySelector(".aviso");

      this.AvisoNull.style.display = "none";
      this.Aviso.style.display = "none";
      this.EmailInput.style.border = "1px solid #ced4da";
      this.PasswordInput.style.border = "1px solid #ced4da";

      if (this.EmailInput.value == "" || this.PasswordInput.value == "") {
        if (this.EmailInput.value == "") {
          this.EmailInput.style.border = "1px solid darkred";
        } else {
          this.PasswordInput.style.border = "1px solid darkred";
        }
        this.AvisoNull.style.display = "block";
      } else {
        this.reqLogin();
      }
    },

    reqLogin() {
      let formData = new FormData();

      formData.append("email", this.EmailInput.value);
      formData.append("password", this.PasswordInput.value);

      const res = axios({
        method: "POST",
        url: process.env.VUE_APP_TRADE_API_URL + "/login",
        headers: {},
        data: formData,
      }).then((resp) => {
        if (resp.status == 200) {
          window.localStorage.setItem("currentToken", resp.data.access_token);
          window.location = "/";
        } else {
          this.EmailInput.style.border = "1px solid darkred";
          this.PasswordInput.style.border = "1px solid darkred";
          this.Aviso.style.display = "block";
        }
      });
    },

    Cadastre(){
      window.location = "#/cadastro";
    }
  },
};
</script>

<style scoped>
.container {
  width: 90%;
}

.titlePage {
  font-size: 2rem !important;
  color: #2e2e2e;
  font-weight: 500;
  margin-bottom: 20px;
  margin-top: 30px;
}

.aviso,
.avisoNull {
  display: none;
  color: darkred;
}

.btnLogin {
  height: 3rem;
  width: 9rem;
  margin-top: 40px;
  margin-bottom: 20px;
}

hr {
  margin-top: 25px;
  margin-bottom: 25px;
}

.divSemConta {
  margin: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.divSemConta p {
  margin: 0;
  padding: 0;
}

.btnCadastre {
  width: 8rem;
  height: 2.5rem;
}

.modal {
  display: none;
  align-items: center;
  justify-content: center;
}
.modal-dialog {
  width: 80%;
}

@media (min-width: 990px) {
  .container {
    width: 40%;
  }
}

@media (max-width: 599px) {
  a,
  p {
    font-size: 0.9rem !important;
  }
}
</style>
