# -*- coding: utf-8 -*-
"""
Created on Tue Oct 24 00:54:25 2023

@author: User
"""


import json
import joblib
import pandas as pd
import numpy as np
from datetime import datetime, timedelta
import requests
import re

import networkx as nx
import matplotlib.pyplot as plt

from sklearn.preprocessing import MinMaxScaler

import datetime as dt  # Usar 'dt' como alias en lugar de 'datetime'

import os
from pathlib import Path

from jinja2 import Template

import argparse

import funcionesGraphs
import funcionesTreatment
from unidecode import unidecode


def solucionApp (barrio, franja, rutaCSV):
    # =============================================================================
    # Declaramos las rutas necesarias
    PATH = os.getcwd()
    OUTPUTDIR = PATH + "/../Output/"
    INPUTDIR = PATH + "/../Input/"
    MODELS = PATH + "/../models/"
    
    GRAFSINPUT = INPUTDIR + "Grafs/"
    STATIONSINPUT = INPUTDIR + "Stations/"
    
    
    # =============================================================================
    #fichero = "Trips_2021_05.csv"
    
    
    
    
    # =============================================================================
    # Obtener la fecha de hoy
    hoy = dt.date.today()
    
    # Leemos la base de datos del grafo medio correspondiente 
    barrioAve = re.search(r'\d+', barrio).group()
    franjaAve = funcionesTreatment.retornaFranja(franja)
    
    # Obtener el día de la semana (0=Lunes, 1=Martes, ..., 6=Domingo)
    dia_semana = hoy.weekday()
    # Ajustar para que la semana comience en 1=Lunes
    diaAve = str((dia_semana + 1) % 7).zfill(2)
    
    # Creamos la lista de las estaciones
    estaciones = funcionesTreatment.creacionEstaciones(STATIONSINPUT)
    
    # Leemos los datos de entrada
    datosEntrada = pd.read_csv(rutaCSV)
    

    estacionesSeleccionadas = estaciones[barrio]
    
    # Nos quedamos con los datos necesarios a nivel de barrio
    dataAnalizar = funcionesTreatment.subsetBarrio(datosEntrada, barrio, estaciones)
    
    # Realizamos el subset por franja horaria
    dataAnalizar = funcionesTreatment.subsetFranja(dataAnalizar, franja=franja)
    
    # obtenemos la matriz de desplazamientos
    matrizDesplazamientos = funcionesTreatment.convertirMatriz(dataAnalizar)
    
    # Insertamos aquellas estaciones faltanes
    matrizDesplazamientos = funcionesTreatment.completar_matriz_ordenada(matrizDesplazamientos, estacionesSeleccionadas)
    
    # Normalizamos la matriz
    matrizNormalizadaDesplazamientos = funcionesTreatment.normalitzaMatriuOverall(matrizDesplazamientos)
    
    # Cargamos los datos correspondientes a los datos de las estaciones 
    estacionesInfo = pd.read_csv(STATIONSINPUT + "Stations_2022_15_12_new.csv")
    
    # Nos quedamos con la información de las estaciones seleccionadas
    estacionesEspecificas = estacionesInfo[estacionesInfo['Station Id'].isin(estacionesSeleccionadas)]
    
    # Miramos si existe la carpeta, si no es el caso la creamos
    CARPETAOUTPUT = OUTPUTDIR + hoy.strftime("%Y%m%d")
    if not os.path.exists(CARPETAOUTPUT):
        os.makedirs(CARPETAOUTPUT)
    
    # Realizamos los dibujos de los grafos
    funcionesGraphs.getPlotsInInterval(matrizDesplazamientos, estacionesSeleccionadas, estacionesEspecificas, neighbourhood_name = barrio, 
                       time_zone = franjaAve, tipo = "day")
    
    # Llegim les dades del grafos 
    grafo_average = pd.read_csv(GRAFSINPUT + "graf_" + str(barrioAve) + str(diaAve) + str(franjaAve) + ".csv")
    
    # Añadimos el nombre de las columnas  >>> Estoy AQUI
    grafo_average.columns = pd.Index(estacionesSeleccionadas, name = "Start Station Id",dtype= np.int64)
    grafo_average.index = pd.Index(estacionesSeleccionadas, name = "End Station Id",dtype= np.int64)
    
    funcionesGraphs.getPlotsInInterval(grafo_average, estacionesSeleccionadas, estacionesEspecificas, neighbourhood_name = barrio, 
                       time_zone = franjaAve, tipo = "average")
    
    # creamos la matriz para que pueda ser leeida por el modelo
    array = funcionesTreatment.convertirMatriz(matrizNormalizadaDesplazamientos, arrays = True)
    
    # Convertimos el array en data frame
    df = pd.DataFrame(array.reshape(1, -1))
    
    # Carregue o arquivo Joblib
    modelo = funcionesTreatment.retornaModel(MODELS, barri = barrio)
    
    # Aplicamos el modelo 
    response = modelo.predict(df)
    
    # Creamos el nombre del fichero JSON
    nombre_archivos = CARPETAOUTPUT + "/resultado_" + barrioAve + franjaAve + ".json"
    
    if response[0] != "Normal":
        # Llamamos a la página de barcelona activa
        txt = "Dia Anómalo"
        agenda = funcionesTreatment.accesAgenda()
        
        # Nos quedamos sólo con los eventos correspondientes a dicho evento 
        eventos = agenda.loc[agenda.neighborhood_id == str(barrioAve), ]
        
        # Nos quedamos con unas columnas necesarias
        cols = ['name', 'start_date', 'end_date', 'address_name', 'street_number_1']
        eventos = eventos[cols]
        
        eventos['start_date'] = [ev.strftime('%Y-%m-%d') if pd.notna(ev) else np.nan for ev in eventos['start_date']]
        eventos['end_date'] = [ev.strftime('%Y-%m-%d') if pd.notna(ev) else np.nan for ev in eventos['end_date']]
        
        
        eventos['eventos'] = eventos.name + "( " + eventos.start_date + " - " + eventos.end_date + ", " + eventos.address_name + " " + eventos.street_number_1 + " )"
        
        # Eliminamos los casos NA's 
        eventos = eventos.dropna(subset=['eventos'])

        eventos['barrio'] = barrio    
        eventos['franja'] = franja
        eventos['respuesta'] = "Unexpected"
        
        # Nos quedamos con las columnas necesarias
        cols = ["barrio", "franja", "respuesta", "eventos"]
        eventos = eventos[cols]
        
        # eliminamos las dobles comillas 
        eventos.eventos = [re.sub(r'(")', "", events) for events in list(eventos.eventos)]
        eventos.eventos = [re.sub(r"(')", "", events) for events in list(eventos.eventos)]
        eventos.eventos = [unidecode(events) for events in list(eventos.eventos)]

        eventos = eventos.groupby(['barrio', 'franja', 'respuesta'])['eventos'].apply(list).reset_index()        
        
        items = [{"barrio" : eventos.barrio[0],
         "franja" : eventos.franja[0], 
         "respuesta" : eventos.respuesta[0],
         "eventos" : eventos.eventos[0] }]
          
    else: 
        items = [{"barrio" : barrio,
         "franja" : franja, 
         "respuesta" : "Expected",
         "eventos" : np.nan }]
        

    # Guardar el objeto JSON en un archivo
    with open(nombre_archivos, "w") as archivo:
        json.dump(items, archivo, ensure_ascii=False, indent=4);


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--neighborhood", type=str, help="Neighborhood in which you want to carry out the study")
    parser.add_argument("--timeSlot", type=str, help="Time slot necessary for the process")
    parser.add_argument("--path", type=str, help="Path to csv")
    args = parser.parse_args()
    
    
    solucionApp(args.neighborhood, args.timeSlot, args.path)
    
