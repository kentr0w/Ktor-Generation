# Ktor-Generation

## What is it?
Ktor-generation is a tool to generate a Kotlin project, using Ktor Framework.

## How to use it?

### CLI
Just download project, go to `Ktor-Generation-Core` folder and build it by `gradle build`. Then put jar file, that id located in `build/libs` folder, in the desired folder. Then run `java -jar *jar_name` 
where `*jar_name` - name of jar file. If this folder doesn't containt configurations file, then `Ktor-Generation` will use default files.

### Web
If you want to use this service via Web you should:
- Run Ktor server. Go to `Ktor-Generation-Server` folder and then run `gradle run` command.
- Run react. Go to `ktor-generation-react` folder, run `npm install` command to install dependencies and then run `npm start` command.

## Example of configurations file:

### Feature config:
``` YAML
global:
  folder: "user"
  projectName: "own-ktor-project"
  buildType: 'Gradle'
  group: "com.example"
  version: "0.0.1-SNAPSHOT"
  ktorVersion: "1.5.0"
  kotlinVersion: "1.4.21"
  port: "8080"
features:
  routes:
    - name: "Item"
      file: "Item.kt"
      routeDetail:
        - path: "/item"
          requests:
            - type: 'GET'
              path: "/getItem"
            - type: 'POST'
              path: "/saveItem"
  web:
    name: "Static"
    file: "Static.kt"
    template: "user/template"
    resources:
      - remotePath: "/static"
        resource: "index.html"
  database:
    type: 'MYSQL'
    file: "DataBase.kt"
    port: "3306"
    host: "localhost"
    dbName: "test"
    username: "root"
    password: ""
    entities:
      - name: "User"
        file: "DataBase.kt"
        tableName: "User"
        route:
          standardRoutes:
            - 'GETAll'
            - 'SAVE'
            - 'UPDATE'
            - 'DELETE'
          routeDetail:
            - path: "/user"
              requests:
                - type: 'GET'
                  path: "/getUser"
                - type: 'POST'
                  path: "/saveUser"
        primaryKey:
          idName: "id"
          type: 'LONG'
        entityFields:
          - variableName: "name"
            columnName: "name"
            type: 'VARCHAR'
            length: "10"
            fieldDetail:
              - 'NULLABLE'
          - variableName: "age"
            columnName: "age"
            type: 'INTEGER'
            fieldDetail:
              - 'NULLABLE'
  socket:
    file: "Socket.kt"
    name: "websocket"
    path: "web"
    answer: "You said: $text"
    closeWord: "bye"
    closeMessage: "Bye"
```

### Project config:

```YAML
src dir
    controllers dir
        User.kt file
    Application.kt file
```



