import React from 'react'

const rows = 2

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
            showAnimalInfo: false,
            showClientInfo: false,
            showPhysicalCondition: false,
            showClinicalHistory: false,
        }
        this.onClickHandlerAnimalInfo = this.onClickHandlerAnimalInfo.bind(this)
        this.onClickHandlerClientInfo = this.onClickHandlerClientInfo.bind(this)
        this.onClickHandlerPhysicalCondition = this.onClickHandlerPhysicalCondition.bind(this)
        this.onClickHandlerClinicalHistory = this.onClickHandlerClinicalHistory.bind(this)
        this.finishAppointment = this.finishAppointment.bind(this)
    }

    render () {
        const style = {
            resize: 'none',
            width: '100%'
        }
        const inner1 = this.state.showAnimalInfo ? <div>Informação do Animal</div> : <div/>
        const inner2 = this.state.showClientInfo ? <div>Informação do Cliente</div> : <div/>
        const inner3 = this.state.showPhysicalCondition ? <div>Estado Físico</div> : <div/>
        const inner4 = this.state.showClinicalHistory ? <div>Historial Clínico</div> : <div/>
        return (
            <div>
                <div>
                    <button className="btn btn-primary btn-block" onClick={this.onClickHandlerAnimalInfo}>
                        Informação do Animal v
                    </button>
                    {inner1}
                </div>
                <br/>
                <div>
                    <button className="btn btn-primary btn-block" onClick={this.onClickHandlerClientInfo}>
                        Informação do Cliente v
                    </button>
                    {inner2}
                </div>
                <br/>
                <div>
                    <button className="btn btn-primary btn-block" onClick={this.onClickHandlerPhysicalCondition}>Estado Físico v</button>
                    {inner3}
                </div>
                <br/>
                <div>
                    <button className="btn btn-primary btn-block" onClick={this.onClickHandlerClinicalHistory}>Historial Clínico v</button>
                    {inner4}
                </div>
                <br/>      

                <div className="row">
                    <div className="col-md-6">
                        <div className="form-group">
                            <label htmlFor="anamnese">Anamnese</label>
                            <textarea className="form-control" id="anamnese" style={style} rows="4"></textarea>
                        </div>
                        <div className="form-group">
                            <label htmlFor="diagnostico">Diagnóstico</label>
                            <textarea className="form-control" id="diagnostico" style={style} rows="1"></textarea>
                        </div>
                        <div className="form-group">
                            <label htmlFor="tratamento">Tratamento</label>
                            <textarea className="form-control" id="tratamento" style={style} rows="3"></textarea>
                        </div>                      
                    </div>
                    <div className="col-md-6">
                        <div className="row">
                            <div className="col-md-6">
                                <div className="form-group row">
                                    <label htmlFor="weight" className="col-2 col-form-label">Peso</label>
                                    <div className="col-10">
                                        <input className="form-control" type="text" id="weight" placeholder="Kg"/>
                                    </div>
                                </div>
                            </div>
                            <div className="col-md-6">
                                <div className="form-group row">
                                    <label htmlFor="temperature" className="col-2 col-form-label">Temp</label>
                                    <div className="col-10">
                                        <input className="form-control" type="text" id="temperature" placeholder="ºC"/>
                                    </div>
                                </div>
                            </div>
                        </div>                        
                        <div className="form-group row">
                            <label htmlFor="heartAuscultation" className="col-2 col-form-label">Auscultação Cardiaca</label>
                            <div className="col-10">
                                <input className="form-control" type="text" id="heartAuscultation"/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="pulmonaryAuscultation" className="col-2 col-form-label">Auscultação Pulmonar</label>
                            <div className="col-10">
                                <input className="form-control" type="text" id="pulmonaryAuscultation"/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="mucousMembranes" className="col-2 col-form-label">Mucosas</label>
                            <div className="col-10">
                                <input className="form-control" type="text" id="mucousMembranes"/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="id1" className="col-2 col-form-label">Estado de Hidratação</label>
                            <div className="col-10">
                                <input className="form-control" type="text" id="id1"/>
                            </div>
                        </div>    
                    </div>
                </div>

                <div>
                    <label htmlFor="table">Produtos/receita</label>
                    <table className="table table-bordered" id="table">
                        <thead>
                            <tr>
                                <th>Nome</th>
                                <th>Tipo</th>
                                <th>Preço(€)</th>
                                <th>Hora de alerta</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Vacina da gripe</td>
                                <td>Vacina</td>
                                <td>0.00</td>
                                <td><input type="text" id="alert"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div className="form-group">
                    <label htmlFor="observacoes">Observações</label>
                    <textarea className="form-control" id="observacoes" style={style} rows={rows}></textarea>
                </div>
                <div align="right">
                    <button className="btn btn-primary" onClick={this.finishAppointment}>Concluir</button>
                </div> 
            </div>
        )
    }

    finishAppointment () {
        console.log('Concluir')
        const url = mainUrl + action.href
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': action.type
            },
            body: JSON.stringify(instance)
            })
            .then(resp => {
                return resp.json().then(json => {
                    this.setState({})
                })      
            })
            .catch(error => {
            this.setState({fetchState: FetchStates.error, error: error})
        })
    }

    onClickHandlerAnimalInfo () {
        const show = this.state.showAnimalInfo
        this.setState({
            showAnimalInfo: !show
        })
    }

    onClickHandlerClientInfo () {
        const show = this.state.showClientInfo
        this.setState({
            showClientInfo: !show
        })
    }

    onClickHandlerPhysicalCondition () {
        const show = this.state.showPhysicalCondition
        this.setState({
            showPhysicalCondition: !show
        })
    }

    onClickHandlerClinicalHistory () {
        const show = this.state.showClinicalHistory
        this.setState({
            showClinicalHistory: !show
        })
    }
}
