<template>
  <div>
    <Post v-for="post in posts" :post="post" :key="post.id"/>
  </div>
</template>

<script>
import Post from "./Post";

export default {
  name: "Index",
  components: {
    Post
  },
  props: ["posts", "users"]
}
</script>

<style scoped>

</style>