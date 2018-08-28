import React from 'react'
import fetch from 'isomorphic-fetch'

const FetchStates = {
    loading: 'loading',
    clientList: 'clientList',
    postClient: 'postClient',
    error: 'error'
}
const prefixId = 'postClient'
const postClientUri = '/register/client'
const getClientUri = '/client'

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
            fetchState: FetchStates.loading
        }
    }

    changeToCreateClient () {
        this.setState({
            fetchState: FetchStates.postClient
        })
    }

    createClient () {
        const client = {
            givenName: document.getElementById(prefixId + 'Fname').value,
            familyName: document.getElementById(prefixId + 'Lname').value,
            telephone: document.getElementById(prefixId + 'PhoneNumber1').value,
            email: document.getElementById(prefixId + 'Email').value,
            address: document.getElementById(prefixId + 'Address').value,
            postalCode: document.getElementById(prefixId + 'PostalCode').value,
            nif: document.getElementById(prefixId + 'TaxNumber').value
        }
        const url = this.props.url + postClientUri
        console.log('POST - '+url)
        fetch(url, {
                method: 'POST',
                headers: {
                'Content-Type': 'application/json',
                'Authorization': this.getCookie()
                },
                credentials: 'same-origin',
                body: JSON.stringify(client)
        })
        .then(resp => {
            if (resp.status !== 201) {
                console.log('resp.status:'+resp.status)
                throw new Error()
            }
            this.setState({
                fetchState: FetchStates.loading
            })
            return resp.text()
        })
        .catch(error => {
            console.log(error)
            this.setState({fetchState: FetchStates.error, error: error})
        })
    }

    cancelCreateClient () {
        this.setState({
            fetchState: FetchStates.clientList
        })
    }
    

    componentDidMount () {
        if (this.state.fetchState !== FetchStates.loading) return
        const url = this.props.url + getClientUri
        console.log('GET - '+url)
        return fetch(url, {
                method: 'GET',
                headers: {
                    'Authorization': this.getCookie()
                },
                credentials: 'same-origin'
            })
            .then(resp => {
                if (resp.status !== 200) {
                    throw new Error()
                }
                return resp.json().then(json => {
                    this.setState({
                        fetchState: FetchStates.clientList,
                        json: json,
                        response: resp
                    })
                })
            })
            .catch(error => {
                this.setState({fetchState: FetchStates.error, error: error})
            })  
    }

    getCookie () {
        return document.cookie.replace(/(?:(?:^|.*;\s*)Authorization\s*\=\s*([^;]*).*$)|^.*$/, "$1")
    }

    render () {
        return (
            <div>
                <div className="container">
                    {this.renderContent()}   
                </div>
            </div>
        )        
    }

    renderContent () {
        switch (this.state.fetchState) {
            case FetchStates.clientList:
                return this.renderClientList()
            case FetchStates.postClient:
                return this.renderPostClient()
            case FetchStates.error:
                return this.renderError()
        }
    }

    renderClientList () {
        const clients = this.state.json['_embedded'].clients
        return (        
            <div>
                <input type="text" className="search-query span3" placeholder="Search.."/>
                <button type="button" className="btn btn-default">
                    <span className="glyphicon glyphicon-search"></span> Search
                </button>
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
                <div align="right">
                    <button type="button" onClick={() => this.changeToCreateClient()}>Registar cliente</button>
                </div>
            </div>
        )
    }

    renderPostClient () {
        const msg = 'Registar cliente'
        const pairs = [
            {
                id1: 'Fname',
                label1: 'Nome',
                id2: 'Lname',
                label2: 'Apelido'                
            },
            {
                id1: 'Address',
                label1: 'Morada',
                id2: 'PostalCode',
                label2: 'Código Postal'                                
            },
            {
                id1: 'TaxNumber',
                label1: 'NIF',
                id2: 'PhoneNumber1',
                label2: 'Telemóvel'                                
            }
        ]
        return (
            <div>
                <h2>{msg}</h2>
                {pairs.map(p => 
                    <div className="row" key={p.id1+p.id2}>
                        <div className="col-md-6 mb-3">
                            <label htmlFor={prefixId + p.id1} className="col-2 col-form-label">{p.label1}</label>
                            <div className="input-group mb-3">
                                <input type="text" className="form-control" id={prefixId + p.id1}/>
                            </div>
                        </div>
                        <div className="col-md-6 mb-3">
                            <label htmlFor={prefixId + p.id2} className="col-3 col-form-label">{p.label2}</label>
                            <div className="input-group mb-3">
                                <input type="text" className="form-control" id={prefixId + p.id2}/>
                            </div>
                        </div>
                    </div>
                )}
                <label htmlFor={prefixId + 'Email'} className="col-2 col-form-label">E-mail</label>
                <div className="input-group mb-3">
                    <input type="text" className="form-control" id={prefixId + 'Email'}/>
                </div>
                <div align="right">
                    <button type="button" onClick={() => this.cancelCreateClient()}>Cancelar</button>
                    <button type="button" onClick={() => this.createClient()}>Concluir</button>
                </div>                
            </div>
        )
    }

    renderError () {
        return (
            <div>ERROR</div>
        )
    }
}
