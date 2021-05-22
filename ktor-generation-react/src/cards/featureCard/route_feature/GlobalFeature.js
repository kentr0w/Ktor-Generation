import React, {Component} from 'react'
import './route.css'
import RouteLocal from './RouteLocal'
import routes_local from './../../data/routes_local'
import './../../../App.css'

export default class GlobalFeature extends Component {

    constructor(props) {
        super(props)
        this.state = {            
            routes: routes_local,
            localRouteNewPath: '',
        }
    }

    findCorrectRoutes () {        
        var q = new Array()
        this.state.routes.map((it) => {
            if (it.parentGlobalRouteId === this.props.globalFeature.id) {
                q.push(it)
            }
        })
        console.log(q.length)
        return q
    }

    createNewLocalRoute = () => {
        this.setState({
            routes: [...this.state.routes, {id: this.state.routes.length + 1, parentGlobalRouteId: this.props.globalFeature.id, path: this.state.localRouteNewPath }]
        })
    }

    findCo = (fi) => {
        var q
        this.state.routes.map((it) => {
            if (it === fi)
                q = it
        })        
        return this.state.routes.indexOf(q)
    }

    remove = (it) => {
        const newRoutes = [...this.state.routes]
        const index = this.findCo(it) 
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
                                    <RouteLocal localRoute = {it}/>
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
