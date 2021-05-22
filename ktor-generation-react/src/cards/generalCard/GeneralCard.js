import React, {Component} from 'react'
import './Section.css'
import ListVariable from './ListVariable'
import FileData from './FileData'


export default class GeneralCard extends Component {

    constructor(props) {
        super(props);
        this.state = {
          data: FileData,
        }
    }

    updateValue = (e, value) => {
        e.value = value
        this.setState ({e})
    }

    render() {
        return(
            <div className = 'first-card'>
                <div className = "first-card-name"> General Information</div>
                <ListVariable fields = {this.state.data} addNewItemByTitle = {this.props.addNewItemByTitle}/>
            </div>
        )
    }
}