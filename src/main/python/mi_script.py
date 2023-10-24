import matplotlib.pyplot as plt
import numpy as np
import os

# Obtener el directorio actual
current_directory = os.getcwd()

# Generar datos para el primer gráfico
x1 = np.linspace(0, 10, 100)
y1 = np.sin(x1)

# Generar datos para el segundo gráfico
x2 = np.linspace(0, 10, 100)
y2 = np.cos(x2)

# Crear el primer gráfico
plt.figure(1)
plt.plot(x1, y1, label='y = sin(x)')
plt.xlabel('x')
plt.ylabel('y')
plt.title('Gráfico 1: y = sin(x)')
plt.legend()

# Guardar el primer gráfico como imagen en el directorio actual
plt.savefig(os.path.join(current_directory, 'grafico1.png'))

# Crear el segundo gráfico
plt.figure(2)
plt.plot(x2, y2, label='y = cos(x)')
plt.xlabel('x')
plt.ylabel('y')
plt.title('Gráfico 2: y = cos(x)')
plt.legend()

# Guardar el segundo gráfico como imagen en el directorio actual
plt.savefig(os.path.join(current_directory, 'grafico2.png'))

# Cerrar los gráficos para evitar que se muestren en la ventana
plt.close(1)
plt.close(2)

# Mostrar un mensaje en la consola
print("¡Script de Python ejecutado con éxito!")