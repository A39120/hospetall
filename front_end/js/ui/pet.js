import React from 'react'

export default class extends React.Component {
    constructor (props) {
        super(props)
    }

    render () {
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
                            <th>Nº de registo</th>
                            <th>Nome</th>
                            <th>ID</th>
                            <th>Nome do dono</th>
                            <th>Raça</th>
                            <th>Espécie</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Bobby</td>
                            <td>1</td>
                            <td>Giancomo Guilizzoni</td>
                            <td>Rafeiro</td>
                            <td>Cão</td>
                        </tr>
                    </tbody>
                </table>
                <div align="right">
                    <button >{this.props.info.label}</button>
                </div>   
            </div>
        )
    }
}
