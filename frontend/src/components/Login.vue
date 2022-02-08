/* eslint-disable camelcase */
/* eslint-disable camelcase */
<template >
  <div class="loginBody">
    <div class="log_in">
      <h2>Login</h2>
      <p>USERNAME *</p>
      <input id="login_user" type="text" v-model="loginForm.username" />
      <br />
      <p>PASSWORD *</p>
      <input id="login_pass" type="password" v-model="loginForm.password" />
      <button v-on:click="login">LOG IN</button>
    </div>

    <div class="create_account">
      <h2>Create Account</h2>
      <p>USERNAME *</p>
      <input type="text" v-model="signupForm.username" />
      <p>PASSWORD *</p>
      <input type="password" v-model="signupForm.password" />
      <p>EMAIL *</p>
      <input type="text" v-model="signupForm.email" />
      <p>Phone Number *</p>
      <input type="text" v-model="signupForm.phone" />
      <button v-on:click="signup">Create Account</button>
    </div>
  </div>
</template>

<script>

export default {
  beforeCreate: function () {
    document.body.classList.add('pinkBody')
  },
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      signupForm: {
        username: '',
        password: '',
        email: '',
        phone: ''
      },
      responseResult: []
    }
  },
  methods: {
    login () {
      var _this = this
      console.log(this.$store.state)
      this.$axios
        .post('/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        })
        .then(successResponse => {
          console.log(successResponse)
          console.log(_this.loginForm)
          if (successResponse.data.code === 200) {
            _this.$store.commit('login', _this.loginForm)
            var path = this.$route.query.redirect
            this.$router.replace({
              path: path === '/' || path === undefined ? '/book' : path
            })
          } else {
            _this.$message({
              type: 'error',
              message: '账号或密码错误'
            })
          }
        })
        .catch(failResponse => {})
    },
    signup () {
      var _this = this
      this.$axios
        .post('/signup', {
          username: this.signupForm.username,
          password: this.signupForm.password,
          email: this.signupForm.email,
          phone: this.signupForm.phone
        })
        .then(successResponse => {
          console.log(successResponse)
          if (successResponse.data.code === 200) {
            _this.$message({
              type: 'success',
              message: '注册成功'
            })
          } else if (successResponse.data.code === 600) {
            _this.$message({
              type: 'error',
              message: '此账号已注册'
            })
          } else {
            _this.$message({
              type: 'error',
              message: '注册失败'
            })
          }
        })
        .catch(failResponse => {})
    }
  }
}
</script>

<style>
.loginBody {
  background-color: rgb(252, 238, 234);
  font-size: 16px;
  font-family: satisfy;
}
.pinkBody {
  background-color: rgb(252, 238, 234);
}
</style>
<style scoped>
.wheatBody {
  background-color: rgb(244, 227, 220);
}
.loginBody {
  background-color: rgb(252, 238, 234);
  font-size: 16px;
  font-family: satisfy;
}
.pinkBody {
  background-color: rgb(252, 238, 234);
}
.log_in {
  width: 500px;
  float: left;
  height: 600px;
  margin-left: 150px;
  margin-top: 80px;
}
h2 {
  font-size: 42px;
}
p {
  line-height: 1.19;
}
input {
  background-color: #f9dcd4 !important;
  display: block;
  height: 1.5em;
  width: 95%;
  padding: 0.75em 0.88rem;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  color: #000;
  background-clip: padding-box;
  border: 0 solid #ced4da;
  border-radius: 20px;
  text-shadow: none;
  outline: none;
}
button {
  margin-top: 40px;
  cursor: pointer;
  font-size: 18px;
  padding: 0.8em 2.6em 0.9em;
  position: relative;
  text-transform: uppercase;
  overflow: hidden;
  border: none;
  display: inline-block;
  font-weight: 400;
  text-align: center;
  vertical-align: middle;
  line-height: 1.5;
  border-radius: 2em;
  color: #fff;
  background-color: #000;
}
.create_account {
  float: left;
  width: 500px;
  height: 650px;
  margin-left: 150px;
  margin-top: 10px;
}
</style>
