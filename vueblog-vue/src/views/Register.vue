<!-- 注册页面 -->

<template>
    <div>
        <el-container>
            <el-main>
                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="ruleForm.username" placeholder="请输入用户名"></el-input>
                    </el-form-item>

                    <el-form-item label="密码" prop="password">
                        <el-input type="password" v-model="ruleForm.password" placeholder="请输入密码"></el-input>
                    </el-form-item>
                    
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="ruleForm.email" placeholder="请输入邮箱账号"></el-input>
                    </el-form-item>

                    <div class="back">
                    <el-link type="primary" href="/login">&lt;&lt;返回登录</el-link>
                    </div>
                    
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
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
                    password: '',
                    email: ''
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 2, max: 10, message: '用户名长度在 2 到 10 个字符之间', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min:3, max: 16, message: '密码长度在 3 到 16 个字符之间', trigger: 'blur'}
                    ],
                    email: [
                        { required: true, message: '请输入邮箱', trigger: 'blur'},
                        { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this
                        this.$axios.post('/user/register', this.ruleForm).then(res => {
                            this.$message({
                                type: 'success',
                                message: '注册成功！'
                            });
                            setTimeout(() => {
                                _this.$router.push("/login")
                            }, 1000)
                        }).catch(() => {
                            this.$message({
                                type: 'error',
                                message: '该用户已被注册！'
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
    

    .el-button {
        margin-right: 20px;
    }


    .back{
        margin: 15px 45px;
        text-align: left;
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