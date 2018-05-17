import React from 'react'

export default class extends React.Component {
    constructor (props) {
        super(props)
    }

    render () {
        return (
            <div>
                <div className="btn-group">
                    <button type="button" className="btn btn-primary">Todas as esperas</button>
                    <button type="button" className="btn btn-primary">Consultas</button>
                    <button type="button" className="btn btn-primary">Tratamentos</button>
                </div>
                <table className="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>Posição</th>
                        <th>Id</th>
                        <th>Nome Pet</th>
                        <th>Nome Dono</th>
                        <th>Tipo</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>33</td>
                        <td>15137</td>
                        <td>Toby</td>
                        <td>Giancomo Guilizzoni</td>
                        <td>Tratamento</td>
                    </tr>
                    </tbody>
                </table>
                <div align="right">
                    <button >Adicionar Cliente</button>
                </div>                
            </div>
        )
    }
}
