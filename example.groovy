@Library(['github.com/shared-library']) _

def exampleMethod() {
    def file = new File 'http://13.52.98.219:8081/repository/maven_nikhil/com/marsh/et2/0.0.3-SNAPSHOT/maven-metadata.xml' 
def xml = new XmlParser.parseText(file)
println xml.@groupId
}

return this
