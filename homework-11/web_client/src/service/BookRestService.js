import axios from 'axios';

export class BookRestService {
    getBooks() {
        return axios.get("/api/books");
    }

    createBook(book) {
        return axios.post("/api/books", book);
    }

    deleteBook(id) {
        return axios.delete("/api/books/" + id);
    }

    editBook(book) {
        return axios.put("/api/books/" + book.id, book);
    }
}