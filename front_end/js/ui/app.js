import React from 'react'
import ClientList from './client-list'
import GridMenu from './grid-menu'

export default function (props) {
  
  const parts = [
    {name: 'Salas de Espera', url: 'url1'},
    {name: 'Marcações', url: 'url2'},
    {name: 'Cliente', url: 'http://localhost:8081/client'},
    {name: 'Animal', url: 'url4'},
    {name: 'Produtos', url: 'url5'},
    {name: 'Configurações', url: 'url6'}
  ]
  return (
    <GridMenu parts={parts}/>  
  )
  
  /*
  const url = 'http://localhost:8081/client'
  return (
    <ClientList url={url}/>
  )
  */
  
}
