import axios from 'axios';

export class AuthorRestService {
    getAuthors() {
        return axios.get("/api/authors");
    }

    createAuthor(newName) {
        return axios.post("/api/authors", {
                fullName: newName
            }
        );
    }

    deleteAuthor(id) {
        return axios.delete("/api/authors/" + id);
    }

    editAuthor(author) {
        return axios.put("/api/authors/" + author.id, author);
    }

}