import React, {Component} from 'react'
import './web.css'
import { FileDrop } from 'react-file-drop'
import WebInfo from './WebInfo'
import WebRoute from './WebRoute'

export default class WebFeature extends Component {

    constructor(props) {
        super(props)
        this.webRouteId = 0                
        this.state = {    
            webData: [],        
            newWebPath: '',
            newWebPathFile: ''
        }
    } 
    
    findCorrectWebInData = (fi) => {
        var q
        this.state.webData.map((it) => {
            if (it === fi)
                q = it
        })        
        return this.state.webData.indexOf(q)
    }

    remove = (web) => { 
        this.props.remove('webRoutes', web)                    
        const newWebs = [...this.state.webData]
        const index = this.findCorrectWebInData(web) 
        if (index != -1) {
            newWebs.splice(index,1)        
        }
        this.setState(state => {
            return {
                webData: [...newWebs]
            }
        })        
    }

    create = () => {        
        const item = {id: this.webRouteId, path: this.state.newWebPath, resource: this.state.newWebPathFile }
        this.props.create('webRoutes', item)
        this.setState({
            webData: [...this.state.webData, item]
        })    
        this.webRouteId += 1    
    }

    setNewWebPath = (it) => {
        this.setState({
            newWebPath: it.target.value
        })
    }

    setNewWebFile = (it) => {
        this.setState({
            newWebPathFile: it.target.value
        })
    }   

    render() {
        return(
            <div className = 'small-feature'>
                <div className = "first-card-name"> Web Feature</div>
                <WebInfo addNewItemByTitle = {this.props.addNewItemByTitle} create = {this.props.create}/>                
                <div className = 'oot'>                
                    {
                        this.state.webData.map((it) => {
                            return(
                                <div className = 'web-feature-routes'>
                                    <button className = 'remove-btn' onClick = {() => {
                                        this.remove(it)
                                    }}>&#10005;</button>
                                    <WebRoute route = {it}/>                                                                           
                                </div>
                            )
                        })
                    }    
                    <div className = 'web-feature-create-new'>
                        <div>
                        <div>
                            <p className = 'pp'>Path:</p>
                            &nbsp;
                            &nbsp;
                            <input className = 'pretty-input' placeholder = 'Path' onChange = {this.setNewWebPath}></input>
                        </div>
                        <div>
                            <p className = 'pp'>Path to source file:</p>
                            &nbsp;
                            &nbsp;
                            <input className = 'pretty-input' placeholder = 'source file' onChange = {this.setNewWebFile}></input>
                        </div>
                        <button onClick = { () => {
                            this.create(this.state.newWebPath, this.state.newWebPathFile)
                        }}>Create</button>   
                        </div>
                    </div> 
                    <div className = 'nbv'>
                    <FileDrop                        
                        onDrop={(files, event) => {
                            this.props.saveFile(files[0])
                            console.log('onDrop!', files, event)
                        }}
                    >
                        Drop some files here!
                    </FileDrop>
                </div>                                    
                </div>
                
            </div>
        )
    }
}