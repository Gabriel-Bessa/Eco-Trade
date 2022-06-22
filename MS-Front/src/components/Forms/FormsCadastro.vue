<template>
  <div class="container CadastrarArea">
    <p class="titlePage">Cadastro</p>

    <form class="row g-3" action="#">
      <div class="divImgUser">
        <input type="file" id="imageUser" />
      </div>
      <p class="AlertUser">Necessário preencher todos os campos!</p>
      <div class="col-md-6 col-lg-12 divLabel">
        <input
          type="text"
          class="form-control"
          id="inputNameUser"
          placeholder="Nome:"
          required
        />
      </div>
      <div class="col-md-6 divLabel">
        <input
          type="number"
          class="form-control"
          id="inputCPF"
          placeholder="CPF:"
          required
        />
      </div>
      <div class="col-md-6 divLabel">
        <input
          placeholder="Data de nascimento"
          class="form-control dateAniv"
          id="inputBirthDate"
          type="text"
          onfocus="(this.type='date')"
          onblur="(this.type='text')"
          required
        />
      </div>
      <div class="col-md-6 divLabel">
        <input
          type="tel"
          inputmode="tel"
          class="form-control"
          id="inputPhone"
          placeholder="Telefone:"
          required
        />
      </div>

      <hr class="dropdown-divider divisor" />

      <div class="col-6 divLabel">
        <input
          type="number"
          class="form-control"
          id="inputCEP"
          placeholder="CEP:"
          required
        />
      </div>
      <div class="col-6 divLabel">
        <input
          type="text"
          class="form-control"
          id="inputCidade"
          placeholder="Cidade:"
          required
        />
      </div>
      <div class="col-6 col-lg-12 divLabel">
        <input
          type="text"
          class="form-control"
          id="inputEndereco"
          placeholder="Endereço:"
          required
        />
      </div>
      <div class="col-6 col-lg-4">
        <input
          type="number"
          class="form-control"
          id="inputNumEnd"
          placeholder="Número:"
          required
        />
      </div>
      <div class="col-6 col-lg-8 divLabel">
        <select id="estado" name="estado" class="form-control">
          <option value="AC">Acre</option>
          <option value="AL">Alagoas</option>
          <option value="AP">Amapá</option>
          <option value="AM">Amazonas</option>
          <option value="BA">Bahia</option>
          <option value="CE">Ceará</option>
          <option value="DF">Distrito Federal</option>
          <option value="ES">Espírito Santo</option>
          <option value="GO">Goiás</option>
          <option value="MA">Maranhão</option>
          <option value="MT">Mato Grosso</option>
          <option value="MS">Mato Grosso do Sul</option>
          <option value="MG">Minas Gerais</option>
          <option value="PA">Pará</option>
          <option value="PB">Paraíba</option>
          <option value="PR">Paraná</option>
          <option value="PE">Pernambuco</option>
          <option value="PI">Piauí</option>
          <option value="RJ">Rio de Janeiro</option>
          <option value="RN">Rio Grande do Norte</option>
          <option value="RS">Rio Grande do Sul</option>
          <option value="RO">Rondônia</option>
          <option value="RR">Roraima</option>
          <option value="SC">Santa Catarina</option>
          <option value="SP">São Paulo</option>
          <option value="SE">Sergipe</option>
          <option value="TO">Tocantins</option>
          <option value="EX">Estrangeiro</option>
        </select>
      </div>

      <hr class="dropdown-divider divisor" />

      <div class="col-md-6 col-lg-12 divLabel">
        <input
          type="email"
          class="form-control"
          id="inputEmail"
          placeholder="E-mail:"
          required
        />
      </div>
      <p class="AlertUserPassword">Senhas não correspondem!</p>
      <div class="col-md-6 divLabel">
        <input
          type="password"
          class="form-control"
          id="inputPassword"
          placeholder="Senha:"
          required
        />
      </div>
      <div class="col-md-6 divLabel">
        <input
          type="password"
          class="form-control"
          id="inputPassword02"
          placeholder="Confirme a senha:"
          required
        />
      </div>

      <div class="col-12 divBtnCadastrar">
        <button
          v-on:click="VerificaCadastro()"
          type="button"
          class="btn btn-primary btn-lg"
        >
          Cadastrar
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import SuccessToast from "../../services/notifyService";

export default {
  nome: "Cadastro",
  components: {
    SuccessToast
  },
  data() {
    return {
      nome: String,
      DataNasc: String,
      Telefone: String,
      password: String,
      confirmPassword: String,
      CEP: String,
      Cidade: String,
      Endereco: String,
      NumEnd: String,
      EstadoEnd: String,
      Email: String
    };
  },

  methods: {
    VerificaCadastro() {

      this.Senha = document.getElementById("inputPassword");
      this.nome = document.getElementById("inputNameUser");
      this.DataNasc = document.getElementById("inputBirthDate");
      this.Telefone = document.getElementById("inputPhone");
      this.CEP = document.getElementById("inputCEP");
      this.Cidade = document.getElementById("inputCidade");
      this.Endereco = document.getElementById("inputEndereco");
      this.NumEnd = document.getElementById("inputNumEnd");
      this.EstadoEnd = document.getElementById("estado");
      this.Email = document.getElementById("inputEmail");
      this.password = document.getElementById("inputPassword");
      this.confirmPassword = document.getElementById("inputPassword02");

      var AlertMessage = document.querySelector(".AlertUser");
      var AlertMessagePassword = document.querySelector(".AlertUserPassword");

      if(this.nome.value === ""){
        this.nome.style.border = '1px solid red'
      }else if(this.DataNasc.value === ""){
        this.DataNasc.style.border = '1px solid red'
      }else if(this.Telefone.value === ""){
        this.Telefone.style.border = '1px solid red'
      }else if(this.CEP.value === ""){
        this.CEP.style.border = '1px solid red'
      }else if(this.Cidade.value === ""){
        this.Cidade.style.border = '1px solid red'
      }else if(this.Endereco.value === ""){
        this.Endereco.style.border = '1px solid red'
      }else if(this.NumEnd.value === ""){
        this.NumEnd.style.border = '1px solid red'
      }else if(this.EstadoEnd.value === ""){
        this.EstadoEnd.style.border = '1px solid red'
      }else if(this.Email.value === ""){
        this.Email.style.border = '1px solid red'
      }else if(this.password.value === ""){
        this.password.style.border = '1px solid red'
      }
      else if (this.password.value !== this.confirmPassword.value) {
        AlertMessagePassword.style.display = 'block'
        this.password.style.border = '1px solid red'
        this.confirmPassword.style.border = '1px solid red'
      }else {
        this.createUser()
        return
      }
      AlertMessage.style.display = 'block';
    },
    createUser() {
      const imageUser = document.getElementById("imageUser");

      let formData = new FormData();

      const obj = {
        name: this.nome.value,
        email: this.Email.value,
        birthDate: this.DataNasc.value,
        password: this.Senha.value,
        cep: this.CEP.value,
        address: this.Endereco.value,
        number: this.NumEnd.value,
        city: this.Cidade.value,
        state: this.EstadoEnd.options[this.EstadoEnd.selectedIndex].value,
        phone: this.Telefone.value,
      };

      formData.append('body', new Blob( [ JSON.stringify(obj)], { type: 'application/json' }));
      formData.set('file', new Blob([imageUser.files[0]]));

      const res = axios({
        method: "POST",
        url: process.env.VUE_APP_TRADE_API_URL + "/public/user",
        headers: { "Content-Type": "multipart/form-data" },
        data: formData,
      }).then((resp) => {
        let data = resp.data;
        SuccessToast.SuccessToast(data.message, data.property);
        window.location.href = "/";
      });
    },
  },
};
</script>

<style scoped>
.container {
  width: 90%;
  color: black;
}

.titlePage {
  font-size: 2rem;
  font-weight: 500;
  margin-bottom: 20px;
}

::placeholder {
  color: rgb(78, 78, 78) !important;
}

.divImgUser img {
  border-radius: 50%;
  width: 150px;
  height: 150px;
  margin-bottom: 15px;
  z-index: 0;
}

.divLabel {
  text-align: left !important;
}

label {
  margin-left: 12px;
  color: rgb(78, 78, 78) !important;
}

.dateAniv {
  color: rgb(78, 78, 78) !important;
}

.AlertUserPassword, .AlertUser{
  display: none;
  color: rgb(209, 5, 5);
  margin-bottom: 0;
}

.divTermos {
  margin: 25px 0 25px 0;
  align-content: center;
}

.divBtnCadastrar {
  margin: 5px 0 50px 0;
}

.divBtnCadastrar button {
  height: 3rem;
  width: 9rem;
  margin-top: 2rem;
}

.divisor {
  margin: 25px 0 25px 0;
}

@media (min-width: 1000px) {
  .container {
    width: 40%;
  }
}
</style>
