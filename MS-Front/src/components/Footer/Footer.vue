<template>
  <footer class="footer">
    <div class="container grid">
      <div class="brand">
        <div class="logo">
          <a href="#" to="/" id="logo">eco<span>Trade</span></a>
        </div>
        <p>2022 ecoTrade</p>
        <p>&copy; Todos os direitos reservados</p>
      </div>

      <div class="social grid">
        <a href="https://instagram.com" target="_blank">
          <i class="bi bi-instagram"></i>
        </a>
        <a href="https://facebook.com" target="_blank">
          <i class="bi bi-facebook"></i>
        </a>
        <a href="https://twitter.com" target="_blank">
          <i class="bi bi-twitter"></i>
        </a>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  
}
</script>

<style scoped>
  p{
    margin: 0;
  }

  a {
    text-decoration: none;
    color: #fff;
    cursor: pointer;
  }

  a:hover {
    color: white;
  }

  .footer{
    background: var(--color2);
    color: #fff;
    padding: 3.75rem 0;
    position: absolute;
    left: 0;
    width: 100%;
  }

  .grid{
    display: grid;
    gap: 2rem;
  }

  .container{    
    margin-right: 1.5rem;
    margin-left: 1.5rem;
  }

  .brand{
    text-align: left;
  }

  #logo {
    font-size: 1.5rem;
  }

  #logo span {
    font-weight: bold;
  }

  .social{
    grid-auto-flow: column;
    width: fit-content;
  }

  @media (min-width: 1200px) {
    .container {
      max-width: 1120px;
      margin-left: auto;
      margin-right: auto;
      grid-auto-flow: column;
      align-items: center;
      justify-content: space-between;
    }

    
  }
</style>