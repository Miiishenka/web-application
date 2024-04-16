<template>
  <article>
    <div class="information">By {{ comment.user.name }}</div>
    <div class="body">{{ comment.text }}</div>
  </article>
</template>

<script>
export default {
  name: "Comment",
  props: ["comment"]
}
</script>

<style scoped>
.information, .body {
  margin-bottom: 0.7rem;
}
.body {
  white-space: pre-wrap;
}

article {
  word-break: break-word;
}

</style>