import React from 'react'
import Menu from './menu'
import DetailedPage from './detailed-page'
import DetailedPageVet from './detailed-page-vet'

const RenderStatesRecept = [
    {state: 'waiting-room', value: 'Salas de Espera'},
    {state: 'appointment', value: 'Marcações'},
    {state: 'client', value: 'Cliente'},
    {state: 'pet', value: 'Animal'},
    {state: 'product', value: 'Produtos'},
    {state: 'configs', value: 'Configurações'}
]

const RenderStatesVet = [
    {state: 'consultation', value: 'Consultas'},
    {state: 'agenda', value: 'Agenda'},
    {state: 'consultationRegister', value: 'Registo de Consultas'},
    {state: 'animalsList', value: 'Lista de Animais'},
    {state: 'configs', value: 'Configurações'}
]

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
            renderState: this.props.isRecept ? RenderStatesRecept[0].state : RenderStatesVet[0].state,
            urls: this.props.parts.map(p => p.url)
        }
        this.handleDetailPageChange = this.handleDetailPageChange.bind(this)   
    }

    handleDetailPageChange (buttonValue) {
        const newState = this.props.isRecept ? RenderStatesRecept.find(s => s.value === buttonValue) : RenderStatesVet.find(s => s.value === buttonValue)
        this.setState({
            renderState: newState.state
        })
    }

    renderDetailedPageRecept () {
        return (
            <DetailedPage renderState={this.state.renderState} urls={this.state.urls}/>
        )
    }

    renderDetailedPageVet () {
        return (
            <DetailedPageVet renderState={this.state.renderState} urls={this.state.urls}/>
        )
    }

    render () {
        return (
            <div className="container-fluid">
                <div className="row">
                    <div className="col-md-2"><Menu parts={this.props.parts} onClick={this.handleDetailPageChange}/></div>
                    <div className="col-md-10">{this.props.isRecept ? this.renderDetailedPageRecept() : this.renderDetailedPageVet()}</div>
                </div>
            </div>
        )
    }
}
