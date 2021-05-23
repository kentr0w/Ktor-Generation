import React, {Component} from 'react'
import './route.css'
import RouteLocal from './RouteLocal'
import routes_local from './../../data/routes_local'
import './../../../App.css'

export default class GlobalFeature extends Component {

    constructor(props) {
        super(props)
        this.localRouteId = 0
        this.state = {            
            routes: [],
            localRouteNewPath: '',
        }
    }

    findCorrectRoutes () {        
        var result = new Array()
        this.state.routes.map((it) => {
            if (it.parentGlobalRouteId === this.props.globalFeature.id) {
                result.push(it)
            }
        })        
        return result
    }

    createNewLocalRoute = () => {
        const item = {id: this.localRouteId, parentGlobalRouteId: this.props.globalFeature.id, path: this.state.localRouteNewPath }
        this.props.create('localRoutes', item)
        this.setState({
            routes: [...this.state.routes, item]
        })
        this.localRouteId += 1
    }

    findCorrect = (fi) => {
        var result
        this.state.routes.map((it) => {
            if (it === fi)
            result = it
        })        
        return this.state.routes.indexOf(result)
    }

    remove = (it) => {
        this.props.remove('localRoutes', it)
        const newRoutes = [...this.state.routes]
        const index = this.findCorrect(it) 
        if (index != -1) {
            newRoutes.splice(index,1)        
        }
        this.setState(state => {
            return {
                routes: [...newRoutes]
            }
        })                   
    }

    createNewLocalRoutePath = (it) => {
        this.setState({
            localRouteNewPath: it.target.value
        })
    }

    render() {
        return(
            <div className = 'global-route-main-div'>                
                <div className = 'global-route-main-div-scrollable'>                                                
                    <div className = 'global-feature-info'>
                        <p>Method name = {this.props.globalFeature.methodName}</p>
                        <p>File = {this.props.globalFeature.file}</p>
                    </div>
                    <div className = 'test-two'>
                        {this.findCorrectRoutes().map((it) => {
                            return(
                                <div className = 'test'>
                                    <RouteLocal localRoute = {it} remove = {this.props.remove} create = {this.props.create}/>
                                    <button onClick = {() => {this.remove(it)}}>&#10005;</button>
                                </div>
                            )
                        })}  
                        <div className = 'test-3'>                                  
                            <div className = 'test-2'>
                                <p className = 'pp'>Path = </p>
                                <input className = 'pretty-input'onChange = {this.createNewLocalRoutePath} placeholder = 'path'></input>                                                        
                            </div>
                            <button onClick = {this.createNewLocalRoute}>Add new Route</button>                                                                
                        </div>
                    </div>                                                        
                </div>
            </div>
        )
    }
}
