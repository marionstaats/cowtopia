<template>
  <v-row>
    <v-col
      v-for="cow in cows.slice(0, 4)"
      :key="cow.id"
      class="d-flex child-flex"
      cols="12"
      md="6"
    >
      <v-hover>

        <template v-slot:default="{ hover }">
          <v-img
            v-bind:src="cow.image"
            aspect-ratio="1"
            class="grey lighten-2"
          >
              <v-fade-transition>
                <v-overlay
                  v-if="hover"
                  absolute
                  color="accent"
                >
                  <v-btn @click="editCow(cow.id)">
                    <v-icon left>mdi-cow</v-icon>
                    {{cow.name}}
                  </v-btn>
                </v-overlay>
              </v-fade-transition>
          </v-img>
        </template>

      </v-hover>

    </v-col>
  </v-row>
</template>

<script>

  export default {
    props: ["cows"],
    data: () => ({
      overlay: false,
    }),
    methods: {
      editCow(id) {
      this.$router.push({ name: "cow-details", params: { id: id } });
      }
    }
  }
</script>

<style>

</style>