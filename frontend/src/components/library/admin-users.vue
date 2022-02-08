<template>
  <div class="adminUsersBody">
    <h1>用户管理</h1>
    <div class="userCards">
      <el-card
        :key="user.id"
        class="box-card"
        style="position:relative;margin-bottom:50px;background-color: rgb(49, 51, 72);border-color:rgb(49, 51, 72);border-radius:10px;"
        v-for="user in users"
      >
        <div class="clearfix" slot="header" style="background-color: rgb(49, 51, 72)">
          <span>用户ID：{{user.id}}</span>

          <el-button
            @click="deleteUser(user)"
            circle
            icon="el-icon-delete"
            style="position:absolute; right:5px;top:10px;"
            type="danger"
          ></el-button>
        </div>
        <p class="inline">
          <span class="desc">用户名：</span>
          <span class="desc2">{{user.username}}</span>
          <el-button @click="openEdit1(user)" circle icon="el-icon-edit" style type="primary"></el-button>
        </p>
        <p class="inline">
          <span class="desc">邮箱：</span>
          <span class="desc2">{{user.email}}</span>
          <el-button @click="openEdit2(user)" circle icon="el-icon-edit" style type="primary"></el-button>
        </p>
        <p class="inline">
          <span class="desc">联系方式：</span>
          <span class="desc2">{{user.phone}}</span>
          <el-button @click="openEdit3(user)" circle icon="el-icon-edit" style type="primary"></el-button>
        </p>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: 'adminUsers',
  data () {
    return { users: [] }
  },
  mounted () {
    this.loadUsers()
  },
  methods: {
    loadUsers () {
      this.$axios.get('/findAllUsers').then(resp => {
        this.users = resp.data
      })
    },
    openEdit1 (user) {
      let _this = this
      this.$prompt('修改用户名', '提示', {
        inputValue: user.username,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      })
        .then(({ value }) => {
          user.username = value
          this.$axios
            .post('/updateUser', {
              id: user.id,
              username: user.username,
              password: user.password,
              email: user.email,
              phone: user.phone
            })
            .then(resp => {
              this.$message({
                type: 'success',
                message: '修改成功'
              })
            })
        })
        .catch(() => {
          _this.$message({
            type: 'info',
            message: '取消修改'
          })
        })
    },
    openEdit2 (user) {
      let _this = this
      this.$prompt('修改邮箱', '提示', {
        inputValue: user.email,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      })
        .then(({ value }) => {
          user.email = value
          this.$axios
            .post('/updateUser', {
              id: user.id,
              username: user.username,
              password: user.password,
              email: user.email,
              phone: user.phone
            })
            .then(resp => {
              this.$message({
                type: 'success',
                message: '修改成功'
              })
            })
        })
        .catch(() => {
          _this.$message({
            type: 'info',
            message: '取消修改'
          })
        })
    },
    openEdit3 (user) {
      let _this = this

      this.$prompt('修改联系方式', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: user.phone
      })
        .then(({ value }) => {
          user.phone = value
          this.$axios
            .post('/updateUser', {
              id: user.id,
              username: user.username,
              password: user.password,
              email: user.email,
              phone: user.phone
            })
            .then(resp => {
              this.$message({
                type: 'success',
                message: '修改成功'
              })
            })
        })
        .catch(() => {
          _this.$message({
            type: 'info',
            message: '取消修改'
          })
        })
    },
    deleteUser (user) {
      let _this = this
      this.$confirm('确认删除该用户？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.get('/deleteUser?id=' + user.id).then(resp => {
          this.$message({
            type: 'success',
            message: '删除成功'
          })
          _this.loadUsers()
        })
      })
    }
  }
}
</script>

<style scoped>
.userCards div {
  background-color: rgb(42, 43, 61);
}
.userCards {
  width: 60%;
  margin: 0 auto;
  position: relative;
}
.inline {
  display: block;
  width: 400px;
  margin-left: 31%;
  text-align: left;
}
.desc {
  display: inline-block;
  width: 80px;
}
.desc2 {
  display: inline-block;
  width: 200px;
}
</style>
