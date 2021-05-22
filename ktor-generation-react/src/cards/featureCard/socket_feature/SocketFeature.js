import React, {Component} from 'react'
import './socket.css'

export default class SocketFeature extends Component {

    constructor(props) {
        super(props)
    }

    setNewItem = (name, it) => {               
        const item = {feature: 'socket', title: name, value: it.target.value}
        this.props.addNewItemByTitle(item)
    }

    render(){
        return(
            <div className = "small-feature">
                <div className = "first-card-name"> Socket</div>
                <div className = 'socket-main-div'>
                    <div className = 'socket-field'>
                        <p className = 'pp'> Name:</p>
                        &nbsp;
                        &nbsp;
                        <input className = 'pretty-input' placeholder = 'Name' onChange = {(it) => {
                            this.setNewItem('name', it)
                        }}></input>
                    </div>
                    <div className = 'socket-field'>
                        <p className = 'pp'> Path:</p>
                        &nbsp;
                        &nbsp;
                        <input className = 'pretty-input' placeholder = 'Path' onChange = {(it) => {
                            this.setNewItem('webPath', it)
                        }}></input>
                    </div>
                    <div className = 'socket-field'>
                        <p className = 'pp'> Answer:</p>
                        &nbsp;
                        &nbsp;
                        <input className = 'pretty-input' placeholder = 'Answer' onChange = {(it) => {
                            this.setNewItem('answer', it)
                        }}></input>
                    </div>
                    <div className = 'socket-field'>
                        <p className = 'pp'> Close word:</p>
                        &nbsp;
                        &nbsp;
                        <input className = 'pretty-input' placeholder = 'Close word' onChange = {(it) => {
                            this.setNewItem('closeWord', it)
                        }}></input>
                    </div>
                    <div className = 'socket-field'>
                        <p className = 'pp'> Close message:</p>
                        &nbsp;
                        &nbsp;
                        <input className = 'pretty-input' placeholder = 'Close message' onChange = {(it) => {
                            this.setNewItem('closeMessage', it)
                        }}></input>
                    </div>
                    <p className = 'pp'> File: </p>
                    &nbsp;
                    &nbsp;
                    <select>
                        <option>Application</option>
                        <option>Another</option>
                    </select>
                </div>
            </div>
        )
    }
}