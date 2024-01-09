import axios from 'axios';

export class GenreRestService {
    getGenres() {
        return axios.get("/api/genres");
    }

    createGenre(newName) {
        return axios.post("/api/genres", {
                name: newName
            }
        );
    }

    deleteGenre(id) {
        return axios.delete("/api/genres/" + id);
    }
}