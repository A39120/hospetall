import React from 'react'
import ClientList from './client-list'
import GridMenu from './grid-menu'

const partsRecept = [
  {name: 'Salas de Espera', url: 'http://localhost:8081/waintingRoom'},
  {name: 'Marcações', url: 'http://localhost:8081/appointment'},
  {name: 'Cliente', url: 'http://localhost:8081/client'},
  {name: 'Animal', url: 'http://localhost:8081/pet'},
  {name: 'Produtos', url: 'http://localhost:8081/product'},
  {name: 'Configurações', url: 'http://localhost:8081/config'}
]

const partsVet = [
  {name: 'Consultas', url: 'http://10.10.2.56/waintingRoom'},
  {name: 'Agenda', url: 'http://10.10.2.56/appointment'},
  {name: 'Registo de Consultas', url: 'http://10.10.2.56/client'},
  {name: 'Lista de Animais', url: 'http://10.10.2.56/pet'},
  {name: 'Configurações', url: 'http://10.10.2.56/config'}
]

const isRecept = false

export default function (props) {
  return (
    <GridMenu parts={isRecept ? partsRecept : partsVet} isRecept={isRecept}/>
  )  
}
