import React from 'react'

export default class extends React.Component {
    constructor (props) {
        super(props)
    }

    render () {
        return (
            <div> 
                <table className="table table-bordered table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Código</th>
                            <th>Nome</th>
                            <th>Tipo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>v102</td>
                            <td>Vacina da Gripe</td>
                            <td>Vacina</td>
                        </tr>
                    </tbody>
                </table>
                <div className="row">
                    <div className="col-md-6" align="left"><button>Alterar Stock</button></div>
                    <div className="col-md-6" align="right"><button>Adicionar Produto</button></div>
                </div>
            </div>
        )
    }
}