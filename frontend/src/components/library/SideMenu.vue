<template>
  <el-menu
    @select="handleSelect"
    active-text-color="red"
    background-color="rgb(248, 243, 237)"
    class="categories"
    default-active="0"
  >
    <el-menu-item index="0">
      <i class="el-icon-s-grid"></i>
      <span slot="title">全部</span>
    </el-menu-item>
    <el-menu-item index="8">
      <i class="el-icon-trophy"></i>
      <span slot="title">热门</span>
    </el-menu-item>
    <el-menu-item index="9">
      <i class="el-icon-sugar"></i>
      <span slot="title">新品</span>
    </el-menu-item>
    <el-menu-item index="10">
      <i class="el-icon-star-off"></i>
      <span slot="title">推荐</span>
    </el-menu-item>
    <el-menu-item index="1">
      <i class="el-icon-menu"></i>
      <span slot="title">文学</span>
    </el-menu-item>
    <el-menu-item index="2">
      <i class="el-icon-menu"></i>
      <span slot="title">流行</span>
    </el-menu-item>
    <el-menu-item index="3">
      <i class="el-icon-menu"></i>
      <span slot="title">文化</span>
    </el-menu-item>
    <el-menu-item index="4">
      <i class="el-icon-menu"></i>
      <span slot="title">生活</span>
    </el-menu-item>
    <el-menu-item index="5">
      <i class="el-icon-menu"></i>
      <span slot="title">经管</span>
    </el-menu-item>
    <el-menu-item index="6">
      <i class="el-icon-menu"></i>
      <span slot="title">科技</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'SideMenu',
  data () {
    return {
      cid: ''
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      this.cid = key
      this.$emit('indexSelect')
    }
  }
}
</script>

<style scoped>
.categories {
  position: fixed;
  margin-left: 0;
  left: 0;
  top: 68px;
  width: 120px;
}
</style>
<style>
::-webkit-scrollbar {
  width: 20px;
}

::-webkit-scrollbar-track {
  background-color: transparent;
}

::-webkit-scrollbar-thumb {
  background-color: rgba(204, 199, 199, .7);
  border-radius: 20px;
  border: 6px solid transparent;
  background-clip: content-box;
}

::-webkit-scrollbar-thumb:hover {
  background-color: rgb(218, 183, 141);
}
</style>
