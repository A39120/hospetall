import React from 'react'
import fetch from 'isomorphic-fetch'

//import IssueStateFilterPanel from './issue-state-filter-panel'
//import PaginatorPanel from './paginator-panel'

const FetchStates = {
  loading: 'loading',
  loaded: 'loaded',
  error: 'error'
}

export default class extends React.Component {
  // props = {url}
  constructor (props) {
    super(props)
    this.state = {
      url: props.url,
      fetchState: FetchStates.loading,
      queryString: ''
    }
    //this.handleFilterChange = this.handleFilterChange.bind(this)
    //this.handlePagination = this.handlePagination.bind(this)
  }  

  componentDidMount () {
    this.loadIfNeeded()
  }

  loadIfNeeded () {
    if (this.state.fetchState !== FetchStates.loading) return
    const url = this.state.url
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
        {this.renderContent()}
      </div>
    )
  }

  renderContent () {
    switch (this.state.fetchState) {
      case FetchStates.loading:
        return this.renderLoading()
      case FetchStates.loaded:
        return this.renderLoaded()
      case FetchStates.error:
        return this.renderError()
    }
  }

  renderLoading () {
    return <div>... loading ...</div>
  }

  renderLoaded () {
    const clients = this.state.json['_embedded'].client
    const style = {
      marginTop: '10px'
    }
    return (
      <div>
        {clients.map(resp => <div key={resp.client.id} style={style}>{resp.client.givenName} {resp.client.familyName}</div>)}
      </div>
    )
  }

  renderError () {
    return <div> ERROR </div>
  }
}
