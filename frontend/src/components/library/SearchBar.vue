<template>
  <div
    class="inputWraper"
    style="margin-bottom: 30px;display: flex;justify-content: center;align-items: center"
  >
    <!-- <input
      @keyup.enter.native="searchClick"
      class="el_input"
      placeholder="通过书名或作者搜索..."
      prefix-icon="el-icon-search"
      size="small"
      style="width: 400px;margin-right: 10px"
      v-model="keywords"
    />-->
    <label class="input">
      <input @keyup.enter="searchClick"  v-model="keywords" class="input__field" placeholder=" " type="text" />
      <span class="input__label">keywords...</span>
    </label>
    <el-button @click="searchClick" icon="el-icon-search" size="small" type="primary">搜索</el-button>
  </div>
</template>

<script>
export default {
  name: 'SearchBar',
  data () {
    return {
      keywords: '',
      books: [],
      cardLoading: []
    }
  },
  methods: {
    searchClick () {
      this.$emit('onSearch')
    }
  }
}
</script>

<style scoped>
.input {
  position: relative;
  width: 300px;
  margin-right: 5px;
}

.input__label {
  position: absolute;
  left: 20px;
  top: 0;
  padding: calc(0.5rem * 0.75) calc(0.5rem * 0.5);
  margin: calc(0.5rem * 0.75 + 3px) calc(0.5rem * 0.5);
  background: pink;
  white-space: nowrap;
  transform: translate(0, 0);
  transform-origin: 0 0;
  background: rgb(248, 243, 237);
  transition: transform 120ms ease-in;
  font-weight: bold;
  line-height: 1.2;
}

.input__field {
  box-sizing: border-box;
  display: block;
  width: 100%;
  border: 3px solid currentColor;
  padding: calc(0.5rem * 1.5) 0.5rem;
  color: currentColor;
  background: transparent;
  border-radius: 4px;
}

.input__field:not(:-moz-placeholder-shown) + .input__label {
  transform: translate(0.25rem, -65%) scale(0.8);
  color: #fab700;
}

.input__field:not(:-ms-input-placeholder) + .input__label {
  transform: translate(0.25rem, -65%) scale(0.8);
  color: #fab700;
}

.input__field:focus + .input__label,
.input__field:not(:placeholder-shown) + .input__label {
  transform: translate(0.25rem, -65%) scale(0.8);
  color: #fab700;
}

button:hover {
  background-color: rgb(216, 61, 87);
}

button {
  color: currentColor;
  padding: 0.5rem calc(0.5rem * 2);
  background: #fab700;
  border: none;
  border-radius: var(--size-radius);
  font-weight: 900;
  border-radius: 4px;
}

button[type='reset'] {
  background: white;
  font-weight: 200;
}

button + button {
  margin-left: calc(0.5rem * 2);
}

.icon {
  display: inline-block;
  width: 1em;
  height: 1em;
  margin-right: 0.5em;
}

.hidden {
  display: none;
}
</style>
