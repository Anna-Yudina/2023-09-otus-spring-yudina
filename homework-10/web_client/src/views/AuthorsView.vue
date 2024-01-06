<template>
    <div class="container">
        <div>
            <h4>Список авторов</h4>
            <table class="table table-bordered table-sm">
                <thead class="table-primary">
                <tr>
                    <th class="col col-lg-1">id</th>
                    <th class="col col-lg-3">ФИО автора</th>
                    <th class="col col-lg-1"></th>
                    <th class="col col-lg-1"></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="author in authors" :key="author.id" scope="row">
                    <td>{{ author.id }}</td>
                    <td>{{ author.fullName }}</td>
                    <td>
                        <button @click="showEditAuthor(author)" class="btn btn-link">редактировать</button>
                    </td>
                    <td>
                        <button @click="deleteAuthor(author.id)" class="btn btn-link">удалить</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <br>
        <div v-if="isEdit">
            <h4>Редактирование автора:</h4>
            <form>
                <table class="table table-bordered table-sm">
                    <thead class="table-primary">
                    <tr>
                        <th class="col col-lg-1">id</th>
                        <th class="col col-lg-3">ФИО автора</th>
                        <th class="col col-lg-1"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr scope="row">
                        <td>{{ editedAuthor.id }}</td>
                        <td>
                            <input type="text" v-model="editedAuthor.fullName" class="form-control"
                                   :class="{ 'is-invalid': isEditedNameInvalid }">
                            <div class="invalid-feedback">Имя не должно быть пустым!</div>
                        </td>
                        <td>
                            <button type="button" @click="editAuthor(editedAuthor)" class="btn btn-outline-primary">
                                Сохранить
                            </button>
                            <button type="button" @click="cansel" class="btn btn-outline-primary">
                                Отмена
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <br>
        <h4>Добавить нового автора</h4>
        <form @submit.prevent="createAuthor">
            <div class="input-group mb-3">
                <label for="name" class="input-group-text">Введите полное имя автора:</label>
                <input id="name" name="name" v-model="newName" type="text" class="form-control"
                       :class="{ 'is-invalid': isCreatedNameInvalid }"/>
                <button type="submit" class="btn btn-outline-primary">Сохранить</button>
                <div class="invalid-feedback">Имя не должно быть пустым!</div>
            </div>
        </form>
    </div>
</template>

<script>
import {AuthorRestService} from "@/service/AuthorRestService";

export default {
    data() {
        return {
            authors: [],
            isEdit: false,
            editedAuthor: null,
            newName: "",
            isEditedNameInvalid: false,
            isCreatedNameInvalid: false,
            service: new AuthorRestService()
        };
    },

    mounted() {
        this.loadAuthors();
    },

    methods: {
        loadAuthors() {
            this.isCreatedNameInvalid = false;
            this.isEditedNameInvalid = false;
            this.edit = false;
            this.service.getAuthors()
                .then(response => (this.authors = response.data))
                .then(response => {
                    console.log(response);
                });
        },

        deleteAuthor(id) {
            this.service.deleteAuthor(id)
                .then(response => {
                    console.log(response);
                    this.loadAuthors();
                });
        },

        showEditAuthor(author) {
            this.isCreatedNameInvalid = false;
            this.isEdit = true;
            this.editedAuthor = author;
            console.log("isEdit: " + this.isEdit);
            console.log("editedAuthor: " + this.editedAuthor);
        },

        editAuthor(author) {
            if (author.fullName.length === 0) {
                this.isEditedNameInvalid = true;
                return;
            }

            this.service.editAuthor(author)
                .then(response => {
                    console.log(response);
                    this.isEdit = false;
                    this.loadAuthors();
                })
        },

        createAuthor() {
            this.isEditedNameInvalid = false;
            this.isEdit = false;
            if (this.newName.length === 0) {
                this.isCreatedNameInvalid = true;
                return;
            }

            this.service.createAuthor(this.newName).then(response => {
                console.log(response);
                this.loadAuthors();
                this.newName = "";
            });
        },

        cansel() {
            this.loadAuthors();
            this.isEdit = false;
        }
    }
}
</script>
