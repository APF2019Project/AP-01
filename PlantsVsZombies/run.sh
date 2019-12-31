javac -cp lib/gson-2.8.6.jar $(find src -name '*.java') -d ~/.ap-project-dev/
if [ "$1" = "make-jar" ]; then
  echo "making jar"
  cat > ~/.ap-project-dev/Manifest.txt <<EOL
Manifest-Version: 1.0
Build-Jdk-Spec: 11
Created-By: AP-01
Main-class: main.Program
EOL
  echo "done"
else
  java -cp ~/.ap-project-dev/:lib/\*:src/ main.Program
fi
