import React, {Component} from 'react'
import './route.css'

export default class MacroRoute extends Component {
    constructor(props) {
        super(props)        
    }

    render() {        
        return(
            <div className = 'macro-route-center-content'>                
                <div>                    
                    <p>path = {this.props.macroRoute.path}</p>                                        
                    <p>type = {this.props.macroRoute.type}</p>
                </div>                
            </div>
        )
    }
}