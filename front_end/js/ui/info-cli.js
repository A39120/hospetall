import React from 'react'

export default class extends React.Component {
    constructor (props) {
        super(props)
    }

    render () {
        return (
            <div>
                <div className="row">            
                    <div className="col-md-8">
                        <p>Apelido: "Apelido" Nome: "Nome"</p>
                        <p>Morada: "Morada"</p>
                        <p>Telefone: "Telefone"</p>
                        <p>email: "email"</p>                        
                    </div>
                    <div className="col-md-4">
                        <centre><img src="user.jpg" alt="user.jpg" width="160" height="160"/></centre>                        
                    </div>
                </div>
                <p>Observações:</p>
                <p>Texto de observações...</p>
            </div>
        )
    }
}
