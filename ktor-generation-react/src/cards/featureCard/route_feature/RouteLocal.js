import React, {Component} from 'react'
import './route.css'
import mini_route from './../../data/mini_route'
import './../../../App.css'
import MacroRoute from './MacroRoute'

export default class RouteLocal extends Component {


    constructor(props) {
        super(props)
        this.state = {
            localRoute: props.localRoute,
            miniRoutes: mini_route,
            newMacroRoutePath: '',
            newMacroRouteType: 'GET'
        }
        this.removeFun = props.remove
        
    }

    findCorrectMiniRoutes() {
        var q = []
        this.state.miniRoutes.map((it) => {
            if (it.parentLocalRouteId === this.props.localRoute.id) {
                q.push(it)
            }
        })
        
        return q
    }

    createNewMiniRoute = () => {     
        console.log(this.state.newMacroRoutePath)   
        console.log(this.state.newMacroRouteType)   
        var lateId
        if (this.state.miniRoutes.length == 0) {
            lateId = 1
        } else {
            lateId = this.state.miniRoutes[this.state.miniRoutes.length-1].id + 1
        }
        this.setState({
            miniRoutes: [...this.state.miniRoutes, {id: lateId, parentLocalRouteId: this.props.localRoute.id, path: this.state.newMacroRoutePath, type: this.state.newMacroRouteType }]
        })
    }

    findCo = (fi) => {
        var q
        this.state.miniRoutes.map((it) => {
            if (it === fi)
                q = it
        })        
        return this.state.miniRoutes.indexOf(q)
    }

    remove = (it) => {        
        const newMiniRoutes = [...this.state.miniRoutes]
        const index = this.findCo(it)        
        if (index != -1) {
            newMiniRoutes.splice(index,1)        
        }
        this.setState({
            miniRoutes: newMiniRoutes            
        })                                     
    }

    changeNewMicroRoutePath = (it) => {
        this.setState({
            newMacroRoutePath: it.target.value
        })
    }

    changeNewMicroRouteType = (it) => {
        this.setState({
            newMacroRouteType: it.target.value
        })        
    }

    render() {
        return(
            <div className = 'route-local-main-div'>
                <div className = 'qwe-counter'>
                    <div className = 'qwe'>
                        <p className = 'p-local-route-name'>Path = {this.props.localRoute.path}</p>
                        {this.findCorrectMiniRoutes().map((it, index) => {                            
                            return(
                                <div className = 'macro-route-main-div'>
                                    <MacroRoute macroRoute = {it}/>                                    
                                    <button onClick = {() => {
                                        this.remove(it)
                                    }}>remove</button>
                                </div>
                            )
                        })}
                        <div className = 'div-local-route-create-micro'>
                            <div className = 'ee-all'>
                                <div>
                                    <p className = 'pp'>path = </p>
                                    <input className = 'pretty-input' placeholder = 'path' onChange = {this.changeNewMicroRoutePath}></input>
                                    &nbsp;                        
                                    &nbsp;
                                    <p className = 'pp'>type</p>
                                    <select onChange = {this.changeNewMicroRouteType}>
                                        <option>GET</option>
                                        <option>POST</option>
                                    </select>
                                </div>
                                <button onClick = {this.createNewMiniRoute}>Add new micro</button>                    
                            </div>
                        </div>                      
                    </div>
                </div>
            </div>
        )
    }
}