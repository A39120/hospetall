import React from 'react'

export default function (props) {
    return (
        <div>
            <div className="container">
                <input type="text" className="search-query span3" placeholder="Search.."/>
                <button type="button" className="btn btn-default">
                    <span className="glyphicon glyphicon-search"></span> Search
                </button>
            </div>
            <table className="table table-bordered table-hover">
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Morada</th>
                        <th>Telemóvel</th>
                        <th>E-mail</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Giancomo Guilizzoni</td>
                        <td>96260 North Circle</td>
                        <td>687-569-2069</td>
                        <td>zglenny0@auda.org.au</td>
                    </tr>
                </tbody>
            </table>
            <div align="right">
                <button >{props.info.label}</button>
            </div>   
        </div>
    )
    
}
