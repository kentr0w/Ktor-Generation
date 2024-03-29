import React, {Component} from 'react'
import GeneralCard from './generalCard/GeneralCard'
import './../App.css'
import FileCard from './fileCard/FileCard'
import RouteFeature from './featureCard/route_feature/RouteFeature'
import DBFeature from './featureCard/db_feature/DBFeature'
import SocketFeature from './featureCard/socket_feature/SocketFeature'
import WebFeature from './featureCard/web_feature/WebFeature'
import global_data from './data/global_data'
import TreeData from './fileCard/tree/treeData';
import axios from 'axios';

export default class MainView extends Component {

    constructor(props) {
        super(props);
        this.state = {
          globalData: global_data,          
          data: TreeData,
        }        
    }

    treeDataSet = (newData) => {
        this.setState({newData})             
    }

    prepareConfig = () => {            
        this.state.globalData.global.map((it) => {
            if (it.title === "projectName"){
                if (it.value === undefined) {                        
                    return
                }
            }
        })        
        const q = {"feature" : this.state.globalData, "files" : this.state.data}
        const requestOptions = {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',                
            },
            body: JSON.stringify(q)
        };
        fetch('http://localhost:8080/generate/submit', requestOptions)
            .then(response => response.text())
            .then(text => {
                const url = 'http://localhost:8080/generate/file/';    
                axios({
                    url: url + text,
                    method: 'GET',                                        
                    responseType: 'blob',
                }).then((response) => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', 'project.zip');
                    document.body.appendChild(link);
                    link.click();
                });
            })                          
    }

    setNewGlobal = (newGlobal) => {        
        this.setState(() => {
            return {
                globalData: newGlobal
            }
        })        
    }

    addNewItem = (name, item) => {                         
        const newGlobal = this.state.globalData
        newGlobal[name].push(item)
        this.setState({
            globalData: newGlobal
        })        
    }
    
    findCorrectItem = (arr, item) => {
        var result
        arr.map((it) => {
            if (it === item)                
                result = it            
        })        
        return arr.indexOf(result)
    }

    removeItem = (name, item) => {          
        const newGlobal = this.state.globalData
        const arr = newGlobal[name]
        const index = this.findCorrectItem(arr, item)
        if (index != -1) {
            newGlobal[name].splice(index, 1)
        }
        this.setState({
            globalData: newGlobal
        })                  
    }                

    addNewItemByTitle = (name, item) => {
        const newGlobal = this.state.globalData
        newGlobal[name].map((it) => {
            if(it.title === item.title) {
                it.value = item.value
            }
        })
        this.setState({
            globalData: newGlobal
        })
    }

    makeList = () => {
        let arr = new Array()
        this.treeAsList(arr, this.state.data)
        return arr
    }

    treeAsList = (arr, el) => {
        if(el.children.length == 0) {
            arr.push(el.name)
        }
        el.children.map((it) => {
            this.treeAsList(arr, it)
        })
    }

    render() {
        let arr = this.makeList()        
        return(
            <div className = 'mainView'>
                <GeneralCard addNewItemByTitle = {this.addNewItemByTitle}/>
                <FileCard treeDataSet = {this.treeDataSet} data = {this.state.data}/>                
                <RouteFeature remove = {this.removeItem} create = {this.addNewItem}/>
                <DBFeature addNewItemByTitle = {this.addNewItemByTitle} remove = {this.removeItem} create = {this.addNewItem}/>
                <SocketFeature addNewItemByTitle = {this.addNewItemByTitle} treeData = {arr}/>
                <WebFeature addNewItemByTitle = {this.addNewItemByTitle} remove = {this.removeItem} create = {this.addNewItem}/>
                <div className = 'gen-div'>
                    <button onClick = {this.prepareConfig} className = 'gen-btn'>Generate Project</button>                
                </div>                
            </div>
        )
    }
}