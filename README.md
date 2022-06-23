# Introduction 

# Getting Started
1.	Installation process
2.	Software dependencies#Introdução
O projeto EcoTrade tem como objetivo atender a necessidade do mercado de possibilitar trocas online de maneira prática e acessível, além de apoiar causas sustentáveis com o incentivo a diminuição do consumismo e realizando doações para instituições beneficentes e ecológicas.
Diante de pesquisas realizadas, foi observado que, atualmente, não existia uma plataforma com ambiente e ferramentas que realizassem, especificamente, o serviço de troca de produtos. A ideia é apoiar ainda mais a sustentabilidade com a diminuição do consumismo e a realização de doações.
A plataforma é movimentada com base em uma moeda virtual chamada EcoPoints.
A cada troca realizada e confirmada na nossa plataforma o usuário receberá um valor em Ecopoints.Após juntar determinado valor, o usuário poderá realizar a doação de suas moedas para diversas instituições da sua escolha, todas listadas na página própria dos EcoPoints.

# Problema
Diante de pesquisas realizadas, foi observado que, atualmente, não existia uma plataforma com ambiente e ferramentas que realizassem, especificamente, o serviço de troca de produtos. Com isso, a ideia é apoiar ainda mais a sustentabilidade com a diminuição do consumismo e a realização de doações para instituições beneficentes e ecológicas.

# Objetivo geral
O projeto EcoTrade tem como objetivo atender a necessidade do mercado de possibilitar trocas online de maneira prática e acessível, além de apoiar causas sustentáveis com o incentivo a diminuição do consumismo e realizando doações para instituições beneficentes e ecológicas.

# Objetivos específicos
Os objetivos específicos do projeto são:
Criação de anúncios  e propostas de trocas;
Possibilitar que o usuário se desapegue de seus produtos não mais utilizados  e consiga em troca outros que sejam de seu interesse;
Exibir facilmente os anúncios e as propostas de trocas;
Proporcionar um meio de contato entre participantes do processo;
Possibilitar que o usuário faça doações para instituições que atuam no âmbito de sustentabilidade;

# Requisitos funcionais:
O sistema deve exibir uma lista de produtos
O sistema deve permitir o usuário cadastrar na plataforma
O sistema deve permitir o usuário realize login na plataforma 
O sistema deve permitir que o usuário edite os seus dados
O sistema deve disponibilizar um sistema para troca de mensagens entre os usuários
O sistema deve validar se o usuário confirmou o recebimento do produto 
Após validação de troca, o sistema deve enviar a bonificação correspondente
O sistema deve definir o nível de confiança do usuário de acordo com a bonificação
O sistema deve exibir uma lista de instituições para doações da bonificação
O usuário pode criar um anúncio de troca
O usuário pode editar o anúncio
O usuário pode excluir o anúncio
O usuário pode receber uma proposta
O usuário pode realizar uma proposta de troca
O usuário confirma se recebeu o produto combinado na troca
O usuário pode usar a bonificação para ajudar uma instituição

# Requisitos Não Funcionais
A bonificação será concedida usando um valor fixo e escalável de acordo com a quantidade de trocar confirmadas
O nível de confiança do usuário será definido através da porcentagem de trocar confirmadas em relação ao total de trocas realizadas.
O anúncio do produto deve possuir no mínimo 1 e no máximo 5 fotos.

#Tecnologias
Para a criação da plataforma foram utilizados nas mais diversas tecnologias, e cada tecnologia com sua finalidade. Deste modo a utilização do “core” da aplicação foi feita com o framework Vue.js  e o backend foi realizado em Spring boot,  como banco de dados foi utilizado Postgres e  como  cache distribuída foi utilizado o redis.  A tecnologia Vue.js, que foi empregada no front, foi produzida uma aplicação web responsiva com integração com back-end feita em Spring boot. O Banco relacional Postgresql foi empregado para armazenamento de dados persistentes  recebidos no backend spring boot, o redis por sua vez  foi utilizado como cache distribuído  e para auxiliar no complemento do fluxo de autenticação empregada na plataforma. Para hospedagem disponibilidade da aplicação em ambiente produção foram utilizados os serviços de kubernetes da Azure  cujo nome comercial representado pela sigla AKS.   este serviço foi disponibilizado pelos créditos concedidos aos alunos da PUC para utilização e estudo de computação em Cloud em um ambiente Microsoft.  devido a esse motivo a utilização da KS juntamente com serviços de DNS serviços de disco em Cloud da da rede para que haja uma maior disponibilidade e utilização daev em quase que na totalidade da plataforma. Para determinação dos resultados  e averiguação da hospedagem desta plataforma na Azure  aplicação  encontra-se disponível na URL: http://eco-trade.df69564bf1354d198208.australiacentral.aksapp.io/.

# Conclusão
Ao término do projeto foi possível notar o potencial da utilização deste sistema e trocas, observando as facilidades proporcionadas a um procedimento que até então ocorria de forma nada prática e segura, juntamente do potencial sustentável do projeto, incentivando doações a instituições e diminuição do consumismo exacerbado. 
3.	Latest releases
4.	API references

# Build and Test

# Contribute

- [Repositório](https://github.com/Gabriel-Bessa/Eco-Trade)
