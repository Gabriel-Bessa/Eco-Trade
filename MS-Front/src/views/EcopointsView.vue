<template>
    <div class="containerTitle">
        <p class="title">Doe seus ecopoints!</p>
        <p class="ecopoints">Ecopoints: {{ecopoints}}</p>
    </div>
    <!-- <h1>Bem vindo a página de Ecopoints!</h1> -->
    <div class="listInstitution">
        <CardEcopoints :institution="Institution"/>
    </div>
</template>

<script>
    import Navbar from '@/components/Header/Navbar.vue'
    import CardEcopoints from '@/components/Ecopoints/CardEcopoints.vue'
    import axios from 'axios'

    const infoUser = JSON.parse(localStorage.getItem("user"))

    export default {
        name: 'EcopointsView',
        components: {
            Navbar,
            CardEcopoints
        },
        data(){
            return{
                Institution: {},
                ecopoints: infoUser.ecopoints
            }
        },
        methods:{
            dadosInstituicao(){
                axios({
                    method: "GET",
                    url: process.env.VUE_APP_TRADE_API_URL + "/public/organization",
                }).then((resp) => ((this.Institution = resp.data)))
            }
        },
        mounted(){
            this.dadosInstituicao()
        }
    }
</script>

<style scoped>
    .containerTitle{
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 1rem;
        padding: 1rem 4rem;
        height: 4rem;
        box-shadow: rgb(0 0 0 / 20%) 0px 2px 6px;
    }

    .containerTitle p{
        margin: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #707070;
    }

    .title{
        font-size: 1.1rem;
        font-weight: 600;
    }

    .ecopoints{
        font-size: .9rem;
    }

    .listInstitution{
        padding: 5px;
        display: flex;
        flex-direction: column;
        gap: 10px;
        align-items: center;
        justify-content: center;
    }

    @media (max-width: 460px) {
        .containerTitle{
            display: block;
            height: 5rem;
            padding: 1rem;
        }
    }

    @media(min-width: 820px){
        .listInstitution{
            flex-direction: row;
            flex-wrap: wrap;
        }
    }

</style>