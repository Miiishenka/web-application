<template>
  <div class="middle">
    <Sidebar :posts="viewPosts"/>
    <main>
      <Index v-if="page === 'Index'" :posts="posts" :users="users"/>
      <Enter v-if="page === 'Enter'"/>
      <Register v-if="page === 'Register'"/>
      <Users v-if="page === 'Users'" :users="users"/>
      <Post v-if="page === 'Post'" :post="post"
            :user="user" :toViewComments="true"></Post>
      <WritePost v-if="page === 'WritePost'"/>
    </main>
  </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "./main/Users";
import Post from "./main/Post";
import WritePost from "./main/WritePost"

export default {
  name: "Middle",
  data: function () {
    return {
      page: "Index",
      post: null
    }
  },
  components: {
    Register,
    Enter,
    Index,
    Sidebar,
    Users,
    Post,
    WritePost
  },
  props: ["posts", "users", "user"],
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    }
  }, beforeCreate() {
    this.$root.$on("onViewPost", (post) => this.post = post);
    this.$root.$on("onChangePage", (page) => this.page = page)
  }
}
</script>

<style scoped>

</style>
