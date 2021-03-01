import http from "../http-common";

class cowDataService {
    getAll() {
        return http.get("/cows");
    }

    get(id) {
        return http.get(`/cows/${id}`);
    }

    create(data) {
        return http.post("/cows", data);
    }

    update(id, data) {
        return http.put(`/cows/${id}`, data);
    }

    delete(id) {
        return http.delete(`/cows/${id}`);
    }

    deleteAll() {
        return http.delete(`/cows`);
    }

    findByName(name) {
        return http.get(`/cows?name=${name}`);
    }

}

export default new cowDataService();