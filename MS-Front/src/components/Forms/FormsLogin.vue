<template>
  <div class="container LoginArea">
    <form class="row g-3 align-items-center">
      <div class="col-12">
        <input
          type="email"
          class="form-control"
          id="inputEmailLogin"
          placeholder="E-mail"
          value=""
          required
        />
      </div>
      <div class="col-12">
        <input
          type="password"
          class="form-control"
          id="inputPasswordLogin"
          placeholder="Senha"
          required
        />
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
};
</script>

<style scoped>
.container {
  width: 90%;
  color: black;
}

::placeholder {
  color: rgb(78, 78, 78) !important;
}

.divisor {
  margin: 25px 0 25px 0;
}

.btnLogin {
  height: 3rem;
  width: 9rem;
}
</style>
