import React from 'react'
import fetch from 'isomorphic-fetch'

const FetchStates = {
    loading: 'loading',
    clientList: 'clientList',
    postClient: 'postClient',
    error: 'error'
}
const prefixId = 'postClient'

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
            ['given_name']: document.getElementById(prefixId + 'Fname').value,
            ['family_name']: document.getElementById(prefixId + 'Lname').value,
            telephone: document.getElementById(prefixId + 'PhoneNumber1').value,
            email: document.getElementById(prefixId + 'Email').value,
            address: document.getElementById(prefixId + 'Address').value,
            ['postal_code']: document.getElementById(prefixId + 'PostalCode').value,
            ['telephone_alternative']: document.getElementById(prefixId + 'PhoneNumber2').value,
            nif: document.getElementById(prefixId + 'TaxNumber').value
        }
        fetch(this.props.url, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(client)
          })
          .then(resp => {
            if (resp.status !== respStatus) {
              console.log('resp.status:'+resp.status)
              throw new Error()
            }
           return resp.json().then(json => {
            this.setState({
              fetchState: FetchStates.loading,
              json: json,
              response: resp
            })
           })      
          })
          .catch(error => {
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
        const url = this.props.url
        console.log(url)
        return fetch(url)
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

    render () {
        return (
            <div>
                {this.renderContent()}   
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
        const clients = this.state.json['_embedded'].clientList
        return (        
            <div className="container">
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
        return (
            <div>
                <h2>{msg}</h2>
                <div>
                    Nome:<input type="text" id={prefixId + 'Fname'} autoFocus/>
                    Apelido:<input type="text" id={prefixId + 'Lname'} autoFocus/>
                    <br/>
                    Morada:<input type="text" id={prefixId + 'Address'} autoFocus/>
                    Código-postal:<input type="text" id={prefixId + 'PostalCode'} autoFocus/>
                    <br/>
                    NIF:<input type="text" id={prefixId + 'TaxNumber'} autoFocus/>
                    <br/>
                    E-mail:<input type="text" id={prefixId + 'Email'} autoFocus/>
                    <br/>
                    Telemóvel:<input type="text" id={prefixId + 'PhoneNumber1'} autoFocus/>
                    <br/>
                    Telemóvel Alternativo:<input type="text" id={prefixId + 'PhoneNumber2'} autoFocus/>
                    <br/>
                    <button type="button" onClick={() => this.createClient()}>{msg}</button>
                    <button type="button" onClick={() => this.cancelCreateClient()}>Cancelar</button>
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
