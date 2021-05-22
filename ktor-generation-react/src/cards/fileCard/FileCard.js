import React, {Component} from 'react'
import './Section.css'
import Tree from './tree/Tree'


export default class FileCard extends Component {

    constructor(props) {
        super(props);
        this.state = {
        }
    }

    render() {
        return(
            <div className = 'file-card'>
                <div className = "second-card-name">File Tree</div>          
                <Tree/>
            </div>
        )
    }
}