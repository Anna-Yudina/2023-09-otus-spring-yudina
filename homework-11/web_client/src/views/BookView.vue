<template>
    <div class="container">
        <div>
            <h4>Список книг</h4>
            <table class="table table-bordered table-sm">
                <thead class="table-primary">
                <tr>
                    <th class="col col-lg-1">id</th>
                    <th class="col col-lg-3">Название книги</th>
                    <th class="col col-lg-3">ФИО автора</th>
                    <th class="col col-lg-1">жанр</th>
                    <th class="col col-lg-1"></th>
                    <th class="col col-lg-1"></th>
                    <th class="col col-lg-1"></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="book in books" :key="book.id" scope="row">
                    <td>{{ book.id }}</td>
                    <td>{{ book.title }}</td>
                    <td>{{ book.author.fullName }}</td>
                    <td>{{ book.genre.name }}</td>
                    <td>
                        <button @click="showComments(book)" class="btn btn-link">комментарии</button>
                    </td>
                    <td>
                        <button @click="showEditBook(book)" class="btn btn-link">редактировать</button>
                    </td>
                    <td>
                        <button @click="deleteBook(book.id)" class="btn btn-link">удалить</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div v-if="isEdit">
            <br>
            <h4>Редактирование книги:</h4>
            <form>
                <table border="1" class="table table-bordered table-sm">
                    <thead class="table-primary">
                    <tr>
                        <th class="col col-lg-1">id</th>
                        <th class="col col-lg-3">Название книги</th>
                        <th class="col col-lg-3">ФИО автора</th>
                        <th class="col col-lg-2">жанр</th>
                        <th class="col col-lg-2"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr scope="row">
                        <td>{{ editedBook.id }}</td>
                        <td>
                            <input type="text" class="form-control" v-model="editedBook.title"
                                   :class="{ 'is-invalid': isEditedNameInvalid }">
                            <div class="invalid-feedback">Название не должно быть пустым!</div>
                        </td>
                        <td>
                            <select class="form-select" v-model="editedBook.author">
                                <option v-for="author in authors" :key="author.id"
                                        :value="author">{{ author.fullName }}
                                </option>
                            </select>
                        </td>
                        <td>
                            <select v-model="editedBook.genre" class="form-select">
                                <option v-for="genre in genres" :key="genre.id"
                                        :value="genre">{{ genre.name }}
                                </option>
                            </select>
                        </td>
                        <td>
                            <button type="button" @click="editBook(editedBook)" class="btn btn-outline-primary">
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
        <div class="container border">
            <h4>Добавить новую книгу</h4>
            <form>
                <div class="row mb-3">
                    <label for="title" class="col-sm-2 col-form-label">Название книги:</label>
                    <div class="col-sm-10">
                        <input id="title" name="title" type="text" class="form-control" v-model="createdBookTitle"
                               :class="{ 'is-invalid': isCreatedTitleInvalid }"/>
                        <div class="invalid-feedback">Название не должно быть пустым!</div>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">ФИО автора:</label>
                    <div class="col-sm-10">
                        <select class="form-select" v-model="createdBookAuthor"
                                :class="{ 'is-invalid': isCreatedAuthorInvalid }">
                            <option v-for="author in authors" :key="author.id"
                                    :value="author">
                                {{ author.fullName }}
                            </option>
                        </select>
                        <div class="invalid-feedback">Нужно выбрать автора!</div>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">жанр:</label>
                    <div class="col-sm-10">
                        <select class="form-select" v-model="createdBookGenre"
                                :class="{ 'is-invalid': isCreatedGenreInvalid }">
                            <option v-for="genre in genres" :key="genre.id"
                                    :value="genre">{{ genre.name }}
                            </option>
                        </select>
                        <div class="invalid-feedback">Нужно выбрать жанр!</div>
                    </div>
                </div>
                <div class="d-grid d-md-flex justify-content-md-end">
                    <button type="button" class="btn btn-outline-primary" @click="createBook">Сохранить</button>
                </div>
            </form>
        </div>
        <br>
        <div v-if="isShowComment">
            <h4>Комментарии к книге "{{ selectedBook.title }}" {{ selectedBook.author.fullName }}:</h4>
            <table class="table table-bordered table-sm">
                <thead class="table-primary">
                <tr>
                    <th class="col col-lg-1">id</th>
                    <th class="col col-lg-3">текст комментария</th>
                    <th class="col col-lg-1"></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="comment in comments" :key="comment.id">
                    <td>{{ comment.id }}</td>
                    <td>{{ comment.text }}</td>
                    <td>
                        <button @click="deleteComment(selectedBook, comment)" class="btn btn-link">удалить</button>
                    </td>
                </tr>
                </tbody>
            </table>
            <br>
            <div>
                <form @submit.prevent="createComment">
                    <div class="input-group mb-3">
                        <label for="comment" class="input-group-text">Добавить комментарий</label>
                        <input id="comment" name="comment" type="text" class="form-control" v-model="newCommentText"
                               :class="{ 'is-invalid': isCreatedCommentTextInvalid }"/>
                        <button type="submit" class="btn btn-outline-primary">Сохранить</button>
                        <div class="invalid-feedback">Текст комментария не должен быть пустым!</div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>

import {BookRestService} from "@/service/BookRestService";
import {AuthorRestService} from "@/service/AuthorRestService";
import {GenreRestService} from "@/service/GenreRestService";
import {CommentRestService} from "@/service/CommentRestService";

export default {
    name: 'HomeView',

    data() {
        return {
            books: [],
            authors: [],
            author: null,
            genres: [],
            comments: [],
            isEdit: false,
            isShowComment: false,
            editedBook: null,
            isEditedNameInvalid: false,
            isCreatedTitleInvalid: false,
            isCreatedCommentTextInvalid: false,
            isCreatedAuthorInvalid: false,
            isCreatedGenreInvalid: false,
            selectedBook: null,
            createdBookGenre: null,
            createdBookAuthor: null,
            createdBookTitle: "",
            newCommentText: "",
            bookService: new BookRestService(),
            authorService: new AuthorRestService(),
            genreService: new GenreRestService(),
            commentService: new CommentRestService()
        }
    },

    mounted() {
        this.loadBooks();
        this.loadAuthors();
        this.loadGenres();
    },

    methods: {
        loadBooks() {
            this.isEdit = false;
            this.isEditedNameInvalid = false;
            this.isCreatedTitleInvalid = false;

            this.bookService.getBooks()
                .then(response => (this.books = response.data))
                .then(response => {
                    console.log(response);
                });
        },

        loadAuthors() {
            this.authorService.getAuthors()
                .then(response => (this.authors = response.data))
                .then(response => {
                    console.log(response);
                });
        },

        loadGenres() {
            this.genreService.getGenres()
                .then(response => (this.genres = response.data))
                .then(response => {
                    console.log(response);
                });
        },

        loadComments(book) {
            this.commentService.getCommentsByBookId(book.id)
                .then(response => (this.comments = response.data))
                .then(response => {
                    console.log(response);
                    this.isCreatedCommentTextInvalid = false;
                })
        },

        deleteBook(id) {
            this.bookService.deleteBook(id)
                .then(response => {
                    console.log(response);
                    this.loadBooks();
                });
        },

        showEditBook(book) {
            this.isEdit = true;
            this.editedBook = book;
        },

        showComments(book) {
            this.isShowComment = true;
            this.isCreatedCommentTextInvalid = false;
            this.selectedBook = book;

            this.loadComments(book);
        },

        editBook(book) {
            if (book.title.length === 0) {
                this.isEditedNameInvalid = true;
                return;
            }

            this.bookService.editBook(book)
                .then(response => {
                    console.log(response);
                    this.isEdit = false;
                    this.loadBooks();
                })
        },

        cansel() {
            this.loadBooks();
            this.isEdit = false;
        },

        createBook() {
            this.isCreatedTitleInvalid = false;
            this.isCreatedAuthorInvalid = false;
            this.isCreatedGenreInvalid = false;
            this.isEditedNameInvalid = false;
            this.isEdit = false;

            if (this.createdBookTitle.length === 0
                || this.createdBookAuthor === null
                || this.createdBookGenre === null) {
                if (this.createdBookTitle.length === 0) {
                    this.isCreatedTitleInvalid = true;
                }

                if (this.createdBookAuthor === null) {
                    this.isCreatedAuthorInvalid = true;
                }

                if(this.createdBookGenre === null) {
                    this.isCreatedGenreInvalid = true;
                }

                return;
            }

            let createdBook = {
                title: this.createdBookTitle,
                author: this.createdBookAuthor,
                genre: this.createdBookGenre
            };

            this.bookService.createBook(createdBook).then(response => {
                console.log(response);
                this.createdBookTitle = "";
                this.createdBookAuthor = null;
                this.createdBookGenre = null;
                this.loadBooks();
            });
        },

        deleteComment(book, comment) {
            this.isCreatedCommentTextInvalid = false;

            this.commentService.deleteComment(book.id, comment.id)
                .then(response => {
                    console.log(response);
                    this.loadComments(book);
                })
        },

        createComment() {
            if (this.newCommentText.length === 0) {
                this.isCreatedCommentTextInvalid = true;
                return;
            }

            this.commentService.createComment(this.selectedBook.id, this.newCommentText)
                .then(response => {
                    console.log(response);
                    this.loadComments(this.selectedBook);
                    this.newCommentText = "";
                })
        }
    }
}
</script>
