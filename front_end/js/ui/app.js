import React from 'react'
import ClientList from './client-list'
import GridMenu from './grid-menu'

export default function (props) {  
  const parts = [
    {name: 'Salas de Espera', url: 'http://localhost:8081/waintingRoom'},
    {name: 'Marcações', url: 'http://localhost:8081/appointment'},
    {name: 'Cliente', url: 'http://localhost:8081/client'},
    {name: 'Animal', url: 'http://localhost:8081/pet'},
    {name: 'Produtos', url: 'http://localhost:8081/product'},
    {name: 'Configurações', url: 'http://localhost:8081/config'}
  ]
  const parts2 = [
    {name: 'Salas de Espera', url: 'http://10.10.2.56/waintingRoom'},
    {name: 'Marcações', url: 'http://10.10.2.56/appointment'},
    {name: 'Cliente', url: 'http://10.10.2.56/client'},
    {name: 'Animal', url: 'http://10.10.2.56/pet'},
    {name: 'Produtos', url: 'http://10.10.2.56/product'},
    {name: 'Configurações', url: 'http://10.10.2.56/config'}
  ]
  return (
    <GridMenu parts={parts}/>  
  )  
}
