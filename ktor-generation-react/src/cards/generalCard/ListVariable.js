import React from 'react'
import Variable from './Variable'


function ListVariable({fields, addNewItemByTitle}) {    
    return(
        <div className = 'list-variable'>
            <ul className = 'first-card-ul'>
                {fields.map(field => {
                    return <Variable field = {field} addNewItemByTitle = {addNewItemByTitle}/>
                })}
            </ul>
        </div>
    )
}

export default ListVariable