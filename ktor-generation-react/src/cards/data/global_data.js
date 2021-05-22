var global_data = [

    {
        feature: 'global', children: [
            {title: 'projectName', value: ''},
            {title: 'buildType', value: 'Gradle'},
            {title: 'group', value: 'com.example'},
            {title: 'version', value: '0.0.1-SNAPSHOT'},
            {title: 'ktorVersion', value: '1.5.0'},
            {title: 'kotlinVersion', value: '1.4.21'},
            {title: 'port', value: '8080'},        
        ]
    },
    {
        feature: 'database', children: [
            {title: 'dbName', value: ''},
            {title: 'host', value: 'localhost'},
            {title: 'port', value: '3306'},
            {title: 'username', value: 'root'},
            {title: 'password', value: ''},
            {title: 'type', value: 'MYSQL'},
            {title: 'path', value: ''},        
        ]
    },
    {
        feature: 'socket', children: [
            {title: 'name', value: ''},
            {title: 'webPath', value: ''},
            {title: 'answer', value: ''},
            {title: 'closeWord', value: ''},
            {title: 'closeMessage', value: ''},
        ]
    },
    {
        feature: 'web', children: [
            {title: 'name', value: ''},
        ]
    }
]

export default global_data