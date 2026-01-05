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
  read -n 1 -s -r -p "Presione cualquier tecla para continuar..."
}

compilarTarea1() {
  echo "Compilando Servidor Central..."
  cd Tarea1 || exit
  mvn package assembly:single
  cd ..
  cp Tarea1/target/servidor-central-1.0.0-jar-with-dependencies.jar ./servidor.jar
}

compilarTarea2() {
  echo "Compilando Servidor Web..."
  cd Tarea2 || exit
  mvn package
  cd ..
  cp Tarea2/target/webapp-1.0.0.war ./web.war
}

compilarMovil() {
  echo "Compilando Movil..."
  cd Movil || exit
  mvn package
  cd ..
  cp Movil/target/Movil-1.0.0.war ./movil.war
}

compilarTodo() {
  compilarTarea1
  compilarTarea2
  compilarMovil
}

main

