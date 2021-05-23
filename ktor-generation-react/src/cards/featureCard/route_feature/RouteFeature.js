import React, {Component} from 'react'
import globalRoutes from './../../data/route_data'
import GlobalFeature from './GlobalFeature'
import './route.css'
import './../feature.css'
import './../../../App.css'

export default class RouteFeature extends Component {
    constructor(props) {
        super(props)
        this.globalRoutesId = 0
        this.state = {
            globalRoutes: [],
            newGlobalRouteMethodName: "",
            newGlobalRouteFile: "Application",
        }
    }

    changeMethodName = (it) => {
        this.setState({
            newGlobalRouteMethodName: it.target.value
        })
    }

    changeFile = (it) => {
        this.setState({
            newGlobalRouteFile: it.target.value
        })
    }

    createNewGlobalRoute = () => {
        const item = {id: this.globalRoutesId, methodName: this.state.newGlobalRouteMethodName, file: this.state.newGlobalRouteFile }
        this.props.create('globalRoutes', item)
        this.setState({
            globalRoutes: [...this.state.globalRoutes, item]
        })
        this.globalRoutesId += 1
    }

    findCorrect = (fi) => {
        var result
        this.state.globalRoutes.map((it) => {
            if (it === fi)
            result = it
        })        
        return this.state.globalRoutes.indexOf(result)
    }

    removeGlobalRoute = (it) => {
        this.props.remove('globalRoutes', it)
        const newRoutes = [...this.state.globalRoutes]
        const index = this.findCorrect(it) 
        if (index != -1) {
            newRoutes.splice(index,1)        
        }
        this.setState(state => {
            return {
                globalRoutes: [...newRoutes]
            }
        })
    }

    render(){
        return(
            <div className = 'feature route-feature-main-div'>  
                <div className = 'route-feature-route-blocks'>                    
                    {this.state.globalRoutes.map((gFeature) => {
                        return (
                            <div>
                                <button onClick = {() => {this.removeGlobalRoute(gFeature)}}>&#10005;</button>
                                <GlobalFeature globalFeature = {gFeature} remove = {this.props.remove} create = {this.props.create}/>
                            </div>
                        )
                    })}                                        
                </div> 
                <div className = 'route-feature-create-route'>
                    <div>
                        <div>
                            <p className = 'pp'>Method name = </p>
                            <input className = 'pretty-input' onChange = {this.changeMethodName} placeholder = 'Method name'></input>
                        </div>
                        <div>
                            <p className = 'pp'> File</p>
                            <select onChange = {this.changeFile}>
                                <option>Application</option>
                                <option>Another file</option>
                            </select>
                        </div>
                    </div>
                    <button onClick = {this.createNewGlobalRoute}>new Route</button>
                </div>             
            </div>
        )
    }
}