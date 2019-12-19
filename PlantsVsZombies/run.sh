javac -cp lib/gson-2.8.6.jar $(find src -name '*.java') -d ~/.ap-project-dev/
java -cp ~/.ap-project-dev/:lib/\* main.Program
