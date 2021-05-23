var global_data = {
    global: [        
            {title: 'projectName', value: ''},
            {title: 'buildType', value: 'Gradle'},
            {title: 'group', value: 'com.example'},
            {title: 'version', value: '0.0.1-SNAPSHOT'},
            {title: 'ktorVersion', value: '1.5.0'},
            {title: 'kotlinVersion', value: '1.4.21'},
            {title: 'port', value: '8080'},        
    ],
    database: [
            {title: 'dbName', value: ''},
            {title: 'host', value: 'localhost'},
            {title: 'port', value: '3306'},
            {title: 'username', value: 'root'},
            {title: 'password', value: ''},
            {title: 'type', value: 'MYSQL'},
            {title: 'path', value: ''},        
    ],
    socket: [
            {title: 'name', value: ''},
            {title: 'webPath', value: ''},
            {title: 'answer', value: ''},
            {title: 'closeWord', value: ''},
            {title: 'closeMessage', value: ''},
        ],
    web: [
            {title: 'name', value: ''},
            {title: 'file', value: ''},
    ],

    
    dbEntities: [],
    dbFields: [],
    webRoutes: [],
    globalRoutes: [],
    localRoutes: [],
    miniRoutes: [],   
    
    dbRoute: []
}

export default global_data