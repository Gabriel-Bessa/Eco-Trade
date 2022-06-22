<template>

  <div class="card" v-for="inst in institution" :key="inst">
    <div class="image">
      <img :src="inst.photoUrl" alt="">
    </div>
    <div class="about">
      <div class="institution">
        <h5>{{inst.name}}</h5>
        <p>{{inst.description}}</p>
        <a target="_blank" :href="inst.link">saiba mais</a>
      </div>
      <div class="ecopoints">
        <input class="inputValor" type="number" v-model="valor[inst.id]">
        <button class="inputButton" @click="enviarEcopoints(inst.id, valor[inst.id])">Doar</button>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  import FeedbackMessage from "../../services/notifyService";
  import Swal from "sweetalert2";

  export default {
    name: "CardEcopoints",
    components: {
      FeedbackMessage
    },
    setup(){
      return{
        valor: []
      }
    },
    data(){
      return{
        user: {}
      }
    },
    props:{
      institution: {}
    },
    methods:{
      async enviarEcopoints(id, ecopoints){
        let infoUser = JSON.parse(localStorage.getItem("user"))
        if(ecopoints <= 0 || ecopoints == null){
          FeedbackMessage.ErrorToast("", "Quantidade de ecopoints a ser doada é inválida!");
        }
        else if(ecopoints > infoUser.ecopoints){
          FeedbackMessage.ErrorToast("", "Quantidade de ecopoints a ser doada é maior que a disponível!");
        }
        else{
          await axios({
            method: "POST",
            url:
              process.env.VUE_APP_TRADE_API_URL + `/donation/organization/${id}?quantity=${ecopoints}`
          }).then((resp) => {
            this.User = {
              address: infoUser.address,
              birthDate: infoUser.birthDate,
              cep: infoUser.cep,
              city: infoUser.city,
              ecopoints: infoUser.ecopoints - ecopoints,
              email: infoUser.email,
              name: infoUser.name,
              number: infoUser.number,
              phone: infoUser.phone,
              state: infoUser.state,
              url: infoUser.url
            }
            
            localStorage.setItem("user", JSON.stringify(this.User))

            Swal.fire({
              text: "Doação realizada com sucesso!",
              icon: "success",
            }).then((result) => {
              if(result.isConfirmed){
                window.location.href = "/"
              }
            })
          });
        }
      }
    }
  };
</script>

  <style scoped>
    .card{
      display: grid;
      grid-template-columns: .5fr 1fr;
      grid-gap: .3rem;
      padding: 0.6rem;
      border-radius: 5px;
      max-width: 30rem;
      height: 12.5rem;
      max-height: 12.5rem;
      box-shadow: rgb(0 0 0 / 20%) 0px 2px 6px;
      transition: all 0.3s;
    }

    .card:hover{
      box-shadow: rgb(0 0 0 / 200%) 0px 2px 6px;
    }

    .image{
      display: flex;
      align-items: center;
      justify-content: center;
      overflow: hidden;
      background: whitesmoke;
    }

    .image img{
      width: 80%;
      object-fit: cover;
    }

    .about{
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      padding: .3rem;
    }

    .institution{
      text-align: left;
      margin-bottom: .3rem;
    }

    .institution h5{
      margin-bottom: .3rem;
    }

    .institution p{
      display: -webkit-box;
      overflow: hidden;
      text-overflow: ellipsis;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
      font-size: .8rem;
      margin: 0;
    }

    .institution a{
      text-decoration: none;
      color: var(--color2);
      font-size: .8rem;
    }

    .institution a:hover{
      color: var(--color1);
    }

    .ecopoints{
      display: flex;
      gap: .3rem;
      width: 100%;
    }

    input[type=number]::-webkit-inner-spin-button,
    input[type=number]::-webkit-outer-spin-button {
      -webkit-appearance: none;
      margin: 0;
    }

    .inputValor{
      /* border-radius: .3rem; */
      border: 1px solid #858585;
      border-radius: .3rem;
      padding: .2rem;
      width: 60%;
    }

    .inputValor:focus{
      outline: none;
    }

    .inputButton{
      background: var(--color2);
      color: #fff;
      border: none;
      border-radius: .3rem;
      width: 40%;
      transition: all .3s;
      text-align: center;
    }

    .inputButton:hover{
      background: var(--color1);
    }

    @media(max-width: 360px){
      .card{
        height: 14rem;
        max-height: 14rem;
      }
    }
  </style>



