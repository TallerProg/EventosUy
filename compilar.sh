#!/bin/env bash
main() {
  echo "----------------------------------------------"
  echo "-                EventosUy                   -"
  echo "----------------------------------------------"
  echo "- 1. Compilar todo                           -"
  echo "- 2. Compilar Servidor Central               -"
  echo "- 3. Compilar Servidor Web                   -"
  echo "- 4. Compilar Dispositivo Movil              -"
  echo "- 5. Salir                                   -"
  echo "----------------------------------------------"
  read -n 1 -p "Ingrese opcion: " mainmenuinput

  echo ""

  if [ "$mainmenuinput" = "1" ]; then
    compilarTodo
  elif [ "$mainmenuinput" = "2" ]; then
    compilarTarea1
  elif [ "$mainmenuinput" = "3" ]; then
    compilarTarea2
  elif [ "$mainmenuinput" = "4" ]; then
    compilarMovil
  elif [ "$mainmenuinput" = "5" ]; then
    exit
  else
    clear
    main
  fi

  echo ""
  echo ""
  # wait for user input to exit
  read -n 1 -s -r -p "Presione cualquier tecla para continuar..."
}

compilarTarea1() {
  echo "Compilando Servidor Central..."
  cd Tarea1
  mvn clean package
  cd ..
  cp Tarea1/target/servidor-central-1.0.0.jar ./servidor.jar
  cd Tarea1
  mvn clean
  cd ..
}

compilarTarea2() {
  echo "Compilando Servidor Web..."
  cd Tarea2
  mvn clean package
  cd ..
  cp Tarea2/target/webapp-1.0.0.war ./web.war
  cd Tarea2
  mvn clean
  cd ..
}

compilarMovil() {
  echo "Compilando Movil..."
  cd Movil
  mvn clean package
  cd ..
  cp Movil/target/movil.war ./movil.war
  cd Movil
  mvn clean
  cd ..
}

compilarTodo() {
  compilarTarea1
  compilarTarea2
  compilarMovil
}

main
