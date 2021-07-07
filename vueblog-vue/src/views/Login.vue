<!-- 登录页面 -->

<template>
    <div>
        <el-container>
            <el-main>
                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="ruleForm.username"></el-input>
                    </el-form-item>

                    <el-form-item label="密码" prop="password">
                        <el-input type="password" v-model="ruleForm.password"></el-input>
                    </el-form-item>
                    <div class="register">
                        <span>还没账号？</span>
                        <el-link type="primary" href="/user/register">点击注册</el-link>
                    </div>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                ruleForm: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 2, max: 10, message: '用户名长度在 2 到 10 个字符之间', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'change' },
                        { min:3, max: 16, message: '密码长度在 3 到 16 个字符之间', trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this
                        
                        this.$axios.post('/login', this.ruleForm)
                            .then(res => {
                            //从返回的结果的请求头中获取到JwtToken的信息
                            const jwt = res.headers['authorization']
                            const userInfo = res.data.data
                            const hasLogin = true
                            console.log(userInfo)
                            
                            //把数据共享，使用store提交token和用户信息的状态
                            _this.$store.commit("SET_TOKEN",jwt) //设置store中的token信息
                            _this.$store.commit("SET_USERINFO", userInfo) //设置store中的用户信息
                            _this.$store.commit("SET_HASLOGIN", hasLogin) //设置登录状态为已登录
                            
                            //获取用户信息
                            console.log(_this.$store.getters.getUser)
                            
                            _this.$router.push("/blogs")
                        })
                        .catch(() => {
                            this.$message({
                                type: 'error',
                                message: '账户名或密码错误！'
                            });
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>
    .el-header, .el-footer {
        padding: 10px;
        background-color: #87d6ff;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
        line-height: 200px;
    }
    
    .register {
        margin-bottom: 15px;
        font-size: 14px;
    }
    
    .register *{
        vertical-align: middle;
    }
    
    .register span{
        padding-right: 5px;
    }
    
    .el-button {
        margin-right: 20px;
    }
    

    .el-main {
        /*background-color: #E9EEF3;*/
        color: #333;
        text-align: center;
        line-height: 10px;
    }

    body > .el-container {
        margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }
    
    .el-header * {
        vertical-align: middle;
    }
    
    .logo{
        height: 68%;
    }
    
    .el-header span{
        font-size: 38px;
        margin-left: 25px;
        font-weight: 800;
        font-family: "Times New Roman";
    }
    
    .demo-ruleForm{
        max-width: 450px;
        margin: 10% auto;
    }
    
    .login-form{
        margin-left: 35%;
        margin-top: 5%;
        background-color: #e7e7e7;
        width: 400px;
        height: 380px;
        float: left;
        z-index: 9999;
        position: fixed;
        opacity: 0.75;
    }
</style>