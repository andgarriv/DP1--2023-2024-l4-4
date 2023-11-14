import os

# Ejecutar mvn spring-boot:run en cmd
os.system("start /B start cmd.exe @cmd /k mvn spring-boot:run")

# Cambiar al directorio frontend
os.chdir("frontend")

# Ejecutar npm start en cmd
os.system("start /B start cmd.exe @cmd /k npm start")

os.chdir("..")
