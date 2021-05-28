var global_data = {
    global: [        
            {title: 'projectName', value: undefined},
            {title: 'buildType', value: 'Gradle'},
            {title: 'group', value: 'com.example'},
            {title: 'version', value: '0.0.1-SNAPSHOT'},
            {title: 'ktorVersion', value: '1.5.0'},
            {title: 'kotlinVersion', value: '1.4.21'},
            {title: 'port', value: '8080'},        
    ],
    database: [
            {title: 'dbName', value: undefined},
            {title: 'host', value: undefined},
            {title: 'port', value: undefined},
            {title: 'username', value: undefined},
            {title: 'password', value: ''},
            {title: 'type', value: 'MYSQL'},
            {title: 'path', value: undefined},        
    ],
    socket: [
            {title: 'name', value: undefined},
            {title: 'webPath', value: undefined},
            {title: 'answer', value: undefined},
            {title: 'closeWord', value: undefined},
            {title: 'closeMessage', value: undefined},
        ],
    web: [
            {title: 'name', value: undefined},
            {title: 'file', value: undefined},
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