import React, {Component} from 'react'
import './web.css'

export default class WebRoute extends Component {

    constructor(props) {
        super(props)        
    }

    render(){
        return(
            <div className = 'web-route-main-div'>                
                <p>Path: {this.props.route.path}</p>
                <p>From file: {this.props.route.resource}</p>
            </div>
        )
    }
}