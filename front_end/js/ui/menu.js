import React from 'react'

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
            parts: this.props.parts
        }
    }

    render () {
        return (
            <nav>
                <ul className="nav nav-pills flex-column">
                    {this.props.parts.map(part => <li className="nav-item" key={part.name}><button className="btn btn-link btn-block">{part.name}</button></li>)}
                </ul>
            </nav>
        )
    }
}
