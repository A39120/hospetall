import React from 'react'
import Menu from './menu'

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
        }
    }

    render () {
        return (
            <div className="container-fluid">
                <div className="row">
                    <Menu />
                    <div className="col-lg-10"><p>Componente Principal</p></div>
                </div>
            </div>
        )
    }
}
