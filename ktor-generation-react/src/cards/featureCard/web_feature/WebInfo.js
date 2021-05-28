import React, {Component} from 'react'
import './web.css'

export default class WebInfo extends Component {
    constructor(props) {
        super(props)        
    }
    
    setNewItem = (name, it) => {           
        const item = {title: name, value: it.target.value}
        this.props.addNewItemByTitle('web', item)
    }

    render(){
        return(
            <div className = 'web-main-div'>
                    <div className = 'web-field'>
                        <p className = 'pp'>Name:</p>
                        &nbsp;
                        &nbsp;
                        <input className = 'pretty-input' placeholder = 'Name' onChange = {(it) => {
                            this.setNewItem('name', it)
                        }}></input>
                    </div>
                    <div className = 'web-field'>
                        <p className = 'pp'>File:</p>
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