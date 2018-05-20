import React from 'react'

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.handleChange = this.handleChange.bind(this)
    }

    handleChange (ev) {
        this.props.onClick(ev.target.value)
    }

    render () {
        return (
            <nav>
                <ul className="nav nav-pills flex-column">
                    {this.props.parts.map(part => 
                        <li className="nav-item" key={part.name}>
                            <button value={part.name} className="btn btn-link btn-block" onClick={this.handleChange}>{part.name}</button>
                        </li>)
                    }
                </ul>
            </nav>
        )
    }
}
