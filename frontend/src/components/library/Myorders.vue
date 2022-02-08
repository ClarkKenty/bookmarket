<template>
  <div class="MyordersBody">
    <div class="ordersContainer">
      <div style="position:relative;" class="suggest">
        <h1>历史订单</h1>
        <span style="position:absolute;right:0;bottom:35px;font-size:16px;">(用户：{{this.$store.state.user.username}})</span>
      </div>
      <el-collapse accordion>
        <el-collapse-item :key="order.id" v-for="(order,index) in this.orders">
          <template slot="title">
            <span style="width:40px;text-align:left;">#{{index+1}}</span>
            <span style="width:108px;text-align:left;">订单号： {{order.id}}</span>
            <span style="width:150px;text-align:left;margin-right:20px;">小计： ￥{{order.subTotal}}</span>
            <span>折扣： 5%</span>
            <span style="width:150px;text-align:left;margin-right:30px;">总价： ￥{{order.totalPrice}}</span>
            <span style="margin-right:0;">时间：{{((new Date(order.orderTime)).toLocaleString('zh-Hans-CN', {hour12: false}).split('/').join('-'))}}</span>
          </template>
          <div style="font-size:16px;font-style:italic;color:gray;margin-bottom:10px;">订单列表</div>
          <div class="items">
            <span style="width:60px;text-align:left;">序号</span>
            <span style="width:200px;text-align:left;">书名</span>
            <span style="width:150px;text-align:left;">作者</span>
            <span style="width:60px;text-align:left;">单价</span>
            <span style="width:60px;text-align:left;">数量</span>
          </div>
          <div :key="item.id" class="items" v-for="(item,index) in order.order_items">
            <span style="width:60px;text-align:left;">{{index+1}}</span>
            <span style="width:200px;text-align:left;height:23px;overflow:hidden;">{{item.bName}}</span>
            <span style="width:150px;text-align:left;height:23px;overflow:hidden;">{{item.bAuthor}}</span>
            <span style="width:60px;text-align:left;">{{item.bPrice}}</span>
            <span style="width:60px;text-align:left;">{{item.count}}</span>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Myorders',
  beforeCreate: function () {
    document.body.classList.add('wheatBody')
  },
  data () {
    return {
      subTotal: 0,
      uid: 0,
      activeName: '1',
      orders: []
    }
  },
  mounted: function () {
    this.loadOrders()
  },
  methods: {
    getUid () {
      let name = this.$store.state.user.username
      let _this = this
      this.$axios.get('/getIdByUserName?username=' + name).then(resp => {
        _this.uid = parseInt(resp.data)
      })
    },
    loadOrders () {
      let name = this.$store.state.user.username
      let _this = this
      this.$axios.get('/getIdByUserName?username=' + name).then(resp => {
        _this.uid = parseInt(resp.data)
        this.$axios.get('/orders?uid=' + this.uid).then(resp => {
          if (resp && resp.status === 200) {
            _this.orders = resp.data
            for (let order of _this.orders) {
              this.$axios.get('/orderItems?oid=' + order.id).then(resp => {
                order.order_items = resp.data
                console.log(order)
                this.$forceUpdate()
              })
            }
          }
        })
      })
    }
  }
}
</script>
<style scoped>
.items span {
  display: inline-block;
}
.MyordersBody {
  position: relative;
  top: 70px;
  background-color: rgb(248, 243, 237);
}
.ordersContainer {
  width: 70%;
  margin: 0 auto;
  font-size: 18px;
}
.ordersContainer h1 {
  display: inline-block;
  -webkit-text-size-adjust: 100%;
  text-rendering: optimizeLegibility;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  font-size: 2em;
  line-height: 1;
  font-weight: 400;
  text-align: center;
  margin: 0.5rem 0 1rem;
  margin-bottom: 40px;
}
.ordersContainer span {
  margin-right: 70px;
}

.el-collapse >>> .el-collapse-item {
  border-bottom: 1px solid gray;
  background-color: rgb(248, 243, 237);
}
.el-collapse-item >>> .el-collapse-item__header {
  background-color: rgb(248, 243, 237);
  font-size: 18px;
}
.el-collapse-item >>> .el-collapse-item__wrap {
  background-color: rgb(248, 243, 237);
}
</style>
