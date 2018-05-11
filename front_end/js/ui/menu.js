import React from 'react'

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
        }
    }

    render () {
        return (
            <div className="col-lg-2">
                <nav>
                    <ul className="nav nav-pills flex-column">
                        <li className="nav-item">
                            <a className="nav-link active" href="#">Salas de Espera<span className="sr-only">(current)</span></a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">Marcações</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">Cliente</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">Animal</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">Produtos</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">Configurações</a>
                        </li>
                    </ul>
                    </nav>
            </div>
        )
    }
}