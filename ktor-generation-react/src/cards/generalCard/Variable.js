import React from 'react'
import InputVariable from './InputVariable'
import './Section.css'


function Variable({field, addNewItemByTitle}) {
    return(
        <li className = 'first-card-li'>
            <span>
                {field.title}
                &nbsp;            
                <InputVariable field = {field} addNewItemByTitle = {addNewItemByTitle}/>
            </span>
        </li>   
    )
}

export default Variable