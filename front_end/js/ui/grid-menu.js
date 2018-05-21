import React from 'react'
import Menu from './menu'
import DetailedPage from './detailed-page'

const FetchStates = [
    {state: 'waiting-room', value: 'Salas de Espera'},
    {state: 'appointment', value: 'Marcações'},
    {state: 'client', value: 'Cliente'},
    {state: 'pet', value: 'Animal'},
    {state: 'product', value: 'Produtos'},
    {state: 'configs', value: 'Configurações'}
]

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
            fetchState: FetchStates[0].state
        }
        this.handleDetailPageChange = this.handleDetailPageChange.bind(this)   
    }

    handleDetailPageChange (buttonValue) {
        const newState = FetchStates.find(s => s.value === buttonValue)
        this.setState({
            fetchState: newState.state
        })
    }

    render () {
        return (
            <div className="container-fluid">
                <div className="row">
                    <div className="col-md-2"><Menu parts={this.props.parts} onClick={this.handleDetailPageChange}/></div>
                    <div className="col-md-10"><DetailedPage fetchState={this.state.fetchState}/></div>
                </div>
            </div>
        )
    }
}
