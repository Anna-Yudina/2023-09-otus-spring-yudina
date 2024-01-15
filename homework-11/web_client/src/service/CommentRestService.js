import axios from 'axios';

export class CommentRestService {
    getCommentsByBookId(bookId) {
        return axios.get("/api/books/" + bookId + "/comments");
    }

    deleteComment(bookId, commentId) {
        return axios.delete("/api/books/" + bookId + "/comments/" + commentId);
    }

    createComment(bookId, newText) {
        return axios.post("/api/books/" + bookId + "/comments", {
                text: newText
            }
        );
    }
}