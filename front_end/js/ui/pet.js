import React from 'react'

const FetchStates = {
    loading: 'loading',
    loaded: 'loaded',
    error: 'error'
}
const getPetUri = '/pet'

export default class extends React.Component {
    constructor (props) {
        super(props)
        this.state = {
            fetchState: FetchStates.loading
        }
    }

    componentDidMount () {
        if (this.state.fetchState !== FetchStates.loading) return
        const url = this.props.url + getPetUri
        console.log(url)
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
            case FetchStates.loaded:
                return this.renderPetList()
            case FetchStates.error:
                return this.renderError()
        }
    }

    renderPetList () {
        const pets = this.state.json['_embedded'].pets
        return (
            <div>
                <input type="text" className="search-query span3" placeholder="Search.."/>
                <button type="button" className="btn btn-default">
                    <span className="glyphicon glyphicon-search"></span> Search
                </button>
                <table className="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Nº de registo</th>
                            <th>Nome</th>
                            <th>ID</th>
                            <th>Data de Nascimento</th>
                            <th>Raça</th>
                            <th>Espécie</th>
                        </tr>
                    </thead>
                    <tbody>
                        {pets.map(p => <tr key={p.chip_number}>
                                            <td>{p.chip_number}</td>
                                            <td>{p.name}</td>
                                            <td>{p.id}</td>
                                            <td>{p.birthdate}</td>
                                            <td>{p.race}</td>
                                            <td>{p.species}</td>
                                        </tr>
                        )}
                    </tbody>
                </table>
                <div align="right">
                    <button >{this.props.info.label}</button>
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
