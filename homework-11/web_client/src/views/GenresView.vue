<template>
    <div class="container">
        <h4>Список жанров</h4>
        <table class="table table-bordered table-sm">
            <thead class="table-primary">
            <tr>
                <th class="col col-lg-1">id</th>
                <th class="col col-lg-3">Наименование жанра</th>
                <th class="col col-lg-1"></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="genre in genres" :key="genre.id" scope="row">
                <td>{{genre.id}}</td>
                <td>{{ genre.name }}</td>
                <td>
                    <button @click="deleteGenre(genre.id)" class="btn btn-link">удалить</button>
                </td>
            </tr>
            </tbody>
        </table>
        <br>
        <h4>Добавить новый жанр</h4>
        <form @submit.prevent="createGenre">
            <div class="input-group mb-3">
                <label for="name" class="input-group-text">Введите название жанра:</label>
                <input id="name" name="name" type="text" v-model="newName" class="form-control"
                       :class="{ 'is-invalid': isCreatedNameInvalid }"/>
                <button type="submit" class="btn btn-outline-primary">Сохранить</button>
                <div class="invalid-feedback">Название жанра не должно быть пустым!</div>
            </div>
        </form>
    </div>
</template>

<script>

import {GenreRestService} from "@/service/GenreRestService";

export default {
    data() {
        return {
            genres: [],
            isEdit: false,
            isCreatedNameInvalid: false,
            newName: "",
            service: new GenreRestService()
        };
    },

    mounted() {
        this.loadGenres();
    },

    methods: {
        loadGenres() {
            this.isCreatedNameInvalid = false;

            this.service.getGenres()
                .then(response => (this.genres = response.data))
                .then(response => {
                    console.log(response);
                });
        },

        deleteGenre(id) {
            this.service.deleteGenre(id)
                .then(response => {
                    console.log(response);
                    this.loadGenres();
                });
        },

        createGenre() {
            if (this.newName.length === 0) {
                this.isCreatedNameInvalid = true;
                return;
            }

            this.service.createGenre(this.newName).then(response => {
                console.log(response);
                this.loadGenres();
                this.newName = "";
            });
        },
    }
}
</script>
