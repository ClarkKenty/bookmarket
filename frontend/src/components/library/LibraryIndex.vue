<template>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <SideMenu @indexSelect="listBy" ref="sideMenu"></SideMenu>
    </el-aside>
    <el-main style="overflow:hidden;">
      <books class="books-area" ref="booksArea"></books>
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import Books from './Books'

export default {
  beforeCreate: function () {
    document.body.classList.add('wheatBody')
  },
  name: 'AppLibrary',
  components: { Books, SideMenu },
  methods: {
    listBy () {
      this.listByCategory()
    },
    listByCategory () {
      var _this = this
      var cid = this.$refs.sideMenu.cid

      var url = ''
      if (parseInt(cid) === 10) {
        let uid = 0
        let name = this.$store.state.user.username
        this.$axios.get('/getIdByUserName?username=' + name).then(resp => {
          uid = parseInt(resp.data)
          url = 'categories/' + uid + '/books'
          this.$axios.get(url).then(resp => {
            if (resp && resp.status === 200) {
              _this.$refs.booksArea.books = resp.data
            }
          })
        })
      } else {
        url = 'categories/' + cid + '/books'
        this.$axios.get(url).then(resp => {
          if (resp && resp.status === 200) {
            _this.$refs.booksArea.books = resp.data
          }
        })
      }
    }
  }
}
</script>

<style scoped>
.books-area {
  width: 100%;
  margin-left: auto;
  margin-right: auto;
}
</style>
<style>
html {
  overflow-y: scroll;
}
</style>
