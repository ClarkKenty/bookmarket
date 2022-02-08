<template >
  <div class="adminBody">
    <el-menu
      active-text-color="red"
      background-color="rgb(42, 43, 61)"
      class="categories"
      router
      style="border-right: solid 4px rgb(60, 63, 88);"
    >
      <el-menu-item index="/anly">
        <i class="el-icon-s-grid"></i>
        <span slot="title">数据分析</span>
      </el-menu-item>
      <el-menu-item index="/data">
        <i class="el-icon-s-grid"></i>
        <span slot="title">数据挖掘</span>
      </el-menu-item>
      <el-menu-item index="/adminOrders">
        <i class="el-icon-trophy"></i>
        <span slot="title">订单管理</span>
      </el-menu-item>
      <el-menu-item index="/adminUsers">
        <i class="el-icon-sugar"></i>
        <span slot="title">账号管理</span>
      </el-menu-item>
      <a class="el-icon-back back" href="./"></a>
    </el-menu>
    <div class="wrapper">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  mounted: function () {
    document.body.classList.add('darkBody')
    var ss = document.styleSheets[0]
    ss.insertRule(
      '::-webkit-scrollbar-thumb {background-color: #3c3f58 !important}',
      0
    )
    ss.insertRule(
      '::-webkit-scrollbar-thumb:hover {background-color: rgb(39, 17, 83) !important}',
      0
    )
  },
  name: 'admin',
  data () {
    return {}
  }
}
</script>

<style scoped>
.back {
  position: absolute;
  bottom: 0;
  left: 30%;
  font-size: 50px;
}
ul.el-menu li {
  border-bottom: 3px solid rgb(60, 63, 88);
}
a {
  text-decoration: none;
}

.categories {
  position: fixed;
  margin-left: 0;
  left: 0;
  top: 0;
  width: 150px;
  height: 100%;
}
.wrapper {
  margin-left: 150px;
}

::-webkit-scrollbar {
  width: 20px;
  background: transparent;
}

::-webkit-scrollbar-track {
  background-color: transparent;
}

::-webkit-scrollbar-thumb {
  background-color: #3c3f58;
  border-radius: 20px;
  border: 6px solid transparent;
  background-clip: content-box;
}

::-webkit-scrollbar-thumb:hover {
  background-color: rgb(39, 17, 83);
}
html {
  overflow-y: scroll;
}
</style>

<style>
/* ::-webkit-scrollbar {
  width: 20px;
  background: transparent;
}

::-webkit-scrollbar-track {
  background-color: transparent;
}

::-webkit-scrollbar-thumb {
  background-color: #3c3f58;
  border-radius: 20px;
  border: 6px solid transparent;
  background-clip: content-box;
}
::-webkit-scrollbar-thumb:hover {
  background-color: rgb(39, 17, 83);
} */
</style>
