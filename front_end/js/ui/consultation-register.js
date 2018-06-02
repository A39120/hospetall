import React from 'react'

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
            showAnimalInfo: false,
            showPhysicalCondition: false,
            showClinicalHistory: false,
        }
        this.onClickHandlerAnimalInfo = this.onClickHandlerAnimalInfo.bind(this)
        this.onClickHandlerPhysicalCondition = this.onClickHandlerPhysicalCondition.bind(this)
        this.onClickHandlerClinicalHistory = this.onClickHandlerClinicalHistory.bind(this)
    }

    render () {
        const style = {
            resize: 'none',
            width: '100%'
        }
        const inner1 = this.state.showAnimalInfo ? <div>Informação do Animal</div> : <div/>
        const inner2 = this.state.showPhysicalCondition ? <div>Estado Físico</div> : <div/>
        const inner3 = this.state.showClinicalHistory ? <div>Historial Clínico</div> : <div/>
        return (
            <div>
                <div>
                    <button className="btn btn-primary btn-block" onClick={this.onClickHandlerAnimalInfo}>Informação do Animal</button>
                    {inner1}
                </div>
                <br/>
                <div>
                    <button className="btn btn-primary btn-block" onClick={this.onClickHandlerPhysicalCondition}>Estado Físico</button>
                    {inner2}
                </div>
                <br/>
                <div>
                    <button className="btn btn-primary btn-block" onClick={this.onClickHandlerClinicalHistory}>Historial Clínico</button>
                    {inner3}
                </div>
                <br/>                
                <div className="form-group">
                    <label for="anamnese">Anamnese</label>
                    <textarea className="form-control" id="anamnese" style={style} rows="5"></textarea>
                </div>
                <div className="form-group">
                    <label for="diagnostico">Diagnóstico</label>
                    <textarea className="form-control" id="diagnostico" style={style} rows="5"></textarea>
                </div>
                <div className="form-group">
                    <label for="tratamento">Tratamento</label>
                    <textarea className="form-control" id="tratamento" style={style} rows="5"></textarea>
                </div>

                <div className="form-group row">
                    <label for="weight" className="col-2 col-form-label">Peso</label>
                    <div className="col-10">
                        <input className="form-control" type="text" id="weight"/>
                    </div>
                </div>
                <div className="form-group row">
                    <label for="temperature" className="col-2 col-form-label">Temperatura</label>
                    <div className="col-10">
                        <input className="form-control" type="text" id="temperature"/>
                    </div>
                </div>
                <div className="form-group row">
                    <label for="heartAuscultation" className="col-2 col-form-label">Auscultação Cardiaca</label>
                    <div className="col-10">
                        <input className="form-control" type="text" id="heartAuscultation"/>
                    </div>
                </div>
                <div className="form-group row">
                    <label for="pulmonaryAuscultation" className="col-2 col-form-label">Auscultação Pulmonar</label>
                    <div className="col-10">
                        <input className="form-control" type="text" id="pulmonaryAuscultation"/>
                    </div>
                </div>
                <div className="form-group row">
                    <label for="mucousMembranes" className="col-2 col-form-label">Mucosas</label>
                    <div className="col-10">
                        <input className="form-control" type="text" id="mucousMembranes"/>
                    </div>
                </div>

                <div className="form-group">
                    <label for="observacoes">Observações</label>
                    <textarea className="form-control" id="observacoes" style={style} rows="5"></textarea>
                </div>
                <div align="right">
                    <button className="btn">Concluir</button>
                </div> 
            </div>
        )
    }

    onClickHandlerAnimalInfo () {
        const show = this.state.showAnimalInfo
        this.setState({
            showAnimalInfo: !show
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
