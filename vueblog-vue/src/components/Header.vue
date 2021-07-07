<template>
    <div class="m-content">
        <div class="m-logo">
            <img src="../assets/blog.png">
            <span>个人博客</span>
        </div>
        <div class="m-user" >
            <el-avatar :size="50" :src="user.avatar"></el-avatar>
            <div>{{user.username}}</div>
        </div>

        <div class="m-action">
            <span><el-link href="/blogs">主页</el-link></span>
            <el-divider direction="vertical"></el-divider>
            <span><el-link type="success" href="/blog/add">发表博客</el-link></span>
            <el-divider direction="vertical"></el-divider>
            <span v-show="!hasLogin"><el-link type="primary" href="/login">登录</el-link></span>
            <span v-show="hasLogin"><el-link type="danger" @click="logout">退出</el-link></span>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data() {
            return {
                user: {
                    username: '请先登录',
                    avatar: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
                },
                hasLogin: false
            }
        },
        methods: {
            logout() {
                const _this = this
                _this.$axios.get("/logout",{
                    headers: {
                        "Authorization": localStorage.getItem("token")
                    }
                }).then(res => {
                    _this.$store.commit("REMOVE_INFO")
                    _this.$router.push("/login")
                })
            }
        },
        created() {
            if (this.$store.getters.getState) {
                this.user.username = this.$store.getters.getUser.username
                this.user.avater = this.$store.getters.getUser.avatar
                this.hasLogin = true
            }
        }
    }
</script>

<style scoped>
    .m-content{
        max-width: 960px;
        margin: 0 auto;
        text-align: center;
    }
    .m-action{
        margin-bottom: 30px;
    }
    
    .m-logo {
        padding: 10px 0;
        height: 80px;
    }
    
    .m-logo * {
        vertical-align: middle;
    }
    
    .m-logo img{
        height: 65%;
    }
    
    .m-logo span {
        font-size: 38px;
        margin-left: 25px;
        font-weight: 800;
        font-family: "Times New Roman";
    }
    
    .m-user{
        margin-bottom: 15px;
    }
    
</style>