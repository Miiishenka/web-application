<template>
  <div id="app">
    <Header :user="user"/>
    <Middle :posts="posts" :users="users" :user="user"/>
    <Footer/>
  </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
  name: 'App',
  components: {
    Footer,
    Middle,
    Header
  },
  data: function () {
    return {
      user: null,
      posts: [],
      users: [],
    }
  },
  beforeMount() {
    if (localStorage.getItem("jwt") && !this.user) {
      this.$root.$emit("onJwt", localStorage.getItem("jwt"));
    }

    axios.get("/api/1/posts").then(response => {
      this.posts = response.data;
    });

    axios.get("/api/1/users").then(response => {
      this.users = response.data;
    });


  },
  beforeCreate() {
    this.$root.$on("onRegister", (login, name, password) => {
      if (password === "") {
        this.$root.$emit("onRegisterValidationError", "Password is required");
        return;
      }

      axios.post("/api/1/users", {
        login, name, password
      }).then(response => {
        this.users.unshift(response.data);
        this.$root.$emit("onEnter", login, password);
      }).catch(error => {
        this.$root.$emit("onRegisterValidationError", error.response.data);
      });
    });

    this.$root.$on("onEnter", (login, password) => {
      if (password === "") {
        this.$root.$emit("onEnterValidationError", "Password is required");
        return;
      }

      axios.post("/api/1/jwt", {
        login, password
      }).then(response => {
        localStorage.setItem("jwt", response.data);
        this.$root.$emit("onJwt", response.data);
      }).catch(error => {
        this.$root.$emit("onEnterValidationError", error.response.data);
      });
    });

    this.$root.$on("onJwt", (jwt) => {
      localStorage.setItem("jwt", jwt);

      axios.get("/api/1/users/auth", {
        params: {
          jwt
        }
      }).then(response => {
        this.user = response.data;
        this.$root.$emit("onChangePage", "Index");
      }).catch(() => this.$root.$emit("onLogout"))
    });

    this.$root.$on("onLogout", () => {
      localStorage.removeItem("jwt");
      this.user = null;
    });

    this.$root.$on("onWritePost", (title, text) => {
      const jwt = localStorage.getItem("jwt");
      if (!jwt) {
        this.$root.$emit("onWritePostValidationError", "User is not authorized");
        return;
      }
      axios.post("/api/1/posts", {
        title, text, jwt
      }).then(() => {
        axios.get("/api/1/posts").then(response => {
          this.posts = response.data;
        });
        this.$root.$emit("onChangePage", "Index");
      }).catch(error => {
        this.$root.$emit("onWritePostValidationError", error.response.data);
      });
    });

    this.$root.$on("onWriteComment", (post, text) => {
      const jwt = localStorage.getItem("jwt");
      if (!jwt) {
        this.$root.$emit("onWriteCommentValidationError", "User is not authorized");
        return;
      }

      axios.post("/api/1/comments", {
        text, post, jwt
      }).then(() => {
        axios.get("/api/1/posts").then(response => {
          this.posts = response.data;
          this.$root.$emit("onViewPost", this.posts.find(p => p.id === post.id));
          this.$root.$emit("onChangePage", "Post");
        });
      }).catch(error => {
        this.$root.$emit("onWriteCommentValidationError", error.response.data);
      });
    });
  }
}
</script>

<style>
#app {

}
</style>
