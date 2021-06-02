import React, {Component} from 'react'
import './db.css'
import './../feature.css'
import './../../../App.css'
import db_fields from '../../data/db_fields'
import DBFields from './DBFields'

export default class DBEntity extends Component {

    constructor(props) {
        super(props)
        this.fieldId = 0
        this.state = {        
            fields: [],
            newFieldName: '',
            newFieldType: 'VARCHAR',
            newColumnName: ''
        }        
    }

    findCorrectFields = () => {
        var result = new Array()
        this.state.fields.map((it) => {
            if (it.parentEntityId === this.props.entity.id) {
                result.push(it)
            }
        })        
        return result
    }

    setNewEntityName = (it) => {
        this.setState({
            newFieldName: it.target.value
        })
    }

    setNewColumnName = (it) => {
        this.setState({
            newColumnName: it.target.value
        })
    }

    setNewEntityType = (it) => {
        this.setState({
            newFieldType: it.target.value
        })
    }

    createNewEntity = () => {
        const item = {id: this.fieldId, parentEntityId: this.props.entity.id , name: this.state.newFieldName, column: this.state.newColumnName, type: this.state.newFieldType }
        this.props.create('dbFields', item)
        this.setState({
            fields: [...this.state.fields, item]
        })
        this.fieldId += 1
    }

    findCorrectField = (fi) => {
        var result
        this.state.fields.map((it) => {
            if (it === fi)
            result = it
        })        
        return this.state.fields.indexOf(result)
    }

    remove = (it) => {
        this.props.remove('dbFields', it)                   
        const newField = [...this.state.fields]
        const index = this.findCorrectField(it) 
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
                    <p>Table Name: {this.props.entity.tableName}</p>
                    <p>File: {this.props.entity.file}</p>
                    <p>Fields:</p>
                    <div className = 'db-field-co'>
                        {
                            this.findCorrectFields().map((it) => {                                
                                return (
                                    <div>
                                        <button className =  'nb' onClick = {() => {this.remove(it)}}>&#10005;</button>
                                        <DBFields field = {it} />
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
                            <p className = 'pp'>Column Name:</p>
                            &nbsp;
                            &nbsp;
                            <input className = 'pretty-input' placeholder = 'Column name' onChange = {this.setNewColumnName}></input>
                        </div>
                        <div>
                            <p className = 'pp'>Type:</p>
                            <select onChange = {this.setNewEntityType}>
                                <option>VARCHAR</option>
                                <option>INTEGER</option>
                                <option>DOUBLE</option>
                                <option>DATE</option>
                            </select>
                        </div>
                        <button onClick = {this.createNewEntity}>Create new field</button>
                    </div>
                </div>                                   
            </div>            
        )
    }

}