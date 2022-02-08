<template>
  <div class="adminOrdersBody">
    <div class="ordersContainer">
      <div class="suggest">
        <h1>所有用户订单</h1>
      </div>
      <el-collapse accordion>
        <el-collapse-item :key="user.id" class="UserSpan" v-for="(user,index) in this.users">
          <template slot="title">
            <span style="margin-right:95px">序号：{{index}}</span>
            <span style="width:90px;margin-right:95px">用户ID:{{user.id}}</span>
            <span style="width:150px;margin-right:95px">用户名：{{user.username}}</span>
            <span style="width:300px;margin-right:95px">邮箱：{{user.email}}</span>
            <span style="width:200px;margin-right:0px">电话：{{user.phone}}</span>
          </template>
          <el-collapse accordion style="width:90%;margin:0 auto;" class="innerOrder">
            <el-collapse-item :key="order.id" v-for="(order,index) in user.orders" >
              <template slot="title">
                <span style="width:40px;text-align:left;">#{{index+1}}</span>
                <span style="width:108px;text-align:left;">订单号： {{order.id}}</span>
                <span style="width:150px;text-align:left;margin-right:20px;">小计： ￥{{order.subTotal}}</span>
                <span>折扣： 5%</span>
                <span
                  style="width:150px;text-align:left;margin-right:30px;"
                >总价： ￥{{order.totalPrice}}</span>
                <span
                  style="margin-right:0;"
                >时间：{{((new Date(order.orderTime)).toLocaleString('zh-Hans-CN', {hour12: false}).split('/').join('-'))}}</span>
              </template>
              <div style="font-size:16px;font-style:italic;margin-bottom:10px;">订单列表</div>
              <div class="items">
                <span style="width:60px;text-align:left;">序号</span>
                <span style="width:200px;text-align:left;">书名</span>
                <span style="width:150px;text-align:left;">作者</span>
                <span style="width:60px;text-align:left;">单价</span>
                <span style="width:60px;text-align:left;">数量</span>
              </div>
              <div :key="item.id" class="items" v-for="(item,index) in order.order_items">
                <span style="width:60px;text-align:left;">{{index+1}}</span>
                <span
                  style="width:200px;text-align:left;height:23px;overflow:hidden;"
                >{{item.bName}}</span>
                <span
                  style="width:150px;text-align:left;height:23px;overflow:hidden;"
                >{{item.bAuthor}}</span>
                <span style="width:60px;text-align:left;">{{item.bPrice}}</span>
                <span style="width:60px;text-align:left;">{{item.count}}</span>
              </div>
            </el-collapse-item>
          </el-collapse>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: 'adminOrders',
  data () {
    return {
      subTotal: 0,
      uid: 0,
      activeName: '1',
      orders: [],
      users: []
    }
  },
  mounted: function () {
    this.loadUsers()
  },
  methods: {
    getUid () {
      let name = this.$store.state.user.username
      let _this = this
      this.$axios.get('/getIdByUserName?username=' + name).then(resp => {
        _this.uid = parseInt(resp.data)
      })
    },
    loadUsers () {
      this.$axios.get('/findAllUsers').then(resp => {
        this.users = resp.data
        for (let user of this.users) {
          this.loadOrders(user)
        }
        this.$forceUpdate()
      })
    },
    loadOrders (user) {
      let uid = user.id
      this.$axios.get('/orders?uid=' + uid).then(resp => {
        if (resp && resp.status === 200) {
          user.orders = resp.data
          for (let order of user.orders) {
            this.$axios.get('/orderItems?oid=' + order.id).then(resp => {
              order.order_items = resp.data
              this.$forceUpdate()
            })
          }
        }
      })
    }
  }
}
</script>
<style scoped>
.innerOrder >>> .el-collapse-item__header{
  border-bottom: 1px solid rgb(202, 221, 158);
  border-top: 1px solid rgb(202, 221, 158);
}
.UserSpan span {
  text-align: left;
}
.items span {
  display: inline-block;
}
.MyordersBody {
  position: relative;
  top: 70px;
  background-color: rgb(248, 243, 237);
}
.ordersContainer {
  width: 1262px;
  margin: 0 auto;
  font-size: 18px;
}
.ordersContainer h1 {
  display: inline-block;
  text-rendering: optimizeLegibility;
  color: rgb(248, 243, 237);
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  font-size: 2em;
  text-align: center;
  margin: 0.5rem 0 1rem;
  margin-top: 20px;
  margin-bottom: 20px;
  position: relative;
  right: 10px;
}
.ordersContainer span {
  margin-right: 70px;
}

.el-collapse >>> .el-collapse-item {
  border-bottom: 1px solid gray;
  background-color: rgb(42, 43, 61);
}
.el-collapse-item >>> .el-collapse-item__header {
  background-color: rgb(42, 43, 61);
  font-size: 18px;
}
.el-collapse-item >>> .el-collapse-item__wrap {
  background-color: rgb(42, 43, 61);
}
</style>
