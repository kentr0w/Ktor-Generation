var fields = [
    {name: 'projectName', type: 'text', title: 'Project Name', default: "My project", value: ''},
    {name: 'group', type: 'text', title: 'Group', default: 'com.example', value: ''},
    {name: 'version', type: 'text', title: 'Project Version', default: '0.0.1-SNAPSHOT', value: ''},
    {name: 'buildType', type: 'select', title: 'Build tool', options: ["Gradle", "Maven"], default: 'Gradle', value: ''},
    {name: 'ktorVersion', type: 'select', title: 'Ktor Version', options: ["1.4.3", "1.5.1", "1.5.2", "1.5.3"], default: '1.4.3', value: ''},
    {name: 'kotlinVersio', type: 'select', title: 'Kotlin Version', options: ["1.4.21", "1.4.20", "1.4.10"], default: '1.4.21', value: ''},
    {name: 'port', type: 'text', title: 'Port', default: '8080', value: ''}
]

export default fields