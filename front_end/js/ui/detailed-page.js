import React from 'react'
import InfoCli from './info-cli'
import WaitingRoom from './waiting-room'
import ClientAndPet from './client-pet'

const FetchStates = {
    loading: 'loading',
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
            fetchState: FetchStates.client
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
        switch (this.state.fetchState) {
            case FetchStates.waitingRoom:
                return this.renderWaitingRoom()
            case FetchStates.client:
                return this.renderClient()
            case FetchStates.pet:
                return this.renderPet()
        }
    }

    renderWaitingRoom () {
        return (
            <div>
                <WaitingRoom />
            </div>
        )
    }

    renderClient() {
        const info = [
            {label: 'Registar cliente'}
        ]
        return ( 
            <div>
                <ClientAndPet info={info}/>
            </div>
        )    
    }

    renderPet() {
        const info = [
            {label: 'Registar animal'}
        ]
        return ( 
            <div>
                <ClientAndPet info={info}/>
            </div>
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
