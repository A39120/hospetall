import React from 'react'
import ConsultationRegister from './consultation-register'

const RenderStates = {
    consultation: 'consultation',
    agenda: 'agenda',
    consultationRegister: 'consultationRegister',
    animalsList: 'animalsList',
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
        switch (this.props.renderState) {
            case RenderStates.consultation:
                return this.renderConsultation()
            case RenderStates.agenda:
                return this.renderAgenda()
            case RenderStates.consultationRegister:
                return this.renderConsultationRegister()
            case RenderStates.animalsList:
                return this.renderAnimalsList()
            case RenderStates.configs:
                return this.renderConfigs()
        }
    }

    renderConsultation () {
        return (
            <div>
                Consultas
            </div>
        )
    }

    renderAgenda () {
        return (
            <div>Agenda</div>
        )
    }

    renderConsultationRegister() {
        return ( 
            <div>
                <ConsultationRegister />
            </div>
        )    
    }

    renderAnimalsList() {
        return ( 
            <div>
                Lista de Animais
            </div>
        )    
    }

    renderConfigs () {
        return (
            <div>Configurações</div>
        )
    }
}
