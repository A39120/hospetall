import React from 'react'

const FetchStates = {
    loading: 'loading',
    loaded: 'loaded',
    error: 'error'
}

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
            fetchState: FetchStates.loading
        }
    }

    componentDidMount () {
        if (this.state.fetchState !== FetchStates.loading) return
        const url = this.props.url
        console.log(url)
        return fetch(url)
            .then(resp => {
                if (resp.status !== 200) {
                throw new Error()
                }
                return resp.json().then(json => {
                    this.setState({
                        fetchState: FetchStates.loaded,
                        json: json,
                        response: resp
                    })
                })
            })
            .catch(error => {
                this.setState({fetchState: FetchStates.error, error: error})
            })  
    }

    render () {
        return (
            <div>
                <div className="container">
                    <input type="text" className="search-query span3" placeholder="Search.."/>
                    <button type="button" className="btn btn-default">
                        <span className="glyphicon glyphicon-search"></span> Search
                    </button>
                </div>
                {this.renderContent()}
                <div align="right">
                    <button>Registar cliente</button>
                </div>   
            </div>
        )        
    }

    renderContent () {
        switch (this.state.fetchState) {
            case FetchStates.loaded:
                return this.renderLoaded()
            case FetchStates.error:
                return this.renderError()
        }
    }

    renderLoaded () {
        const clients = this.state.json['_embedded'].clientList
        return (        
            <table className="table table-bordered table-hover">
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Morada</th>
                        <th>Telemóvel</th>
                        <th>E-mail</th>
                    </tr>
                </thead>
                <tbody>
                    {clients.map(c => <tr key={c.telephone}>
                                        <td>{c.givenName} {c.familyName}</td>
                                        <td>{c.address}</td>
                                        <td>{c.telephone}</td>
                                        <td>{c.email}</td>
                                    </tr>
                    )}
                </tbody>
            </table>
        )
    }

    renderError () {
        return (
            <div>ERROR</div>
        )
    }
}
