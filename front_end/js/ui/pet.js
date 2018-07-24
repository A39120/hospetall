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
                <table className="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Nº de registo</th>
                            <th>Nome</th>
                            <th>ID</th>
                            <th>Nome do dono</th>
                            <th>Raça</th>
                            <th>Espécie</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Bobby</td>
                            <td>1</td>
                            <td>Giancomo Guilizzoni</td>
                            <td>Rafeiro</td>
                            <td>Cão</td>
                        </tr>
                    </tbody>
                </table>
                <div align="right">
                    <button >{this.props.info.label}</button>
                </div>   
            </div>
        )
    }
}
