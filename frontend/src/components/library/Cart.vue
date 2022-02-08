<template>
  <div class="cartBody">
    <div class="cartContainer">
      <div class="suggest">
        <h1>购物车</h1>
        <h2 class="sub-heading">您选购的图书如下，请核对</h2>
      </div>
      <ol>
        <li :key="item.id" v-for="item in this.$store.state.cart">
          <img :src="item[1].item.cover" class="img" />
          <div class="info">
            <div class="info_name">{{item[1].item.title}}</div>
            <div class="info_desc">{{item[1].item.abs}}</div>
            <div class="info_price">￥{{item[1].item.price}}</div>
          </div>
          <div class="modify" data-product-price="0.99">
            <div class="grid">
              <button class="btn subtract" v-on:click="reduceCount(item)">−</button>
              <div class="qty">{{item[1].count}}</div>
              <button class="btn plus" v-on:click="addCount(item)">+</button>
            </div>
            <button class="btn remove" v-on:click="removeItem(item)">Remove</button>
            <div class="price total-price">￥{{item[1].count*item[1].item.price}}</div>
          </div>
        </li>
      </ol>
      <div class="checkOut">
        <ul>
          <li class="totalRow">
            <span class="label">小计</span>
            <span class="value">￥{{ subTotal }}</span>
          </li>

          <li class="totalRow">
            <span class="label">折扣</span>
            <span class="value">-5%</span>
          </li>
          <li class="totalRow final">
            <span class="label">Total</span>
            <span class="value">￥{{(subTotal*0.95).toFixed(1)}}</span>
          </li>
          <li class="totalRow">
            <a @click="checkOut()" class="btn continue">Checkout</a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  beforeCreate: function () {
    document.body.classList.add('wheatBody')
  },
  created: function () {
    let res = 0
    let values = this.$store.state.cart.values()
    for (let value of values) {
      res += value.item.price * value.count
    }
    this.subTotal = res
  },
  name: 'Cart',
  data () {
    return {
      subTotal: 0
    }
  },
  methods: {
    listByCategory () {
      var _this = this
      var cid = this.$refs.sideMenu.cid
      var url = 'categories/' + cid + '/books'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          _this.$refs.booksArea.books = resp.data
        }
      })
    },
    getSubtotal () {
      let res = 0
      let values = this.$store.state.cart.values()
      for (let value of values) {
        res += value.item.price * value.count
      }
      this.subTotal = res
    },
    reduceCount (item) {
      item[1].count--
      if (item[1].count === 0) {
        this.$store.state.cart.delete(item[0])
      }
      this.getSubtotal()
      this.$forceUpdate()
    },
    addCount (item) {
      item[1].count++
      this.getSubtotal()
      this.$forceUpdate()
    },
    removeItem (item) {
      this.$store.state.cart.delete(item[0])
      this.getSubtotal()
      this.$forceUpdate()
    },
    checkOut () {
      if (this.$store.state.cart.size === 0) {
        this.$message({
          type: 'warning',
          message: '您还没有加入任何书籍！'
        })
        return
      }
      let uid = 0
      let _this = this
      let name = _this.$store.state.user.username
      let orderItems = []
      let values = this.$store.state.cart.values()
      for (let value of values) {
        let count = value.count
        let bid = value.item.id
        let bName = value.item.title
        let bAuthor = value.item.author
        let bPrice = value.item.price
        orderItems.push({
          count: count,
          bid: bid,
          bName: bName,
          bAuthor: bAuthor,
          bPrice: bPrice
        })
      }

      this.$axios.get('/getIdByUserName?username=' + name).then(resp => {
        uid = parseInt(resp.data)
        let curTime = new Date()
        console.log(curTime)
        console.log(uid)
        this.$axios
          .post('/check', {
            orders: {
              id: 0,
              orderTime: curTime,
              subTotal: this.subTotal.toFixed(1),
              uid: uid,
              totalPrice: (this.subTotal * 0.95).toFixed(1)
            },
            order_items: orderItems
          })
          .then(resp => {
            if (resp && resp.status === 200) {
              this.$message({
                type: 'success',
                message: '下单成功！'
              })
              this.$store.state.cart.clear()
              this.getSubtotal()
              this.$forceUpdate()
            } else {
              this.$message({
                type: 'error',
                message: '失败！'
              })
            }
          })
      })
    }

    // CheckOut () {
    // }
  }
}
</script>

<style scoped>
.checkOut {
  color: #333;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  border: 0;
  font: inherit;
  font-size: 100%;
  vertical-align: baseline;
  float: right;
  width: 35%;
}
.checkOut ul {
  list-style: none;
}

.checkOut ul li {
  padding: 0.5em;
  text-align: right;
}

.checkOut ul li .label {
  font-family: 'Montserrat', sans-serif;
  font-size: 0.85em;
  text-transform: uppercase;
  color: #777;
}
.checkOut ul li .value {
  color: #333;
  -webkit-font-smoothing: antialiased;
  list-style: none;
  box-sizing: border-box;
  margin: 0;
  border: 0;
  font: inherit;
  font-size: 100%;
  vertical-align: baseline;
  display: inline-block;
  padding: 0 0 0 1em;
  text-align: right;
  letter-spacing: -0.025em;
  width: 35%;
}
.checkOut ul li.final {
  font-size: 1.25em;
  font-weight: bold;
}
.checkOut ul li.final .label {
  font-family: 'Montserrat', sans-serif;
  font-size: 0.85em;
  text-transform: uppercase;
  color: #777;
}
.checkOut ul li.final .value {
  letter-spacing: -0.025em;
  width: 35%;
}
.continue {
  text-decoration: none;
  font-family: 'Montserrat', sans-serif;
  letter-spacing: -0.015em;
  font-size: 1em;
  padding: 1em 3em;
  color: #fff;
  background: #82ca9c;
  font-weight: bold;
  border-radius: 50px;
  float: right;
  text-align: right;
}
.cartBody {
  position: relative;
  top: 70px;
}
.cartBody h1 {
  display: inline-block;
  -webkit-text-size-adjust: 100%;
  text-rendering: optimizeLegibility;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  box-sizing: border-box;
  font-size: 2em;
  line-height: 1;
  font-weight: 400;
  text-align: center;
  margin: 0.5rem 0 1rem;
}

.cartBody h2 {
  -webkit-text-size-adjust: 100%;
  text-rendering: optimizeLegibility;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  box-sizing: border-box;
  line-height: 1;
  text-align: center;
  margin: 0.5rem 0 1rem;
  font-size: 0.75em;
  font-weight: 300;
}

.cartContainer {
  -webkit-text-size-adjust: 100%;
  text-rendering: optimizeLegibility;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  box-sizing: border-box;
  display: block;
  width: 80%;
  max-width: 60rem;
  margin: 0 auto;
}

ol {
  position: relative;
  right: -35px;
  -webkit-text-size-adjust: 100%;
  text-rendering: optimizeLegibility;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  box-sizing: border-box;
  margin: 40px auto 1.5rem;
  list-style: none inside;
  padding-left: 0;
  margin-bottom: 0;
}

ol li {
  -webkit-text-size-adjust: 100%;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  list-style: none inside;
  box-sizing: border-box;
  text-align: justify !important;
  font-size: 0 !important;
  text-rendering: optimizespeed;
  border: 1px solid #bdc3c7;
  margin-bottom: 3rem;
  height: 10rem;
  overflow: hidden;
}

ol li .img {
  -webkit-text-size-adjust: 100%;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  list-style: none inside;
  box-sizing: border-box;
  display: inline-block;
  vertical-align: top;
  font-size: medium;
  text-align: left;
  text-rendering: optimizeLegibility;
  width: 12%;
  border-color: #3498db;
  height: 100%;
}

ol li .info {
  position: relative;
  -webkit-text-size-adjust: 100%;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  list-style: none inside;
  box-sizing: border-box;
  display: inline-block;
  vertical-align: top;
  font-size: medium;
  text-align: left;
  text-rendering: optimizeLegibility;
  width: 75.5%;
  padding: 0.5rem;
  border-color: #3498db;
  height: 100%;
}

ol li .info_name {
  max-width: 90%;
  -webkit-text-size-adjust: 100%;
  color: #686868;
  list-style: none inside;
  text-align: center;
  text-rendering: optimizeLegibility;
  box-sizing: border-box;
  font: 300 2em/1 'Lato', sans-serif;
  letter-spacing: -0.025em;
  margin: 0 0 0.125em;
  max-height: 32px;
  overflow: hidden;
}

ol li .info_desc {
  max-width: 90%;
  -webkit-text-size-adjust: 100%;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  list-style: none inside;
  font-size: medium;
  text-align: center;
  text-rendering: optimizeLegibility;
  box-sizing: border-box;
  max-height: 90px;
  margin: 0 0 1.5em;
  overflow: hidden;
  border-color: #3498db;
}

ol li .info_price {
  -webkit-text-size-adjust: 100%;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  list-style: none inside;
  text-rendering: optimizeLegibility;
  box-sizing: border-box;
  line-height: 1;
  text-align: right;
  margin-top: -1rem;
  font-size: 2em;
  border-color: #3498db;
  position: absolute;
  bottom: 0;
  right: 0;
}

ol li .modify {
  -webkit-text-size-adjust: 100%;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  list-style: none inside;
  box-sizing: border-box;
  display: inline-block;
  vertical-align: top;
  font-size: medium;
  text-rendering: optimizeLegibility;
  width: 12.496358%;
  text-align: right;
  border-left: 1px solid #bdc3c7;
  margin-right: 0;
  height: 100%;
}

ol li .grid {
  -webkit-text-size-adjust: 100%;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  color: #686868;
  list-style: none inside;
  box-sizing: border-box;
  text-align: justify !important;
  font-size: 0 !important;
  text-rendering: optimizespeed;
}

.btn {
  display: inline-block;
  border: none;
  padding: 0.5em 0.75em;
  font-weight: 300;
}

.btn:hover,
.total-price:hover {
  background-color: #3498db;
  color: #ecf0f1;
}

.subtract,
.plus,
.qty {
  width: 33.330557%;
  background-color: transparent;
  color: #686868;
  text-align: center;
}

.qty {
  -webkit-text-size-adjust: 100%;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  list-style: none inside;
  box-sizing: border-box;
  display: inline-block;
  vertical-align: top;
  font-size: medium;
  text-rendering: optimizeLegibility;
  width: 33.330557%;
  background-color: transparent;
  color: #686868;
  text-align: center;
  padding: 0.35em 0.75em;
  margin-right: 0;
}

.price {
  -webkit-text-size-adjust: 100%;
  font: 300 1.25em/1.4 'Lato', sans-serif;
  list-style: none inside;
  text-rendering: optimizeLegibility;
  box-sizing: border-box;
  line-height: 1;
  text-align: right;
  border-top: 1px solid #bdc3c7;
  color: #95a5a6;
  font-size: 1.25em;
  padding: 0.5rem;
}

.remove {
  -webkit-text-size-adjust: 100%;
  list-style: none inside;
  box-sizing: border-box;
  overflow: visible;
  font-family: sans-serif;
  line-height: 1.15;
  margin: 0;
  text-transform: none;
  -webkit-appearance: button;
  display: inline-block;
  border: none;
  padding: 0.5em 0.75em;
  text-align: center;
  font-weight: 300;
  font-size: 0.875em;
  margin-top: 3.35rem;
  background-color: #e74c3c;
  color: #ecf0f1;
  width: 100%;
  visibility: hidden;
  transition: all 0.2s ease;
}
.modify:hover .remove {
  visibility: visible;
}

.remove:hover {
  background-color: #c0392b;
}
</style>
