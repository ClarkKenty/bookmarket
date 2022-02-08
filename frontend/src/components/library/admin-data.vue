<template>
  <div class="dataBody">
    <h1>Apriori Algorithm</h1>
    <h4>(挖掘关联数据集)</h4>
    <h2>当前订单项数据总数量：{{itemCount}}</h2>
    <div class="block">
      <span class="demonstration">最小支持度</span>
      <el-slider :marks="marks" :max="0.9" :min="0.1" :step="0.01" v-model="value1"></el-slider>
    </div>
    <div class="block">
      <span class="demonstration">最小置信度</span>
      <el-slider :marks="marks" :max="0.9" :min="0.1" :step="0.01" v-model="value2"></el-slider>
    </div>
    <el-button :loading="loading" @click.native="execAlg" type="primary">运行算法</el-button>
    <div class="resultBox">
      <div :key="item.id" v-for="item in association">
        <span class="item1">{{item.myKey.item1}}</span>
        <span class="produce el-icon-right"></span>
        <span class="item2">{{item.myKey.item2}}</span>
        <span class="confidence">置信度为：{{item.confidence}}</span>
      </div>
      <p v-if="association.length">已将关联数据更新至数据库</p>
      <p v-if="this.isclicked&&association.length==0">未找到规则</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'dataDig',
  data () {
    return {
      loading: false,
      association: [],
      itemCount: 0,
      value1: 0.15,
      value2: 0.6,
      min: 0.1,
      max: 0.9,
      isclicked: false,
      marks: {
        0.1: {
          style: {
            color: 'red'
          },
          label: this.$createElement('strong', '0.1')
        },
        0.5: {
          style: {
            color: 'red'
          },
          label: this.$createElement('strong', '0.5')
        },
        0.9: {
          style: {
            color: 'red'
          },
          label: this.$createElement('strong', '0.9')
        }
      }
    }
  },
  mounted: function () {
    this.$axios.get('/itemcount').then(resp => {
      if (resp && resp.status === 200) {
        this.itemCount = resp.data
      }
    })
  },
  methods: {
    formatTooltip (val) {
      return val / 100
    },
    getAso () {
      let _this = this
      this.$axios.get('/getAssociation').then(resp => {
        if (resp && resp.status === 200) {
          this.association = resp.data
          for (let ass of this.association) {
            _this.findBookById(ass)
          }
          this.$forceUpdate()
          this.loading = false
        }
      })
      this.isclicked = true
    },
    findBookById (ass) {
      this.$axios.get('/bookbyid?id=' + ass.myKey.item1).then(resp => {
        if (resp && resp.status === 200) {
          ass.myKey.item1 = resp.data.title
        }
      })
      this.$axios.get('/bookbyid?id=' + ass.myKey.item2).then(resp => {
        if (resp && resp.status === 200) {
          ass.myKey.item2 = resp.data.title
        }
      })
    },

    execAlg () {
      this.loading = true
      let _this = this
      this.$axios
        .get('/apriori?arg1=' + _this.value1 + '&arg2=' + _this.value2)
        .then(resp => {
          if (resp && resp.status === 200) {
            this.$message({
              type: 'success',
              message: '已开始执行算法'
            })
            setTimeout(_this.getAso, 2000)
          }
        })
    }
  }
}
</script>
<style scoped>
h4 {
  font-style: italic;
}
h1 {
  font-family: 'Indie Flower';
}
.resultBox {
  line-height: 30px;
  margin: 0 auto;
  margin-top: 60px;
  background-color: rgb(49, 51, 72);
  width: 70%;
  border-radius: 10px;
}
.block {
  width: 80%;
  margin: 0 auto;
  margin-bottom: 50px;
}
</style>
