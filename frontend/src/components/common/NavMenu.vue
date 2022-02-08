<template>
  <el-menu
    :default-active="isActive"
    active-text-color="red"
    background-color="rgb(248, 243, 237)"
    mode="horizontal"
    router
    style="min-width: 100%;"
    text-color="#222"
  >
    <el-menu-item :index="item.name" :key="i" v-for="(item,i) in navList">{{ item.navItem }}</el-menu-item>
    <a href="./admin" class="el-icon-s-custom" style="font-size: 25px;color: #222;float: right;padding: 20px;"></a>
  </el-menu>
</template>
<!-- materilize design -->>
<!--<template>
  <div>
    <nav>
      <div class="nav-wrapper">
        <ul class="left hide-on-med-and-down">
          <li
            :class="{ active: isActive==item.name }"
            :index="item.name"
            :key="i"
            v-for="(item,i) in navList "
            v-on:click="setActive(item)"
          >
            <router-link :to="item.name">{{ item.navItem }}</router-link>
          </li>
        </ul>
      </div>
    </nav>
  </div>
</template>-->

<script>
export default {
  name: 'NavMenu',
  mounted: function () {
    this.isActive = this.$route.fullPath
  },
  data () {
    return {
      isActive: '',
      navList: [
        { name: '/book', navItem: '图书' },
        { name: '/cart', navItem: '购物车' },
        { name: '/myOrders', navItem: '我的订单' }
      ]
    }
  },
  methods: {
    setActive (item) {
      this.isActive = item.name
    }
  }
}
</script>

<style scoped>
.el-menu {
  position: fixed;
  z-index: 999;
}
ul li {
  width: 120px;
}
a {
  text-decoration: none;
}

span {
  pointer-events: none;
}
</style>
