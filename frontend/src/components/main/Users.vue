<template>
  <div class="datatable">
    <div class="caption">Users</div>
    <table>
      <thead>
      <tr>
        <th>Id</th>
        <th>Login</th>
        <th>Name</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id">
        <td>{{ user.id }}</td>
        <td>{{ user.login }}</td>
        <td>{{ user.name }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

export default {
  name: "Users",
  props: ["users"],
}
</script>

<style scoped>

</style>