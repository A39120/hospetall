import React from 'react'
import InfoCli from './info-cli'
import WaitingRoom from './waiting-room'
import Client from './client'
import Pet from './pet'
import Product from './product'

const FetchStates = {
    waitingRoom: 'waiting-room',
    appointment: 'appointment',
    client: 'client',
    pet: 'pet',
    product: 'product',
    configs: 'configs'
}

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
            urls: this.props.urls
        }
    }
    
    render () {
        return (
        <div>
            {this.renderContent()}
        </div>
        )
    }     
      
    renderContent () {
        switch (this.props.fetchState) {
            case FetchStates.waitingRoom:
                return this.renderWaitingRoom()
            case FetchStates.appointment:
                return this.renderAppointment()
            case FetchStates.client:
                return this.renderClient()
            case FetchStates.pet:
                return this.renderPet()
            case FetchStates.product:
                return this.renderProducts()
            case FetchStates.configs:
                return this.renderConfigs()
        }
    }

    renderWaitingRoom () {
        return (
            <div>
                <WaitingRoom />
            </div>
        )
    }

    renderAppointment () {
        return (
            <div>Marcações</div>
        )
    }

    renderClient() {
        return ( 
            <div>
                <Client url={this.state.urls[2]}/>
            </div>
        )    
    }

    renderPet() {
        const info = {label: 'Registar animal'}
        
        return ( 
            <div>
                <Pet info={info}/>
            </div>
        )    
    }

    renderProducts () {
        return (
            <div><Product /></div>
        )
    }

    renderConfigs () {
        return (
            <div>Configurações</div>
        )
    }
}

/*
render () {
        const text = 'Informação do Cliente'
        const inner = this.state.show ? <InfoCli /> : <div/>
        return (
            <div>
                <div>
                    <button className="btn btn-primary btn-block" onClick={this.onClickHandler}>{text}</button>
                    {inner}
                </div>
            </div>
        )
      }      
    
      onClickHandler () {
        this.setState(oldState => ({show: !oldState.show}))
      }
*/
