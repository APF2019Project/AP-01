#!/usr/bin/env bash
rm concat.uml
cat *.uml > concat.uml
sed -i '/@startuml/d' concat.uml
sed -i '/@enduml/d' concat.uml
sed -i '1 i\@startuml' concat.uml
echo "@enduml" >> concat.uml
java -jar plantuml.jar concat.uml
echo done