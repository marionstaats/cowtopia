<template>

  <v-row>

    <!-- Search and table column -->
    <v-col cols="12" md="8">
      <v-row justify="center">
          
          <v-col cols="10" md="4">
            <v-text-field v-model="search" label="Search by name" color="secondary"></v-text-field>
          </v-col>

          <v-col cols="3" md="1" class="mr-2" align-self="center">
            <v-btn small color="accent" @click="searchName()">
              Search
            </v-btn>
          </v-col>

          <v-col cols="3" md="1" align-self="center">
            <v-btn small color="blue" @click="reset()">
              Reset
            </v-btn>
          </v-col>


          <v-col cols="12" md="10" class="secondary">
            <v-card class="mx-auto pa-5" tile>
              <v-card-title>All</v-card-title>

              <v-data-table
                :headers="headers"
                :items="cows"
              >
                <template v-slot:[`item.actions`]="{ item }">
                  <v-icon small class="mr-2" @click="editCow(item.id)">mdi-pencil</v-icon>
                  <v-icon small @click="deleteCow(item.id)">mdi-delete</v-icon>
                </template>

                <template v-slot:[`item.match`]="{ item }" >
                  <v-tooltip right color="error">
                    <template v-slot:activator="on" >
                      <v-icon  class="mr-2" @click="openMatchDialog(item, item.image)" v-on="on">mdi-heart</v-icon>
                    </template>
                    <span>Find match</span>
                  </v-tooltip>
                </template>

              </v-data-table>

              <v-card-actions v-if="cows.length > 0">
                <v-btn small color="error" @click="dialog = true">
                  Remove All
                </v-btn>

              </v-card-actions>
            </v-card>
          </v-col>
          


      </v-row>
    

    </v-col>
    
    <!-- Pictures right column -->
    <v-col cols="12" md="4">
      <Carousel v-bind:cows="cows" />
    </v-col>

    <!-- Dialog popup - Remove all -->
    <v-dialog
        v-model="dialog"
        persistent
        max-width="450"
      >
        <v-card class="error">
          <v-card-title class="headline">
            Are you sure you want to delete all?
          </v-card-title>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              text
              @click="dialog = false"
            >
              No
            </v-btn>
            <v-btn
              text
              @click="removeAllCows"
            >
              Yes delete all
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <!-- Match Dialog -->

      <v-dialog
      v-model="matchDialog"
      persistent
      max-width="900"
      >
        <v-card color="secondary" >
          <v-card-title class="headline black--text">
            Looking for a match...
          </v-card-title>
          <v-container>
            <v-row>

              <v-col cols="6" align="center">
                <v-img :src="matchCowImage" aspect-ratio="1" max-width="300"></v-img>
              </v-col>

              <v-col cols="6" align="center">
                <v-img :src="matchedCowImage" aspect-ratio="1" max-width="300"></v-img>
              </v-col>

              <v-col cols="12" align-self="center">
                <v-card-actions>
                  <v-spacer></v-spacer>

                  <v-btn
                    color="error"
                    @click="matchTheCows"
                  >
                    Yes!
                  </v-btn>
                  <v-btn
                    color="blue"
                    @click="matchDialog = false; index = 0; matchedCow={}; matchedCowImage=null;"
                  >
                    No thanks
                  </v-btn>
                  <v-spacer></v-spacer>
                </v-card-actions>

              </v-col>

            </v-row>
          </v-container>

        </v-card>
      </v-dialog>

  </v-row>


</template>

<script>
import cowDataService from "../services/cowDataService";
import Carousel from '@/components/Carousel';

export default {
  name: "cows-list",
  components: {
    Carousel
  },
  data() {
    return {
      cows: [],
      search: "",
      headers: [
        { text: "Name", align: "start", sortable: true, value: "name" },
        { text: "Gender", value: "gender", sortable: true},
        { text: "Description", value: "description", sortable: false },
        { text: "Status", value: "status", sortable: true },
        { text: "Actions", value: "actions", sortable: false },
        { text: "Match", value: "match", sortable: false}
      ],
      dialog: false,
      matchDialog: false,
      matchCow: {},
      matchCowImage: "",
      matchCowId: "",
      matchedCowImage: "",
      matchedCow: {},
      index: 0
    };
  },
  methods: {
    retrieveCows() {
      cowDataService.getAll()
        .then((response) => {
            this.cows = response.data.map(this.getDisplayCow);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    reset(){
      this.search = "";
      this.retrieveCows();
    },

    refreshList() {
      this.retrieveCows();
    },

    removeAllCows() {
      cowDataService.deleteAll()
        .then(() => {
          this.dialog = false;
          this.cows = [];
        })
        .catch((e) => {
          console.log(e);
        });
    },

    searchName() {
      cowDataService.findByName(this.search)
        .then((response) => {
          if (response.data.length > 0) {
            this.cows = response.data.map(this.getDisplayCow);
          } else {
            this.cows = []
          }
        })
        .catch((e) => {
          console.log(e);
        });
    },

    editCow(id) {
      this.$router.push({ name: "cow-details", params: { id: id } });
    },

    deleteCow(id) {
      cowDataService.delete(id)
        .then(() => {
          this.refreshList();
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getDisplayCow(cow) {
      return {
        id: cow.id,
        name: cow.name.length > 30 ? cow.name.substr(0, 30) + "..." : cow.name,
        gender: cow.gender,
        description: cow.description.length > 30 ? cow.description.substr(0, 30) + "..." : cow.description,
        status: cow.published ? "Matched" : "Pending",
        image: cow.image
      };
    },

    openMatchDialog(cow, image) {
      this.matchDialog = true;
      this.matchCow = cow;
      this.matchCowId = cow.id;
      this.matchCowImage = image;

      let interval = setInterval(() => {
        if (this.index >= this.cows.length) {
        this.index = 0;
        }
        if (this.matchCowImage == this.cows[this.index].image){
          this.index++;
        }
        
        this.matchedCowImage = this.cows[this.index].image
        this.matchedCow = this.cows[this.index]
        this.index++
      }, 1000)

      setTimeout(() => {
        clearInterval(interval)
      }, (Math.floor((Math.random() * 10000))+2000))

    },

    matchTheCows() {
      let dataMatchedCow = {
          id: this.matchedCow.id,
          name: this.matchedCow.name,
          gender: this.matchedCow.gender,
          description: this.matchedCow.description,
          published: true,
          image: this.matchedCow.image
      };

      let dataMatchCow = {
          id: this.matchCow.id,
          name: this.matchCow.name,
          gender: this.matchCow.gender,
          description: this.matchCow.description,
          published: true,
          image: this.matchCow.image
      };


      cowDataService.update(this.matchedCow.id, dataMatchedCow)
          .then(() => {
            cowDataService.update(this.matchCow.id, dataMatchCow)
              .then(() => {
                this.refreshList();
                this.matchDialog = false; 
                this.index = 0;
            })
          })
          .catch((e) => {
            console.log(e);
          });
    }

  },
  mounted() {
    this.retrieveCows();
  },
};
</script>

<style scoped>
.list {
  max-width: 850px;
}
</style>