if [ ! javac -cp lib/gson-2.8.6.jar $(find src -name '*.java') -d ~/.ap-project-dev/ ]
then
  exit 1
fi
java -cp ~/.ap-project-dev/:lib/\* main.Program