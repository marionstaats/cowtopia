<template>
    <v-row justify="center">
        <!-- Image -->
        <v-col cols="10" md="5" class="ma-md-3" >
            <v-img v-bind:src="currentcow.image" max-height="500">
            </v-img>
            <v-btn id="upload_widget" color="accent" class="mt-5" @click="openUploadModal">Change image</v-btn>
        </v-col>

        <!-- Info -->
        <v-col cols="12" md="6" align="center">
            <div v-if="currentcow" class="edit-form pa-5 ma-3">
                <p class="headline">Edit your cattle</p>

                <v-form ref="form" lazy-validation>
                <v-text-field
                    v-model="currentcow.name"
                    :rules="[(v) => !!v || 'name is required']"
                    label="name"
                    required
                    color="secondary"
                ></v-text-field>
                <v-radio-group v-model="currentcow.gender" row required :rules="[(v) => !!v || 'Gender is required']">
                    <v-radio
                        color="secondary"
                        label="Cow"
                        value="cow"
                    ></v-radio>
                    <v-radio
                        color="secondary"
                        label="Bull"
                        value="bull"
                    ></v-radio>
                </v-radio-group>

                <v-textarea
                    v-model="currentcow.description"
                    :rules="[(v) => !!v || 'Description is required']"
                    label="Description"
                    required
                    color="secondary"
                ></v-textarea>

                <label><strong>Status:</strong></label>
                {{ currentcow.published ? "Matched" : "Pending" }}

                <v-divider class="my-5"></v-divider>

                <v-btn v-if="currentcow.published"
                    @click="updatePublished(false)"
                    color="blue" small class="mr-2"
                >
                    UnMatch
                </v-btn>

                <v-btn v-else
                    @click="updatePublished(true)"
                    color="secondary" small class="mr-2 black--text"
                >
                    Already matched
                </v-btn>

                <v-btn color="error" small class="mr-2" @click="deletecow">
                    Delete
                </v-btn>

                <v-btn color="accent" small @click="updatecow">
                    Update
                </v-btn>
                </v-form>

                <p class="mt-3">{{ message }}</p>
            </div>

            <div v-else>
                <p>Please click on a cow...</p>
            </div>


        </v-col>
    </v-row>
</template>

<script>
import cowDataService from "../services/cowDataService";

export default {
    name: "cow",
    data() {
        return {
        currentcow: null,
        message: "",
        };
    },
    methods: {
        getcow(id) {
        cowDataService.get(id)
            .then((response) => {
            this.currentcow = response.data;
            })
            .catch((e) => {
            console.log(e);
            });
        },

        updatePublished(status) {
            var data = {
                id: this.currentcow.id,
                name: this.currentcow.name,
                gender: this.currentcow.gender,
                description: this.currentcow.description,
                published: status,
                image: this.currentcow.image
            };

            cowDataService.update(this.currentcow.id, data)
                .then(() => {
                this.currentcow.published = status;
                })
                .catch((e) => {
                console.log(e);
                });
        },

        updatecow() {
        cowDataService.update(this.currentcow.id, this.currentcow)
            .then((response) => {
            console.log(response.data);
            this.message = "The cow was updated successfully!";
            })
            .catch((e) => {
            console.log(e);
            });
        },

        deletecow() {
        cowDataService.delete(this.currentcow.id)
            .then(() => {
            this.$router.push({ name: "cows" });
            })
            .catch((e) => {
            console.log(e);
            });
        },

        openUploadModal() {
            window.cloudinary.openUploadWidget(
                { cloud_name: 'dfqpsmhyl',
                upload_preset: 'ml_default_preset'
                },
                (error, result) => {
                if (!error && result && result.event === "success") {
                    this.currentcow.image = result.info.url;
                    this.updateImage(); 
                    }
                }).open();
        },

        updateImage() {
            var data = {
                id: this.currentcow.id,
                name: this.currentcow.name,
                gender: this.currentcow.gender,
                description: this.currentcow.description,
                published: this.currentcow.status,
                image: this.currentcow.image
            };

            cowDataService.update(this.currentcow.id, data)
                .then((res) => {
                console.log(res);
                })
                .catch((e) => {
                console.log(e);
                });
        }
    },
    mounted() {
        this.message = "";
        this.getcow(this.$route.params.id);
    },
};
</script>

<style>
.edit-form {
    max-width: 400px;
    margin: auto;
    border: 1px solid #E0DFD5;
}
</style>