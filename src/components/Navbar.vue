<template>
    <v-app-bar
        app
        absolute
        color=primary
        prominent
        src="/cownavbar.jpg"
    >
        <template v-slot:img="{ props }">
            <v-img
            v-bind="props"
            gradient="to top right, rgba(100,115,201,.7), rgba(25,32,72,.7)"
            ></v-img>
        </template>

        <v-toolbar-title>Find a match for your amazing cattle</v-toolbar-title>

        <v-spacer></v-spacer>

        <v-tooltip bottom color="error">
            <template v-slot:activator="{ on, attrs }">
            <v-btn icon v-bind="attrs" v-on="on" @click="snackbar = true">
                <v-icon>mdi-heart</v-icon>
            </v-btn>
            </template>
            <span>Send extra love</span>
        </v-tooltip>

        <template v-slot:extension>
            <v-tabs align-with-title>
            <v-tab to="/">All</v-tab>
            <v-tab to="/add">Add new</v-tab>
            </v-tabs>
        </template>

        <v-snackbar
        v-model="snackbar"
        :timeout="timeout"
        color="accent"
        >
        Thank you!
        </v-snackbar>
    </v-app-bar>
</template>

<script>

export default {
    name: 'Navbar',
    data: () => ({
        snackbar: false,
        timeout: 2000,
    }),
}
</script>

