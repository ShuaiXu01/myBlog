<!-- 博客详情页 -->

<template>
    <div>
        <Header></Header>
        <div class="m-blog">
            <h2>{{blog.title}}</h2>
            <el-link icon="el-icon-edit" v-if="ownBlog">
                <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}">编辑</router-link>
            </el-link>
            <el-link type="danger" v-if="ownBlog" style="margin-left: 10px" @click="open()">
                删除
            </el-link>
            <el-divider></el-divider>
            <div class="markdown-body" v-html="blog.content"></div>
        </div>
    </div>
</template>

<script>
    import Header from "../components/Header";
    import "github-markdown-css/github-markdown.css"
    export default {
        name: "BlogDetail",
        components: {Header},
        data() {
            return {
                blog: {
                    id: "",
                    title: "",
                    content: ""
                },
                ownBlog: false
            }
        },
        created() {
            const blogId = this.$route.params.blogId
            // console.log(blogId)
            const _this = this
            if (blogId) {
                this.$axios.get('/blog/' + blogId).then(res => {
                    const blog = res.data.data
                    _this.blog.id = blog.id
                    _this.blog.title = blog.title
                    
                    //对内容进行渲染
                    var MarkdownIt = require("markdown-it")
                    var md = new MarkdownIt()
                    var result = md.render(blog.content)
                    _this.blog.content = result
                    
                    //当博客的登录id等于当前用户id才展示编辑按钮
                    _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)
                })
            }
        },
        methods: {
            open() {
                this.$confirm('是否确认删除此条博客?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let blogId = new URLSearchParams;
                    blogId.append("id", this.blog.id)
                    // console.log('id -> ' + blogId)
                    this.$axios.post('/blog/delete', blogId,{
                        headers: {
                            "Authorization": localStorage.getItem("token")
                        }
                    }).then(() => {
                       /* this.$alert('删除成功', '提示', {
                            confirmButtonText: '确定',
                            callback: action => {
                                this.$router.push("/blogs")
                            }
                        });*/
                       this.$message({
                           type: 'success',
                           message: '删除成功！'
                       });
                       setTimeout(() => {
                           this.$router.push("/blogs")
                       }, 1000)
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        }
    }
</script>

<style scoped>
    .m-blog{
        margin: 20px auto;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 100%;
        min-height: 650px;
        padding: 20px 15px;
    }
</style>