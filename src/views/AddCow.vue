<template>
    <div class="submit-form mt-5 mx-auto pa-5">
        <p class="headline">Add your cattle</p>

        <div v-if="!submitted">
            <v-form ref="form" lazy-validation>
                <v-text-field
                v-model="cow.name"
                :rules="[(v) => !!v || 'Name is required']"
                label="Name"
                required
                color="secondary"
                ></v-text-field>
                <v-radio-group v-model="cow.gender" row>
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
                v-model="cow.description"
                :rules="[(v) => !!v || 'Description is required']"
                label="Description"
                color="secondary"
                required
                ></v-textarea>
                
                <v-btn id="upload_widget" class="secondary black--text" @click="openUploadModal">Upload profile pic</v-btn>
            </v-form>
            <p v-if="successfullUpload">Picture successfully added!</p>
            <v-btn v-if="allOk" color="accent" class="mt-3" @click="savecow">Submit</v-btn>
            </div>

            <div v-else>
            <v-card class="mx-auto black">
                <v-card-title>
                Submitted successfully!
                </v-card-title>

                <v-card-subtitle>
                Click the button to add new cattle.
                </v-card-subtitle>

                <v-card-actions>
                <v-btn color="accent" @click="newcow">Add</v-btn>
                </v-card-actions>
            </v-card>
        </div>
    </div>
</template>

<script>
import cowDataService from "../services/cowDataService";

export default {
    name: "add-cow",
    data() {
        return {
            cow: {
                id: null,
                name: "",
                gender: "cow",
                description: "",
                image: "",
                published: false,
            },
            submitted: false,
            successfullUpload: false,
            allOk: false
        }
    },
    methods: {
        openUploadModal() {
            window.cloudinary.openUploadWidget(
                { cloud_name: 'dfqpsmhyl',
                upload_preset: 'ml_default_preset'
                },
                (error, result) => {
                if (!error && result && result.event === "success") {
                    this.cow.image = result.info.url; 
                    this.successfullUpload = true;
                    this.allOk = true;       }
                }).open();
        },
        savecow() {
            var data = {
                name: this.cow.name,
                gender: this.cow.gender,
                description: this.cow.description,
                image: this.cow.image
            };

            cowDataService.create(data)
                .then((response) => {
                    this.cow.id = response.data.id;
                    this.submitted = true;
                })
                .catch((e) => {
                    console.log(e);
                });
        },

        newcow() {
            this.submitted = false;
            this.cow = {};
            this.successfullUpload = false;
            this.allOk = false;
        },
    },
};
</script>

<style>
.submit-form {
    max-width : 500px;
    border: 1px solid #E0DFD5;
}
</style>