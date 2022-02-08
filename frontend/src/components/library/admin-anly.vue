<template>
  <div class="anlyBody">
    <div class="desc">
      <h1>Data&nbsp; Analysis</h1>
      <div class="desc_box">
        <p>
          <span class="icon el-icon-circle-check"></span>
          <span class="tit">总销量：</span>
          <span class="num">{{t1}}</span>
        </p>
      </div>
      <div class="desc_box">
        <p>
          <span class="icon el-icon-notebook-2"></span>
          <span class="tit">图书总量：</span>
          <span class="num">{{t2}}</span>
        </p>
      </div>
      <div class="desc_box">
        <p>
          <span class="icon el-icon-user-solid"></span>
          <span class="tit">用户数量：</span>
          <span class="num">{{t3}}</span>
        </p>
      </div>
    </div>
    <div class="charts">
      <div class="page-line-chart chart">
        <h4>销量折线图</h4>
        <ve-line :data="chartData" :theme="custheme" class="line"></ve-line>
      </div>
      <div class="page-line-chart chart">
        <h4>各类图书销量对比</h4>
        <ve-Pie :data="piechartData" :theme="custheme" class="line"></ve-Pie>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'anly',
  data () {
    return {
      t1: 0,
      t2: 0,
      t3: 0,
      daily: [],
      cpy: [],
      custheme: require('../../assets/theme/chalk.json'),
      chartData: {
        columns: ['日期', '销售量'],
        rows: []
      },
      piechartData: {
        columns: ['类型', '数量'],
        rows: []
      }
    }
  },
  mounted: function () {
    this.$axios.get('/tit').then(resp => {
      if (resp && resp.status === 200) {
        this.t1 = resp.data[0].sold
        this.t2 = resp.data[0].book
        this.t3 = resp.data[0].user
      }
    })
    this.$axios.get('/ctyCompare').then(resp => {
      if (resp && resp.status === 200) {
        this.cpy = resp.data
        for (let cp of this.cpy) {
          this.piechartData.rows.push({
            类型: cp.name,
            数量: cp.count
          })
        }
      }
    })
    this.$axios.get('/daily').then(resp => {
      if (resp && resp.status === 200) {
        this.daily = resp.data
        for (let da of this.daily) {
          this.chartData.rows.push({
            日期: da.date.substr(5),
            销售量: da.count
          })
        }
      }
    })
  }
}
</script>
<style scoped>
h1{
  font-family: satisfy,'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-size: 35px;
}
.desc_box p {
  line-height: 100%;
}
.icon {
  margin-right: 80px;
  font-size: 40px;
}
.tit {
  margin-right: 30px;
}
.desc_box {
  padding: 16px;
  width: 360px;
  height: 60px;
  background-color: rgb(49, 51, 72);
  float: left;
  margin-right: 80px;
  border-radius: 10px;
}
.num {
  font-size: 40px;
}
.desc_box:first-of-type {
  margin-left: 30px;
}
.desc_box:last-child {
  margin-right: 0;
}
.desc {
  margin-top: 30px;
}
.desc h2 span {
  margin-right: 120px;
}
h2 {
  color: #d1d5db;
}
h4 {
  color: #9ca3af;
}
.line {
  border-radius: 20px;
  height: 40px;
  overflow: hidden;
}
.chart {
  margin-top: 20px;
  float: left;
}
.chart:nth-of-type(1) {
  margin-left: 100px;
}
.chart:nth-of-type(2) {
  margin-left: 150px;
}
.page-line-chart {
  width: 500px;
}
</style>
