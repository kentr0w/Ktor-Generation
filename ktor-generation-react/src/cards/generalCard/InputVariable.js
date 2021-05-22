import React from 'react'
import './Section.css'
import './../../App.css'

function InputVariable({field, addNewItemByTitle}) {

    function setNewItem(it) {                    
        const item = {feature: 'global', title: field.name, value: it.target.value}
        addNewItemByTitle(item)
    }

    if (field.type === 'select') {
        return(
            <select onChange = {setNewItem}>
                {field.options.map(opt => {
                    return <option>{opt}</option>
                })} 
            </select>
        )
    } else {
        return(
            <input className = 'text-variable-placeholder pretty-input' type = 'text' placeholder = {field.default} onChange = {setNewItem}/>
        )
    }
}

export default InputVariable