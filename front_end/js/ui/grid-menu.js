import React from 'react'
import Menu from './menu'
import DetailedPage from './detailed-page'

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
                    <div className="col-md-2"><Menu parts={this.props.parts}/></div>
                    <div className="col-md-10"><DetailedPage /></div>
                </div>
            </div>
        )
    }
}
