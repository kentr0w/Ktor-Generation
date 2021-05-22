import React, {Component} from 'react'
import './db.css'

export default class DBFields extends Component {
    constructor(props) {
        super(props)                
    }

    render() {        
        return(
            <div>
                <div>
                    <p className = 'pp'>Name:</p>
                    &nbsp;
                    &nbsp;
                    <p className = 'pp'>{this.props.field.name}</p>
                </div>                
                <div>
                    <p className = 'pp'>Type: </p>
                    &nbsp;
                    &nbsp;
                    <p className = 'pp'>{this.props.field.type}</p>
                </div>
            </div>
        )
    }
}