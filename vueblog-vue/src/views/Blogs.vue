<!-- 博客列表页 -->

<template>
    <div>
        <Header></Header>
        <div class="block">
            <el-timeline>
                <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs">
                    <el-card>
                        <h4>
                            <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
                                {{blog.title}}
                            </router-link>
                        </h4>
                        <p>{{blog.description}}</p>
                    </el-card>
                </el-timeline-item>
            </el-timeline>

            <el-pagination class="m-page"
                    background
                    layout="prev, pager, next" 
                           :current-page="currentPage"
                           :page-size="pageSize"
                           :total="total"
                           @current-change=page
            >
            </el-pagination>
        </div>

    </div>
</template>

<script>
    import Header from "../components/Header";
    export default {
        name: "Blogs.vue",
        components: {Header},
        data() {
            return {
                blogs: {},
                currentPage: 1,
                total: 0,
                pageSize: 5,
                userId: "",
            }
        },
        methods: {
            page(currentPage) {
                const _this = this
                if (_this.$store.getters.getState) {
                    _this.$axios.get("/blogs?currentPage=" + currentPage + "&userId=" + _this.userId).then(res => {
                        console.log(res)
                        _this.blogs = res.data.data.records
                        _this.currentPage = res.data.data.current
                        _this.total = res.data.data.total
                        _this.pageSize = res.data.data.size
                    })
                }else {
                    _this.$axios.get("/blogs?currentPage=" + currentPage).then(res => {
                        console.log(res)
                        _this.blogs = res.data.data.records
                        _this.currentPage = res.data.data.current
                        _this.total = res.data.data.total
                        _this.pageSize = res.data.data.size
                    })
                }
            }
        },
        created() {
           if (this.$store.getters.getState){
               this.userId = this.$store.getters.getUser.id
           }
           this.page(1)
        }
    }
</script>

<style scoped>
    .m-page{
        margin: 0 auto;
        text-align: center;
    }
</style>