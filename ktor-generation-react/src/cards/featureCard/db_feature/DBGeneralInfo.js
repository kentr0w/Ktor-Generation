import React, {Component} from 'react'
import './../feature.css'
import './db.css'
import './../../../App.css'

export default class DBGeneralInfo extends Component {

    setNewItem = (name, it) => {               
        const item = {title: name, value: it.target.value}
        this.props.addNewItemByTitle('database',item)
    }

    render() {
        return(
            <div className = 'db-feature-main-info'>
                        <div className = 'db-general-name'>
                            <p className = 'pp'>Database name: </p>
                            &nbsp;
                            &nbsp;
                            <input className = 'pretty-input' placeholder = "Name" onChange = {(it) => {
                                this.setNewItem('dbName', it)
                            }}></input>
                        </div>
                        <div className = 'db-general-name'>
                            <p className = 'pp'>Database host: </p>
                            &nbsp;
                            &nbsp;
                            <input className = 'pretty-input' placeholder = "Host" onChange = {(it) => {
                                this.setNewItem('host', it)
                            }}></input>
                        </div>
                        <div className = 'db-general-name'>
                            <p className = 'pp'>Database port: </p>
                            &nbsp;
                            &nbsp;
                            <input className = 'pretty-input' placeholder = "Port" onChange = {(it) => {
                                this.setNewItem('port', it)
                            }}></input>
                        </div>
                        <div className = 'db-general-name'>
                            <p className = 'pp'>Database username: </p>
                            &nbsp;
                            &nbsp;
                            <input className = 'pretty-input' placeholder = "Username" onChange = {(it) => {
                                this.setNewItem('username', it)
                            }}></input>
                        </div>
                        <div className = 'db-general-name'>
                            <p className = 'pp'>Database password: </p>
                            &nbsp;
                            &nbsp;                        
                            <input className = 'pretty-input' placeholder = "Password" onChange = {(it) => {
                                this.setNewItem('password', it)
                            }}></input>
                        </div>
                        <div className = 'db-general-name'>
                            <select onChange = {(it) => {
                                this.setNewItem('type', it)
                            }}>
                                <option>MYSQL</option>
                                <option>Postresql</option>
                                <option>H2</option>
                            </select>
                        </div>
                        <div className = 'db-general-name'>
                            <select>
                                <option>Application</option>
                                <option>Another file</option>
                            </select>
                        </div>
                    </div>
        )
    }
}