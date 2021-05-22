import React, {Component} from 'react'
import './db.css'
import './../feature.css'
import './../../../App.css'
import db_fields from '../../data/db_fields'
import DBFields from './DBFields'

export default class DBEntity extends Component {

    constructor(props) {
        super(props)
        this.state = {        
            fields: db_fields,
            newFieldName: '',
            newFieldType: 'String'
        }        
    }

    findCorrectFields = () => {
        var q = new Array()
        this.state.fields.map((it) => {
            if (it.parentEntityId === this.props.entity.id) {
                q.push(it)
            }
        })        
        return q
    }

    setNewEntityName = (it) => {
        this.setState({
            newFieldName: it.target.value
        })
    }

    setNewEntityType = (it) => {
        this.setState({
            newFieldType: it.target.value
        })
    }

    createNewEntity = () => {
        this.setState({
            fields: [...this.state.fields, {id: this.state.fields.length + 1, parentEntityId: this.props.entity.id , name: this.state.newFieldName, type: this.state.newFieldType }]
        })
    }

    findCo = (fi) => {
        var q
        this.state.fields.map((it) => {
            if (it === fi)
                q = it
        })        
        return this.state.fields.indexOf(q)
    }

    remove = (it) => {
        const newField = [...this.state.fields]
        const index = this.findCo(it) 
        if (index != -1) {
            newField.splice(index,1)        
        }
        this.setState(state => {
            return {
                fields: [...newField]
            }
        })                   
    }


    render(){
        return(                 
            <div className = 'db-entity-main-div'>
                <div className = 'db-entity-border'>
                    <p>Name: {this.props.entity.name}</p>
                    <p>File: {this.props.entity.file}</p>
                    <p>Fields:</p>
                    <div className = 'db-field-co'>
                        {
                            this.findCorrectFields().map((it) => {                                
                                return (
                                    <div>
                                        <button className =  'nb' onClick = {() => {this.remove(it)}}>&#10005;</button>
                                        <DBFields field = {it}/>
                                        <div className = 'db-entity-back-line'></div>
                                    </div>
                                )
                            })
                        }
                    </div>
                    <div className = 'db-entity-create-new'>
                        <div>
                            <p className = 'pp'>Name:</p>
                            &nbsp;
                            &nbsp;
                            <input className = 'pretty-input' placeholder = 'Name' onChange = {this.setNewEntityName}></input>
                        </div>
                        <div>
                            <p className = 'pp'>Type:</p>
                            <select onChange = {this.setNewEntityType}>
                                <option>String</option>
                                <option>Integer</option>
                                <option>Double</option>
                                <option>Date</option>
                            </select>
                        </div>
                        <button onClick = {this.createNewEntity}>Create new field</button>
                    </div>
                </div>                                   
            </div>            
        )
    }

}