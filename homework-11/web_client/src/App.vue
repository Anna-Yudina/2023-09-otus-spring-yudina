<template>
    <div id="app">
        <nav id="nav">
            <router-link to="/">Книги</router-link>
            |
            <router-link to="/authors">Авторы</router-link>
            |
            <router-link to="/genres">Жанры</router-link>
        </nav>
        <router-view/>
    </div>
</template>

<style>
nav {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    padding: 30px;
}

nav a {
    font-weight: bold;
    color: #2c3e50;
}

nav a.router-link-exact-active {
    color: #394fd5;
}
</style>
