<template>
  <div class="container InfoDivs DivInfos">
    <div class="row">
      <div class="col-12 IconEdit">
        <i class="bi bi-pencil" @click="EditInfo()"></i>
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <div class="col-12 ImgPerfil">
          <img id="" :src="InfoUser.url" alt="" />
        </div>
      </div>
    </div>
    <div class="row">
      <h2 class="NameUser" id="UserName">{{ InfoUser.name }}</h2>
      <h4 class="InfoUser" id="EmailUser">{{ InfoUser.email }}</h4>
      <h4 class="InfoUser" id="EnderecoUser">
        {{ InfoUser.city }}, {{ InfoUser.state }}
      </h4>
      <h4 class="InfoUser" id="TelUser">+55 {{ InfoUser.phone }}</h4>
    </div>
  </div>

  <div class="container DivEditing">
    <div class="col-12">
      <div class="col-12 IconEdit">
        <i class="bi bi-check-lg" @click="SaveEdit()"></i>
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <div class="col-12 ImgPerfil">
          <img id="ImgUser" :src="this.InfoUser.url" alt="" />
        </div>
        <div class="input-group divNewImg">
          <input type="file" class="form-control inputFile" id="imageUser">
        </div>
      </div>
    </div>

    <div class="NameUser InfoUserStyleInput input-group col-12">
      <input
        type="text"
        class="form-control"
        v-model="name"
        placeholder="Nome de usuário"
      />
    </div>

    <div class="InfoUserStyleInput input-group col-12">
      <input
        type="email"
        class="form-control"
        v-model="email"
        placeholder="E-mail usuário"
      />
    </div>

    <div class="row">
      <div class="InfoUserStyleInput col-6">
        <input
          type="text"
          class="form-control"
          v-model="city"
          placeholder="Cidade"
        />
      </div>

      <div class="InfoUserStyleInput col-6">
        <select
          v-model="this.state"
          name="estado"
          class="form-control"
        >
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
    </div>

    <div class="row">
      <div class="InfoUserStyleInput col-9">
        <input
          type="text"
          class="form-control"
          v-model='adress'
          placeholder="Endereço"
        />
      </div>

      <div class="InfoUserStyleInput col-3">
        <input
          type="text"
          class="form-control"
          v-model='number'
          placeholder="Nº"
        />
      </div>
    </div>

    <div class="input-group col-12 InfoUserStyleInput">
      <span class="input-group-text" id="inputGroup-sizing-default">+55</span>
      <input
        type="text"
        v-model='phone'
        class="form-control telefoneUserInput"
      />
    </div>
  </div>

  <hr />

  <div class="container InfoBotton InfoBottonEco">
    <span>EcoPoints: {{ InfoUser.ecopoints }}</span>
  </div>

  <!--<div class="container InfoBotton">
    <span class="">Nível de confiabilidade:</span>
    <div class="confiabilidade">75%</div>
  </div>-->

</template>

<script>
import Navbar from "@/components/Header/Navbar.vue";
import axios from "axios";

export default {
  name: "PerfilView",
  components: {
    Navbar,
  },
  data() {
    return {
      InfoUser: {},
      name: '',
      email: '',
      birthDate: '',
      password: '',
      cep: '',
      adress: '',
      number: '',
      city: '',
      state: '',
      phone: '',
    };
  },
  methods: {
    EditInfo() {
      var container1 = document.querySelector(".DivInfos");
      var container2 = document.querySelector(".DivEditing");

      if (container2.style.display === "block") {
        container2.style.display = "none";
        container1.style.display = "block";
      } else {
        container2.style.display = "block";
        container1.style.display = "none";
      }
    },
    SaveEdit() {

      const imageUser = document.getElementById("imageUser");

      let formData = new FormData();

      const obj = {
        name: this.name,
        email: this.email,
        birthDate: this.InfoUser.birthDate,
        password: '',
        cep: this.InfoUser.cep,
        address: this.adress,
        number: this.number,
        city: this.city,
        state: this.state,
        phone: this.phone,
      };

      console.log(imageUser.files[0])


      formData.append('body', new Blob( [ JSON.stringify(obj)], { type: 'application/json' }));
      formData.set('file', new Blob([imageUser.files[0]]));

      axios({
        method: "PUT",
        url: process.env.VUE_APP_TRADE_API_URL + "/user",
        headers: {},
        data: formData,

      }).then((resp) => Swal.fire(resp.data.property, resp.data.message, "success"));

      
      console.log(formData, this.state, this.cep, this.InfoUser.url, this.InfoUser.birthDate)

      /*var container1 = document.querySelector(".DivInfos");
      var container2 = document.querySelector(".DivEditing");

      axios({
        method: "PUT",
        url: process.env.VUE_APP_TRADE_API_URL + "/user",
        headers: {},
        data: formData,

      }).then((resp) => Swal.fire(resp.data.property, resp.data.message, "success"));

      if (container2.style.display === "block") {
        container2.style.display = "none";
        container1.style.display = "block";
      } else {
        container2.style.display = "block";
        container1.style.display = "none";
      }*/
    },
  },
  mounted() {
    this.InfoUser = JSON.parse(localStorage.getItem("user"));
    this.name = this.InfoUser.name
    this.email = this.InfoUser.email
    this.city = this.InfoUser.city
    this.state = this.InfoUser.state
    this.adress = this.InfoUser.address
    this.number = this.InfoUser.number
    this.cep = this.InfoUser.cep
    this.phone = this.InfoUser.phone
  },
};
</script>

<style scoped>
.container {
  margin-top: 45px;
  display: none;
}

.DivEditing {
  display: block;
}

.divNewImg{
  margin: 2rem 0;
}

.NameUser {
  margin-top: 20px;
  font-weight: 600;
  font-size: 1.8rem;
}

.InfoUserStyle {
  margin-top: 10px;
}

.InfoUserStyle span {
  margin-left: 25px;
}

.IconEdit {
  margin-bottom: 20px;
}

.IconEdit i {
  background-color: var(--color2);
  border-radius: 50%;
  font-size: 1.7rem;
  padding: 4px 10px;
  color: white;
  position: absolute;
  right: 35px;
  cursor: pointer;
}

.ImgPerfil {
  margin-top: 35px;
}

.ImgPerfil img {
  border-radius: 50%;
  width: 200px;
  height: 200px;
}

.InfoUserStyleInput {
  margin-top: 10px;
}

.InfoBotton {
  width: 30% !important;
  height: 6rem;
  margin-top: 10px;
  font-weight: bold;
  background-color: rgb(251, 251, 251);
  border: 1px solid darkgray;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 5px;
}

.InfoBottonEco {
  height: 2.5rem !important;
}

.InfoBotton div {
  margin-top: 0.5rem;
  border: 2px solid darkgrey;
  border-radius: 5px;
  padding: 0 1.3rem;

  background-color: green;
  color: white;
}

hr {
  margin: 50px 0;
  color: rgb(58, 58, 58);
}

h4 {
  font-size: 1.2rem;
}

@media (min-width: 800px) {
  .container {
    width: 55%;
  }

  .InfoUserStyleInput span {
    margin-left: 0px;
  }
}

@media (max-width: 799px) {
  .container {
    width: 80%;
  }

  .inputFile{
    font-size: 0.7rem;
  }

  .InfoBotton {
    width: 60% !important;
  }

  .InfoUserStyleInput span {
    margin-left: 0px;
  }
}
</style>
