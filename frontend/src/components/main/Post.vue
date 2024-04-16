<template>
  <div>
    <article>
      <div class="title"><a href="#" @click.prevent="onViewPost">{{ post.title }}</a></div>
      <div class="information">By {{ post.user.name }}</div>
      <div class="body">{{ post.text }}</div>
      <div class="footer">
        <div class="left">
          <img src="../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
          <span class="positive-score">+173</span>
          <img src="../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
        </div>
        <div class="right">
          <img src="../../assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
          2 days ago
          <img src="../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
          <a href="#" @click.prevent="onViewPost">{{ post.comments.length }}</a>
        </div>
      </div>
    </article>
    <template v-if="toViewComments">
      <div v-if="user" class="form">
        <div class="header">Write Comment</div>
        <div class="body">
          <form @submit.prevent="onWriteComment">
            <div class="field">
              <div class="name">
                <label for="text">Text:</label>
              </div>
              <div class="value">
                <textarea id="text" name="text" v-model="text"></textarea>
              </div>
            </div>
            <div class="error">{{ error }}</div>
            <div class="button-field">
              <input type="submit" value="Write">
            </div>
          </form>
        </div>
      </div>
      <Comment v-for="comment in post.comments" :comment="comment" :key="comment.id"/>
    </template>
  </div>
</template>

<script>
import Comment from "./Comment"
export default {
  name: "SinglePost",
  components: {
    Comment
  },
  data: function () {
    return {
      text: "",
      error: ""
    }
  },
  props: ["post", "user", "toViewComments"],
  methods: {
    onViewPost: function () {
      this.$root.$emit("onViewPost", this.post);
      this.$root.$emit("onChangePage", "Post");
    },
    onWriteComment: function () {
      this.error = "";
      this.$root.$emit("onWriteComment", this.post, this.text);
    }
  },
  beforeMount() {
    this.$root.$on("onWriteCommentValidationError", error => this.error = error);
    this.$root.$on("onChangePage", () => {
      this.text = "";
      this.error = "";
    });
  }
}
</script>

<style scoped>
  .title, .information, .body {
    margin-bottom: 0.7rem;
  }
  .title a {
    text-decoration: none;
    color: inherit;
  }
  .left img {
    padding: 0 0.2rem;
  }
  .body {
    white-space: pre-wrap;
  }
  article {
    word-break: break-word;
  }
</style>