import React, {Component} from 'react'
import './../feature.css'
import './db.css'
import './../../../App.css'
import entity from './../../data/entity'
import DBGeneralInfo from './DBGeneralInfo'
import DBEntity from './DBEntity'

export default class DBFeature extends Component {

    constructor(props) {
        super(props)
        this.entityId = 0
        this.state = {
            newEntityName: '',
            newEntityFile: 'Application',
            newTableName: '',
            entity: [],
        }
    }

    setNewEntityName = (it) => {
        this.setState({
            newEntityName: it.target.value
        })
    }

    setNewTableName = (it) => {
        this.setState({
            newTableName: it.target.value
        })
    }

    setNewEntityFile = (it) => {
        this.setState({
            newEntityFile: it.target.value
        })
    }

    createNewEntity = () => {
        const item = {id: this.entityId, name: this.state.newEntityName, tableName: this.state.newTableName, file: this.state.newEntityFile }        
        this.props.create('dbEntities',item)
        this.setState({
            entity: [...this.state.entity, item]
        })
        this.entityId += 1
    }

    createNewRoute = (name, it) => {
        const item = {id: this.entityId, value: name}
        if (it.target.checked) {            
            this.props.create('dbRoute', item)
        } else {
            this.props.remove('dbRoute', item)
        }
    }

    findCorrectEntity = (ent) => {
        var result
        this.state.entity.map((it) => {
            if (it === ent)
            result = it
        })        
        return this.state.entity.indexOf(result)
    }

    remove = (it) => {
        this.props.remove('dbEntities', it)
        const newEntity = [...this.state.entity]
        const index = this.findCorrectEntity(it) 
        if (index != -1) {
            newEntity.splice(index,1)        
        }
        this.setState(state => {
            return {
                entity: [...newEntity]
            }
        })                   
    }

    render(){
        return(
            <div className = 'feature'>
                <div className = "db-feature">                    
                    <div className = "first-card-name"> Database Feature</div>                                        
                    <DBGeneralInfo addNewItemByTitle = {this.props.addNewItemByTitle} entity = {this.entity}/>
                    <div className = 'db-features-all-entities'>
                    {
                        this.state.entity.map((it) => {
                            return(
                                <div className = 'db-feature-entity-with-remove'>
                                    <button onClick = {() => {this.remove(it)}}>&#10005;</button>
                                    <DBEntity entity = {it} remove = {this.props.remove} create = {this.props.create}/>                                    
                                </div>                                
                            )
                        })                        
                    }
                    <div className = 'db-feature-create-new-entity'>
                        <div className = 'db-feature-create-new-entity-co'>
                            <div>
                                <p>Name: </p>
                                &nbsp;
                                &nbsp;
                                <input className = 'pretty-input' placeholder = 'Name' onChange = {this.setNewEntityName}></input>
                            </div>
                            <div>
                                <p>Table Name: </p>
                                &nbsp;
                                &nbsp;
                                <input className = 'pretty-input' placeholder = 'Name' onChange = {this.setNewTableName}></input>
                            </div>
                            <div className = 'check-box-inline'>
                                <div className = 'htr'>
                                    <p>Get All</p>
                                    &nbsp;
                                    &nbsp;
                                    <input type = 'checkbox' className = 'pretty-input' onChange = {(it) => {
                                        this.createNewRoute('GETAll', it)
                                    }}></input>
                                </div>
                                <div className = 'htr'>
                                    <p>Save</p>
                                    &nbsp;
                                    &nbsp;
                                    <input type = 'checkbox' className = 'pretty-input' onChange = {(it) => {
                                        this.createNewRoute('SAVE', it)
                                    }}></input>
                                </div>
                                <div className = 'htr'>
                                    <p>Update</p>
                                    &nbsp;
                                    &nbsp;
                                    <input type = 'checkbox' className = 'pretty-input' onChange = {(it) => {
                                        this.createNewRoute('UPDATE', it)
                                    }}></input>
                                </div>
                                <div className = 'htr'>
                                    <p>Delete</p>
                                    &nbsp;
                                    &nbsp;
                                    <input type = 'checkbox' className = 'pretty-input' onChange = {(it) => {
                                        this.createNewRoute('DELETE', it)
                                    }}></input>
                                </div>
                            </div>
                            <div className = 'db-feature-create-new-entity-fields'>
                                <p>File: </p>   
                                &nbsp;
                                &nbsp;
                                <select onChange = {this.setNewEntityFile}>
                                    <option>Application</option>
                                    <option>Another</option>
                                </select>
                            </div>
                            <button onClick = {this.createNewEntity}>Create new Entity</button>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
        )
    }
}