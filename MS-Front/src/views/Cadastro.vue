<template>
  <br />
  <br />
  <br />
  <FormsCadastro />
</template>

<script>
import Navbar from "@/components/Header/Navbar.vue";
import FormsCadastro from "@/components/Forms/FormsCadastro.vue";
import axios from "axios";
import Swal from 'sweetalert2'

export default {
  name: "Cadastro",
  components: {
    Navbar,
    FormsCadastro,
  },
  data(){
    return{

    }
  },
  methods:{
    async createUser() {

      var imageUser = document.getElementById('imageUser');
      var nome = document.getElementById('inputNameUser').value;
      var DataNasc = document.getElementById('inputBirthDate').value;
      var Telefone = document.getElementById('inputPhone').value;
      var CEP = document.getElementById('inputCEP').value;
      var Cidade = document.getElementById('inputCidade').value;
      var Endereco = document.getElementById('inputEndereco').value;
      var NumEnd = document.getElementById('inputNumEnd').value;
      var EstadoEnd = document.getElementById('inputEstadoEnd').value;
      var Email = document.getElementById('inputEmail').value;
      var Senha = document.getElementById('inputPassword').value;

      let formData = new FormData();
      formData.append('body', new Blob( [ JSON.stringify({
          name: nome,
          email: Email,
          birthDate: DataNasc,
          password: Senha,
          cep: CEP,
          adress: Endereco,
          number: NumEnd,
          city: Cidade,
          state: EstadoEnd,
          phone: Telefone,
        })]), 'application/json');

      formData.set('file', new Blob([imageUser.files[0]]));

      axios({
        method: "POST",
        url: process.env.VUE_APP_TRADE_API_URL + "/public/user",
        headers: {},
        data: formData,

      }).then((resp) => Swal.fire(resp.data.property, resp.data.message, "success"));
    },
  }
};
</script>

<style scoped>
</style>
