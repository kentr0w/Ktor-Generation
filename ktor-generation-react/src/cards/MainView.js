import React, {Component} from 'react'
import GeneralCard from './generalCard/GeneralCard'
import './../App.css'
import FileCard from './fileCard/FileCard'
import RouteFeature from './featureCard/route_feature/RouteFeature'
import DBFeature from './featureCard/db_feature/DBFeature'
import SocketFeature from './featureCard/socket_feature/SocketFeature'
import WebFeature from './featureCard/web_feature/WebFeature'
import global_data from './data/global_data'

export default class MainView extends Component {

    constructor(props) {
        super(props);
        this.state = {
          globalData: global_data
        }
    }

    prepareConfig = () => {
        console.log(this.state.globalData)
        console.log(JSON.stringify({"config": this.state.globalData}))
        const requestOptions = {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',                
            },
            body: JSON.stringify({"titles": this.state.globalData})
        };
        //fetch('http://localhost:8080/generate/submit', requestOptions)            
    }

    setNewGlobal = (newGlobal) => {        
        this.setState(() => {
            return {
                globalData: newGlobal
            }
        })        
    }

    addNewItem = (item) => {
        this.setNewGlobal([...this.state.globalData, item])      
    }
    
    findCorrectItem = (item) => {
        var result
        this.state.globalData.map((it) => {
            if (it === item)                
                result = it            
        })        
        return this.state.globalData.indexOf(result)
    }

    removeItem = (item) => {          
        const newGlobal = [...this.state.globalData]
        const index = this.findCorrectItem(item) 
        if (index != -1) {            
            newGlobal.splice(index,1)        
        }
        this.setNewGlobal(newGlobal)                  
    }                

    addNewItemByTitle = (item) => {
        const newWebs = [...this.state.globalData]        
        newWebs.map((it) => {
            if(it.feature === item.feature) {
                it.children.map((ch) => {
                    if(ch.title === item.title){
                        ch.value = item.value
                    }
                })                                
            }
        })
        this.setNewGlobal(newWebs)
    }

    render() {
        return(
            <div className = 'mainView'>
                <GeneralCard addNewItemByTitle = {this.addNewItemByTitle}/>
                <FileCard/>                
                <RouteFeature/>
                <DBFeature addNewItemByTitle = {this.addNewItemByTitle}/>
                <SocketFeature addNewItemByTitle = {this.addNewItemByTitle}/>
                <WebFeature addNewItemByTitle = {this.addNewItemByTitle} remove = {this.removeItem} create = {this.addNewItem}/>
                <div className = 'gen-div'>
                    <button onClick = {this.prepareConfig} className = 'gen-btn'>Generate Project</button>                
                </div>
            </div>
        )
    }
}