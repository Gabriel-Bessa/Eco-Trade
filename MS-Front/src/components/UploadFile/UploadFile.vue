<template>
  <div class="container_arquivo">
    <label for="upload">
      <div class="icon">
        <i class="bi bi-plus"></i>
      </div>
      <div class="text">
        <p>Carregar arquivos</p>
      </div>
    </label>
    <input
      type="file"
      name="upload"
      id="upload"
      multiple
      @change="validarUpload()"
    />
  </div>
</template>

<script>
export default {
  methods: {
    validarUpload() {
      var uploadArquivo = document.getElementById("upload");
    },
  },
};
</script>

<style scoped>
p {
  margin: 0;
  padding: 0;
}

input[type="file"] {
  opacity: 0;
  width: 10px;
  height: 10px;
  display: none;
  visibility: hidden;
}

.container_arquivo {
  display: flex;
  align-items: center;
  margin: 10px;
}

.container_arquivo label {
  display: flex;
  align-items: center;
  cursor: pointer;
  transition: all 0.3s;
}

.icon {
  border: 2px solid;
  border-radius: 50%;
}

.icon i {
  display: flex;
  font-size: 2rem;
}

.text {
  margin-left: 10px;
}

.container_arquivo label:hover {
  color: var(--color2);
}
</style>
