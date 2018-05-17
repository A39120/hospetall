import React from 'react'
import ClientList from './client-list'
import GridMenu from './grid-menu'

export default function (props) {
  
  const parts = [
    {name: 'Salas de Espera', url: ''},
    {name: 'Marcações', url: ''},
    {name: 'Cliente', url: ''},
    {name: 'Animal', url: ''},
    {name: 'Produtos', url: ''},
    {name: 'Configurações', url: ''}
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
