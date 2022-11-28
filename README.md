# **Trianafy**
## Descripción:
Aplicación realizada para el curso de 2°DAM por Alejandro Damas Villatoro
Consiste en una API desarrollada con Java/Spring, y documentada con Swagger en la que el usuario puede consumir información de una aplicación similar a Spotify con artistas, canciones y playlists

Para mas información en el proyecto, ir a:  http://localhost:8080/swagger-ui/index.html#/
## Autores:
 - [Alejandro Damas Villatoro](https://github.com/SirMowglo)
## End-points:
### Artistas
- **GET**  /artist/
 - **GET**  /artist/{id}
 - **PUT** /artist/{id}
 - **POST** /artist/
 - **DELETE** /artist/{id}
### Canciones
- **GET**  /song/
 - **GET**  /song/{id}
 - **PUT** /song/{id}
 - **POST** /song/
 - **DELETE** /song/{id}
### Playlists
- **GET**  /list/
 - **GET**  /list/{id}
 -  **GET** /list/{idList}/song/
 - **GET** /list/{idList}/song/{idSong}
 - **PUT** /list/{id}
 - **POST** /list/
 - **POST** /list/{idList}/song/{idSong}
 - **DELETE** /list/{id}
 - **DELETE** /list/{idList}/song/{idSong}
## Imágenes del Proyecto:
### Swagger-UI
![Trianafy_OpenAPI](https://user-images.githubusercontent.com/96171740/204174686-e171a4de-3c7d-44e9-9e2d-8727d98e3ca0.png)
