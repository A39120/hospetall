import React from 'react'
import ClientList from './client-list'
import GridMenu from './grid-menu'

//const ip = 'http://10.10.1.142/'
const ip = 'http://localhost:8081'

const partsRecept = [
  {name: 'Salas de Espera', url: ip+'waintingRoom'},
  {name: 'Marcações', url: ip+'appointment'},
  {name: 'Cliente', url: ip},
  {name: 'Animal', url: ip},
  {name: 'Produtos', url: ip+'product'},
  {name: 'Configurações', url: ip+'config'}
]

const partsVet = [
  {name: 'Consultas', url: ip+'waintingRoom'},
  {name: 'Agenda', url: ip+'appointment'},
  {name: 'Registo de Consultas', url: ip+'client'},
  {name: 'Lista de Animais', url: ip+'pet'},
  {name: 'Configurações', url: ip+'config'}
]

const isRecept = true

export default function (props) {
  return (
    <GridMenu parts={isRecept ? partsRecept : partsVet} isRecept={isRecept}/>
  )  
}
