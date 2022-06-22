<template>
    <div class="container">
        <img src="https://eco-trade.s3.amazonaws.com/assets/EcoTradeBlack.png" alt="" class="imgLogo">

        <div class="topic">
            <h3>Quem Somos</h3>
            <span>
                O projeto EcoTrade tem como objetivo atender a necessidade do mercado de possibilitar
                trocas online de maneira prática e acessível, além de apoiar causas sustentáveis com o
                incentivo a diminuição do consumismo e realizando doações para instituições beneficentes e
                ecológicas.
            </span>
        </div>
        <div class="topic">
            <h3>Proposta Ecológica</h3>
            <span>
                Diante de pesquisas realizadas, foi observado que, atualmente, não existia uma plataforma
                com ambiente e ferramentas que realizassem, especificamente, o serviço de troca de produtos.
                <br>A ideia é apoiar ainda mais a sustentabilidade com a diminuição do consumismo e a
                realização de doações para instituições beneficentes e ecológicas.

            </span>
        </div>
        <div class="topic lastText">
            <h3>Doações</h3>
            <span>
                A nossa plataforma é movimentada com base em uma moeda chamada EcoPoints. A cada troca realizada e confirmada por meio da nossa plataforma, o usuário receberá um valor em EcoPoints, que juntando esse valor, ele poderá realizar a troca dos seus EcoPoints por doações para diversas instituições da sua escolha, todas listadas na página própria dos EcoPoints.
            </span>
        </div>
    </div>
</template>

<script>   
    import Navbar from '@/components/Header/Navbar.vue'
    export default {
        name: 'SobreView',
        components: {
        Navbar
        }
    }
</script>

<style scoped>
.container{

}

.imgLogo{
    margin: 3rem 0 -4rem 0;
}

.topic{
    text-align: left;
    margin-top: 2rem;
}

h3{
    font-weight: 600;
    color: rgb(23, 23, 23);
}

.lastText{
    margin-bottom: 7rem;
}

@media (min-width: 800px) {
 
}

@media (max-width: 799px) {
    .imgLogo{
        width: 25rem;
    }
    .topic{
        text-align: center;
    }
}

</style>