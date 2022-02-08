<template>
  <div class="bookBody">
    <el-row>
      <search-bar @onSearch="searchResult" ref="searchBar"></search-bar>
      <el-tooltip
        :key="item.id"
        :manual="true"
        effect="dark"
        placement="right"
        v-for="(item) in books.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        v-model="item.disabled"
      >
        <p slot="content" style="font-size: 14px;margin-bottom: 6px;">{{item.title}}</p>
        <p slot="content" style="font-size: 13px;margin-bottom: 6px">
          <span>{{item.author}}</span> /
          <span>{{item.date}}</span> /
          <span>{{item.press}}</span>
        </p>
        <p class="abstract" slot="content" style="width: 300px">{{item.abs}}</p>
        <el-card
          bodyStyle="padding:10px"
          class="book"
          shadow="hover"
          style="background-color:rgb(248, 243, 237);width: 135px;margin-bottom: 20px;height: 245px;float: left;margin-right: 15px"
        >
          <div @click="toggleItem(item)" class="cover">
            <img
              :src="item.cover"
              alt="封面"
              style="border-radius: 5px;box-shadow: 1px 16px 16px -16px rgba(0, 0, 0, 0.40);"
            />
          </div>
          <div class="info">
            <div class="title" style="height:20px;overflow:hidden;">
              <a href>{{item.title}}</a>
            </div>
            <!-- <i @click="deleteBook(item.id)" class="el-icon-delete"></i> -->
            <i @click="buyBook(item.id,item)" class="el-icon-shopping-cart-full"></i>
          </div>
          <div class="author">{{item.author}}</div>
          <div class="price" style="font-size:12px">￥{{item.price}}</div>
        </el-card>
      </el-tooltip>
    </el-row>
    <el-row style="margin-bottom:90px;">
      <el-pagination
        :current-page="currentPage"
        :page-size="pagesize"
        :total="books.length"
        @current-change="handleCurrentChange"
      ></el-pagination>
    </el-row>
  </div>
</template>

<script>
import SearchBar from './SearchBar'
export default {
  name: 'Books',
  components: { SearchBar },
  data () {
    return {
      books: [],
      cart: [],
      currentPage: 1,
      pagesize: 64
    }
  },
  mounted: function () {
    document.body.classList.remove('pinkBody')
    this.loadBooks()
    var ss = document.styleSheets[0]
    ss.insertRule('::-webkit-scrollbar-thumb {background-color: rgba(204, 199, 199, 0.7)}', 0)
    ss.insertRule('::-webkit-scrollbar-thumb:hover {background-color: rgb(218, 183, 141)}', 0)
  },
  methods: {
    toggleItem (item) {
      item.disabled = !item.disabled
      this.$forceUpdate()
    },
    loadBooks () {
      var _this = this
      this.$axios.get('/books').then(resp => {
        if (resp && resp.status === 200) {
          _this.books = resp.data
        }
      })
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
      console.log(this.currentPage)
    },
    searchResult () {
      var _this = this
      let keywords = this.$refs.searchBar.keywords
      this.$axios
        .get('/search?keywords=' + keywords)
        .then(resp => {
          if (resp && resp.status === 200) {
            _this.books = resp.data
            this.currentPage = 1
          }
        })
    },

    deleteBook (id) {
      this.$confirm('此操作将永久删除该书籍, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$axios.post('/delete', { id: id }).then(resp => {
            if (resp && resp.status === 200) {
              this.loadBooks()
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      // alert(id)
    },
    buyBook (id, item) {
      this.$message({
        type: 'success',
        message: '已加入购物车'
      })
      if (!this.$store.state.cart.has(id)) {
        this.$store.state.cart.set(id, { count: 1, item: item })
      } else {
        let retrive = this.$store.state.cart
        retrive.set(id, {
          count: retrive.get(id).count + 1,
          item: retrive.get(id).item
        })
      }
      console.log(this.$store.state.cart)
    },
    editBook (item) {
      this.$refs.edit.dialogFormVisible = true
      this.$refs.edit.form = {
        id: item.id,
        cover: item.cover,
        title: item.title,
        author: item.author,
        date: item.date,
        press: item.press,
        abs: item.abs,
        category: {
          id: item.category.id.toString(),
          name: item.category.name
        }
      }
    }
  }
}
</script>
<style scoped>
.bookBody {
  position: relative;
  top: 70px;
}
.cover {
  width: 115px;
  height: 172px;
  margin-bottom: 7px;
  cursor: pointer;
}

img {
  width: 115px;
  height: 172px;
  /*margin: 0 auto;*/
}

.title {
  font-size: 14px;
  text-align: left;
}

.author {
  color: #333;
  height: 17px;
  overflow: hidden;
  font-size: 13px;
  margin-bottom: 6px;
  text-align: left;
}

.abstract {
  display: block;
  line-height: 17px;
}

.el-icon-shopping-cart-full {
  cursor: pointer;
  float: right;
  transform: scale(1.5);
  position: relative;
  top: 5px;
}

.switch {
  display: flex;
  position: absolute;
  left: 780px;
  top: 25px;
}

a {
  text-decoration: none;
}

a:link,
a:visited,
a:focus {
  color: #3377aa;
}
::-webkit-scrollbar {
  width: 20px;
}

::-webkit-scrollbar-track {
  background-color: transparent;
}

::-webkit-scrollbar-thumb {
  background-color: rgba(204, 199, 199, 0.7);
  border-radius: 20px;
  border: 6px solid transparent;
  background-clip: content-box;
}

::-webkit-scrollbar-thumb:hover {
  background-color: rgb(218, 183, 141);
}
</style>
