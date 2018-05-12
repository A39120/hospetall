import React from 'react'
import Menu from './menu'

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
            parts: props.parts
        }
    }

    render () {
        return (
            <div className="container-fluid">
                <div className="row">
                    <div className="col-lg-2"><Menu parts={this.props.parts}/></div>
                    <div className="col-lg-10"><p>Componente Principal</p></div>
                </div>
            </div>
        )
    }
}
